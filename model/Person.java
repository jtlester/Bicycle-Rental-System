public abstract class Person extends EntityBase {

	public int personId;
	public String firstName;
	public String lastName;
	public String phoneNumber;
	public String email;
	public String bannerId;
	public static final myTableName = "Person";
	Properties persistentState;
		
	public Person(Properties p) {
		
		super(myTableName);

		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = p.propertyNames();
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
				whereClause.setProperty("personId",
				persistentState.getProperty("personId"));
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

}
