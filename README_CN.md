# SimpleConfig

[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat-square)](https://android-arsenal.com/api?level=14)

这是一个基于 `SharePreference` 简化应用配置信息读取和保存的库, 灵感来自于 [retrofit](https://github.com/square/retrofit)

## Gradle

```
compile 'github.hellocsl:simpleconfig:{lastest-version}'
```

## 使用

### **Step1** 定义配置接口

[定义配置接口（推荐）](https://github.com/BCsl/SimpleConfig/blob/master/app/src/main/java/github/hellocsl/sample/TestConfig.java)

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

还可以这样定义[配置接口（需要注意方法混淆！不推荐使用）](https://github.com/BCsl/SimpleConfig/blob/master/app/src/main/java/github/hellocsl/sample/NoAnnotationConfig.java)

```java
//配置名为接口名 NoAnnotationConfig
public interface NoAnnotationConfig {
    //以 get 开头，Key 为 HelloInt，并指定默认值 def，返回类型为 int
    int getHelloInt(int def);

    //以 get 开头，Key 为 HelloInt，并制定默认值 -1，返回类型为 int
    int getHelloInt();

    //以 apply 开头，Key 为 HelloInt，更新值为 value，可接受 boolean 类型的返回值，但总会返回 true
    void applyHelloInt(int value);

    //以 commit 开头，Key 为 HelloInt，更新值为 value，可接受 boolean 类型的返回值，指定是否已经成功写入到配置文件
    boolean commitHelloInt(int value);
}
```

> 因为是基于 `SharePreference` 所以仅支持 `int|Integer`、`long|Long`、`float|Float`、`boolean|Boolean`、`String`、`Set<String>` 这几种类型

### **Step2** 使用 SimpleConfig 对象来解析配置接口

使用 `SimpleConfig.Builder` 来构建一个 `SimpleConfig` 对象，然后调用 `SimpleConfig#create` 方法来对配置接口进行解析并返回一个实现配置接口的动态代理对象，然后就可以直接用该接口来实现配置的读取和更新

```java
SimpleConfig simpleConfig = new SimpleConfig.Builder(mAppContext).build();
TestConfig testConfig = mSimpleConfig.create(TestConfig.class);
//...
testConfig.applyIndex(111);
int result = testConfig.getIndex(100);
//...
```

## 详细配置

### 配置接口的构建

配置接口内可以定义两种方法，**获取配置的方法** 和 **更新配置的方法**

- 获取配置的方法，可以配置 `@GET` 注解或者不使用注解而以 `get` 为前缀，这类方法可以接收**最多一个参数**，用于指定默认值，**返回值类型** 用来确定配置值具体类型，时刻注意返回值类型和参数类型的一致性

- 更新配置的方法，可以配置 `@APPLY` 注解或者 `@COMMIT` 注解，对应的不使用注解的时候还可以以 `apply` 或 `commit` 为前缀，这类方法仅能接收**一个参数**，用于指定要更新的值，返回类型可以为 `boolean` 或 `void`，如果使用的是 `apply` 方式，定义返回值 `boolean` 并没意义，总返回 `true`

配置接口可使用的注解类型

注解      | 作用域      | 作用                        | 注意点
:------ | :------- | :------------------------ | :------------------------------------------------------------------------
@CONFIG | 配置接口     | 可以定义配置名，默认为配置名为接口名        | 可不使用
@GET    | 获取配置值的方法 | 定义配置 `KEY`，获取该 `KEY` 对应的值 | 如果不使用，获取配置信息的方法必须以 `get` 开头，例如方法名为 `getHelloInt`，则 `KEY` 为 `HelloInt`
@APPLY  | 更新配置的方法  | 定义配置 `KEY`，将以异步的方式更新配置    | 如果不使用，更新配置信息的方法可以以 `apply` 开头，例如方法名为 `applyHelloInt`，则 `KEY` 为 `HelloInt`
@COMMIT | 更新配置的方法  | 定义配置 `KEY`，将以同步的方式更新配置    | 如果不使用，更新配置信息的方法可以以 `commit` 例如方法名为 `commitHelloInt`，则 `KEY` 为 `HelloInt`

### 配置自己的配置文件读写类

`SimpleConfig` 默认提供了使用 `SharePreference` 实现的配置读写类 [PreferenceConfig](https://github.com/BCsl/SimpleConfig/blob/master/simpleconfig/src/main/java/github/hellocsl/simpleconfig/impl/PreferenceConfig.java)，如果有需要替换成自己的实现类，那么可以在构建 `SimpleConfig` 的时候配置 `Config.Factory`，并返回你的 `Config` 实现类

```java
mSimpleConfig = new SimpleConfig.Builder(mAppContext).configFactory(new Config.Factory() {
     @Override
     public Config newConfig(Context context, String name, int mode) {
          // return your Config
         return null;
     }
 }).build();
```

## DEMO

可以直接看单元测试例子，[TestConfigTest](https://github.com/BCsl/SimpleConfig/blob/master/app/src/androidTest/java/github/hellocsl/sample/TestConfigTest.java) 和 [NoAnnotationConfigTest](https://github.com/BCsl/SimpleConfig/blob/master/app/src/androidTest/java/github/hellocsl/sample/NoAnnotationConfigTest.java)

## 混淆

配置接口推荐使用注解的方式来定义，否则通过方法名的前缀 `get` 等来判断，在混淆后一般会失效，请注意

# 开源协议

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
