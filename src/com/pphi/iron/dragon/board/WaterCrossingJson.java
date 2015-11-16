package com.pphi.iron.dragon.board;

import com.pphi.iron.dragon.component.Country;

public class WaterCrossingJson {

    private String name;
    private Country country;
    private WaterCrossingType waterCrossingType;

    public WaterCrossingJson(String name, String country, WaterCrossingType waterCrossingType) {
        this.name = name;
        this.country = Country.valueOf(country);
        this.waterCrossingType = waterCrossingType;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public WaterCrossingType getWaterCrossingType() {
        return waterCrossingType;
    }

}
