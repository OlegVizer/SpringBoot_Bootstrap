package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUserById(int id) {
        userDao.deleteUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> user = userDao.findByUsername(username);
        return  user.get(0);
    }
}
