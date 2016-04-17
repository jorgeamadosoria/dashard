package org.jasr.dashard.dao;

import java.util.List;

import org.jasr.dashard.domain.BaseEntity;
import org.jasr.dashard.domain.Device;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public interface DeviceDAO {

    public List<Device> list();

    public void upsert(Device entity);

    public Device get(String accessId);

    public Device get(Long id);

    public void delete(Long id);

}
