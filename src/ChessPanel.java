import com.jaeheonshim.chessboard.*;
import com.jaeheonshim.chessboard.piece.EnPassant;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ChessPanel extends JPanel{

    private BufferedImage board_img;
    private Spot[][] pieces;
    private HashMap<String, BufferedImage> pieces_images;
    private Board board;
    private Game game;

    private BufferedImage selection_frame;
    private BufferedImage possible_move_frame;
    private BufferedImage attack_frame;
    private int selected_x;
    private int selected_y;

    {
        try{
            selected_x = -1;

            board_img = ImageIO.read(new File("imgs\\board.png"));
            pieces_images = new HashMap<>();
            pieces_images.put("b", ImageIO.read(new File("imgs\\Black_bishop.png")));
            pieces_images.put("k", ImageIO.read(new File("imgs\\Black_king.png")));
            pieces_images.put("n", ImageIO.read(new File("imgs\\Black_knight.png")));
            pieces_images.put("p", ImageIO.read(new File("imgs\\Black_pawn.png")));
            pieces_images.put("q", ImageIO.read(new File("imgs\\Black_queen.png")));
            pieces_images.put("r", ImageIO.read(new File("imgs\\Black_rook.png")));
            pieces_images.put("B", ImageIO.read(new File("imgs\\White_bishop.png")));
            pieces_images.put("K", ImageIO.read(new File("imgs\\White_king.png")));
            pieces_images.put("N", ImageIO.read(new File("imgs\\White_knight.png")));
            pieces_images.put("P", ImageIO.read(new File("imgs\\White_pawn.png")));
            pieces_images.put("Q", ImageIO.read(new File("imgs\\White_queen.png")));
            pieces_images.put("R", ImageIO.read(new File("imgs\\White_rook.png")));

            attack_frame = ImageIO.read(new File("imgs\\Attack_frame.png"));
            selection_frame = ImageIO.read(new File("imgs\\Selection_frame.png"));
            possible_move_frame = ImageIO.read(new File("imgs\\Possible_move_frame.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(board_img,0, 0, null);

        for(int i = 0; i<pieces.length; i++){
            for(int j = 0; j<pieces.length; j++){
                if(pieces[pieces.length-j-1][i].getPiece() instanceof EnPassant) continue;
                g.drawImage(pieces_images.get(pieces[pieces.length-j-1][i].toString()), translateCoords(i), translateCoords(j), null);
            }
        }

        if(selected_x!=-1) {
            g.drawImage(selection_frame, translateCoords(selected_x), getHeight() - translateCoords(selected_y + 1), null);
            for(int i = 0; i<pieces.length; i++){
                for(int j = 0; j<pieces[i].length; j++) {
                    if (! (pieces[j][i].getPiece() == null || pieces[j][i].getPiece().isWhite() == pieces[selected_y][selected_x].getPiece().isWhite()) && pieces[selected_y][selected_x].getPiece().canMove(board, pieces[j][i])){
                        g.drawImage(attack_frame, translateCoords(i), getHeight() - translateCoords(j + 1), null);
                        continue;
                    }
                    if(pieces[selected_y][selected_x].getPiece().canMove(board, pieces[j][i])) g.drawImage(possible_move_frame, translateCoords(i), getHeight() - translateCoords(j + 1), null);
                }
            }
        }
        //System.out.println(board.toString());
    }

    private int translateCoords(int c){
        //18 - ширина краев доски, 90 - размер клетки
        return 18+c*90;
    }
    private int translateChessCoords(int c){
        return (c-18)/90;
    }

    public ChessPanel(Game game){
        this.game = game;
        this.board = game.getGameBoard();
        this.pieces = board.getBoard();

        setBounds(0, 0, GameGUI.HEIGHT, GameGUI.HEIGHT);
        setLayout(null);
        setFocusable(true);
        setPreferredSize(new Dimension(GameGUI.WIDTH, GameGUI.HEIGHT));

        addMouseListener(new MouseListener() {
            boolean isBeginning = true;
            private int x1, y1;
            @Override
            public void mouseReleased (MouseEvent e) {
                if(isBeginning) {
                    if(pieces[translateChessCoords(getHeight()-e.getY())][translateChessCoords(e.getX())].getPiece()==null) return;
                    if(pieces[translateChessCoords(getHeight()-e.getY())][translateChessCoords(e.getX())].getPiece() instanceof EnPassant) return;
                    if(pieces[translateChessCoords(getHeight()-e.getY())][translateChessCoords(e.getX())].getPiece().isWhite()!=game.isWhite_turn()) return;
                    x1 = translateChessCoords(e.getX());
                    y1 = translateChessCoords(getHeight()-e.getY());
                    isBeginning = false;
                    selected_x = x1;
                    selected_y = y1;
                    repaint();
                    return;
                }
                if(board.move(pieces[y1][x1], pieces[translateChessCoords(getHeight()-e.getY())][translateChessCoords(e.getX())]))
                game.setWhite_turn(!game.isWhite_turn());
                isBeginning = true;
                selected_x = -1;
                repaint();
                if(board.getKing(true).inCheckmate(board))
                    new WinDialog(game, false);
                if(board.getKing(false).inCheckmate(board))
                    new WinDialog(game, true);
                System.out.println(board.getFenRecord());
            }

            @Override
            public void mousePressed (MouseEvent e) {

            }
            @Override
            public void mouseClicked (MouseEvent e) {

            }
            @Override
            public void mouseEntered (MouseEvent e) {

            }
            @Override
            public void mouseExited (MouseEvent e) {

            }
        });
    }

    public void update(){
        this.board = this.game.getGameBoard();
        this.pieces = board.getBoard();
        repaint();
    }
}
