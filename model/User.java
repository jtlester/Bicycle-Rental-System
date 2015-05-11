// specify the package
package model;

// system imports
//GUI Imports
import impresario.IView;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class User extends EntityBase implements IView {

	private static final String myTableName = "User";
	protected Properties dependencies;
	Properties userInfo;
	private String updateStatusMessage = "";

	//Initilize User
	public User(Properties props) {
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

	public boolean update() {

		try {
			if (persistentState.getProperty("userId") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("userId",
						persistentState.getProperty("userId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "User data for bannerId : " + persistentState.getProperty("bannerId") + " updated successfully in database!";
			} else {
				Integer userId =  insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("userId", "" + userId.intValue());
				updateStatusMessage = "Inserted user " + persistentState.getProperty("userId");
			}
		}
		catch (SQLException ex) {
			updateStatusMessage = "Error in installing user data in database!";
			return false;
		}
		return true;
	}

	public boolean updateUserInfo()
	{
		try
		{
			Properties whereClause = new Properties();
			whereClause.setProperty("bannerId", persistentState.getProperty("bannerId"));
			updatePersistentState(mySchema, persistentState, whereClause);

		}
		catch(SQLException ex)
		{
			return false;
		}
		return true;
	}


	public Properties userInfo(Properties props) {
		String authQuery = "SELECT * FROM `" + myTableName + "` WHERE (`bannerId` = '" + props.getProperty("bannerId") + "');";
		Vector allDataRetrieved = getSelectQueryResult(authQuery);
		int size = allDataRetrieved.size();
		if(size == 1) {
			Properties retrievedUserData = (Properties)allDataRetrieved.elementAt(0);
			Enumeration allKeys = retrievedUserData.propertyNames();
			while(allKeys.hasMoreElements() == true) {
				String nextKey = (String)allKeys.nextElement();
				String nextValue = retrievedUserData.getProperty(nextKey);
				if(nextValue != null) {
					retrievedUserData.setProperty(nextKey, nextValue);
				}
			}
			return retrievedUserData;
		}
		return null;
	}

	public void delete() {
		try {
			if (persistentState.getProperty("userId") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("userId", persistentState.getProperty("userId"));

				deletePersistentState(persistentState,whereClause);
				updateStatusMessage="Deleted user id: "+persistentState.getProperty("userId");
				System.out.println(updateStatusMessage);
			}
		} catch(SQLException sqle) {
			updateStatusMessage="Deletion failed";
			System.out.println(updateStatusMessage);
		}
	}

	public Object getState(String key) {
		if (key.equals("UpdateStatusMessage") == true) {
			return updateStatusMessage;
		}
		return persistentState.getProperty(key);
	}
	public Properties GetUser()
	{
		return userInfo;
	}

	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}
}
