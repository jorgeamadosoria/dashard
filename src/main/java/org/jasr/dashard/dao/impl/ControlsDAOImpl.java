package org.jasr.dashard.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jasr.dashard.dao.ControlsDAO;
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

    public void toggle(Long id) {
        template.update(env.getProperty("toggle.switch"), id);
        template.update(env.getProperty("toggle.children.switch"), id, id);
    }

    public List<Switch> list(Long id) {
        return template.query(env.getProperty("select.switches.by.device"), new Object[] { id },
                new BeanPropertyRowMapper<Switch>(Switch.class));
    }

    public List<Switch> listPending(String accessId) {
        return template.query(env.getProperty("select.pending.switches.by.device_id"), new Object[] { accessId },
                new BeanPropertyRowMapper<Switch>(Switch.class));
    }
    
    public List<Switch> list(String accessId) {
        return template.query(env.getProperty("select.switches.by.device.accessId"), new Object[] { accessId },
                new BeanPropertyRowMapper<Switch>(Switch.class));
    }

    public void upsert(Long deviceId, List<Switch> entities) {
        for (Switch s : entities) {
            if (s.getName() != null)
                upsert(deviceId, s);
        }
    }

    public void upsert(Long deviceId, Switch entity) {
        if (entity.getParentId() == null)
            entity.setParentId(0L);
        if (entity.getId() == null || entity.getId() == 0)
            template.update(env.getProperty("insert.switch"), entity.getName(), entity.getDescription(), entity.getPin(),
                    deviceId, entity.getParentId());
        else
            template.update(env.getProperty("update.switch"), entity.getName(), entity.getDescription(), entity.getPin(),
                    entity.getParentId(), entity.isEnabled(), entity.getStatus(),entity.getId());
    }

    public void delete(Long id) {
        template.update(env.getProperty("delete.switch"), id);
    }

    @Override
    public void syncSwitches(String accessId, List<Switch> switches) {
        for (Switch s : switches)
            template.update(env.getProperty("confirm.switch.status"), s.getState(), s.getPin(), accessId);
    }

    @Override
    public void sendSwitches(String accessId, List<Switch> switches) {
        for (Switch s : switches)
            template.update(env.getProperty("send.switch.status"), s.getState(), s.getId());
    }
}
