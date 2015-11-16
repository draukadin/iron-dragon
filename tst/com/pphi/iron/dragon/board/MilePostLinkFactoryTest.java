package com.pphi.iron.dragon.board;

import com.google.common.base.Optional;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.hexagon.neighbors.PointyTopCubeNeighbors;
import com.pphi.iron.dragon.CoordinateDataProvider;
import com.pphi.iron.dragon.component.Country;
import com.pphi.iron.dragon.component.TerrainType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.pphi.iron.dragon.component.TerrainType.CITY_AND_PORT;
import static com.pphi.iron.dragon.component.TerrainType.PORT;
import static com.pphi.iron.dragon.component.TerrainType.SEA_POINT;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MilePostLinkFactoryTest {



    private MilePostFactory milePostFactory;
    private MilePostLinkFactory milePostLinkFactory;

    @BeforeTest
    public void setup() throws Exception {
        milePostFactory = new MilePostFactory();
        milePostLinkFactory = new MilePostLinkFactory();

    }

    @Test(dataProvider = "portCoordinates", dataProviderClass = CoordinateDataProvider.class)
    public void testPortsHaveConnectionToAllNeighbors(HexagonCubeCoordinate portCoordinate) {
        Collection<MilePost> milePosts = milePostFactory.createMilePost(portCoordinate);
        MilePost portMilePost = getPortMilePost(milePosts);
        List<MilePost> neighborMilePosts = getMainMapNeighbors(portCoordinate);
        for (MilePost neighborMilePost : neighborMilePosts) {
            assertTrue(milePostLinkFactory.createLink(portMilePost, neighborMilePost).isPresent());
        }
    }

    @Test(dataProvider = "seaPointCoordinates", dataProviderClass = CoordinateDataProvider.class)
    public void testSeaPointsDoNotConnectToNonPortLandPoints(HexagonCubeCoordinate seaPointCoordinate) {
        Collection<MilePost> milePosts = milePostFactory.createMilePost(seaPointCoordinate);
        Optional<MilePost> coastalSeaPointMilePost = getCoastalSeaPointMilePost(milePosts);
        if (coastalSeaPointMilePost.isPresent()) {
            MilePost seaPointMilePost = coastalSeaPointMilePost.get();
            List<MilePost> neighborMilePosts = getMainMapNeighbors(seaPointCoordinate);
            for (MilePost neighborMilePost : neighborMilePosts) {
                if (neighborMilePost.getTerrainType() != SEA_POINT) {
                    assertFalse(milePostLinkFactory.createLink(seaPointMilePost, neighborMilePost).isPresent());
                }
            }
        }
    }

    private Optional<MilePost> getCoastalSeaPointMilePost(Collection<MilePost> milePosts) {
        Optional<MilePost> optional = Optional.absent();
        for (MilePost milePost : milePosts) {
            if (milePost.getTerrainType().equals(SEA_POINT)) {
                List<MilePost> neighborMilePosts = getMainMapNeighbors(milePost.getCubeCoordinate());
                for (MilePost neighborMilePost : neighborMilePosts) {
                    if (neighborMilePost.getTerrainType() != SEA_POINT) {
                        optional = Optional.of(milePost);
                        break;
                    }
                }
            }
        }
        return optional;
    }

    private MilePost getPortMilePost(Collection<MilePost> milePosts) {
        MilePost portMilePost = null;
        for (MilePost milePost : milePosts) {
            TerrainType terrainType = milePost.getTerrainType();
            if (terrainType.equals(PORT) || terrainType.equals(CITY_AND_PORT)) {
                portMilePost = milePost;
            }
        }
        return portMilePost;
    }

    private List<MilePost> getMainMapNeighbors(HexagonCubeCoordinate portCoordinate) {
        List<HexagonCubeCoordinate> neighbors = PointyTopCubeNeighbors.getNeighbors(portCoordinate);
        List<MilePost> neighborMilePosts = newArrayList();
        for (HexagonCubeCoordinate neighbor : neighbors) {
            for (MilePost milePost : milePostFactory.createMilePost(neighbor)) {
                if (milePost.getCountry() != Country.UNDERGROUND && milePost.getTerrainType() != PORT
                        && milePost.getTerrainType() != CITY_AND_PORT) {
                    neighborMilePosts.add(milePost);
                }
            }
        }
        return neighborMilePosts;
    }
}
