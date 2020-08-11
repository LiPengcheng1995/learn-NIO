package com.lpc.learn.nio.codec;

import com.alibaba.fastjson.JSON;
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
        List<String> src = new ArrayList<>();
        src.add("lipengcheng");
        src.add("李鹏程");
        src.add("1234567890");
        MessagePack messagePack = new MessagePack();
        byte[] raw = messagePack.write(src);
        System.out.println("raw.length:"+raw.length+",raw:"+raw);
        List<String> target =  messagePack.read(raw, Templates.tList(Templates.TString));

        System.out.println(JSON.toJSONString(target));
    }
}
