package com.pphi.iron.dragon.board.view;

import static com.google.common.collect.Maps.newHashMap;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.Map;

import com.pphi.iron.dragon.board.model.MilePost;
import com.pphi.iron.dragon.board.model.MilePostLink;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.TransformerUtils;

public class GameBoardLayoutFactory {

    private static final int TWO = 2;
    private static final int NUMBER_OF_HEXAGONS_PER_ROW = 66;
    private static final int NUMBER_OF_ROWS = 68;

    public static Layout<MilePost, MilePostLink> createLayout(Graph<MilePost, MilePostLink> graph) {
        Transformer<MilePost, Point2D> initilizer = createInitializer(graph);
        Dimension size = createDimensions(graph);
        return new StaticLayout<>(graph, initilizer, size);
    }

    private static Transformer<MilePost, Point2D> createInitializer(Graph<MilePost, MilePostLink> graph) {
        Map<MilePost, Point2D> vertexLocations = newHashMap();
        MilePostTransformer milePostTransformer = new MilePostTransformer();
        for (MilePost milePost : graph.getVertices()) {
            vertexLocations.put(milePost, milePostTransformer.transform(milePost));
        }
        return TransformerUtils.mapTransformer(vertexLocations);
    }

    public static Dimension createDimensions(Graph<MilePost, MilePostLink> graph) {
        MilePost milePost = graph.getVertices().iterator().next();
        int diameter = milePost.getRadius() * TWO;
        int width = diameter * NUMBER_OF_HEXAGONS_PER_ROW;
        int height = diameter * NUMBER_OF_ROWS;
        return new Dimension(width, height);
    }
}
