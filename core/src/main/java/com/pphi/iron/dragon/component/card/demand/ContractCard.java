package com.pphi.iron.dragon.component.card.demand;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class ContractCard extends DemandCard {

    private final List<Contract> contracts;

    @JsonCreator
    public ContractCard(
            @JsonProperty("cardNumber") int cardNumber,
            @JsonProperty("contracts") List<Contract> contracts) {
        super(cardNumber);
        if (contracts.size() != 3 ){
            throw new IllegalArgumentException("Attempting to put to many or too few contracts on the card");
        }
        this.contracts = contracts;
    }

    @JsonProperty("cardNumber")
    public int getCardNumber() {
        return cardNumber;
    }

    @JsonProperty("contracts")
    public List<Contract> getContracts() {
        return contracts;
    }

    public Contract getContractByNumber(int number) {
        checkArgument(number >= 0 && number < 3, "Must be between 0 and 2");
        return contracts.get(number);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cardNumber", cardNumber)
                .add("contracts", contracts)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cardNumber, contracts);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ContractCard other = (ContractCard) obj;
        return Objects.equal(this.cardNumber, other.cardNumber)
                && Objects.equal(this.contracts, other.contracts);
    }
}
