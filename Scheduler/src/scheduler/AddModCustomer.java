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
 
import java.time.ZonedDateTime;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


        public class AddModCustomer {
            private static String actiontype;
            
            static Customer thiscustomer;
           

            public static void display(String typeIn, Customer selectedCustomer, TableView<Customer> customerTable, ObservableList<Customer> customers, String username)
            {
                actiontype = typeIn;
                ZonedDateTime timePoint = ZonedDateTime.now();
                thiscustomer = selectedCustomer;
                Stage customerStage = new Stage();
              
                customerStage.initModality(Modality.APPLICATION_MODAL);
                customerStage.setTitle("Add an appointment");
                customerStage.setMinWidth(250);
                customerStage.setMinHeight(300);

                Button SaveButton = new Button("Save");
                Button CancelButton = new Button("Cancel");
                
                TextField NameTextField = new TextField();
                TextField AddressTextField = new TextField();
                TextField Address2TextField = new TextField();
                TextField CityTextField = new TextField();
                TextField postalCodeTextField = new TextField();
                TextField phoneTextField = new TextField();
                TextField countryTextField = new TextField();
                
                HBox save_cancelButton= new HBox(5);
                save_cancelButton.getChildren().addAll(SaveButton, CancelButton);

                GridPane customerPane = new GridPane();
                customerPane.setVgap(10);
                customerPane.setHgap(10);
                customerPane.setPadding(new Insets(10,10,10,10));
                customerPane.add(SaveButton,1,8);
                customerPane.add(CancelButton, 5, 8);
                customerPane.addRow(1, new Label("Customer's Name: "), NameTextField);                
                customerPane.addRow(2, new Label("Address: "), AddressTextField);
                customerPane.addRow(3, new Label("Address2: "), Address2TextField);
                customerPane.addRow(4, new Label("City: "), CityTextField);
                customerPane.addRow(5, new Label("Postal code: "), postalCodeTextField);
                customerPane.addRow(6, new Label("Phone: "),phoneTextField);
                customerPane.addRow(7, new Label("Country: "),countryTextField);               
                
                if(actiontype.equals("Modify")){
                    NameTextField.setText(String.valueOf(thiscustomer.getCustomerName()));
                    AddressTextField.setText(String.valueOf(thiscustomer.getAddress()));
                    Address2TextField.setText(String.valueOf(thiscustomer.getAddress2()));
                    CityTextField.setText(String.valueOf(thiscustomer.getCity()));
                    postalCodeTextField.setText(String.valueOf(thiscustomer.getPostalCode()));
                    phoneTextField.setText(String.valueOf(thiscustomer.getPhone()));
                    countryTextField.setText(String.valueOf(thiscustomer.getCountry()));
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure ?");

                    CancelButton.setOnAction(e->{alert.showAndWait()
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> customerStage.close());
                    });       
                SaveButton.setOnAction(e -> {   
                    
                    if(actiontype.equals("Add")) {
                        try{ 
                          
                            CheckInput.entriesAvailable(NameTextField.getText(),AddressTextField.getText(),Address2TextField.getText(),CityTextField.getText(),postalCodeTextField.getText(),phoneTextField.getText(),countryTextField.getText());
                            Customer tempCustomer = new Customer(NameTextField.getText(),AddressTextField.getText(),Address2TextField.getText(),CityTextField.getText(),postalCodeTextField.getText(),phoneTextField.getText(),countryTextField.getText());
                            customerTable.getItems().add(tempCustomer);
           
                            new DataConnection().addNewCustomer(tempCustomer,username,timePoint,customers);

                            System.out.println(NameTextField.getText()+ "is registered");
                            customerStage.close();
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
                            CheckInput.entriesAvailable(NameTextField.getText(),AddressTextField.getText(),Address2TextField.getText(),CityTextField.getText(),postalCodeTextField.getText(),phoneTextField.getText(),countryTextField.getText());
                            
                            selectedCustomer.setCustomerName(NameTextField.getText());
                            selectedCustomer.setAddress(AddressTextField.getText());
                            selectedCustomer.setAddress2(Address2TextField.getText());
                            selectedCustomer.setCity(CityTextField.getText());
                            selectedCustomer.setPostalCode(postalCodeTextField.getText());
                            selectedCustomer.setPhone(phoneTextField.getText());
                            selectedCustomer.setCountry(countryTextField.getText());
                            new DataConnection().updateCustomer(selectedCustomer, username, timePoint);
                            
                            customerStage.close();   
                    }
                        catch (Exception ex1) {
                            
                            Alert a = new Alert(AlertType.ERROR);
                            a.setContentText(ex1.getMessage());
                            a.setTitle("Error");
                            a.showAndWait();
                      }   
                       } 
                    }
                );
                Scene addModCustomerScene = new Scene(customerPane, 500, 300);
                customerStage.setScene(addModCustomerScene);
                customerStage.showAndWait();
            }

        }  