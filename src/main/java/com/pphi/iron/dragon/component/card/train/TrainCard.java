package com.pphi.iron.dragon.component.card.train;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.pphi.iron.dragon.component.Load;
import com.pphi.iron.dragon.component.card.demand.ContractCard;
import com.pphi.iron.dragon.exceptions.FullTrainException;
import com.pphi.iron.dragon.exceptions.LoadNotOnTrainException;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class TrainCard {

    private final String uuid;
    private final String name;
    private final TrainsSpecifications trainsSpecifications;
    private final List<Load> loadManifest;

    @JsonCreator
    public TrainCard(
            @JsonProperty("name") String name,
            @JsonProperty("trainSpecifications") TrainsSpecifications trainsSpecifications) {
        uuid = UUID.randomUUID().toString();
        this.name = name;
        this.trainsSpecifications = trainsSpecifications;
        loadManifest = newArrayList();
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonIgnore
    public int getCurrentCapacity() {
        return trainsSpecifications.getCapacity() - loadManifest.size();
    }

    @JsonProperty("trainSpecifications")
    public TrainsSpecifications getTrainsSpecifications() {
        return trainsSpecifications;
    }

    public void pickUpLoad(Load load) {
        if (loadManifest.size() < trainsSpecifications.getCapacity()) {
            loadManifest.add(load);
        } else {
            throw new FullTrainException();
        }
    }

    public Load deliverLoad(ContractCard contractCard, int contractNumber) {
        Load load = contractCard.getContractByNumber(contractNumber).getLoad();
        if (loadManifest.contains(load)) {
            loadManifest.remove(load);
        } else {
            throw new LoadNotOnTrainException(load + " is not on board your train.");
        }
        return load;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("trainClass", trainsSpecifications)
                .add("loadManifest", loadManifest)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, name, trainsSpecifications, loadManifest);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TrainCard other = (TrainCard) obj;
        return Objects.equal(this.uuid, other.uuid)
                && Objects.equal(this.name, other.name)
                && Objects.equal(this.trainsSpecifications, other.trainsSpecifications)
                && Objects.equal(this.loadManifest, other.loadManifest);
    }
}
