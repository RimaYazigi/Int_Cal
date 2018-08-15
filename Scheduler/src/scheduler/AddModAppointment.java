package scheduler;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


        public class AddModAppointment {
            private static String actiontype;

            static Appointment thisappointment;
           static ComboBox<String> customerBox;

            
            public static void display(String typeIn, TableView<Appointment> appointmentTable, Appointment selectedAppointment, ObservableList<Appointment>appointments, String username)
            {
                actiontype = typeIn;
                ZonedDateTime timePoint = ZonedDateTime.now();

                thisappointment = selectedAppointment;
                Stage appointmentStage = new Stage();
               
                appointmentStage.initModality(Modality.APPLICATION_MODAL);
                appointmentStage.setTitle("Add an appointment");
                appointmentStage.setMinWidth(250);
                appointmentStage.setMinHeight(500);

                ComboBox<String> customerBox = new ComboBox<>();
                customerBox.setPromptText("select a customer...");
                fillcustomers(customerBox);
               
                Button SaveButton = new Button("Save");
                Button CancelButton = new Button("Cancel");
                
                ComboBox<Integer> starthours = new ComboBox<>();                
                ComboBox<Integer> endhours = new ComboBox<>(); 
                ComboBox<Integer> startminutes = new ComboBox<>(); 
                ComboBox<Integer> endminutes = new ComboBox<>(); 

                TextField NameTextField = new TextField();
                Button addcustomer = new Button();
                addcustomer.setText("add a new customer");
                TextField titleTextField = new TextField();
                TextField descriptionTextField = new TextField();
                descriptionTextField.setPromptText("Enter new or regular");
                TextField locationTextField = new TextField();
                TextField contactTextField = new TextField();
                TextField urlTextField = new TextField();
                
                DatePicker date = new DatePicker();
                date.setValue(LocalDate.now());
                
                ObservableList<Integer> hours = FXCollections.observableArrayList();
                for(int i=9; i<=16; i++) {
                    hours.add(i );
                }
                starthours.getItems().addAll(hours);
                endhours.getItems().addAll(hours);

                ObservableList<Integer> minutes = FXCollections.observableArrayList();
                for(int i=0; i<=55; i+=5) {
                    minutes.add(i );
                }
                startminutes.getItems().addAll(minutes);
                endminutes.getItems().addAll(minutes);
                HBox save_cancelButton= new HBox(7);
                save_cancelButton.getChildren().addAll(SaveButton, CancelButton);
                
                HBox starttime = new HBox(5);
                starttime.getChildren().addAll(new Label("StartTime: "),starthours,startminutes);
                HBox endtime = new HBox(5);
                endtime.getChildren().addAll(new Label("EndTime:  "),endhours,endminutes);
                
                GridPane appointmentPane = new GridPane();
                appointmentPane.setVgap(10);
                appointmentPane.setHgap(10);
                appointmentPane.setPadding(new Insets(5,5,5,5));
                appointmentPane.add(save_cancelButton, 0, 12,2,1);
                appointmentPane.addRow(1, new Label("Customer's Name: "), customerBox);   
                appointmentPane.addRow(2, new Label("Or: "), addcustomer);
                appointmentPane.addRow(3, new Label("title: "), titleTextField);
                appointmentPane.addRow(4, new Label("Description: "), descriptionTextField);
                appointmentPane.addRow(5, new Label("Location: "), locationTextField);
                appointmentPane.addRow(6, new Label("Contact: "), contactTextField);
                appointmentPane.addRow(7, new Label("Url: "),urlTextField);
                appointmentPane.add(starttime, 0, 8, 2, 1);
                appointmentPane.add(endtime, 0, 9, 2, 1);

                appointmentPane.addRow(10, new  Label("Date: "), date);
               
                //----------------------------------
                addcustomer.setOnAction(event -> {
                    TableView<Customer> customerTable = new TableView<>();
                    ObservableList<Customer> customers = DataConnection.getCustomers();
                    Customer tempCustomer = new Customer();
                    String check = "Add";
                    fillcustomers(customerBox);

                    AddModCustomer.display(check , tempCustomer , customerTable, customers, username);
                    fillcustomers(customerBox);

                    customerBox.getSelectionModel().selectLast();
                    System.out.println("this: "+ customerBox.getSelectionModel().getSelectedItem().toString());
                });     

                if(actiontype.equals("Modify")){
                    customerBox.setValue(String.valueOf(thisappointment.getCustomerName()));
                    customerBox.setDisable(true);
                    addcustomer.setDisable(true);
                    titleTextField.setText(String.valueOf(thisappointment.getTitle()));
                    descriptionTextField.setText(String.valueOf(thisappointment.getDescription()));
                    locationTextField.setText(String.valueOf(thisappointment.getLocation()));
                    contactTextField.setText(String.valueOf(thisappointment.getContact()));
                    urlTextField.setText(String.valueOf(thisappointment.getUrl()));
                    starthours.setValue(thisappointment.getStart().getHour());
                    startminutes.setValue(thisappointment.getStart().getMinute());
                    endhours.setValue(thisappointment.getEnd().getHour());
                    endminutes.setValue(thisappointment.getEnd().getMinute());
                    date.setValue(thisappointment.getStart().toLocalDate());
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure ?");

                    CancelButton.setOnAction(e->{alert.showAndWait()
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> appointmentStage.close());
                    });       
                            
                SaveButton.setOnAction(e -> {   
                    
                    if(actiontype.equals("Add")) {
                        
                        try{ 

                            String time =starthours.getValue() +":"+startminutes.getValue();
                            System.out.println(time);
                          
                            ZoneId zoneid = ZoneId.systemDefault();
                            System.out.println(zoneid.toString());

                          LocalDate startDate = date.getValue();
                          LocalTime startTime = LocalTime.of(starthours.getValue().intValue(), startminutes.getValue().intValue());
                          ZonedDateTime startDateTime = ZonedDateTime.of(startDate, startTime, ZoneId.systemDefault());//.withZoneSameInstant(ZoneId.of("UTC"));

                          LocalDate endDate = date.getValue();
                          LocalTime endTime = LocalTime.of(endhours.getValue().intValue(), endminutes.getValue().intValue());
                          ZonedDateTime endDateTime = ZonedDateTime.of(endDate, endTime, ZoneId.systemDefault());//.withZoneSameInstant(ZoneId.of("UTC"));
                            //-----------------------------------------
                              int day = date.getValue().getDayOfMonth();
                              int month = date.getValue().getMonthValue();
                              int year = date.getValue().getYear();
                            CheckInput.isOverlapping(day, month, year, startTime, endTime);
                            CheckInput.OnWeekend(startDateTime);
                            Appointment tempAppointment = new Appointment(customerBox.getValue(),titleTextField.getText(),descriptionTextField.getText(),locationTextField.getText(),contactTextField.getText(),urlTextField.getText(),startDateTime, endDateTime, username);
                            appointmentTable.getItems().add(tempAppointment);
                            
                                new DataConnection().addNewAppointment(tempAppointment,username,timePoint,appointments);
                                LocalDate localDate =tempAppointment.getStart().toLocalDate();
                                int weekNumber = tempAppointment.getStart().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                                System.out.println("the added week numer is" + weekNumber);
                                System.out.println(NameTextField.getText()+ "is registered");
                            new ShowCalendar(YearMonth.now()).getView();                            
                            appointmentStage.close();
                        }
                        catch (Exception ex1) {
                            Alert a = new Alert(AlertType.ERROR);
                            a.setContentText(ex1.getMessage());
                            a.setTitle("Error");
                            a.showAndWait();
                      }
                    }
                
                    if (actiontype.equals("Modify")){
                        try{ 
                            LocalDate startDate = date.getValue();
                            LocalTime startTime = LocalTime.of(starthours.getValue().intValue(), startminutes.getValue().intValue());
                            ZonedDateTime startDateTime = ZonedDateTime.of(startDate, startTime, ZoneId.systemDefault());//.withZoneSameInstant(ZoneId.of("UTC"));

                            LocalDate endDate = date.getValue();
                            LocalTime endTime = LocalTime.of(endhours.getValue().intValue(), endminutes.getValue().intValue());
                            ZonedDateTime endDateTime = ZonedDateTime.of(endDate, endTime, ZoneId.systemDefault());//.withZoneSameInstant(ZoneId.of("UTC"));

                            selectedAppointment.setCustomerName(customerBox.getValue());
                            selectedAppointment.setTitle(titleTextField.getText());
                            selectedAppointment.setDescription(descriptionTextField.getText());
                            selectedAppointment.setLocation(locationTextField.getText());
                            selectedAppointment.setContact(contactTextField.getText());
                            selectedAppointment.setUrl(urlTextField.getText());
                            selectedAppointment.setStart(startDateTime);
                            selectedAppointment.setEnd(endDateTime);
                            int day = date.getValue().getDayOfMonth();
                            int month = date.getValue().getMonthValue();
                            int year = date.getValue().getYear();
                          CheckInput.isOverlapping(day, month, year, startTime, endTime);
                          CheckInput.OnWeekend(startDateTime);
                            new DataConnection().updateAppointment(selectedAppointment,username,timePoint,appointments);
                            new ShowCalendar(YearMonth.now()).getView();                            
                            appointmentStage.close();   
                    }
                        catch (Exception ex1) {
                            
                            Alert a = new Alert(AlertType.ERROR);
                            a.setContentText(ex1.getMessage());
                            a.setTitle("Error");
                            a.showAndWait();
                      }   
                    } 
                    });
                
                Scene addModAppointmentScene = new Scene(appointmentPane, 500, 500);
                appointmentStage.setScene(addModAppointmentScene);
                appointmentStage.showAndWait();
            }


private static   ComboBox<String> fillcustomers(ComboBox<String> customerBox) {
    for(Customer c : DataConnection.getCustomers())
        if(!customerBox.getItems().contains(c.getCustomerName() ))
            customerBox.getItems().add(c.getCustomerName());
    return customerBox;
}
}

              

       