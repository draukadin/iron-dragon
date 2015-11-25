package com.pphi.iron.dragon.board.model;

import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import edu.uci.ics.jung.graph.SortedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class BoardGraph extends SortedSparseMultigraph<MilePost, MilePostLink> {

    private static final Set<MilePost> EMPTY_NODES = ImmutableSet.of();
    private static final Set<MilePostLink> EMPTY_EDGES = ImmutableSet.of();

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
    public Collection<MilePostLink> getIncidentEdges(MilePost vertex) {
        Collection<MilePostLink> incidentEdges = super.getIncidentEdges(vertex);
        if (incidentEdges == null) {
            incidentEdges = EMPTY_EDGES;
        }
        return incidentEdges;
    }

    @Override
    public Collection<MilePostLink> getInEdges(MilePost vertex) {
        Collection<MilePostLink> inEdges = super.getInEdges(vertex);
        if (inEdges == null) {
            inEdges = EMPTY_EDGES;
        }
        return inEdges;
    }

    @Override
    public Collection<MilePostLink> getOutEdges(MilePost vertex) {
        Collection<MilePostLink> outEdges = super.getOutEdges(vertex);
        if (outEdges == null) {
            outEdges = EMPTY_EDGES;
        }
        return outEdges;
    }

    @Override
    @JsonIgnore
    public Collection<MilePost> getVertices() {
        return super.getVertices();
    }

    @Override
    @JsonIgnore
    public int getEdgeCount() {
        return super.getEdgeCount();
    }

    @Override
    @JsonIgnore
    public int getVertexCount() {
        return super.getVertexCount();
    }

    @Override
    @JsonIgnore
    public EdgeType getDefaultEdgeType() {
        return super.getDefaultEdgeType();
    }
}
