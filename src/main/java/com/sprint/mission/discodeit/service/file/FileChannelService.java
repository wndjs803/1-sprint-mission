package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileChannelService implements ChannelService {
    private final FileStorage fileStorage;
    private final FileUserService fileUserService;
    private final Path directory = Paths.get(System.getProperty("user.dir"), "data", "channel");

    private FileChannelService(FileStorage fileStorage, FileUserService fileUserService) {
        this.fileStorage = fileStorage;
        this.fileUserService = fileUserService;
        fileStorage.init(directory);
    }

    public static FileChannelService getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FileChannelService INSTANCE =
                new FileChannelService(new FileStorage(), FileUserService.getInstance());
    }

    @Override
    public Channel createChannel(UUID channelOwnerId, String name) {
        User channelOwner = fileUserService.findUserByIdOrThrow(channelOwnerId);

        Channel channel = Channel.of(name, channelOwner);

        Path filePath = directory.resolve(channel.getId().toString().concat(".ser"));
        fileStorage.save(filePath, channel);

        return channel;
    }

    @Override
    public Channel findChannelByIdOrThrow(UUID channelId) {
        List<Channel> channelList = fileStorage.load(directory);

        Optional<Channel> optionalChannel = channelList.stream()
                .filter(channel -> channel.getId().equals(channelId))
                .findFirst();

        return optionalChannel.orElseThrow(() -> new RuntimeException(ErrorMessage.CHANNEL_NOT_FOUND.getMessage()));
    }

    @Override
    public List<Channel> findAllChannels() {
        return fileStorage.load(directory);
    }

    @Override
    public void updateChannelName(UUID channelOwnerId, UUID channelId, String name) {

    }

    @Override
    public void deleteChannel(UUID channelOwnerId, UUID channelId) {

    }

    @Override
    public void inviteUsers(UUID channelId, List<User> invitedUserList) {

    }

    @Override
    public void leaveUsers(UUID channelId, List<User> leaveUserList) {

    }
}
