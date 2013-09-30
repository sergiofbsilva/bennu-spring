package org.fenixedu.bennu.spring.portal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Controller;

@Controller
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringFunctionality {

    Class<?> application();

    String titleKey();

    String descriptionKey();

    String accessGroup() default "anyone";

}
