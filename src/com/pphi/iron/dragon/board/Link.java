package com.pphi.iron.dragon.board;

import com.google.common.collect.ComparisonChain;

public class Link implements Comparable<Link> {

    @Override
    public int compareTo(Link o) {
        return ComparisonChain
                .start()
                .result();
    }
}
