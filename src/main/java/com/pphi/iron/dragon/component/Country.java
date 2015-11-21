package com.pphi.iron.dragon.component;

import com.iron.dragon.exceptions.UnsupportedEnumException;

public enum Country {
    BRIGHT("Bright"),
    OLDE_WORLD("Olde World"),
    WISLANDOR("Wislandor"),
    SILVARRE("Silvarre"),
    CALCENDEN("Calcenden"),
    EUSARCH("Eusarch"),
    DUL_UL_DUR("Dul-Ul-Dur"),
    IRON_HOLM("Iron Holm"),
    JANOSHAL("Janoshal"),
    RAKHATZ("Rakhatz"),
    GLYTH_GAMEL("Glyth Gamel"),
    PIRATE_ISLES("Priate Isle"),
    ORC_WASTES("Orc Wasts"),
    KOLAND("Koland"),
    NORTHERN_WASTE("Northern Waste"),
    UNDERGROUND("Underground"),
    NONE("None");

    private String name;

    Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Country getCountryByName(String name) {
        for (Country c : Country.values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new UnsupportedEnumException("Country " + name + " is not supported");
    }

    public static Country getCountryByValue(String country) {
        for (Country c : Country.values()) {
            if (c.toString().equalsIgnoreCase(country)) {
                return c;
            }
        }
        throw new UnsupportedEnumException("Country " + country + " is not supported");
    }
}
