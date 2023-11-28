import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class Frame extends JFrame {
    private final gameField game;
    Frame() {
        super("Крестики нолики");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // верхняя строка
        JMenuBar menuBar = new JMenuBar();
        // пункт меню Игра
        JMenu menuGame = new JMenu("Игра");
        // пункт меню О проекте
        JMenu menuAbout = new JMenu("О проекте");
        // подпункт Новая игра
        JMenu menuGameStart = new JMenu("Новая игра");
        // подподпункт Игра против ПК
        JMenuItem itemStartvsPC = new JMenuItem("Играть с ПК");
        itemStartvsPC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.chckVsPCGame(true);
                game.Start();
            }
        });
        // подподпункт Игра против друга
        JMenuItem itemStartP2P = new JMenuItem("Играть с другом");
        itemStartP2P.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                game.chckVsPCGame(false);
                game.Start();
            }
        });
        // подпункт выход
        JMenuItem itemExit = new JMenuItem("Выход");
        itemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        // подпункт о проекте
        JMenuItem menuRules = new JMenuItem("Правила игры");
        menuRules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("JOptionPane showMessageDialog");
                JOptionPane.showMessageDialog(frame,"Игроки по очереди ставят на свободные клетки поля 3×3,\n" +
                                "<br>знаки (один всегда крестики, другой всегда нолики).\n" +
                                "Первый, выстроивший в ряд 3 своих фигуры по вертикали,\n" +
                                "горизонтали или большой диагонали, выигрывает.\n\n"+
                                "играть можно с другом или против ПК.",
                        "Правила игры", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // подпункт об авторе
        JMenuItem menuAutor = new JMenuItem("Автор");
        menuAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("JOptionPane showMessageDialog");
                JOptionPane.showMessageDialog(frame,
                        "Автор: Галкин Валерий Николаевич",
                        "Автор", JOptionPane.INFORMATION_MESSAGE);


            }
        });
        // собираем меню
        menuGameStart.add(itemStartvsPC);
        menuGameStart.add(itemStartP2P);
        menuGame.add(menuGameStart);
        menuGame.add(itemExit);
        menuAbout.add(menuRules);
        menuAbout.add(menuAutor);
        menuBar.add(menuGame);
        menuBar.add(menuAbout);
        setJMenuBar(menuBar);
        game = new gameField();
        game.AllButtons(false);
        add(game);

        // картинка
        try {
            setIconImage(ImageIO.read(getClass().getResource("image/tictactoe.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // отображение главного окна
        setSize(500, 500);
        setVisible(true);
        setResizable(false);
    }

}