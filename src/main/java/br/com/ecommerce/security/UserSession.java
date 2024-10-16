package br.com.ecommerce.security;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.ConfigurableNavigationHandler;
import jakarta.faces.context.*;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("userSession")
@SessionScoped
public class UserSession implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    Logger LOGGER;

    @Inject
    private SecurityContext securityContext;

    public boolean isLoggedIn() {
        var principal = securityContext.getCallerPrincipal();
        LOGGER.log(Level.INFO, "CallerPrincipal: " + (principal != null ? principal.getName() : "null"));
        return principal != null && !"anonymous".equals(principal.getName());
    }

    public String getCallerName() {
        return securityContext.getCallerPrincipal() != null ? securityContext.getCallerPrincipal().getName() : null;
    }

    public void login() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler navigationHandler =
                (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
        navigationHandler.performNavigation("login?faces-redirect=true");
    }

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpSession session = request.getSession(false);

        if (session != null) {
            try {
                request.logout();
                session.invalidate();
                externalContext.getFlash().put("message", "Logout successful");
                return "home?faces-redirect=true";
            } catch (ServletException e) {
                externalContext.getFlash().put("error", "Logout failed");
                return "home?faces-redirect=true";
            }
        } else {
            externalContext.getFlash().put("message", "No active session");
            return "home?faces-redirect=true";
        }
    }
}
