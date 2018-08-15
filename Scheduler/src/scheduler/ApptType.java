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
import javafx.beans.property.SimpleStringProperty;


public class ApptType {
    public final SimpleStringProperty description;
    public final SimpleStringProperty apptcount;
   
    public ApptType(String desc, String count) 
    {
        this.description = new SimpleStringProperty(desc);
        this.apptcount = new SimpleStringProperty(count);
       
    }
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String desc) {
        description.set(desc);
    }
    
    public String getApptcount() {
        return apptcount.get();
    }

    public void setApptcount(String count) {
        apptcount.set(count);
}
}

