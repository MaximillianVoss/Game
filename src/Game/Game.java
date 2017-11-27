package Game;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class MiniJava {
    // textuelle Eingabe
    public static String readString(String text) {
        JFrame frame = new JFrame();
        String s = JOptionPane.showInputDialog(frame, text);
        frame.dispose();
        // frame.dispose() ist unter Java1.4 notwendig, um den Dialog
        // korrekt zu löschen. Ansonsten terminiert das Programm nicht.

        if (s == null)
            System.exit(0);
        return s;
    }

    public static String readString() {
        return readString("Eingabe:");
    }

    // Ganzzahlige Eingabe
    public static int readInt(String text) {
        JFrame frame = new JFrame();
        String s = JOptionPane.showInputDialog(frame, text);
        frame.dispose();

        int x = 0;
        if (s == null)
            System.exit(0);
        try {
            x = Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            x = readInt(text);
        }
        return x;
    }

    public static int readInt() {
        return readInt("Geben Sie eine ganze Zahl ein:");
    }

    public static int read(String text) {
        return readInt(text);
    }

    public static int read() {
        return readInt();
    }

    // Fließkomma Eingabe
    public static double readDouble(String text) {
        JFrame frame = new JFrame();
        String s = JOptionPane.showInputDialog(frame, text);
        frame.dispose();

        double x = 0;
        if (s == null)
            System.exit(0);
        try {
            x = Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            x = readDouble(text);
        }
        return x;
    }

    public static double readDouble() {
        return readDouble("Geben Sie eine Zahl ein:");
    }

    //
    // Ausgabe
    //
    public static void write(String output) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, output, "Ausgabe", JOptionPane.PLAIN_MESSAGE);
        frame.dispose();
    }

    public static void write(int output) {
        write("" + output);
    }

    public static void write(double output) {
        write("" + output);
    }
}

class Aerger extends MiniJava {
    private static class Field extends JPanel {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        static class Haus extends Field {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            int spieler;
            boolean besetzt;

            public Haus(boolean besetzt, int spieler, int steinnr) {
                super(-1, 0, steinnr);
                this.spieler = spieler;
                this.besetzt = besetzt;
            }

            public void paint(Graphics g) {
                super.paint(g);
                if (besetzt)
                    paintSpielstein(g, spieler, steinnr);
            }

        }

        int feldnummer, spieler, steinnr;
        Point p;

        public Field(int feldnummer, int spieler, int steinnr) {
            this.feldnummer = feldnummer;
            this.spieler = spieler;
            this.steinnr = steinnr;
            p = getLocation();
        }

        public void paint(Graphics g) {
            super.paint(g);
            // Hintergrund
            g.setColor(Color.BLACK);
            g.fillRect(p.getLocation().x, p.getLocation().y, getWidth() * 2, getHeight());
            // Feld
            Paint pa = ((Graphics2D) g).getPaint();
            g.setColor(Color.WHITE);
            GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, getWidth(), 0, Color.DARK_GRAY);
            ((Graphics2D) g).setPaint(gradient);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            int pad = getWidth() / 10;
            g.fillRect(p.getLocation().x + pad, p.getLocation().y + pad, (int) (getWidth() - 2 * pad),
                    (int) (getHeight() - 2 * pad));
            g.setColor(Color.BLACK);
            if (feldnummer >= 0)
                g.drawString("" + feldnummer, 2 * pad, 3 * pad);

            // Spielstein

            paintSpielstein(g, spieler, steinnr);
            ((Graphics2D) g).setPaint(pa);
        }

        public Dimension getPreferredSize() {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int square = (Math.min(dim.width, dim.height) - 20) / 15;
            return new Dimension(square, square);
        }

