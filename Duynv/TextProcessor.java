package tut2;

import java.util.ArrayList;

public class TextProcessor
{
    public static void main(String[] args)
    {
        // All results have been hand-tested + verified by http://wordcounttools.com/

        // There is still a bug when TextIO tries to write weird symbol to data2.txt, this is not an error by TextIO
        // but rather a lack of encoding feature in TextIO
        // How to fix: Modify TextIO so that it can encode the file to ANSI (currently UTF-8), or use another implementation with encoding supported.
        UpdateData();
        CountLinesOriginalData();
        CountDistinctWordsUpdatedData();
        CountRedundantSpaces();
    }

    /**
     * Read data.txt, remove redundant spaces, capitalize first letter of each
     * word, then update the content to data2.txt
     */
    public static void UpdateData()
    {
        TextIO.readFile("data.txt");
        TextIO.writeFile("data2.txt");

        while (!TextIO.eof())
        {
            String[] spl = TextIO.getln().trim().toLowerCase().split(" ");
            for (int i = 0; i < spl.length; i++)
            {
                String word = spl[i];
                if (word.length() > 0)
                    TextIO.put(Character.toUpperCase(word.charAt(0)) + word.substring(1, word.length()) + (i != spl.length - 1 ? " " : ""));
            }
            TextIO.putln();
        }
    }

    /**
     * Count the number of lines and words in data.txt
     */
    public static void CountLinesOriginalData()
    {
        TextIO.readFile("data.txt");
        TextIO.writeStandardOutput();

        int line_count = 0;
        int word_count = 0;
        while (!TextIO.eof())
        {
            String line = TextIO.getln();
            if (line.length() > 0)
                line_count++;

            // A simple method of counting words in a file/line
            // Unfortunately, after several testings, I found this method inaccurate and also lack of stability, avoid at all cost
            /*char[] arr = line.toCharArray();
             char previous = ' ';
             for (char ch : arr)
             {
             if (Character.isLetter(ch) && previous == ' ')
             word_count++;
             previous = ch;
             }*/
            String[] spl = line.split(" ");
            for (String word : spl)
                if (word.length() > 0)
                    word_count++;
        }
        TextIO.putln("The number of lines in original data is:" + line_count);
        TextIO.putln("The number of words in original data is:" + word_count);
    }

    /**
     * Count the number of distinctive words in data2.txt
     */
    public static void CountDistinctWordsUpdatedData()
    {
        TextIO.readFile("data2.txt");
        TextIO.writeStandardOutput();

        ArrayList al = new ArrayList();
        while (!TextIO.eof())
        {
            // This introduces another method of getting words in a file/line, while the other one is only good for counting
            String[] spl = TextIO.getln().split(" ");
            for (String word : spl)
                if (word.length() > 0 && !al.contains(word))
                    al.add(word);
        }
        TextIO.putln("The number of distinct words in updated data is: " + al.size());
    }

    /**
     * Count the number of removed spaces from task 1.
     * This is the closest I can get to, implementing something redundant to count something 'redundant' is very unlikely and just doesn't make any sense at all
     */
    public static void CountRedundantSpaces()
    {
        TextIO.writeStandardOutput();
        TextIO.readFile("data.txt");

        int c = 0;
        while (!TextIO.eof())
        {
            TextIO.getAnyChar();
            c++;
        }

        TextIO.readFile("data2.txt");

        while (!TextIO.eof())
        {
            TextIO.getAnyChar();
            c--;
        }
        TextIO.putln("The number of redundant spaces is: " + c);
    }
}
