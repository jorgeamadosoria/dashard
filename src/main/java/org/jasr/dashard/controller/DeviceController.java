package org.jasr.dashard.controller;

import java.io.IOException;
import java.util.List;

import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.dao.MetricsDAO;
import org.jasr.dashard.domain.Device;
import org.jasr.dashard.domain.Metrics;
import org.jasr.dashard.utils.CommUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceDAO  deviceDAO;
    @Autowired
    private MetricsDAO metricsDAO;
    @Autowired
    private CommUtils  commUtils;

    @RequestMapping(value = "/{accessId}/metrics", method = RequestMethod.POST)
    public void report(@PathVariable String accessId, String metricsStr) {

        Device device = deviceDAO.get(accessId);
        if (device != null) {
            List<Metrics> metrics = commUtils.parseMetricsString(device.getId(), metricsStr);
            metricsDAO.upsert(device.getId(),metrics);
        }
    }

    @RequestMapping(value = "/{accessId}/switches", method = RequestMethod.GET)
    public String order(@PathVariable String accessId) {

        Device device = deviceDAO.get(accessId);
        return commUtils.generateSwitchString(device.getSwitches());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void upsert(@PathVariable Long id) throws IOException {
        deviceDAO.delete(id);
    }

    @RequestMapping(value = "/upsert", method = RequestMethod.POST)
    public String upsert(Device device) throws IOException {
        device.setAccessId(commUtils.generateAccessId());
        deviceDAO.upsert(device);
        return "redirect:/upsert.html";
    }

}
