package org.fenixedu.bennu.spring;

import org.fenixedu.bennu.core.util.CoreConfiguration;
import org.fenixedu.bennu.core.util.CoreConfiguration.ConfigurationProperties;
import org.fenixedu.bennu.spring.portal.PortalHandlerMapping;
import org.fenixedu.bennu.spring.portal.PortalHandlerInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan("org.fenixedu.bennu")
public class BennuSpringConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public ConfigurationProperties bennuCoreConfiguration() {
        return CoreConfiguration.getConfiguration();
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PortalHandlerInterceptor());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setDefaultEncoding("UTF-8");
        source.setBasename("/WEB-INF/resources/messages");
        // Reload resources only when in development mode
        source.setCacheSeconds(bennuCoreConfiguration().developmentMode() ? 1 : -1);
        return source;
    }

    @Bean(name = "i18n")
    public I18NBean i18n() {
        return new I18NBean(getMessageSource());
    }

    @Override
    public PortalHandlerMapping requestMappingHandlerMapping() {
        PortalHandlerMapping handlerMapping = new PortalHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager());
        return handlerMapping;
    }

}
