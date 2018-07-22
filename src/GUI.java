import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Sergey on 21-Jul-18.
 */
public class GUI {
    private JPanel panel1;
    private JLabel clock;
    private JButton button1;
    private JPanel percentpanel;
    private JLabel percentage;
    private JPanel quotepanel;
    private JLabel quote;
    private JLabel remaining;

    //Set the hours of your working day
    private int StartOfDay = 01;
    private int EndOfDay = 05;
    private String QuoteFilename = "Quotes.txt";
    private List<String> quotes;


    public GUI() {
        ReadQuotes(QuoteFilename);
        int randomquote = ThreadLocalRandom.current().nextInt(0, 20);
        percentage.setText("Workday over");
        new Thread(){
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                while(true){
                    long millis = System.currentTimeMillis();
                    //Using SimpleDateFormat which is a better practise than Calendar
                    Date now = new Date();
                    String strDate = sdf.format(now);
                    clock.setText(strDate);

                    //Calculating percentage of working day
                    int hour = Integer.parseInt(strDate.substring(0,2));
                    int minute = Integer.parseInt(strDate.substring(3,5));

                    if (hour < EndOfDay & hour >= StartOfDay){
                        float min = CalculateMinutes(StartOfDay, EndOfDay);
                        remaining.setText(min + "");
                        //FORMULA HAS BUG. TODO
                        float percent = Math.round(minute/min*100);
                        percentage.setText((int)percent + "%");
                    }
                    quote.setText(quotes.get(randomquote));

                    //Sleep code for 1 second to prevent CPU drainage
                    try {
                        Thread.sleep(1000 - millis % 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    public int CalculateMinutes(int begin, int end){
        return ((end-begin) * 60);
    }

    public void ReadQuotes(String filename){
        quotes = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader("src/" + filename))) {
            String line = br.readLine();
            while (line != null) {
                quotes.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        GUI g = new GUI();
        frame.add(g.panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);

    }
}
