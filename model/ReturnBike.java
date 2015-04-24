//package
package model;

//system imports
import java.sql.*;
import java.util.*;

//GUI Imports 
import impresario.IView;

public class ReturnBike extends EntityBase implements IView
{
	private static final String myTableName = "Rental";
	protected Properties dependencies;
	
	private String updateStatusMessage = "";
	
	//initialize return object
	public ReturnBike(Properties props)
	{
		super(myTableName);
		
		setDependencies();
		persistentState = new Properties();
		
		Enumeration allKeys = props.propertyNames();
		while(allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);
			
			if(nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
			
		}
	}
	
	 private void setDependencies() {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }
    
    protected void initializeSchema(String tableName) {
         if (mySchema == null) {
             mySchema = getSchemaInfo(tableName);
         }
    }

    public void stateChangeRequest(String key, Object value) {
        myRegistry.updateSubscribers(key, this);
    }
	
	//Update the return info/table
	public void update()
	{
		
		try 
		{
					Properties whereClause = new Properties();
					whereClause.setProperty("bikeId", persistentState.getProperty("bikeId"));
					whereClause.setProperty("bannerId", persistentState.getProperty("bannerId"));
					whereClause.setProperty("status", "Active");
					updatePersistentState(mySchema, persistentState, whereClause);
					updateStatusMessage = "Updated return for Bike: " + persistentState.getProperty("bikeId");
				
		}
		catch(SQLException ex)
		{
			updateStatusMessage = "Error in updating";
			
		}
		System.out.println(updateStatusMessage);
		
	}
	
	public Object getState(String key) {
		if (key.equals("UpdateStatusMessage") == true) {
			return updateStatusMessage;
        }
		return persistentState.getProperty(key);
	}
	
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}
	
}