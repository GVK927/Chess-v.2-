import javax.swing.*;

public class Main{
    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Game game = new Game();
        GameGUI gui = new GameGUI(game);
        game.setGui(gui);
    }
}
