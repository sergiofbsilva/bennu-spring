package org.fenixedu.bennu.spring.portal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.fenixedu.bennu.core.domain.groups.AnyoneGroup;
import org.fenixedu.bennu.core.domain.groups.Group;
import org.fenixedu.bennu.core.util.CoreConfiguration;
import org.fenixedu.bennu.portal.model.Application;
import org.fenixedu.bennu.portal.model.ApplicationRegistry;
import org.fenixedu.bennu.portal.model.Functionality;
import org.fenixedu.bennu.spring.AccessControl;
import org.fenixedu.commons.i18n.LocalizedString;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;

public class PortalHandlerMapping extends RequestMappingHandlerMapping implements MessageSourceAware {

    private static final String[] EMPTY_ARRAY = new String[0];

    private MessageSource messageSource;

    private final Map<Method, Functionality> functionalities = new HashMap<>();
    private final Map<Class<?>, Application> applicationClasses = new HashMap<>();

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        SpringFunctionality annotation = method.getAnnotation(SpringFunctionality.class);

        if (annotation != null) {
            Class<?> appClass = annotation.application();
            Application application = null;
            if (!applicationClasses.containsKey(appClass)) {
                SpringApplication app = appClass.getAnnotation(SpringApplication.class);
                application =
                        new Application(SpringPortalBackend.BACKEND_KEY, appClass.getName(), app.path(), app.accessGroup(),
                                getLocalized(app.titleKey()), getLocalized(app.descriptionKey()));
                applicationClasses.put(appClass, application);
                ApplicationRegistry.registerApplication(application);
            } else {
                application = applicationClasses.get(appClass);
            }

            String pattern = info.getPatternsCondition().getPatterns().iterator().next();
            Functionality functionality =
                    new Functionality(SpringPortalBackend.BACKEND_KEY, pattern, pattern.substring(pattern.lastIndexOf('/')),
                            annotation.accessGroup(), getLocalized(annotation.titleKey()),
                            getLocalized(annotation.descriptionKey()));
            functionalities.put(method, functionality);

            application.addFunctionality(functionality);
        }

        return info;
    }

    @Override
    @Atomic(mode = TxMode.READ)
    protected void initHandlerMethods() {
        super.initHandlerMethods();
    }

    @Override
    protected HandlerMethod createHandlerMethod(Object handler, Method method) {
        HandlerMethod handlerMethod = super.createHandlerMethod(handler, method);
        Functionality functionality = functionalities.get(method);

        Group group = functionality != null ? functionality.getAccessGroup() : extractAccessControl(method);

        return new PortalHandlerMethod(handlerMethod, group, functionality);
    }

    private Group extractAccessControl(Method method) {
        AccessControl accessControl = method.getAnnotation(AccessControl.class);
        if (accessControl == null) {
            accessControl = method.getDeclaringClass().getAnnotation(AccessControl.class);
        }
        return accessControl == null ? AnyoneGroup.getInstance() : Group.parse(accessControl.value());
    }

    private LocalizedString getLocalized(String key) {
        LocalizedString.Builder builder = new LocalizedString.Builder();
        for (Locale locale : CoreConfiguration.supportedLocales()) {
            builder.with(locale, messageSource.getMessage(key, EMPTY_ARRAY, "!!" + key + "!!", locale));
        }
        return builder.build();
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
