package com.jorado.logger;

import java.lang.annotation.*;

/**
 * 自动装载标记
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AutoLoad {

}
