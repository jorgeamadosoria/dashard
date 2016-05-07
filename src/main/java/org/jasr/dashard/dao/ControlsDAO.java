package org.jasr.dashard.dao;

import java.util.List;

import org.jasr.dashard.domain.Switch;

public interface ControlsDAO {
    public void toggle(Long id);

    public List<Switch> list(Long id);
    public List<Switch> list(String accessId);
    public List<Switch> listPending(String accessId);
    public void upsert(Long deviceId,List<Switch> entities);
    public void upsert(Long deviceId,Switch entity);
    public void syncSwitches(String accessId,List<Switch> switches);
    public void sendSwitches(String accessId,List<Switch> switches);
    public void delete(Long id);
}
