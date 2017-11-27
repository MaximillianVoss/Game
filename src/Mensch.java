import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Александр on 26.11.2017.
 */
public class Mensch extends Aerger {
    private static JPanel btnPanel;
    private static JButton btnMove;
    private static JButton btnThrow;
    private static JLabel lblNum;
    private static JLabel lblPlayer;
    private static JLabel lblItemNum;
    private static JTextField txbItem;
    private static JComboBox combo;
    private static int dice = -1;
    //yellow-0,blue-1,red-2,green-3
    private static int playerNum = 0;
    private static int playersCount = 4;
    private static int playerWin = -1;

    private static ArrayList<int[]> players = new ArrayList<>();

    public static void paintField() {
        if (players.size() >= playersCount)
            paintField(players.get(0), players.get(1), players.get(2), players.get(3));
    }

    private static JButton InitBtn(JButton btn, String text) {
        btn = new JButton();
        btn.setText(text);
        btn.setPreferredSize(new Dimension(150, 50));
        return btn;
    }

    public static String ShowWho() {
        return ShowWho(playerNum);
    }

    public static String ShowWho(int playerNum) {
        String[] names = {"yellow", "blue", "red", "green"};
        String str = "";
        if (playerNum > -1 && playerNum < names.length)
            return names[playerNum] + " player's turn";
        else
            return "UNKNOWN! CHECK YOU CODE!";
    }

    public static void OnThrow(int min, int max) {
        dice = min + (int) (Math.random() * max);
        //dice = 1;
        btnThrow.setText("Dice value:" + String.valueOf(dice));
    }

    public static void PutHome(int position, int curPLayer) {
        for (int i = 0; i < players.size(); i++) {
            if (i != curPLayer)
                for (int j = 0; j < players.get(i).length; j++) {
                    if (position == players.get(j)[j])
                        players.get(j)[j] = -1;
                }
        }
    }

    public static boolean CheckVictory() {
        for (int i = 0; i < playersCount; i++) {
            int counter = 0;
            for (int j = 0; j < players.get(i).length; j++)
                if (players.get(i)[j] == -1)
                    counter++;
            if (counter == players.get(i).length)
                playerWin = i;
            return true;
        }
        return false;
    }


    public static void OnMove() {
        try {
            int item = ((int) combo.getSelectedItem());
            if (dice != -1) {
                if (item < 5 && item > 0) {
                    int newPos = players.get(playerNum)[item - 1] + dice;
                    int index = -1;
                    for (int i = 0; i < players.get(playerNum).length; i++)
                        if (players.get(playerNum)[i] == newPos)
                            index = i;
                    if (index == -1) {

                        PutHome(newPos, playerNum);
                        players.get(playerNum)[item - 1] += dice;
                        players.get(playerNum)[item - 1] = players.get(playerNum)[item - 1] % 40;
                        playerNum += 1;
                        playerNum = playerNum % playersCount;
                        lblPlayer.setText(ShowWho());
                        if (CheckVictory())
                            btnMove.setText("Player " + ShowWho() + "  has won ");
                        paintField();
                        btnMove.setText("Throw dice!");
                        dice = -1;
                        btnThrow.setText("Throw dice again!");
                    } else
                        btnMove.setText("You can't go here!");
                    //btnMove.setText("Throw dice!");
                } else
                    btnMove.setText("Enter item to move!");
            } else
                btnMove.setText("Throw dice first!");
        } catch (Exception ex) {
            btnMove.setText(ex.getMessage());
        }

    }

    public static void AddButtons() {
        if (btnPanel != null)
            btnPanel.removeAll();
        else {
            btnPanel = new JPanel();
            btnPanel.setVisible(true);
            btnPanel.setBounds(0, 800, 800, 50);
        }
        btnMove = InitBtn(btnMove, "Make move");
        btnMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnMove();
            }
        });
        btnThrow = InitBtn(btnThrow, "Throw dice");
        btnThrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnThrow(1, 6);
            }
        });
        lblPlayer = new JLabel();
        lblPlayer.setText(ShowWho());
        lblItemNum = new JLabel();
        lblItemNum.setText("Input number of item to move");
        btnPanel.add(lblPlayer);
        btnPanel.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);
        btnPanel.add(lblItemNum);
        combo = new JComboBox();
        for (int i = 1; i <= 4; i++)
            combo.addItem(i);
        btnPanel.add(combo);
        btnPanel.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);

        btnPanel.add(btnMove);
        btnPanel.add(btnThrow);

        myFrame.add(btnPanel, BorderLayout.SOUTH);
        myFrame.add(pan);
        myFrame.repaint();
        myFrame.revalidate();
    }

    void UpdateFiled() {
        paintField();
        AddButtons();
    }

    boolean InGame(int[] p) {
        for (int i = 0; i < p.length; i++)
            if (p[i] != -1)
                return true;
        return false;
    }

    Mensch(int[] p1, int[] p2, int[] p3, int[] p4) {
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        for (int[] item : players)
            if (!InGame(item))
                playersCount--;
        UpdateFiled();
    }

    Mensch(ArrayList<int[]> _players) {
        players = _players;
    }


}
