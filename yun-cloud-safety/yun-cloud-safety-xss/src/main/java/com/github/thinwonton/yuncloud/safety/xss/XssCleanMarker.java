package com.github.thinwonton.yuncloud.safety.xss;

public class XssCleanMarker {
    private static final ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    public static boolean shouldClean() {
        //thread local的值为空也判断为true
        return !Boolean.FALSE.equals(threadLocal.get());
    }

    public static void enabled() {
        threadLocal.set(Boolean.TRUE);
    }

    public static void disabled() {
        threadLocal.set(Boolean.FALSE);
    }

    public static void clean() {
        threadLocal.remove();
    }
}
