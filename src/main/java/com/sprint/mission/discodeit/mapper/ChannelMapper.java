package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelMapper {

  private final MessageRepository messageRepository;
  private final ReadStatusRepository readStatusRepository;
  private final UserMapper userMapper;

  public Channel toEntity(String name, String description, ChannelType channelType) {
    return Channel.of(name, description, channelType);
  }

  public ChannelDto toChannelDto(Channel channel) {
    return new ChannelDto(channel.getId(), channel.getChannelType(),
        channel.getName(), channel.getDescription(), getChannelUserList(channel),
        getLastMessageAt());
  }

  private List<UserDto> getChannelUserList(Channel channel) {
    List<UserDto> channelUserList = new ArrayList<>();

    if (channel.isPrivate()) {
      channelUserList = readStatusRepository.findAllReadStatusByChannel(channel).stream()
          .map(readStatus -> userMapper.toUserDto(readStatus.getUser()))
          .toList();
    }

    return channelUserList;
  }

  private Instant getLastMessageAt() {
    Optional<Message> foundMessage = messageRepository.findLastMessage();

    // 채널에 메세지가 하나도 없을 때 시간 정보를 null로 해서 보내도 될까? -> 일단 null 로 기본값 지정
    Instant lastMessageAt = null;
    if (foundMessage.isPresent()) {
      lastMessageAt = foundMessage.get().getCreatedAt();
    }

    return lastMessageAt;
  }
}
