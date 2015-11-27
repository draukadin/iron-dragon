package com.pphi.iron.dragon.jackson.deserialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pphi.iron.dragon.board.model.BoardGraph;
import com.pphi.iron.dragon.board.model.GameBoard;
import com.pphi.iron.dragon.board.model.MilePost;
import com.pphi.iron.dragon.board.model.MilePostLink;
import edu.uci.ics.jung.graph.Graph;

public class GameBoardDeserializer extends JsonDeserializer<GameBoard> {

    private static ObjectMapper objectMapper;

    public GameBoardDeserializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public GameBoard deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Graph<MilePost, MilePostLink> graph = new BoardGraph();
        deserializeEdges(node.get("boardGraph"), graph);
        return new GameBoard(graph);
    }

    private void deserializeEdges(JsonNode node, Graph<MilePost, MilePostLink> graph) throws JsonProcessingException {
        JsonNode edges = node.get("edges");
        for (JsonNode edge : edges) {
            MilePostLink milePostLink = objectMapper.treeToValue(edge, MilePostLink.class);
            //TODO come up with a better way to serialize and deserialize the graph
//            graph.addEdge(milePostLink, milePostLink.getSrc(), milePostLink.getDest());
        }
    }
}
