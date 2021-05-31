import com.jaeheonshim.chessboard.Board;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class Game implements Serializable {
    private Board game_board;
    private boolean white_turn;
    private transient GameGUI gui;
    private Date beginDate;
    private int playTime;

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
    public Date getBeginDate () {
        return beginDate;
    }
    public int getPlayTime () {
        return playTime;
    }
    public void setPlayTime (int playTime) {
        this.playTime = playTime;
    }

    private static final long serialVersionUID = 123456;

    public Game(){
        game_board = new Board();
        white_turn = true;
        beginDate = new Date();
    }

    public void restart(){
        gui.getMainPanel().setFiftyRuleOpportunity(false);
        gui.getMovesList().setListData(new Vector<>());
        white_turn = true;
        game_board.resetBoard();
        gui.getChessPanel().update();
    }
}
