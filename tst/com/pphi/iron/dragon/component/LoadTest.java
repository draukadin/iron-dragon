package com.pphi.iron.dragon.component;

import com.iron.dragon.exceptions.UnsupportedEnumException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LoadTest {

    @Test
    public void getLoadTest() {
        assertEquals(Load.getLoad("Ale"), Load.ALE);
        assertEquals(Load.getLoad("ALE"), Load.ALE);
        assertEquals(Load.getLoad("ale"), Load.ALE);
    }

    @Test
    public void getLoadTestStringWithLeadingAndTrailingSpaces() {
        assertEquals(Load.getLoad(" Ale "), Load.ALE);
    }

    @Test(expectedExceptions = UnsupportedEnumException.class)
    public void getLoadTestWithEmptyStringInput() {
        assertEquals(Load.getLoad(""), Load.ALE);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getLoadTestWithNullString() {
        assertEquals(Load.getLoad(null), Load.ALE);
    }

    @Test
    public void getQuantityTest() {
        assertEquals(Load.ALE.getQuantity(), 4);
    }
}
