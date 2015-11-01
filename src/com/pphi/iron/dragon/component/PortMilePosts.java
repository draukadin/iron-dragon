package com.pphi.iron.dragon.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

public class PortMilePosts {

    private Map<HexagonCubeCoordinate, PortMilePost> ports;

    public PortMilePosts() throws IOException {
        ports = getCoords();
    }

    private Map<HexagonCubeCoordinate, PortMilePost> getCoords() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(Paths.get("GameData/PortMilePosts.json").toFile());
        Map<HexagonCubeCoordinate, PortMilePost> ports = newHashMap();
        for (JsonNode childNode : node) {
            int x = childNode.get("x").asInt();
            int z = childNode.get("z").asInt();
            PortMilePost portMilePost = new PortMilePost(x, z);
            ports.put(portMilePost.getCubeCoordinate(), portMilePost);
        }
        return ports;
    }

    public Set<HexagonCubeCoordinate> getCoordinates() {
        return ports.keySet();
    }

    public PortMilePost getMilePost(HexagonCubeCoordinate coordinate) {
        return ports.get(coordinate);
    }
}
