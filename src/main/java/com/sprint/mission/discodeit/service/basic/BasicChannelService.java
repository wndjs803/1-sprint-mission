package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.UtilMethod;
import com.sprint.mission.discodeit.dto.channel.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.response.CreateChannelResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

//    @Qualifier("fileChannelRepository")
    @Qualifier("jcfChannelRepository")
    private final ChannelRepository channelRepository;
    private final UserService userService;
    private final UserValidator userValidator;
    private final ChannelMapper channelMapper;
    private final ReadStatusRepository readStatusRepository;

    @Override
    public CreateChannelResponse createPublicChannel(CreatePublicChannelRequest createPublicChannelRequest) {
        User channelOwner = userValidator.validateUserExistsByUserId(createPublicChannelRequest.channelOwnerId());

        Channel channel = channelMapper.toEntity(createPublicChannelRequest.name(),
                createPublicChannelRequest.description(), channelOwner, ChannelType.PUBLIC);

        channelRepository.saveChannel(channel);
        return channelMapper.toCreateChannelResponse(channel);
    }

    @Override
    public CreateChannelResponse createPrivateChannel(CreatePrivateChannelRequest createPrivateChannelRequest) {
        User channelOwner = userValidator.validateUserExistsByUserId(createPrivateChannelRequest.channelOwnerId());

        // 요구 사항에 name, description 속성 생략 -> 임의 랜던값 지정
        String name = generateRandomString();
        String description = "description";

        Channel channel = channelMapper.toEntity(name, description, channelOwner, ChannelType.PRIVATE);

        // 유저 초대 및 ReadStatus 생성
        for (UUID userId : createPrivateChannelRequest.channelUserList()) {
            User user = userValidator.validateUserExistsByUserId(userId);
            ReadStatus readStatus = ReadStatus.of(user, channel);
            readStatusRepository.saveReadStatus(readStatus);

            channel.addChannelUser(user);
        }

        return channelMapper.toCreateChannelResponse(channel);
    }

    @Override
    public Channel findChannelByIdOrThrow(UUID channelId) {
        return Optional.ofNullable(channelRepository.findChannelById(channelId))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.CHANNEL_NOT_FOUND.format(channelId)));
    }

    @Override
    public List<Channel> findAllChannels() {
        return channelRepository.findAllChannels();
    }

    @Override
    public Channel updateChannelName(UUID channelOwnerId, UUID channelId, String name) {
        Channel foundChannel = findChannelByIdOrThrow(channelId);
        userService.findUserByIdOrThrow(channelOwnerId);

        if (foundChannel.isNotOwner(channelOwnerId)) {
            throw new RuntimeException(ErrorMessage.NOT_CHANNEL_CREATOR.format(channelOwnerId));
        }

        foundChannel.updateName(name);
        foundChannel.updateUpdatedAt(UtilMethod.getCurrentTime());

        return channelRepository.saveChannel(foundChannel);
    }

    @Override
    public void deleteChannel(UUID channelOwnerId, UUID channelId) {
        userService.findUserByIdOrThrow(channelOwnerId);
        Channel foundChannel = findChannelByIdOrThrow(channelId);

        if (foundChannel.isNotOwner(channelOwnerId)) {
            throw new RuntimeException(ErrorMessage.NOT_CHANNEL_CREATOR.format(channelOwnerId));
        }

        channelRepository.removeChannel(channelId);
    }

    @Override
    public Channel inviteUsers(UUID channelId, List<User> invitedUserList) {
        Channel foundChannel = findChannelByIdOrThrow(channelId);

        invitedUserList.forEach(user -> foundChannel.addChannelUser(user));

        return channelRepository.saveChannel(foundChannel);
    }

    @Override
    public Channel leaveUsers(UUID channelId, List<User> leaveUserList) {
        Channel foundChannel = findChannelByIdOrThrow(channelId);

        leaveUserList.forEach(user -> foundChannel.deleteChannelUser(user));

        return channelRepository.saveChannel(foundChannel);
    }

    private String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
