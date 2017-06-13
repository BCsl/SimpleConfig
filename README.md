# SimpleConfig

Remove boilerplate code for using SharePreference to persist app's config data, Inspire by [retrofit](https://github.com/square/retrofit)

[中文版本戳这里](./README_CN.md)

## Gradle

```
compile 'github.hellocsl:simpleconfig:{lastest-version}'
```

## Usage

### Step1 Define your config interface

[Config Interface](https://github.com/BCsl/SimpleConfig/blob/master/app/src/main/java/github/hellocsl/sample/TestConfig.java)

```java
@CONFIG(name = "Simple")
public interface TestConfig {
    String TEST_KEY_INT = "HELLO_INT";

    @GET(key = TEST_KEY_INT)
    int getIndex(int def);

    @GET(key = TEST_KEY_INT)
    int getIndex();

    @APPLY(key = TEST_KEY_INT)
    void applyIndex(int value);


    @COMMIT(key = TEST_KEY_INT)
    boolean commitIndex(int value);

}
```

Other way to define your [Config Interface](https://github.com/BCsl/SimpleConfig/blob/master/app/src/main/java/github/hellocsl/sample/NoAnnotationConfig.java)

```java
//config name will be NoAnnotationConfig
public interface NoAnnotationConfig {
    //get as prefix，HelloInt will be consider as the key，default value is def，return type is int
    int getHelloInt(int def);

    //get as prefix，HelloInt will be consider as the key，default value is -1，return type is int
    int getHelloInt();

    //apply as prefix，HelloInt will be consider as the key
    void applyHelloInt(int value);

    //commit as prefix，HelloInt will be consider as the key value，return type is boolean which indicated the new values were successfully written to persistent storage.
    boolean commitHelloInt(int value);
}
```

> Since this lib use `SharePreference` to save [key-value] at default, so only `int|Integer`、`long|Long`、`float|Float`、`boolean|Boolean`、`String`、`Set<String>` are support to

### Step2 Use SimpleConfig to parse config interface

Use `SimpleConfig.Builder` to build a `SimpleConfig` object ，and then use `SimpleConfig#create` method to parse Config interface and it will return a dynamic proxy object witch implements the config interface ，now you can use this to update values to config or get values from config

```java
SimpleConfig simpleConfig = new SimpleConfig.Builder(mAppContext).build();
TestConfig testConfig = mSimpleConfig.create(TestConfig.class);
//...
testConfig.applyIndex(111);
int result = testConfig.getIndex(100);
//...
```

## Further settings

### How to define your config interface

Config interface contains two type of method，**Method to get value** 和 **Method to update value**

- Method to get value

  This type of method can be annotate by `@Get` or with a `get` as prefix of the method name.At most one argument allowed by these methods, and it will consider to be the default value ,`SimpleConfig` use the method return type as the type of the value to retrieve

- Method to update value

  This type of method can be annotate by `@APPLY` or `@COMMIT`,with `apply` or `commit` as prefix of the method name is also support.Only one argument allowed by these methods represent a new value for the config. Both `boolean` and `void` are support as the return type，but `boolean` type is meaningless when defines in apply method and alway true is returned

Here Annotations can use

Annotation | Target                 | Function                                                                            | Note
:--------- | :--------------------- | :---------------------------------------------------------------------------------- | :-------------------------------------------------------------------------------------------------------------
@CONFIG    | Config interface       | Specified the name of config                                                        | If not set，Interface name will be consider as name of config
@GET       | Method to get value    | Specified the key be used to retrieve value from config                             | If not set and the method start with "get"，such as "getHelloInt" ，"HelloInt" will be consider as the key
@APPLY     | Method to update value | Specified the key be used to modify the config，it will update config asynchronously | If not set and the method start with "apply"，such as "applyHelloInt" ，"HelloInt" will be consider as the key
@COMMIT    | Method to update value | Specified the key be used to modify the config                                      | If not set and the method start with "commit"，such as "commitHelloInt" ，"HelloInt" will be consider as the key

### Implement your [Config](https://github.com/BCsl/SimpleConfig/blob/master/simpleconfig/src/main/java/github/hellocsl/simpleconfig/Config.java)

At Default ,`SimpleConfig` use `SharePreference` to persist config value, [PreferenceConfig](https://github.com/BCsl/SimpleConfig/blob/master/simpleconfig/src/main/java/github/hellocsl/simpleconfig/impl/PreferenceConfig.java)，but you can implement your persistent systems by specified your `Config.Factory` when build a SimpleConfig

```java
mSimpleConfig = new SimpleConfig.Builder(mAppContext).configFactory(new Config.Factory() {
     @Override
     public Config newConfig(Context context, String name, int mode) {
          // return your persistent systems
         return null;
     }
 }).build();
```

## ProGuard

# License

```
The MIT License (MIT)

Copyright 2017 BCsl

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
