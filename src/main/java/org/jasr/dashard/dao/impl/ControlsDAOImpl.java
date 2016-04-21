package org.jasr.dashard.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jasr.dashard.dao.ControlsDAO;
import org.jasr.dashard.domain.Metrics;
import org.jasr.dashard.domain.Switch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ControlsDAOImpl implements ControlsDAO {

    @Autowired
    private Environment  env;
    @Resource
    private JdbcTemplate template;

    public void toggle(Switch entity) {
        template.update(env.getProperty("select.switches.by.device"), entity.getState(), entity.getId(), entity.getId());
    }

    public List<Switch> list(Long id) {
        return template.query(env.getProperty("select.switches.by.device"), new Object[] { id },
                new BeanPropertyRowMapper<Switch>(Switch.class));
    }
    
    public void upsert(List<Switch> entities) {
        for(Switch metrics:entities){
            upsert(metrics);
        }
    }

    public void upsert(Switch entity) {
        if (entity.getId() == null)
            template.update(env.getProperty("insert.metrics"), entity.getName(), entity.getDescription(), entity.getPin(),
                    entity.getDeviceId(), entity.getParentId());
        else
            template.update(env.getProperty("update.metrics"), entity.getName(), entity.getDescription(), entity.getPin(),
                    entity.getParentId(), entity.getId());
    }

    public void delete(Long id) {
        template.update(env.getProperty("delete.switch"), id);
    }
}