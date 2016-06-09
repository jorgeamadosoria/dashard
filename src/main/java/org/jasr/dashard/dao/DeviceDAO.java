package org.jasr.dashard.dao;

import java.util.List;

import org.jasr.dashard.domain.Device;

public interface DeviceDAO {

    public List<Device> list(String user);

    public void upsert(Device entity,String user);

    public Device get(String accessId,String user);

    public Device get(Long id,String user);

    public void delete(Long id,String user);

}
