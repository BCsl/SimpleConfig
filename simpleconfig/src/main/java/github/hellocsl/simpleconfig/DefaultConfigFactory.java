package github.hellocsl.simpleconfig;

import android.content.Context;

import github.hellocsl.simpleconfig.impl.PreferenceConfig;

/**
 * Created by chensuilun on 2017/6/12.
 */
public class DefaultConfigFactory implements Config.Factory {

    @Override
    public Config newConfig(Context context, String name, int mode) {
        return new PreferenceConfig(context, name, mode);
    }
}
