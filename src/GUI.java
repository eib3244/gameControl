// give each extra display object the list of attachments and when we change it have them all change ?
//also give it a media player view etc ?

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GUI extends Application implements Observer {
    public String finalPath = "";
    public int currentAttachment = 0;
    public ImageView attachmentImage = new ImageView();
    public Image imageAttachmentImageNotView;
    public MediaPlayer player;
    public MediaView videoPlayerView = new MediaView();

    ArrayList<extraDisplay> screens = new ArrayList<extraDisplay>();

    public TextField consoleInput = new TextField();

    public Button nextAttachment;
    public Button lastAttachment;
    public Button viewAttachments;
    public Button  sendAttachment;
   public Button playVideoButton;
   public Button pauseVideoButton;
   public Button stopVideoButton;
   public  Button closeAttachment;

    public static String cssLayout = "-fx-border-color: black;\n" +
            "-fx-border-insets: 5;\n" +
            "-fx-border-width: 5;\n" +
            "-fx-border-style: solid;\n";

    public static String cssLayout2 = "-fx-border-color: black;\n" +
            "-fx-border-insets: 25;\n" +
            "-fx-border-width: 25;\n" +
            "-fx-border-style: solid;\n";

    public Stage mainStage = new Stage();
   // public BorderPane playerBorderPane = new BorderPane();
   // public Stage playerStage = new Stage();
    public VBox sharedViewVbox = new VBox();
    //public VBox playVbox = new VBox();
    public GridPane sharedGridpane = new GridPane();
   // public GridPane playGrid = new GridPane();
    public TextArea playerTextArea = new TextArea();

    public Label timeRemLabel = new Label("Time Left: ");
    public Label timeLeft = new Label();
    public Label cluesLeftLabel = new Label("Clues Left: ");
    public Label cluesLeft = new Label();
   // public Label timeRemLabelPlayer = new Label("Time Left: ");
  //  public Label timeLeftPlayer = new Label();
   // public Label cluesLeftLabelPlayer = new Label("Clues Left: ");
   // public Label cluesLeftPlayer = new Label();


    @Override
    public void update(Observable o, Object arg) {
        // if the timer is being updated we use 0 for this
        if (arg.equals(0)){

            javafx.application.Platform.runLater(() ->
                    timeLeft.setText(Integer.toString(Controller.ourModel.minLeft) + ":" + Integer.toString(Controller.ourModel.secLeft)));

            for(extraDisplay aDisplay: screens) {


                javafx.application.Platform.runLater(() ->
                        aDisplay.timeLeft.setText(Integer.toString(Controller.ourModel.minLeft) + ":" + Integer.toString(Controller.ourModel.secLeft)));
            }

        }

        if (arg.equals(1)) {
            javafx.application.Platform.runLater(() ->
                    cluesLeft.setText(Integer.toString(Controller.ourModel.cluesLeft)));

            for (extraDisplay aDisplay : screens) {
                javafx.application.Platform.runLater(() ->
                        aDisplay.cluesLeft.setText(Integer.toString(Controller.ourModel.cluesLeft)));
            }
        }

        if (arg.equals(2)){
            javafx.application.Platform.runLater(() ->
                    consoleInput.setText(Controller.ourModel.textBoxText));
        }

        if (arg.equals(3)){
            javafx.application.Platform.runLater(() ->
                    playerTextArea.appendText("\n" + Controller.ourModel.textBoxText));
            for(extraDisplay aDisplay: screens) {

                javafx.application.Platform.runLater(() ->
                        aDisplay.textInfo.appendText("\n"+ Controller.ourModel.textBoxText));
            }
        }

        if(arg.equals(4)){
            javafx.application.Platform.runLater(() ->
                    playerTextArea.clear());
            for(extraDisplay aDisplay: screens) {

                javafx.application.Platform.runLater(() ->
                        aDisplay.textInfo.clear());
            }
        }
        if (arg.equals(5)){

            nextAttachment.setDisable(false);
            lastAttachment.setDisable(false);
            closeAttachment.setDisable(false);


            if (Controller.attachmentFiles.get(currentAttachment).getName().toLowerCase().contains("png")) {
                playVideoButton.setDisable(true);

                if (sharedViewVbox.getChildren().contains(attachmentImage)){
                    javafx.application.Platform.runLater(() ->
                    sharedViewVbox.getChildren().remove(attachmentImage));
                }

                if (sharedViewVbox.getChildren().contains(videoPlayerView)){
                    javafx.application.Platform.runLater(() ->
                    sharedViewVbox.getChildren().remove(videoPlayerView));
                }

                javafx.application.Platform.runLater(() ->
                        sharedViewVbox.getChildren().add(attachmentImage));


                /*
                for (extraDisplay display: screens){

                    ImageView a = new ImageView(imageAttachmentImageNotView);

                    javafx.application.Platform.runLater(() ->

                            // like others object is placed in ONE location only
                            display.playVbox.getChildren().add(a));
                }
                */
            }
            else {

                playVideoButton.setDisable(false);
                pauseVideoButton.setDisable(false);
                stopVideoButton.setDisable(false);

                if (sharedViewVbox.getChildren().contains(videoPlayerView)){
                    javafx.application.Platform.runLater(() ->
                    sharedViewVbox.getChildren().remove(videoPlayerView));
                }
                if (sharedViewVbox.getChildren().contains(attachmentImage)){
                    javafx.application.Platform.runLater(() ->
                    sharedViewVbox.getChildren().remove(attachmentImage));
                }

                javafx.application.Platform.runLater(() ->
                        sharedViewVbox.getChildren().add(videoPlayerView));
                /*
                for (extraDisplay display: screens){
                    MediaView a = new MediaView(player);
                    // like others object is placed in ONE location only
                    javafx.application.Platform.runLater(() ->
                            display.playVbox.getChildren().add(a));
                }
                */
            }
        }

        if (arg.equals(6)){
            sendAttachment.setDisable(true);
            if (Controller.attachmentFiles.get(currentAttachment).getName().toLowerCase().contains("png")) {

                for (extraDisplay display: screens){
                    if (display.playVbox.getChildren().contains(display.mediaViewForPlayer)){
                        System.out.println("has video player");
                        javafx.application.Platform.runLater(() ->
                                display.playVbox.getChildren().remove(display.mediaViewForPlayer));
                    }
                    if (display.playVbox.getChildren().contains(display.imageViewForPlayer)){
                        javafx.application.Platform.runLater(() ->
                                display.playVbox.getChildren().remove(display.imageViewForPlayer));
                    }



                    display.imageViewForPlayer = new ImageView(imageAttachmentImageNotView);
                   // display.imageViewForPlayer.setFitHeight(display.bounds.getHeight() *.9);
              //      display.imageViewForPlayer.setFitWidth(display.bounds.getWidth() *.9);

                    javafx.application.Platform.runLater(() ->
                            // like others object is placed in ONE location only
                            display.playVbox.getChildren().add(display.imageViewForPlayer));
                }

            }
            else {

                for (extraDisplay display: screens){
                    if (display.playVbox.getChildren().contains(display.mediaViewForPlayer)){
                        javafx.application.Platform.runLater(() ->
                                display.playVbox.getChildren().remove(display.mediaViewForPlayer));
                    }
                    if (display.playVbox.getChildren().contains(display.imageViewForPlayer)){
                        javafx.application.Platform.runLater(() ->
                                display.playVbox.getChildren().remove(display.imageViewForPlayer));
                    }

                    display.mediaViewForPlayer = new MediaView(player);
                    // like others object is placed in ONE location only
                    javafx.application.Platform.runLater(() ->
                            display.playVbox.getChildren().add(display.mediaViewForPlayer));
                }

            }
        }

        if (arg.equals(7)){
            nextAttachment.setDisable(true);
            lastAttachment.setDisable(true);
            viewAttachments.setDisable(false);
            sendAttachment.setDisable(true);
            playVideoButton.setDisable(true);
            pauseVideoButton.setDisable(true);
            stopVideoButton.setDisable(true);


            if (Controller.attachmentFiles.get(currentAttachment).getName().toLowerCase().contains("png")) {
                javafx.application.Platform.runLater(() ->
                sharedViewVbox.getChildren().remove(attachmentImage));
                for (extraDisplay display: screens){
                    if (display.playVbox.getChildren().contains(display.mediaViewForPlayer)){
                        System.out.println("has video player");
                        javafx.application.Platform.runLater(() ->
                        display.playVbox.getChildren().remove(display.mediaViewForPlayer));
                    }
                    if (display.playVbox.getChildren().contains(display.imageViewForPlayer)){
                        javafx.application.Platform.runLater(() ->
                        display.playVbox.getChildren().remove(display.imageViewForPlayer));
                    }
                }

            }
            else {

                try{
                    player.stop();
                }catch (java.lang.NullPointerException e){}
                javafx.application.Platform.runLater(() ->
                sharedViewVbox.getChildren().remove(videoPlayerView));
                for (extraDisplay display: screens){

                    if (display.playVbox.getChildren().contains(display.mediaViewForPlayer)){
                        javafx.application.Platform.runLater(() ->
                        display.playVbox.getChildren().remove(display.mediaViewForPlayer));
                    }
                    if (display.playVbox.getChildren().contains(display.imageViewForPlayer)){
                        javafx.application.Platform.runLater(() ->
                        display.playVbox.getChildren().remove(display.imageViewForPlayer));
                    }
                }

            }
        }
    }

        //mainStage.sizeToScene();

    @Override
    public void init() throws Exception {
        try {
            // adding observer (our model)
            // basically the GUI will run the update fn when the model notifies its observers.
            Controller.ourModel.addObserver(this);
        } catch (java.lang.NullPointerException e){
            System.out.println("Error: " + e);
            return;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane mainBorderPane = new BorderPane();
        Scene mainScene = new Scene(mainBorderPane);
        GridPane lowerLeft = new GridPane();

        VBox leftVbox = new VBox();
        leftVbox.setStyle(cssLayout);
        sharedViewVbox.setStyle(cssLayout);
        //playVbox.setStyle(cssLayout2);
        sharedGridpane.setStyle(cssLayout);
        consoleInput.setStyle(cssLayout);
        consoleInput.setText("Type Text Here");
        ScrollPane scrollForBtns = new ScrollPane();
        scrollForBtns.setStyle(cssLayout);
        VBox VboxForBtns = new VBox();
        scrollForBtns.setContent(VboxForBtns);

        timeRemLabel.setStyle("-fx-font-size: 2em; ");
        timeLeft.setStyle("-fx-font-size: 2em; ");
        cluesLeftLabel.setStyle("-fx-font-size: 2em; ");
        cluesLeft.setStyle("-fx-font-size: 2em; ");

       // timeRemLabelPlayer.setStyle("-fx-font-size: 2em; ");
       // timeLeftPlayer.setStyle("-fx-font-size: 2em; ");
       // cluesLeftLabelPlayer.setStyle("-fx-font-size: 2em; ");
       // cluesLeftPlayer.setStyle("-fx-font-size: 2em; ");

       // Scene playerScene = new Scene(playerBorderPane);
       // playerBorderPane.setCenter(playVbox);


        mainBorderPane.setCenter(sharedViewVbox);
        mainBorderPane.setLeft(leftVbox);
        GridPane upperLeftGrid = new GridPane();
        upperLeftGrid.setStyle(cssLayout);

        Button startButton = new Button("Start Timer");
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.timerControl(0);
            }
        });

        Button pauseButton = new Button("Pause Timer");
        pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.timerControl(1);
            }
        });

        Button stopButton = new Button("Stop Timer");
        stopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.timerControl(2);
            }
        });

        Button add1Min = new Button("Add 1 Min");
        add1Min.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.timerControl(3);
            }
        });

        Button sub1Min = new Button("Sub 1 Min");
        sub1Min.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.timerControl(4);
            }
        });

        Button resetTime = new Button("Reset Time");
        resetTime.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.timerControl(5);
            }
        });

        Button addClue = new Button("Add 1 Clue");
        addClue.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.clueControl(0);
            }
        });

        Button subClue = new Button("Sub 1 Clue");
        subClue.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.clueControl(1);
            }
        });

        Button resetClue = new Button("Reset Clues");
        resetClue.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.clueControl(2);
            }
        });

        upperLeftGrid.add(startButton,0,0);
        upperLeftGrid.add(add1Min,1,0);
        upperLeftGrid.add(addClue,2,0);
        upperLeftGrid.add(pauseButton,0,1);
        upperLeftGrid.add(sub1Min,1,1);
        upperLeftGrid.add(subClue,2,1);
        upperLeftGrid.add(stopButton,0,2);
        upperLeftGrid.add(resetTime,1,2);
        upperLeftGrid.add(resetClue,2,2);

        timeLeft.setText(Integer.toString(Controller.minOnTimer) + ":" + Integer.toString(Controller.secOnTimer)+ "0");
        cluesLeft.setText(Integer.toString(Controller.cluesCounter));

        //timeLeftPlayer.setText(Integer.toString(Controller.minOnTimer) + ":" + Integer.toString(Controller.secOnTimer)+ "0");
        //cluesLeftPlayer.setText(Integer.toString(Controller.cluesCounter));\

        for (String clue: Controller.cluesFromFile){
            Button abtn = new Button(clue);
            abtn.setStyle("-fx-font-size: 1em; ");
            abtn.setWrapText(true);

            abtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Controller.TextBoxEdit(abtn.getText());
                }
            });

            VboxForBtns.getChildren().add(abtn);
        }

        sharedGridpane.add(timeRemLabel,0,0);
        sharedGridpane.add(timeLeft,1,0);
        sharedGridpane.add(cluesLeftLabel,0,1);
        sharedGridpane.add(cluesLeft,1,1);

        Button sendText = new Button("Send Text To Players");
        sendText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.sendText(consoleInput.getText());
            }
        });

        Button clearText = new Button("Clear Player Text");
        clearText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.clearText();
            }
        });

        viewAttachments = new Button("View Attachments");
        viewAttachments.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                viewAttachments.setDisable(true);
                sendAttachment.setDisable(false);
                getCurrentAttachment();
            }
        });

        nextAttachment = new Button("Next Attachment");
        nextAttachment.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentAttachment = Math.floorMod(currentAttachment + 1 , Controller.attachmentFiles.size());
                getCurrentAttachment();
            }
        });

        lastAttachment = new Button("Last Attachment");
        lastAttachment.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentAttachment = Math.floorMod(currentAttachment - 1 , Controller.attachmentFiles.size());
                getCurrentAttachment();
            }
        });

        nextAttachment.setDisable(true);
        lastAttachment.setDisable(true);

         sendAttachment = new Button("Send Attachment");
         sendAttachment.setDisable(true);
        sendAttachment.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Controller.sendAttachment();
                nextAttachment.setDisable(true);
                lastAttachment.setDisable(true);
            }
        });

        closeAttachment = new Button("Close Attachment");
        closeAttachment.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               Controller.removeAttachment();
            }
        });
        closeAttachment.setDisable(true);

        // Create the Buttons
         playVideoButton = new Button("Play Video");
         pauseVideoButton = new Button("Pause Video");
         stopVideoButton = new Button("Stop video");

         playVideoButton.setDisable(true);
         pauseVideoButton.setDisable(true);
         stopVideoButton.setDisable(true);



        // Create the Event Handlers for the Button
        playVideoButton.setOnAction(new EventHandler <ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    if (!(player.getStatus() == MediaPlayer.Status.PLAYING)) {
                        try {
                            player.play();
                        } catch (java.lang.NullPointerException e) {
                        }
                    }
                }catch (java.lang.NullPointerException e){}
            }
        });

        // Create the Event Handlers for the Button
        pauseVideoButton.setOnAction(new EventHandler <ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                if (player.getStatus() == MediaPlayer.Status.PLAYING) {
                    try {
                        player.pause();
                    }catch (java.lang.NullPointerException e){}
                }
                }catch (java.lang.NullPointerException e){}
            }
        });

        stopVideoButton.setOnAction(new EventHandler <ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    player.stop();
                }catch (java.lang.NullPointerException e){}
            }
        });

        //playGrid.add(timeRemLabelPlayer,0,0);
        //playGrid.add(timeLeftPlayer,1,0);
       // playGrid.add(cluesLeftLabelPlayer,0,1);
       // playGrid.add(cluesLeftPlayer,1,1);

        lowerLeft.add(sendText,0,0);
        lowerLeft.add(clearText,1,0);
        lowerLeft.add(viewAttachments,0,1);
        lowerLeft.add(nextAttachment,0,2);
        lowerLeft.add(lastAttachment,1,2);
        lowerLeft.add(closeAttachment,1,1);
        lowerLeft.add(sendAttachment,2,1);

        lowerLeft.add(playVideoButton,0,3);
        lowerLeft.add(pauseVideoButton,1,3);
        lowerLeft.add(stopVideoButton,2,3);

        playerTextArea.setEditable(false);
        mainBorderPane.setBottom(consoleInput);
        leftVbox.getChildren().addAll(upperLeftGrid,scrollForBtns,lowerLeft);
        sharedViewVbox.getChildren().addAll(sharedGridpane,playerTextArea);
       // playVbox.getChildren().add(playGrid);

        sharedGridpane.setAlignment(Pos.CENTER);



        mainStage.setScene(mainScene);
        //playerStage.setScene(playerScene);

        /*
        List<Screen> allScreens = Screen.getScreens();
        if (allScreens.size() > 1) {
            System.out.println("1st");
            Screen secondaryScreen = allScreens.get(1);
            Rectangle2D bounds = secondaryScreen.getVisualBounds();
            System.out.println(bounds.getMinX());
            System.out.println(bounds.getMinY());

            //Stage stage = new Stage();
            playerStage.setX(bounds.getMinX());
            playerStage.setY(bounds.getMinY());

            //playerStage.setWidth(bounds.getWidth()) ;
            //playerStage.setHeight(bounds.getHeight());

            //playerStage.initStyle(StageStyle.UNDECORATED);
            //playerStage.initModality(Modality.APPLICATION_MODAL);
            playerStage.show();
            playerStage.setFullScreen(true);
            playerStage.setFullScreenExitHint("");

        } else {
            System.out.println("else");
            //Stage stage = new Stage();
            playerStage.setFullScreen(true);
            playerStage.setFullScreenExitHint("");

            //playerStage.initStyle(StageStyle.UNDECORATED);
            //playerStage.initModality(Modality.APPLICATION_MODAL);
            playerStage.show();
        }
        */

       // playerStage.show();
        for (Screen aScreen: Screen.getScreens()){
            extraDisplay newScreen = new extraDisplay(aScreen);
           screens.add(newScreen);
        }


        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                stop();
            }
        });


        mainStage.show();
        //playerStage.show();
        //playerStage.setFullScreen(true);
    }

    public void getCurrentAttachment(){

        // we get the path of the current attachment
        finalPath = alternatePath(Controller.attachmentFiles.get(currentAttachment).getPath());

        // if the file is not a video
        if (!finalPath.toLowerCase().contains("mp4")) {
            System.out.println(finalPath);
            System.out.println("The final path: " + finalPath);
            // creating a new image with the final path
             imageAttachmentImageNotView = new Image(getClass().getResourceAsStream(finalPath));
            // setting the attachemtns image to the new image
            attachmentImage.setImage(imageAttachmentImageNotView);
           // attachmentImage.setFitWidth(imageAttachmentImageNotView.getWidth() * .5);
           // attachmentImage.setFitHeight(imageAttachmentImageNotView.getHeight() * .5);
            Controller.viewAttachments();
            // setting the display to the image that we just got
            //Controller.viewImage
        }
        else {
            System.out.println(finalPath);
            //System.out.println("files/email1/attatchments/LOL2.mp4 X2");
            // we have to build the video player. we send the vidoeStage to it.
            buildVideoPlayer();
        }

    }

    public void buildVideoPlayer() {

        // Locate the video using the final path from getCurrentAttachment method
        URL mediaUrl = getClass().getResource((String)finalPath);
        System.out.println(mediaUrl);
        String mediaStringUrl = mediaUrl.toExternalForm();
        mediaStringUrl = mediaStringUrl;
        System.out.println(mediaStringUrl);

        // Creates the Media
        Media media = new Media(mediaStringUrl);

        // Create a Media Player. we have a global player so we can stop it when we change the current attachment
        player = new MediaPlayer(media);

        // so video doesn't auto play.
        player.setAutoPlay(false);

        // Create a 400X300 MediaView
        //videoPlayerView = new MediaView(player);
        videoPlayerView = new MediaView(player);
        videoPlayerView.setFitWidth(400);
        videoPlayerView.setFitHeight(300);
        videoPlayerView.setSmooth(true);

        // Create the DropShadow effect
        DropShadow dropshadow = new DropShadow();
        dropshadow.setOffsetY(5.0);
        dropshadow.setOffsetX(5.0);
        dropshadow.setColor(Color.WHITE);
        videoPlayerView.setEffect(dropshadow);
        videoPlayerView.setStyle(cssLayout);
        Controller.viewAttachments();
    }


    public String alternatePath(String pathToChange){
        // below code is for chaing \'s to / so we can reference the attachment file

        //  arraylist is used to store our old path so we can change it
        ArrayList<Character> tmpPath = new ArrayList<>();

        // looping through the old path and adding it to the tmpPath
        for (int i = 0; i < pathToChange.length();i++){
            tmpPath.add(pathToChange.charAt(i));
        }

        // looping through the tmpPath and changing \'s to /
        for (int i = 0; i < tmpPath.size(); i++){
            if (Character.toString(tmpPath.get(i)).equals("\\")){
                tmpPath.set(i,'/');
            }
        }

        // replacing all spaces / commas with nothing
        String finalAltPath = tmpPath.toString().replaceAll(" ","").replaceAll(",","");
        System.out.println(finalAltPath);
        // removing the brackets from the ends of the string
        finalAltPath = finalAltPath.substring(1 , finalAltPath.length()-1 );

        return finalAltPath;
    }

    /*
  * Method used when the user logs off of the email client (closes the GUI).
   */
    @Override
    public void stop(){
        System.out.println("close");
        try {
            player.stop();
        }catch(java.lang.NullPointerException e){}
        mainStage.close();
        for(extraDisplay display : screens){
            display.playerStage.close();
        }
        return;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
