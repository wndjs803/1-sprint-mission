package com.sprint.mission;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) {
        JCFUserService jcfUserService = JCFUserService.getInstance();
        JCFChannelService jcfChannelService = JCFChannelService.getInstance();
        JCFMessageService jcfMessageService = JCFMessageService.getInstance();


        // User test
        // user 생성
        User user1 = jcfUserService.createUser("test1", "nickname1", "email1",
                "password1", "profileImageUrl1");
        User user2 = jcfUserService.createUser("test2", "nickname2", "email2",
                "password2", "profileImageUrl2");
        User user3 = jcfUserService.createUser("test3", "nickname3", "email3",
                "password3", "profileImageUrl3");
        User user4 = jcfUserService.createUser("test4", "nickname4", "email4",
                "password4", "profileImageUrl4");

        // user 조회
        User findUser1 = jcfUserService.findUserById(user1.getId());
        UUID findUser1Id = findUser1.getId();
        System.out.println("유저 조회");
        System.out.println(findUser1.getName());
        System.out.println();

        // user 목록 조회
        List<User> userList = jcfUserService.findAllUsers();
        System.out.println("유저 목록 조회");
        userList.forEach(user -> System.out.println(user.getName()));
        System.out.println();

        // user 업데이트
        jcfUserService.updateUser(findUser1Id,"updateTest1", "nickname1", "email1",
                "password1", "profileImageUrl1");
        User updateUser1 = jcfUserService.findUserById(findUser1Id);
        System.out.println("유저 업데이트");
        System.out.println(updateUser1.getName());
        System.out.println();

        // user 삭제
        jcfUserService.deleteUser(findUser1Id);
        try {
            jcfUserService.findUserById(findUser1Id);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println();


        // Channel test
        // 채널 생성
        UUID channelOwnerId = user2.getId();
        Channel channel1 = jcfChannelService.createChannel(channelOwnerId, "channel1");
        Channel channel2 = jcfChannelService.createChannel(channelOwnerId, "channel2");
        System.out.println("채널 생성");
        System.out.println(channel1.getName());
        System.out.println();

        // channel 조회
        Channel findChannel1 = jcfChannelService.findChannelById(channel1.getId());
        UUID findChannel1Id = findChannel1.getId();
        System.out.println("채널 조회");
        System.out.println(findChannel1.getName());
        System.out.println();

        // 채널 목록 조회
        List<Channel> channelList = jcfChannelService.findAllChannels();
        System.out.println("채널 목록 조회");
        channelList.forEach(channel -> System.out.println(channel.getName()));
        System.out.println();

        // 채널 인원 초대
        List<User> channelUsers = new ArrayList<>();
        channelUsers.add(user3);
        channelUsers.add(user4);
        jcfChannelService.inviteUsers(findChannel1Id, channelUsers);
        System.out.println("채널 인원 초대");
        findChannel1.getChannelUserList().forEach(user -> System.out.println(user.getName()));
        System.out.println("채널 인원 수");
        System.out.println(findChannel1.getChannelUserList().size());
        System.out.println();

        // 채널 나가기
        jcfChannelService.leaveUsers(findChannel1Id, channelUsers);
        System.out.println("채널 나가기");
        findChannel1.getChannelUserList().forEach(user -> System.out.println(user.getName()));
        System.out.println("채널 인원 수");
        System.out.println(findChannel1.getChannelUserList().size());
        System.out.println();

        // 채널명 변경
        jcfChannelService.updateChannelName(channelOwnerId, findChannel1Id, "updateChannel1");
        System.out.println("채널명 변경");
        System.out.println(findChannel1.getName());

        // 채널 삭제
        jcfChannelService.deleteChannel(channelOwnerId, findChannel1Id);
        try {
            jcfChannelService.findChannelById(findChannel1Id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Message test
        // 메세지 생성
        UUID sendUserId = user3.getId();
        UUID channelId = channel2.getId();
        Message message1 = jcfMessageService.createMessage(sendUserId, channelId, "hello");
        Message message2 = jcfMessageService.createMessage(sendUserId, channelId, "world");
        System.out.println("메세지 생성");
        System.out.println(message1.getContent());
        System.out.println(message2.getContent());
        System.out.println();

        // 메세지 조회
        Message findMessage1 = jcfMessageService.findMessageById(message1.getId());
        System.out.println("메세지 조회");
        System.out.println(findMessage1.getContent());
        System.out.println();

        // 메세지 수정
        UUID findMessage1Id = findMessage1.getId();
        jcfMessageService.updateMessage(sendUserId, findMessage1Id, "hi");
        System.out.println("메세지 수정");
        System.out.println(findMessage1.getContent());

        // 메세지 삭제
        jcfMessageService.deleteMessage(sendUserId, findMessage1Id);
        System.out.println("메세지 삭제");
        try {
            jcfMessageService.findMessageById(findMessage1Id);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}