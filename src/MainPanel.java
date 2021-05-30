import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class MainPanel {
    private JList movesList;
    private JPanel chessPanelContainer;
    private JPanel rootPanel;
    private JButton newBtn;
    private JButton openBtn;
    private JButton saveBtn;
    private ChessPanel chessPanel;

    public MainPanel(Game game){
        this.chessPanel = new ChessPanel(game);
        chessPanelContainer.setLayout(null);
        chessPanelContainer.add(chessPanel);
        movesList.setListData(new Vector());

        newBtn.addActionListener(l->{
            game.restart();
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
