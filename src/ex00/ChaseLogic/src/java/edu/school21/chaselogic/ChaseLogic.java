package edu.school21.chaselogic;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class ChaseLogic {

    private final int countEnemies;
    private final int INF = (int) 1e9;
    private final char playerSymbol;
    private final char enemySymbol;
    private final char wallSymbol;
    private final char goalSymbol;
    private final char emptySymbol;
    private char[][] gameField;
    private char[][] copyGameField;
    private int sizeField;

    public ChaseLogic(int countEnemies, char playerSymbol, char enemySymbol, char wallSymbol, char goalSymbol, char emptySymbol) {
        this.playerSymbol = playerSymbol;
        this.enemySymbol = enemySymbol;
        this.wallSymbol = wallSymbol;
        this.goalSymbol = goalSymbol;
        this.emptySymbol = emptySymbol;
        this.countEnemies = countEnemies;
    }

    public void enemyToPlayer(char[][] gameField) {
        this.sizeField = gameField.length;
        this.gameField = gameField;
        copyGameField = new char[sizeField][sizeField];
        for (int i = 0; i < sizeField; ++i) {
            copyGameField[i] = Arrays.copyOf(this.gameField[i], sizeField);
        }
        int startX, startY;
        for (int i = 0; i < sizeField; ++i) {
            for (int j = 0; j < sizeField; ++j) {
                if (copyGameField[i][j] == playerSymbol) {
                    startX = j;
                    startY = i;
                    bfs(startX, startY);
                }
            }
        }
    }

    public boolean isCorrect(char[][] gameFieldArg, int startY, int startX, int finishY, int finishX) {
        gameField = gameFieldArg;
        sizeField = gameField.length;
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        int[][] dist = new int[sizeField][sizeField];
        final int[] dy = {-1, 0, 1, 0};
        final int[] dx = {0, 1, 0, -1};
        for (int i = 0; i < sizeField; ++i) {
            Arrays.fill(dist[i], INF);
        }
        dist[startY][startX] = 0;
        q.add(new Pair<>(startY, startX));
        while (!q.isEmpty()) {
            Pair<Integer, Integer> coordinates = q.poll();
            int y = coordinates.getY();
            int x = coordinates.getX();
            for (int i = 0; i < dy.length; ++i) {
                int ty = y + dy[i];
                int tx = x + dx[i];
                if (0 <= tx && 0 <= ty && ty < sizeField && tx < sizeField && gameField[ty][tx] != wallSymbol && gameField[y][x] != enemySymbol && dist[ty][tx] > dist[y][x] + 1) {
                    dist[ty][tx] = dist[y][x] + 1;
                    q.add(new Pair<>(ty, tx));
                }
            }
        }
        return (dist[finishY][finishX] == INF);
    }

    private void bfs(int startX, int startY) {
        for (int j = 0; j < countEnemies; ++j) {
            simpleBfs(startY, startX);
        }
    }


    private void simpleBfs(int startY, int startX) {
        @SuppressWarnings("unchecked")
        Pair<Integer, Integer>[][] from = (Pair<Integer, Integer>[][]) new Pair[sizeField][sizeField];
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        int[][] dist = new int[sizeField][sizeField];
        final int[] dy = {-1, 0, 1, 0};
        final int[] dx = {0, 1, 0, -1};
        for (int i = 0; i < sizeField; ++i) {
            Arrays.fill(dist[i], INF);
            Arrays.fill(from[i], new Pair<>(-1, -1));
        }
        dist[startY][startX] = 0;
        q.add(new Pair<>(startY, startX));
        int finishY = -1, finishX = -1;
        while (!q.isEmpty()) {
            Pair<Integer, Integer> coordinates = q.poll();
            int y = coordinates.getY();
            int x = coordinates.getX();
            for (int i = 0; i < dy.length; ++i) {
                int ty = y + dy[i];
                int tx = x + dx[i];
                if (0 <= tx && 0 <= ty && ty < sizeField && tx < sizeField && gameField[ty][tx] != wallSymbol && gameField[ty][tx] != goalSymbol && gameField[y][x] != enemySymbol && dist[ty][tx] > dist[y][x] + 1) {
                    dist[ty][tx] = dist[y][x] + 1;
                    q.add(new Pair<>(ty, tx));
                    from[ty][tx] = new Pair<>(y, x);
                    if (copyGameField[ty][tx] == enemySymbol && finishY == -1 && finishX == -1) {
                        finishY = ty;
                        finishX = tx;
                    }
                }
            }
        }
        moveEnemy(dist, from, finishY, finishX);
    }

    private void moveEnemy(int[][] dist, Pair<Integer, Integer>[][] from, int finishY, int finishX) {
        int y = from[finishY][finishX].getY();
        int x = from[finishY][finishX].getX();
        int maxLength = dist[finishY][finishX];
        while (y != -1 && x != -1) {
            Pair<Integer, Integer> point = from[y][x];
            if (dist[y][x] == maxLength - 1) {
                gameField[y][x] = enemySymbol;
                gameField[finishY][finishX] = emptySymbol;
                copyGameField[finishY][finishX] = emptySymbol;
            }
            y = point.getY();
            x = point.getX();
        }
    }

    static class Pair<Y, X> {
        private final X x;
        private final Y y;

        public Pair(Y y, X x) {
            this.y = y;
            this.x = x;
        }

        public X getX() {
            return x;
        }

        public Y getY() {
            return y;
        }
    }
}
