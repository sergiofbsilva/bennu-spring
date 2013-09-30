package org.fenixedu.bennu.spring.portal;

import org.fenixedu.bennu.portal.servlet.PortalBackend;
import org.fenixedu.bennu.portal.servlet.SemanticURLHandler;

public class SpringPortalBackend implements PortalBackend {

    public static final String BACKEND_KEY = "bennu-spring";

    private final SpringSemanticURLHandler handler = new SpringSemanticURLHandler();

    @Override
    public SemanticURLHandler getSemanticURLHandler() {
        return handler;
    }

    @Override
    public boolean requiresServerSideLayout() {
        return true;
    }

    @Override
    public String getBackendKey() {
        return BACKEND_KEY;
    }

}
