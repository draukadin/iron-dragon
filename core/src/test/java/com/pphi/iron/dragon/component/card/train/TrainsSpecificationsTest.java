package com.pphi.iron.dragon.component.card.train;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class TrainsSpecificationsTest {

    private TrainsSpecifications trainsSpecifications1 = new TrainsSpecifications(5, 16, 3);
    private TrainsSpecifications trainsSpecifications2 = new TrainsSpecifications(5, 16, 3);
    private TrainsSpecifications trainsSpecifications3 = new TrainsSpecifications(5, 16, 3);
    private TrainsSpecifications trainsSpecifications4 = new TrainsSpecifications(4, 14, 3);

    @Test
    public void getLevelTest() {
        assertEquals(5, trainsSpecifications1.getLevel());
    }

    @Test
    public void getSpeedTest() {
        assertEquals(16, trainsSpecifications1.getSpeed());
    }

    @Test
    public void toStringTest() {
        assertNotNull(trainsSpecifications1.toString());
        assertTrue(trainsSpecifications1.toString().length() > 0);
    }

    @Test
    public void reflexiveTest() {
        assertTrue(trainsSpecifications1.equals(trainsSpecifications1));
    }

    @Test
    public void symmetryTest() {
        assertTrue(trainsSpecifications1.equals(trainsSpecifications2));
        assertTrue(trainsSpecifications2.equals(trainsSpecifications1));
    }

    @Test
    public void nullabilityTest() {
        assertFalse(trainsSpecifications1.equals(null));
    }

    @Test
    public void transitiveTest() {
        assertTrue(trainsSpecifications1.equals(trainsSpecifications2));
        assertTrue(trainsSpecifications2.equals(trainsSpecifications3));
        assertTrue(trainsSpecifications3.equals(trainsSpecifications1));
    }

    @Test
    public void nonEqualTest() {
        assertFalse(trainsSpecifications1.equals(trainsSpecifications4));
    }

    @Test
    public void hashCodeEqualTest() {
        assertEquals(trainsSpecifications1.hashCode(), trainsSpecifications2.hashCode());
    }

    @Test
    public void hashCodeNotEqualTest() {
        assertNotEquals(trainsSpecifications1.hashCode(), trainsSpecifications4.hashCode());
    }
}
