package edu.school21.game;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import edu.school21.chaselogic.ChaseLogic;

import java.io.IOException;
import java.util.Scanner;

public class GameLogic {
    private final int[] UP_XY = {-1, 0};
    private final int[] DOWN_XY = {1, 0};
    private final int[] LEFT_XY = {0, -1};
    private final int[] RIGHT_XY = {0, 1};

    public void play(String[] args) throws IllegalParametersException, IOException, IllegalPropertiesException {
        Scanner sc = new Scanner(System.in);
        ParserArguments parserArguments = new ParserArguments(args);
        boolean isProduction = parserArguments.getIsProduction();
        ParserProperties parserProperties = new ParserProperties(isProduction);
        int sizeField = parserArguments.getSizeField();
        int countEnemies = parserArguments.getEnemiesCount();
        int countWalls = parserArguments.getWallsCount();
        char playerSymbol = parserProperties.getPlayerSymbol();
        char enemySymbol = parserProperties.getEnemySymbol();
        char wallSymbol = parserProperties.getWallSymbol();
        char goalSymbol = parserProperties.getGoalSymbol();
        char emptySymbol = parserProperties.getEmptySymbol();
        char[][] gameField = CreateGameField.createGameField(sizeField, countEnemies, countWalls, playerSymbol, enemySymbol, goalSymbol, wallSymbol, emptySymbol);
        ChaseLogic chaseLogic = new ChaseLogic(countEnemies, playerSymbol, enemySymbol, wallSymbol, goalSymbol, emptySymbol);
        String inputLine = "";
        while (!inputLine.equals("9")) {
            printGameField(gameField, parserProperties);
            inputLine = sc.nextLine();
            StatusGame statusGame;
            switch (inputLine.toLowerCase()) {
                case "w":
                    statusGame = movePlayer(UP_XY, gameField, playerSymbol, enemySymbol, wallSymbol, goalSymbol, emptySymbol);
                    break;
                case "s":
                    statusGame = movePlayer(DOWN_XY, gameField, playerSymbol, enemySymbol, wallSymbol, goalSymbol, emptySymbol);
                    break;
                case "a":
                    statusGame = movePlayer(LEFT_XY, gameField, playerSymbol, enemySymbol, wallSymbol, goalSymbol, emptySymbol);
                    break;
                case "d":
                    statusGame = movePlayer(RIGHT_XY, gameField, playerSymbol, enemySymbol, wallSymbol, goalSymbol, emptySymbol);
                    break;
                case "9":
                    statusGame = StatusGame.LOSE;
                    break;
                default:
                    System.err.println("Enter correct direction");
                    statusGame = StatusGame.STAY;
            }
            if (statusGame == StatusGame.WIN) {
                System.out.println("What do you think of that, L? This is my perfect victory! THAT'S RIGHT, I WIN!!!!!!!!!");
                break;
            } else if (statusGame == StatusGame.LOSE) {
                System.out.println("GG, LOSE");
                break;
            }
            if (statusGame == StatusGame.PLAY) {
                if (checkProdNum(sc, isProduction)) {
                    chaseLogic.enemyToPlayer(gameField);
                }
            }
        }
    }

    private boolean checkProdNum(Scanner sc, boolean isProduction) {
        System.out.println("Enter 8");
        if (!isProduction) {
            return sc.nextLine().equals("8");
        }
        return true;
    }

    private StatusGame movePlayer(int[] coordinatesXY, char[][] gameField, char playerSymbol, char enemySymbol, char wallSymbol, char goalSymbol, char emptySymbol) {
        int sizeField = gameField.length;
        int playerX = 0, playerY = 0;
        for (int i = 0; i < sizeField; ++i) {
            for (int j = 0; j < sizeField; ++j) {
                if (gameField[i][j] == playerSymbol) {
                    playerY = i;
                    playerX = j;
                }
            }
        }
        int newCoordinateX = coordinatesXY[1] + playerX;
        int newCoordinateY = coordinatesXY[0] + playerY;
        if (newCoordinateX >= 0 && newCoordinateY >= 0 && newCoordinateX < sizeField && newCoordinateY < sizeField) {
            if (gameField[newCoordinateY][newCoordinateX] == goalSymbol) {
                return StatusGame.WIN;
            } else if (gameField[newCoordinateY][newCoordinateX] == enemySymbol || isLoseMove(gameField, newCoordinateY, newCoordinateX, enemySymbol)) {
                return StatusGame.LOSE;
            } else if (gameField[newCoordinateY][newCoordinateX] != wallSymbol) {
                gameField[newCoordinateY][newCoordinateX] = playerSymbol;
                gameField[playerY][playerX] = emptySymbol;
            }
        } else {
            System.out.println("I can't move");
            return StatusGame.STAY;
        }
        return StatusGame.PLAY;
    }

    private boolean isLoseMove(char[][] gameField, int newCoordinateY, int newCoordinateX, char enemySymbol) {
        final int[] dy = {-1, 0, 1, 0};
        final int[] dx = {0, 1, 0, -1};
        int sizeField = gameField.length;
        for (int i = 0; i < dy.length; ++i) {
            int ty = newCoordinateY + dy[i];
            int tx = newCoordinateX + dx[i];
            if (0 <= tx && 0 <= ty && ty < sizeField && tx < sizeField) {
                if (gameField[ty][tx] == enemySymbol) {
                    return true;
                }
            }
        }
        return false;
    }

    private void printGameField(char[][] gameField, ParserProperties properties) {
        final Attribute enemyColor = properties.getEnemyColor();
        final Attribute playerColor = properties.getPlayerColor();
        final Attribute wallColor = properties.getWallColor();
        final Attribute goalColor = properties.getGoalColor();
        final Attribute emptyColor = properties.getEmptyColor();
        final char enemySymbol = properties.getEnemySymbol();
        final char playerSymbol = properties.getPlayerSymbol();
        final char wallSymbol = properties.getWallSymbol();
        final char goalSymbol = properties.getGoalSymbol();
        final char emptySymbol = properties.getEmptySymbol();
        int sizeField = gameField.length;
        for (int i = 0; i < sizeField; ++i) {
            for (int j = 0; j < sizeField; ++j) {
                if (gameField[i][j] == playerSymbol) {
                    System.out.print(new AnsiFormat(getTextColor(playerColor), playerColor).format(String.valueOf(playerSymbol)));
                } else if (gameField[i][j] == enemySymbol) {
                    System.out.print(new AnsiFormat(getTextColor(enemyColor), enemyColor).format(String.valueOf(enemySymbol)));
                } else if (gameField[i][j] == goalSymbol) {
                    System.out.print(new AnsiFormat(getTextColor(goalColor), goalColor).format(String.valueOf(goalSymbol)));
                } else if (gameField[i][j] == wallSymbol) {
                    System.out.print(new AnsiFormat(getTextColor(wallColor), wallColor).format(String.valueOf(wallSymbol)));
                } else {
                    System.out.print(new AnsiFormat(getTextColor(enemyColor), emptyColor).format(String.valueOf(emptySymbol)));
                }
            }
            System.out.println();
        }
    }

    private Attribute getTextColor(Attribute backColor) {
        return backColor.toString().equals(Attribute.BLACK_BACK().toString()) ? Attribute.WHITE_TEXT() : Attribute.BLACK_TEXT();
    }
}