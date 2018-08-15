/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

/**
 *
 * @author Rima
 */
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Reports
{
    public Reports(Stage ps) {
    }

    public Reports() {
    }

    public static void display(Stage ps)
    {
        Stage window = new Stage();
        window.setTitle("Reports");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setOnCloseRequest(event1 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to go back ?", ButtonType.YES,
                        ButtonType.NO);
                alert.setTitle("Exit");
                alert.setHeaderText(null);
                Optional<ButtonType> result = alert.showAndWait();
                //
                if (result.get() == ButtonType.YES) {
                    window.close();
                }
            });
   
        Label types = new Label("Number of appointment types by month");
        Label schedule = new Label("Schedule for each consultant");
        Label custbycity = new Label("Display customers by city");
       
        Button typesButton = new Button("Go");
        Button scheduleButton = new Button("Go");
        Button custbycityButton = new Button("Go");
        
        Button back = new Button("Back");
        typesButton.setOnAction(e-> new Monthlyapptypes(window));
        scheduleButton.setOnAction(e-> new Apptbyuser(window));
        custbycityButton.setOnAction(e-> new CustomerByCity(window));
       

        back.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to go back ?", ButtonType.YES,
                    ButtonType.NO);
            alert.setTitle("Exit");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                window.close();
            }
        });
        
        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(15, 15, 15, 15));

        GridPane.setConstraints(types, 0,1);
        GridPane.setConstraints(schedule, 0, 3);
        GridPane.setConstraints(custbycity, 0, 5);
        GridPane.setConstraints(typesButton, 3, 1);
        GridPane.setConstraints(scheduleButton, 3, 3);
        GridPane.setConstraints(custbycityButton, 3, 5);
        GridPane.setConstraints(back, 0, 7);

        layout.getChildren().addAll(types, custbycity, schedule, back, typesButton, scheduleButton, custbycityButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
