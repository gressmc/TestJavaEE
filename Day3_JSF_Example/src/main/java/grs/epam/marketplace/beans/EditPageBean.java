package grs.epam.marketplace.beans;

import grs.epam.marketplace.model.Users;
import grs.epam.marketplace.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ManagedBean
@ViewScoped
public class EditPageBean {

    @EJB
    private UserService userService;

    private Users user;

    @PostConstruct
    public void init() {
        user = (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if (user == null) {
            user = new Users();
        }
    }

    public void save() throws IOException {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user") == null) {
            userService.saveUser(user);
        } else {
            userService.merge(user);
        }

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("user", null);

        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.sendRedirect("index.xhtml");
    }

    public Users getUser() {
        return user;
    }
}
