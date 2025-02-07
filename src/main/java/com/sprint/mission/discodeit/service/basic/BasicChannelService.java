package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.util.RandomStringGenerator;
import com.sprint.mission.discodeit.common.util.TimeUtil;
import com.sprint.mission.discodeit.dto.channel.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.DeleteChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.UpdateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.response.FindChannelResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.validator.ChannelValidator;
import com.sprint.mission.discodeit.validator.ReadStatusValidator;
import com.sprint.mission.discodeit.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;

    private final ChannelValidator channelValidator;
    private final UserValidator userValidator;
    private final ReadStatusValidator readStatusValidator;

    private final ChannelMapper channelMapper;
    private final RandomStringGenerator randomStringGenerator;

    @Override
    public Channel createPublicChannel(CreatePublicChannelRequest createPublicChannelRequest) {
        User channelOwner = userValidator.validateUserExistsByUserId(createPublicChannelRequest.channelOwnerId());

        Channel channel = channelMapper.toEntity(createPublicChannelRequest.name(),
                createPublicChannelRequest.description(), channelOwner, ChannelType.PUBLIC);

        channelRepository.saveChannel(channel);
        return channel;
    }

    @Override
    public Channel createPrivateChannel(CreatePrivateChannelRequest createPrivateChannelRequest) {
        User channelOwner = userValidator.validateUserExistsByUserId(createPrivateChannelRequest.channelOwnerId());

        // 요구 사항에 name, description 속성 생략 -> 임의 랜던값 지정
        String name = randomStringGenerator.generateRandomString();
        String description = "description";

        Channel channel = channelMapper.toEntity(name, description, channelOwner, ChannelType.PRIVATE);

        // 유저 초대 및 ReadStatus 생성
        for (UUID userId : createPrivateChannelRequest.channelUserList()) {
            User user = userValidator.validateUserExistsByUserId(userId);

            // service로 대체해야할까?
            ReadStatus readStatus = ReadStatus.of(user, channel);
            readStatusRepository.saveReadStatus(readStatus);

            channel.addChannelUser(user);
        }

        channelRepository.saveChannel(channel);

        return channel;
    }

    @Override
    public FindChannelResponse findChannelByIdOrThrow(UUID channelId) {
        Channel channel = channelValidator.validateChannelExistsByChannelId(channelId);

        // 가장 최근 메세지의 시간 정보(createdAt)
        // 1. repository(DB)에서 가장 최근 메세지의 시간 정보(createdAt)를 필터링하여 가져오는것 -> 가져오는 데이터 양이 적어진다.
        // 2. 애플리케이션으로 데이터를 가져와서 필터링 -> 메서드 재사용성 증가
        // service로 대체해야 할까?
        Message foundMessage = messageRepository.findAllMessagesByChannel(channel).stream()
                .max(Comparator.comparing(message -> message.getCreatedAt()))
                .orElse(null);

        // 채널에 메세지가 하나도 없을 때 시간 정보를 null로 해서 보내도 될까? -> 일단 EPOCH 로 기본값 지정
        Instant lastMessageTime = Instant.EPOCH;
        if (foundMessage != null) {
           lastMessageTime = foundMessage.getCreatedAt();
        }

        List<UUID> channelUsersIdList = new ArrayList<>();

        if (channel.isPrivate()) {
            channelUsersIdList = channel.getChannelUserList().stream()
                    .map(user -> user.getId())
                    .toList();
        }

        return channelMapper.toFindChannelResponse(channel, lastMessageTime, channelUsersIdList);
    }

    @Override
    public List<FindChannelResponse> findAllChannelsByUserId(UUID userId) {
        // PUBLIC + User가 참가한 PRIVATE 채널
        User user = userValidator.validateUserExistsByUserId(userId);

        // 여기도 repository에서 필터를 해서 조회 or service에서 필터
        List<Channel> channelList = channelRepository.findAllChannels().stream()
                .filter(channel ->
                        channel.isPublic() || (channel.isPrivate() && channel.isUserInChannel(user)))
                .toList();

        return channelList.stream()
                .map(channel -> findChannelByIdOrThrow(channel.getId()))
                .toList();
    }

    @Override
    public Channel updateChannel(UpdateChannelRequest updateChannelRequest) {
        Channel foundChannel = channelValidator.validateChannelExistsByChannelId(updateChannelRequest.channelId());

        if (foundChannel.isPrivate()) {
            throw new RuntimeException(ErrorMessage.CANNOT_UPDATE_PRIVATE_CHANNEL
                    .format("id: " + foundChannel.getId()));
        }

        UUID channelOwnerId = updateChannelRequest.channelOwnerId();

        userValidator.validateUserExistsByUserId(channelOwnerId);

        if (foundChannel.isNotOwner(channelOwnerId)) {
            throw new RuntimeException(ErrorMessage.NOT_CHANNEL_CREATOR.format("id: " + channelOwnerId));
        }

        foundChannel.updateName(updateChannelRequest.name());
        foundChannel.updateDescription(updateChannelRequest.description());
        foundChannel.updateUpdatedAt(TimeUtil.getCurrentTime());

        return channelRepository.saveChannel(foundChannel);
    }

    @Override
    public void deleteChannel(DeleteChannelRequest deleteChannelRequest) {
        UUID channelOwnerId = deleteChannelRequest.channelOwnerId();
        UUID channelId = deleteChannelRequest.channelId();

        userValidator.validateUserExistsByUserId(channelOwnerId);
        Channel foundChannel = channelValidator.validateChannelExistsByChannelId(channelId);

        if (foundChannel.isNotOwner(channelOwnerId)) {
            throw new RuntimeException(ErrorMessage.NOT_CHANNEL_CREATOR.format("id: " + channelOwnerId));
        }

        // Message, ReadStatus 함께 삭제
        messageRepository.findAllMessagesByChannel(foundChannel)
                .forEach(message -> messageRepository.removeMessage(message.getId()));

        foundChannel.getChannelUserList()
                .forEach(user -> {
                    // service 대체?
                    ReadStatus readStatus = readStatusValidator.validateReadStatusExistsByUserId(user.getId());
                    readStatusRepository.removeReadStatus(readStatus.getId());
                });

        channelRepository.removeChannel(channelId);
    }

    // inviteUsers, leaveUsers 는 남는 시간에 수정
    @Override
    public Channel inviteUsers(UUID channelId, List<User> invitedUsers) {
        Channel foundChannel = channelValidator.validateChannelExistsByChannelId(channelId);

        invitedUsers.forEach(user -> foundChannel.addChannelUser(user));

        return channelRepository.saveChannel(foundChannel);
    }

    @Override
    public Channel leaveUsers(UUID channelId, List<User> leaveUsers) {
        Channel foundChannel = channelValidator.validateChannelExistsByChannelId(channelId);

        leaveUsers.forEach(user -> foundChannel.removeUserFromChannel(user));

        return channelRepository.saveChannel(foundChannel);
    }


}
