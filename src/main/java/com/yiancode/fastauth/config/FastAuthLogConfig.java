package com.yiancode.fastauth.config;

import com.yiancode.fastauth.log.Log;

/**
 * FastAuth 日志配置类
 *
 * @author HeJin
 */
public class FastAuthLogConfig {

    /**
     * 设置日志级别
     *
     * @param level 日志级别
     */
    public static void setLevel(Log.Level level) {
        Log.Config.level = level;
    }

    /**
     * 关闭日志
     */
    public static void disable() {
        Log.Config.enable = false;
    }

    /**
     * 开启日志
     */
    public static void enable() {
        Log.Config.enable = true;
    }
}
