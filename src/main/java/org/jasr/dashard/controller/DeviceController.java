package org.jasr.dashard.controller;

import java.io.IOException;
import java.util.List;

import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.domain.Device;
import org.jasr.dashard.utils.CommUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceDAO deviceDAO;

    @RequestMapping(value = "/{accessId}/metrics", method = RequestMethod.POST)
    public void report(@PathVariable String accessId,String metrics){
    }
    
    @RequestMapping(value = "/{accessId}/switches", method = RequestMethod.GET)
    public String order(@PathVariable String accessId){
        
        Device device = deviceDAO.get(accessId);
        return CommUtils.generateSwitchString(device.getSwitches());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Device> list() {
            return deviceDAO.list();

    }

    @RequestMapping(value = "/upsert", method = RequestMethod.POST)
    public void upsert(Device device) throws IOException {
    	device.setAccessId(CommUtils.generateAccessId());
        deviceDAO.upsert(device);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void upsert(@PathVariable Long id) throws IOException {
        deviceDAO.delete(id);
    }

}
