package com.pphi.iron.dragon.board.factory;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pphi.iron.dragon.component.TerrainType;

//import org.apache.log4j.Logger;

public class IconFactory {

//    private static final Logger LOGGER = Logger.getLogger(IconCreator.class);

    private static final String IMAGE_LOCATIONS = "images";

    public Icon getIcon(TerrainType terrainType) {
        Path iconLocation =  Paths.get(String.format("%s/%s", IMAGE_LOCATIONS, terrainType.getImageFileName()));
        byte[] fileContent = new byte[0];
        try {
            fileContent = Files.readAllBytes(iconLocation);
        } catch (IOException e) {
//            LOGGER.error(e.getMessage(), e);
        }
        ImageIcon icon = new ImageIcon(fileContent);
        icon.setDescription(terrainType.getImageFileName());
        return icon;
    }
}
