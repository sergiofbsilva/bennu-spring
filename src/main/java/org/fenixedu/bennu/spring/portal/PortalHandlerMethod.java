package org.fenixedu.bennu.spring.portal;

import org.fenixedu.bennu.core.domain.groups.Group;
import org.fenixedu.bennu.portal.model.Functionality;
import org.springframework.web.method.HandlerMethod;

public class PortalHandlerMethod extends HandlerMethod {

    private final String accessExpression;
    private final Group group;
    private final Functionality functionality;

    public PortalHandlerMethod(HandlerMethod method, String accessExpression, Functionality functionality) {
        super(method);
        this.accessExpression = accessExpression;
        this.group = null;
        this.functionality = functionality;
    }

    public PortalHandlerMethod(HandlerMethod method, Group group, Functionality functionality) {
        super(method);
        this.accessExpression = null;
        this.group = group;
        this.functionality = functionality;
    }

    public Group getGroup() {
        if (group == null && accessExpression != null) {
            return Group.parse(accessExpression);
        }
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
