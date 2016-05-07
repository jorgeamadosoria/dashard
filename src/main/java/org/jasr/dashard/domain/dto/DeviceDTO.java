package org.jasr.dashard.domain.dto;

import java.util.Date;
import java.util.List;

import org.jasr.dashard.domain.Metrics;
import org.jasr.dashard.domain.Switch;

public class DeviceDTO {
    List<Metrics> metrics;
    List<Switch> switches;
    Date         date;

    public DeviceDTO(List<Metrics> metrics,List<Switch> switches) {
        date = new Date();
        this.metrics = metrics;
        this.switches = switches;
    }

    public List<Switch> getSwitches() {
        return switches;
    }

    public List<Metrics> getMetrics() {
        return metrics;
    }

    public Date getDate() {
        return date;
    }

}
