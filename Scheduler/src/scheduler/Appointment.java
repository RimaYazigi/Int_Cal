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
import java.time.ZonedDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;


public class Appointment {
        private final IntegerProperty appointmentId = new SimpleIntegerProperty() ;
        private final IntegerProperty customerId = new SimpleIntegerProperty() ;
        private final StringProperty title = new SimpleStringProperty() ;
        private final StringProperty description = new SimpleStringProperty() ;
        private final StringProperty location = new SimpleStringProperty() ;
        private final StringProperty contact = new SimpleStringProperty() ;
        private final StringProperty url = new SimpleStringProperty() ;
        private final ObjectProperty<ZonedDateTime> start = new SimpleObjectProperty<>();
        private final ObjectProperty<ZonedDateTime> end = new SimpleObjectProperty<>();
        private final StringProperty createdBy = new SimpleStringProperty() ;
       private final StringProperty customerName= new SimpleStringProperty();

        private static ObservableList<Appointment> appointments = observableArrayList();
        public static ObservableList<Appointment> getAppointments() {
            return appointments;
        }       
        //--------------------------------
        public final IntegerProperty appointmentIdProperty() {
            return this.appointmentId;
        }
       
            public final Integer getAppointmentId() {
            return appointmentId.get();
        }
        public final void setAppointmentId(final int appointmentId) {
            this.appointmentId.set(appointmentId);
        }
        //----------------------------------------
        public final IntegerProperty customerIdProperty() {
            return this.customerId;
        }
       
            public final Integer getCustomerId() {
            return customerId.get();
        }
        public final void setCustomerId(final int customerId) {
            this.customerId.set(customerId);
        }
        //----------------------------------------------------------
        public final StringProperty titleProperty() {
            return this.title;
        }

        public final String getTitle() {
            return this.titleProperty().get();
        }
        public final void setTitle(final String title) {
            this.title.set(title);
        }
       
        //------------------------------------------------------
        public final StringProperty descriptionProperty() {
            return this.description;
        }

        public final String getDescription() {
            return this.descriptionProperty().get();
        }
        public final void setDescription(final String description) {
            this.description.set(description);
        }
      //-------------------------------------------------------------
        public final StringProperty locationProperty() {
            return this.location;
        }

        public final String getLocation() {
            return this.locationProperty().get();
        }
        public final void setLocation(final String location) {
            this.location.set(location);
        }
        //-------------------------------------------------------------
        public final StringProperty contactProperty() {
            return this.contact;
        }

        public final String getContact() {
            return this.contactProperty().get();
        }
        public final void setContact(final String contact) {
            this.contact.set(contact);
        }
        //-------------------------------------------------------------
        public final StringProperty urlProperty() {
            return this.url;
        }

        public final String getUrl() {
            return this.urlProperty().get();
        }
        public final void setUrl(final String url) {
            this.url.set(url);
        }
        //-------------------------------------------------------------
        public ZonedDateTime getStart() {
            return start.get();
        }

        public void setStart(ZonedDateTime value) {
            start.set(value);
        }
       
        public ObjectProperty<ZonedDateTime> startProperty() {
            return this.start;
        }
      //-------------------------------------------------------------
        public ZonedDateTime getEnd() {
            return end.get();
        }

        public void setEnd(ZonedDateTime value) {
            end.set(value);
        }

        public ObjectProperty<ZonedDateTime> endProperty() {
            return end;
        }
        //--------------------------------------------------------
        public final StringProperty customerNameProperty() {
            return this.customerName;
        }

        public final String getCustomerName() {
            return this.customerNameProperty().get();
        }
        public final void setCustomerName(final String customerName) {
            this.customerName.set(customerName);
        }
        //-------------------------------------------------------------
        public final StringProperty createdByProperty() {
            return this.createdBy;
        }

        public final String getCreatedBy() {
            return this.createdByProperty().get();
        }
        public final void setCreatedBy(final String createdBy) {
            this.createdBy.set(createdBy);
        }
//        ----------------------------------------------------------------
        public Appointment(){}
        public Appointment(int appointmentId, int customerId, String title, String description, String location,
                String contact, String url, ZonedDateTime start, ZonedDateTime end) {
            
            this.setAppointmentId(appointmentId);
            this.setCustomerId(customerId);
            this.setTitle(title);
            this.setDescription(description);
            this.setLocation(location);
            this.setContact(contact);
            this.setUrl(url);
            this.setStart(start);
            this.setEnd(end);
        }
        public Appointment(String customerName, String title, String description, String location,
                String contact, String url, ZonedDateTime start, ZonedDateTime end, String createdBy) {
            this.setCustomerName(customerName);
            
            this.setTitle(title);
            this.setDescription(description);
            this.setLocation(location);
            this.setContact(contact);
            this.setUrl(url);
            this.setStart(start);
            this.setEnd(end);
            this.setCreatedBy(createdBy);
        }
        public static void addAppointment(Appointment appointment){
            appointments.add(appointment);
        }

        boolean occuresOn(int day, int month, int year){
            if( (this.getStart().getDayOfMonth() == day) && (this.getStart().getMonthValue() == month) && (this.getStart().getYear() == year))
                return true;

            return false;
        }
        @Override
        public String toString() {
           return this.getCustomerName() + " @ " + this.getStart().toLocalDateTime().getHour()+":"+this.getStart().toLocalDateTime().getMinute();
        }

}
      
        