package org.jasr.dashard.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jasr.dashard.dao.ControlsDAO;
import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.dao.MetricsDAO;
import org.jasr.dashard.domain.Device;
import org.jasr.dashard.utils.CommUtils;
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
    private CommUtils  commUtils;
    @Autowired
    private MetricsDAO   metricsDAO;

    public List<Device> list(String user) {
        List<Device> devices = template.query(env.getProperty("list.devices"), new Object[] { user },new BeanPropertyRowMapper<Device>(Device.class));
        return devices;
    }
            
    public void upsert(Device entity,String user) {
        Device tempEntity = entity;
        if (entity.getId() == null || entity.getId() == 0) {
            entity.setAccessId(commUtils.generateAccessId(user));
            template.update(env.getProperty("insert.device"), entity.getName(), entity.getDescription(), entity.getAccessId(),user);
            tempEntity = get(entity.getAccessId(),user);
        }
        else {
            template.update(env.getProperty("update.device"), entity.getName(), entity.getDescription(), entity.isEnabled(),entity.getId(),user);
            tempEntity = entity;
        }

        if (entity.getMetrics() != null)
            metricsDAO.upsert(tempEntity.getId(), entity.getMetrics());
        if (entity.getSwitches() != null)
            controlsDAO.upsert(tempEntity.getId(), entity.getSwitches());
    }
          
    public Device get(String accessId,String user) {

        List<Device> devices = template.query(env.getProperty("select.device.accessId"), new Object[] { accessId,user },
                new BeanPropertyRowMapper<Device>(Device.class));

        return populateDevice(devices);
    }

    Device populateDevice(List<Device> devices) {
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
    public Device get(Long id,String user) {

        List<Device> devices = template.query(env.getProperty("select.device"), new Object[] { id,user },
                new BeanPropertyRowMapper<Device>(Device.class));

        return populateDevice(devices);
    }

    public void delete(Long id,String user) {
        template.update(env.getProperty("delete.metrics.by.device"), id);
        template.update(env.getProperty("delete.switches.by.device"), id);

        template.update(env.getProperty("delete.device"), id,user);
    }

}
