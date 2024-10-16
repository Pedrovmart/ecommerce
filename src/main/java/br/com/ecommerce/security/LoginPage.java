package br.com.exemplo.hotelaria.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.ConfigurableNavigationHandler;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static jakarta.faces.application.FacesMessage.SEVERITY_ERROR;
import static jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@Named
@RequestScoped
public class LoginPage {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext facesContext;

    private String username;
    private String password;

    public void login() {
        switch (securityContext.authenticate(
                getRequest(),
                getResponse(),
                withParams().credential(new UsernamePasswordCredential(username, new Password(password))))) {

            case SUCCESS:
                ConfigurableNavigationHandler navigationHandler =
                        (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
                navigationHandler.performNavigation("home?faces-redirect=true");
                return;

            case SEND_CONTINUE:
                facesContext.responseComplete();
                return;

            case SEND_FAILURE:
                addError("Login failed");
                return;

            default:
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private HttpServletResponse getResponse() {
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) facesContext.getExternalContext().getRequest();
    }

    private void addError(String message) {
        facesContext.addMessage(null, new FacesMessage(SEVERITY_ERROR, message, null));
    }
}
