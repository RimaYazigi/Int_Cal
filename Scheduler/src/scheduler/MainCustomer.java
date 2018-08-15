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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Optional;
import java.util.function.Function;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class MainCustomer{
    GridPane customerpane;
    Stage mainstage;
    Scene mainscene;
    ObservableList<Customer> customers;

    
public MainCustomer (Stage stage2, String username)  {

        TableView<Customer> customerTable = new TableView<>();
        
        customerTable.getColumns().add(column("Customer Name", Customer::customerNameProperty));
        customerTable.getColumns().add(column("Address", Customer::addressProperty));
        customerTable.getColumns().add(column("Address2", Customer::address2Property));
        customerTable.getColumns().add(column("City", Customer::cityProperty));
        customerTable.getColumns().add(column("Postal code", Customer::postalCodeProperty));
        customerTable.getColumns().add(column("Phone", Customer::phoneProperty));
        customerTable.getColumns().add(column("Country", Customer::countryProperty));

        customerTable.setPrefWidth(700);
        customerTable.setItems(DataConnection.getCustomers());
        
        mainstage = stage2;
        Label Title = new Label("Customers");
        Title.setStyle("-fx-font: 18px Serif");
        stage2.setResizable(true);
       
        GridPane mainpane = new GridPane();
        mainpane.setHgap(20);
        mainpane.setPadding(new Insets(35));
        
        mainpane.setVgap(10);
        mainpane.add(Title, 0, 0,2,1);
        Button addCustomersButton = new Button("Add");
        Button modCustomersButton = new Button("Modify");
        Button delCustomersButton = new Button("delete");
        Button searchCustomer = new Button("Search");
        TextField txtsearchCustomer = new TextField();

        Button exit = new Button("Back");
        addCustomersButton.setPrefWidth(60);
        modCustomersButton.setPrefWidth(60);
        delCustomersButton.setPrefWidth(60);
        exit.setPrefWidth(60);
        
        HBox leftpaneButtons = new HBox(10, addCustomersButton, modCustomersButton, delCustomersButton);
        mainpane.setStyle("-fx-border-color: black");
        Label customersLabel = new Label(username + " is logged in");
        customersLabel.setFont(Font.font ("Verdana", 14));
        customersLabel.setTextFill(Color.RED);

        customersLabel.setMinWidth(10);
        
        HBox customersearchbar = new HBox(10, customersLabel,searchCustomer,txtsearchCustomer);
        
        mainpane.add(customersearchbar, 0, 1);
        mainpane.add(customerTable, 0,3,4,1);
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

        addCustomersButton.setOnAction(event -> {
            
            String check = "Add";
            AddModCustomer.display(check,customerTable.getSelectionModel().getSelectedItem(), customerTable, customers,username);
        });
        
        modCustomersButton.setOnAction(event->{
            String check = "Modify";
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            
            AddModCustomer.display(check,selectedCustomer, customerTable, customers,username);
        });
        
        modCustomersButton.disableProperty().bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));
        delCustomersButton.disableProperty().bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, ("Are you sure ?"));


        delCustomersButton.setOnAction(e->{
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            
            alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
             try {
                new DataConnection().deleteCustomer(selectedCustomer, username);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            customerTable.getItems().remove(customerTable.getSelectionModel().getSelectedIndex());
            });
         });

        
        searchCustomer.setOnAction(e -> { 
           try{
                if (txtsearchCustomer.getText() == null || txtsearchCustomer.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Please enter customer name");
                }
                String searchName = txtsearchCustomer.getText().trim();
                Optional<Customer> optional = customerTable.getItems().stream()
                        .filter(customer -> customer.getCustomerName().equals(searchName))
                        .findAny();
                if(optional.isPresent()) {
                    customerTable.setStyle("-selected-color:blue;");
                    customerTable.getSelectionModel().select( optional.get());                    
                    customerTable.scrollTo( optional.get()); 
                }
                else {
                    alert.initOwner(customerTable.getScene().getWindow());
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setHeaderText("Item not found!!!");
                    alert.setContentText(null);
                    alert.show();
                }
           } catch (IllegalArgumentException ex) {
               Alert a = new Alert(AlertType.ERROR);
               a.setContentText(ex.getMessage());
               a.setTitle("Error");
               a.showAndWait();
           }
            });
        
                stage2.setScene(mainscene);
                stage2.setMinWidth(700);
                stage2.setMinHeight(400);
                stage2.show();
                stage2.alwaysOnTopProperty();
    }

    private <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        
        return col ;
    }
}