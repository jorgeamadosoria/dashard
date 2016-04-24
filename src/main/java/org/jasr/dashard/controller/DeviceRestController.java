package org.jasr.dashard.controller;

import java.util.List;

import org.jasr.dashard.dao.ControlsDAO;
import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.dao.MetricsDAO;
import org.jasr.dashard.domain.Device;
import org.jasr.dashard.domain.Metrics;
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
    private ControlsDAO controlsDAO;
    @Autowired
    private CommUtils  commUtils;

    @RequestMapping(value = "/metrics", method = RequestMethod.GET)
    public List<Metrics> metrics(Long id) {
        return metricsDAO.list(id);
    }

    @RequestMapping(value = "/switches", method = RequestMethod.GET)
    public String order(Long id) {
        return commUtils.generateSwitchString(controlsDAO.list(id));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Device> list() {
        return deviceDAO.list();

    }

}
