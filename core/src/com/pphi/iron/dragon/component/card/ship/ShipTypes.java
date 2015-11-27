package com.pphi.iron.dragon.component.card.ship;

public enum ShipTypes {
    CLASS_1(1, 8), CLASS_2(1, 9), CLASS_3(2, 10), CLASS_4(2, 11), CLASS_5(3, 12), CLASS_6(3, 13);

    private int cost;
    private int speed;

    ShipTypes(int cost, int speed) {
        this.cost = cost;
        this.speed = speed;
    }

    public int getCost() {
        return cost;
    }

    public int getSpeed() {
        return speed;
    }
}
