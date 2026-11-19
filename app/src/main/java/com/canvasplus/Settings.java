package com.canvasplus;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 设置项。模块进程用 MODE_WORLD_READABLE 写，LSPosed 会把文件放到 hook 进程
 * 能读的位置；画布进程用 XSharedPreferences 读。
 */
final class Settings {

    static final String PREFS = "canvasplus";
    /** 从菜单缩小过之后，点击小窗口不再自动放大。 */
    static final String NO_CLICK_ZOOM = "no_click_zoom";

    private Settings() {}

    static SharedPreferences prefs(Context context) {
        try {
            return context.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE);
        } catch (SecurityException error) {
            // 没有开启 LSPosed 的 xposedsharedprefs 特性时退回私有存储
            return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        }
    }

    static boolean get(Context context) {
        return prefs(context).getBoolean(NO_CLICK_ZOOM, true);
    }

    static boolean put(Context context, boolean value) {
        return prefs(context).edit().putBoolean(NO_CLICK_ZOOM, value).commit();
    }
}
