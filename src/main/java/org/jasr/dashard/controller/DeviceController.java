package org.jasr.dashard.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.jasr.dashard.dao.ControlsDAO;
import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.dao.MetricsDAO;
import org.jasr.dashard.domain.Device;
import org.jasr.dashard.domain.Metrics;
import org.jasr.dashard.utils.CommUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    private ControlsDAO  controlDAO;
    @Autowired
    private MetricsDAO metricsDAO;
    @Autowired
    private CommUtils  commUtils;

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteDevice(Principal principal,Long id) throws IOException {
        deviceDAO.delete(id,principal.getName());
        return "redirect:/list.html";
    }
    
    @RequestMapping(value = "/upsert", method = RequestMethod.POST)
    public String upsert(Principal principal,Device device) throws IOException {
        
        deviceDAO.upsert(device,principal.getName());
        return "redirect:/list.html";
    }
    


}
