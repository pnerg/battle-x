/**
 *  Copyright 2015 Peter Nerg
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dmonix.battlex.resources;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.dmonix.battlex.datamodel.PieceData;

/**
 * @author Peter Nerg
 */
public final class Resources {

    private static final Map<String, Image> player1Images = new HashMap<>();
    private static final Map<String, Image> player2Images = new HashMap<>();
    static {
        preLoadPlayerImages(1, player1Images);
        preLoadPlayerImages(2, player2Images);
    }

    /**
     * Private constructor.
     */
    private Resources() {
    };

    public static ImageIcon getIcon(int player, String type, int scale) {
        return new ImageIcon(loadImage(player, type, scale));
    }

    public static BufferedImage getBackgroundImage(int player) {
        try {
            return ImageIO.read(Resources.class.getResource("/images/player" + player + "/map_large.jpg"));
        } catch (IOException ex) {
            return null;
        }
    }

    public static Image getImage(int player, String type) {
        if (player == 1) {
            return player1Images.get(type);
        } else {
            return player2Images.get(type);
        }
    }

    private static void preLoadPlayerImages(int player, Map<String, Image> images) {
        int defaultHeight = 40;
        images.put(PieceData.PIECE_EMPTY_TYPE, loadImage(player, PieceData.PIECE_EMPTY_TYPE, defaultHeight));
        images.put(PieceData.PIECE_BOMB_TYPE, loadImage(player, PieceData.PIECE_BOMB_TYPE, defaultHeight));
        images.put(PieceData.PIECE_MARSHAL_TYPE, loadImage(player, PieceData.PIECE_MARSHAL_TYPE, defaultHeight));
        images.put(PieceData.PIECE_GENERAL_TYPE, loadImage(player, PieceData.PIECE_GENERAL_TYPE, defaultHeight));
        images.put(PieceData.PIECE_COLONEL_TYPE, loadImage(player, PieceData.PIECE_COLONEL_TYPE, defaultHeight));
        images.put(PieceData.PIECE_MAJOR_TYPE, loadImage(player, PieceData.PIECE_MAJOR_TYPE, defaultHeight));
        images.put(PieceData.PIECE_CAPTAIN_TYPE, loadImage(player, PieceData.PIECE_CAPTAIN_TYPE, defaultHeight));
        images.put(PieceData.PIECE_LIEUTENANT_TYPE, loadImage(player, PieceData.PIECE_LIEUTENANT_TYPE, defaultHeight));
        images.put(PieceData.PIECE_SERGEANT_TYPE, loadImage(player, PieceData.PIECE_SERGEANT_TYPE, defaultHeight));
        images.put(PieceData.PIECE_MINER_TYPE, loadImage(player, PieceData.PIECE_MINER_TYPE, defaultHeight));
        images.put(PieceData.PIECE_SCOUT_TYPE, loadImage(player, PieceData.PIECE_SCOUT_TYPE, defaultHeight));
        images.put(PieceData.PIECE_SPY_TYPE, loadImage(player, PieceData.PIECE_SPY_TYPE, defaultHeight));
        images.put(PieceData.PIECE_FLAG_TYPE, loadImage(player, PieceData.PIECE_FLAG_TYPE, defaultHeight));
    }

    private static Image loadImage(int player, String type, int scale) {
        Image image = new ImageIcon(Resources.class.getResource("/images/player" + player + "/piece-" + type + ".gif")).getImage();
        return image.getScaledInstance(-1, scale, Image.SCALE_SMOOTH);
    }
}