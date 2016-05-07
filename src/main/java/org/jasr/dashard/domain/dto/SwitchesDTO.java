package org.jasr.dashard.domain.dto;

import java.util.Date;
import java.util.List;

import org.jasr.dashard.domain.Switch;

public class SwitchesDTO {
    List<Switch> switches;
    Date        date;

    public SwitchesDTO(List<Switch> switches) {
        date = new Date();
        this.switches = switches;
    }

    public List<Switch> getSwitches() {
        return switches;
    }

    public Date getDate() {
        return date;
    }
}
