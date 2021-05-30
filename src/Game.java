import com.jaeheonshim.chessboard.Board;

import java.io.Serializable;
import java.util.Vector;

public class Game implements Serializable {
    private Board game_board;
    private boolean white_turn;
    private transient GameGUI gui;

    public Board getGameBoard () {
        return game_board;
    }
    public boolean isWhite_turn () {
        return white_turn;
    }
    public void setWhite_turn (boolean white_turn) {
        this.white_turn = white_turn;
    }
    public GameGUI getGui () {
        return gui;
    }
    public void setGui (GameGUI gui) {
        this.gui = gui;
    }

    private static final long serialVersionUID = 123456;

    public Game(){
        game_board = new Board();
        white_turn = true;
    }

    public void restart(){
        gui.getMovesList().setListData(new Vector<>());
        white_turn = true;
        game_board.resetBoard();
        gui.getChessPanel().update();
    }
}
