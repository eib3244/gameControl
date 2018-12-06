import java.util.Observable;

public class model extends Observable {
    int minLeft;
    int secLeft;
    int cluesLeft;
    String textBoxText;
    int currentAttachemnt;

    model(int min, int sec,int cluesLeft){
        this.minLeft = min;
        this.secLeft = sec;
        this.cluesLeft = cluesLeft;
        this.textBoxText = "";
        this.currentAttachemnt = 0;
    }



    public void updateTimer(int status){

        if (minLeft == 0 && secLeft == 1){
            Controller.ourTimer.stop();
        }

        // timer is running
        if (status == 0){
            if (secLeft == 0){
                minLeft --;
                secLeft = 59;
            }
            else {
                secLeft--;
            }
            super.setChanged();
            super.notifyObservers(0);
        }

        // timer has been stopped
        if(status == 1){
            minLeft = 60;
            secLeft = 0;
            super.setChanged();
            super.notifyObservers(0);
        }

        if (status == 3){
            minLeft ++;
            super.setChanged();
            super.notifyObservers(0);
        }

        if (status == 4){
            minLeft --;
            super.setChanged();
            super.notifyObservers(0);
        }

        if (status == 5){
            minLeft = 60;
            secLeft = 0;
            super.setChanged();
            super.notifyObservers(0);
        }


    }

    public void updateClues(int status){
        if (status == 0){
            cluesLeft ++;
            super.setChanged();
            super.notifyObservers(1);
        }

        if (status == 1){
            cluesLeft --;
            super.setChanged();
            super.notifyObservers(1);
        }

        if (status == 2){
            cluesLeft = 3;
            super.setChanged();
            super.notifyObservers(1);
        }
    }

    public void changeTextBox(String Text){
        this.textBoxText = Text;
        super.setChanged();
        super.notifyObservers(2);
    }

    public void sendText(String text){
        this.textBoxText = text;
        super.setChanged();
        super.notifyObservers(3);
    }

    public void clearText(){
        super.setChanged();
        super.notifyObservers(4);
    }

    public void viewAttachments(){
        super.setChanged();
        super.notifyObservers(5);
    }

    public void sendAttachment(){
        super.setChanged();
        super.notifyObservers(6);
    }

    public void removeAttachment(){
        super.setChanged();
        super.notifyObservers(7);
    }
}
