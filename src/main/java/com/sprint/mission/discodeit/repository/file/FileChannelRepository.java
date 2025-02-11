package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {
    private final FileStorage fileStorage;
    private final Path directory = Paths.get(System.getProperty("user.dir"), "data", "channel");

    private FileChannelRepository(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
        fileStorage.init(directory);
    }

    public static FileChannelRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FileChannelRepository INSTANCE = new FileChannelRepository(new FileStorage());
    }

    @Override
    public Channel saveChannel(Channel channel) {
        Path filePath = directory.resolve(channel.getId().toString().concat(".ser"));
        return fileStorage.save(filePath, channel);
    }

    @Override
    public Channel findChannelById(UUID channelId) {
        List<Channel> channelList = fileStorage.load(directory);

        return channelList.stream()
                .filter(channel -> channel.getId().equals(channelId))
                .findFirst().get();
    }

    @Override
    public List<Channel> findAllChannels() {
        return fileStorage.load(directory);
    }

    @Override
    public void removeChannel(UUID channelId) {
        Channel channel = findChannelById(channelId);
        fileStorage.remove(directory, channel);
    }
}
