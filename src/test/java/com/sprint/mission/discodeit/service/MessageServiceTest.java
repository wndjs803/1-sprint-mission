package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageServiceTest {
    private MessageService messageService;
//    private JCFMessageRepository messageRepository;
//    private JCFUserService userService;
//    private JCFChannelService channelService;

    private FileMessageRepository messageRepository;
    private FileUserService userService;
    private FileChannelService channelService;

    @BeforeEach
    void setUp() {
//        messageRepository = mock(JCFMessageRepository.class);
//        userService = mock(JCFUserService.class);
//        channelService = mock(JCFChannelService.class);
//        messageService = new JCFMessageService(messageRepository, userService, channelService);

        messageRepository = mock(FileMessageRepository.class);
        userService = mock(FileUserService.class);
        channelService = mock(FileChannelService.class);
        messageService = new FileMessageService(messageRepository, userService, channelService);
    }

    @Nested
    @DisplayName("메세지 생성 테스트")
    class createMessageTest {
        @Test
        @DisplayName("메세지 생성 성공")
        void success() {
            // given
            User user = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            String channelName = "channel1";
            Channel channel = Channel.of(channelName, user);
            String content = "hello";
            Message message = Message.of(user, channel, content);

            when(userService.findUserByIdOrThrow(any())).thenReturn(user);
            when(channelService.findChannelByIdOrThrow(any())).thenReturn(channel);

            when(messageRepository.saveMessage(any())).thenReturn(message);

            // when
            Message createdMessage = messageService.createMessage(user.getId(), channel.getId(), content);

            // then
            assertEquals(content, createdMessage.getContent());
        }
    }

    @Nested
    @DisplayName("메세지 단일 조회 테스트")
    class findMessageByIdOrThrowTest {
        @Test
        @DisplayName("메세지 단일 조회 성공")
        void success() {
            // given
            User user = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            String channelName = "channel1";
            Channel channel = Channel.of(channelName, user);
            String content = "hello";
            Message message = Message.of(user, channel, content);

            when(messageRepository.findMessageById(message.getId())).thenReturn(message);

            // when
            Message foundMessage = messageService.findMessageByIdOrThrow(message.getId());

            // then
            assertEquals(content, foundMessage.getContent());
        }

        @Test
        @DisplayName("메세지 아이디 존재 여부 확인 후 에외 발생")
        void findMessageByIdOrThrow_ThrowsException_WhenMessageIdDoesNotExist() {
            when(messageRepository.findMessageById(any())).thenReturn(null);

            assertThatThrownBy(() -> messageService.findMessageByIdOrThrow(UUID.randomUUID()))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(ErrorMessage.MESSAGE_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("메세지 목록 조회 테스트")
    class findAllMessageTest {
        @Test
        @DisplayName("메세지 목록 조회 성공")
        void success() {
            // given
            User user = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            String channelName = "channel1";
            Channel channel = Channel.of(channelName, user);
            String content1 = "hello";
            String content2 = "world";
            String content3 = "!!";
            Message message1 = Message.of(user, channel, content1);
            Message message2 = Message.of(user, channel, content2);
            Message message3 = Message.of(user, channel, content3);

            List<Message> messageList = new ArrayList<>();
            messageList.add(message1);
            messageList.add(message2);
            messageList.add(message3);

            when(messageRepository.findAllMessages()).thenReturn(messageList);

            // when
            List<Message> foundMessageList = messageService.findAllMessage();

            // then
            assertArrayEquals(messageList.toArray(), foundMessageList.toArray());
        }
    }

    @Nested
    @DisplayName("메세지 수정 테스트")
    class updateMessageTest {
        @Test
        @DisplayName("메세지 수정 성공")
        void success() {
            // given
            User user = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            String channelName = "channel1";
            Channel channel = Channel.of(channelName, user);
            String content = "hello";
            Message message = Message.of(user, channel, content);

            when(userService.findUserByIdOrThrow(user.getId())).thenReturn(user);
            when(messageRepository.findMessageById(any())).thenReturn(message);

            when(messageRepository.saveMessage(any())).thenReturn(message);
            String updatedContent = "hi";

            // when
            Message updatedMessage = messageService.updateMessage(user.getId(), message.getId(), updatedContent);

            // then
            assertEquals(updatedContent, updatedMessage.getContent());
        }

        @Test
        @DisplayName("메세지 생성자 일치 여부 확인 후 예외 발생")
        void updateMessage_ThrowsException_WhenOwnerDoesNotMatch() {
            // given
            User user = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            String channelName = "channel1";
            Channel channel = Channel.of(channelName, user);
            String content = "hello";
            Message message = Message.of(user, channel, content);

            when(userService.findUserByIdOrThrow(user.getId())).thenReturn(user);
            when(messageRepository.findMessageById(any())).thenReturn(message);

            String updatedContent = "hi";

            // when & then
            assertThatThrownBy(() -> messageService.updateMessage(UUID.randomUUID(), message.getId(), updatedContent))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(ErrorMessage.NOT_MESSAGE_CREATOR.getMessage());
        }
    }

    @Nested
    @DisplayName("메세지 삭제 테스트")
    class deleteMessageTest {
        @Test
        @DisplayName("메세지 삭제 성공")
        void success() {
            // given
            User user = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            String channelName = "channel1";
            Channel channel = Channel.of(channelName, user);
            String content = "hello";
            Message message = Message.of(user, channel, content);

            when(messageRepository.findMessageById(message.getId())).thenReturn(message);

            // when
            messageService.deleteMessage(user.getId(), message.getId());

            // then
            verify(messageRepository).removeMessage(message.getId());
        }

        @Test
        @DisplayName("메세지 생성자 일치 여부 확인 후 예외 발생")
        void deleteMessage_ThrowsException_WhenOwnerDoesNotMatch() {
            // given
            User user = User.of("test1", "nickname1", "email1",
                    "password1", "profileImageUrl1", true);
            String channelName = "channel1";
            Channel channel = Channel.of(channelName, user);
            String content = "hello";
            Message message = Message.of(user, channel, content);

            when(messageRepository.findMessageById(message.getId())).thenReturn(message);

            // when & then
            assertThatThrownBy(() -> messageService.deleteMessage(UUID.randomUUID(), message.getId()))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(ErrorMessage.NOT_MESSAGE_CREATOR.getMessage());
        }
    }

}