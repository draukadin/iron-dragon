package com.pphi.iron.dragon.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import edu.uci.ics.jung.graph.util.Pair;

import java.io.IOException;
import java.nio.file.Path;

public final class CoordinateDeserializationUtil {

    private CoordinateDeserializationUtil() {}

    public static Multimap<Integer, Pair<Integer>> getCoords(Path path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(path.toFile());
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
