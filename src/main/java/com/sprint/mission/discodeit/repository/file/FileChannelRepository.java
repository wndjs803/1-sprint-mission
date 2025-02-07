//package com.sprint.mission.discodeit.repository.file;
//
//import com.sprint.mission.discodeit.entity.Channel;
//import com.sprint.mission.discodeit.repository.ChannelRepository;
//import org.springframework.stereotype.Repository;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public class FileChannelRepository implements ChannelRepository {
//    private final FileStorage fileStorage;
//    private final Path directory = Paths.get(System.getProperty("user.dir"), "data", "channel");
//
//    public FileChannelRepository(FileStorage fileStorage) {
//        this.fileStorage = fileStorage;
//        fileStorage.init(directory);
//    }
//
//    @Override
//    public Channel saveChannel(Channel channel) {
//        Path filePath = directory.resolve(channel.getId().toString().concat(".ser"));
//        return fileStorage.save(filePath, channel);
//    }
//
//    @Override
//    public Channel findChannelById(UUID channelId) {
//        List<Channel> channelList = fileStorage.load(directory);
//
//        return channelList.stream()
//                .filter(channel -> channel.getId().equals(channelId))
//                .findFirst().get();
//    }
//
//    @Override
//    public List<Channel> findAllChannels() {
//        return fileStorage.load(directory);
//    }
//
//    @Override
//    public void removeChannel(UUID channelId) {
//        Channel channel = findChannelById(channelId);
//        fileStorage.remove(directory, channel);
//    }
//}
