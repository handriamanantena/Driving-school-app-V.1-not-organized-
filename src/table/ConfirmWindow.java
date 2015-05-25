package table;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andrians
 */
public class ConfirmWindow extends Stage{
    
    private final Button okButton = new Button("OK");
    private final Button cancelButton = new Button("Cancel");

    ConfirmWindow(String text, Region region, int width, int height){
       initModality(Modality.APPLICATION_MODAL);
       Scene scene = new Scene(new Group(),width,height);
       setScene(scene);
       HBox buttonBox = new HBox(5);
       okButton.setMinWidth(100);
       cancelButton.setMinWidth(100);
       buttonBox.getChildren().addAll(okButton,cancelButton);
       buttonBox.setStyle("-fx-hpos: center");
       buttonBox.setAlignment(Pos.BOTTOM_CENTER);
       Label textDiologue = new Label(text);
       textDiologue.setStyle("-fx-font-size: 20");
       GridPane gridpane = (GridPane)region;
       gridpane.setStyle(" -fx-padding: 5");
       VBox vbox = new VBox();
       vbox.setStyle(" -fx-padding: 10");
       initStyle(StageStyle.UTILITY);
       vbox.getChildren().addAll(textDiologue,gridpane,buttonBox);
       final Separator separator1 = new Separator();
       final Separator separator2 = new Separator();
       vbox.getChildren().add(1,separator1);
       vbox.getChildren().add(3,separator2);
      ((Group) scene.getRoot()).getChildren().addAll(vbox);
    }
    
     Button getOkButton(){
         return okButton;
    }
     
     Button getCancelButton(){
         return cancelButton;
     }
}
