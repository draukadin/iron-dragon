package com.pphi.iron.dragon.component;

import org.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import org.testng.annotations.Test;

import java.util.Map;

public class OceanMilePostsTest {

    @Test
    public void testConstructOceanMilePosts() throws Exception {
        OceanMilePosts oceanMilePosts = new OceanMilePosts();
        Map<HexagonCubeCoordinate, OceanMilePost> map = oceanMilePosts.getOceanMilePostMilePosts();
        System.out.println(map.size());
    }
}
