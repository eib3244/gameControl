import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Date;
import javax.swing.*;

public class ClockFrame extends JFrame{

    ClockFrame() {
        JLabel label = new JLabel();
        getContentPane().add(label);
        Thread t = new Clock(label);
        t.start();
    }

    public static void main(String[] args) {
        ClockFrame frame = new ClockFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200,60);
        frame.setVisible(true);
    }


    class Clock extends Thread {
        JLabel labelToUpdate;

        Clock(JLabel label) {
            labelToUpdate = label;
        }

        public void run() {
            int min = 60;
            int sec = 0;
            for(;;) {
                if (sec == 0){
                    min --;
                    sec = 59;
                }
                else {
                    sec--;
                }

                labelToUpdate.setText(Integer.toString(min) + ":" + Integer.toString(sec));
                labelToUpdate.repaint();
                try {
                    Thread.sleep(1000);
                }
                catch(Exception e) {}
            }
        }
    }
}