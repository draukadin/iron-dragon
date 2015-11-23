package com.pphi.iron.dragon.component.card.train;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.exceptions.FullTrainException;
import com.pphi.iron.dragon.exceptions.LoadNotOnTrainException;
import com.pphi.iron.dragon.component.Load;
import com.pphi.iron.dragon.component.card.demand.Contract;
import com.pphi.iron.dragon.component.card.demand.ContractCard;
import org.testng.annotations.Test;

public class TrainCardTest {

    private TrainCard trainCard1 = new TrainCard("Iron Dragon", new TrainsSpecifications(5, 16, 3));
    private TrainCard trainCard2 = new TrainCard("Iron Dragon", new TrainsSpecifications(5, 16, 3));
    private TrainCard trainCard3 = new TrainCard("Iron Dragon", new TrainsSpecifications(5, 16, 3));
    private TrainCard trainCard4 = new TrainCard("Clay Dragon", new TrainsSpecifications(1, 10, 3));

    @Test
    public void getNameTest() {
        assertEquals(trainCard1.getName(), "Iron Dragon");
    }

    @Test
    public void getTrainClassTest() {
        assertEquals(trainCard1.getTrainsSpecifications(), new TrainsSpecifications(5, 16, 3));
    }

    @Test(expectedExceptions = FullTrainException.class)
    public void pickupMoreThenMax() {
        TrainCard trainCard = new TrainCard("Iron Dragon", new TrainsSpecifications(5, 16, 3));
        trainCard.pickUpLoad(Load.getLoad("ale"));
        assertEquals(trainCard.getCurrentCapacity(), 2);
        trainCard.pickUpLoad(Load.getLoad("ale"));
        assertEquals(trainCard.getCurrentCapacity(), 1);
        trainCard.pickUpLoad(Load.getLoad("ale"));
        assertEquals(trainCard.getCurrentCapacity(), 0);
        trainCard.pickUpLoad(Load.getLoad("ale"));
    }

    @Test
    public void pickupLoadTest() {
        TrainCard trainCard = new TrainCard("Iron Dragon", new TrainsSpecifications(5, 16, 3));
        trainCard.pickUpLoad(Load.getLoad("ale"));
        assertEquals(trainCard.getCurrentCapacity(), 2);
    }

    @Test(expectedExceptions = LoadNotOnTrainException.class)
    public void deliverLoadTrainDoesntHaveTest() {
        Contract contract1 = new Contract(City.KOLA, Load.ALE, 10);
        Contract contract2 = new Contract(City.KOLA, Load.ARMS, 10);
        Contract contract3 = new Contract(City.KOLA, Load.SPELLS, 10);
        ContractCard contractCard = new ContractCard(1, ImmutableList.of(contract1, contract2, contract3));
        TrainCard trainCard = new TrainCard("Iron Dragon", new TrainsSpecifications(5, 16, 3));
        trainCard.deliverLoad(contractCard, 0);
    }

    @Test
    public void deliverLoadTest() {
        Contract contract1 = new Contract(City.KOLA, Load.ALE, 10);
        Contract contract2 = new Contract(City.KOLA, Load.ARMS, 10);
        Contract contract3 = new Contract(City.KOLA, Load.SPELLS, 10);
        ContractCard contractCard = new ContractCard(1, ImmutableList.of(contract1, contract2, contract3));
        TrainCard trainCard = new TrainCard("Iron Dragon", new TrainsSpecifications(5, 16, 3));
        trainCard.pickUpLoad(Load.getLoad("ale"));
        Load load = trainCard.deliverLoad(contractCard, 0);
        assertEquals(load, Load.ALE);
        assertEquals(trainCard.getCurrentCapacity(), 3);
    }

    @Test
    public void toStringTest() {
        assertNotNull(trainCard1.toString());
        assertTrue(trainCard1.toString().length() > 0);
    }

    @Test
    public void reflexiveTest() {
        assertTrue(trainCard1.equals(trainCard1));
    }

    @Test
    public void symmetryTest() {
        assertTrue(trainCard1.equals(trainCard2));
        assertTrue(trainCard2.equals(trainCard1));
    }

    @Test
    public void nullabilityTest() {
        assertFalse(trainCard1.equals(null));
    }

    @Test
    public void transitiveTest() {
        assertTrue(trainCard1.equals(trainCard2));
        assertTrue(trainCard2.equals(trainCard3));
        assertTrue(trainCard3.equals(trainCard1));
    }

    @Test
    public void nonEqualTest() {
        assertFalse(trainCard1.equals(trainCard4));
    }

    @Test
    public void hashCodeEqualTest() {
        assertEquals(trainCard1.hashCode(), trainCard2.hashCode());
    }

    @Test
    public void hashCodeNotEqualTest() {
        assertNotEquals(trainCard1.hashCode(), trainCard4.hashCode());
    }
}
