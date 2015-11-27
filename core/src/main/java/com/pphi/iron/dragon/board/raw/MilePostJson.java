package com.pphi.iron.dragon.board.raw;

import com.pphi.iron.dragon.component.Country;
import edu.uci.ics.jung.graph.util.Pair;

public class MilePostJson {

    private Country country;
    private Pair<Integer> pair;

    public MilePostJson(Country country, Pair<Integer> pair) {
        this.country = country;
        this.pair = pair;
    }

    public Country getCountry() {
        return country;
    }

    public Pair<Integer> getPair() {
        return pair;
    }
}
