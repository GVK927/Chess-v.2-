import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
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
    private JLabel gameTimer;
    private JButton timerBtn;
    private ChessPanel chessPanel;
    private JFileChooser fileChooser;
    private Game game;
    private GameGUI gui;
    private Timer timer;
    private DateFormat timerFormat;
    private int time;
    private boolean timerFlag;
    private ImageIcon pauseImg;
    private ImageIcon playImg;

    public MainPanel (Game game, GameGUI gui) {
        this.game = game;
        this.gui = gui;
        this.chessPanel = new ChessPanel(game);
        this.timerFormat = new SimpleDateFormat("HH:mm:ss");
        this.time = 0;
        this.timerFlag = false;
        timerFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        chessPanelContainer.setLayout(null);
        chessPanelContainer.add(chessPanel);
        movesList.setListData(new Vector());
        moveMessageLabel.setText("Следующий ход: " + (game.isWhite_turn()?"белых":"черных"));
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Файл сохранения .dat", "dat"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File("Saves"));

        try {
            this.playImg = new ImageIcon("imgs/play.png");
            this.pauseImg = new ImageIcon("imgs/pause.png");
        }catch (Exception e){
            e.printStackTrace();
        }

        this.timer = new Timer(1000, l->{
            this.gameTimer.setText("Времени прошло: "+timerFormat.format(new Date(time)));
            time+=1000;
        });
        timer.start();

        timerBtn.addActionListener(l -> {
            if(timerFlag){
                timerBtn.setIcon(pauseImg);
                timer.start();
            }else {
                timerBtn.setIcon(playImg);
                timer.stop();
            }
            chessPanel.setEnable(timerFlag);
            timerFlag = !timerFlag;
        });
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
                    game.setPlayTime(time);
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
    public void setTimerWhenLoaded(){
        this.timerBtn.setIcon(playImg);
        timerFlag = true;
        time = game.getPlayTime();
        this.gameTimer.setText("Времени прошло: "+timerFormat.format(new Date(time)));
        timer.stop();
        chessPanel.setEnable(false);
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
