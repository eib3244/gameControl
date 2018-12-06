import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class extraDisplay {
    Stage playerStage;
    Scene playerScene;
    BorderPane playerBorderPane;
    VBox playVbox;
    GridPane playGrid;
    TextArea textInfo;
    ImageView imageViewForPlayer;
    MediaView mediaViewForPlayer;

    Label timeLabel = new Label("Time Left: ");
    Label timeLeft = new Label();

    Label clueLabel = new Label("Clues Left: ");
    Label cluesLeft = new Label();

    Rectangle2D bounds;

    extraDisplay(Screen secondaryScreen){
        this.playerStage = new Stage();
        this.playerBorderPane = new BorderPane();
        this.playVbox = new VBox();
        this.playGrid = new GridPane();

        this.playerScene = new Scene(playerBorderPane);
        this.playerStage.setScene(playerScene);

        this. playerBorderPane.setCenter(playVbox);
        this.playVbox.getChildren().add(playGrid);
        this.textInfo = new TextArea();
        playVbox.getChildren().add(textInfo);

         this.playGrid.add(timeLabel,0,0);
         this.playGrid.add(timeLeft,1,0);
         this.playGrid.add(clueLabel,0,1);
         this.playGrid.add(cluesLeft,1,1);

        this.bounds = secondaryScreen.getVisualBounds();

        this.playerStage.setX(bounds.getMinX());
        this.playerStage.setY(bounds.getMinY());
        this.playerStage.setHeight(bounds.getHeight());
        this.playerStage.setWidth(bounds.getWidth());

        this.playerStage.setFullScreen(true);
        this.playerStage.setFullScreenExitHint("");

        this.timeLeft.setText(Integer.toString(Controller.minOnTimer) + ":" + Integer.toString(Controller.secOnTimer)+ "0");
        this.cluesLeft.setText(Integer.toString(Controller.cluesCounter));

         this.playVbox.setStyle(GUI.cssLayout2);
         this.playGrid.setStyle(GUI.cssLayout);
         this.timeLabel.setStyle("-fx-font-size: 2em; ");
         this.timeLeft.setStyle("-fx-font-size: 2em; ");
         this.cluesLeft.setStyle("-fx-font-size: 2em; ");
         this.clueLabel.setStyle("-fx-font-size: 2em; ");

         this.playerStage.show();


    }






    //boundry boarder (see how to do atm)
    // stage, scene,vbox,gridpane, etc?
    //could use get screans and then create these displays by iterating over it
    // update would be iterate over an array list of this type



}
