package com.pphi.iron.dragon.board.view;

import java.awt.geom.Point2D;

import com.pphi.hexagon.Orientation;
import com.pphi.hexagon.coordinates.HexagonCoordinateConverter;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.iron.dragon.board.model.MilePost;
import org.apache.commons.collections15.Transformer;

public class MilePostTransformer implements Transformer<MilePost, Point2D> {

    HexagonCoordinateConverter hexagonCoordinateConverter;

    public MilePostTransformer() {
        hexagonCoordinateConverter = new HexagonCoordinateConverter();
    }

    @Override
    public Point2D transform(MilePost input) {
        int size = input.getRadius();
        HexagonCubeCoordinate cubeCoordinate = input.getCubeCoordinate();
        return hexagonCoordinateConverter.getPixelCoordinate(cubeCoordinate, size, Orientation.POINTY_TOP);
    }
}
