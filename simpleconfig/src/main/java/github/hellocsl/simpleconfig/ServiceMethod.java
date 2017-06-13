package github.hellocsl.simpleconfig;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

import github.hellocsl.simpleconfig.annotation.APPLY;
import github.hellocsl.simpleconfig.annotation.COMMIT;
import github.hellocsl.simpleconfig.annotation.GET;

/**
 * Created by chensuilun on 2017/6/12.
 */
class ServiceMethod implements ConfigMethod {
    private static final String TAG = "ServiceMethod";
    private Config mConfig;

    private ConfigMethod mProxy;

    public ServiceMethod(Config config, Method method) {
        mConfig = config;
        parseMethod(method);
    }

    private void parseMethod(Method method) {
        GET get = method.getAnnotation(GET.class);
        if (get != null) {
            mProxy = new GetMethod(method, get);
            return;
        }
        APPLY apply = method.getAnnotation(APPLY.class);
        if (apply != null) {
            mProxy = new ApplyMethod(method, apply);
            return;
        }
        COMMIT commit = method.getAnnotation(COMMIT.class);
        if (commit != null) {
            mProxy = new ComfitMethod(method, commit);
            return;
        }
        // Libs also support the method which with get/apply/commit prefix
        String methodName = method.getName();
        String key = null;
        if (!TextUtils.isEmpty(methodName)) {
            if (methodName.startsWith(BASE_GET_PREFIX)) {
                key = methodName.substring(BASE_GET_PREFIX.length());
                if (TextUtils.isEmpty(key)) {
                    methodNameError(methodName);
                }
                mProxy = new GetMethod(method, key);
            } else if (methodName.startsWith(BASE_APPLY_PREFIX)) {
                key = methodName.substring(BASE_APPLY_PREFIX.length());
                if (TextUtils.isEmpty(key)) {
                    methodNameError(methodName);
                }
                mProxy = new ApplyMethod(method, key);
            } else if (methodName.startsWith(BASE_COMMIT_PREFIX)) {
                key = methodName.substring(BASE_COMMIT_PREFIX.length());
                if (TextUtils.isEmpty(key)) {
                    methodNameError(methodName);
                }
                mProxy = new ComfitMethod(method, key);
            }
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "parseMethod without annotation: methodName:" + methodName + ",key:" + key);
            }
        }
    }

    private void methodNameError(String methodName) {
        throw new IllegalStateException("Service Method without both annotation and valid name,method name:" + methodName);
    }


    public Object invoke(Object[] args) {
        if (mProxy != null) {
            return mProxy.invoke(args);
        } else {
            return null;
        }
    }

    /**
     * Take responsibility for get config from SharePreference
     */
    private class GetMethod implements ConfigMethod {
        private Method mRawMethod;
        private String mKey;
        private Type mReturnType;
        @SupportType
        private int mReturnParamType;

        public GetMethod(Method method, GET get) {
            this(method, get.key());
        }

        public GetMethod(Method method, String key) {
            mRawMethod = method;
            mKey = key;
            Type returnType = mRawMethod.getGenericReturnType();
            mReturnType = returnType;
            mReturnParamType = getMethodType(mReturnType);
        }


        @Override
        public Object invoke(Object[] args) {
            if (args != null && args.length > 1) {
                throw new IllegalArgumentException("GetMethod supports at most 1 argument");
            }
            boolean hasDefault = args != null && args.length == 1;
            switch (mReturnParamType) {
                case TYPE_INT:
                    return mConfig.getInt(mKey, hasDefault ? (Integer) args[0] : -1);
                case TYPE_LONG:
                    return mConfig.getLong(mKey, hasDefault ? (Long) args[0] : -1L);
                case TYPE_BOOLEAN:
                    return mConfig.getBoolean(mKey, hasDefault ? (Boolean) args[0] : false);
                case TYPE_FLOAT:
                    return mConfig.getFloat(mKey, hasDefault ? (Float) args[0] : 0);
                case TYPE_STRING:
                    return mConfig.getString(mKey, hasDefault ? (String) args[0] : null);
                case TYPE_SET:
                    return mConfig.getStringSet(mKey, hasDefault ? (Set<String>) args[0] : null);
                default:
                    return new IllegalStateException("UnKnow SupportType of:" + mReturnType);
            }
        }
    }

    /**
     * Take responsibility for  apply change to SharePreference
     */
    private class ApplyMethod implements ConfigMethod {
        private final Method mRawMethod;
        private String mKey;
        @SupportType
        private
        int mParamType;
        private boolean mDoReturn;

        public ApplyMethod(Method method, APPLY apply) {
            this(method, apply.key());
        }

        public ApplyMethod(Method method, String key) {
            mRawMethod = method;
            mKey = key;
            Type[] parameterTypes = mRawMethod.getGenericParameterTypes();
            if (parameterTypes.length != 1) {
                throw new IllegalArgumentException("ApplyMethod must only has 1 argument");
            }
            mParamType = getMethodType(parameterTypes[0]);
            Type returnType = mRawMethod.getGenericReturnType();
            if (returnType != void.class && returnType != Void.class) {
                if (returnType != boolean.class && returnType != Boolean.class) {
                    throw new IllegalArgumentException("ApplyMethod only support return type of Void or Boolean");
                } else {
                    mDoReturn = true;
                }
            } else {
                mDoReturn = false;
            }
        }

        @Override
        public Object invoke(Object[] args) {
            if (args == null || args.length != 1) {
                throw new IllegalArgumentException("ApplyMethod must only has 1 argument");
            }
            switch (mParamType) {
                case TYPE_INT:
                    mConfig.putInt(mKey, (Integer) args[0], true);
                    break;
                case TYPE_LONG:
                    mConfig.putLong(mKey, (Long) args[0], true);
                    break;
                case TYPE_BOOLEAN:
                    mConfig.putBoolean(mKey, (Boolean) args[0], true);
                    break;
                case TYPE_FLOAT:
                    mConfig.putFloat(mKey, (Float) args[0], true);
                    break;
                case TYPE_STRING:
                    mConfig.putString(mKey, (String) args[0], true);
                    break;
                case TYPE_SET:
                    mConfig.putStringSet(mKey, (Set<String>) args[0], true);
                    break;
                default:
                    return new IllegalStateException("UnKnow SupportType of:" + mParamType);
            }
            return mDoReturn ? true : null;
        }
    }

    /**
     * Take responsibility for commit change to SharePreference
     */
    private class ComfitMethod implements ConfigMethod {
        private final Method mRawMethod;
        private String mKey;
        @SupportType
        private
        int mParamType;
        private boolean mDoReturn;

        public ComfitMethod(Method method, COMMIT commit) {
            this(method, commit.key());
        }

        public ComfitMethod(Method method, String key) {
            mRawMethod = method;
            mKey = key;
            Type[] parameterTypes = mRawMethod.getGenericParameterTypes();
            if (parameterTypes.length != 1) {
                throw new IllegalArgumentException("CommitMethod must only has 1 argument");
            }
            mParamType = getMethodType(parameterTypes[0]);
            Type returnType = mRawMethod.getGenericReturnType();
            if (returnType != void.class && returnType != Void.class) {
                if (returnType != boolean.class && returnType != Boolean.class) {
                    throw new IllegalArgumentException("CommitMethod only support return type of void or boolean");
                } else {
                    mDoReturn = true;
                }
            } else {
                mDoReturn = false;
            }
        }

        @Override
        public Object invoke(Object[] args) {
            if (args == null || args.length != 1) {
                throw new IllegalArgumentException("CommitMethod must only has 1 argument");
            }
            Object result;
            switch (mParamType) {
                case TYPE_INT:
                    result = mConfig.putInt(mKey, (Integer) args[0], false);
                    break;
                case TYPE_LONG:
                    result = mConfig.putLong(mKey, (Long) args[0], false);
                    break;
                case TYPE_BOOLEAN:
                    result = mConfig.putBoolean(mKey, (Boolean) args[0], false);
                    break;
                case TYPE_FLOAT:
                    result = mConfig.putFloat(mKey, (Float) args[0], false);
                    break;
                case TYPE_STRING:
                    result = mConfig.putString(mKey, (String) args[0], false);
                    break;
                case TYPE_SET:
                    result = mConfig.putStringSet(mKey, (Set<String>) args[0], false);
                    break;
                default:
                    return new IllegalStateException("UnKnow SupportType of:" + mParamType);
            }
            return mDoReturn ? result : null;
        }

    }

    public
    @SupportType
    int getMethodType(Type type) {
        if (type == int.class || type == Integer.class) {
            return TYPE_INT;
        }
        if (type == long.class || type == Long.class) {
            return TYPE_LONG;
        }
        if (type == boolean.class || type == Boolean.class) {
            return TYPE_BOOLEAN;
        }
        if (type == float.class || type == Float.class) {
            return TYPE_FLOAT;
        }
        if (type == String.class) {
            return TYPE_STRING;
        }
        if (type instanceof ParameterizedType) {
            if (Utils.getRawType(type) == Set.class && Utils.getParameterUpperBound(0, (ParameterizedType) type) == String.class) {
                return TYPE_SET;
            }
        }
        throw new IllegalStateException("Service Method is not support type of :" + Utils.typeToString(type));
    }


}
