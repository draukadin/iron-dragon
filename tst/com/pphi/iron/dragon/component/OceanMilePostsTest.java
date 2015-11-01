package com.pphi.iron.dragon.component;

import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class OceanMilePostsTest {

    @Test
    public void testConstructOceanMilePosts() throws Exception {
        OceanMilePosts oceanMilePosts = new OceanMilePosts();
        assertNotNull(oceanMilePosts.getMilePost(new HexagonCubeCoordinate(-17 , 48, -31))); //First mile post in row
        assertNotNull(oceanMilePosts.getMilePost(new HexagonCubeCoordinate(0 , 29, -29))); //MilePost in the middle of row definition
        assertNotNull(oceanMilePosts.getMilePost(new HexagonCubeCoordinate(48 , -17, -31))); //Last mile post in row
        assertNull(oceanMilePosts.getMilePost(new HexagonCubeCoordinate(0, 0, 0))); // Not a Ocean MilePost
    }
}
