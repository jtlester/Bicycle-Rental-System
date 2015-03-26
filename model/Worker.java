//specify package
package model;

//Imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


/**Class contains the Workers**/
//----------------------------------------------------------
public class Worker extends Person implements IView
{
	private static final String myTableName = "Worker";
	protected Properties persistentState;
	
	private String updateStatusMessage = "";
	
	//Constructor number one for this class
	//------------------------------------------------------
	public Worker(String bannerId) throws InvalidPrimaryKeyException
	{
		super(myTableName);
		
		setDependencies();
		
		String query = "SELECT * FROM " + myTableName + "WHERE (bannerId = " + bannerId + ")";
		Vector allDataRetrieved =  getSelectQueryResults(query);
		
		//Need to bring back at least one worker, error checking here
		if(allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();
			
			if(size == 1)
			{
				throw new InvalidPrimaryKeyException("Multiple workers match the banner id of: " + bannerId);
			}
			else
			{
				//copy the data we obtained and put it into a persistent state
				Properties retrievedWorkerData = (Properties)allDataRetrieved.elementAt(0);
				Properties persistentState = new Properties();
				
				Enumeration allKeys = retrievedWorkerData.propertyNames();
				while(allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedWorkerData.getProperty(nextKey);
					
					if(nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}
				
			}
			
		}
		//throw exception if no data found
		else
		{
			throw new InvalidPrimaryKeyException("No worker matching the banner id: " + bannerId);
		}
	}
	
	public Worker(Properties p)
	{
		super(p);
		
		setDependencies();
		Properties persistentState = new Properties();
		Enumeration allKeys = p.propertyNames();
		while(allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = p.getProperty(nextKey);
			
			if(nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}
	
	public void update()
	{
		super.update();
		
		try
		{
			if (persistentState.getProperty("personId") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("personId",
				persistentState.getProperty("personId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Person data for person id : " + persistentState.getProperty("personId") + " updated successfully in database!";
				System.out.println(updateStatusMessage);
			}
			else
			{
				
				updateStatusMessage = "Error in inserting worker information";
					
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
	
	private void setDependencies()
	{	
		myRegistry.setDependencies(persistentState);
	}
	
	//-----------------------------------------------------------------------------------
		protected void initializeSchema(String tableName)
		{
			if (mySchema == null)
			{
				mySchema = getSchemaInfo(tableName);
			}
		}
	
}
