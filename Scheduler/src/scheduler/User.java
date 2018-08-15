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

import static javafx.collections.FXCollections.observableArrayList;
import java.time.Instant;
import javafx.collections.ObservableList;

public class User {
    
    private int userId;    
    private String userName;
    private String password;
    private boolean active;
    private String createBy;
    private Instant createDate;
    private Instant lastUpdate;
    private String lastUpdatedBy;
    private static ObservableList<User> users = observableArrayList();
    public static ObservableList<User> getUsers() {
        return users;
    }
    
    public User(int userId, String userName, String password, boolean active, String createBy, Instant createDate,
            Instant lastUpdate, String lastUpdatedBy) {
        
        super();
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createBy = createBy;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

   public int setUserId(int userID){
       return userId;
   }
   

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }   

    @Override
    public String toString(){
        return this.userName;
    }
}
