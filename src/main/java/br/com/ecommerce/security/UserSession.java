package br.com.ecommerce.security;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.ConfigurableNavigationHandler;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
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
    private SecurityContext securityContext;

    public boolean isLoggedIn() {
        if (securityContext != null) {
            var principal = securityContext.getCallerPrincipal();

            if (principal != null && "anonymous".equals(principal.getName())) {

                return false;
            }
            return principal != null;
        }

        return false;
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
        HttpSession session = request.getSession(false); // Don't create a new session if it doesn't exist

        if (session != null) {
            try {
                request.logout(); // Log out the user
                session.invalidate(); // Invalidate the session
                externalContext.getFlash().put("message", "Logout successful");
                return "home?faces-redirect=true";
            } catch (ServletException e) {
                // Handle any exception that may occur during logout
                externalContext.getFlash().put("error", "Logout failed");
                return "home?faces-redirect=true";
            }
        } else {
            externalContext.getFlash().put("message", "No active session");
            return "home?faces-redirect=true";
        }
    }

    public boolean isAdmin() {
        return securityContext.isCallerInRole("admin");
    }

    public boolean isUser() {
        return securityContext.isCallerInRole("usuario");
    }

    public String getUsername() {
        return isLoggedIn() ? securityContext.getCallerPrincipal().getName() : null;
    }
}