package org.jasr.dashard.controller;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.jasr.dashard.dao.ControlsDAO;
import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.dao.MetricsDAO;
import org.jasr.dashard.domain.Device;
import org.jasr.dashard.domain.Metrics;
import org.jasr.dashard.utils.CommUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.api.client.http.HttpStatusCodes;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceDAO  deviceDAO;
    @Autowired
    private ControlsDAO  controlDAO;
    @Autowired
    private MetricsDAO metricsDAO;
    @Autowired
    private CommUtils  commUtils;

    @RequestMapping(value = "/{accessId}/metrics", method = RequestMethod.POST)
    public void report(@PathVariable String accessId, String metricsStr) {

        Device device = deviceDAO.get(accessId);
        if (device != null) {
            List<Metrics> metrics = commUtils.parseMetricsString(device.getId(), metricsStr);
            metricsDAO.updateValues(metrics);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteDevice(Long id) throws IOException {
        deviceDAO.delete(id);
        return "redirect:/index.html";
    }
    

    @RequestMapping(value = "/upsert", method = RequestMethod.POST)
    public String upsert(Device device) throws IOException {
        
        deviceDAO.upsert(device);
        return "redirect:/index.html";
    }
    


}
