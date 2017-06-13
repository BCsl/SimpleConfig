package github.hellocsl.sample;

import java.util.Set;

/**
 * Created by chensuilun on 2017/6/12.
 */
public interface NoAnnotationConfig {


    int getHelloInt(int def);

    int getHelloInt();

    boolean getHelloBoolean(boolean def);

    boolean getHelloBoolean();

    float getHelloFloat(float def);

    float getHelloFloat();

    long getHelloLong(long def);

    long getHelloLong();

    String getHelloString(String def);

    String getHelloString();

    Set<String> getHelloSet(Set<String> def);

    Set<String> getHelloSet();


    void applyHelloInt(int value);

    void applyHelloBoolean(boolean value);

    void applyHelloFloat(float value);

    void applyHelloLong(long value);

    void applyHelloString(String value);

    void applyHelloSet(Set<String> value);

    boolean commitHelloInt(int value);

    boolean commitHelloBoolean(boolean value);

    boolean commitHelloFloat(float value);

    boolean commitHelloLong(long value);

    boolean commitHelloString(String value);

    boolean commitHelloSet(Set<String> value);
}
