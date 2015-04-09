// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;


//GUI Imports
import impresario.IView;



public class User extends EntityBase implements IView
{
        
    private static final String myTableName = "User";
    protected Properties dependencies;
    
    private String updateStatusMessage = "";


        // GUI Components
        //private String insertStatusMessage = "";
        

    //Initilize User
    public User(Properties props)
    {

       

        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        //String value = "";
        //Properties PersonPersistentState = new Properties();



        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
        

    }

    private void setDependencies()
    {
        dependencies = new Properties();
    
        myRegistry.setDependencies(dependencies);
    }
    
    @Override
     protected void initializeSchema(String tableName)
     {
         if (mySchema == null)
         {
             mySchema = getSchemaInfo(tableName);
         }
     }

 
    @Override
    public void stateChangeRequest(String key, Object value)
    {
        myRegistry.updateSubscribers(key, this);
    }

    public void update()
    {
            //super.update();

            try
            {
                    if (persistentState.getProperty("userId") != null)
                    {
                            Properties whereClause = new Properties();
                            whereClause.setProperty("userId",
                            persistentState.getProperty("userId"));
                            updatePersistentState(mySchema, persistentState, whereClause);
                            updateStatusMessage = "User data for userId : " + persistentState.getProperty("userId") + " updated successfully in database!";
                            System.out.println(updateStatusMessage);
                    }
                    else
                    {
                            Integer userId =
                                    insertAutoIncrementalPersistentState(mySchema, persistentState);
                            persistentState.setProperty("userId", "" + userId.intValue());

                            updateStatusMessage = "Inserted user "+persistentState.getProperty("userId");

                            System.out.println(updateStatusMessage);
                    }
            }
            catch (SQLException ex)
            {
                    updateStatusMessage = "Error in installing user data in database!";
                    System.out.println(updateStatusMessage);
            }
            //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);

	}
    
        public void delete()
        {
            try
            {
                if (persistentState.getProperty("userId") != null)
                {
                    Properties whereClause = new Properties();
                    whereClause.setProperty("userId", persistentState.getProperty("userId"));
                    
                    deletePersistentState(persistentState,whereClause);
                    updateStatusMessage="Deleted user id: "+persistentState.getProperty("userId");
                    System.out.println(updateStatusMessage);
                }
            }
            catch(SQLException sqle)
            {
                updateStatusMessage="Deletion failed";
                System.out.println(updateStatusMessage);
            }
        }
	
	//----------------------------------------------------------
        @Override
	public Object getState(String key)
	{
		if (key.equals("UpdateStatusMessage") == true)
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}
	
        @Override
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}
}
