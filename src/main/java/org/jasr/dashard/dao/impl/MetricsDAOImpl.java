package org.jasr.dashard.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jasr.dashard.dao.MetricsDAO;
import org.jasr.dashard.domain.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MetricsDAOImpl implements MetricsDAO {

    @Autowired
    private Environment  env;
    @Resource
    private JdbcTemplate template;

    public List<Metrics> list(Long id) {
        return template.query(env.getProperty("select.metrics.by.device"), new Object[] { id },
                new BeanPropertyRowMapper<Metrics>(Metrics.class));
    }

    public void updateValue(Metrics entity) {
        template.update(env.getProperty("update.metrics.value"), entity.getValue(), entity.getDate(), entity.getCode(),
                entity.getDeviceId());
        template.update(env.getProperty("insert.metrics.history"), entity.getDeviceId(), entity.getCode(), entity.getValue(),
                entity.getDate());

    }
    
    public void upsert(List<Metrics> entities) {
        for(Metrics metrics:entities){
            upsert(metrics);
        }
    }

    public void upsert(Metrics entity) {
        if (entity.getId() == null)
            template.update(env.getProperty("insert.metrics"), entity.getDeviceId(), entity.getName(), entity.getCode(),
                    entity.getDate());
        else
            template.update(env.getProperty("update.metrics"), entity.getName(), entity.getCode(), entity.getId());
    }

    public void delete(Long id) {
        template.update(env.getProperty("delete.metrics"), id);
    }

}
