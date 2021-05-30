import javax.swing.*;

public class GameGUI extends JFrame{
    public static final int WIDTH = 758;
    public static final int HEIGHT = 758;
    private MainPanel mainPanel;

    private Game game;

    public GameGUI(Game game){
        this.game = game;
        this.mainPanel = new MainPanel(game);

        setTitle("Chess");
        setBounds(200, 200, WIDTH, HEIGHT);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(){
        setContentPane(mainPanel.getRootPanel());
    }

    public ChessPanel getChessPanel () {
        return mainPanel.getChessPanel();
    }
    public JList<String> getMovesList(){
        return mainPanel.getMovesList();
    }
    public void setMainPanel (MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
