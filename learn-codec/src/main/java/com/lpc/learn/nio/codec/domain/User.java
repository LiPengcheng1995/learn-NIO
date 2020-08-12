package com.lpc.learn.nio.codec.domain;

import lombok.Data;
import org.msgpack.annotation.Message;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/8/11
 * @Time: 10:24
 * @Description:
 */
@Message
@Data
public class User {
    String name;
    int age;
    boolean sex;

}
