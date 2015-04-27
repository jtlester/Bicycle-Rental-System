// specify the package
package model;

// system imports
import java.sql.*;
import java.util.*;
import javax.swing.*;

// project imports
//import exception.InvalidPrimaryKeyException;
import database.*;

//GUI Imports
import impresario.IView;

public class Bicycle extends EntityBase implements IView {
        
    private static final String myTableName = "Bicycle";
    protected Properties persistentState;
    protected Properties dependencies;
    Properties bikeInfo;
    public String make, model, color,bikeCondition, serialNumber, locationOnCampus, description, status;

    
    // GUI Components
    private String updateStatusMessage = "";

    //Initilize Bicycle
    public Bicycle(Properties props) {

        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true) {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }

    }

    //Takes an array of strings to insert bicycle into Database
    public void addBicycle(String[] Values) {
        String insertStatusMessage;
        try {
            if (persistentState.getProperty("bikeId") != null) {
                insertPersistentState(mySchema, persistentState);
                insertStatusMessage = "Account data for account number : " + persistentState.getProperty("bikeId") + " Inserted successfully!";
            
            }
        }   
        catch (SQLException e) {
            updateStatusMessage = "Error in Adding Bicycle Data To Database!";
        }
            
     }

     protected void initializeSchema(String tableName) {
         if (mySchema == null) {
             mySchema = getSchemaInfo(tableName);
         }
     }

    public void update() {
        updateStateInDatabase();
    }
      
    private void updateStateInDatabase() {

        try {
            if (persistentState.getProperty("bikeId") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("bikeId", persistentState.getProperty("bikeId"));
                persistentState.remove("bikeId");
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Account data for account number : " + persistentState.getProperty("bikeId") + " updated successfully in database!";
            
            } else {
                Integer bikeId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("bikeId", "" + bikeId.intValue());
                updateStatusMessage = "Bicycle data for new bicycle: " +  persistentState.getProperty("bikeId")
                    + "installed successfully in database!";
                  System.out.println("TEST OTHER");  
                    System.out.println(updateStatusMessage);
            } 
        }
        catch (SQLException ex) {
            updateStatusMessage = "Error in installing bicycle data in database!";
            System.out.println(ex.getMessage());

        }
           
            
        
    }
    
    //-------------------------------------------------------------------
    public void getBikeInfo(Properties props)
    {
        String authQuery = "SELECT * FROM `" + myTableName + "` WHERE (`bikeId` = '" + props.getProperty("bikeId") + "');";
        Vector allDataRetrieved = getSelectQueryResult(authQuery);
        int size = allDataRetrieved.size();
        if(size == 1)
        {
            //System.out.println("IN GET BIKE INFO-------------------");
            Properties retrievedBikeData = (Properties)allDataRetrieved.elementAt(0);
            bikeInfo = new Properties();
            
            Enumeration allKeys = retrievedBikeData.propertyNames();
            while(allKeys.hasMoreElements() == true)
            {
                String nextKey = (String)allKeys.nextElement();
                String nextValue = retrievedBikeData.getProperty(nextKey);
                
                if(nextValue != null)
                {
                    bikeInfo.setProperty(nextKey, nextValue);
                }
            }
            
            make = bikeInfo.getProperty("make");
            model = bikeInfo.getProperty("model");
            color = bikeInfo.getProperty("color");
            bikeCondition = bikeInfo.getProperty("bikeCondition");
            serialNumber = bikeInfo.getProperty("serialNumber");
            locationOnCampus = bikeInfo.getProperty("locationOnCampus");
            description = bikeInfo.getProperty("description");
            status = bikeInfo.getProperty("status");
        }
        else
        {
            System.out.println("No Bike Found");
        }
        
    }
    //GETTERS FOR BIKE INFO
    public String getMake()
    {
        return make;
    }
    public String getModel()
    {
        return model;
    }
    public String getColor()
    {
        return color;
    }
    public String getCondition()
    {
        return bikeCondition;
     }
    public String getSerial()
    {
        return serialNumber;
    }
    public String getLocation()
    {
        return locationOnCampus;
    }
    public String getDescription()
    {
        return description;
    }
    public String getstatus()
    {
        return status;
    }

//-----------------------------------------------------------------------

    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true) {
            return updateStatusMessage;
        }

        return persistentState.getProperty(key);
    }

    public void save() {
        try {
            if(persistentState.getProperty("bikeId") != null) {
                update();
            }
        }
        catch(Exception ex) {
            System.out.println("Error in save: Unable to insert or update.");
        }
    }

    public void stateChangeRequest(String key, Object value) {
        myRegistry.updateSubscribers(key, this);
    }

    private void setDependencies() {   
        myRegistry.setDependencies(persistentState);
    }

}
