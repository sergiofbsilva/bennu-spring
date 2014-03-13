package org.fenixedu.bennu.spring.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fenixedu.bennu.portal.domain.MenuFunctionality;
import org.fenixedu.bennu.portal.model.Functionality;
import org.fenixedu.bennu.portal.servlet.BennuPortalDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PortalHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PortalHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PortalHandlerMethod handlerMethod = (PortalHandlerMethod) handler;
        MenuFunctionality functionality = chooseSelectedFunctionality(request, handlerMethod);
        if (functionality != null && !functionality.isAvailableForCurrentUser()) {
            request.getRequestDispatcher("/bennu-spring/unauthorized.jsp").forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    private static MenuFunctionality chooseSelectedFunctionality(HttpServletRequest request, PortalHandlerMethod handler) {
        MenuFunctionality current = (MenuFunctionality) request.getAttribute(BennuPortalDispatcher.SELECTED_FUNCTIONALITY);
        if (current == null) {
            Functionality model = handler.getFunctionality();
            if (model == null) {
                logger.warn("Could not map {} to a functionality!", handler.getMethod());
                return null;
            }
            MenuFunctionality functionality =
                    MenuFunctionality.findFunctionality(SpringPortalBackend.BACKEND_KEY, model.getKey());
            if (functionality == null) {
                logger.warn("Trying to access a not installed functionality!");
                return null;
            }
            request.setAttribute(BennuPortalDispatcher.SELECTED_FUNCTIONALITY, functionality);
            return functionality;
        } else {
            return current;
        }
    }

}
