package github.hellocsl.simpleconfig.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Set;

import github.hellocsl.simpleconfig.Config;

/**
 * Implement {@link Config} using {@link SharedPreferences}
 * Created by chensuilun on 2017/6/12.
 */
public class PreferenceConfig implements Config {
    private Context mContext;
    private String mName;
    private int mMode;
    private SharedPreferences mPreferences;

    public PreferenceConfig(Context context, String name, int mode) {
        mContext = context;
        mName = name;
        mMode = mode;
        mPreferences = mContext.getSharedPreferences(mName, mMode);
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public int getMode() {
        return mMode;
    }

    @Override
    public boolean putString(String key, String value, boolean async) {
        if (async) {
            mPreferences.edit().putString(key, value).apply();
        } else {
            return mPreferences.edit().putString(key, value).commit();
        }
        return true;
    }

    @Override
    public boolean putStringSet(String key, @Nullable Set<String> values, boolean async) {
        if (async) {
            mPreferences.edit().putStringSet(key, values).apply();
        } else {
            return mPreferences.edit().putStringSet(key, values).commit();
        }
        return true;
    }

    @Override
    public boolean putBoolean(String key, boolean value, boolean async) {
        if (async) {
            mPreferences.edit().putBoolean(key, value).apply();
        } else {
            return mPreferences.edit().putBoolean(key, value).commit();
        }
        return true;
    }

    @Override
    public boolean putFloat(String key, float value, boolean async) {
        if (async) {
            mPreferences.edit().putFloat(key, value).apply();
        } else {
            return mPreferences.edit().putFloat(key, value).commit();
        }
        return true;
    }

    @Override
    public boolean putLong(String key, long value, boolean async) {
        if (async) {
            mPreferences.edit().putLong(key, value).apply();
        } else {
            return mPreferences.edit().putLong(key, value).commit();
        }
        return true;
    }

    @Override
    public boolean putInt(String key, int value, boolean async) {
        if (async) {
            mPreferences.edit().putInt(key, value).apply();
        } else {
            return mPreferences.edit().putInt(key, value).commit();
        }
        return true;
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return mPreferences.getString(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return mPreferences.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    @Override
    public boolean remove(String key, boolean async) {
        if (async) {
            mPreferences.edit().remove(key).apply();
        } else {
            return mPreferences.edit().remove(key).commit();
        }
        return true;
    }

    @Override
    public boolean clear(boolean async) {
        if (async) {
            mPreferences.edit().clear().apply();
        } else {
            return mPreferences.edit().clear().commit();
        }
        return true;
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }
}
