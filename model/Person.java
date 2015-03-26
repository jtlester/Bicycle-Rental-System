package model;

//System Imports
import java.util.Properties;
import java.util.Enumeration;
import java.sql.SQLException;

public abstract class Person extends EntityBase {

	public int personId;
	public String firstName;
	public String lastName;
	public String phoneNumber;
	public String email;
	public String bannerId;
	public static final String myTableName = "Person";
	private String updateStatusMessage;
	private Properties persistentState;
		
	public Person(Properties p) {
		
		persistentState = p;

		setDependencies();
		Enumeration allKeys = persistentState.propertyNames();
		while (allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = persistentState.getProperty(nextKey);

			if (nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}
		
	}
	
	public void update()
	{
		updateStateInDatabase();
	}
	
	private void updateStateInDatabase() 
	{
		try
		{
			if (persistentState.getProperty("personId") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("personId", persistentState.getProperty("personId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Person data for person id : " + persistentState.getProperty("personId") + " updated successfully in database!";
				System.out.println(updateStatusMessage);
			}
			else
			{
				
				Integer personId =
					insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("personId", "" + personId.intValue());
				updateStatusMessage = "Person data for new person: " +  persistentState.getProperty("personId")
					+ "installed successfully in database!";
					
					System.out.println(updateStatusMessage);
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing person data in database!";
			System.out.println(updateStatusMessage);
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
	}

//------------------------------------------------------------------------

	public Object getState(String key)
	{
		if (key.equals("UpdateStatusMessage") == true)
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}

	public void stateChangeRequest(String key, Object value)
	{
		myRegistry.updateSubscribers(key, this);
	}

	private void setDependencies()
	{	
		myRegistry.setDependencies(persistentState);
	}

}
