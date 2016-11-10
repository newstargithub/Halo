package com.gd.halo.bean;

/**
 * Created by zhouxin on 2016/11/9.
 * Description:
 */
public class Carrier {


    /**
     * carrier_code : ems
     * carrier_phone : 11183
     * carrier_name : EMS
     */

    private String carrier_code;
    private String carrier_phone;
    private String carrier_name;

    public String getCarrier_code() {
        return carrier_code;
    }

    public void setCarrier_code(String carrier_code) {
        this.carrier_code = carrier_code;
    }

    public String getCarrier_phone() {
        return carrier_phone;
    }

    public void setCarrier_phone(String carrier_phone) {
        this.carrier_phone = carrier_phone;
    }

    public String getCarrier_name() {
        return carrier_name;
    }

    public void setCarrier_name(String carrier_name) {
        this.carrier_name = carrier_name;
    }
}
