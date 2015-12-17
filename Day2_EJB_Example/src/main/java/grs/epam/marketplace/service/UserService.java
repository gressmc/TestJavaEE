package grs.epam.marketplace.service;

import grs.epam.marketplace.model.Users;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface UserService {

    Users findUserById(Long id);
    Users findUserByLogin(String login);
    List getAllUsers();
    void removeUser(Users user);
    void saveUser(Users user);
    Users merge(Users user);
}
