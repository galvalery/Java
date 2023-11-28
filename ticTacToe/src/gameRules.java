import javax.swing.*;

/**
 * Класс, в котором происходит сам процесс игры, то есть заполнение панели, запись каждого хода и результат игры.
 */
public class gameRules {
    private gameField Pan;
    private final int[][] PlayBoard; // массив для заполнения и дальнейшей проверки
    private final int Length = gameField.fieldLength;
    private final int ALL_MOVES = Length * Length;
    private static final int DOTS_TO_WIN = 3;        // сколько ячеек нужно подряд заполнить, чтобы победить

    /**
     * Заполняем массив, являющийся нашим полем, нулями
     * @param B панель, на которой мы играем
     */
    gameRules(gameField B) {
        PlayBoard = new int[Length][Length];
        Pan = B;
        for (int i = 0; i < Length; i++) {
            for (int j = 0; j < Length; j++) {
                PlayBoard[i][j] = 0;
            }
        }
    }
    /**
     * проверяем победил ли текущий игрок
     * @param playerDot текущий игрок
     */
    private boolean isWin(int playerDot) {
        int hor, ver;
        int diagMain, diagReverse;
        for (int i = 0; i < Length; i++) {
            hor = 0;
            ver = 0;
            for (int j = 0; j < Length; j++) {
                // проверяем горизонтальные линии на возможную победу
                if (PlayBoard[i][j] == playerDot)
                    hor++;
                else if (PlayBoard[i][j] != playerDot && hor < DOTS_TO_WIN)
                    hor = 0;
                // проверяем вертикальные линии на возможную победу
                if (PlayBoard[j][i] == playerDot)
                    ver++;
                else if (PlayBoard[j][i] != playerDot && ver < DOTS_TO_WIN)
                    ver = 0;
            }
            if (hor >= DOTS_TO_WIN || ver >= DOTS_TO_WIN)
                return true;
        }

        for (int j = 0; j < Length; j++) {
            diagMain = 0;
            for (int i = 0; i < Length; i++) {
                int k = j + i;
                if (k < Length) {
                    // проверяем главную диагональ от центральной оси вправо на возможную победу
                    if (PlayBoard[i][k] == playerDot)
                        diagMain++;
                    else if (PlayBoard[i][k] != playerDot && diagMain < DOTS_TO_WIN)
                        diagMain = 0;
                }
                if (diagMain >= DOTS_TO_WIN)
                    return true;
            }
        }
        for (int j = 1; j < Length; j++) {
            diagMain = 0;
            for (int i = 0; i < Length; i++) {
                int k = j + i;
                if (k < Length) {
                    // проверяем главную диагональ от центральной оси вниз на возможную победу
                    if (PlayBoard[k][i] == playerDot)
                        diagMain++;
                    else if (PlayBoard[k][i] != playerDot && diagMain < DOTS_TO_WIN)
                        diagMain = 0;
                }
                if (diagMain >= DOTS_TO_WIN)
                    return true;
            }
        }
        for (int j = 0; j < Length; j++) {
            diagReverse = 0;
            for (int i = 0; i < Length; i++) {
                int k = (Length - 1) - i;
                int l = j + i;
                if (k >= 0 && l < Length)
                    // проверяем побочную диагональ от центральной оси вниз на возможную победу
                    if (PlayBoard[l][k] == playerDot)
                        diagReverse++;
                    else if (PlayBoard[l][k] != playerDot && diagReverse < DOTS_TO_WIN)
                        diagReverse = 0;
                if (diagReverse >= DOTS_TO_WIN)
                    return true;
            }
        }
        for (int j = 1; j < Length; j++) {
            diagReverse = 0;
            for (int i = 0; i < Length; i++) {
                int k = (Length - 1) - j - i;
                if (k >= 0)
                    // проверяем побочную диагональ от центральной оси влево на возможную победу
                    if (PlayBoard[i][k] == playerDot)
                        diagReverse++;
                    else if (PlayBoard[i][k] != playerDot && diagReverse < DOTS_TO_WIN)
                        diagReverse = 0;
                if (diagReverse >= DOTS_TO_WIN)
                    return true;
            }
        }
        return false;
    }

    /**
     * проверяем не закончилось ли количество ходов
     * если закончились, то ничья
     * @param cntMoves текущий игрок
     */
    private boolean isDraw(int cntMoves) {
        return cntMoves == ALL_MOVES;
    }
    /**
     * Записываем каждый ход и проверяем не окончилась ли игра
     * @param i координаты нашего хода
     * @param j координаты нашего хода
     * @param Check номер игрока, который сходил
     * @param DrawCheck счетчик, который помогает проверить ничью
     */
    public int InArray(int i, int j, int Check, int DrawCheck) {
        PlayBoard[i][j] = Check;
        if (isWin(Check)){
            JOptionPane.showMessageDialog(Pan, "Победил игрок " + Check);
            return 1;
        }
        else if (isDraw(DrawCheck)) {
            JOptionPane.showMessageDialog(Pan, "Победила дружба");
            return 1;
        }
        else
            return 0;
    }
}