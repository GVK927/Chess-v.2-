import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Vector;

public class MainPanel{
    private JList movesList;
    private JPanel chessPanelContainer;
    private JPanel rootPanel;
    private JButton newBtn;
    private JButton openBtn;
    private JButton saveBtn;
    private JButton fiftyRuleBtn;
    private JLabel moveMessageLabel;
    private ChessPanel chessPanel;
    private JFileChooser fileChooser;
    private Game game;
    private GameGUI gui;

    public MainPanel (Game game, GameGUI gui) {
        this.game = game;
        this.gui = gui;
        this.chessPanel = new ChessPanel(game);
        chessPanelContainer.setLayout(null);
        chessPanelContainer.add(chessPanel);
        movesList.setListData(new Vector());
        moveMessageLabel.setText("Следующий ход: " + (game.isWhite_turn()?"белых":"черных"));
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Файл сохранения .dat", "dat"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File("Saves"));

        newBtn.addActionListener(l -> {
            this.game.restart();
        });
        openBtn.addActionListener(l -> {
            fileChooser.setDialogTitle("Загрузка игры");
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                this.gui.load(fileChooser.getSelectedFile());
            }
        });
        saveBtn.addActionListener(l -> {
            fileChooser.setDialogTitle("Сохранение игры");
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream((fileChooser.getSelectedFile().getPath() + ".dat")))) {
                    oos.writeObject(game);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("shit not working");
                }
            }
        });
        fiftyRuleBtn.addActionListener(l ->{
            new WinDialog(this.game);
        });
    }

    public void setFiftyRuleOpportunity(boolean flag){
        fiftyRuleBtn.setEnabled(flag);
    }

    public JPanel getRootPanel () {
        return rootPanel;
    }
    public ChessPanel getChessPanel () {
        return chessPanel;
    }
    public JList getMovesList () {
        return movesList;
    }
    public JLabel getMoveMessageLabel () {
        return moveMessageLabel;
    }
}
