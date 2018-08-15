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

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CheckInput {


public static boolean entriesAvailable(String customerName, String address, String address2, String city, String postalCode, String phone,String country) throws Exception{
boolean entriesAvailable = false;    

    if (customerName.equals("")  || address.equals("") || city.equals("")
            || postalCode.equals("")|| phone.equals("")|| country.equals("")) {

        throw new Exception("please enter data in fields");
       
    } else {
        entriesAvailable = true;
    }
    return entriesAvailable;  
}

public static boolean OnWeekend(ZonedDateTime startDateTime) throws Exception {
boolean OnWeekend = false;
    if (startDateTime.toLocalDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) || startDateTime.toLocalDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
        throw new Exception("Cannot set appointments on weekends");
        
     } else {
         OnWeekend = true;
     }
     return OnWeekend;  
    }

public static boolean isOverlapping(int day, int month, int year, LocalTime startTime , LocalTime endTime) throws Exception{
    boolean isOverlapping = false;
    ObservableList<Appointment> aps = DataConnection.getAppointments().stream()
            .filter(p-> p.occuresOn(day, month, year))
            .collect(Collectors.toCollection(FXCollections::observableArrayList));

            for (Appointment p: aps){
                if (endTime.isAfter(p.getStart().toLocalTime()) && endTime.isBefore(p.getEnd().toLocalTime()) 
                    || startTime.isAfter(p.getStart().toLocalTime()) && startTime.isBefore(p.getEnd().toLocalTime())
                    || startTime == (p.getStart().toLocalTime()) || endTime == (p.getEnd().toLocalTime()) 
                    || startTime.isAfter(p.getStart().toLocalTime()) && endTime.isBefore(p.getEnd().toLocalTime())){
                throw new Exception("appointment is overlapping");
                }else{
                    isOverlapping = true;
                }
            }
            return isOverlapping;

}
}




