package com.pphi.iron.dragon.component;


import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import org.pphi.hexagon.util.CoordinateUtil;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class PortMilePostsTest {

    @Test
    public void testPortsCreation() throws IOException {
        PortMilePosts portMilePosts = new PortMilePosts();
        assertNotNull(portMilePosts.getMilePost(new HexagonCubeCoordinate(-32, CoordinateUtil.solveForY(-32, 8), 8)));
        assertNull(portMilePosts.getMilePost(new HexagonCubeCoordinate(0, 0, 0)));
    }
}
