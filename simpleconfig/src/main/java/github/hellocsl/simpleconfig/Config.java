package github.hellocsl.simpleconfig;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.Set;

/**
 * Interface for accessing and modifying config data
 * Created by chensuilun on 2017/6/12.
 */
public interface Config {
    /**
     * @return Name of config
     */
    String getName();

    /**
     * @return Operating mode.  Use 0 or {@link Context#MODE_PRIVATE} for the
     * default operation.
     */
    int getMode();

    /**
     * Set a String value in the config
     *
     * @param key   The name of the config to modify.
     * @param value The new value for the config.
     * @param async When async is true it will writes its value out
     *              to persistent storage asynchronously
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    boolean putString(String key, String value, boolean async);


    /**
     * Set a Set<String> value in the config
     *
     * @param key    The name of the config to modify.
     * @param values The new value for the config.
     * @param async  When async is true it will writes its value out
     *               to persistent storage asynchronously
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    boolean putStringSet(String key, @Nullable Set<String> values, boolean async);

    /**
     * Set a Boolean value in the config
     *
     * @param key   The name of the config to modify.
     * @param value The new value for the config.
     * @param async When async is true it will writes its value out
     *              to persistent storage asynchronously
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    boolean putBoolean(String key, boolean value, boolean async);

    /**
     * Set a Float value in the config
     *
     * @param key   The name of the config to modify.
     * @param value The new value for the config.
     * @param async When async is true it will writes its value out
     *              to persistent storage asynchronously
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    boolean putFloat(String key, float value, boolean async);

    /**
     * Set a Long value in the config
     *
     * @param key   The name of the config to modify.
     * @param value The new value for the config.
     * @param async When async is true it will writes its value out
     *              to persistent storage asynchronously
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    boolean putLong(String key, long value, boolean async);

    /**
     * Set a Int value in the config
     *
     * @param key   The name of the config to modify.
     * @param value The new value for the config.
     * @param async When async is true it will writes its value out
     *              to persistent storage asynchronously
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    boolean putInt(String key, int value, boolean async);

    /**
     * Retrieve a String value from the config.
     *
     * @param key      The name of the config to retrieve.
     * @param defValue Value to return if this config does not exist.
     * @return Returns the config value if it exists, or defValue.  Throws
     * ClassCastException if there is a config with this name that is not
     * a String.
     * @throws ClassCastException
     */
    @Nullable
    String getString(String key, @Nullable String defValue);

    /**
     * Retrieve a set of String values from the configs.
     * <p>
     * <p>Note that you <em>must not</em> modify the set instance returned
     * by this call.  The consistency of the stored data is not guaranteed
     * if you do, nor is your ability to modify the instance at all.
     *
     * @param key       The name of the config to retrieve.
     * @param defValues Values to return if this config does not exist.
     * @return Returns the config values if they exist, or defValues.
     * Throws ClassCastException if there is a config with this name
     * that is not a Set.
     * @throws ClassCastException
     */
    @Nullable
    Set<String> getStringSet(String key, @Nullable Set<String> defValues);

    /**
     * Retrieve an int value from the configs.
     *
     * @param key      The name of the config to retrieve.
     * @param defValue Value to return if this config does not exist.
     * @return Returns the config value if it exists, or defValue.  Throws
     * ClassCastException if there is a config with this name that is not
     * an int.
     * @throws ClassCastException
     */
    int getInt(String key, int defValue);

    /**
     * Retrieve a long value from the configs.
     *
     * @param key      The name of the config to retrieve.
     * @param defValue Value to return if this config does not exist.
     * @return Returns the config value if it exists, or defValue.  Throws
     * ClassCastException if there is a config with this name that is not
     * a long.
     * @throws ClassCastException
     */
    long getLong(String key, long defValue);

    /**
     * Retrieve a float value from the configs.
     *
     * @param key      The name of the config to retrieve.
     * @param defValue Value to return if this config does not exist.
     * @return Returns the config value if it exists, or defValue.  Throws
     * ClassCastException if there is a config with this name that is not
     * a float.
     * @throws ClassCastException
     */
    float getFloat(String key, float defValue);

    /**
     * Retrieve a boolean value from the configs.
     *
     * @param key      The name of the config to retrieve.
     * @param defValue Value to return if this config does not exist.
     * @return Returns the config value if it exists, or defValue.  Throws
     * ClassCastException if there is a config with this name that is not
     * a boolean.
     * @throws ClassCastException
     */
    boolean getBoolean(String key, boolean defValue);

    /**
     * @param key
     * @param async
     * @return
     */
    boolean remove(String key, boolean async);

    /**
     * Remove all values from the config
     */
    boolean clear(boolean async);

    /**
     * Create {@link Config} base on config name and mode
     */
    interface Factory {
        /**
         * @param context
         * @param name    Desired config file
         * @param mode    Operating mode
         * @return
         */
        Config newConfig(Context context, String name, int mode);
    }
}
