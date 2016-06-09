package org.jasr.dashard.controller;

import java.io.IOException;
import java.util.List;

import org.jasr.dashard.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class RestUserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> list() {

        return userDAO.list();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public User get(String username) throws IOException {
        return userDAO.get(username);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public void get(String username, String password) throws IOException {
        userDAO.upsert(username, password);
    }



}
