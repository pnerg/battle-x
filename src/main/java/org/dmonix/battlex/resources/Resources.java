/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmonix.battlex.resources;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author Peter Nerg
 */
public final class Resources {
    // public static final int RESULT_WIN = 1;
    // public static final int RESULT_DRAW = 0;
    // public static final int RESULT_LOOSE = -1;
    // public static final int RESULT_WIN_GAME = 69;

    public static final String PIECE_NO_PIECE = "NO_PIECE";// ????
    public static final String PIECE_EMPTY_TYPE = "empty";
    public static final String PIECE_BOMB_TYPE = "bomb";
    public static final String PIECE_MARSHAL_TYPE = "marshal";
    public static final String PIECE_GENERAL_TYPE = "general";
    public static final String PIECE_COLONEL_TYPE = "colonel";
    public static final String PIECE_MAJOR_TYPE = "major";
    public static final String PIECE_CAPTAIN_TYPE = "captain";
    public static final String PIECE_LIEUTENANT_TYPE = "lieutenant";
    public static final String PIECE_SERGEANT_TYPE = "sergeant";
    public static final String PIECE_MINER_TYPE = "miner";
    public static final String PIECE_SCOUT_TYPE = "scout";
    public static final String PIECE_SPY_TYPE = "spy";
    public static final String PIECE_FLAG_TYPE = "flag";

    /**
     * used for testing purposes
     */
    // public static final int PIECE_BOMB_COUNT = 1;
    // public static final int PIECE_MARSHAL_COUNT = 1;
    // public static final int PIECE_GENERAL_COUNT = 0;
    // public static final int PIECE_COLONEL_COUNT = 0;
    // public static final int PIECE_MAJOR_COUNT = 0;
    // public static final int PIECE_CAPTAIN_COUNT = 0;
    // public static final int PIECE_LIEUTENANT_COUNT = 0;
    // public static final int PIECE_SERGEANT_COUNT = 0;
    // public static final int PIECE_MINER_COUNT = 0;
    // public static final int PIECE_SCOUT_COUNT = 0;
    // public static final int PIECE_SPY_COUNT = 0;
    // public static final int PIECE_FLAG_COUNT = 1;

    public static final int PIECE_BOMB_COUNT = 6;
    public static final int PIECE_MARSHAL_COUNT = 1;
    public static final int PIECE_GENERAL_COUNT = 1;
    public static final int PIECE_COLONEL_COUNT = 2;
    public static final int PIECE_MAJOR_COUNT = 3;
    public static final int PIECE_CAPTAIN_COUNT = 4;
    public static final int PIECE_LIEUTENANT_COUNT = 4;
    public static final int PIECE_SERGEANT_COUNT = 4;
    public static final int PIECE_MINER_COUNT = 5;
    public static final int PIECE_SCOUT_COUNT = 8;
    public static final int PIECE_SPY_COUNT = 1;
    public static final int PIECE_FLAG_COUNT = 1;

    public static final int TOTAL_PIECES = PIECE_BOMB_COUNT + PIECE_MARSHAL_COUNT + PIECE_GENERAL_COUNT + PIECE_COLONEL_COUNT + PIECE_MAJOR_COUNT
            + PIECE_CAPTAIN_COUNT + PIECE_LIEUTENANT_COUNT + PIECE_SERGEANT_COUNT + PIECE_MINER_COUNT + PIECE_SCOUT_COUNT + PIECE_SPY_COUNT + PIECE_FLAG_COUNT;

    // private static final String PATH = "org/dmonix/battlex/";
    //
    // private static Image IMG_PLAYER1_PIECE0 = Resources.getImage(1, PIECE_BOMB_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE1 = Resources.getImage(1, PIECE_MARSHAL_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE2 = Resources.getImage(1, PIECE_GENERAL_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE3 = Resources.getImage(1, PIECE_COLONEL_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE4 = Resources.getImage(1, PIECE_MAJOR_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE5 = Resources.getImage(1, PIECE_CAPTAIN_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE6 = Resources.getImage(1, PIECE_LIEUTENANT_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE7 = Resources.getImage(1, PIECE_SERGEANT_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE8 = Resources.getImage(1, PIECE_MINER_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE9 = Resources.getImage(1, PIECE_SCOUT_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE10 = Resources.getImage(1, PIECE_SPY_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE11 = Resources.getImage(1, PIECE_FLAG_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER1_PIECE100 = Resources.getImage(1, PIECE_EMPTY_TYPE, IMG_HEIGHT);
    //
    // private static Image IMG_PLAYER2_PIECE0 = Resources.getImage(2, PIECE_BOMB_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE1 = Resources.getImage(2, PIECE_MARSHAL_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE2 = Resources.getImage(2, PIECE_GENERAL_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE3 = Resources.getImage(2, PIECE_COLONEL_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE4 = Resources.getImage(2, PIECE_MAJOR_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE5 = Resources.getImage(2, PIECE_CAPTAIN_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE6 = Resources.getImage(2, PIECE_LIEUTENANT_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE7 = Resources.getImage(2, PIECE_SERGEANT_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE8 = Resources.getImage(2, PIECE_MINER_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE9 = Resources.getImage(2, PIECE_SCOUT_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE10 = Resources.getImage(2, PIECE_SPY_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE11 = Resources.getImage(2, PIECE_FLAG_TYPE, IMG_HEIGHT);
    // private static Image IMG_PLAYER2_PIECE100 = Resources.getImage(2, PIECE_EMPTY_TYPE, IMG_HEIGHT);

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
        images.put(PIECE_EMPTY_TYPE, loadImage(player, PIECE_EMPTY_TYPE, defaultHeight));
        images.put(PIECE_BOMB_TYPE, loadImage(player, PIECE_BOMB_TYPE, defaultHeight));
        images.put(PIECE_MARSHAL_TYPE, loadImage(player, PIECE_MARSHAL_TYPE, defaultHeight));
        images.put(PIECE_GENERAL_TYPE, loadImage(player, PIECE_GENERAL_TYPE, defaultHeight));
        images.put(PIECE_COLONEL_TYPE, loadImage(player, PIECE_COLONEL_TYPE, defaultHeight));
        images.put(PIECE_MAJOR_TYPE, loadImage(player, PIECE_MAJOR_TYPE, defaultHeight));
        images.put(PIECE_CAPTAIN_TYPE, loadImage(player, PIECE_CAPTAIN_TYPE, defaultHeight));
        images.put(PIECE_LIEUTENANT_TYPE, loadImage(player, PIECE_LIEUTENANT_TYPE, defaultHeight));
        images.put(PIECE_SERGEANT_TYPE, loadImage(player, PIECE_SERGEANT_TYPE, defaultHeight));
        images.put(PIECE_MINER_TYPE, loadImage(player, PIECE_MINER_TYPE, defaultHeight));
        images.put(PIECE_SCOUT_TYPE, loadImage(player, PIECE_SCOUT_TYPE, defaultHeight));
        images.put(PIECE_SPY_TYPE, loadImage(player, PIECE_SPY_TYPE, defaultHeight));
        images.put(PIECE_FLAG_TYPE, loadImage(player, PIECE_FLAG_TYPE, defaultHeight));
    }

    private static Image loadImage(int player, String type, int scale) {
        Image image = new ImageIcon(Resources.class.getResource("/images/player" + player + "/piece-" + type + ".gif")).getImage();
        return image.getScaledInstance(-1, scale, Image.SCALE_SMOOTH);
    }
}