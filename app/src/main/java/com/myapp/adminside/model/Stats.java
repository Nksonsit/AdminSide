package com.myapp.adminside.model;

/**
 * Created by ishan on 29-11-2017.
 */

public class Stats {
    private String Id;
    private String Site;
    private String Dl;
    private String Ul;
    private String TotalDl;
    private String TotalUl;
    private String AvgDl;
    private String AvgUl;
    private String Count;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getDl() {
        return Dl;
    }

    public void setDl(String dl) {
        Dl = dl;
    }

    public String getUl() {
        return Ul;
    }

    public void setUl(String ul) {
        Ul = ul;
    }

    public String getTotalDl() {
        return TotalDl;
    }

    public void setTotalDl(String totalDl) {
        TotalDl = totalDl;
    }

    public String getTotalUl() {
        return TotalUl;
    }

    public void setTotalUl(String totalUl) {
        TotalUl = totalUl;
    }

    public String getAvgDl() {
        return AvgDl;
    }

    public void setAvgDl(String avgDl) {
        AvgDl = avgDl;
    }

    public String getAvgUl() {
        return AvgUl;
    }

    public void setAvgUl(String avgUl) {
        AvgUl = avgUl;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }
}
