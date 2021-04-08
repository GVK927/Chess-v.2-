import com.jaeheonshim.chessboard.Board;

public class Game {
    private Board game_board;
    private boolean white_turn;
    private GameGUI gui;

    public Board getGameBoard () {
        return game_board;
    }
    public boolean isWhite_turn () {
        return white_turn;
    }
    public void setWhite_turn (boolean white_turn) {
        this.white_turn = white_turn;
    }
    public void setGui (GameGUI gui) {
        this.gui = gui;
    }
    public GameGUI getGui () {
        return gui;
    }

    public Game(){
        game_board = new Board();
        white_turn = true;
        gui = new GameGUI(this);
    }

    public void restart(){
        game_board.resetBoard();
        System.out.println(game_board.toString());
        gui.getChessPanel().update();
    }
}
