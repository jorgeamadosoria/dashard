package org.jasr.dashard.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Metrics extends BaseEntity {

    private Long   id;
    private Long   deviceId;
    private String code;
    private String name;
    private String value;
    private Date   date;
    private String type;
    private boolean enabled;
    
    
    
    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public SimpleDateFormat getFormat() {
        return format;
    }


    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }

    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/y kk:mm:ss");
    
    public String getDateString(){
        return format.format(date);
    }
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
