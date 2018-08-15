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
    import java.sql.ResultSet;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

    public class Apptbyuser 
    {
        Stage ps;
        ResultSet rs;
        public Apptbyuser(Stage st)
        {
            DropShadow shadow = new DropShadow();
            shadow.setColor(Color.CADETBLUE);
            
            ps = st;
            Text t1 = new Text();
            t1.setFont(Font.font("Yu Gothic",23));
            t1.setX(50); 
            t1.setY(50); 
            t1.setText("User's appointments");
            
            ComboBox<String> userBox = new ComboBox<>();
            userBox.setPromptText("select a user...");
            fillusers(userBox);
            
           userBox.setPromptText("Pick a user:");
            
            TextField id = new TextField();
            id.setPrefWidth(40);
            
            Label label = new Label();
            label.setLayoutX(300);
            label.setLayoutY(105);
            label.setTextFill(Color.RED);
            
            Label l2 = new Label();
            l2.setFont(Font.font("Calibri Light",18));
            
            
            Label result = new Label();
            result.setFont(Font.font("Calibri Light",21));
            
            
            GridPane g = new GridPane();
            g.setMinSize(500, 100); 
            g.setPadding(new Insets(10, 10, 10, 10));  
            g.setVgap(15); 
            g.setHgap(30);     
            g.setLayoutY(65);
            g.setAlignment(Pos.CENTER); 
            g.add(userBox, 0, 0);       
 
            Button b2 = new Button("Appointments by user");
            b2.setPrefSize(150, 50);
            b2.setLayoutX(175);
            b2.setLayoutY(260);
            b2.setStyle(" -fx-border-color: darkslateblue; -fx-body-color: white  ; ");
            b2.addEventHandler(MouseEvent.MOUSE_ENTERED, 
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            b2.setEffect(shadow);
                        }
                });
                b2.addEventHandler(MouseEvent.MOUSE_EXITED, 
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            b2.setEffect(null);
                        }
                });
            b2.setOnAction(e->{            
                            String userpicked = userBox.getValue();                                 
                                        try {
                                            new userResults(ps, userpicked).ApptbyUser(userpicked);
                                        } catch (Exception e1) {
                                            e1.printStackTrace();
                                            label.setText("Please pick a user.");
                                        }               
                 });
                       
            Image returni = new Image(getClass().getResource("back.png").toExternalForm(), 50, 50, true, true);
            double r=1.5;
            
            Button re = new Button("Back");
            re.setShape(new Circle(r));
            re.setMinSize(2*r, 2*r);
            re.setMaxSize(2*r, 2*r);

            re.setLayoutX(225);
            re.setLayoutY(350);
            re.setGraphic(new ImageView(returni));
            
            re.addEventHandler(MouseEvent.MOUSE_ENTERED, 
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            re.setEffect(shadow);
                        }
                });
                re.addEventHandler(MouseEvent.MOUSE_EXITED, 
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            re.setEffect(null);
                        }
                });
            
            re.setOnAction(new EventHandler<ActionEvent>() 
            {
                public void handle(ActionEvent e) 
                {
                    ps.close();
                }
             } );
            
            Group root = new Group();
            ObservableList<Node> list = root.getChildren(); 
            list.addAll(t1,g,label,b2,re);       

            Scene s2 = new Scene(root,500,400);        
            ps.setScene(s2);
            ps.show();           
        }
     
        private static void fillusers(ComboBox<String> userBox) {
            for(User c : DataConnection.getUsers())
                if(!userBox.getItems().contains(c.getUserName() ))
                    userBox.getItems().add(c.getUserName());
    }
    }
