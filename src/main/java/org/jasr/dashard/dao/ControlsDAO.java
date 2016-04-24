package org.jasr.dashard.dao;

import java.util.List;

import org.jasr.dashard.domain.Switch;

public interface ControlsDAO {
    public void toggle(Switch entity);

    public List<Switch> list(Long id);

    public void upsert(Long deviceId,List<Switch> entities);
    public void upsert(Long deviceId,Switch entity);

    public void delete(Long id);
}
