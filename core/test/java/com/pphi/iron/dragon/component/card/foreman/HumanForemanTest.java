package com.pphi.iron.dragon.component.card.foreman;

import static org.testng.Assert.assertEquals;

import com.pphi.iron.dragon.component.card.ship.ShipTypes;
import org.testng.annotations.Test;

public class HumanForemanTest {

    private Foreman foreman = new HumanForeman(1, "HUMAN", "Joe", "Builds Bridges");

    @Test
    public void getBoardingFeeTest() {
        assertEquals(3, foreman.getBoardingFee(ShipTypes.CLASS_5));
    }

    @Test
    public void oceanInletCrossingCostTest() {
        assertEquals(foreman.getOceanInletCrossingCost(), 1);
    }

    @Test
    public void riverCrossingCostTest() {
        assertEquals(foreman.getRiverCrossingCost(), 0);
    }
}
