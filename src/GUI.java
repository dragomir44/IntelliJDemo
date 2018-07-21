import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 * Created by Sergey on 21-Jul-18.
 */
public class GUI {
    private JPanel panel1;
    private JLabel clock;
    private JButton button1;
    private JPanel Top;
    private JPanel Bot;

    public GUI() {

        new Thread(){
            public void run() {
                while(true){
                    Calendar cal = new GregorianCalendar();
                    int hour = cal.get(Calendar.HOUR);
                    int minute = cal.get(Calendar.MINUTE);
                    int second = cal.get(Calendar.SECOND);
                    int AM_PM = cal.get(Calendar.AM_PM);
                    String half = "";
                    half = ((AM_PM == 1) ? "PM" : "AM");

                    String time = hour + ":" + minute + ":" + second + " " + half;
                    clock.setText(time);
                }
            }
        }.start();

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel1);
        frame.setContentPane(new GUI().Top);
        frame.setContentPane(new GUI().Bot);
        frame.setContentPane(new GUI().clock);
        frame.setContentPane(new GUI().button1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
