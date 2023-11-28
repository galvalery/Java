import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.stream.IntStream;
import javax.swing.*;

public class gameField extends JPanel implements ActionListener {
    private final JButton[][] buttons;// массив кнопок
    private gameRules GArray; // класс игры
    private boolean AIisActive = false; // включен ли режим игры против ПК
    public static int fieldLength = 3; // размер игрового поля
    private int PlayerMarker; // обозначение игрока
    private int chkEnd; // проверка конца игры
    private static int MovesCnt; // счетчик ходов для ничьей

    /**
     * Меняем игроков после хода
     * @param currentPlayer, который сходил.
     */
    private int switchPlayer(int currentPlayer) {
        if (currentPlayer == 2) {
            PlayerMarker = 1;
            return 1;
        } else {
            PlayerMarker = 2;
            return 2;
        }
    }
    /**
     * Делаем кнопку неактивной
     * @param Btn кнопка, которую нужно сделать неактивной.
     */
    private void disableButton(JButton Btn) {
        Btn.setEnabled(false);
    }
    /**
     * Для игры против ПК
     * если x и y находятся в допустимых пределах И если ячейка не равна пустой, то возвращаем true
     * @param x - координаты для проверки бота по горизонтали.
     * @param y - координаты для проверки бота по вертикали.
     */
    private boolean isCellAvailable(int x, int y) {
        return x >= 0 && x < fieldLength
                && y >= 0 && y < fieldLength
                && buttons[x][y].isEnabled();
    }
    /**
     * Для игры против ПК
     * Ход компьютера
     */
    private void AIMoves() {
        int x, y;
        do {
            x = new Random().nextInt(fieldLength);
            y = new Random().nextInt(fieldLength);
        } while (!isCellAvailable(x, y));
        pressButton(buttons[x][y]);
    }

    /**
     * Делаем все кнопки активными или нет, зависит от этапа игры
     * @param curStatus переменная, показывающая, в какое состояние нужно перевести кнопки.
     */
    public void AllButtons(boolean curStatus) {
        for (JButton[] button : buttons)
            for (JButton jButton : button) jButton.setEnabled(curStatus);
    }

    /**
     * Заполняем пустым текстом клетки
     */
    private void clearField() {
        for (JButton[] button : buttons)
            for (JButton jButton : button) {
                jButton.setText("");
            }
    }

    /**
     * Пишем крестики-нолики в клетки
     * @param Btn клетка, в которую нужно написать
     * @param Player игрок, который сходил
     */
    private void addSign(JButton Btn, int Player) {
        Btn.setFont(new Font("Arial", Font.BOLD, 200/fieldLength));
        if (Player == 2)
            Btn.setText("O");
        else if (Player == 1)
            Btn.setText("X");
    }

    /**
     * заполнение локальной переменной
     * @param gameVsPC игра против ПК, если false, то играем с другом
     */
    public void chckVsPCGame(boolean gameVsPC) {
        AIisActive = gameVsPC;
    }

    /**
     начало новой игры
     */
    public void Start() {
        GArray = new gameRules(this);
        clearField();
        AllButtons(true);
        PlayerMarker = 1;
        MovesCnt = 0;
    }

    /**
     создаем панель и добавляем кнопки
     */
    gameField() {
        setLayout(new GridLayout(fieldLength, fieldLength));
        buttons = new JButton[fieldLength][fieldLength];
        for (int i=0; i < buttons.length; i++)
            for (int j=0; j < buttons[i].length; j++)
            {
                buttons[i][j] = new JButton("");
                if ((i+j)%2 == 0)
                    buttons[i][j].setBackground(new Color(234, 211, 140));
                else
                    buttons[i][j].setBackground(new Color(234, 228, 220));
            }
        Start();
        for (JButton[] button : buttons)
            IntStream.range(0, button.length).forEach(j -> {
                add(button[j]);
                button[j].addActionListener(this);
            });
    }
    /**
     * нажатие кнопки со всеми вытекаюющими
     * отключение кнопки
     * проверки на победу
     * занесение в массив
     * отметка на панели
     */
    private void pressButton (JButton Pressed){
        for (int i=0; i < buttons.length; i++)
            for (int j=0; j < buttons[i].length; j++) {
                if (Pressed == buttons[i][j]) {
                    addSign(Pressed, PlayerMarker);
                    disableButton(Pressed);
                    chkEnd = GArray.InArray(i, j, PlayerMarker, ++MovesCnt);
                    if (chkEnd == 1)
                        AllButtons(false);
                    PlayerMarker = switchPlayer(PlayerMarker);
                }
            }
    }

    /**
     реакция на нажатие кнопки
     */
    public void actionPerformed(ActionEvent E) {
        JButton Pressed = (JButton) E.getSource();
        pressButton(Pressed);
        if (chkEnd == 0 && AIisActive) {
            AIMoves();
        }
    }
}