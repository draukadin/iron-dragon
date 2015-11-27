package com.pphi.iron.dragon.component.card.ship;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class ShipCardTest {

    private ShipCard shipCard1 = new ShipCard("Slow", "CLASS_1");
    private ShipCard shipCard2 = new ShipCard("Fast", "CLASS_6");

    @Test
    public void getShipCostTest() {
        assertEquals(shipCard1.getShipCost(), 1);
        assertEquals(shipCard2.getShipCost(), 3);
    }

    @Test
    public void getShipSpeed() {
        assertEquals(shipCard1.getShipSpeed(), 8);
        assertEquals(shipCard2.getShipSpeed(), 13);
    }

    @Test
    public void getNameTest() {
        assertEquals(shipCard1.getName(), "Slow");
        assertEquals(shipCard2.getName(), "Fast");
    }

    @Test
    public void getShipTypeTest() {
        assertEquals(shipCard1.getShipType(), ShipTypes.CLASS_1);
        assertEquals(shipCard2.getShipType(), ShipTypes.CLASS_6);
    }
}
