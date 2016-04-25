package org.jasr.dashard.controller;

import java.io.IOException;
import java.util.List;

import org.jasr.dashard.dao.ControlsDAO;
import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.dao.MetricsDAO;
import org.jasr.dashard.domain.Device;
import org.jasr.dashard.domain.Metrics;
import org.jasr.dashard.domain.Switch;
import org.jasr.dashard.utils.CommUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DeviceRestController {

    @Autowired
    private DeviceDAO  deviceDAO;
    @Autowired
    private MetricsDAO metricsDAO;
    @Autowired
    private ControlsDAO controlDAO;
    @Autowired
    private CommUtils  commUtils;

    @RequestMapping(value = "/metrics", method = RequestMethod.GET)
    public List<Metrics> metrics(Long id) {
        return metricsDAO.list(id);
    }

    @RequestMapping(value = "/switches", method = RequestMethod.GET)
    public String order(Long id) {
        return commUtils.generatePinString(controlDAO.list(id));
    }
    
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public Device get(Long id) {
        return deviceDAO.get(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Device> list() {
        return deviceDAO.list();

    }

    @RequestMapping(value = "/toggle", method = RequestMethod.POST)
    public List<Switch> toggleSwitch(Long deviceId,Long id) throws IOException {
        controlDAO.toggle(id);
        return controlDAO.list(deviceId);
    }
    
}
