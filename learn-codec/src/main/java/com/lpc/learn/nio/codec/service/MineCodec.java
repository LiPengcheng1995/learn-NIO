package com.lpc.learn.nio.codec.service;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/8/14
 * @Time: 17:15
 * @Description:
 */
public interface MineCodec {
    byte[] encode(Object data);

    Object decode(byte[] data);
}
