package com.gloryroad.demo.constant;

public class RedisKey {
    /**
     * 用户登录后Session hash前缀
     */
    public static final String SESSION_NAME_PREFIX = "Session:";
    /**
     * 用户登录后SessionId 前缀
     */
    public static final String SESSION_ID_PREFIX = "SessionId:";

    public static final String SESSION_TOKEN = "tokenId:";

    public static final String SESSION_USER= "gloryroad:";

    /**
     * 用户登录后Session超时时间
     */
    public static final int KEY_SESSION_EXPIRE_SECOND = 86400;
}
