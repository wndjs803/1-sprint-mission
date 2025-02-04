package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class DiscodeitApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

//        JCFUserService jcfUserService = new JCFUserService(JCFUserRepository.getInstance());
//        JCFChannelService jcfChannelService = new JCFChannelService(JCFChannelRepository.getInstance(), jcfUserService);
//        JCFMessageService jcfMessageService = new JCFMessageService(JCFMessageRepository.getInstance(),
//                jcfUserService, jcfChannelService);
//
//        FileUserService fileUserService = new FileUserService(FileUserRepository.getInstance());
//        FileChannelService fileChannelService = new FileChannelService(FileChannelRepository.getInstance(),
//                fileUserService);
//        FileMessageService fileMessageService = new FileMessageService(FileMessageRepository.getInstance(),
//                fileUserService, fileChannelService);

        FileUserRepository fileUserRepository = context.getBean(FileUserRepository.class);
        FileChannelRepository fileChannelRepository = context.getBean(FileChannelRepository.class);
        FileMessageRepository fileMessageRepository = context.getBean(FileMessageRepository.class);

        BasicUserService basicUserService = context.getBean(BasicUserService.class);
        BasicChannelService basicChannelService = context.getBean(BasicChannelService.class);
        BasicMessageService basicMessageService = context.getBean(BasicMessageService.class);

        UserService userService = basicUserService;
        ChannelService channelService = basicChannelService;
        MessageService messageService = basicMessageService;

        // User test
        // user 생성
        System.out.println("======= User ========");
        System.out.println("유저 생성");

        User user1 = userService.createUser("test1", "nickname1", "email1",
                "password1", "profileImageUrl1");
        User user2 = userService.createUser("test2", "nickname2", "email2",
                "password2", "profileImageUrl2");
        User user3 = userService.createUser("test3", "nickname3", "email3",
                "password3", "profileImageUrl3");
        User user4 = userService.createUser("test4", "nickname4", "email4",
                "password4", "profileImageUrl4");

        System.out.println(user1.getName());
        System.out.println(user1.getCreatedAt());
        System.out.println();

        // user 조회
        System.out.println("유저 조회");

        User findUser1 = userService.findUserByIdOrThrow(user1.getId());
        UUID findUser1Id = findUser1.getId();

        System.out.println(findUser1.getName());
        System.out.println();

        // user 목록 조회
        System.out.println("유저 목록 조회");

        List<User> userList = userService.findAllUsers();

        userList.forEach(user -> System.out.println(user.getName()));
        System.out.println();

        // user 업데이트
        System.out.println("유저 업데이트");
        System.out.println("변경 전");
        System.out.println(findUser1.getName());

        userService.updateUser(findUser1Id, "updateTest1", "nickname1", "email1",
                "password1", "profileImageUrl1");
        User updateUser1 = userService.findUserByIdOrThrow(findUser1Id);

        System.out.println("변경 후");
        System.out.println(updateUser1.getName());
        System.out.println();

        // user 삭제
        System.out.println("유저 삭제");

        userService.deleteUser(findUser1Id);
        try {
            userService.findUserByIdOrThrow(findUser1Id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();


        // Channel test
        // 채널 생성
        System.out.println("======= Channel ========");
        System.out.println("채널 생성");

        UUID channelOwnerId = user2.getId();
        Channel channel1 = channelService.createChannel(channelOwnerId, "channel1");
        Channel channel2 = channelService.createChannel(channelOwnerId, "channel2");

        System.out.println(channel1.getName());
        System.out.println();

        // channel 조회
        System.out.println("채널 조회");

        Channel findChannel1 = channelService.findChannelByIdOrThrow(channel1.getId());
        UUID findChannel1Id = findChannel1.getId();

        System.out.println(findChannel1.getName());
        System.out.println();

        // 채널 목록 조회
        System.out.println("채널 목록 조회");

        List<Channel> channelList = channelService.findAllChannels();

        channelList.forEach(channel -> System.out.println(channel.getName()));
        System.out.println();

        // 채널 인원 초대
        System.out.println("채널 인원 초대");

        List<User> channelUsers = new ArrayList<>();
        channelUsers.add(user3);
        channelUsers.add(user4);
        channelService.inviteUsers(findChannel1Id, channelUsers);

        Channel invitedChannel = channelService.findChannelByIdOrThrow(findChannel1Id);

        invitedChannel.getChannelUserList().forEach(user -> System.out.println(user.getName()));
        System.out.println("채널 인원 수");
        System.out.println(invitedChannel.getChannelUserList().size());
        System.out.println();

        // 채널 나가기
        System.out.println("채널 나가기");

        channelService.leaveUsers(findChannel1Id, channelUsers);

        Channel leavedChannel = channelService.findChannelByIdOrThrow(findChannel1Id);

        leavedChannel.getChannelUserList().forEach(user -> System.out.println(user.getName()));
        System.out.println("채널 인원 수");
        System.out.println(leavedChannel.getChannelUserList().size());
        System.out.println();

        // 채널명 변경
        System.out.println("채널명 변경");
        System.out.println("변경 전");
        System.out.println(findChannel1.getName());
        System.out.println();

        channelService.updateChannelName(channelOwnerId, findChannel1Id, "updateChannel1");
        Channel updatedChannel = channelService.findChannelByIdOrThrow(findChannel1Id);

        System.out.println("변경 후");
        System.out.println(updatedChannel.getName());
        System.out.println();

        // 채널 삭제
        System.out.println("채널 삭제");
        channelService.deleteChannel(channelOwnerId, findChannel1Id);
        try {
            channelService.findChannelByIdOrThrow(findChannel1Id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        // Message test
        // 메세지 생성
        System.out.println("======= Message ========");
        System.out.println("메세지 생성");

        UUID sendUserId = user3.getId();
        UUID channelId = channel2.getId();
        Message message1 = messageService.createMessage(sendUserId, channelId, "hello");
        Message message2 = messageService.createMessage(sendUserId, channelId, "world");

        System.out.println(message1.getContent());
        System.out.println(message2.getContent());
        System.out.println();

        // 메세지 조회
        System.out.println("메세지 조회");

        Message findMessage1 = messageService.findMessageByIdOrThrow(message1.getId());

        System.out.println(findMessage1.getContent());
        System.out.println();

        // 메세지 목록 조회
        System.out.println("메세지 목록 조회");

        List<Message> findMessagList = messageService.findAllMessage();

        findMessagList.forEach(message -> System.out.println(message.getContent()));
        System.out.println();

        // 메세지 수정
        System.out.println("메세지 수정");
        System.out.println("변경 전");
        System.out.println(findMessage1.getContent());

        UUID findMessage1Id = findMessage1.getId();
        messageService.updateMessage(sendUserId, findMessage1Id, "hi");
        Message updatedMessage = messageService.findMessageByIdOrThrow(findMessage1Id);

        System.out.println("변경 후");
        System.out.println(updatedMessage.getContent());
        System.out.println();

        // 메세지 삭제
        System.out.println("메세지 삭제");

        messageService.deleteMessage(sendUserId, findMessage1Id);
        try {
            messageService.findMessageByIdOrThrow(findMessage1Id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
