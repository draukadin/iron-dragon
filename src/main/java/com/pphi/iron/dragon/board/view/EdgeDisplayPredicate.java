package com.pphi.iron.dragon.board.view;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import org.apache.commons.collections15.Predicate;

/**
 * This class exist to force the edges to not be displayed.
 */
public class EdgeDisplayPredicate<V, E> implements Predicate<Context<Graph<V, E>, E>> {

    @Override
    public boolean evaluate(Context<Graph<V, E>, E> graphEContext) {
        return false;
    }
}
