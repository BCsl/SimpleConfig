package github.hellocsl.simpleconfig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by chensuilun on 2017/6/12.
 */
@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface APPLY {
    String key() default "";
}
