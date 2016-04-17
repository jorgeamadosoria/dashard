package org.jasr.dashard.dao;

import java.util.List;

import org.jasr.dashard.domain.Metrics;

public interface MetricsDAO {
    public List<Metrics> list(Long id);

    public void updateValue(Metrics entity);
    public void upsert(List<Metrics> entities);
    public void upsert(Metrics entity);

    public void delete(Long id);
}
