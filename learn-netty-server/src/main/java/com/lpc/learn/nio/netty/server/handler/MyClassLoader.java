package com.lpc.learn.nio.netty.server.handler;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2021/3/1
 * @Time: 21:08
 * @Description:
 */
public class MyClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
}
