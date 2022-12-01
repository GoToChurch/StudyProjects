package game_2048;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Contoller contoller = new Contoller(model);

        JFrame game = new JFrame();
        game.setTitle("2048");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(450, 500);
        game.setResizable(false);

        game.add(contoller.getView());

        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }

}
