package com.myapp.adminside.model;

import java.io.Serializable;

/**
 * Created by ishan on 23-11-2017.
 */

public class Site implements Serializable{
    private String Lat;
    private String Long;
    private String Site;
    private String Description;
    private String Distance;

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }
}
