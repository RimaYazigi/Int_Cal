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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


    public class MainAppointment{
        GridPane appointmentpane;
        Stage mainstage;
        Scene mainscene;
        ObservableList<Appointment> appointments;

        
    public MainAppointment (Stage stage2, String username)  {

            TableView<Appointment> appointmentTable = new TableView<>();
            appointmentTable.getColumns().add(column("Customer Name", Appointment::customerNameProperty));
            appointmentTable.getColumns().add(column("Appointment Title", Appointment::titleProperty));
            appointmentTable.getColumns().add(column("Description", Appointment::descriptionProperty));
            appointmentTable.getColumns().add(column("location", Appointment::locationProperty));
            appointmentTable.getColumns().add(column("Contact", Appointment::contactProperty));
            appointmentTable.getColumns().add(column("url", Appointment::urlProperty));
            appointmentTable.getColumns().add(column("Start", Appointment::startProperty));
            appointmentTable.getColumns().add(column("End", Appointment::endProperty));
            appointmentTable.getColumns().add(column("Consultant", Appointment::createdByProperty));

//            appointmentTable.setPrefWidth(900);
            appointmentTable.setPrefSize(1000,500 );
            appointmentTable.setItems(DataConnection.getAppointments());
            
            mainstage = stage2;
            Label Title = new Label("Appointments");
            Title.setStyle("-fx-font: 18px Serif");
            stage2.setResizable(true);
           
            GridPane mainpane = new GridPane();
            mainpane.setHgap(20);
            mainpane.setPadding(new Insets(35));
            
            mainpane.setVgap(10);
            mainpane.add(Title, 0, 0,2,1);
            Button addAppointmentsButton = new Button("Add");
            Button modAppointmentsButton = new Button("Modify");
            Button delAppointmentsButton = new Button("delete");
            
            Button exit = new Button("Back");
            addAppointmentsButton.setPrefWidth(60);
            modAppointmentsButton.setPrefWidth(60);
            delAppointmentsButton.setPrefWidth(60);
            exit.setPrefWidth(60);
            
            HBox leftpaneButtons = new HBox(10, addAppointmentsButton, modAppointmentsButton, delAppointmentsButton);
            mainpane.setStyle("-fx-border-color: black");
            Label appointmentsLabel = new Label(username + " is logged in");
            appointmentsLabel.setFont(Font.font ("Verdana", 14));
            appointmentsLabel.setTextFill(Color.RED);
            
            appointmentsLabel.setMinWidth(10);
            
            RadioButton permonth = new RadioButton("Current Month ");
            RadioButton perweek = new RadioButton("Current Week ");
            RadioButton all = new RadioButton("All Appointments");
            HBox paneForRadioButtons = new HBox(5);
            ToggleGroup group = new ToggleGroup();
            permonth.setToggleGroup(group);
            perweek.setToggleGroup(group);
            all.setToggleGroup(group);
            all.setSelected(true);
            paneForRadioButtons.getChildren().addAll(permonth, perweek, all);

            HBox appointmentbar = new HBox(10, appointmentsLabel, paneForRadioButtons);
            
            mainpane.add(appointmentbar, 0, 1);
            mainpane.add(appointmentTable, 0,3,4,1);
            mainpane.add(leftpaneButtons, 0, 4);
            mainpane.setHalignment(leftpaneButtons, HPos.RIGHT);
            mainpane.add(exit, 4, 5);
            Scene mainscene = new Scene(mainpane, 700, 250, Color.WHITE);
            
            exit.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to go back ?", ButtonType.YES,
                        ButtonType.NO);
                alert.setTitle("Exit");
                alert.setHeaderText(null);
                Optional<ButtonType> result = alert.showAndWait();
                //
                if (result.get() == ButtonType.YES) {
                 
                    stage2.close();
                }
            });
            permonth.setOnAction(e->{
                if (permonth.isSelected()) {
                ObservableList<Appointment> listthismonth = DataConnection.getAppointments().stream()
                  .filter(p-> p.getStart().getMonthValue() == ZonedDateTime.now().getMonthValue())
                  .collect(Collectors.toCollection(FXCollections::observableArrayList));
                  
                appointmentTable.setItems(listthismonth);
                
                }
            });
            all.setOnAction(e->{
                if (all.isSelected()) {
                appointmentTable.setItems(DataConnection.getAppointments());
                }
            });
            perweek.setOnAction(e->{
                if (perweek.isSelected()){
                List<Appointment> appointments = DataConnection.getAppointments();
                int weekNumber = ZonedDateTime.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                System.out.println("this week number is: "  +weekNumber);
                ObservableList<Appointment> listthisweek = appointments.stream()
                  .filter(p-> p.getStart().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == weekNumber)
                  .collect(Collectors.toCollection(FXCollections::observableArrayList));
                  appointmentTable.setItems(listthisweek);
                }
            });
            addAppointmentsButton.setOnAction(event -> {
                
                String check = "Add";
                AddModAppointment.display(check,appointmentTable,  appointmentTable.getSelectionModel().getSelectedItem(), appointments, username);
               
               
            });
            
            modAppointmentsButton.setOnAction(event->{
                String check = "Modify";
                Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                
                AddModAppointment.display(check,appointmentTable, selectedAppointment, appointments,username);
            });
            
            modAppointmentsButton.disableProperty().bind(Bindings.isEmpty(appointmentTable.getSelectionModel().getSelectedItems()));
            delAppointmentsButton.disableProperty().bind(Bindings.isEmpty(appointmentTable.getSelectionModel().getSelectedItems()));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, ("Are you sure ?"));


            delAppointmentsButton.setOnAction(e->{
                Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                
                alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
                    try {
                        new DataConnection().deleteAppointment(selectedAppointment, username);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                appointmentTable.getItems().remove(appointmentTable.getSelectionModel().getSelectedIndex());
                        });
             });

                    stage2.setScene(mainscene);
                    stage2.setMinWidth(1000);
                    stage2.setMinHeight(500);
                    stage2.show();
                    stage2.alwaysOnTopProperty();
        }

        
        private <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
            TableColumn<S,T> col = new TableColumn<>(title);
            col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
            
            return col ;
        }
    }
