import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static File[] listOfFilesAdded;
    public static ArrayList<String> cluesFromFile = new ArrayList<String>();
    public static ArrayList<File> attachmentFiles = new ArrayList<File>();
    public static Timeline ourTimer;
    public static int minOnTimer = 60;
    public static int secOnTimer = 0;
    public static int cluesCounter = 3;
    public static int createClock = 0;
    public static model ourModel;
    public static int currentAttachment = 0;

    public static void loadFiles(File[] listOfFiles) {
        // Looping through our list of "emails"
        for (File file : listOfFiles) {
            if (file.isDirectory()){
                loadFiles(file.listFiles());
            }
            if (file.isFile() && file.getName().equals("presetClues.txt")){
                cluesFromFile = readFile(file.getPath().toString());
            }
            if (file.getName().toLowerCase().contains(".png") || file.getName().toLowerCase().contains(".mp4")){
                attachmentFiles.add(file);
            }

        }
    }

    public static ArrayList<String> readFile (String filename) {
        ArrayList<String> records = new ArrayList<String>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.add(line);
            }
            reader.close();
            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }

    public static void timerControl(int status){
        // if we are clicking start for 1st time or we are restarting the timer
        if (status == 0 && createClock == 0){
            ourTimer = new Timeline();
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), ae -> ourModel.updateTimer(0));
            ourTimer.getKeyFrames().add(keyFrame);
            ourTimer.setCycleCount(Timeline.INDEFINITE);
            ourTimer.play();
            createClock = 1;
        }
        // if we are unpausing the timer
        else if(status == 0){
            ourTimer.play();
        }
        if (status == 1){
            ourTimer.pause();
        }
        // if we are stopping the timer
        if (status == 2){
            ourTimer.stop();
            createClock = 0;
            minOnTimer = 60;
            secOnTimer = 0;
            ourModel.updateTimer(1);
        }

        if (status == 3){
            ourModel.updateTimer(3);
        }

        if (status == 4){
            ourModel.updateTimer(4);
        }

        if (status == 5){
            ourModel.updateTimer(5);
        }

    }

    public static void clueControl(int status){
        if (status == 0){
            ourModel.updateClues(status);
        }

        if (status == 1){
            ourModel.updateClues(status);
        }

        if (status == 2){
            ourModel.updateClues(status);
        }
    }

    public static void TextBoxEdit(String Text){
        ourModel.changeTextBox(Text);
    }

    public static void sendText(String text){
        ourModel.sendText(text);
    }

    public static void clearText(){
        ourModel.clearText();
    }

    public static void viewAttachments(){
        ourModel.viewAttachments();
    }

    public static void sendAttachment(){
        ourModel.sendAttachment();
    }

    public static void removeAttachment(){
        ourModel.removeAttachment();
    }

    public static void changeCurrentAttachment(String movement){
        if (movement.equals("forward")){
            currentAttachment = Math.floorMod(currentAttachment + 1,attachmentFiles.size() );
           // ourModel.changeAttachment();
        }
        else {
            currentAttachment = Math.floorMod(currentAttachment - 1,attachmentFiles.size() );
           // ourModel.changeAttachment();
        }
    }


    public static void main(String[] args) {
        final File filesDir = new File("files");

         listOfFilesAdded = filesDir.listFiles();

         loadFiles(listOfFilesAdded);
        for (String clue: cluesFromFile){
            System.out.println(clue.toString());
        }

        for (File attach: attachmentFiles){
            System.out.println(attach.getName());
        }

        ourModel = new model(minOnTimer,secOnTimer,cluesCounter);

        GUI.main(args);
    }
}
