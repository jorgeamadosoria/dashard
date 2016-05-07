package org.jasr.dashard.domain.dto;

import java.util.Date;
import java.util.List;

import org.jasr.dashard.domain.Metrics;

public class MetricsDTO {
    List<Metrics> metrics;
    Date         date;

    public MetricsDTO(List<Metrics> metrics) {
        date = new Date();
        this.metrics = metrics;
    }

    public List<Metrics> getMetrics() {
        return metrics;
    }

    public Date getDate() {
        return date;
    }

}
