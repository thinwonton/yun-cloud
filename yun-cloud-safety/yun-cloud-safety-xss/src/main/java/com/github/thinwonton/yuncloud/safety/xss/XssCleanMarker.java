package com.github.thinwonton.yuncloud.safety.xss;

public class XssCleanMarker {
    private static final ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    public static boolean shouldClean() {
        return Boolean.TRUE.equals(threadLocal.get());
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
