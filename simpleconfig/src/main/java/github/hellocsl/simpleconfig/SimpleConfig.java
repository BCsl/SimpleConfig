package github.hellocsl.simpleconfig;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import github.hellocsl.simpleconfig.annotation.CONFIG;

/**
 * Created by chensuilun on 2017/6/12.
 */
public class SimpleConfig {
    private static final int DEFAULT_MODE = Context.MODE_PRIVATE;

    private Context mContext;

    private Config.Factory mConfigFactory;

    private final Map<Method, ServiceMethod> mServiceMethodCache = new ConcurrentHashMap<Method, ServiceMethod>();

    private final Map<Class, Config> mConfigsCache = new ConcurrentHashMap<Class, Config>();

    private SimpleConfig(Context context, Config.Factory factory) {
        mContext = context;
        mConfigFactory = factory;
    }

    public <T> T create(final Class<T> service) {
        Utils.validateServiceInterface(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        ServiceMethod serviceMethod = loadServiceMethod(service, method);
                        return serviceMethod.invoke(args);
                    }
                });
    }

    private ServiceMethod loadServiceMethod(Class service, Method method) {
        ServiceMethod serviceMethod;
        serviceMethod = mServiceMethodCache.get(method);
        if (serviceMethod == null) {
            serviceMethod = new ServiceMethod(loadConfigs(service), method);
            mServiceMethodCache.put(method, serviceMethod);
        }
        return serviceMethod;
    }

    public Config loadConfigs(Class service) {
        Utils.validateServiceInterface(service);
        Config config = mConfigsCache.get(service);
        if (config == null) {
            String name = service.getSimpleName();
            int mode = DEFAULT_MODE;
            CONFIG configAnno = (CONFIG) service.getAnnotation(CONFIG.class);
            if (configAnno != null) {
                name = configAnno.name();
                mode = configAnno.mode();
            }
            config = mConfigFactory.newConfig(mContext, name, mode);
            mConfigsCache.put(service, config);
        }
        return config;
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

    /**
     * Build a new {@link SimpleConfig}.
     */
    public static class Builder {
        private Context mContext;

        private Config.Factory mFactory;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder configFactory(Config.Factory factory) {
            mFactory = factory;
            return this;
        }

        public SimpleConfig build() {
            Config.Factory factory = mFactory;
            if (factory == null) {
                factory = new DefaultConfigFactory();
            }
            return new SimpleConfig(mContext, factory);
        }
    }
}
