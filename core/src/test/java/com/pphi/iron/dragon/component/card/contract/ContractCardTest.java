package com.pphi.iron.dragon.component.card.contract;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Load;
import com.pphi.iron.dragon.component.card.demand.Contract;
import com.pphi.iron.dragon.component.card.demand.ContractCard;
import org.testng.annotations.Test;

public class ContractCardTest {

    Contract contract1 = new Contract(City.KOLA, Load.CATTLE, 10);
    Contract contract2 = new Contract(City.KOLA, Load.CATTLE, 10);
    Contract contract3 = new Contract(City.KOLA, Load.CATTLE, 10);
    Contract contract4 = new Contract(City.KOLA, Load.CATTLE, 100);

    ContractCard contractCard1 = new ContractCard(7, ImmutableList.of(contract1, contract2, contract3));
    ContractCard contractCard2 = new ContractCard(7, ImmutableList.of(contract1, contract2, contract3));
    ContractCard contractCard3 = new ContractCard(7, ImmutableList.of(contract1, contract2, contract3));
    ContractCard contractCard4 = new ContractCard(8, ImmutableList.of(contract1, contract2, contract4));

    @Test
    public void getContractCardNumberTest() {
        assertEquals(contractCard1.getCardNumber(), 7);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getContractByInvalidNumberTest() {
        assertEquals(contractCard1.getContractByNumber(-1), contract3);
    }

    @Test
    public void getContractByNumberTest() {
        assertEquals(contractCard1.getContractByNumber(2), contract3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void illegalArgumentTestToMany() {
        new ContractCard(7, ImmutableList.of(contract1, contract2, contract3, contract4));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void illegalArgumentTestNotEnough() {
        new ContractCard(7, ImmutableList.of(contract3, contract4));
    }

    @Test
    public void toStringTest() {
        assertNotNull(contractCard1.toString());
        assertTrue(contractCard1.toString().length() > 0);
    }

    @Test
    public void reflexiveTest() {
        assertTrue(contractCard1.equals(contractCard1));
    }

    @Test
    public void symmetryTest() {
        assertTrue(contractCard1.equals(contractCard2));
        assertTrue(contractCard2.equals(contractCard1));
    }

    @Test
    public void nullabilityTest() {
        assertFalse(contractCard1.equals(null));
    }

    @Test
    public void transitiveTest() {
        assertTrue(contractCard1.equals(contractCard2));
        assertTrue(contractCard2.equals(contractCard3));
        assertTrue(contractCard3.equals(contractCard1));
    }

    @Test
    public void nonEqualTest() {
        assertFalse(contractCard1.equals(contractCard4));
    }

    @Test
    public void hashCodeEqualTest() {
        assertEquals(contractCard1.hashCode(), contractCard2.hashCode());
    }

    @Test
    public void hashCodeNotEqualTest() {
        assertNotEquals(contractCard1.hashCode(), contractCard4.hashCode());
    }
}
