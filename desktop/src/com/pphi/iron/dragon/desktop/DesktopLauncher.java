package com.pphi.iron.dragon.desktop;

import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pphi.iron.dragon.IronDragon;
import com.pphi.iron.dragon.board.model.GameBoard;
import com.pphi.iron.dragon.board.model.MilePost;
import com.pphi.iron.dragon.board.model.MilePostLink;
import edu.uci.ics.jung.graph.Graph;

public class DesktopLauncher {
    public static void main (String[] arg) throws IOException {
        Graph<MilePost, MilePostLink> g;
        GameBoard gameBoard = new GameBoard();
        g = gameBoard.getBoardGraph();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new IronDragon(g), config);
    }
}
