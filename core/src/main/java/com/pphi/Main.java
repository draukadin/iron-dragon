package com.pphi;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pphi.iron.dragon.board.model.GameBoard;
import com.pphi.iron.dragon.board.model.MilePost;
import com.pphi.iron.dragon.board.model.MilePostLink;
import com.pphi.iron.dragon.board.view.GameBoardLayoutFactory;
import com.pphi.iron.dragon.jackson.JacksonUtil;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class Main {

    private static final Path COMPRESSED_JSON = Paths.get("GameData/GameBoard.json.gz");

    public static void main(String[] args) throws IOException {
        GameBoard gameBoard;
        long startTime = System.currentTimeMillis();
        if (COMPRESSED_JSON.toFile().exists()) {
            gameBoard = JacksonUtil.deserialize(COMPRESSED_JSON, GameBoard.class);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("Total time to create map from serialized data: %d", endTime - startTime));
        } else {
            gameBoard = new GameBoard(15);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("Total time to create map from raw data: %d", endTime - startTime));
        }
        Graph<MilePost, MilePostLink> g = gameBoard.getBoardGraph();
        Layout<MilePost, MilePostLink> layout = GameBoardLayoutFactory.createLayout(g);
        Dimension dimension = GameBoardLayoutFactory.createDimensions(g);
        VisualizationViewer<MilePost, MilePostLink> vv = new VisualizationViewer<>(layout);
        vv.setPreferredSize(dimension); //Sets the viewing area size
        // Create a graph mouse and add it to the visualization component
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);
        JFrame frame = new JFrame("Game Board Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}
