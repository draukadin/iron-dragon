package com.pphi.iron.dragon.component.card.train;

import static org.testng.Assert.assertEquals;

import com.google.common.collect.ImmutableList;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Load;
import com.pphi.iron.dragon.component.card.demand.Contract;
import com.pphi.iron.dragon.component.card.demand.ContractCard;
import com.pphi.iron.dragon.exceptions.FullTrainException;
import com.pphi.iron.dragon.exceptions.LoadNotOnTrainException;
import org.testng.annotations.Test;

public class TrainCardTest {

    private TrainCard trainCard1 = new TrainCard("Iron Dragon", new TrainsSpecifications(5, 16, 3));

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
}
