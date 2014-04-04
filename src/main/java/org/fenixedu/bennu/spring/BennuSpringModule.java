package org.fenixedu.bennu.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/***
 * Indicates that an annotated class is a configuration Bennu Spring Module.
 * Specifies basePackages for {@link ComponentScan} and baseNames for {@link ReloadableResourceBundleMessageSource}
 * 
 * @author Sérgio Silva (sergio.silva@tecnico.ulisboa.pt)
 * 
 */
@ComponentScan
@Configuration
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BennuSpringModule {
    /**
     * The base packages within this module for component scanning.
     * Not yet supported due to Spring issue @see <a href="https://jira.spring.io/browse/SPR-11557">SPR-11557</a>
     * 
     * @return packages configuration for @{link @ComponentScan} annotation
     */
    String[] basePackages() default {};

    /**
     * The resource bundles relative to "/WEB-INF/resources/" in this module.
     * The bundle definition must not include file extensions.
     * 
     * Example
     * 
     * <pre class="code">
     * &#064;BennuSpringResource(bundles="MyModuleResources")
     * </pre>
     * 
     * It will search for the following files:
     * <ul>
     * <li>/WEB-INF/resources/MyModuleResources.properties</li>
     * <li>/WEB-INF/resources/MyModuleResources_&lt;locale&gt;.properties</li>
     * </ul>
     * 
     * @return bundle names which will be available as messageSource providers.
     * @see BennuSpringConfiguration#messageSource(org.springframework.context.ApplicationContext)
     */
    String[] bundles() default {};
}
