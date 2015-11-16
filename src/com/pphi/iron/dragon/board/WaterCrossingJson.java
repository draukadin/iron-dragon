package com.pphi.iron.dragon.board;

public class WaterCrossingJson {

    private String name;
    private WaterCrossingType waterCrossingType;

    public WaterCrossingJson(String name, WaterCrossingType waterCrossingType) {
        this.name = name;
        this.waterCrossingType = waterCrossingType;
    }

    public String getName() {
        return name;
    }

    public WaterCrossingType getWaterCrossingType() {
        return waterCrossingType;
    }

}
