package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
        // 추후 중복 검사
        Channel channel = Channel.of(name, channelOwner);

        Path filePath = directory.resolve(channel.getId().toString().concat(".ser"));
        fileStorage.save(filePath, channel);

        return channel;
    }

    @Override
    public Channel findChannelByIdOrThrow(UUID channelId) {
        return null;
    }

    @Override
    public List<Channel> findAllChannels() {
        return null;
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
