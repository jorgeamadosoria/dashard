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

    public void updateValues(List<Metrics> list) {
        for (Metrics metrics : list) {
            updateValue(metrics);
        }

    }

    public void updateValue(Metrics entity) {
        template.update(env.getProperty("update.metrics.value"), entity.getValue(), entity.getCode(),
                entity.getDeviceId());
    }

    public void upsert(Long deviceId, List<Metrics> entities) {
        for (Metrics metrics : entities) {
            upsert(deviceId,metrics);
        }
    }

    public void upsert(Long deviceId, Metrics entity) {
        entity.setDeviceId(deviceId);
        if (entity.getId() == null || entity.getId() == 0)
            template.update(env.getProperty("insert.metrics"), entity.getDeviceId(), entity.getName(), entity.getCode(), entity.getType());
        else
            template.update(env.getProperty("update.metrics"), entity.getName(), entity.getCode(), entity.getType(), entity.getId());
    }

    public void delete(Long id) {
        template.update(env.getProperty("delete.metrics"), id);
    }

}
