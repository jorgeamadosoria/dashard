package org.jasr.dashard.utils;

import java.util.ArrayList;
import java.util.List;

import org.jasr.dashard.domain.Metrics;
import org.jasr.dashard.domain.Switch;
import org.springframework.stereotype.Component;

@Component
public class CommUtils {

    
    public static String generateAccessId(){
        return "";
    }
    
    public static List<Metrics> parseMetricsString(Long deviceId,String metrics){
        return new ArrayList<Metrics>();
    }
    
    public static String generateSwitchString(List<Switch> switches){
        
        return "";
    }
}
