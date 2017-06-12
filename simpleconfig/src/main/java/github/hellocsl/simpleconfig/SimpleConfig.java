package github.hellocsl.simpleconfig;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Set;

import github.hellocsl.simpleconfig.annotation.CONFIG;

/**
 * Created by chensuilun on 2017/6/12.
 */
public class SimpleConfig {
    public static final String DEFAULT_CONFIG = "SimpleConfig";
    private Context mContext;
    private String mConfigName = DEFAULT_CONFIG;
    private int mConfigMode = Context.MODE_PRIVATE;
    private SharedPreferences mSharedPreferences;

    public SimpleConfig(Context context) {
        mContext = context;
    }

    public <T> T create(final Class<T> service) {
        Utils.validateServiceInterface(service);
        // TODO: 2017/6/12 cache
        CONFIG config = service.getAnnotation(CONFIG.class);
        if (config != null) {
            mConfigMode = config.mode();
            mConfigName = config.name();
        }
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        ServiceMethod serviceMethod = loadServiceMethod(method);
                        return serviceMethod.invoke(args);
                    }
                });
    }

    private ServiceMethod loadServiceMethod(Method method) {
        // TODO: 2017/6/12 cache
        return new ServiceMethod(getConfig(), method);
    }


    public String getConfigName() {
        return mConfigName;
    }

    public int getConfigMode() {
        return mConfigMode;
    }

    // TODO: 2017/6/12 移除在 SimpleConfig 记录的 config 配置信息
    public SharedPreferences getConfig() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(mConfigName, mConfigMode);
        }
        return mSharedPreferences;
    }

    /**
     * @param type
     * @return
     */
    public static boolean supportedType(Type type) {
        if (type == int.class || type == Integer.class) {
            return true;
        }
        if (type == long.class || type == Long.class) {
            return true;
        }
        if (type == boolean.class || type == Boolean.class) {
            return true;
        }
        if (type == float.class || type == Float.class) {
            return true;
        }
        if (type == String.class) {
            return true;
        }
        if (type == Set.class) {
            if (Utils.getRawType(type) == String.class) {
                return true;
            }
        }
        return false;
    }
}
