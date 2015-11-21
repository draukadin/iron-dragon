package com.pphi.iron.dragon.component;

import static org.testng.Assert.assertEquals;

import com.iron.dragon.exceptions.UnsupportedEnumException;
import org.testng.annotations.Test;

public class CountryTest {

    @Test(expectedExceptions = {UnsupportedEnumException.class})
    public void invalidArgumentTest_getByValue() {
        Country.getCountryByValue("");
    }

    @Test(expectedExceptions = {UnsupportedEnumException.class})
    public void invalidArgumentTest_getByName() {
        Country.getCountryByName("");
    }

    @Test
    public void getCityByValue() {
        assertEquals(Country.getCountryByValue("KOLAND"), Country.KOLAND);
    }

    @Test
    public void getCityByName() {
        assertEquals(Country.getCountryByName("Koland"), Country.KOLAND);
    }
}
