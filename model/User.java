// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;



// project imports
//import exception.InvalidPrimaryKeyException;
import database.*;


//GUI Imports
import impresario.IView;
import userinterface.View;
import userinterface.ViewFactory;



public class User extends Person //will change to: extends Person
{
        
    private static final String myTableName = "user";
        protected Properties dependencies;
        protected Properties persistentState;
    
    private String updateStatusMessage = "";


        // GUI Components
        //private String insertStatusMessage = "";
        

    //Initilize User
    public User(Properties props)
    {

       

        super(props);

        setDependencies();
        persistentState = new Properties();
        String value = "";
        Properties PersonPersistentState = new Properties();



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


    //Takes an array of strings to insert user into Database
    public void addUser(String[] Values)
    {
        super.addperson();
        String insertStatusMessage;
        try {
            if (persistentState.getProperty("patronId") != null)
            {
                insertPersistentState(mySchema, persistentState);
                insertStatusMessage = "Account data for account number : " + persistentState.getProperty("patronId") + " Inserted successfully!";
            
            }
        }   
        catch (SQLException e)
        {
            updateStatusMessage = "Error in Adding User Data To Database!";
        }
            
     }

     protected void initializeSchema(String tableName)
     {
         if (mySchema == null)
         {
             mySchema = getSchemaInfo(tableName);
         }
     }
      
    private void updateStateInDatabase() 
    {

       super.update();//Will be used from the user class to communicate with person super class in order to update both tables
    
    
    
        try
        {
            if (persistentState.getProperty("BannerId") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("AccountNumber",
                persistentState.getProperty("AccountNumber"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Account data for account number : " + persistentState.getProperty("AccountNumber") + " updated successfully in database!";
            }
                else
            {

                System.out.println("BannerID == NULL");
                                
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing account data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

}
