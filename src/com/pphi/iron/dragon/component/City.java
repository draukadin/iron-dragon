package com.pphi.iron.dragon.component;

import static com.google.common.collect.Lists.newArrayList;
import static com.pphi.iron.dragon.component.Country.BRIGHT;
import static com.pphi.iron.dragon.component.Country.CALCENDEN;
import static com.pphi.iron.dragon.component.Country.DUL_UL_DUR;
import static com.pphi.iron.dragon.component.Country.EUSARCH;
import static com.pphi.iron.dragon.component.Country.GLYTH_GAMEL;
import static com.pphi.iron.dragon.component.Country.IRON_HOLM;
import static com.pphi.iron.dragon.component.Country.KOLAND;
import static com.pphi.iron.dragon.component.Country.NORTHERN_WASTE;
import static com.pphi.iron.dragon.component.Country.OLDE_WORLD;
import static com.pphi.iron.dragon.component.Country.ORC_WASTES;
import static com.pphi.iron.dragon.component.Country.PIRATE_ISLES;
import static com.pphi.iron.dragon.component.Country.RAKHATZ;
import static com.pphi.iron.dragon.component.Country.SILVARRE;
import static com.pphi.iron.dragon.component.Country.UNDERGROUND;
import static com.pphi.iron.dragon.component.Country.WISLANDOR;

import com.google.common.base.MoreObjects;
import com.iron.dragon.exceptions.UnsupportedEnumException;
import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;

import java.util.List;

