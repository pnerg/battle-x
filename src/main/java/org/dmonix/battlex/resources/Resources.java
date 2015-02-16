package org.dmonix.battlex.resources;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @version 1.0
 */
public final class Resources {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(Resources.class.getName());

    public static final int PIECE_NO_PIECE = 666;
    public static final int PIECE_EMPTY_TYPE = 100;
    public static final int PIECE_BOMB_TYPE = 0;
    public static final int PIECE_MARSHAL_TYPE = 1;
    public static final int PIECE_GENERAL_TYPE = 2;
    public static final int PIECE_COLONEL_TYPE = 3;
    public static final int PIECE_MAJOR_TYPE = 4;
    public static final int PIECE_CAPTAIN_TYPE = 5;
    public static final int PIECE_LIEUTENANT_TYPE = 6;
    public static final int PIECE_SERGEANT_TYPE = 7;
    public static final int PIECE_MINER_TYPE = 8;
    public static final int PIECE_SCOUT_TYPE = 9;
    public static final int PIECE_SPY_TYPE = 10;
    public static final int PIECE_FLAG_TYPE = 11;

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

    private static int IMG_HEIGHT = 40;
    private static final String PATH = "org/dmonix/battlex/";

    private static Image IMG_PLAYER1_PIECE0 = Resources.getImage(1, PIECE_BOMB_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE1 = Resources.getImage(1, PIECE_MARSHAL_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE2 = Resources.getImage(1, PIECE_GENERAL_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE3 = Resources.getImage(1, PIECE_COLONEL_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE4 = Resources.getImage(1, PIECE_MAJOR_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE5 = Resources.getImage(1, PIECE_CAPTAIN_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE6 = Resources.getImage(1, PIECE_LIEUTENANT_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE7 = Resources.getImage(1, PIECE_SERGEANT_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE8 = Resources.getImage(1, PIECE_MINER_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE9 = Resources.getImage(1, PIECE_SCOUT_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE10 = Resources.getImage(1, PIECE_SPY_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE11 = Resources.getImage(1, PIECE_FLAG_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER1_PIECE100 = Resources.getImage(1, PIECE_EMPTY_TYPE, IMG_HEIGHT);

    private static Image IMG_PLAYER2_PIECE0 = Resources.getImage(2, PIECE_BOMB_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE1 = Resources.getImage(2, PIECE_MARSHAL_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE2 = Resources.getImage(2, PIECE_GENERAL_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE3 = Resources.getImage(2, PIECE_COLONEL_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE4 = Resources.getImage(2, PIECE_MAJOR_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE5 = Resources.getImage(2, PIECE_CAPTAIN_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE6 = Resources.getImage(2, PIECE_LIEUTENANT_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE7 = Resources.getImage(2, PIECE_SERGEANT_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE8 = Resources.getImage(2, PIECE_MINER_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE9 = Resources.getImage(2, PIECE_SCOUT_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE10 = Resources.getImage(2, PIECE_SPY_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE11 = Resources.getImage(2, PIECE_FLAG_TYPE, IMG_HEIGHT);
    private static Image IMG_PLAYER2_PIECE100 = Resources.getImage(2, PIECE_EMPTY_TYPE, IMG_HEIGHT);

    /**
     * Contains the names of all pieces
     */
    private static final String[] PIECE_NAMES = new String[] { "Bomb", "Marshal", "General", "Colonel", "Major", "Captain", "Lieutenant", "Sergeant", "Miner",
            "Scout", "Spy", "Flag" };

    /**
     * Private constructor.
     */
    private Resources() {
    };

    public static ImageIcon getIcon(int player, int type, int scale) {
        return new ImageIcon(getImage(player, type, scale));
    }

    public static BufferedImage getBackgroundImage(int player) {
        try {
            return ImageIO.read(Resources.class.getResource("/images/player" + player + "/battlexmap_large.jpg"));
        } catch (IOException ex) {
            return null;
        }
    }

    public static Image getImage(int player, int type) {
        try {
            return (Image) Resources.class.getDeclaredField("IMG_PLAYER" + player + "_PIECE" + type).get(Resources.class);
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Failed to load image: IMG_PLAYER" + player + "_PIECE" + type, ex);
            return null;
        }
    }

    /**
     * Get the name for a piece type.
     * 
     * @param piece
     *            The type
     * @return The name
     */
    public static String getPieceName(int piece) {
        try {
            return PIECE_NAMES[piece];
        } catch (Exception ex) {
            if (piece == PIECE_NO_PIECE)
                return "NO_SUCH_PIECE";

            log.log(Level.WARNING, "No name found for piece " + piece);
            return null;
        }
    }

    private static Image getImage(int player, int type, int scale) {
        Image image = new ImageIcon(Resources.class.getResource("/images/player" + player + "/battlex-piece-" + type + ".gif")).getImage();
        return image.getScaledInstance(-1, scale, Image.SCALE_SMOOTH);
    }

}