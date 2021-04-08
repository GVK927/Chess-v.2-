import javax.swing.*;
import java.awt.*;

public class GameGUI extends JFrame {
    public static final int WIDTH = 758;
    public static final int HEIGHT = 758;
    private ChessPanel chessPanel;

    private Game game;

    public GameGUI(Game game){
        this.game = game;

        setTitle("Chess");
        setBounds(200, 200, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        pack();
        setVisible(true);
    }

    private void initComponents(){
        chessPanel = new ChessPanel(game);
        getContentPane().add(chessPanel, BorderLayout.CENTER);
    }

    public ChessPanel getChessPanel () {
        return chessPanel;
    }
}
