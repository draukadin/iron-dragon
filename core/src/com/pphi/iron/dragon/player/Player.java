package com.pphi.iron.dragon.player;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Optional;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.iron.dragon.board.factory.MilePostFactory;
import com.pphi.iron.dragon.board.model.BoardGraph;
import com.pphi.iron.dragon.board.model.GameBoard;
import com.pphi.iron.dragon.board.model.MilePost;
import com.pphi.iron.dragon.component.card.demand.DemandCard;
import com.pphi.iron.dragon.component.card.demand.EventCard;
import com.pphi.iron.dragon.component.card.foreman.Foreman;
import com.pphi.iron.dragon.component.card.ship.ShipCard;
import com.pphi.iron.dragon.component.card.train.TrainCard;
import com.pphi.iron.dragon.component.deck.DemandDeck;
import com.pphi.iron.dragon.component.deck.ForemanDeck;
import com.pphi.iron.dragon.component.deck.ShipDeck;
import com.pphi.iron.dragon.component.deck.TrainDeck;
import com.pphi.iron.dragon.exceptions.DemandCardCapcityExceededException;
import com.pphi.iron.dragon.exceptions.InsufficentGoldException;

public class Player {

    private static final int STARTING_GOLD_AMOUNT = 60;
    private static final int PICK_FOREMAN_COST = 5;
    private static final int TRAIN_UPGRADE_COST = 10;

    private int gold;
    private TrainCard trainCard;
    private Optional<ShipCard> shipCard;
    private BoardGraph boardGraph;
    private HexagonCubeCoordinate location;
    private Foreman foreman;
    private List<DemandCard> demandCards;

    private DemandDeck demandDeck;
    private ForemanDeck foremanDeck;
    private GameBoard gameBoard;
    private MilePostFactory milePostFactory;
    private ShipDeck shipDeck;
    private TrainDeck trainDeck;

    public Player(DemandDeck demandDeck,
                  ForemanDeck foremanDeck,
                  GameBoard gameBoard,
                  MilePostFactory milePostFactory,
                  ShipDeck shipDeck,
                  TrainDeck trainDeck) {
        this. demandDeck = demandDeck;
        this.foremanDeck = foremanDeck;
        this. gameBoard = gameBoard;
        this.milePostFactory = milePostFactory;
        this.shipDeck = shipDeck;
        this.trainDeck = trainDeck;
        gold = STARTING_GOLD_AMOUNT;
        trainCard = trainDeck.getStartingTrain();
        shipCard = Optional.absent();
        boardGraph = new BoardGraph();
        demandCards = newArrayList();
        location = null;
    }

    public void obtainDemandCard() {
        if (demandCards.size() == 3) {
            throw new DemandCardCapcityExceededException();
        }
        DemandCard demandCard = demandDeck.getRandomCard();
        if (demandCard instanceof EventCard) {
            //TODO enable event action(s)
        } else {
            demandCards.add(demandCard);
        }
    }

    public void drawForemanFromDeck() {
        hireForeman(foremanDeck.getRandomCard());
    }

    public void drawFormanFromDiscardPile() {
        hireForeman(foremanDeck.getCardFromDiscardPile());
    }

    public List<Foreman> showPlayerEntireForemanDeck() {
        if (gold < PICK_FOREMAN_COST) {
            throw new InsufficentGoldException(PICK_FOREMAN_COST, gold);
        }
        gold -= PICK_FOREMAN_COST;
        return foremanDeck.getEntireDeck();
    }

    public void hireForemanFromDeck(int index) {
        hireForeman(foremanDeck.chooseCardFromDeck(index));
    }

    private void hireForeman(Foreman foreman) {
        this.foreman = foreman;
    }

    public void placeTrainOnBoard(HexagonCubeCoordinate location) {
        Collection<MilePost> milePosts = milePostFactory.createMilePost(location);
        if (milePosts.size() != 1) {
            throw new IllegalStateException("That location is not a valid milepost");
        }
        MilePost milePost = milePosts.iterator().next();
        if (milePost.getCityMilePost().isPresent()) {
            this.location = location;
        } else {
            throw new IllegalStateException("The starting location must be a city");
        }
    }

    public void upgradeTrain(String name) {
        if (gold < TRAIN_UPGRADE_COST) {
            throw new InsufficentGoldException(TRAIN_UPGRADE_COST, gold);
        }
        trainCard = trainDeck.upgradeTrain(trainCard, name);
        gold -= TRAIN_UPGRADE_COST;
    }
}
