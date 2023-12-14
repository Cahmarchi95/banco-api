package br.com.banco.persistence.dao;

import br.com.banco.persistence.model.Account;
import br.com.banco.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AccountDao {
    @Inject
    EntityManager entityManager;

    @Transactional
    public void create(Account account) {
        this.entityManager.persist(account);
    }

    @Transactional
    public void save(Account account) {
        this.entityManager.persist(account);
    }

    public List<Account> listarContas() {
        return entityManager.createQuery("SELECT a FROM Account a", Account.class).getResultList();
    }



    @Transactional
    public Optional<Account> findByNumeroConta(String numeroConta) {
        TypedQuery<Account> query = entityManager.createQuery(
                "SELECT a FROM Account a WHERE a.numeroConta = :numeroConta", Account.class);
        query.setParameter("numeroConta", numeroConta);
        return query.getResultStream().findFirst();
    }

    @Transactional
    public Optional<Account> findByUser(User user) {
        TypedQuery<Account> query = entityManager.createQuery(
                "SELECT a FROM Account a WHERE a.user = :user", Account.class);
        query.setParameter("user", user);
        return query.getResultStream().findFirst();
    }
}


