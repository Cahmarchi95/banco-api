package br.com.banco.persistence.dao;

import br.com.banco.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class UserDao {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void create(User user) {
        this.entityManager.persist(user);
    }

    public User get(Long userId) {
        try {
            TypedQuery<User> query = this.entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
            query.setParameter("id", userId);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
