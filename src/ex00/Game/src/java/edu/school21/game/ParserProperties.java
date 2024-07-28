package edu.school21.game;

import com.diogonunes.jcolor.Attribute;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ParserProperties {
    private final Properties properties;
    private final Attribute enemyColor;
    private final Attribute playerColor;
    private final Attribute wallColor;
    private final Attribute goalColor;
    private final Attribute emptyColor;
    private final char enemySymbol;
    private final char playerSymbol;
    private final char wallSymbol;
    private final char goalSymbol;
    private final char emptySymbol;

    public ParserProperties(boolean isProduction) throws IOException, IllegalPropertiesException {
        properties = new Properties();
        String nameProperties = isProduction ? "application-production.properties" : "application-dev.properties";
        properties.load(new FileInputStream(System.getProperty("user.dir") + File.separator + "Game" + File.separator + "target" + File.separator + "classes"  + File.separator + nameProperties));
        enemySymbol = parsChar("enemy.char");
        playerSymbol = parsChar("player.char");
        wallSymbol = parsChar("wall.char");
        goalSymbol = parsChar("goal.char");
        emptySymbol = parsChar("empty.char");
        enemyColor = parsColor("enemy.color");
        playerColor = parsColor("player.color");
        wallColor = parsColor("wall.color");
        goalColor = parsColor("goal.color");
        emptyColor = parsColor("empty.color");
    }

    private char parsChar(String key) throws IllegalPropertiesException {
        String property = properties.getProperty(key);
        String[] splitProperty = property.split(" ");
        if (splitProperty.length == 1) {
            if (splitProperty[0].isEmpty()) {
                return ' ';
            }
            return splitProperty[0].charAt(0);
        }
        throw new IllegalPropertiesException("Property " + key + " is illegal");
    }

    private Attribute parsColor(String key) throws IllegalPropertiesException {
        String property = properties.getProperty(key);
        String[] splitProperty = property.split(" ");
        if (splitProperty.length == 1) {
            return isColor(splitProperty[0]);
        }
        throw new IllegalPropertiesException("Property " + key + " is illegal");
    }

    private Attribute isColor(String color) throws IllegalPropertiesException {
        switch (color.toUpperCase()) {
            case ("RED"):
                return Attribute.RED_BACK();
            case ("GREEN"):
                return Attribute.GREEN_BACK();
            case ("BLACK"):
                return Attribute.BLACK_BACK();
            case ("YELLOW"):
                return Attribute.YELLOW_BACK();
            case ("BLUE"):
                return Attribute.BLUE_BACK();
            case ("WHITE"):
                return Attribute.WHITE_BACK();
            case ("MAGENTA"):
                return Attribute.MAGENTA_BACK();
            case ("CYAN"):
                return Attribute.CYAN_BACK();
            case ("NONE"):
                return Attribute.NONE();
        }
        throw new IllegalPropertiesException("Property " + color + " is illegal");
    }

    public Attribute getEnemyColor() {
        return enemyColor;
    }

    public Attribute getPlayerColor() {
        return playerColor;
    }

    public Attribute getWallColor() {
        return wallColor;
    }

    public Attribute getGoalColor() {
        return goalColor;
    }

    public Attribute getEmptyColor() {
        return emptyColor;
    }

    public char getEnemySymbol() {
        return enemySymbol;
    }

    public char getPlayerSymbol() {
        return playerSymbol;
    }

    public char getWallSymbol() {
        return wallSymbol;
    }

    public char getGoalSymbol() {
        return goalSymbol;
    }

    public char getEmptySymbol() {
        return emptySymbol;
    }

}
