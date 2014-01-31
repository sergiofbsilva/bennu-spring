package org.fenixedu.bennu.spring.portal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.PACKAGE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringApplication {

    String path();

    String titleKey();

    String descriptionKey();

    String accessGroup() default "anyone";

}
