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
                whereClause.setProperty("bikeId",
                persistentState.getProperty("bikeId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Account data for account number : " + persistentState.getProperty("bikeId") + " updated successfully in database!";
            } else {
                Integer bikeId =
                    insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("bikeId", "" + bikeId.intValue());
                updateStatusMessage = "Bicycle data for new bicycle: " +  persistentState.getProperty("bikeId")
                    + "installed successfully in database!";
                    
                    System.out.println(updateStatusMessage);
            }
        }
        catch (SQLException ex) {
            updateStatusMessage = "Error in installing bicycle data in database!";
            System.out.println(ex.getMessage());

        }
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
