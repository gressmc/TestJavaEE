package grs.epam.marketplace.serviceImpl;

import grs.epam.marketplace.model.Users;
import grs.epam.marketplace.service.UserService;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;

@Stateless
public class UserServiceImpl implements UserService{

    private static final Class USER_CLASS = Users.class;

    @PersistenceContext(unitName = "marketPlace")
    private EntityManager entityManager;

    @Resource(name="myDatabase")
    DataSource myDatabase;

    public Users findUserById(Long id) {
        return (Users) entityManager.find(USER_CLASS, id);
    }

    public Users findUserByLogin(String login){
        return (Users) entityManager.find(USER_CLASS, login);
    }

    public List getAllUsers() {
        System.out.println("****************");
        List results = entityManager.createQuery("SELECT u FROM Users u").getResultList();
        int size = results.size();
        System.out.println("Count of users: " + size);
        System.out.println("****************");
        return results;
    }

    public void removeUser(Users user) {
//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
        entityManager.remove(user);
//        transaction.commit();
    }

    public void saveUser(Users user) {
//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
        entityManager.persist(user);
//        transaction.commit();
    }

    public Users merge(Users user) {
        return entityManager.merge(user);
    }
}
