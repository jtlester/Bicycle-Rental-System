package userinterface;


// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Properties;
import java.util.EventObject;
import java.util.Locale;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

// project imports
import impresario.IModel;
import model.*;



public class LoginView extends JPanel implements ActionListener
{
    // GUI stuff
    private Peon man;
    private JTextField bannerID;
    private JPasswordField password;
    private JButton submitButton;
    private JButton cancelButton;
    private JRadioButton English;
    private JRadioButton French;

    // For showing error message
    private MessageView statusLog;

    
    public LoginView(peon guy)
    {
        //super(model, "LoginView");
        man = guy;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        // Error message area
        add(createStatusLog("                          "));

        populateFields();


    }
    public void paint(Graphics g)
    {
        super.paint(g);
        bannerID.requestFocus();
    }
    	private JPanel createStatusLog(String initialMessage)
	{

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}
		public void populateFields()
	{
		bannerID.setText("");
		password.setText("");
		English.setSelected(true);
		French.setSelected(false);
	}
        private JPanel createTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Bike Rental System:Login");
        Font myFont = new Font("Arial", Font.BOLD, 24);
        label.setFont(myFont);
        temp.add(label);
        return temp;
    }
    private JPanel createDataEntryFields()
	{
		JPanel temp = new JPanel();
		// set the layout for this panel
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

		// data entry fields
		JPanel temp1 = new JPanel();
		temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel useridLabel = new JLabel("Banner ID : ");
		temp1.add(useridLabel);

		bannerID = new JTextField(20);
		bannerID.addActionListener(this);
		temp1.add(bannerID);

		temp.add(temp1);

		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel passwordLabel = new JLabel("Password : ");
		temp2.add(passwordLabel);

		password = new JPasswordField(20);
		password.addActionListener(this);
		temp2.add(password);
		
		temp.add(temp2);
		
		JPanel temp3 =new JPanel();

        temp3.setLayout(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup LanguegeButtons = new ButtonGroup();
		
		JLabel LanguegeSelect=new JLabel("Select Languege :");
		temp3.add(LanguegeSelect);
	    English = new JRadioButton("English");
	    English.addActionListener(this);
	    LanguegeButtons.add(English);
	    temp3.add(English);
	    
	    French = new JRadioButton("Français");
	    French.addActionListener(this);
	    LanguegeButtons.add(French);
	    temp3.add(French);
	    
	    temp.add(temp3);

		

		return temp;
	}
		private JPanel createNavigationButtons()
	{
		JPanel temp = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(1);
		f1.setHgap(25);
		temp.setLayout(f1);
		


		// create the buttons, listen for events, add them to the panel
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		temp.add(submitButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		temp.add(cancelButton);


		return temp;
	}
    	public void actionPerformed(ActionEvent evt)
	{
		// DEBUG: System.out.println("TellerView.actionPerformed()");

		clearErrorMessage();

		String bannerIDEntered = bannerID.getText();
        
		if(evt.getSource() == submitButton)
		{
    		if ((bannerIDEntered == null) || (bannerIDEntered.length() == 0))
    		{
    			displayErrorMessage("Please enter a Banner ID!");
    			//bannerIDEntered.requestFocus();
    		}
    		else
    		{
    			char[] passwordValueEntered = password.getPassword();
    			String passwordEntered = new String(passwordValueEntered);
    			if ((passwordEntered == null) || passwordEntered.length() == 0)
    			{
    			    displayErrorMessage("Please enter a Password!");
    			    
    			    
    			    
    			 }
    			 else
    			 {
    			     for (int cnt = 0; cnt < passwordValueEntered.length; cnt++)
    			     {
    			         passwordValueEntered[cnt] = 0;
    			     }
                     
    			     processUserIDAndPassword(bannerIDEntered, passwordEntered);
                }
    		}

        }
        else if (evt.getSource() == cancelButton)
        {
            
            man.exitSystem();
        }
     }

	/**
	 * Process userid and pwd supplied when Submit button is hit.
	 * Action is to pass this info on to the teller object
	 */
	//----------------------------------------------------------
	private void processUserIDAndPassword(String useridString,
		String passwordString)
	{
		Properties props = new Properties();
		props.setProperty("ID", useridString);
		props.setProperty("Password", passwordString);

		// clear fields for next time around
		bannerID.setText("");
		password.setText("");
        String Languege = new String();
        if (English.isSelected() == true)
        {
            LocaleConfig.setLocale(new Locale("en", "US"));
        }
        else
        {
            LocaleConfig.setLocale(new Locale("fr", "FR"));
        }
		man.authenticateLogin(props);
	}

	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// STEP 6: Be sure to finish the end of the 'perturbation'
		// by indicating how the view state gets updated.
		if (key.equals("LoginError") == true)
		{
			// display the passed text
			displayErrorMessage((String)value);
		}

	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}


}
