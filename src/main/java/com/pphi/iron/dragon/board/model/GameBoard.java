package com.pphi.iron.dragon.board.model;

import static com.pphi.iron.dragon.component.Country.UNDERGROUND;
import static com.pphi.iron.dragon.component.TerrainType.UNDERGROUND_ENTRANCE;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.hexagon.neighbors.PointyTopCubeNeighbors;
import com.pphi.iron.dragon.board.factory.MilePostFactory;
import com.pphi.iron.dragon.board.factory.MilePostLinkFactory;
import com.pphi.iron.dragon.component.City;
import com.pphi.iron.dragon.component.Country;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class GameBoard {

    private static final HexagonCubeCoordinate MAIN_MAP_SEED = new HexagonCubeCoordinate();
    private static final HexagonCubeCoordinate SIDE_MAP_SEED = new HexagonCubeCoordinate(-36, 36, 0);

    private Graph<MilePost, MilePostLink> boardGraph;

    public GameBoard() throws IOException {
        boardGraph = new BoardGraph();
        MilePostFactory milePostFactory = new MilePostFactory();
        MilePostLinkFactory milePostLinkFactory = new MilePostLinkFactory();
        Queue<MilePost> queue = new LinkedList<>();
        queue.addAll(milePostFactory.createMilePost(MAIN_MAP_SEED));
        queue.addAll(milePostFactory.createMilePost(SIDE_MAP_SEED));
        while (!queue.isEmpty()) {
            MilePost current = queue.poll();
            for (HexagonCubeCoordinate neighbor : PointyTopCubeNeighbors.getNeighbors(current.getCubeCoordinate())) {
                if (milePostFactory.isCoordinateValid(neighbor)) {
                    Collection<MilePost> mileposts = milePostFactory.createMilePost(neighbor);
                    for (MilePost neighborMilePost : mileposts) {
                        if (!boardGraph.containsVertex(neighborMilePost)) {
                            queue.add(neighborMilePost);
                        }
                        addToGraph(current, neighborMilePost, milePostLinkFactory);
                        if (neighborMilePost.getTerrainType().equals(UNDERGROUND_ENTRANCE)) {
                            addUnderGroundTunnelLink(neighborMilePost, milePostFactory, milePostLinkFactory);
                        }
                    }
                }
            }
        }
        createMagicBridge(City.WIKKEDDE, City.OZU_ZARKH, milePostFactory, milePostLinkFactory);
        //Takes ~50 seconds to build map from serialized JSON and ~30 seconds to build from raw data so not much point
        //in serializing the game board right now
        //JacksonUtil.serializeToFile(this, COMPRESSED_JSON);
    }

    @JsonCreator
    public GameBoard(
            @JsonProperty("boardGraph")
            Graph<MilePost, MilePostLink> boardGraph) {
        this.boardGraph = boardGraph;
    }

    public void createMagicBridge(City wikkedde, City ozuZarkh, MilePostFactory milePostFactory,
            MilePostLinkFactory milePostLinkFactory) {
        milePostLinkFactory.createMagicConnection(boardGraph, wikkedde, ozuZarkh, milePostFactory);
    }

    private void addUnderGroundTunnelLink(MilePost nearSide, MilePostFactory milePostFactory,
            MilePostLinkFactory milePostLinkFactory) {
        HexagonCubeCoordinate farSideCoord;
        if (nearSide.getCountry().equals(UNDERGROUND)) {
            farSideCoord = milePostFactory.reverseShiftXAxisCoordinate(nearSide.getCubeCoordinate());
        } else {
            farSideCoord = milePostFactory.shiftXAxisCoordinate(nearSide.getCubeCoordinate());
        }
        MilePost farSide = milePostFactory.createMilePost(farSideCoord).iterator().next();
        add(nearSide, farSide, milePostLinkFactory);
    }

    private void addToGraph(MilePost current, MilePost neighbor, MilePostLinkFactory milePostLinkFactory) {
        Country currentCountry = current.getCountry();
        Country neighborCountry = neighbor.getCountry();
        if (currentCountry.equals(UNDERGROUND) && neighborCountry.equals(UNDERGROUND)) {
            add(current, neighbor, milePostLinkFactory);
        } else if (!currentCountry.equals(UNDERGROUND) && !neighborCountry.equals(UNDERGROUND)) {
            add(current, neighbor, milePostLinkFactory);
        }
    }

    private void add(MilePost current, MilePost neighbor, MilePostLinkFactory milePostLinkFactory) {
        Optional<MilePostLink> optionalMilePostLink = milePostLinkFactory.createLink(current, neighbor);
        if (optionalMilePostLink.isPresent()) {
            MilePostLink milePostLink = optionalMilePostLink.get();
            if (!boardGraph.containsEdge(milePostLink)) {
                boardGraph.addEdge(milePostLink, current, neighbor, EdgeType.UNDIRECTED);
            }
        }
    }

    public Graph<MilePost, MilePostLink> getBoardGraph() {
        return boardGraph;
    }
}
