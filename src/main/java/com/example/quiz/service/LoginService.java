package com.example.quiz.service;


import com.example.quiz.dao.UserDao;
import com.example.quiz.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {


    private final UserDao userDao;

    public LoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> validateLogin(String email, String password) {
        return userDao.findByEmailAndPassword(email, password);
    }

}