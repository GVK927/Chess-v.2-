import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.Vector;

public class MainPanel {
    private JList movesList;
    private JPanel chessPanelContainer;
    private JPanel rootPanel;
    private JButton newBtn;
    private JButton openBtn;
    private JButton saveBtn;
    private ChessPanel chessPanel;
    private JFileChooser fileChooser;

    public MainPanel(Game game){
        this.chessPanel = new ChessPanel(game);
        chessPanelContainer.setLayout(null);
        chessPanelContainer.add(chessPanel);
        movesList.setListData(new Vector());
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Файл сохранения .dat", ".dat"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        newBtn.addActionListener(l->{
            game.restart();
        });
        openBtn.addActionListener(l->{
            fileChooser.setDialogTitle("Загрузка игры");
            if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){

            }
        });
        saveBtn.addActionListener(l->{
            fileChooser.setDialogTitle("Сохранение игры");
            if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){

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
