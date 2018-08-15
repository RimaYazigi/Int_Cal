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

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.time.*;

public class DayCell extends VBox {

    private LocalDate date;

    boolean valid;
    int day, month, year;
    String text;
    Label label;
    int column;
    int row;
    public DayCell(){
        valid = false;
    }
 
    public boolean isValid() {
        return valid;
    }

    private void setDay(int day) {
        this.day = day;
    }
    
    private void setMonth(int month) {
        this.month = month;
    }
    
    private void setYear(int year) {
        this.year = year;
    }
    
    public int getDay() {
        return day;
    }
    
    public int getMonth() {
        return month;
    }
    
    public int getYear() {
        return year;
    }
    public String toString() {
        return (getMonth()+1) + "/" + getDay() + "/" + getYear();
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public final int getColumn() {
        return column;
    }
    public final void setColumn(int column) {
        this.column = column;
    }
    public final int getRow() {
        return row;
    }
    public final void setRow(int row) {
        this.row = row;
    }
    boolean occuresOn(int day, int month, int year){
        if( (this.day == day) && (this.month == month) && (this.year == year))
            return true;

        return false;
    }
}