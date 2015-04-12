// specify the package
package model;

// system imports
import java.sql.*;
import java.util.*;

//GUI Imports
import impresario.IView;

public class Login extends EntityBase implements IView {
        
    private static final String myTableName = "Login";
    protected Properties dependencies;
    
    private String updateStatusMessage = "";

    //Initilize Login
    public Login(Properties props) {
        super(myTableName);

        //setDependencies();
        //persistentState = new Properties();

        // Enumeration allKeys = props.propertyNames();
        // while (allKeys.hasMoreElements() == true) {
        //     String nextKey = (String)allKeys.nextElement();
        //     String nextValue = props.getProperty(nextKey);

        //     if (nextValue != null) {
        //         persistentState.setProperty(nextKey, nextValue);
        //     }
        // }
    }

    private void setDependencies() {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }
    
    protected void initializeSchema(String tableName) {
         if (mySchema == null) {
             //mySchema = getSchemaInfo(tableName);
         }
    }

    public void stateChangeRequest(String key, Object value) {
        myRegistry.updateSubscribers(key, this);
    }

    public void update() {

    try {
        if (persistentState.getProperty("userId") != null) {
                            Properties whereClause = new Properties();
                            whereClause.setProperty("userId",
                            persistentState.getProperty("userId"));
                            updatePersistentState(mySchema, persistentState, whereClause);
                            updateStatusMessage = "Login data for userId : " + persistentState.getProperty("userId") + " updated successfully in database!";
                    } else {
                        Integer userId =  insertAutoIncrementalPersistentState(mySchema, persistentState);
                        persistentState.setProperty("userId", "" + userId.intValue());

                        updateStatusMessage = "Inserted user "+persistentState.getProperty("userId");
                    }
            }
            catch (SQLException ex) {
                    updateStatusMessage = "Error in installing user data in database!";
            }
            System.out.println(updateStatusMessage);
	}
	
	public Object getState(String key) {
		if (key.equals("UpdateStatusMessage") == true) {
			return updateStatusMessage;
        }
		return persistentState.getProperty(key);
	}

    public boolean authenticationIsSuccessful() {
        return true;
    }
	
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}
}
