package grs.epam.marketplace.beans;

import grs.epam.marketplace.model.Users;
import grs.epam.marketplace.service.UserService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@ManagedBean
@ViewScoped
public class IndexPageBean {
    @EJB
    private UserService userService;

    public List<Users> getUser(){
        return userService.getAllUsers();
    }

    public void editUser(Users user) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("user",user);

        HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
        response.sendRedirect("edit.xhtml");
    }

    public void removeUser(Users user) {
        userService.removeUser(user);
    }
}
