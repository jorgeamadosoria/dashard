package org.jasr.dashard.dao;

import java.util.List;

import org.springframework.security.core.userdetails.User;

public interface UserDAO {

    public List<User> list();

    public User get(String username);

    public void upsert(User user);

    public void delete(String username);

}
