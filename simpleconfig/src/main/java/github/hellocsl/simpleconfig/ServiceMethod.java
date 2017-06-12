package github.hellocsl.simpleconfig;

import android.content.SharedPreferences;

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
    private SharedPreferences mPreferences;

    private ConfigMethod mProxy;

    public ServiceMethod(SharedPreferences preferences, Method method) {
        mPreferences = preferences;
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
    }

    public Object invoke(Object[] args) {
        return mProxy.invoke(args);
    }

    /**
     * Take responsibility for get config from SharePreference
     */
    private class GetMethod implements ConfigMethod {
        private Method mRawMethod;
        private String mKey;
        private GET mGET;
        private Type mReturnType;
        @SupportType
        private int mReturnParamType;

        public GetMethod(Method method, GET get) {
            mRawMethod = method;
            mGET = get;
            mKey = mGET.key();
            Type returnType = mRawMethod.getGenericReturnType();
            mReturnType = returnType;
            mReturnParamType = getMethodType(mReturnType);
        }


        @Override
        public Object invoke(Object[] args) {
            if (args != null && args.length > 1) {
                throw new IllegalArgumentException("GetMethod supports at most 1 argument");
            }
            SharedPreferences preferences = mPreferences;
            boolean hasDefault = args != null && args.length == 1;
            switch (mReturnParamType) {
                case TYPE_INT:
                    return preferences.getInt(mKey, hasDefault ? (Integer) args[0] : -1);
                case TYPE_LONG:
                    return preferences.getLong(mKey, hasDefault ? (Long) args[0] : -1L);
                case TYPE_BOOLEAN:
                    return preferences.getBoolean(mKey, hasDefault ? (Boolean) args[0] : false);
                case TYPE_FLOAT:
                    return preferences.getFloat(mKey, hasDefault ? (Float) args[0] : 0);
                case TYPE_STRING:
                    return preferences.getString(mKey, hasDefault ? (String) args[0] : null);
                case TYPE_SET:
                    return preferences.getStringSet(mKey, hasDefault ? (Set<String>) args[0] : null);
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
        private APPLY mApply;
        private String mKey;
        @SupportType
        private
        int mParamType;
        private boolean mDoReturn;

        public ApplyMethod(Method method, APPLY apply) {
            mRawMethod = method;
            mApply = apply;
            mKey = mApply.key();
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
            SharedPreferences.Editor editor = mPreferences.edit();
            switch (mParamType) {
                case TYPE_INT:
                    editor.putInt(mKey, (Integer) args[0]).apply();
                    break;
                case TYPE_LONG:
                    editor.putLong(mKey, (Long) args[0]).apply();
                    break;
                case TYPE_BOOLEAN:
                    editor.putBoolean(mKey, (Boolean) args[0]).apply();
                    break;
                case TYPE_FLOAT:
                    editor.putFloat(mKey, (Float) args[0]).apply();
                    break;
                case TYPE_STRING:
                    editor.putString(mKey, (String) args[0]).apply();
                    break;
                case TYPE_SET:
                    editor.putStringSet(mKey, (Set<String>) args[0]).apply();
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
        private COMMIT mCommit;
        private String mKey;
        @SupportType
        private
        int mParamType;
        private boolean mDoReturn;

        public ComfitMethod(Method method, COMMIT commit) {
            mRawMethod = method;
            mCommit = commit;
            mKey = mCommit.key();
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
            SharedPreferences.Editor editor = mPreferences.edit();
            Object result;
            switch (mParamType) {
                case TYPE_INT:
                    result = editor.putInt(mKey, (Integer) args[0]).commit();
                    break;
                case TYPE_LONG:
                    result = editor.putLong(mKey, (Long) args[0]).commit();
                    break;
                case TYPE_BOOLEAN:
                    result = editor.putBoolean(mKey, (Boolean) args[0]).commit();
                    break;
                case TYPE_FLOAT:
                    result = editor.putFloat(mKey, (Float) args[0]).commit();
                    break;
                case TYPE_STRING:
                    result = editor.putString(mKey, (String) args[0]).commit();
                    break;
                case TYPE_SET:
                    result = editor.putStringSet(mKey, (Set<String>) args[0]).commit();
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
