package com.pphi.iron.dragon.board.factory;

import static com.google.common.collect.Lists.newArrayList;
import static com.pphi.iron.dragon.component.Country.UNDERGROUND;
import static com.pphi.iron.dragon.component.TerrainType.CITY_AND_PORT;
import static com.pphi.iron.dragon.component.TerrainType.PORT;
import static com.pphi.iron.dragon.component.TerrainType.SEA_POINT;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Optional;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.hexagon.neighbors.PointyTopCubeNeighbors;
import com.pphi.iron.dragon.CoordinateDataProvider;
import com.pphi.iron.dragon.board.model.MilePost;
import com.pphi.iron.dragon.component.TerrainType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

    @Test(dataProvider = "seaPointCoordinates", dataProviderClass = CoordinateDataProvider.class)
    public void testSeaPointsAreAllConnectedToEachOther(HexagonCubeCoordinate seaPointCoordinate) {
        Collection<MilePost> milePosts = milePostFactory.createMilePost(seaPointCoordinate);
        MilePost oceanMilePost = filterOutUndergroundMilePosts(milePosts);
        List<MilePost> neighborMilePosts = getOceanNeighbors(seaPointCoordinate);
        for (MilePost neighborMilePost : neighborMilePosts) {
            assertTrue(milePostLinkFactory.createLink(oceanMilePost, neighborMilePost).isPresent());
        }
    }

    @Test(dataProvider = "landCoordinates", dataProviderClass = CoordinateDataProvider.class)
    public void testLandPointsAreConnectedToEachOther(HexagonCubeCoordinate landCoordinate) {
        Collection<MilePost> milePosts = milePostFactory.createMilePost(landCoordinate);
        MilePost undergroundMilePost = null;
        MilePost landMilePost = null;
        for (MilePost milePost : milePosts) {
            if (milePost.getCountry().equals(UNDERGROUND)) {
                undergroundMilePost = milePost;
            } else if (milePost.getTerrainType() != SEA_POINT){
                landMilePost = milePost;
            }
        }
        List<MilePost> undergroundNeighbors = getUndergroundNeighbors(undergroundMilePost);
        List<MilePost> landNeighbors = getLandNeighbors(landMilePost);
        for (MilePost neighborMilePost : undergroundNeighbors) {
            assertTrue(milePostLinkFactory.createLink(undergroundMilePost, neighborMilePost).isPresent());
        }
        for (MilePost neighborMilePost : landNeighbors) {
            assertTrue(milePostLinkFactory.createLink(landMilePost, neighborMilePost).isPresent());
        }
    }

    @Test(dataProvider = "undergroundEntranceCoordinates", dataProviderClass = CoordinateDataProvider.class)
    public void testUndergroundEntrancesOnMainMapConnectToEntranceOnSideMap(HexagonCubeCoordinate coordinate) {
        Collection<MilePost> milePosts = milePostFactory.createMilePost(coordinate);
        MilePost undergroundEntrance = null;
        MilePost mainMapEntrance = null;
        for (MilePost milePost : milePosts) {
        undergroundEntrance = milePostFactory.createMilePost(
                milePostFactory.shiftXAxisCoordinate(milePost.getCubeCoordinate())).iterator().next();
        mainMapEntrance = milePost;
        }
        assertTrue(milePostLinkFactory.createLink(undergroundEntrance, mainMapEntrance).isPresent());
    }

    private List<MilePost> getUndergroundNeighbors(MilePost undergroundMilePost) {
        List<MilePost> neighborMilePosts = newArrayList();
        if (undergroundMilePost != null) {
            List<HexagonCubeCoordinate> neighbors = PointyTopCubeNeighbors.getNeighbors(
                    undergroundMilePost.getCubeCoordinate());
            for (HexagonCubeCoordinate neighbor : neighbors) {
                for (MilePost milePost : milePostFactory.createMilePost(neighbor)) {
                    if (milePost.getTerrainType() != SEA_POINT && milePost.getCountry() == UNDERGROUND) {
                        neighborMilePosts.add(milePost);
                    }
                }
            }
        }
        return neighborMilePosts;
    }

    private List<MilePost> getLandNeighbors(MilePost landMilePost) {
        List<MilePost> neighborMilePosts = newArrayList();
        if (landMilePost != null) {
            List<HexagonCubeCoordinate> neighbors = PointyTopCubeNeighbors.getNeighbors(landMilePost.getCubeCoordinate());
            for (HexagonCubeCoordinate neighbor : neighbors) {
                for (MilePost milePost : milePostFactory.createMilePost(neighbor)) {
                    if (milePost.getTerrainType() != SEA_POINT && milePost.getCountry() != UNDERGROUND) {
                        neighborMilePosts.add(milePost);
                    }
                }
            }
        }
        return neighborMilePosts;
    }

    private MilePost filterOutUndergroundMilePosts(Collection<MilePost> milePosts) {
        MilePost oceanMilePost = null;
        for (MilePost milePost : milePosts) {
            if (milePost.getCountry() != UNDERGROUND) {
                oceanMilePost = milePost;
            }
        }
        return oceanMilePost;
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
                if (milePost.getCountry() != UNDERGROUND && milePost.getTerrainType() != PORT
                        && milePost.getTerrainType() != CITY_AND_PORT) {
                    neighborMilePosts.add(milePost);
                }
            }
        }
        return neighborMilePosts;
    }

    private List<MilePost> getOceanNeighbors(HexagonCubeCoordinate seaPointCoordinate) {
        List<HexagonCubeCoordinate> neighbors = PointyTopCubeNeighbors.getNeighbors(seaPointCoordinate);
        List<MilePost> neighborMilePosts = newArrayList();
        for (HexagonCubeCoordinate neighbor : neighbors) {
            for (MilePost milePost : milePostFactory.createMilePost(neighbor)) {
                if (milePost.getTerrainType() == SEA_POINT) {
                    neighborMilePosts.add(milePost);
                }
            }
        }
        return neighborMilePosts;
    }
}
