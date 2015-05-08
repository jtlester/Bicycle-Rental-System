//package
package model;

import impresario.IView;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

//I'm getting could not generate keys for this one.. not sure what i changed to get it tho

public class RentBike extends EntityBase implements IView {
	private static final String myTableName = "Rental";
	protected Properties dependencies;
	private String updateStatusMessage = "";

	//initialize return object
	public RentBike(Properties props) {
		super(myTableName);

		setDependencies();
		persistentState = new Properties();

		Enumeration allKeys = props.propertyNames();
		while(allKeys.hasMoreElements() == true) {
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);
			if(nextValue != null) {
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}

	private void setDependencies() {
		dependencies = new Properties();
		myRegistry.setDependencies(dependencies);
	}

	protected void initializeSchema(String tableName) {
		if(mySchema == null) {
			mySchema = getSchemaInfo(tableName);
		}
	}

	public void stateChangeRequest(String key, Object value) {
		myRegistry.updateSubscribers(key, this);
	}

	//Update the return info/table
	public boolean update() {
		try {
			Integer rentId = insertAutoIncrementalPersistentState(mySchema, persistentState);
			persistentState.setProperty("rentalId", "" + rentId.intValue());
			updateStatusMessage = "Rental data for new rental: " +  persistentState.getProperty("rentalId") + "installed successfully in database!";
			System.out.println(updateStatusMessage);
			return true;
		} catch(SQLException ex) {
			updateStatusMessage = "Error in renting";
			System.out.println(ex);
		}
		return false;
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