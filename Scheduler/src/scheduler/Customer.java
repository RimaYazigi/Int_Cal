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

import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import static javafx.collections.FXCollections.observableArrayList;


public  class Customer {
    public static AtomicInteger itemSequence = new AtomicInteger(0);
    public final   IntegerProperty customerId =  new SimpleIntegerProperty(itemSequence.incrementAndGet());
       
        private final IntegerProperty active = new SimpleIntegerProperty() ;
        private final IntegerProperty addressId = new SimpleIntegerProperty() ;
        private final IntegerProperty cityId = new SimpleIntegerProperty() ;
        private final IntegerProperty countryId = new SimpleIntegerProperty();
        private final StringProperty customerName = new SimpleStringProperty() ;
        private final StringProperty phone = new SimpleStringProperty() ;
        private final StringProperty address = new SimpleStringProperty() ;
        private final StringProperty address2 = new SimpleStringProperty() ;
        private final StringProperty city = new SimpleStringProperty() ;
        private final StringProperty postalCode = new SimpleStringProperty();
        private final StringProperty country = new SimpleStringProperty() ;
        
        private static ObservableList<Customer> customers = observableArrayList();
        public static ObservableList<Customer> getCustomers() {
            return customers;
        }
        
        public final IntegerProperty customerIdProperty() {
            return this.customerId;
        }
        
            public final Integer getCustomerId() {
            return customerId.get();
        }
        public final void setCustomerId(final int customerId) {
            this.customerId.set(customerId);
        }
        //-----------------------------------------------
        public final IntegerProperty activeProperty() {
            return this.active;
        }
        public final Integer getActive() {
            return active.get();
        }
        public final void setActive(final int active) {
            this.active.set(active);
        }
        //------------------------------------------------

        public final IntegerProperty addressIdProperty() {
            return this.addressId;
        }
       
            public final Integer getAddressId() {
            return addressId.get();
        }
        public final void setAddressId(final int addressId) {
            this.addressId.set(addressId);
        }
        //-------------------------------------------------

        public final IntegerProperty cityIdProperty() {
            return this.cityId;
        }
       
            public final Integer getCityId() {
            return cityId.get();
        }
        public final void setCityId(final int cityId) {
            this.cityId.set(cityId);
        }
        //---------------------------------------------------

        public final IntegerProperty countryIdProperty() {
            return this.countryId;
        }
       
            public final Integer getCountryId() {
            return countryId.get();
        }
        public final void setCountryId(final int countryId) {
            this.countryId.set(countryId);
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
        //---------------------------------------------------------
        public final StringProperty phoneProperty() {
            return this.phone;
        }

        public final String getPhone() {
            return this.phoneProperty().get();
        }
        public final void setPhone(final String phone) {
            this.phone.set(phone);
        }
        //-------------------------------------------------------------
        public final StringProperty addressProperty() {
            return this.address;
        }

        public final String getAddress() {
            return this.addressProperty().get();
        }
        public final void setAddress(final String address) {
            this.address.set(address);
        }
        //-------------------------------------------------------------
        public final StringProperty address2Property() {
            return this.address2;
        }

        public final String getAddress2() {
            return this.address2Property().get();
        }
        public final void setAddress2(final String address2) {
            this.address2.set(address2);
        }
        //-------------------------------------------------------------
        public final StringProperty cityProperty() {
            return this.city;
        }

        public final String getCity() {
            return this.cityProperty().get();
        }
        public final void setCity(final String city) {
            this.city.set(city);
        }
        //-----------------------------------------------------------------
        
        public final StringProperty postalCodeProperty() {
            return this.postalCode;
        }

        public final String getPostalCode() {
            return this.postalCodeProperty().get();
        }
        public final void setPostalCode(final String postalCode) {
            this.postalCode.set(postalCode);
        }
        //-----------------------------------------------------------------
        public final StringProperty countryProperty() {
            return this.country;
        }

        public final String getCountry() {
            return this.countryProperty().get();
        }
        public final void setCountry(final String country) {
            this.country.set(country);
        }
        
        
        public Customer(){
        
        }
            public Customer(int customerId, int active, int addressId, int cityId, int countryId,String customerName,String phone, String address, String address2, String city, String postalCode, String country)
            {
                this.setCustomerId(customerId);
                this.setAddressId(addressId);
                this.setCityId(cityId);
                this.setCountryId(countryId);
                this.setCustomerName(customerName);
                this.setAddress(address);
                this.setAddress2(address2);
                this.setCity(city);
                this.setPostalCode(postalCode);
                this.setPhone(phone);
                this.setCountry(country);
            }  
            public Customer(int active, int addressId, int cityId, int countryId,String customerName,String phone, String address, String address2, String city, String postalCode, String country)
            {
                this.setActive(active);
                this.setAddressId(addressId);
                this.setCityId(cityId);
                this.setCountryId(countryId);
                this.setCustomerName(customerName);
                this.setPhone(phone);
                this.setAddress(address);
                this.setAddress2(address2);
                this.setCity(city);
                this.setPostalCode(postalCode);
                this.setCountry(country);
            }
            public Customer(String customerName, String address, String address2, String city, String postalCode, String phone,String country)
            {
                this.setCustomerName(customerName);                
                this.setAddress(address);
                this.setAddress2(address2);
                this.setCity(city);
                this.setPostalCode(postalCode);
                this.setPhone(phone);
                this.setCountry(country);
            }
            public static void addCustomer(Customer customer){
                customers.add(customer);
            }
            public String toString() {
                return this.getCustomerName() ;
             }
}