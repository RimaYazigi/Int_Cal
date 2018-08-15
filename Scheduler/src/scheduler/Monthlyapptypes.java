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
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class Monthlyapptypes {
    Stage ps;
    ResultSet rs;
    public Monthlyapptypes(Stage st)
    {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.CADETBLUE);
        
        ps = st;
        Text t1 = new Text();
        t1.setFont(Font.font("Yu Gothic",23));
        t1.setX(50); 
        t1.setY(50); 
        t1.setText("Appointments Types");
        
        Text t2 = new Text();
        t2.setFont(Font.font("Calibri Light",20));
        t2.setText("Select A Month:");
        
        TextField id = new TextField();
        id.setPrefWidth(40);
        
        ChoiceBox<String> months = new ChoiceBox<String>(); 
        months.setStyle("-fx-body-color: white  ; ");
        months.getItems().addAll("1 - January", "2 - February", "3 - March", "4 - April"
                ,"5 - May", "6 - June", "7 - July", "8 - August" , "9 - September", "10 - October"
                , "11 - November", "12 - December"); 

        Line line = new Line(); 
        
        Label l2 = new Label();
        l2.setFont(Font.font("Calibri Light",18));
        l2.setLayoutX(120);
        l2.setLayoutY(144);
        
        Label l3 = new Label();
        l3.setFont(Font.font("Calibri Light",18));
        l3.setLayoutX(75);
        l3.setLayoutY(175);
        
        Label result = new Label();
        result.setFont(Font.font("Calibri Light",21));
        result.setLayoutX(215);
        result.setLayoutY(173);
        
        GridPane g = new GridPane();
        g.setMinSize(500, 100); 
        g.setPadding(new Insets(10, 10, 10, 10));  
        g.setVgap(15); 
        g.setHgap(30);     
        g.setLayoutY(55);
        g.setAlignment(Pos.CENTER); 
        g.add(t2, 0, 0);       
        g.add(months, 1, 0); 
       
    
        Label label = new Label();
        label.setLayoutX(272);
        label.setLayoutY(125);
        label.setTextFill(Color.RED);
        
        Button b1 = new Button("Total Appointments by type");
        b1.setPrefSize(150, 50);
        b1.setLayoutX(320);
        b1.setLayoutY(155);
        b1.setStyle(" -fx-border-color: darkslateblue; -fx-body-color: white  ; ");
        b1.addEventHandler(MouseEvent.MOUSE_ENTERED, 
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        b1.setEffect(shadow);
                    }
            });
            b1.addEventHandler(MouseEvent.MOUSE_EXITED, 
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        b1.setEffect(null);
                    }
            });
        b1.setOnAction(e->
            {
                String choice = months.getValue();
                if (choice != null)
                {
                    System.out.println(choice);
                    int MonthNum = MonthInt(choice);
                                try {
                                        label.setText(" ");
                                        rs = new DataConnection().getMonthlyapptbytype(MonthNum);
                                        new ApptResults(ps).MonthlyApptbyType(rs,choice);
                                    
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                        label.setText("Error in database response.");
                                    }
                 }else
                 { 
                     label.setText("Please select a Month.");
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
            re.setOnAction(e -> {
                    ps.close();
            });
        
       
        Group root = new Group();
        ObservableList<Node> list = root.getChildren(); 
        list.addAll(t1,g,l2,l3,result,line,label,b1,re);       
        Scene s2 = new Scene(root,500,400);
        ps.setScene(s2);
        ps.show();
        
        
    }
    public static int MonthInt(String choice) {
        int result = 0;
        
        switch (choice)
        {
        case "1 - January":
            result = 1;
            break;                
        case "2 - February":
            result = 2;
            break;            
        case "3 - March":
            result = 3;
            break;
        case "4 - April":
            result = 4;
            break;
        case "5 - May":
            result = 5;
            break;           
        case "6 - June":
            result = 6;
            break;            
        case "7 - July":
            result = 7;
            break;
        case "8 - August":
            result = 8;
            break;
        case "9 - September":
            result = 9;
            break;                 
        case "10 - October":
            result = 10;
            break;            
        case "11 - November":
            result = 11;
            break;
        case "12 - December":
            result = 12;
            break;
        }
        
        return result;
    }
}
