
package scheduler;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Scheduler extends Application {
	
    
   
    private static String username;
    private static String password;
        static final Logger logger = Logger.getLogger(Scheduler.class.getName());  

    FileHandler fh;  
    static ResourceBundle rb;
    

    static Label emptyusername;
    Label emptypassword;

	public void start(Stage primaryStage) throws Exception  {
	    try{ 
                            

	    fh = new FileHandler("src/MyLogFile.txt", true);  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  

        } catch (SecurityException | IOException e) {  
        e.printStackTrace();  
        }  


	    Locale currentLocale = Locale.getDefault();
	    ResourceBundle rb = ResourceBundle.getBundle("LogIn", currentLocale);
	    System.out.println(Locale.getDefault());
            primaryStage.setTitle(rb.getString("Title"));
            GridPane grid = new GridPane();
            Scene scene = new Scene(grid, 620, 460);
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            Text scenetitle = new Text(rb.getString("Title"));
            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(scenetitle, 0, 0, 2, 1);
            
            System.out.println(ZoneId.systemDefault().getDisplayName(TextStyle.SHORT,Locale.ENGLISH));
            Label lblocation = new Label("your are in "+ ZoneId.systemDefault().getDisplayName(TextStyle.SHORT,Locale.ENGLISH) + " timezone");

            Label userName = new Label(rb.getString("userName"));
            grid.add(userName, 0, 1);

            TextField userNameTextField = new TextField("test");
            grid.add(userNameTextField, 1, 1);

            Label pw = new Label(rb.getString("pw"));
            grid.add(pw, 0, 2);

            PasswordField passwordTextField = new PasswordField();
            passwordTextField.setText("test");
            grid.add(passwordTextField, 1, 2);

            Button logBtn = new Button(rb.getString("logBtnlbl"));
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_LEFT);
            hbBtn.getChildren().add(logBtn);
            grid.add(hbBtn, 0, 4);
            Label lblwait = new Label();
            HBox hwait = new HBox(5);
            Button regBtn = new Button("Register");
            regBtn.setDisable(true);
            HBox hbBtn2 = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(regBtn);
            
            HBox hBox2 = new HBox(lblocation);
            
            grid.add(hbBtn2, 0, 5);
            grid.add(hBox2, 3, 1);

            username = userNameTextField.getText();
            password = passwordTextField.getText();

            System.out.println(Locale.getDefault());

            logBtn.setOnAction(e->{  
                
                hwait.getChildren().add(lblwait);
                grid.add(lblwait, 3, 7);
                username = userNameTextField.getText();
                password = passwordTextField.getText();
                if(username.isEmpty() || password.isEmpty()){
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setHeaderText(rb.getString("headertext"));
                alert2.setContentText(rb.getString("contextempty"));
                alert2.showAndWait();
              }else{
                boolean exists=false;
                exists= DataConnection.login(username, password);
                
                if(exists==true){
                    logger.log(Level.INFO, "{0} logged in", username);
                    userNameTextField.clear();
                    passwordTextField.clear();
                    new MainScreen(primaryStage, username, fh).alarm(username);
                    
                    primaryStage.close();
                }
                    else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(rb.getString("headertext"));
                    alert.setContentText(rb.getString("contenttext"));
                    alert.showAndWait();
                    }              
              }
        });
            primaryStage.setScene(scene);
            primaryStage.show();
	}

	public static boolean isInputValid(){
        

        if(username.isEmpty() || password.isEmpty()){
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setHeaderText(rb.getString("headertext"));
            alert2.setContentText(rb.getString("contextempty"));
            alert2.showAndWait();
        return false;
        }else{
        return true;
        }
	}

       public FileHandler gethandler(){
           return fh;
       }
                
                public static void main(String args[]){

                    System.out.println(Locale.getDefault());
                    //====================to switch to french==================
//                    Locale.setDefault(Locale.FRANCE);
                    //=========================================================
                    launch(args);
                }
	}
  


 
