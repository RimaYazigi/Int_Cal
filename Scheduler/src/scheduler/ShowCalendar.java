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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;



public class ShowCalendar {
  
    public static ArrayList<DayCell> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    GridPane calendar;
    public static YearMonth currentYearMonth;
    DayCell ap;
    ObservableList<Appointment> aps;
   

    public ShowCalendar(YearMonth yearMonth) {

        currentYearMonth = yearMonth;
        GridPane calendar = new GridPane();
        calendar.setPrefSize(800, 700);
        calendar.setStyle("-fx-background-color: white;");
        calendar.setGridLinesVisible(true);
        calendar.setPadding(new Insets(20,20,20,20));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
               DayCell ap = new DayCell();
                ap.setPrefSize(200,200);
                calendar.add(ap,j,i);
                allCalendarDays.add(ap);
            }
        }
        
        Text[] dayNames = new Text[]{ new Text("   Sunday"), new Text("Monday"), new Text("Tuesday"),
                                        new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
                                        new Text("Saturday") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            GridPane days = new GridPane();
            days.setPrefSize(250, 30);
            days.getChildren().add(txt);
            dayLabels.add(days, col++, 0);
        }
        calendarTitle = new Text();
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e ->previousMonth()); 
        
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        populateCalendar(yearMonth);
        view = new VBox(titleBar, dayLabels, calendar);

    }
  
    public ShowCalendar() {
    }

   
    @SuppressWarnings("null")
    public void populateCalendar(YearMonth yearMonth) {

        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
         
        for (Iterator<DayCell> it = allCalendarDays.iterator(); it.hasNext();) {
            DayCell apcell = it.next();
            if (!apcell.getChildren().isEmpty()) {
                apcell.getChildren().remove(0);//==******************************
            }
            apcell.setDate(calendarDate);
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            List<Appointment> appointments = DataConnection.getAppointments();
            int day = apcell.getDate().getDayOfMonth();
            int month = apcell.getDate().getMonthValue();
            int year = apcell.getDate().getYear();
            ObservableList<Appointment> aptsday = appointments.stream().filter(p-> p.occuresOn(day, month, year))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            ListView<Appointment> listView = new ListView<>(aptsday);
            listView.setItems(aptsday);
            listView.setCellFactory((ListView<Appointment> param) -> {
                ListCell<Appointment> cell = new ListCell<Appointment>(){
                    @Override
                    public void updateItem(Appointment item, boolean empty){
                        super.updateItem(item,empty);
                        if (item != null){
                            setText(item.getCustomerName() + " @ " + item.getStart().toLocalDateTime().getHour()+":"+item.getStart().toLocalDateTime().getMinute());
                        }
                    }
                };
                return cell;
            });
            apcell.getChildren().clear();
            apcell.setStyle(null);
            apcell.getChildren().addAll(txt,listView);
            if (apcell.getDate().getDayOfMonth() == LocalDate.now().getDayOfMonth() & apcell.getDate().getMonth() == LocalDate.now().getMonth()
                    & apcell.getDate().getYear() == LocalDate.now().getYear()){
                
                apcell.setStyle("-fx-background-color: DeepSkyBlue");
            }
            if (apcell.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) || apcell.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            apcell.setStyle("-fx-background-color: silver");
            }
            calendarDate = calendarDate.plusDays(1);
        } 
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
              
    }
    public final DayCell getAp() {
        return ap;
    }

    public final void setAp(DayCell ap) {
        this.ap = ap;
    }

    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
    }

    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
  
        populateCalendar(currentYearMonth);
    }

    public static void setcalDate(YearMonth date) {
        date = currentYearMonth;
    }
    public static YearMonth getcalDate() {
        return currentYearMonth;
    }
    public VBox getView() {
        return view;
    }

    public ArrayList<DayCell> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<DayCell> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
}