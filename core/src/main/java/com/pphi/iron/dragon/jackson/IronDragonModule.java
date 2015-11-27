package com.pphi.iron.dragon.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pphi.hexagon.coordinates.HexagonCubeCoordinate;
import com.pphi.iron.dragon.board.model.GameBoard;
import com.pphi.iron.dragon.jackson.deserialize.GameBoardDeserializer;
import com.pphi.iron.dragon.jackson.mixin.HexagonCubeCoordinateMixIn;

public class IronDragonModule extends SimpleModule {

    public IronDragonModule() {
        addDeserializer(GameBoard.class, new GameBoardDeserializer());
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(HexagonCubeCoordinate.class, HexagonCubeCoordinateMixIn.class);
        super.setupModule(context);
    }
}
