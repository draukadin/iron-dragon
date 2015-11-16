package com.pphi.iron.dragon.board;

import com.google.common.base.Optional;
import com.pphi.iron.dragon.component.TerrainType;

import static com.pphi.iron.dragon.component.TerrainType.CITY_AND_PORT;
import static com.pphi.iron.dragon.component.TerrainType.PORT;
import static com.pphi.iron.dragon.component.TerrainType.SEA_POINT;

public class MilePostLinkFactory {

    public Optional<MilePostLink> createLink(MilePost src, MilePost dest) {
        TerrainType srcTerrainType = src.getTerrainType();
        TerrainType destTerrainType = dest.getTerrainType();
        Optional<MilePostLink> linkOptional = Optional.absent();
        if (srcTerrainType == SEA_POINT && destTerrainType == SEA_POINT) {
            linkOptional = MilePostLink.builder().build();
        } else if (srcTerrainType != SEA_POINT && destTerrainType != SEA_POINT) {
            linkOptional = MilePostLink.builder().build();
        } else if ((srcTerrainType == SEA_POINT && destTerrainType == PORT)
                || (srcTerrainType == SEA_POINT && destTerrainType == CITY_AND_PORT)
                || (srcTerrainType == PORT)
                || (srcTerrainType == CITY_AND_PORT)) {
            linkOptional = MilePostLink.builder().build();
        }
        return linkOptional;
    }
}
