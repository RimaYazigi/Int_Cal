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

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

    public class userResults
    {
        Stage stage;
        TableView<Appointment> table;
        DropShadow shadow = new DropShadow();
        
        public userResults(Stage ps, String userpicked )
        {
        this.table = new TableView<>();
            stage = ps;
            shadow.setColor(Color.CADETBLUE);
        }
        
        @SuppressWarnings({"rawtypes", "unchecked" })
        public void ApptbyUser(String userpicked)
        {
            try {
                List<Appointment> appointments = DataConnection.getAppointments();

                ObservableList<Appointment> listbyuser = appointments.stream().filter(p-> p.getCreatedBy().equals(userpicked))

                      .collect(Collectors.toCollection(FXCollections::observableArrayList));
                
                Text t1 = new Text();
                t1.setText("Appt types by user"); 
                t1.setFont(Font.font("Yu Gothic",18));
                
                Text t2 = new Text();
                t2.setText("ID: " + userpicked);
                t2.setFont(Font.font("Yu Gothic",18));
                
                Line line = new Line(); 
                line.setStartX(40); 
                line.setStartY(40); 
                line.setEndX(460); 
                line.setEndY(40);
                
                GridPane g = new GridPane();
                g.setMinSize(500, 50); 
                g.setPadding(new Insets(10, 10, 10, 10));  
                g.setVgap(15); 
                g.setHgap(30);     
                g.setLayoutY(0);
                g.setAlignment(Pos.CENTER); 
                g.add(t1, 0, 0);       
                g.add(t2, 1, 0); 
                
                
                table.setEditable(true);
                table.setLayoutX(100);
                table.setLayoutY(50);
                table.setPrefSize(350, 266);
                table.setItems(listbyuser);
                
                TableColumn nameCol = new TableColumn("Name");
                nameCol.setCellValueFactory(
                        new PropertyValueFactory<Appointment,String>("customerName"));
                nameCol.setMinWidth(130);
                
                TableColumn startCol = new TableColumn("Start");
                startCol.setCellValueFactory(
                        new PropertyValueFactory<ApptType,String>("start"));
                startCol.setMinWidth(90);
                
                TableColumn endCol = new TableColumn("End");
                endCol.setCellValueFactory(
                        new PropertyValueFactory<ApptType,String>("end"));
                endCol.setMinWidth(90);
                
                table.getColumns().addAll(nameCol, startCol,endCol);
                
                
                Image returni = new Image(getClass().getResource("back.png").toExternalForm(), 50, 50, true, true);
                double r=1.5;
                Button re = new Button("Back");
                re.setShape(new Circle(r));
                re.setMinSize(2*r, 2*r);
                re.setMaxSize(2*r, 2*r);

                re.setLayoutX(225);
                re.setLayoutY(350);
                re.setGraphic(new ImageView(returni));
                
                re.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    re.setEffect(shadow);
                });
                    re.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                        re.setEffect(null);
                });
                re.setOnAction((ActionEvent e) -> {
                    new Apptbyuser(stage);
                });
            
                    Group root = new Group();
                    ObservableList<Node> olist = root.getChildren(); 
                    olist.addAll(table,re,g,line);       

                    Scene s1 = new Scene(root,500,400);
                    stage.setScene(s1);
                    stage.show();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
