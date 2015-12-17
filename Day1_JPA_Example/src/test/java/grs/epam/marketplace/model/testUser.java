package grs.epam.marketplace.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

public class testUser {

    EntityManagerFactory managerFactory;
    EntityManager entityManager;

    @Before
    public void init() {
        managerFactory = Persistence.createEntityManagerFactory("marketPlace");
        entityManager = managerFactory.createEntityManager();
    }

    @After
    public void destroy() {
        entityManager.close();
        managerFactory.close();
    }

    /* 1. Получение всех пользователей. */

    @Test
    public void testGetAll() {
        List results = entityManager.createQuery("SELECT u FROM Users u").getResultList();
        int size = results.size();
        System.out.println("Count of users: " + size);

        Assert.assertNotNull(results);
        Assert.assertTrue(results.size() > 0);
    }

    /* 2. Создание нового пользователя. */
    @Test
    public void createUser() {
        Users users = new Users();
        users.setFullName("Roman Radetskiy");
        users.setBillingAddress("Saratov");
        users.setLogin("gress");
        users.setPassword("passwordVeryStrong");
        persistSimplePOJO(users);
    }

    /* 3. Получение пользователя по его логину. */
    @Test
    public void getUserByLogin() {
        List results = entityManager.createQuery("SELECT u FROM Users u WHERE u.login = 'Login'").getResultList();
        int size = results.size();
        Users users = (Users) results.get(0);

        Assert.assertNotNull(results);
        Assert.assertTrue(size > 0);
        Assert.assertEquals(users.getFullName(), "Mark");
    }

    @Test
    public void getUserByLoginToo() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        Root<Users> from = criteriaQuery.from(Users.class);
        Expression myexp = from.get("login").as(Users.class);
        Predicate mypredicate = criteriaBuilder.equal(myexp, "Login");
        criteriaQuery.where(mypredicate);

        CriteriaQuery<Object> select = criteriaQuery.select(from);
        TypedQuery<Object> typedQuery = entityManager.createQuery(select);
        List<Object> results = typedQuery.getResultList();


        int size = results.size();
        Users users = (Users) results.get(0);
        Assert.assertNotNull(results);
        Assert.assertTrue(size > 0);
        Assert.assertEquals(users.getFullName(), "Mark");
    }

    /* 3.1 Получение пользователя по несуществующему логину. */
    @Test
    public void getUserByNotExistLogin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        Root<Users> from = criteriaQuery.from(Users.class);
        Expression myexp = from.get("login").as(Users.class);
        Predicate mypredicate = criteriaBuilder.equal(myexp, "LoginDonUse");
        criteriaQuery.where(mypredicate);

        CriteriaQuery<Object> select = criteriaQuery.select(from);
        TypedQuery<Object> typedQuery = entityManager.createQuery(select);
        List<Object> results = typedQuery.getResultList();


        int size = results.size();
        Users users = (Users) results.get(0);
        Assert.assertNotNull(results);
        Assert.assertTrue(size > 0);
        Assert.assertEquals(users.getFullName(), "Mark");
    }

    /* 4. Изменение данных пользователя. */
    @Test
    public void updateUser() {
        Users users = entityManager.find(Users.class, 1);
        System.out.println(users.getBillingAddress());
        users.setBillingAddress("New Address");
        mergeSimplePOJO(users);
        System.out.println(users.getBillingAddress());
    }


    public void persistSimplePOJO(Object object) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(object);
        tx.commit();
    }

    public void mergeSimplePOJO(Object object) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.merge(object);
        tx.commit();
    }
}