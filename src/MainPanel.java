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
    private ChessPanel chessPanel;
    private JFileChooser fileChooser;
    private Game game;

    public MainPanel (Game game) {
        this.game = game;
        this.chessPanel = new ChessPanel(game);
        chessPanelContainer.setLayout(null);
        chessPanelContainer.add(chessPanel);
        movesList.setListData(new Vector());
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
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()))) {
                    GameGUI gui = game.getGui().cl
                    this.game = (Game) ois.readObject();
                    this.game.setGui(this.gui);
                    this.gui.setMainPanel(new MainPanel(this.game));
                    System.out.println("2");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
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

}
