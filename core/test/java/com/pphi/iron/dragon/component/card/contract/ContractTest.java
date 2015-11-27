package com.pphi.iron.dragon.component.card.contract;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Load;
import com.pphi.iron.dragon.component.card.demand.Contract;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ContractTest {

    private Contract contract1;
    private Contract contract2;
    private Contract contract3;
    private Contract contract4;

    @BeforeTest
    public void testSetUp() {
        contract1 = new Contract("KOLA", Load.CATTLE, 10);
        contract2 = new Contract("KOLA", Load.CATTLE, 10);
        contract3 = new Contract("KOLA", Load.CATTLE, 10);
        contract4 = new Contract("KOLA", Load.CATTLE, 20);
    }

    @Test
    public void getLoadTest() {
        assertEquals(contract1.getLoad(), Load.CATTLE);
    }

    @Test
    public void getCityTest() {
        assertEquals(contract1.getCity(), City.KOLA);
    }

    @Test
    public void getPaymentTest() {
        assertEquals(contract1.getPayment(), 10);
    }

    @Test
    public void toStringTest() {
        assertNotNull(contract1.toString());
        assertTrue(contract1.toString().length() > 0);
    }

    @Test
    public void reflexiveTest() {
        assertTrue(contract1.equals(contract1));
    }

    @Test
    public void symmetryTest() {
        assertTrue(contract1.equals(contract2));
        assertTrue(contract2.equals(contract1));
    }

    @Test
    public void nullabilityTest() {
        assertFalse(contract1.equals(null));
    }

    @Test
    public void transitiveTest() {
        assertTrue(contract1.equals(contract2));
        assertTrue(contract2.equals(contract3));
        assertTrue(contract3.equals(contract1));
    }

    @Test
    public void nonEqualTest() {
        assertFalse(contract1.equals(contract4));
    }

    @Test
    public void hashCodeEqualTest() {
        assertEquals(contract1.hashCode(), contract2.hashCode());
    }

    @Test
    public void hashCodeNotEqualTest() {
        assertNotEquals(contract1.hashCode(), contract4.hashCode());
    }
}
