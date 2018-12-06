import javafx.application.Platform;

public class Clock extends Thread {
    int min;
    int sec;


    Clock(int min,int sec) {
        this.min = min;
        this.sec = sec;
    }

    public void run() {

        for(;;) {
            if (sec == 0){
                min --;
                sec = 59;
            }
            else {
                sec--;
            }

            Platform.runLater(new Runnable() {
                public void run() {
                    Controller.ourModel.updateTimer(0);
                }
            });

            try {
                Thread.sleep(5000);
            }
            catch(Exception e) {}
        }
    }
}