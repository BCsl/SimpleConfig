package github.hellocsl.sample;

import java.util.Set;

import github.hellocsl.simpleconfig.annotation.APPLY;
import github.hellocsl.simpleconfig.annotation.COMMIT;
import github.hellocsl.simpleconfig.annotation.CONFIG;
import github.hellocsl.simpleconfig.annotation.GET;

/**
 * Created by chensuilun on 2017/6/12.
 */
@CONFIG(name = "Simple")
public interface TestConfig {
    String TEST_KEY_INT = "HELLO_INT";
    String TEST_KEY_LONG = "HELLO_LONG";
    String TEST_KEY_FLOAT = "HELLO_FLOAT";
    String TEST_KEY_BOOLEAN = "HELLO_BOOLEAN";
    String TEST_KEY_STRING = "HELLO_STRING";
    String TEST_KEY_SET = "HELLO_SET";


    @GET(key = TEST_KEY_INT)
    int getIndex(int def);

    @GET(key = TEST_KEY_INT)
    int getIndex();

    @GET(key = TEST_KEY_BOOLEAN)
    boolean getBoolean(boolean def);

    @GET(key = TEST_KEY_BOOLEAN)
    boolean getBoolean();

    @GET(key = TEST_KEY_FLOAT)
    float getFloat(float def);

    @GET(key = TEST_KEY_FLOAT)
    float getFloat();

    @GET(key = TEST_KEY_LONG)
    long getLong(long def);

    @GET(key = TEST_KEY_LONG)
    long getLong();

    @GET(key = TEST_KEY_STRING)
    String getString(String def);

    @GET(key = TEST_KEY_STRING)
    String getString();

    @GET(key = TEST_KEY_SET)
    Set<String> getSet(Set<String> def);

    @GET(key = TEST_KEY_SET)
    Set<String> getSet();


    @APPLY(key = TEST_KEY_INT)
    void applyIndex(int value);

    @APPLY(key = TEST_KEY_BOOLEAN)
    void applyBoolean(boolean value);

    @APPLY(key = TEST_KEY_FLOAT)
    void applyFloat(float value);

    @APPLY(key = TEST_KEY_LONG)
    void applyLong(long value);

    @APPLY(key = TEST_KEY_STRING)
    void applyString(String value);

    @APPLY(key = TEST_KEY_SET)
    void applySet(Set<String> value);

    @COMMIT(key = TEST_KEY_INT)
    boolean commitIndex(int value);

    @COMMIT(key = TEST_KEY_BOOLEAN)
    boolean commitBoolean(boolean value);

    @COMMIT(key = TEST_KEY_FLOAT)
    boolean commitFloat(float value);

    @COMMIT(key = TEST_KEY_LONG)
    boolean commitLong(long value);

    @COMMIT(key = TEST_KEY_STRING)
    boolean commitString(String value);

    @COMMIT(key = TEST_KEY_SET)
    boolean commitSet(Set<String> value);
}
