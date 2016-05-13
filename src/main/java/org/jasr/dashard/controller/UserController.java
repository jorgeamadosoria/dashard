package org.jasr.dashard.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.jasr.dashard.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserController {

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

    @RequestMapping(value = "/upsert", method = RequestMethod.POST)
    public void upsert(Set<User> users) throws IOException {
        for (User user : users)
            if (user != null)
                userDAO.upsert(user);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(String username) throws IOException {

        userDAO.delete(username);
    }

}
