package br.com.ecommerce.security;

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
        switch (
            // Continue the authentication dialog manually by invoking the authenticate()
            // method. The form authentication picks this up, just like a post to j_security does.
                securityContext.authenticate(
                        getRequest(),
                        getResponse(),
                        withParams()
                                .credential(new UsernamePasswordCredential(username, new Password(password))))) {

            case SUCCESS:
                ConfigurableNavigationHandler navigationHandler =
                        (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();

                navigationHandler.performNavigation("produtos?faces-redirect=true");
                return;

            case SEND_CONTINUE:

                // Authentication mechanism has send a redirect, should not
                // send anything to response from Face now.
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
        return (HttpServletResponse) facesContext
                .getExternalContext()
                .getResponse();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) facesContext
                .getExternalContext()
                .getRequest();
    }

    private void addError(String message) {
        facesContext
                .addMessage(
                        null,
                        new FacesMessage(SEVERITY_ERROR, message, null));
    }

}