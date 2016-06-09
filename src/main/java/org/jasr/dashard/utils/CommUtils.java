package org.jasr.dashard.utils;

import java.security.SecureRandom;

import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.domain.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommUtils {

    @Autowired
    private DeviceDAO    deviceDAO;

    private SecureRandom random = new SecureRandom();

    public String generateAccessId(String user) {
        String str = "";
        Device device = null;
        do {
            for (int i = 0; i < 100; i++) {
                str += (char) (97 + random.nextInt(26));

            }

            device = deviceDAO.get(str,user);
        }
        while (device != null);
        return str;
    }

}
