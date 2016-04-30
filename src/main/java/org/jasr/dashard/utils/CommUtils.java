package org.jasr.dashard.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jasr.dashard.dao.DeviceDAO;
import org.jasr.dashard.domain.Device;
import org.jasr.dashard.domain.Metrics;
import org.jasr.dashard.domain.Switch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CommUtils {

    @Autowired
    private DeviceDAO    deviceDAO;

    private SecureRandom random = new SecureRandom();

    public String generateAccessId() {
        String str = "";
        Device device = null;
        do {
            for (int i = 0; i < 100; i++) {
                str += (char) (97 + random.nextInt(26));

            }

            device = deviceDAO.get(str);
        }
        while (device != null);
        return str;
    }

    public List<Metrics> parseMetricsString(Long deviceId, String metrics) {
        String[] metricsStr = metrics.split(";");

        List<Metrics> list = new ArrayList<>();
        for (String str : metricsStr) {
            String[] split = str.split(",");
            Metrics m = new Metrics();
            m.setDeviceId(deviceId);
            m.setId(Long.valueOf(split[0]));
            m.setDate(new Date());
            m.setValue(split[1]);
            list.add(m);
        }
        return list;
    }

    public String generatePinString(List<Switch> switches) {
        if (!CollectionUtils.isEmpty(switches)) {
            String hi = " ";
            String lo = " ";
            for (Switch ele : switches) {
                if (ele.getState() == 1)
                    hi += ele.getPin() + ",";
                else
                    lo += ele.getPin() + ",";
            }
            hi = hi.substring(0, hi.length() - 1);
            lo = lo.substring(0, lo.length() - 1);
            return hi.trim() + ";" + lo.trim();
        }
        return "";
    }
}
