package model;

import impresario.IView;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

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
                System.out.println(insertStatusMessage);
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

    public boolean update() {
    	try {
            if (persistentState.getProperty("bikeId") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("bikeId",
                persistentState.getProperty("bikeId"));
                updatePersistentState(mySchema, persistentState, whereClause);
            } else {
                Integer bikeId =
                    insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("bikeId", "" + bikeId.intValue());
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error in installing bicycle data in database!";
			System.out.println(ex);
            return false;
        }
    	return true;
    }
	
    public boolean updateBicycleInfo() {
		try {
			Properties whereClause = new Properties();
			whereClause.setProperty("bikeId", persistentState.getProperty("bikeId"));
			updatePersistentState(mySchema, persistentState, whereClause);
		} catch(SQLException ex) {
			return false;
		}
		return true;
	}
    
	public boolean changeStatus() {
		try {
			Properties whereClause = new Properties();
			whereClause.setProperty("bikeId", persistentState.getProperty("bikeId"));
			updatePersistentState(mySchema, persistentState, whereClause);
			return true;
		} catch(SQLException ex) {
			return false;
		}
	}
	
	public Properties getBikeInfo(String bikeId) {
		String authQuery = "SELECT * FROM `" + myTableName + "` WHERE (`bikeId` = '" + bikeId + "');";
		Vector allDataRetrieved = getSelectQueryResult(authQuery);
		int size = allDataRetrieved.size();
		if(size == 1) {
			Properties retrievedBikeData = (Properties)allDataRetrieved.elementAt(0);			
			Enumeration allKeys = retrievedBikeData.propertyNames();
			while(allKeys.hasMoreElements() == true) {
				String nextKey = (String)allKeys.nextElement();
				String nextValue = retrievedBikeData.getProperty(nextKey);
				if(nextValue != null) {
					retrievedBikeData.setProperty(nextKey, nextValue);
				}
			}
			return retrievedBikeData;
		} 
		return null;	
	}
	
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