import javax.swing.*;

public class Main {
    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Game();
        //TODO класс пешки 41-я строчка - починить первый ход(проверка на препятствие)
    }
}
