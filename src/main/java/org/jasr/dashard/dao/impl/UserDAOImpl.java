package org.jasr.dashard.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.jasr.dashard.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    
    private RowMapper<User> mapper = new RowMapper<User>() {
        
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            String username = rs.getString("username");
            String password = rs.getString("password");
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(username,password,authorities);
        }
    };
    
    public void delete(String username) {
        template.update(env.getProperty("delete.authority"), username);
        template.update(env.getProperty("delete.user"), username);
    }

    @Override
    public List<User> list() {
        List<User> users = template.query(env.getProperty("list.user"), mapper);
        return users;
    }

    @Override
    public void upsert(String username,String password) {
        
        Collection<GrantedAuthority> coll = new HashSet<>();
        coll.add(new SimpleGrantedAuthority("ROLE_USER"));
        upsert(new User(username, password, coll));
    }
        @Override
        public void upsert(User user) {

        if (get(user.getUsername()) == null) {
            template.update(env.getProperty("insert.user"), user.getUsername(), user.getPassword());
            template.update(env.getProperty("insert.authority"), user.getUsername());
        }
        else {
            template.update(env.getProperty("update.password"), user.getPassword(), user.getUsername());
        }

    }

    @Override
    public User get(String username) {
        List<User> users = template.query(env.getProperty("select.user"), new Object[] { username },
                mapper);
        if (!CollectionUtils.isEmpty(users))
            return users.get(0);
        return null;
    }

}
