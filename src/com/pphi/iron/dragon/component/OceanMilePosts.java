package com.pphi.iron.dragon.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import edu.uci.ics.jung.graph.util.Pair;
import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

public class OceanMilePosts {

    private Map<HexagonCubeCoordinate, OceanMilePost> oceanMilePostMilePosts;

    public OceanMilePosts() throws IOException {
        Multimap<Integer, Pair<Integer>> zAndXCoords = getCoords();
        oceanMilePostMilePosts = newHashMap();
        for (Map.Entry<Integer, Collection<Pair<Integer>>> entry : zAndXCoords.asMap().entrySet()) {
            int z = entry.getKey();
            for (Pair<Integer> xCords : entry.getValue()) {
                for (int i = xCords.getFirst(); i <= xCords.getSecond(); i++) {
                    OceanMilePost oceanMilePost = new OceanMilePost(i, z);
                    oceanMilePostMilePosts.put(oceanMilePost.getCubeCoordinate(), oceanMilePost);
                }
            }
        }
    }

    public Set<HexagonCubeCoordinate> getCoordinates() {
        return oceanMilePostMilePosts.keySet();
    }

    public OceanMilePost getMilePost(HexagonCubeCoordinate coordinate) {
        return oceanMilePostMilePosts.get(coordinate);
    }

    private Multimap<Integer, Pair<Integer>> getCoords() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(Paths.get("GameData/OceanMilePosts.json").toFile());
        Multimap<Integer, Pair<Integer>> map = HashMultimap.create();
        for (JsonNode childNode : node) {
            int z = childNode.get("z").asInt();
            int first = childNode.get("first").asInt();
            int second = childNode.get("second").asInt();
            map.put(z, new Pair<>(first, second));
        }
        return map;
    }
}
