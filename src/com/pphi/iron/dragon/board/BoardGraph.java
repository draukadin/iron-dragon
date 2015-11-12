package com.pphi.iron.dragon.board;

import com.google.common.collect.ImmutableSet;
import edu.uci.ics.jung.graph.SortedSparseMultigraph;

import java.util.Collection;
import java.util.Set;

public class BoardGraph extends SortedSparseMultigraph<MilePost, Link> {

    private static final Set<MilePost> EMPTY_NODES = ImmutableSet.of();
    private static final Set<Link> EMPTY_EDGES = ImmutableSet.of();

    @Override
    public Collection<MilePost> getPredecessors(MilePost vertex) {
        Collection<MilePost> predecessor = super.getPredecessors(vertex);
        if (predecessor == null) {
            predecessor = EMPTY_NODES;
        }
        return predecessor;
    }

    @Override
    public Collection<MilePost> getSuccessors(MilePost vertex) {
        Collection<MilePost> successors = super.getSuccessors(vertex);
        if (successors == null) {
            successors = EMPTY_NODES;
        }
        return successors;
    }

    @Override
    public Collection<MilePost> getNeighbors(MilePost vertex) {
        Collection<MilePost> neighbors = super.getNeighbors(vertex);
        if (neighbors == null) {
            neighbors = EMPTY_NODES;
        }
        return neighbors;
    }

    @Override
    public Collection<Link> getIncidentEdges(MilePost vertex) {
        Collection<Link> incidentEdges = super.getIncidentEdges(vertex);
        if (incidentEdges == null) {
            incidentEdges = EMPTY_EDGES;
        }
        return incidentEdges;
    }

    @Override
    public Collection<Link> getInEdges(MilePost vertex) {
        Collection<Link> inEdges = super.getInEdges(vertex);
        if (inEdges == null) {
            inEdges = EMPTY_EDGES;
        }
        return inEdges;
    }

    @Override
    public Collection<Link> getOutEdges(MilePost vertex) {
        Collection<Link> outEdges = super.getOutEdges(vertex);
        if (outEdges == null) {
            outEdges = EMPTY_EDGES;
        }
        return outEdges;
    }
}
