import javax.swing.*;
import java.util.ArrayList;
import java.util.Vector;

public class MainPanel {
    private JList movesList;
    private JPanel chessPanelContainer;
    private JPanel rootPanel;
    private ChessPanel chessPanel;

    public MainPanel(Game game){
        this.chessPanel = new ChessPanel(game);
        chessPanelContainer.setLayout(null);
        chessPanelContainer.add(chessPanel);
        movesList.setListData(new Vector());
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
