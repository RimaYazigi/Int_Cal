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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Statement;
import java.sql.Timestamp;


public class DataConnection {
    String db;
    String url;
    String username;
    String pass;
    Connection conn;

    static Statement statement;
    private static ObservableList<Customer> customers;
    private static ObservableList<User> users;
    private static ObservableList<Appointment> appointments, aps;
    int idcountry,idcity,idaddress,idcustomer, custId, idappointment;
    
public DataConnection() {

        // Load the JDBC driver
          String driver = "com.mysql.jdbc.Driver";
          String db = "U005rH";
          String url = "jdbc:mysql://52.206.157.109/" + db;
          String username = "U005rH";
          String password = "53687114584";
          try {
              Class.forName(driver);
              conn = DriverManager.getConnection(url, username, password);
//        System.out.println("Database connected");
        statement = conn.createStatement();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
}

public static void populate(String username) throws Exception{
   
        java.sql.Statement stmt = null;
        try {
            stmt =  new DataConnection().conn.createStatement(); 
            String query= "insert into user(userId,userName,password,active,createDate,createBy, lastUpdate, lastUpdatedBy) "
                    + "values (2,'guest','guest','2','" + getTimestamp() + "', 'guest' , '"+  getTimestamp() +  "', 'guest')";        

//                    + "values (1,'test','test','1','" + getTimestamp() + "', '" + "'test'" + "', '"+  getTimestamp() +  "', '" + "'test'" + "')";        
            stmt.executeUpdate(query);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }


@SuppressWarnings("null")
    public static boolean login(String username, String password)  {
    
        boolean exists=false;
        Connection conn = null ;
       
      String query = String.format("SELECT * FROM user WHERE userName='%s' and password='%s';", username, password);
        try {            
            try {
                 new DataConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResultSet r =  statement.executeQuery(query);
            if (r.next ()) {
                
                String userName= r.getString(1);
                exists=true;
                System.out.println(userName +" is successfully logged.");
            }
            else{
 
                exists=false;
            }
        } catch (SQLException e) {
            System.err.println("Error registering user, system exiting.");
            System.err.println(e.getMessage());
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, e);

            System.exit(0);     
        }
        return exists;
}

public static ObservableList<Customer> getCustomers(){
    try{
        customers = FXCollections.observableArrayList();
        String query = ("Select  * from customer "+
                " Inner Join address ON customer.addressId = address.addressId" +
                " Inner Join city ON address.cityId = city.cityId" +
                " Inner Join country ON city.countryId = country.countryId");
        try (ResultSet rs = statement.executeQuery(query)) {
            if(rs != null)
                while (rs.next()) {
                    
                    Customer customer = new Customer();
                    customer.setCustomerId(rs.getInt("customerId"));
                    customer.setCustomerName(rs.getString("customerName"));
                    customer.setAddress(rs.getString("address"));
                    customer.setAddress2(rs.getString("address2"));
                    customer.setCity(rs.getString("city"));
                    customer.setPostalCode(rs.getString("postalCode"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setCountry(rs.getString("country"));
                    
                    customers.add(customer);
                }
        }
    }
    catch(Exception e){
        e.printStackTrace();
    }
    
    return customers;
    
}
//---------------

public static ObservableList<User> getUsers(){
  try{
      users = FXCollections.observableArrayList();
      String query = ("Select  * from user");
      try (ResultSet rs = statement.executeQuery(query)) {
          if(rs != null)
              while (rs.next()) {
                  
                  User user = new User();
                  user.setUserId(rs.getInt("userId"));
                  user.setUserName(rs.getString("userName"));
                  user.setPassword(rs.getString("password"));
                  
                  users.add(user);
              }
      }
  }
  catch(Exception e){
      e.printStackTrace();
  }
  
  return users;
  
}

//---------------
public static ObservableList<Appointment> getApptsperDay(int day, int month, int year){
    try{
    aps = FXCollections.observableArrayList();
    String query = ("Select * from appointment USE INDEX (start)" +
            " Inner Join customer ON appointment.customerId = customer.customerId"+
            " Inner Join address ON customer.addressId = address.addressId" +
            " Inner Join city ON address.cityId = city.cityId" +
            " Inner Join country ON city.countryId = country.countryId") +
            " WHERE DAY(start) = " + day + " && MONTH(start) = " + month + 
            " && YEAR(start) = " + year ;

    ResultSet rs = statement.executeQuery(query);
    if(rs != null)
        while (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(rs.getInt("appointmentId"));
            appointment.setCustomerId(rs.getInt("customerId"));
            appointment.setCustomerName(rs.getString("customer.customerName"));
            appointment.setTitle(rs.getString("title"));
            appointment.setDescription(rs.getString("description"));
            appointment.setLocation(rs.getString("location"));
            appointment.setContact(rs.getString("contact"));
            appointment.setUrl(rs.getString("url"));
            appointment.setStart(ZonedDateTimeConverter.convertToEntityAttribute(rs.getTimestamp("start")));
            appointment.setEnd(ZonedDateTimeConverter.convertToEntityAttribute(rs.getTimestamp("end")));
            appointment.setCreatedBy(rs.getString("createdBy"));
            aps.add(appointment);
            }
    }
    catch(Exception e){
        e.printStackTrace();
    }
    return aps;
    
  }

//====================
public static ObservableList<Appointment> getAppointments(){

  try{
      appointments = FXCollections.observableArrayList();
      String query = ("Select  * from appointment USE INDEX (start)"+
              " Inner Join customer ON appointment.customerId = customer.customerId"+
              " Inner Join address ON customer.addressId = address.addressId" +
              " Inner Join city ON address.cityId = city.cityId" +
              " Inner Join country ON city.countryId = country.countryId");

      ResultSet rs = statement.executeQuery(query);
      if(rs != null)
          while (rs.next()) {
              Appointment appointment = new Appointment();
              appointment.setAppointmentId(rs.getInt("appointmentId"));
              appointment.setCustomerId(rs.getInt("customerId"));
              appointment.setCustomerName(rs.getString("customer.customerName"));
              appointment.setTitle(rs.getString("title"));
              appointment.setDescription(rs.getString("description"));
              appointment.setLocation(rs.getString("location"));
              appointment.setContact(rs.getString("contact"));
              appointment.setUrl(rs.getString("url"));
              appointment.setStart(ZonedDateTimeConverter.convertToEntityAttribute(rs.getTimestamp("start")));
              appointment.setEnd(ZonedDateTimeConverter.convertToEntityAttribute(rs.getTimestamp("end")));
              appointment.setCreatedBy(rs.getString("createdBy"));

              appointments.add(appointment);
          } 
  }
  catch(Exception e){
      e.printStackTrace();
  }
  
  return appointments;
  
}


//=============================================================
public ResultSet getMonthlyapptbytype(int id) throws Exception
{
    String query = "select description, count(appointmentId) as 'totalappts' from appointment "
            + "where MONTH(start) =" +id+ " group by description";
    System.out.println(query);
    ResultSet rs = statement.executeQuery(query);
    
    return rs;
}

public ArrayList<String[]> listForMonthlyapptbytype(ResultSet rs) throws Exception
{
    ArrayList<String[]> l = new ArrayList<>();
    
    while (rs.next())
    {
        String[] arr = new String[2]; //2 = number of columns
        String description = rs.getString("description");
        arr[0] = description;
        int totalappts = rs.getInt("totalappts");
        String totalapptsString = Integer.toString(totalappts);
        arr[1] = totalapptsString;
        
        l.add(arr);
    }
    return l;
}
//==========================================================================
    public void addNewAppointment(Appointment tempAppointment, String user, ZonedDateTime timePoint, ObservableList<Appointment> appointments) throws Exception
    {
        statement = new DataConnection().conn.createStatement(); 

        String query = "select customerId from customer where customerName = '" + tempAppointment.getCustomerName()+"'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            custId =rs.getInt("customerId");
        }
        //------------------------------------
        String query1 ="select appointmentId from appointment where customerId = '"+custId+"'";
        ResultSet rs1 = statement.executeQuery(query1);
        
        while (rs1.next()) {
            idappointment= rs1.getInt("appointmentId");
            
            System.out.println(rs1 .getInt(1));
        }   
         
            rs1 = statement.executeQuery("Select MAX(appointmentId) + 1 AS nextId from appointment");
            rs1.next();
            idappointment = rs1.getInt("nextId") ;
            System.out.println(idappointment);
        
        //----------------------------------------
        String query2="INSERT INTO appointment(appointmentId, customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES('" 
                + idappointment + "','"
                + custId + "', '"
                + tempAppointment.getTitle() +  "', '"
                + tempAppointment.getDescription() +  "', '"
                + tempAppointment.getLocation() +  "', '"
                + tempAppointment.getContact() +  "', '"
                + tempAppointment.getUrl() +  "', '"
                + ZonedDateTimeConverter.convertToDatabaseColumn(tempAppointment.getStart()) +  "', '"
                + ZonedDateTimeConverter.convertToDatabaseColumn(tempAppointment.getEnd()) +  "', '"
                + getTimestamp() +  "', '"
                + user +  "', '"
                + getTimestamp() +  "', '"
                + user + "')";
        statement.executeUpdate(query2);
    }

    public void updateAppointment(Appointment selectedAppointment, String user, ZonedDateTime timePoint, ObservableList<Appointment> appointments)  
    {       
        String queryApp = ("UPDATE appointment" +
                " Inner Join customer ON appointment.customerId = customer.customerId" +
                " SET appointment.customerId = '" +selectedAppointment.getCustomerId()+ "'" +
                ", title = '" +selectedAppointment.getTitle()+ "'" +
                ", description = '" +selectedAppointment.getDescription()+ "'" +
                ", location = '" +selectedAppointment.getLocation()+ "'" +
                ", contact = '" +selectedAppointment.getContact()+ "'" +
                ", url = '" + selectedAppointment.getUrl()+ "'" +
                ", start = '"+ ZonedDateTimeConverter.convertToDatabaseColumn(selectedAppointment.getStart()) + "'" +
                ", end = '"+ ZonedDateTimeConverter.convertToDatabaseColumn(selectedAppointment.getEnd()) + "'" +
                ", appointment.lastUpdate ='" + getTimestamp() + "'" +
                ", appointment.lastUpdateBy ='"+ user + "'" +                                     
                " WHERE appointmentId = '" + selectedAppointment.getAppointmentId() + "'");

        try {
        
         int result = statement.executeUpdate(queryApp);
         if (result >0){
             System.out.println("---record updated---");
         }else{
             System.out.println("record not updated");
             }
        }catch (SQLException e){
            e.printStackTrace();
         }
    }  
//   
    public void deleteAppointment(Appointment selectedAppointment, String username) {
    
        String query = "delete from appointment where appointmentId = " +selectedAppointment.getAppointmentId();
       
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }               
    public void addNewCustomer(Customer tempCustomer, String user, ZonedDateTime timePoint, ObservableList<Customer> customers) throws Exception
    {           
        String query ="select countryId from country where country = '"+tempCustomer.getCountry()+"'";
        ResultSet rs = statement.executeQuery(query);
        
        while (rs.next()) {
            idcountry= rs.getInt("countryId");
            
            System.out.println(rs.getInt(1));
        }   
            rs = statement.executeQuery("Select MAX(countryId) + 1 AS nextId from country");
            rs.next();
            idcountry = rs.getInt("nextId") ;
            System.out.println(idcountry);

         query = "insert into country (countryId,country, createDate,createdBy, lastUpdate, lastUpdateBy ) values ('"+idcountry+"','"+tempCustomer.getCountry()+"','"+getTimestamp()+"', '"+user+"', '"+getTimestamp()+"', '"+user+ "');";        
        int result = statement.executeUpdate(query);
        if (result == 1) {
            System.out.println("--record created--");
        }else{
            System.err.println("!! record not created !!");
        }
        //--------------------------------------------------------------------------------
       
        query = "select cityId from city where city = '"+tempCustomer.getCity()+"'";
        ResultSet rs1 = statement.executeQuery(query);

        while (rs1.next()) {
            idcity= rs1.getInt("cityId");
        } 
            rs1 = statement.executeQuery("Select MAX(cityId) + 1 AS nextId from city");
            rs1.next();
            idcity = rs1.getInt("nextId");
        
        //------------------------------------
        query ="insert into city (cityId, city, countryId,createDate,createdBy, lastUpdate, lastUpdateBy) values( '"+idcity+"','"+ tempCustomer.getCity()+"','"+idcountry+"','"+getTimestamp()+"', '"+user+"', '"+getTimestamp()+"', '"+user+ "');";
        statement.executeUpdate(query);
        //----------------------------------------
       
        query = "select addressId from address where address = '"+tempCustomer.getAddress()+"'";
        ResultSet rs2 = statement.executeQuery(query);

        while(rs2.next()) {
            idaddress= rs2.getInt("addressId");
        } 
            rs2 = statement.executeQuery("Select MAX(addressId) + 1 AS nextId from address");
            rs2.next();
            idaddress = rs2.getInt("nextId");
        
        //--------------------------------------------
        query ="insert into address (addressId,address,address2,cityId,postalCode,phone,createDate,createdBy, lastUpdate, lastUpdateBy) values('"+idaddress+"','"+tempCustomer.getAddress()+"','"+tempCustomer.getAddress2()+"','"+idcity+"','"+tempCustomer.getPostalCode()+"','"+tempCustomer.getPhone()+"','"+getTimestamp()+"', '"+user+"', '"+getTimestamp()+"', '"+user+ "');";
        statement.executeUpdate(query);
        //---------------------------------
        query = "select customerId from customer where customerName = '"+tempCustomer.getCustomerName()+"'";
        ResultSet rs3 = statement.executeQuery(query);
        while(rs3.next()) {
            idcustomer= rs3.getInt("customerId");
        } 
            rs3 = statement.executeQuery("Select MAX(customerId) + 1 AS nextId from customer");
            rs3.next();
            idcustomer = rs3.getInt("nextId");
        
        //--------------------------------------------------------------
        query = "insert into customer (customerId,customerName,addressId,active,createDate,createdBy, lastUpdate, lastUpdateBy) values ('"+idcustomer+"','"+tempCustomer.getCustomerName()+"','"+idaddress+"','"+tempCustomer.getActive()+"','"+getTimestamp()+"', '"+user+"', '"+getTimestamp()+"', '"+user+ "');";
        statement.executeUpdate(query);
    }  
    
public void updateCustomer(Customer selectedCustomer, String user, ZonedDateTime timePoint) 
{       
    String query = ("UPDATE customer" +
            " Inner Join address ON customer.addressId = address.addressId" +
            " Inner Join city ON address.cityId = city.cityId" +
            " Inner Join country ON city.countryId = country.countryId" +
            " SET customerName = '" +selectedCustomer.getCustomerName()+ "'" +
            ", address = '" +selectedCustomer.getAddress()+ "'" +
            ", address2 = '" +selectedCustomer.getAddress2()+ "'" +
            ", city = '" +selectedCustomer.getCity()+ "'" +
            ", country = '" +selectedCustomer.getCountry()+ "'" +
            ", postalCode = '" +selectedCustomer.getPostalCode()+ "'" +
            ", phone = '" +selectedCustomer.getPhone()+ "'" +
            ", customer.lastUpdateBy = '" +user+"'" +
            ", address.lastUpdateBy = '" +user+ "'" +
            ", city.lastUpdateBy = '" +user+ "'" +
            ", country.lastUpdateBy = '" +user+ "'" +
            " WHERE customerId = " +selectedCustomer.getCustomerId()+ ";");
    
    try {
    
     int result = statement.executeUpdate(query);
     if (result >0){
         System.out.println("---record updated---");
     }else{
         System.out.println("record not updated");
         }
    }catch (SQLException e){
        e.printStackTrace();
     }
}
public void deleteCustomer(Customer selectedCustomer, String username) {

    String query = "delete from customer where customer.customerId="+selectedCustomer.getCustomerId();
   
    try {
        statement.execute(query);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
 
public static class ZonedDateTimeConverter  {
    static ZoneId utcZoneId = ZoneId.of("UTC");
    static ZoneId defaultZoneId = ZoneId.systemDefault();

    public static Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        return (zonedDateTime == null ? null :
        Timestamp.valueOf(zonedDateTime.withZoneSameInstant(utcZoneId).toLocalDateTime()));
    }

    public static ZonedDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        return (sqlTimestamp == null ? null :
        toDefaultZoneId(sqlTimestamp.toLocalDateTime().atZone(ZoneId.of("UTC"))));

    }


    private static ZonedDateTime toDefaultZoneId(ZonedDateTime zonedDateTime){
        return zonedDateTime.withZoneSameInstant(defaultZoneId);
    }

}

public static java.sql.Timestamp getTimestamp(){
    java.util.Date today = new java.util.Date();
    return new java.sql.Timestamp(today.getTime());
}
}