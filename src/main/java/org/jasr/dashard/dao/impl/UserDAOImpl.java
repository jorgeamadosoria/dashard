package org.jasr.dashard.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jasr.dashard.dao.UserDAO;
import org.jasr.dashard.domain.Device;
import org.jasr.dashard.domain.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private Environment  env;
    @Resource
    private JdbcTemplate template;

    public void delete(String username) {
        template.update(env.getProperty("delete.authority"), username);
        template.update(env.getProperty("delete.username"), username);
    }

    @Override
    public List<User> list() {
        List<User> users = template.query(env.getProperty("list.user"), new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public void upsert(User user) {

        if (get(user.getUsername()) == null) {
            template.update(env.getProperty("insert.user"), user.getUsername(), user.getPassword());
            template.update(env.getProperty("insert.authority"), user.getUsername());
        }
        else {
            template.update(env.getProperty("update.user"), user.getPassword(), user.getUsername());
        }

    }

    @Override
    public User get(String username) {
        List<User> users = template.query(env.getProperty("select.user"), new Object[] { username },
                new BeanPropertyRowMapper<User>(User.class));
        if (!CollectionUtils.isEmpty(users))
            return users.get(0);
        return null;
    }

}
