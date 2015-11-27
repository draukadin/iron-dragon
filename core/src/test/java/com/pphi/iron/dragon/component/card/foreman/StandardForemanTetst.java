package com.pphi.iron.dragon.component.card.foreman;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.google.common.base.Optional;
import com.pphi.iron.dragon.component.TerrainType;
import org.testng.annotations.Test;

public class StandardForemanTetst {

    private Foreman standardForeman1 = new StandardForeman(1, "ELF_HALF_ELF", "Percy", "");
    private Foreman standardForeman2 = new StandardForeman(1, "ELF_HALF_ELF", "Percy", "");
    private Foreman standardForeman3 = new StandardForeman(1, "ELF_HALF_ELF", "Percy", "");
    private Foreman standardForeman4 = new StandardForeman(2, "ELF_HALF_ELF", "Marcy", "");
    private Foreman foreman2 = new StandardForeman(2, "DWARF", "Joe", "");
    private Foreman foreman3 = new StandardForeman(3, "CATMAN", "Lucy", "");
    private Foreman foreman4 = new WeeFolkForeman(4, "WEE_FOLK", "I'm on a boat", "");

    @Test
    public void getForestMilePostCostTest() {
        assertEquals(standardForeman1.getMilePostCost(TerrainType.FOREST), 1);
        assertEquals(standardForeman1.getMilePostCost(TerrainType.MOUNTAIN), 2);
        assertEquals(foreman2.getMilePostCost(TerrainType.MOUNTAIN), 1);
        assertEquals(foreman2.getMilePostCost(TerrainType.FOREST), 2);
        assertEquals(foreman3.getMilePostCost(TerrainType.JUNGLE), 1);
        assertEquals(foreman3.getMilePostCost(TerrainType.FOREST), 2);
        assertEquals(foreman4.getMilePostCost(TerrainType.JUNGLE), 3);
        assertEquals(foreman4.getMilePostCost(TerrainType.ALPINE), 5);
    }

    @Test
    public void getMountainMilePostCostTest() {
        assertEquals(standardForeman1.getMilePostCost(TerrainType.MOUNTAIN), 2);
    }

    @Test
    public void getJungleMilePostCostTest() {
        StandardForeman standardForeman = (StandardForeman) standardForeman1;
        assertEquals(standardForeman.getMilePostCost(TerrainType.JUNGLE), 3);
    }

    @Test
    public void getAlpineMilePostCostTest() {
        assertEquals(standardForeman1.getMilePostCost(TerrainType.ALPINE), 5);
    }

    @Test
    public void getTerrainTypeTest() {
        assertEquals(standardForeman1.getTerrainType().get(), TerrainType.FOREST);
        assertEquals(foreman2.getTerrainType().get(), TerrainType.MOUNTAIN);
        assertEquals(foreman3.getTerrainType().get(), TerrainType.JUNGLE);
        assertEquals(foreman4.getTerrainType(), Optional.absent());
    }

    @Test
    public void toStringTest() {
        assertNotNull(standardForeman1.toString());
        assertTrue(standardForeman1.toString().length() > 0);
    }

    @Test
    public void reflexiveTest() {
        assertTrue(standardForeman1.equals(standardForeman1));
    }

    @Test
    public void symmetryTest() {
        assertTrue(standardForeman1.equals(standardForeman2));
        assertTrue(standardForeman2.equals(standardForeman1));
    }

    @Test
    public void nullabilityTest() {
        assertFalse(standardForeman1.equals(null));
    }

    @Test
    public void transitiveTest() {
        assertTrue(standardForeman1.equals(standardForeman2));
        assertTrue(standardForeman2.equals(standardForeman3));
        assertTrue(standardForeman3.equals(standardForeman1));
    }

    @Test
    public void nonEqualTest() {
        assertFalse(standardForeman1.equals(standardForeman4));
    }

    @Test
    public void hashCodeEqualTest() {
        assertEquals(standardForeman1.hashCode(), standardForeman2.hashCode());
    }

    @Test
    public void hashCodeNotEqualTest() {
        int hashCode1 = standardForeman1.hashCode();
        int hashCode2 = standardForeman4.hashCode();
        assertNotEquals(hashCode1, hashCode2);
    }
}
