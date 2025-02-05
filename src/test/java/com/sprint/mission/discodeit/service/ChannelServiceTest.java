package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.common.ErrorMessage;
import com.sprint.mission.discodeit.common.RandomStringGenerator;
import com.sprint.mission.discodeit.dto.channel.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.response.CreateChannelResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;

import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFReadStatusRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.validator.ChannelValidator;
import com.sprint.mission.discodeit.validator.ReadStatusValidator;
import com.sprint.mission.discodeit.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChannelServiceTest {
    private ChannelRepository channelRepository;
    private ReadStatusRepository readStatusRepository;
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private ChannelValidator channelValidator;
    private UserValidator userValidator;
    private ReadStatusValidator readStatusValidator;
    private ChannelMapper channelMapper;
    @Mock
    private RandomStringGenerator randomStringGenerator;
    private ChannelService channelService;


    @BeforeEach
    void setUp() {
        channelRepository = new JCFChannelRepository();
        readStatusRepository = new JCFReadStatusRepository();
        messageRepository = new JCFMessageRepository();
        userRepository = new JCFUserRepository();
        channelValidator = new ChannelValidator(channelRepository);
        userValidator = new UserValidator(userRepository);
        readStatusValidator = new ReadStatusValidator(readStatusRepository);
        channelMapper = new ChannelMapper();
        channelService = new BasicChannelService(channelRepository, readStatusRepository, messageRepository,
                channelValidator, userValidator, readStatusValidator, channelMapper, randomStringGenerator);
    }

    private User createUser(int num) {
        User user = User.of("test" + num, "nickname" + num, "email" + num, "password" + num);
        return userRepository.saveUser(user);
    }

    @Nested
    @DisplayName("채널 생성 테스트")
    class createChannelTest {
        @Test
        @DisplayName("Public 채널 생성 성공")
        void successWithPublic() {
            // given
            User channelOwner = createUser(0);

            String channelName = "channel1";
            String description = "description";
            CreatePublicChannelRequest createPublicChannelRequest =
                    new CreatePublicChannelRequest(channelOwner.getId(), channelName, description);

            // when
            CreateChannelResponse createChannelResponse = channelService.createPublicChannel(createPublicChannelRequest);

            // then
            assertEquals(channelName, createChannelResponse.name());
            assertEquals(description, createChannelResponse.description());
        }

        @Test
        @DisplayName("Private 채널 생성 성공")
        void successWithPrivate() {
            // given
            User channelOwner = createUser(0);

            int size = 5;
            List<UUID> channelUsersIdList = new ArrayList<>();
            for (int i = 1; i < size; i++) {
                User user = createUser(i);
                channelUsersIdList.add(user.getId());
            }

            CreatePrivateChannelRequest createPrivateChannelRequest =
                    new CreatePrivateChannelRequest(channelOwner.getId(), channelUsersIdList);

            when(randomStringGenerator.generateRandomString()).thenReturn("test");

            // when
            CreateChannelResponse createChannelResponse
                    = channelService.createPrivateChannel(createPrivateChannelRequest);

            // then
            assertEquals("test", createChannelResponse.name());
            assertEquals("description", createChannelResponse.description());
        }
    }
//
//    @Nested
//    @DisplayName("채널 단일 조회 테스트")
//    class findChannelByIdOrThrowTest {
//        @Test
//        @DisplayName("채널 단일 조회 성공")
//        void success() {
//            // given
//            User channelOwner = User.of("test1", "nickname1", "email1",
//                    "password1", "profileImageUrl1", true);
//            String channelName = "channel1";
//            Channel channel = Channel.of(channelName, channelOwner);
//
//            when(channelRepository.findChannelById(channel.getId())).thenReturn(channel);
//
//            // when
//            Channel foundChannel = channelService.findChannelByIdOrThrow(channel.getId());
//
//            // then
//            assertEquals(channel.getName(), foundChannel.getName());
//        }
//
//        @Test
//        @DisplayName("존재하지 않는 채널 아이디 조회 후 예외 발생")
//        void findChannelByIdOrThrow_ThrowsException_WhenChannelIdDoesNotExist() {
//            when(channelRepository.findChannelById(any())).thenReturn(null);
//
//            UUID randomId = UUID.randomUUID();
//            assertThatThrownBy(() -> channelService.findChannelByIdOrThrow(randomId))
//                    .isInstanceOf(RuntimeException.class)
//                    .hasMessage(ErrorMessage.CHANNEL_NOT_FOUND.format(randomId));
//        }
//    }
//
//    @Nested
//    @DisplayName("채널 목록 조회 테스트")
//    class findAllChannelsTest {
//        @Test
//        @DisplayName("채널 목록 조회 성공")
//        void success() {
//            // given
//            User channelOwner = User.of("test1", "nickname1", "email1",
//                    "password1", "profileImageUrl1", true);
//            String channelName1 = "channel1";
//            String channelName2 = "channel2";
//            Channel channel1 = Channel.of(channelName1, channelOwner);
//            Channel channel2 = Channel.of(channelName2, channelOwner);
//
//            List<Channel> channelList = new ArrayList<>();
//            channelList.add(channel1);
//            channelList.add(channel2);
//
//            when(channelRepository.findAllChannels()).thenReturn(channelList);
//
//            // when
//            List<Channel> foundChannelList = channelService.findAllChannels();
//
//            // then
//            assertThat(channelList).hasSameElementsAs(foundChannelList);
//        }
//    }
//
//    @Nested
//    @DisplayName("채널명 수정 테스트")
//    class updateChannelNameTest {
//        @Test
//        @DisplayName("채널명 변경 성공")
//        void success() {
//            // given
//            User channelOwner = User.of("test1", "nickname1", "email1",
//                    "password1", "profileImageUrl1", true);
//            String channelName1 = "channel1";
//            Channel channel = Channel.of(channelName1, channelOwner);
//
//            when(channelRepository.findChannelById(any())).thenReturn(channel);
//            when(userService.findUserByIdOrThrow(any())).thenReturn(channelOwner);
//
//            when(channelRepository.saveChannel(any())).thenReturn(channel);
//
//            String updatedName = "channel2";
//
//            // when
//            Channel updatedChannel = channelService.updateChannelName(channelOwner.getId(), channel.getId(),
//                    updatedName);
//
//            // then
//            assertEquals(updatedName, updatedChannel.getName());
//        }
//
//        @Test
//        @DisplayName("채널 생성자 일치 여부 확인 후 예외 발생")
//        void updateChannelName_ThrowsException_WhenOwnerDoesNotMatch() {
//            // given
//            User channelOwner1 = User.of("test1", "nickname1", "email1",
//                    "password1", "profileImageUrl1", true);
//            User channelOwner2 = User.of("test2", "nickname2", "email2",
//                    "password2", "profileImageUrl2", true);
//            String channelName1 = "channel1";
//            Channel channel1 = Channel.of(channelName1, channelOwner1);
//
//            when(channelRepository.findChannelById(any())).thenReturn(channel1);
//            when(userService.findUserByIdOrThrow(any())).thenReturn(channelOwner1);
//
//            String updatedName = "channel2";
//
//            // when & then
//            assertThatThrownBy(() -> channelService.updateChannelName(channelOwner2.getId(), channel1.getId(),
//                    updatedName))
//                    .isInstanceOf(RuntimeException.class)
//                    .hasMessage(ErrorMessage.NOT_CHANNEL_CREATOR.format(channelOwner2.getId()));
//        }
//    }
//
//    @Nested
//    @DisplayName("채널 삭제 테스트")
//    class deleteChannelTest {
//        @Test
//        @DisplayName("채널 삭제 성공")
//        void success() {
//            // given
//            User channelOwner = User.of("test1", "nickname1", "email1",
//                    "password1", "profileImageUrl1", true);
//            String channelName1 = "channel1";
//            Channel channel1 = Channel.of(channelName1, channelOwner);
//
//            when(userService.findUserByIdOrThrow(any())).thenReturn(channelOwner);
//            when(channelRepository.findChannelById(any())).thenReturn(channel1);
//
//            // when
//            channelService.deleteChannel(channelOwner.getId(), channel1.getId());
//
//            // then
//            verify(channelRepository).removeChannel(channel1.getId());
//        }
//
//        @Test
//        @DisplayName("채널 생성자 일치 여부 확인 후 예외 발생")
//        void deleteChannel_ThrowsException_WhenOwnerDoesNotMatch() {
//            // given
//            User channelOwner = User.of("test1", "nickname1", "email1",
//                    "password1", "profileImageUrl1", true);
//            String channelName1 = "channel1";
//            Channel channel1 = Channel.of(channelName1, channelOwner);
//
//            when(userService.findUserByIdOrThrow(any())).thenReturn(channelOwner);
//            when(channelRepository.findChannelById(any())).thenReturn(channel1);
//
//            UUID randomId = UUID.randomUUID();
//
//            // when & then
//            assertThatThrownBy(() -> channelService.deleteChannel(randomId, channel1.getId()))
//                    .isInstanceOf(RuntimeException.class)
//                    .hasMessage(ErrorMessage.NOT_CHANNEL_CREATOR.format(randomId));
//        }
//    }
//
//    @Nested
//    @DisplayName("채널 인원 초대 테스트")
//    class inviteUsersTest {
//        @Test
//        @DisplayName("채널 인원 초대 성공")
//        void success() {
//            // given
//            User channelOwner = User.of("test1", "nickname1", "email1",
//                    "password1", "profileImageUrl1", true);
//            User user2 = User.of("test2", "nickname2", "email2",
//                    "password2", "profileImageUrl2", true);
//            User user3 = User.of("test3", "nickname3", "email3",
//                    "password3", "profileImageUrl3", true);
//            User user4 = User.of("test4", "nickname4", "email4",
//                    "password4", "profileImageUrl4", true);
//            String channelName1 = "channel1";
//            Channel channel1 = Channel.of(channelName1, channelOwner);
//
//            List<User> invitedUserList = new ArrayList<>();
//            invitedUserList.add(user2);
//            invitedUserList.add(user3);
//            invitedUserList.add(user4);
//
//            when(channelRepository.findChannelById(channel1.getId())).thenReturn(channel1);
//            when(channelRepository.saveChannel(channel1)).thenReturn(channel1);
//
//            // when
//            Channel invitedChannel = channelService.inviteUsers(channel1.getId(), invitedUserList);
//
//            // then
//            assertArrayEquals(invitedUserList.toArray(), invitedChannel.getChannelUserList().toArray());
//        }
//    }
//
//    @Nested
//    @DisplayName("채널 인원 나가기 테스트")
//    class leaveUsersTest {
//        @Test
//        @DisplayName("채널 인원 나가기 성공")
//        void success() {
//            // given
//            User channelOwner = User.of("test1", "nickname1", "email1",
//                    "password1", "profileImageUrl1", true);
//            User user2 = User.of("test2", "nickname2", "email2",
//                    "password2", "profileImageUrl2", true);
//            User user3 = User.of("test3", "nickname3", "email3",
//                    "password3", "profileImageUrl3", true);
//            User user4 = User.of("test4", "nickname4", "email4",
//                    "password4", "profileImageUrl4", true);
//            String channelName1 = "channel1";
//            Channel channel1 = Channel.of(channelName1, channelOwner);
//            channel1.getChannelUserList().add(user2);
//            channel1.getChannelUserList().add(user3);
//            channel1.getChannelUserList().add(user4);
//
//            List<User> leavedUserList = new ArrayList<>();
//            leavedUserList.add(user2);
//            leavedUserList.add(user3);
//            leavedUserList.add(user4);
//
//            when(channelRepository.findChannelById(channel1.getId())).thenReturn(channel1);
//            when(channelRepository.saveChannel(channel1)).thenReturn(channel1);
//
//            // when
//            Channel leavedChannel = channelService.leaveUsers(channel1.getId(), leavedUserList);
//
//            // then
//            assertEquals(0, leavedChannel.getChannelUserList().size());
//        }
//    }

}