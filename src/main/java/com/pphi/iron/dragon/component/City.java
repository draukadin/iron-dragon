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
import static com.pphi.iron.dragon.component.TerrainType.MAJOR;
import static com.pphi.iron.dragon.component.TerrainType.MEDIUM;
import static com.pphi.iron.dragon.component.TerrainType.SMALL;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.pphi.iron.dragon.exceptions.UnsupportedEnumException;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;

public enum City {
    BLUEFELD(21, -8, -13, "Bluefeld", MAJOR, BRIGHT, "Hops"),
    BORGOFORT(9, 3, -12, "Borgofort", SMALL, BRIGHT, "Lumber"),
    BREMMNER(16, -5, -11, "Bremmner", MEDIUM, BRIGHT, "Steel"),
    BREVANDES(8, -5, -3, "Brevandes", SMALL, BRIGHT, "Wheat"),
    BULATZ(17, -8, -9, "Bulatz", MEDIUM, BRIGHT, "Cattle"),
    CAETEE(-27, 1, 26, "Catee", SMALL, CALCENDEN, "Ivory"),
    CALDEEN(-20, -6, 26, "Caldeen", MEDIUM, CALCENDEN, "Wheat"),
    DAGUUI(-30, -1, 31, "Daguui", SMALL, DUL_UL_DUR, "Ivory"),
    DAWUUJ(-37, 4, 33, "Dawuuj", SMALL, DUL_UL_DUR, "Spice"),
    DOBRA_DYN(-20, -13, 33, "Dobra-Dyn", SMALL, DUL_UL_DUR, "Spice"),
    EABANNET(-31, 10, 21, "Eabannet", SMALL, EUSARCH, "Gold"),
    EAGLEHAWK(-38, 17, 21, "Eaglehawk", MAJOR, EUSARCH, "Pilgrims"),
    EASINGWERE(-44, 13, 31, "Easingwere", SMALL, EUSARCH, "Cloth"),
    ELMSWORTH(-39, 12, 27, "Elmsworth", MEDIUM, EUSARCH, "Gems"),
    GHASSOUF(1, 8, -9, "Ghassouf", SMALL, GLYTH_GAMEL, "Fruit"),
    GLYNCLWYN(-8, 10, -2, "Glynclwyn", SMALL, GLYTH_GAMEL, "Sheep"),
    GLYWEEN(0, 6, -6, "Glyween", MEDIUM, GLYTH_GAMEL, "Wine"),
    ILIK_HERB(-31, 25, 6, "Ilik-Herb", MEDIUM, IRON_HOLM, "Arms"),
    INHASS(-31, 23, 8, "Inhass", SMALL, IRON_HOLM, "Armor"),
    IZYNDYL(-33, 21, 12, "Izyndyl", MEDIUM, IRON_HOLM, "Armor", "Arms"),
    JANOSHAL(5, -14, 9, "Janoshal", MEDIUM, Country.JANOSHAL, "Pilgrims"),
    JARLSSTADH(6, -19, 13, "Jarlsstadh", MEDIUM, Country.JANOSHAL, "Pipeweed"),
    JOBABH(0, -12, 12, "Jobabh", SMALL, Country.JANOSHAL, "Sheep"),
    JODARRE(9, -13, 4, "Jodarre", MEDIUM, Country.JANOSHAL, "Pipeweed"),
    KENARE(-10, 36, -26, "Kenare", SMALL, KOLAND, "Furs"),
    KILLAVARE(-22, 34, -12, "Killavare", SMALL, KOLAND, "Fish"),
    KODANKYE(-17, 41, -24, "Kodankye", SMALL, KOLAND, "Ivory"),
    KOLA(-14, 33, -19, "Kola", MAJOR, KOLAND, "Furs"),
    KUTNO(-8, 26, -18, "Kutno", SMALL, KOLAND, "Lumber"),
    NORDKASSEL(8, 14, -22, "Nordkassel", SMALL, NORTHERN_WASTE, "Dragons"),
    OCTOMARE(27, -28, 1, "Octomare", MAJOR, OLDE_WORLD, "Wands", "Cloth"),
    OKYABANTI(37, -26, -11, "Okybanti", SMALL, OLDE_WORLD, "Potions"),
    OPAL_BEZIN(18, -27, 9, "Opal-Bezin", MEDIUM, OLDE_WORLD, "Wands"),
    ORAIANDA(34, -30, -4, "Oraianda", SMALL, OLDE_WORLD, "Potions"),
    OULDE_MORDEL(11, -43, 32, "Oulde-Mordel", SMALL, OLDE_WORLD, "Cloth"),
    OYKJSORD(18, -32, 14, "Oykjsord", MEDIUM, OLDE_WORLD, "Cloth"),
    OZU_ZARKH(12, -35, 23, "Ozu-Zarkh", MAJOR, OLDE_WORLD, "Spells", "Pilgrims"),
    OZU_OZU(17, -47, 30, "Ozu-Ozu", SMALL, OLDE_WORLD, "Potions"),
    ORC_OBASYLN(-8, 5, 3, "Orc Obasyln", MEDIUM, ORC_WASTES, "Gold"),
    ORC_OGONNYK(-24, 8, 16, "Orc Ogonnyk", SMALL, ORC_WASTES),
    ORC_OKTZERRO(-16, 17, -1, "Orc Oktzerro", SMALL, ORC_WASTES),
    ORC_ORODRAG(-19, 9, 10, "Orc Orodrag", SMALL, ORC_WASTES),
    PAREKIS(17, -12, -5, "Parekis", MEDIUM, PIRATE_ISLES, "Fish"),
    PENJOLD(28, -14, -14, "Penjold", SMALL, PIRATE_ISLES, "Cattle"),
    PIGGNYTZ(29, -8, -21, "Piggnytz", MEDIUM, PIRATE_ISLES, "Fish"),
    PINEWAERE(35, -19, -16, "Pinewaere", SMALL, PIRATE_ISLES, "Lumber"),
    RAILLA(-2, 1, 1, "Railla", MAJOR, RAKHATZ, "Gems", "Iron"),
    REDWITTE(2, -2, 0, "Redwitte", MEDIUM, RAKHATZ, "Steel"),
    RISIDAN(-6, -1, 7, "Risidan", SMALL, RAKHATZ, "Gold", "Iron"),
    SAADAH(-8, -5, 13, "Saadah", MEDIUM, SILVARRE, "Wine"),
    SBADEH(-7, -10, 17, "Sbadeh", SMALL, SILVARRE, "Gems"),
    SHAYOBH(-11, -12, 23, "Shayobh", SMALL, SILVARRE, "Fruit"),
    UDERYN(-14, 8, 6, "Uderyn", SMALL, UNDERGROUND, "Ale"),
    ULOGGH(-16, 16, 0, "Uloggh", MAJOR, UNDERGROUND, "Ale"),
    WEST_WYCKK(-2, -22, 24, "West Wyckk", MEDIUM, WISLANDOR, "Lumber", "Jewelry"),
    WIKKEDDE(3, -23, 20, "Wikkedde", MAJOR, WISLANDOR),
    WYRRE(-3, -28, 31, "Wyrre", SMALL, WISLANDOR, "Jewelry");

    private final HexagonCubeCoordinate cubeCoordinate;
    private final String name;
    private final Country country;
    private final TerrainType terrainType;
    private final List<Load> availableLoads;

    City(int x, int y, int z, String name, TerrainType terrainType, Country country, String... load) {
        cubeCoordinate = new HexagonCubeCoordinate(x, y, z);
        availableLoads = newArrayList();
        this.name = name;
        this.country = country;
        this.terrainType = terrainType;
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

    public TerrainType getTerrainType() {
        return terrainType;
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
