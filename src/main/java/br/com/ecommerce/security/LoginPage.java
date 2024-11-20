package br.com.ecommerce.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.ConfigurableNavigationHandler;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
        try {
            // Invalidar a sessão existente (se houver)
            HttpServletRequest request = getRequest();
            if (request.getSession(false) != null) {
                request.getSession().invalidate(); // Invalidar sessão anterior
            }

            // Realizar autenticação
            switch (securityContext.authenticate(
                    request,
                    getResponse(),
                    withParams().credential(new UsernamePasswordCredential(username, new Password(password))))) {

                case SUCCESS:
                    ConfigurableNavigationHandler navigationHandler =
                            (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();

                    // Redirecionar com base no papel do usuário
                    if (securityContext.isCallerInRole("admin")) {
                        navigationHandler.performNavigation("relatorio?faces-redirect=true");
                    } else if (securityContext.isCallerInRole("usuario")) {
                        navigationHandler.performNavigation("produtos?faces-redirect=true");
                    } else {
                        addError("Permissão negada.");
                    }
                    return;

                case SEND_CONTINUE:
                    // Authentication mechanism has sent a redirect, nothing to send to the response now
                    facesContext.responseComplete();
                    return;

                case SEND_FAILURE:
                    addError("Login falhou. Verifique suas credenciais.");
                    return;

                default:
                    addError("Erro inesperado durante o login.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addError("Erro ao realizar login.");
        }
    }

    public String logout() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

            // Invalidar a sessão
            externalContext.invalidateSession();

            // Adicionar mensagem de logout
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout realizado com sucesso!", null));

            // Redirecionar para a página de login
            externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
