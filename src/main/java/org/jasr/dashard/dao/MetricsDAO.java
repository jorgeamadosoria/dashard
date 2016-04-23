package org.jasr.dashard.dao;

import java.util.List;

import org.jasr.dashard.domain.Metrics;

public interface MetricsDAO {
    public List<Metrics> list(Long id);

    public void updateValues(List<Metrics> list);

    public void updateValue(Metrics entity);

    public void upsert(Long deviceId,List<Metrics> entities);

    public void upsert(Long deviceId,Metrics entity);

    public void delete(Long id);
}
