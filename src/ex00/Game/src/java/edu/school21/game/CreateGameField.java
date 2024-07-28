package edu.school21.game;

import edu.school21.chaselogic.ChaseLogic;

import java.util.Random;

public class CreateGameField {

    private static Random random;
    private static char[][] gameField;
    private static int sizeField;

    private static int posYPlayer = 0;
    private static int posXPlayer = 0;

    private static int posYGoal = 0;
    private static int posXGoal = 0;

    public static char[][] createGameField(int sizeFieldArg, int countEnemies, int countWalls, char playerSymbol, char enemySymbol, char goalSymbol, char wallSymbol, char emptySymbol) {
        sizeField = sizeFieldArg;
        gameField = new char[sizeField][sizeField];
        random = new Random();
        ChaseLogic logic = new ChaseLogic(countEnemies, playerSymbol, enemySymbol, wallSymbol, goalSymbol, emptySymbol);
        do {
            for (int i = 0; i < sizeField; ++i) {
                for (int j = 0; j < sizeField; ++j) {
                    gameField[i][j] = emptySymbol;
                }
            }

            setPositionPlayer(playerSymbol);
            setPositionEnemy(countEnemies, enemySymbol, emptySymbol);
            setPositionWall(countWalls, wallSymbol, emptySymbol);
            setPositionGoal(goalSymbol, emptySymbol);
        } while (logic.isCorrect(gameField, posYPlayer, posXPlayer, posYGoal, posXGoal));

        return gameField;
    }

    private static void setPositionPlayer(char playerSymbol) {
        posYPlayer = random.nextInt(sizeField);
        posXPlayer = random.nextInt(sizeField);
        gameField[posYPlayer][posXPlayer] = playerSymbol;
    }

    private static void setPositionEnemy(int countEnemies, char enemySymbol, char emptySymbol) {
        for (int i = 0; i < countEnemies; ++i) {
            int y = random.nextInt(sizeField);
            int x = random.nextInt(sizeField);
            if (gameField[y][x] == emptySymbol) {
                gameField[y][x] = enemySymbol;
            } else {
                --i;
            }
        }
    }

    private static void setPositionWall(int countWalls, char wallSymbol, char emptySymbol) {
        for (int i = 0; i < countWalls; ++i) {
            int y = random.nextInt(sizeField);
            int x = random.nextInt(sizeField);
            if (gameField[y][x] == emptySymbol) {
                gameField[y][x] = wallSymbol;
            } else {
                --i;
            }
        }
    }

    private static void setPositionGoal(char goalSymbol, char emptySymbol) {
        while (true) {

            posYGoal = random.nextInt(sizeField);
            posXGoal = random.nextInt(sizeField);
            if (gameField[posYGoal][posXGoal] == emptySymbol) {
                gameField[posYGoal][posXGoal] = goalSymbol;

                break;
            }
        }
    }
}
