package org.jasr.dashard.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jasr.dashard.dao.ControlsDAO;
import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.dao.MetricsDAO;
import org.jasr.dashard.domain.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Repository
@Transactional
public class DeviceDAOImpl implements DeviceDAO {

    @Autowired
    private Environment  env;
    @Resource
    private JdbcTemplate template;

    @Autowired
    private ControlsDAO  controlsDAO;

    @Autowired
    private MetricsDAO   metricsDAO;

    public List<Device> list() {
        List<Device> devices = template.query(env.getProperty("list.devices"), new BeanPropertyRowMapper<Device>(Device.class));
        return devices;
    }

    public void upsert(Device entity) {
        Device tempEntity = entity;
        if (entity.getId() == null) {
            template.update(env.getProperty("insert.device"), entity.getName(), entity.getDescription(), entity.getAccessId());
            tempEntity = get(entity.getAccessId());
        }
        else {
            template.update(env.getProperty("update.device"), entity.getName(), entity.getDescription(), entity.getId());
        }
        if (entity.getMetrics() != null)
            metricsDAO.upsert(tempEntity.getId(),entity.getMetrics());
        if (entity.getSwitches() != null)
            controlsDAO.upsert(tempEntity.getId(),entity.getSwitches());
    }

    public Device get(String accessId) {

        List<Device> devices = template.query(env.getProperty("select.device.accessId"), new Object[] { accessId },
                new BeanPropertyRowMapper<Device>(Device.class));

        if (!CollectionUtils.isEmpty(devices)) {
            Device device = devices.get(0);

            if (device != null) {
                device.setSwitches(controlsDAO.list(device.getId()));
                device.setMetrics(metricsDAO.list(device.getId()));
            }
            return device;
        }
        return null;
    }

    public Device get(Long id) {

        return template.queryForObject(env.getProperty("select.device"), new Object[] { id },
                new BeanPropertyRowMapper<Device>(Device.class));
    }

    public void delete(Long id) {
        template.update(env.getProperty("delete.device"), id);
    }

}
