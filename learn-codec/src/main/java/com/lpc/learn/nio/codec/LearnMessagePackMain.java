package com.lpc.learn.nio.codec;

import com.alibaba.fastjson.JSON;
import com.lpc.learn.nio.codec.domain.User;
import com.lpc.learn.nio.codec.service.MineCodec;
import com.lpc.learn.nio.codec.service.impl.UserMsgPakCodecImpl;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/8/11
 * @Time: 10:07
 * @Description:
 */
public class LearnMessagePackMain {
    public static void main(String[] args) throws IOException {
        List<String> srcList = new ArrayList<>();
        srcList.add("lipengcheng");
        srcList.add("李鹏程");
        srcList.add("1234567890");
        MessagePack messagePack = new MessagePack();
        byte[] rawList = messagePack.write(srcList);
        System.out.println("raw.length:" + rawList.length + ",raw:" + rawList);
        List<String> targetList = messagePack.read(rawList, Templates.tList(Templates.TString));
        System.out.println(JSON.toJSONString(targetList));


        User srcUser = new User();
        srcUser.setAge(100);
        srcUser.setName("张三");
        srcUser.setSex(true);
        byte[] rawUser = messagePack.write(srcUser);
        System.out.println("raw.length:" + rawUser.length + ",raw:" + rawUser);
        User targetUser = messagePack.read(rawUser, User.class);
        System.out.println(JSON.toJSONString(targetUser));

        MineCodec codec = new UserMsgPakCodecImpl();
        User targetUser2 = (User) codec.decode(codec.encode(srcUser));
        System.out.println(JSON.toJSONString(targetUser2));

    }
}
