import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class GameGUI extends JFrame{
    public static final int WIDTH = 758;
    public static final int HEIGHT = 758;
    private MainPanel mainPanel;

    private Game game;

    public GameGUI(Game game){
        this.game = game;
        this.mainPanel = new MainPanel(game, this);

        try {
            setIconImage(ImageIO.read(new File("imgs/icon.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
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
    public MainPanel getMainPanel () {
        return mainPanel;
    }

    public void load(File file){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    this.game = (Game) ois.readObject();
                    this.game.setGui(this);
                    this.mainPanel = new MainPanel(this.game, this);
                    this.mainPanel.setFiftyRuleOpportunity(this.game.getGameBoard().getHalfmovesCount()>=50);
                    this.mainPanel.setTimerWhenLoaded();
                    setContentPane(this.mainPanel.getRootPanel());
                    revalidate();
                    repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void load(){
            this.mainPanel = new MainPanel(this.game, this);
            this.mainPanel.setFiftyRuleOpportunity(this.game.getGameBoard().getHalfmovesCount()>=50);
            this.mainPanel.setTimerWhenLoaded();
            setContentPane(this.mainPanel.getRootPanel());
            revalidate();
            repaint();
    }
}
