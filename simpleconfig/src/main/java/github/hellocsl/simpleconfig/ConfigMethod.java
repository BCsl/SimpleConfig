package github.hellocsl.simpleconfig;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chensuilun on 2017/6/12.
 */
public interface ConfigMethod {
    String BASE_GET_PREFIX = "get";
    String BASE_APPLY_PREFIX = "apply";
    String BASE_COMMIT_PREFIX = "commit";

    int TYPE_INT = 0;
    int TYPE_LONG = 1;
    int TYPE_BOOLEAN = 2;
    int TYPE_FLOAT = 3;
    int TYPE_STRING = 4;
    int TYPE_SET = 5;


    Object invoke(Object[] args);

    @IntDef({TYPE_INT, TYPE_LONG, TYPE_BOOLEAN, TYPE_FLOAT, TYPE_STRING, TYPE_SET})
    @Retention(RetentionPolicy.SOURCE)
    @interface SupportType {
    }
}
