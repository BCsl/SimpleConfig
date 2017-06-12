package github.hellocsl.simpleconfig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by chensuilun on 2017/6/12.
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface CONFIG {

    String name() default "SimpleConfig";

    /**
     * @return usually is {@link android.content.Context#MODE_PRIVATE}、{@link android.content.Context#MODE_WORLD_READABLE}、
     * {@link android.content.Context#MODE_WORLD_WRITEABLE}
     */
    int mode() default 0;
}
