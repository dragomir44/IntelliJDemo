import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    private JTextField workFromTextField;
    private JTextField workTillTextField;

    //Set the hours of your working day
    private int StartOfDay = 11;
    private int EndOfDay = 18;
    private String QuoteFilename = "Quotes.txt";
    private List<String> quotes;
    private SoundUtils sound = new SoundUtils();


    public GUI() {
        ReadQuotes(QuoteFilename);
        int randomQuote = ThreadLocalRandom.current().nextInt(0, 19);
        percentage.setText("No more work");
        quote.setText(quotes.get(randomQuote));
        workFromTextField.setText(StartOfDay + "");
        workTillTextField.setText(EndOfDay + "");
        new Thread(){
            public void run() {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                while(true){
                    long millis = System.currentTimeMillis();
                    LocalTime now = LocalTime.now();
                    String time = dtf.format(now);
                    clock.setText(time + "");

                    //Calculating percentage of working day
                    int hour = Integer.parseInt(time.substring(0,2));
                    int minute = Integer.parseInt(time.substring(3,5));

                    if (hour < EndOfDay && hour >= StartOfDay){
                        float minOfWorkingDay = ((EndOfDay-StartOfDay)*60);
                        float hoursPassed = hour - StartOfDay;
                        float minPassed = hoursPassed * 60;
                        float totalPassed = minPassed + minute;

                        float percent = Math.round(totalPassed/minOfWorkingDay*100);
                        percentage.setText((int)percent + "%");
                    } else if (hour == EndOfDay && minute == 0) {
                        MakeSound();
                    }

                    //Sleep code for 1 minute to prevent CPU drainage
                    try {
                        Thread.sleep(1000*60 - millis % (1000*60));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
        //Change working variables and quote when button clicked
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String workFrom = workFromTextField.getText();
                String workTill = workTillTextField.getText();
                if (!workFrom.equals("") && !workTill.equals("")) {
                    StartOfDay = Integer.parseInt(workFrom);
                    EndOfDay = Integer.parseInt(workTill);
                }
                int random = ThreadLocalRandom.current().nextInt(0, 19);
                quote.setText(quotes.get(random));
            }
        });
        //Change quote when scrolling
        panel1.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int random = ThreadLocalRandom.current().nextInt(0, 19);
                quote.setText(quotes.get(random));
            }
        });
    }

    public void MakeSound(){
        try {
            sound.tone(400,500);
            Thread.sleep(200);
            sound.tone(400,500);
            Thread.sleep(200);
            sound.tone(400,500);
            Thread.sleep(200);
        } catch (LineUnavailableException e1) {
            e1.printStackTrace();
        }catch (InterruptedException e1) {
            e1.printStackTrace();
        }
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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        GUI g = new GUI();
        frame.add(g.panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}
