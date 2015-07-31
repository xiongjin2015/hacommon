
package com.haha.common.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class HaPreference {

    // private final String TAG = "HaPreference";

    /* preference file name for app */
    private static final String PREF_HAHA = "haha";

    /* preference object for app */
    private SharedPreferences sp;

    private static HaPreference instance;

    public static HaPreference getInstance() {
        if (instance == null)
            instance = new HaPreference();
        return instance;
    }

    public void init(Context context) {
        sp = context.getSharedPreferences(PREF_HAHA, Context.MODE_PRIVATE);
    }

    public int getInt(PrefID id) {
        return sp.getInt(id.getKey(), Integer.parseInt(id.getDefaultValue()));
    }

    public long getLong(PrefID id) {
        return sp.getLong(id.getKey(), Long.parseLong(id.getDefaultValue()));
    }

    public float getFloat(PrefID id) {
        return sp.getFloat(id.getKey(), Float.parseFloat(id.getDefaultValue()));
    }

    public String getString(PrefID id) {
        return sp.getString(id.getKey(), id.getDefaultValue());
    }

    public boolean getBoolean(PrefID id) {
        return sp.getBoolean(id.getKey(), Boolean.parseBoolean(id.getDefaultValue()));
    }

    public void putInt(PrefID id, int value) {
        Editor editor = sp.edit();
        editor.putInt(id.getKey(), value);
        editor.commit();
    }

    public void putLong(PrefID id, long value) {
        Editor editor = sp.edit();
        editor.putLong(id.getKey(), value);
        editor.commit();
    }

    public void putFloat(PrefID id, float value) {
        Editor editor = sp.edit();
        editor.putFloat(id.getKey(), value);
        editor.commit();
    }

    public void putString(PrefID id, String value) {
        Editor editor = sp.edit();
        editor.putString(id.getKey(), value);
        editor.commit();
    }

    public void putBoolean(PrefID id, boolean value) {
        Editor editor = sp.edit();
        editor.putBoolean(id.getKey(), value);
        editor.commit();
    }

    public enum PrefID {

        PREF_MAC("pref_mac", "000000000000"),

        PREF_WORK_DIR("pref_work_dir", ""),
        PREF_MEDIA_DIR("pref_media_dir", "");

        private String key;
        private String defaultValue;

        private PrefID(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

}