        protected void paintSpielstein(Graphics g, int spieler, int steinnr) {
            int pad = getWidth() / 10;
            Color c;
            switch (spieler) {
                case 1:
                    c = Color.ORANGE;
                    break;
                case 2:
                    c = Color.BLUE;
                    break;
                case 3:
                    c = Color.RED;
                    break;
                default:
                    c = Color.GREEN;
            }

            GradientPaint gradient = new GradientPaint(0, 0, c, getWidth(), 0, Color.WHITE);
            ((Graphics2D) g).setPaint(gradient);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            if (spieler != 0) {
                g.fillOval(pad + pad / 2, pad + pad / 2, getWidth() - (3 * pad), getHeight() - (pad * 3));
                g.setColor(Color.WHITE);
                g.setFont(g.getFont().deriveFont((float) 32));
                g.drawString((steinnr > 0) ? ("" + steinnr) : "", (int) (getWidth() * .5) - 8,
                        (int) (getHeight() * .5) + 8);
            }
        }

    }

    public static JFrame myFrame;
    public static JPanel pan;


    private static int whichone(int y, int[] playerone, int[] playertwo, int[] playerthree,
                                int[] playerfour) {
        for (int x = 0; x < 4; x++) {
            if (playerone[x] == y)
                return 4 + x;
        }
        for (int x = 0; x < 4; x++) {
            if (playertwo[x] == y)
                return 8 + x;
        }
        for (int x = 0; x < 4; x++) {
            if (playerthree[x] == y)
                return 12 + x;
        }
        for (int x = 0; x < 4; x++) {
            if (playerfour[x] == y)
                return 16 + x;
        }
        return 0;
    }

    public static void paintField(int[] playerone, int[] playertwo,
                                  int[] playerthree, int[] playerfour) {
        if (myFrame != null) {
            pan.removeAll();
        } else {
            myFrame = new JFrame("Spielfeld");
            pan = new JPanel();
        }
        GridBagConstraints c = new GridBagConstraints();
        pan.setLayout(new GridBagLayout());
        pan.setBackground(Color.BLACK);
        for (int x = 0; x < 40; x++) {
            int z = (x + 4) % 40;

            int figur = whichone(z, playerone, playertwo, playerthree, playerfour);

            if (x < 10) {
                c.gridx = x % 10;
                c.gridy = 0;
            } else if (x < 20) {
                c.gridy = x % 10;
                c.gridx = 10;
            } else if (x < 30) {
                c.gridx = 10 - x % 10;
                c.gridy = 10;
            } else {
                c.gridy = 10 - x % 10;
                c.gridx = 0;
            }
            pan.add(new Field(z, figur / 4, 1 + figur % 4), c);
        }
        c.gridx = 5;
        for (int x = 1; x < 5; x++) {
            c.gridy = x;
            int ttt = (playertwo[x - 1] > 39) ? 2 : 0;
            pan.add(new Field(-1, ttt, x), c);
        }
        for (int x = 9; x > 5; x--) {
            c.gridy = x;
            int ttt = (playerthree[9 - x] > 39) ? 3 : 0;
            pan.add(new Field(-1, ttt, 10 - x), c);
        }
        c.gridy = 5;
        for (int x = 1; x < 5; x++) {
            c.gridx = x;
            int ttt = (playerone[x - 1] > 39) ? 1 : 0;
            pan.add(new Field(-1, ttt, x), c);
        }
        for (int x = 9; x > 5; x--) {
            c.gridx = x;
            int ttt = (playerfour[9 - x] > 39) ? 4 : 0;
            pan.add(new Field(-1, ttt, 10 - x), c);
        }

        for (int x = 0; x < 4; x++) {
            c.gridx = 2 + x % 2;
            c.gridy = 2 + x / 2;

            pan.add(new Field.Haus(playerone[x] < 0, 1, x + 1), c);
        }
        for (int x = 0; x < 4; x++) {
            c.gridx = 7 + x % 2;
            c.gridy = 2 + x / 2;
            pan.add(new Field.Haus(playertwo[x] < 0, 2, x + 1), c);
        }
        for (int x = 0; x < 4; x++) {
            c.gridx = 2 + x % 2;
            c.gridy = 7 + x / 2;
            pan.add(new Field.Haus(playerthree[x] < 0, 3, x + 1), c);
        }
        for (int x = 0; x < 4; x++) {
            c.gridx = 7 + x % 2;
            c.gridy = 7 + x / 2;
            pan.add(new Field.Haus(playerfour[x] < 0, 4, x + 1), c);
        }
        pan.setSize(800, 880);


        myFrame.pack();
        myFrame.setSize(815, 880);
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setVisible(true);
    }

    public static void paintField(int[] one, int[] two) {
        int[] arr = {-1, -1, -1, -1};
        paintField(one, two, arr, arr);
    }
}

class Mensch extends Aerger {
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

public class Game {

    public static void main(String[] args) {
        int[] yellow = {10, 30, 50, -1};
        int[] blue = {0, 20, 40, -1};
        //int[] yellow = {1, 3, 5, 7};
        //int[] blue = {0, 2, 4, 6};
        int[] red = {-1, -1, -1, -1};
        int[] green = {-1, -1, -1, -1};
        Mensch m = new Mensch(yellow, blue, red, green);
    }
}
