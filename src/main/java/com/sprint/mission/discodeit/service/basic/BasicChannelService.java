//package com.sprint.mission.discodeit.service.basic;
//
//import com.sprint.mission.discodeit.common.ErrorMessage;
//import com.sprint.mission.discodeit.common.UtilMethod;
//import com.sprint.mission.discodeit.entity.Channel;
//import com.sprint.mission.discodeit.entity.User;
//import com.sprint.mission.discodeit.repository.ChannelRepository;
//import com.sprint.mission.discodeit.service.ChannelService;
//import com.sprint.mission.discodeit.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class BasicChannelService implements ChannelService {
//
////    @Qualifier("fileChannelRepository")
//    @Qualifier("jcfChannelRepository")
//    private final ChannelRepository channelRepository;
//    private final UserService userService;
//
//    @Override
//    public Channel createChannel(UUID channelOwnerId, String name) {
//        User channelOwner = userService.findUserByIdOrThrow(channelOwnerId);
//
//        Channel channel = Channel.of(name, channelOwner);
//
//        return channelRepository.saveChannel(channel);
//    }
//
//    @Override
//    public Channel findChannelByIdOrThrow(UUID channelId) {
//        return Optional.ofNullable(channelRepository.findChannelById(channelId))
//                .orElseThrow(() -> new RuntimeException(ErrorMessage.CHANNEL_NOT_FOUND.format(channelId)));
//    }
//
//    @Override
//    public List<Channel> findAllChannels() {
//        return channelRepository.findAllChannels();
//    }
//
//    @Override
//    public Channel updateChannelName(UUID channelOwnerId, UUID channelId, String name) {
//        Channel foundChannel = findChannelByIdOrThrow(channelId);
//        userService.findUserByIdOrThrow(channelOwnerId);
//
//        if (foundChannel.isNotOwner(channelOwnerId)) {
//            throw new RuntimeException(ErrorMessage.NOT_CHANNEL_CREATOR.format(channelOwnerId));
//        }
//
//        foundChannel.updateName(name);
//        foundChannel.updateUpdatedAt(UtilMethod.getCurrentTime());
//
//        return channelRepository.saveChannel(foundChannel);
//    }
//
//    @Override
//    public void deleteChannel(UUID channelOwnerId, UUID channelId) {
//        userService.findUserByIdOrThrow(channelOwnerId);
//        Channel foundChannel = findChannelByIdOrThrow(channelId);
//
//        if (foundChannel.isNotOwner(channelOwnerId)) {
//            throw new RuntimeException(ErrorMessage.NOT_CHANNEL_CREATOR.format(channelOwnerId));
//        }
//
//        channelRepository.removeChannel(channelId);
//    }
//
//    @Override
//    public Channel inviteUsers(UUID channelId, List<User> invitedUserList) {
//        Channel foundChannel = findChannelByIdOrThrow(channelId);
//
//        invitedUserList.forEach(user -> foundChannel.addChannelUser(user));
//
//        return channelRepository.saveChannel(foundChannel);
//    }
//
//    @Override
//    public Channel leaveUsers(UUID channelId, List<User> leaveUserList) {
//        Channel foundChannel = findChannelByIdOrThrow(channelId);
//
//        leaveUserList.forEach(user -> foundChannel.deleteChannelUser(user));
//
//        return channelRepository.saveChannel(foundChannel);
//    }
//}
