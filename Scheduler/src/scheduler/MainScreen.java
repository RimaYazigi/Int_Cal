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

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Stream;
import static scheduler.Scheduler.logger;


 public class MainScreen extends Stage{
    
        BorderPane bPane = new BorderPane();
        private Button newAppt = new Button("Appointments by Month/week");
        private Button reportsButton = new Button("Reports");
        private Button customers = new Button("Customers");
        private Label space = new Label("               ");
        private Button exit = new Button("Exit");
        private Label title = new Label("Scheduler");
        
        ListView detailsView = new ListView();
        Label user = new Label();
        Label wait = new Label();
        LocalDate rightnow = LocalDate.now();
        int thisMonth = rightnow.getMonthValue();
        int thisYear = rightnow.getYear();
        int thisDay = rightnow.getDayOfMonth();
        public static YearMonth currentYearMonth;

        
public  MainScreen(Stage stage,String username, FileHandler fh){

    SimpleFormatter formatter = new SimpleFormatter();  
    fh.setFormatter(formatter);  


    
   
        title.setFont((Font.font ("Verdana", 22)));
        user.setFont(Font.font ("Verdana", 14));
        user.setTextFill(Color.RED);
        user.setText(username+" is logged in now");
        user.setTextFill(Color.web("#0076a3"));
        Stage CalendarStage = new Stage();
        BorderPane bPane = new BorderPane();
        bPane.setTop(getMainBar());
        bPane.setCenter(getcalendar());
        bPane.setLeft(getSideBar());
        
        
    exit.setOnAction(e->{
        logger.log(Level.INFO, "{0} logged out", username);
        System.exit(0);

    });

    reportsButton.setOnAction(e-> Reports.display(CalendarStage));
    customers.setOnAction(event -> {
         new MainCustomer(stage, username);    
     });
     
    newAppt.setOnAction(event ->{
        new MainAppointment(stage, username);
    });

     Scene scene = new Scene(bPane, 1000, 600);
     CalendarStage.setScene(scene);
     CalendarStage.setTitle("Calendar");
     CalendarStage.show();
 }

public void redraw(Stage CalendarStage, Scene scene) {
    bPane.getChildren().clear();
    CalendarStage.setScene(null);
    CalendarStage.setScene(scene);
} 
private ToolBar getMainBar(){
    ToolBar mainBar = new ToolBar();
    title.setAlignment(Pos.CENTER);
    mainBar.getItems().addAll(
                
                new Separator(),
                title);
        return mainBar;
    }
public ToolBar getSideBar(){
    ToolBar sidebar = new ToolBar();
    sidebar.setOrientation(Orientation.VERTICAL);
    
    sidebar.getItems().addAll(user, space, new Separator(),newAppt,new Separator(),reportsButton,new Separator() ,customers,new Separator() ,exit, wait);
    return sidebar;
}
    
    public static VBox getcalendar(){
 
        VBox calendar = new ShowCalendar(YearMonth.now()).getView();
        return calendar;
 
    }
    public void alarm(String username){ 

            Optional<Appointment> optional = DataConnection.getAppointments().stream()
                    .filter(t -> t.getCreatedBy().equals(username))
                    .filter(t->t.occuresOn(thisDay, thisMonth, thisYear))
                    .filter(t->t.getStart().toLocalTime().getMinute() > LocalTime.now().getMinute() 
                            && t.getStart().toLocalTime().getMinute() <= LocalTime.now().plusMinutes(15).getMinute())
                            .findAny();

       if(optional.isPresent()) 
       {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, ("You have an appointment within 15 minutes with :"+optional.get().toString()));       
           alert.showAndWait();
           System.out.println("found" +optional.toString() + username  );
       }
    }
 }