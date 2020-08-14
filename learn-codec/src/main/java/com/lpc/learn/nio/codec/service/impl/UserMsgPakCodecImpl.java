package com.lpc.learn.nio.codec.service.impl;

import com.lpc.learn.nio.codec.domain.User;
import com.lpc.learn.nio.codec.service.MineCodec;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;

import java.io.IOException;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/8/14
 * @Time: 17:18
 * @Description:
 */
@Slf4j
public class UserMsgPakCodecImpl implements MineCodec {
    private static MessagePack messagePack = new MessagePack();

    public UserMsgPakCodecImpl() {
    }

    @Override
    public byte[] encode(Object data) {
        try {
            return messagePack.write(data);
        } catch (IOException e) {
            log.error("编码失败，", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object decode(byte[] data) {
        try {
            return messagePack.read(data, User.class);
        } catch (IOException e) {
            log.error("解码失败，", e);
            throw new RuntimeException(e);
        }
    }

}
