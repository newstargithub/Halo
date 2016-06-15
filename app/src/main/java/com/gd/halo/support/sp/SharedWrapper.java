package com.gd.halo.support.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.gd.halo.support.json.GsonWrapper;

import java.lang.reflect.Type;

/**
 * SharedPreferences 封装
 */
public final class SharedWrapper {

    public static SharedWrapper with(Context context, String name) {
        return new SharedWrapper(context, name);
    }

    private final Context context;
    private final SharedPreferences sp;

    private SharedWrapper(Context context, String name) {
        this.context = context.getApplicationContext();
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private String get(String key, String defValue) {
        try {
            String value = sp.getString(key, "");
            if (TextUtils.isEmpty(value)) {
                return defValue;
            } else {
                return value;
            }
        } catch (Exception e) {
            return defValue;
        }
    }

    private void set(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        try {
            editor.putString(key, value);
        } catch (Exception e) {
            editor.putString(key, "");
        }
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return get(key, defValue);
    }

    public void setString(String key, String value) {
        set(key, value);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return Boolean.parseBoolean(get(key, Boolean.toString(defValue)));
    }

    public void setBoolean(String key, boolean value) {
        set(key, Boolean.toString(value));
    }

    public float getFloat(String key, float defValue) {
        return Float.parseFloat(get(key, Float.toString(defValue)));
    }

    public void setFloat(String key, float value) {
        set(key, Float.toString(value));
    }

    public int getInt(String key, int defValue) {
        return Integer.parseInt(get(key, Integer.toString(defValue)));
    }

    public void setInt(String key, int value) {
        set(key, Integer.toString(value));
    }

    public long getLong(String key, long defValue) {
        return Long.parseLong(get(key, Long.toString(defValue)));
    }

    public void setLong(String key, long value) {
        set(key, Long.toString(value));
    }

    public <T>T getObject(String key, Class<T> clz) {
        String json = get(key, null);
        if (json == null) {
            return null;
        } else {
            try {
                return GsonWrapper.gson.fromJson(json, clz);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public <T>T getObject(String key, Type typeOfT) {
        String json = get(key, null);
        if (json == null) {
            return null;
        } else {
            try {
                return GsonWrapper.gson.fromJson(json, typeOfT);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public void setObject(String key, Object value) {
        if (value == null) {
            set(key, "");
        } else {
            String json = GsonWrapper.gson.toJson(value);
            set(key, json);
        }
    }

    public void clear() {
        sp.edit().clear().apply();
    }

}
