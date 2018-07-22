import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    //Set the hours of your working day
    private int StartOfDay = 01;
    private int EndOfDay = 05;

    public GUI() {

        new Thread(){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            public void run() {
                while(true){
                    //Using SimpleDateFormat which is a better practise than Calendar
                    Date now = new Date();
                    String strDate = sdf.format(now);
                    clock.setText(strDate);

                    //Calculating percentage of working day
                    int hour = Integer.parseInt(strDate.substring(0,2));
                    int minute = Integer.parseInt(strDate.substring(3,5));
                    if (hour < EndOfDay & hour >= StartOfDay){
                        float min = CalculateMinutes(StartOfDay, EndOfDay);
                        float percent = Math.round(minute/min*100);
                        percentage.setText((int)percent + "%");
                    } else {
                        percentage.setText("Workday over");
                    }
                }
            }
        }.start();
    }

    public int CalculateMinutes(int begin, int end){
        return ((end-begin) * 60);
    }

    
    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().percentage);
        frame.setContentPane(new GUI().percentpanel);
        frame.setContentPane(new GUI().quote);
        frame.setContentPane(new GUI().quotepanel);
        frame.setContentPane(new GUI().button1);
        frame.setContentPane(new GUI().clock);
        frame.setContentPane(new GUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
