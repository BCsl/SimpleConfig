package github.hellocsl.simpleconfig;

import android.support.annotation.IntDef;

/**
 * Created by chensuilun on 2017/6/12.
 */
public interface ConfigMethod {

    int TYPE_INT = 0;
    int TYPE_LONG = 1;
    int TYPE_BOOLEAN = 2;
    int TYPE_FLOAT = 3;
    int TYPE_STRING = 4;
    int TYPE_SET = 5;


    Object invoke(Object[] args);

    @IntDef({TYPE_INT, TYPE_LONG, TYPE_BOOLEAN, TYPE_FLOAT, TYPE_STRING, TYPE_SET})
    @interface SupportType {
    }
}
