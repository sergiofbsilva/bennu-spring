package org.fenixedu.bennu.spring.portal;

import org.fenixedu.bennu.core.domain.groups.Group;
import org.fenixedu.bennu.portal.model.Functionality;
import org.springframework.web.method.HandlerMethod;

public class PortalHandlerMethod extends HandlerMethod {

    private final Group group;
    private final Functionality functionality;

    public PortalHandlerMethod(HandlerMethod method, Group group, Functionality functionality) {
        super(method);
        this.group = group;
        this.functionality = functionality;

        System.out.println("Created handler for: " + method + " with group: " + group + " and functionality: " + functionality);
    }

    public Group getGroup() {
        return group;
    }

    public Functionality getFunctionality() {
        return functionality;
    }

    @Override
    public HandlerMethod createWithResolvedBean() {
        HandlerMethod method = super.createWithResolvedBean();
        return new PortalHandlerMethod(method, group, functionality);
    }

}
