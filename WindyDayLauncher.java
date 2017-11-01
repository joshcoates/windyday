package windyday;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/*
 * Josh Coates - Windy Day Game Launcher
 */

public class WindyDayLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Windy Day");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        WindyDayGame game = new WindyDayGame();
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
        game.startGame();
    }
}
