package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
public class UserDaolmp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsersList() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id))
                .filter(user -> user != null)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));
    }

    @Override
    public void addUser(User user) {
        executeInsideTransaction(entityManager -> entityManager.persist(user));
    }

    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Пользователь с id=" + id + " не найден");
        }
        executeInsideTransaction(entityManager -> entityManager.remove(entityManager.find(User.class, id)));
    }

    @Override
    public void editUser(User user) {
        User existingUser = entityManager.find(User.class, user.getId());
        if (existingUser == null) {
            throw new EntityNotFoundException("Пользователь с id=" + user.getId() + " не найден");
        }
        executeInsideTransaction(entityManager -> entityManager.merge(user));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        try {
            action.accept(entityManager);
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
