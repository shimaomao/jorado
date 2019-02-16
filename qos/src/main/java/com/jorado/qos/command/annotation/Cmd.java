package com.jorado.qos.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cmd {

    /**
     * Command name
     *
     * @return command name
     */
    String name();

    /**
     * Command description
     *
     * @return command description
     */
    String summary();

    /**
     * Command example
     *
     * @return command example
     */
    String[] example() default {};

    /**
     * Command order in help
     *
     * @return command order in help
     */
    int sort() default 0;
}
