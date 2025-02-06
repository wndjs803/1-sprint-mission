package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.MultipartFileConverter;
import com.sprint.mission.discodeit.dto.message.request.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.request.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFBinaryContentRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.validator.ChannelValidator;
import com.sprint.mission.discodeit.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageServiceTest {
    private MessageRepository messageRepository;
    private BinaryContentRepository binaryContentRepository;
    private UserRepository userRepository;
    private ChannelRepository channelRepository;

    private UserValidator userValidator;
    private ChannelValidator channelValidator;

    private MultipartFileConverter multipartFileConverter;

    private MessageMapper messageMapper;
    private MessageService messageService;


    @BeforeEach
    void setUp() {
        messageRepository = new JCFMessageRepository();
        binaryContentRepository = new JCFBinaryContentRepository();
        userRepository = new JCFUserRepository();
        channelRepository = new JCFChannelRepository();
        userValidator = new UserValidator(userRepository);
        channelValidator = new ChannelValidator(channelRepository);
        multipartFileConverter = new MultipartFileConverter();
        messageMapper = new MessageMapper();
        messageService = new BasicMessageService(messageRepository, binaryContentRepository, userValidator,
                channelValidator, multipartFileConverter, messageMapper);
    }

    private User createUser(int num) {
        User user = User.of("test" + num, "nickname" + num, "email" + num, "password" + num);
        return userRepository.saveUser(user);
    }

    private Channel createPublicChannel(User channelOwner, String channelName, String description) {
        Channel channel = Channel.of(channelName, description, channelOwner, ChannelType.PUBLIC);
        return channelRepository.saveChannel(channel);
    }

    private Message createMessage(User user, Channel channel, String content) {
        Message message = Message.of(user, channel, content);
        return messageRepository.saveMessage(message);
    }

    @Nested
    @DisplayName("메세지 생성 테스트")
    class createMessageTest {
        @Test
        @DisplayName("메세지 생성 성공")
        void success() {
            // given
            User user = createUser(0);

            String channelName = "channel1";
            String channelDescription = "description";
            Channel channel = createPublicChannel(user, channelName, channelDescription);

            String content = "hello";
            createMessage(user, channel, content);

            CreateMessageRequest createMessageRequest =
                    new CreateMessageRequest(user.getId(), channel.getId(), content);

            // when
            Message createdMessage = messageService.createMessage(createMessageRequest, new ArrayList<>());

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
            User user = createUser(0);

            String channelName = "channel1";
            String channelDescription = "description";
            Channel channel = createPublicChannel(user, channelName, channelDescription);

            String content = "hello";
            Message message = createMessage(user, channel, content);

            // when
            Message foundMessage = messageService.findMessageByIdOrThrow(message.getId());

            // then
            assertEquals(content, foundMessage.getContent());
        }

        @Test
        @DisplayName("메세지 아이디 존재 여부 확인 후 에외 발생")
        void findMessageByIdOrThrow_ThrowsException_WhenMessageIdDoesNotExist() {
            UUID randomId = UUID.randomUUID();

            assertThatThrownBy(() -> messageService.findMessageByIdOrThrow(randomId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(ErrorMessage.MESSAGE_NOT_FOUND.format("id: " + randomId));
        }
    }
//
    @Nested
    @DisplayName("메세지 목록 조회 테스트")
    class findAllMessageTest {
        @Test
        @DisplayName("메세지 목록 조회 성공")
        void success() {
            // given
            User user = createUser(0);

            String channelName = "channel1";
            String channelDescription = "description";
            Channel channel = createPublicChannel(user, channelName, channelDescription);

            String content1 = "hello";
            String content2 = "world";
            String content3 = "!!";
            Message message1 = createMessage(user, channel, content1);
            Message message2 = createMessage(user, channel, content2);
            Message message3 = createMessage(user, channel, content3);

            // when
            List<Message> foundMessageList = messageService.findAllMessagesByChannelId(channel.getId());

            // then
            assertEquals(3, foundMessageList.size());
            assertTrue(foundMessageList.contains(message1));
            assertTrue(foundMessageList.contains(message2));
            assertTrue(foundMessageList.contains(message3));
        }
    }

    @Nested
    @DisplayName("메세지 수정 테스트")
    class updateMessageTest {
        @Test
        @DisplayName("메세지 수정 성공")
        void success() {
            // given
            User user = createUser(0);

            String channelName = "channel1";
            String channelDescription = "description";
            Channel channel = createPublicChannel(user, channelName, channelDescription);

            String content = "hello";
            Message message = createMessage(user, channel, content);

            String updateContent = "hi";
            UpdateMessageRequest updateMessageRequest =
                    new UpdateMessageRequest(user.getId(), message.getId(), updateContent);

            // when
            Message updatedMessage = messageService.updateMessage(updateMessageRequest, new ArrayList<>());

            // then
            assertEquals(updateContent, updatedMessage.getContent());
        }

        @Test
        @DisplayName("메세지 생성자 일치 여부 확인 후 예외 발생")
        void updateMessage_ThrowsException_WhenOwnerDoesNotMatch() {
            // given
            User user = createUser(0);
            User anotherUser = createUser(1);

            String channelName = "channel1";
            String channelDescription = "description";
            Channel channel = createPublicChannel(user, channelName, channelDescription);

            String content = "hello";
            Message message = createMessage(user, channel, content);

            String updateContent = "hi";
            UpdateMessageRequest updateMessageRequest =
                    new UpdateMessageRequest(anotherUser.getId(), message.getId(), updateContent);

            // when & then
            assertThatThrownBy(() -> messageService.updateMessage(updateMessageRequest, new ArrayList<>()))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(ErrorMessage.NOT_MESSAGE_CREATOR.format("id: " + anotherUser.getId()));
        }
    }
//
//    @Nested
//    @DisplayName("메세지 삭제 테스트")
//    class deleteMessageTest {
//        @Test
//        @DisplayName("메세지 삭제 성공")
//        void success() {
//            // given
//            User user = User.of("test1", "nickname1", "email1",
//                    "password1", "profileImageUrl1", true);
//            String channelName = "channel1";
//            Channel channel = Channel.of(channelName, user);
//            String content = "hello";
//            Message message = Message.of(user, channel, content);
//
//            when(messageRepository.findMessageById(message.getId())).thenReturn(message);
//
//            // when
//            messageService.deleteMessage(user.getId(), message.getId());
//
//            // then
//            verify(messageRepository).removeMessage(message.getId());
//        }
//
//        @Test
//        @DisplayName("메세지 생성자 일치 여부 확인 후 예외 발생")
//        void deleteMessage_ThrowsException_WhenOwnerDoesNotMatch() {
//            // given
//            User user = User.of("test1", "nickname1", "email1",
//                    "password1", "profileImageUrl1", true);
//            String channelName = "channel1";
//            Channel channel = Channel.of(channelName, user);
//            String content = "hello";
//            Message message = Message.of(user, channel, content);
//
//            when(messageRepository.findMessageById(message.getId())).thenReturn(message);
//
//            UUID randomId = UUID.randomUUID();
//            // when & then
//            assertThatThrownBy(() -> messageService.deleteMessage(randomId, message.getId()))
//                    .isInstanceOf(RuntimeException.class)
//                    .hasMessage(ErrorMessage.NOT_MESSAGE_CREATOR.format(randomId));
//        }
//    }
//
}