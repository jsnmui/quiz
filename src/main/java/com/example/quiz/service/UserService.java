package com.example.quiz.service;


import com.example.quiz.dao.UserDao;
import com.example.quiz.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> validateLogin(String email, String password) {
        return userDao.findByEmailAndPassword(email, password);
    }

    public void createNewUser(User user) {
        userDao.createNewUser(user);
    }


    public List<User> getUsersByPage(int page, int size) {
        int offset = (page - 1) * size;
        return userDao.getUsersWithPagination(offset, size);
    }

    public int countAllUsers() {
        return userDao.getTotalUserCount();
    }
    public int getTotalPages(int pageSize) {
        int count = userDao.getTotalUserCount();
        return (int) Math.ceil((double) count / pageSize);
    }

    public void setUserStatus(int userId, boolean isActive) {
        userDao.updateUserStatus(userId, isActive);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers(); // delegate to DAO
    }
}
