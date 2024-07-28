package edu.school21.game;

public class Game {

    public static void main(String[] args) {
        try {
            GameLogic game = new GameLogic();
            game.play(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