public enum City {
    BLUEFELD(21, -8, -13, "Bluefeld", BRIGHT, "Hops"),
    BORGOFORT(9, 3, -12, "Borgofort", BRIGHT, "Lumber"),
    BREMMNER(16, -5, -11, "Bremmner", BRIGHT, "Steel"),
    BREVANDES(8, -5, -3, "Brevandes", BRIGHT, "Wheat"),
    BULATZ(17, -8, -9, "Bulatz", BRIGHT, "Cattle"),
    CAETEE(-27, 1, 26, "Catee", CALCENDEN, "Ivory"),
    CALDEEN(-20, -6, 26, "Caldeen", CALCENDEN, "Wheat"),
    DAGUUI(-30, -1, 31, "Daguui", DUL_UL_DUR, "Ivory"),
    DAWUUJ(-37, 4, 33, "Dawuuj", DUL_UL_DUR, "Spice"),
    DOBRA_DYN(-20, -13, 33, "Dobra-Dyn", DUL_UL_DUR, "Spice"),
    EABANNET(-31, 10, 21, "Eabannet", EUSARCH, "Gold"),
    EAGLEHAWK(-38, 17, 21, "Eaglehawk", EUSARCH, "Pilgrims"),
    EASINGWERE(-44, 12, 32, "Easingwere", EUSARCH, "Cloth"),
    ELMSWORTH(-39, 12, 27, "Elmsworth", EUSARCH, "Gems"),
    GHASSOUF(1, 8, -9, "Ghassouf", GLYTH_GAMEL, "Fruit"),
    GLYNCLWYN(-8, 10, -2, "Glynclwyn", GLYTH_GAMEL, "Sheep"),
    GLYWEEN(0, 6, -6, "Glyween", GLYTH_GAMEL, "Wine"),
    ILIK_HERB(-31, 25, 6, "Ilik-Herb", IRON_HOLM, "Arms"),
    INHASS(-31, 23, 8, "Inhass", IRON_HOLM, "Armor"),
    IZYNDYL(-33, 21, 12, "Izyndyl", IRON_HOLM, "Armor", "Arms"),
    JANOSHAL(5, -14, 9, "Janoshal", Country.JANOSHAL, "Pilgrims"),
    JARLSSTADH(6, -19, 13, "Jarlsstadh", Country.JANOSHAL, "Pipeweed"),
    JOBABH(0, -12, 12, "Jobabh", Country.JANOSHAL, "Sheep"),
    JODARRE(9, -13, 4, "Jodarre", Country.JANOSHAL, "Pipeweed"),
    KENARE(-10, 36, -26, "Kenare", KOLAND, "Furs"),
    KILLAVARE(-22, 34, -12, "Killavare", KOLAND, "Fish"),
    KODANKYE(-17, 41, -24, "Kodankye", KOLAND, "Ivory"),
    KOLA(-14, 33, -19, "Kola", KOLAND, "Furs"),
    KUTNO(-8, 26, -18, "Kutno", KOLAND, "Lumber"),
    NORDKASSEL(8, 14, -22, "Nordkassel", NORTHERN_WASTE, "Dragons"),
    OCTOMARE(27, -28, 1, "Octomare", OLDE_WORLD, "Wands", "Cloth"),
    OKYABANTI(37, -26, -11, "Okybanti", OLDE_WORLD, "Potions"),
    OPAL_BEZIN(18, -27, 9, "Opal-Bezin", OLDE_WORLD, "Wands"),
    ORAIANDA(34, -30, -4, "Oraianda", OLDE_WORLD, "Potions"),
    OULDE_MORDEL(11, -43, 32, "Oulde-Mordel", OLDE_WORLD, "Cloth"),
    OYKJSORD(18, -32, 14, "Oykjsord", OLDE_WORLD, "Cloth"),
    OZU_ZARKH(12, -35, 23, "Ozu-Zarkh", OLDE_WORLD, "Spells", "Pilgrims"),
    OZU_OZU(17, -47, 30, "Ozu-Ozu", OLDE_WORLD, "Potions"),
    ORC_OBASYLN(-8, 5, 3, "Orc Obasyln", ORC_WASTES, "Gold"),
    ORC_OGONNYK(-24, 8, 16, "Orc Ogonnyk", ORC_WASTES),
    ORC_ORODRAK(0, 0, 0, "Orc Orodarak", ORC_WASTES),
    ORC_OKTZERRO(-16, 17, -1, "Orc Oktzerro", ORC_WASTES),
    ORC_ORODRAG(-19, 9, 10, "Orc Orodrag", ORC_WASTES),
    PAREKIS(17, -12, -5, "Parekis", PIRATE_ISLES, "Fish"),
    PENJOLD(28, -14, -14, "Penjold", PIRATE_ISLES, "Cattle"),
    PIGGNYTZ(29, -8, -21, "Piggnytz", PIRATE_ISLES, "Fish"),
    PINEWAERE(35, -19, -16, "Pinewaere", PIRATE_ISLES, "Lumber"),
    RAILLA(-2, 1, 1, "Railla", RAKHATZ, "Gems", "Iron"),
    REDWITTE(2, -2, 0, "Redwitte", RAKHATZ, "Steel"),
    RISIDAN(-6, -1, 7, "Risidan", RAKHATZ, "Gold", "Iron"),
    SAADAH(-8, -5, 13, "Saadah", SILVARRE, "Wine"),
    SBADEH(-7, -10, 17, "Sbadeh", SILVARRE, "Gems"),
    SHAYOBH(-11, -12, 23, "Shayobh", SILVARRE, "Fruit"),
    UDERNY(2, -6, 4, "Uderny", UNDERGROUND, "Ale"),
    ULOGGH(0, 2, -2, "Uloggh", UNDERGROUND, "Ale"),
    WEST_WYCKK(-2, -22, 24, "West Wyckk", WISLANDOR, "Lumber", "Jewelry"),
    WIKKEDDE(3, -23, 20, "Wikkedde", WISLANDOR),
    WYRRE(-3, -28, 31, "Wyrre", WISLANDOR, "Jewelry");

    private final HexagonCubeCoordinate cubeCoordinate;
    private final String name;
    private final Country country;
    private final List<Load> availableLoads;

    City(int x, int y, int z, String name, Country country, String... load) {
        cubeCoordinate = new HexagonCubeCoordinate(x, y, z);
        availableLoads = newArrayList();
        this.name = name;
        this.country = country;
        for (String s : load) {
            availableLoads.add(Load.getLoad(s));
        }
    }

    public static City getCity(String name) {
        for (City c : City.values()) {
            if (name.equalsIgnoreCase(c.getName())) {
                return c;
            }
        }
        throw new UnsupportedEnumException("City " + name + " is not supported");
    }

    public HexagonCubeCoordinate getCubeCoordinate() {
        return cubeCoordinate;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public List<Load> getAvailableLoads() {
        return availableLoads;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cubeCoordinate", cubeCoordinate)
                .add("name", name)
                .toString();
    }
}
