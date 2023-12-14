package br.com.banco.services;

import br.com.banco.persistence.dao.UserDao;
import br.com.banco.persistence.dto.UserDto;
import br.com.banco.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class UserService {

    @Inject
    UserDao userDao;

    public void createUser(UserDto userDto) {
        User user = new User();
        user.setNome(userDto.getNome());
        user.setIdade(userDto.getIdade());
        user.setTelefone(userDto.getTelefone());
        user.setEndereco(userDto.getEndereco());
        userDao.create(user);
    }

    public User findUserById(Long userId) {
        return userDao.get(userId);
    }


}
