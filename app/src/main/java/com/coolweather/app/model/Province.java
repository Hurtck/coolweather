package com.coolweather.app.model;

import static android.R.attr.id;

/**
 * Created by 47321 on 2016/9/27 0027.
 */

public class Province {

    private int id;
    private String provinceName;
    private  String provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
