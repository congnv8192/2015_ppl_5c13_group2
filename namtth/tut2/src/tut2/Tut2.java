package tut2;

import java.util.Random;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tut2 extends JPanel
{
    private JFrame window; // main window
    private JPanel panel; // panel for the main window
    private Button Ex8;
    private Button Ex9;

    public Tut2()
    {
        InitializeGUI();
    }

    private void InitializeGUI()
    {
        window = new JFrame("Tutorial 2");
        window.setSize(400, 200);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        window.setContentPane(panel);

        Ex8 = new Button("Excercise 8");
        Ex9 = new Button("Excercise 9");

        Ex8.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Chap3Ex8();
            }
        });

        Ex9.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Ex9.setEnabled(false);
                Chap3Ex9();
            }
        });

        panel.add(Ex8);
        panel.add(Ex9);

        window.setVisible(true);
    }

    public static void main(String[] args)
    {
        Tut2 tut = new Tut2();
    }

    /**
     * This solution will not just print out my personal initials but to ask
     * user to input his/her initial and print it out. This is achieved by using
     * pre-generated table containing all alphabetical characters in ASCII art
     * fashion. Note that due to visual differences across text editors, your
     * version of display might be changed significantly, to solve this problem,
     * open ascii.txt in your current IDE and re-position the lines
     * appropriately. Also I've only made A and B, so if you want to try, enter
     * something that only has 'a' and 'b'
     */
    public static void Chap2Ex1()
    {
        String[][] alphabet = new String[26][9]; // fillables for the table
        TextIO.readFile("ascii.txt"); // read file and fill them all in
        for (int c = 0; c < 26; c++)
        {
            for (int i = 0; i < 9 && !TextIO.eof(); i++)
                alphabet[c][i] = TextIO.getln();
            if (TextIO.eof())
                break;
        }

        TextIO.readStandardInput();
        TextIO.putln("Please enter your initials:");
        char[] ln = TextIO.getln().toUpperCase().toCharArray();
        for (int i = 0; i < 9; i++)
        {
            for (char ch : ln)
                TextIO.put(alphabet[(int) ch - 65][i]);
            TextIO.put("\n");
        }
    }

    public static void Chap2Ex2()
    {
        int r1 = roll();
        int r2 = roll();
        int total = r1 + r2;
        TextIO.putln("The first die comes up " + r1);
        TextIO.putln("The second die comes up " + r2);
        TextIO.putln("Your total roll is " + total);
    }

    public static void Chap2Ex3()
    {
        TextIO.putln("Enter your name:");
        String name = TextIO.getln().toUpperCase();
        TextIO.putln("Hello, " + name + ", nice to meet you!");
    }

    public static void Chap2Ex4()
    {
        int cents = 0;
        TextIO.putln("How many quarters do you have?");
        cents += TextIO.getInt() * 25;
        TextIO.putln("How many dimes do you have?");
        cents += TextIO.getInt() * 10;
        TextIO.putln("How many nickels do you have?");
        cents += TextIO.getInt() * 5;
        TextIO.putln("How many pennies do you have?");
        cents += TextIO.getInt();
        TextIO.putln("The total in dollars is $" + ((float) cents / 100));
    }

    public static void Chap2Ex5()
    {
        TextIO.putln("How many eggs do you have?");
        int eggs = TextIO.getInt();
        TextIO.putln("Your number of eggs is " + (eggs / 144) + " gross, " + ((eggs %= 144) / 12) + " dozen, and " + (eggs % 12));
    }

    public static void Chap2Ex6()
    {
        TextIO.putln("What your name (first and last name only)?");
        String[] name = TextIO.getln().split(" ");
        TextIO.putln("Your first name is " + name[0] + ", which has " + name[0].length() + " characters.");
        if (name.length >= 2)
            TextIO.putln("You last name is " + name[1] + ", which has " + name[1].length() + " characters.");
        else
            TextIO.putln("You have no last name, apparently.");
        TextIO.putln("You initials are " + name[0].charAt(0) + ((name.length >= 2) ? name[1].charAt(0) : ""));
    }

    public static void Chap2Ex7()
    {
        if (new File("testdata.txt").isFile() == false) // Removed checking for canRead() cause who cares
        {
            TextIO.putln("No testdata.txt existed");
            return;
        }
        TextIO.readFile("testdata.txt");
        while (!TextIO.eof())
        {
            String name = TextIO.getln();
            int[] marks = new int[3];
            for (int i = 0; i < 3 && !TextIO.eof(); i++)
                marks[i] = TextIO.getlnInt();
            TextIO.putln(String.format("Student %s has an average mark of %.2f", name, (marks[0] + marks[1] + marks[2]) / (double) 3));
        }
    }

    // Try num = 10, it would take 16 minutes to programatically roll a double Yahtzee, given each roll costs 0.1 millisecond
    public static void Chap3Ex1(int num, int val)
    {
        boolean a = true;
        int turn = 0;
        while (a)
        {
            for (int i = 0; i < num; i++)
            {
                int result = roll();
                if (i == 0 && val == 0)
                    val = result;
                else if (result != val)
                    a = false;
            }
            turn++;
            if (a)
            {
                if (num == 2 && val == 1)
                {
                    TextIO.putln("You have rolled snake eyes in " + turn + " rolls.");
                    return;
                }
                if (num == 5)
                    TextIO.putln("Yahtzeee! Your result is " + turn + " rolls (" + (1296 - turn) + " early).");
                else
                    TextIO.putln("You did it!");
                return;
            } else
                a = true;
        }
    }

    public static void Chap3Ex2()
    {
        int max = 2;
        int result = 7560; // The "result", or is it?
        for (int i = 2; i <= 10000; i++)
        {
            int divisors = 2; // 1 and 'i' itself
            for (int k = 2; k <= i / 2; k++)
                if (i % k == 0)
                    divisors++;
            if (divisors > max)
            {
                max = divisors;
                result = i;
            }
        }
        TextIO.putln("Number " + result + " has the most amount of divisors: " + max);
    }

    public static void Chap3Ex3()
    {
        double var1;
        while ((var1 = TextIO.getDouble()) != 0)
        {
            char operator = TextIO.getChar();
            double var2 = TextIO.getDouble();

            switch (operator)
            {
                case '+':
                    TextIO.putln("= " + (var1 + var2));
                    break;
                case '-':
                    TextIO.putln("= " + (var1 - var2));
                    break;
                case '*':
                    TextIO.putln("= " + (var1 * var2));
                    break;
                case '/':
                    TextIO.putln("= " + (var1 / var2));
            }
        }
    }

    public static void Chap3Ex4()
    {
        while (!TextIO.eoln())
            TextIO.put(getWord()); // All magic lies in getWord(), check there for amazement
    }

    public static void Chap3Ex5()
    {
        if (new File("sales.dat").isFile() == false)
        {
            TextIO.putln("No sales.dat existed");
            return;
        }

        int unreported = 0;
        double total = 0;
        TextIO.readFile("sales.dat");
        while (!TextIO.eof())
        {
            String[] spl = TextIO.getln().split(":", 2);
            try
            {
                total += Double.parseDouble(spl[1].trim());
            } catch (Exception e) // 2 possible Exceptions are OutOfBoundException & NumberFormatException
            {
                if (e instanceof NumberFormatException)
                    unreported++;
            }
        }

        TextIO.putln(String.format("The total sales are $%1.2f", total));
        TextIO.putln(unreported == 0 ? "Data was received from all cities" : (String.format("Data was missing from %d ", unreported) + (unreported == 1 ? "city." : "cities.")));
    }

    public static void Chap3Ex6()
    {
        int max = 2;
        int[] all = new int[10001];
        all[1] = 1; // Cause anyone would know this

        for (int i = 2; i < 10000; i++)
        {
            int divisors = 2;
            for (int k = 2; k <= i / 2; k++)
                if (i % k == 0)
                    divisors++;
            all[i] = divisors;
            if (divisors > max)
                max = divisors;
        }

        TextIO.putln("The maximum number of divisors was " + max);
        TextIO.putln("Numbers with that many divisors include:");
        for (int i = 10001; i-- > 1;) // Looping from 10000 to 1 because I am that main stream, or because every one knows the higher the number means bigger chances of having more amount of divisors
            if (all[i] == max)
                TextIO.putln(i);
    }

    public static void Chap3Ex7Part1()
    {
        int[] arr = new int[365];
        int count = 0;
        while (true)
        {
            int birthday = random(365);
            count++;
            if (arr[birthday] == 2)
                break;
            arr[birthday]++;
        }
        TextIO.putln("Found 3 people that have the same birthday among " + count + " people.");
    }

    public static void Chap3Ex7Part2()
    {
        boolean[] arr = new boolean[365];
        int count = 0;
        int result = 0;
        while (count < 365)
        {
            int birthday = random(365);
            if (!arr[birthday])
            {
                arr[birthday] = true;
                result++;
            }
            count++;
        }
        TextIO.putln(result + " different birthdays.");
    }

    public static void Chap3Ex7Part3()
    {
        boolean[] arr = new boolean[365];
        int birthday_left = 365;
        int count = 0;

        while (true)
        {
            int birthday = random(365);
            count++;
            if (!arr[birthday])
            {
                birthday_left--;
                arr[birthday] = true;
            }
            if (birthday_left == 0)
                break;
        }
        TextIO.putln(count + " birthdays.");
    }

    public void Chap3Ex8()
    {
        JFrame window_ex8 = new JFrame("Tutorial 2: Checker board");
        window_ex8.setLocation(100, 50);
        window_ex8.setResizable(false);

        JPanel panel_ex8 = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                drawCheckerboard(g);
            }
        };
        panel_ex8.setBackground(Color.WHITE);
        panel_ex8.setPreferredSize(new Dimension(400, 400));

        window_ex8.setContentPane(panel_ex8);
        window_ex8.pack();
        window_ex8.setVisible(true);
    }

    private int frameNum;

    public void drawFrame(Graphics g, int frameNumber, int width, int height)
    {
        int cyclicFrameNum = frameNumber % 300;
        g.setColor(Color.RED);
        g.fillRect(cyclicFrameNum, 0, 20, 20);

        cyclicFrameNum = frameNumber % 150;
        g.setColor(Color.GREEN);
        g.fillRect(2 * cyclicFrameNum, 20, 20, 20);

        cyclicFrameNum = frameNumber % 100;
        g.setColor(Color.BLUE);
        g.fillRect(3 * cyclicFrameNum, 40, 20, 20);

        int oscillationFrameNum = frameNumber % 600;
        if (oscillationFrameNum > 300)
            oscillationFrameNum = 600 - oscillationFrameNum;
        g.setColor(Color.CYAN);
        g.fillRect(oscillationFrameNum, 60, 20, 20);

        oscillationFrameNum = frameNumber % 300;
        if (oscillationFrameNum > 150)
            oscillationFrameNum = 300 - oscillationFrameNum;
        g.setColor(Color.MAGENTA);
        g.fillRect(2 * oscillationFrameNum, 80, 20, 20);

        oscillationFrameNum = frameNumber % 200;
        if (oscillationFrameNum > 100)
            oscillationFrameNum = 200 - oscillationFrameNum;
        g.setColor(Color.YELLOW);
        g.fillRect(3 * oscillationFrameNum, 100, 20, 20);

        g.setColor(Color.BLACK);
        for (int i = 20; i < 120; i += 20)
            g.drawLine(0, i, 320, i);
    }

    public void Chap3Ex9()
    {
        JFrame window_ex9 = new JFrame("Tutorial 2: Animation Tester");
        window_ex9.setLocation(100, 50);
        window_ex9.setResizable(false);

        JPanel panel_ex9 = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                drawFrame(g, frameNum, getWidth(), getHeight());
            }
        };
        panel_ex9.setBackground(Color.WHITE);
        panel_ex9.setPreferredSize(new Dimension(320, 120));

        window_ex9.setContentPane(panel_ex9);
        window_ex9.pack();

        Timer frameTimer = new Timer(20, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frameNum++;
                panel_ex9.repaint();
            }
        });
        frameTimer.start();

        window_ex9.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                frameTimer.stop();
                Ex9.setEnabled(true);
            }
        });

        window_ex9.setVisible(true);
    }

    public void drawCheckerboard(Graphics g)
    {
        boolean red = true; // true is red, false is black

        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                g.setColor(red ? Color.RED : Color.BLACK);
                red = !red;
                g.fillRect(col * 50, row * 50, 50, 50);
            }
            red = !red;
        }
    }

    static int random(int bound)
    {
        return new Random().nextInt(bound);
    }

    public static String getWord()
    {
        StringBuilder str = new StringBuilder(50);

        char ch = TextIO.getChar();
        while (Character.isLetter(ch))
        {
            str.append(ch);
            ch = TextIO.getAnyChar();
        }
        return str.length() > 0 ? str.append('\n').toString() : str.toString();
    }

    static int roll()
    {
        return new Random().nextInt(6) + 1;
    }

    public static void Exercise1()
    {
        String[] member_list = new String[]
        {
            "Hoang Nam", "Tuan Viet"
        };
        TextIO.putln("Enter key word");
        String key = TextIO.getln().toLowerCase();
        String[] save = new String[member_list.length];
        int c = 0;
        for (String i : member_list)
        {
            if (i.toLowerCase().contains(key))
            {
                save[c] = i;
                c++;
                TextIO.putln(i);
            }
        }
        TextIO.writeFile("data.txt");
        for (String i : save)
            TextIO.putln(i);
    }

    public static void Exercise2()
    {
        int sum = 0;
        TextIO.readFile("data.txt");
        while (!TextIO.eof())
        {
            String line = TextIO.getln();
            String[] spl = line.split(":", 2);
            if (spl.length == 2 && isNumber(spl[1].trim()))
                sum += Integer.parseInt(spl[1].trim());
        }
        TextIO.putln(sum);
    }

    public static boolean isNumber(String str)
    {
        try
        {
            int num = Integer.parseInt(str);
        } catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
