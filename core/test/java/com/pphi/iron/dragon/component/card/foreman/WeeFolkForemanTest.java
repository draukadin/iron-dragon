package com.pphi.iron.dragon.component.card.foreman;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.pphi.iron.dragon.component.card.ship.ShipTypes;
import org.testng.annotations.Test;

public class WeeFolkForemanTest {

    private Foreman foreman1 = new WeeFolkForeman(1, "WEE_FOLK", "Hello", "");
    private Foreman foreman2 = new WeeFolkForeman(1, "WEE_FOLK", "Hello", "");
    private Foreman foreman3 = new WeeFolkForeman(1, "WEE_FOLK", "Hello", "");
    private Foreman foreman4 = new WeeFolkForeman(2, "WEE_FOLK", "Bye", "");

    @Test
    public void getBoardingFeeTest() {
        assertEquals(foreman1.getBoardingFee(ShipTypes.CLASS_6), 0);
    }

    @Test
    public void getDrawCardsTest() {
        assertEquals(foreman1.drawShipCards(), 3);
    }

    @Test
    public void toStringTest() {
        assertNotNull(foreman1.toString());
        assertTrue(foreman1.toString().length() > 0);
    }

    @Test
    public void reflexiveTest() {
        assertTrue(foreman1.equals(foreman1));
    }

    @Test
    public void symmetryTest() {
        assertTrue(foreman1.equals(foreman2));
        assertTrue(foreman2.equals(foreman1));
    }

    @Test
    public void nullabilityTest() {
        assertFalse(foreman1.equals(null));
    }

    @Test
    public void transitiveTest() {
        assertTrue(foreman1.equals(foreman2));
        assertTrue(foreman2.equals(foreman3));
        assertTrue(foreman3.equals(foreman1));
    }

    @Test
    public void nonEqualTest() {
        assertFalse(foreman1.equals(foreman4));
    }

    @Test
    public void hashCodeEqualTest() {
        assertEquals(foreman1.hashCode(), foreman2.hashCode());
    }

    @Test
    public void hashCodeNotEqualTest() {
        assertNotEquals(foreman1.hashCode(), foreman4.hashCode());
    }
}
