package org.jasr.dashard.controller;

import java.io.IOException;

import org.jasr.dashard.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/upsert", method = RequestMethod.POST)
    public String upsert(String username, String password) throws IOException {
        userDAO.upsert(username, password);
        return "redirect:/users.html";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(String username) throws IOException {
        userDAO.delete(username);
        return "redirect:/users.html";
    }
}
