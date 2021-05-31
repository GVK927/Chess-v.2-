import javax.swing.*;
import java.awt.*;

public class WinDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel win_message;

    public WinDialog (Game game, boolean isWhiteWin) {
        /*DataBase.pushToDB(game, isWhiteWin);*/
        setTitle("Шах и мат!");
        setContentPane(contentPane);
        this.win_message.setText("Победа " + (isWhiteWin ? "белых" : "черных"));
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(300, 250));
        pack();
        setLocationRelativeTo(null);
        buttonOK.addActionListener(e -> {
            game.restart();
            dispose();
        });
        setResizable(false);
        setVisible(true);
        setFocusable(true);
    }
    public WinDialog (Game game) {
        setContentPane(contentPane);
        this.win_message.setText("Ничья!");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(300, 250));
        pack();
        setLocationRelativeTo(null);
        buttonOK.addActionListener(e -> {
            game.restart();
            dispose();
        });
        setResizable(false);
        setVisible(true);
        setFocusable(true);
    }
}
