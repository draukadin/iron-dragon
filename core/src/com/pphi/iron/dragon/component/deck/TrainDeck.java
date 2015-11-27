package com.pphi.iron.dragon.component.deck;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pphi.iron.dragon.component.card.train.TrainCard;
import com.pphi.iron.dragon.exceptions.TrainTypeNotAvailableException;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class TrainDeck extends Deck<TrainCard> {

    private static final String STARTING_TRAIN = "Teapot";

    private final Multimap<String, TrainCard> trains;

    public TrainDeck() {
        super();
        trains = HashMultimap.create();
    }

    private void init() {
        if (deck.isEmpty()) {
            throw new IllegalStateException("There are no trains in the deck");
        }
        for (TrainCard trainCard : deck) {
            String name = trainCard.getName();
            trains.put(name, trainCard);
        }
    }

    public TrainCard upgradeTrain(TrainCard oldTrainCard, String newTrainName) {
        if (trains.isEmpty()) {
            init();
        }
        Collection<TrainCard> nextLevelTrainCards = trains.get(newTrainName);
        if (nextLevelTrainCards.isEmpty()) {
            throw new TrainTypeNotAvailableException();
        } else {
            TrainCard trainCard = nextLevelTrainCards.iterator().next();
            String oldTrainName = oldTrainCard.getName();
            trains.put(oldTrainName, oldTrainCard);
            trains.remove(newTrainName, trainCard);
            return trainCard;
        }
    }

    @JsonIgnore
    public TrainCard getStartingTrain() {
        if (trains.isEmpty()) {
            init();
        }
        Collection<TrainCard> startingLevelTrainCards = trains.get(STARTING_TRAIN);
        if (startingLevelTrainCards.isEmpty()) {
            throw new IllegalStateException("Too many players or the deck was not correctly created");
        } else {
            TrainCard trainCard = startingLevelTrainCards.iterator().next();
            trains.remove(STARTING_TRAIN, trainCard);
            return trainCard;
        }
    }

    @Override
    public void discard(TrainCard card) { }

    @Override
    @JsonIgnore
    public List<TrainCard> combineDiscardPileWithDeck() {
        return deck;
    }

    @Override
    @JsonIgnore
    public TrainCard getCardFromDiscardPile() {
        throw new UnsupportedOperationException("Train deck does not have a discard pile");
    }

    @Override
    @JsonIgnore
    public DiscardPile<TrainCard> getDiscardPile() {
        return new DiscardPile<>();
    }
}
