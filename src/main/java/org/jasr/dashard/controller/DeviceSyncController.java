package org.jasr.dashard.controller;

import java.util.List;

import org.jasr.dashard.dao.ControlsDAO;
import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.dao.MetricsDAO;
import org.jasr.dashard.domain.Device;
import org.jasr.dashard.domain.Metrics;
import org.jasr.dashard.domain.Switch;
import org.jasr.dashard.domain.dto.DeviceDTO;
import org.jasr.dashard.domain.dto.SwitchesDTO;
import org.jasr.dashard.utils.CommUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{accessId}")
public class DeviceSyncController {

    @Autowired
    private DeviceDAO   deviceDAO;
    @Autowired
    private ControlsDAO controlDAO;
    @Autowired
    private MetricsDAO  metricsDAO;
    @Autowired
    private CommUtils   commUtils;

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public DeviceDTO init(@PathVariable String accessId) {

        Device device = deviceDAO.get(accessId);
        if (device != null)
            return new DeviceDTO(device.getMetrics(), device.getSwitches());
        else
            return null;
    }

    @RequestMapping(value = "/metrics", method = RequestMethod.POST)
    public void report(@PathVariable String accessId, List<Metrics> metrics) {
        metricsDAO.updateValues(metrics);
    }

    @RequestMapping(value = "/pins", method = RequestMethod.GET)
    public SwitchesDTO order(@PathVariable String accessId, SwitchesDTO switches) {
        
        List<Switch> pendingSwitches = controlDAO.listPending(accessId);
        controlDAO.syncSwitches(accessId,switches.getSwitches());
        controlDAO.sendSwitches(accessId,pendingSwitches);
        
        return new SwitchesDTO(pendingSwitches);
    }
}
