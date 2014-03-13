package org.fenixedu.bennu.spring.portal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Controller;

@Controller
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringFunctionality {

    Class<?> app();

    String title();

    String description() default PortalHandlerMapping.DELEGATE;

    String accessGroup() default PortalHandlerMapping.DELEGATE;

}
