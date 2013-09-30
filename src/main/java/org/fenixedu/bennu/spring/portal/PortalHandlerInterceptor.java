package org.fenixedu.bennu.spring.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fenixedu.bennu.core.domain.groups.Group;
import org.fenixedu.bennu.core.security.Authenticate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PortalHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Group group = ((PortalHandlerMethod) handler).getGroup();
        if (group != null && !group.isMember(Authenticate.getUser())) {
            request.getRequestDispatcher("/bennu-spring/unauthorized.jsp").forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        if (modelAndView != null && modelAndView.getViewName() != null) {
            if (modelAndView.getViewName().startsWith("noPortal:")) {
                modelAndView.setViewName("/WEB-INF/" + modelAndView.getViewName().substring(9) + ".jsp");
            } else {
                modelAndView.addObject("bennu$targetView", "/WEB-INF/" + modelAndView.getViewName() + ".jsp");
                modelAndView.setViewName("/bennu-spring/spring.jsp");
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
