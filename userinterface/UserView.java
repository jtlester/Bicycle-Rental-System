// specify the package
package userinterface;
// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.GridLayout;
import java.util.Properties;
import java.util.EventObject;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
//import javax.swing.JComboBox; //currently not needed
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
// project imports
import impresario.IModel;
import model.*;


//Add UserView
public class UserView extends JPanel implements ActionListener
{

	private Peon myPeon;

	private JLabel firstNameLabel;
	private JTextField firstNameTextField;

	private JLabel lastNameLabel;
	private JTextField lastNameTextField;

	private JLabel bannerLabel;
	private JTextField bannerTextField;

	private JLabel emailLabel;
	private JTextField emailTextField;

	private JLabel phoneLabel;
	private JTextField phoneTextField;

	private JButton submitButton;
	private JButton doneButton;

    public ResourceBundle localizedBundle;

	public UserView(Peon otherPeon)
	{
		myPeon = otherPeon;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createTitle());
		add(createDataEntryFields());
		add(createNavigationButtons());
		add(createStatusLog(" "));
		
	}
	private JPanel createTitle()
	{
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lbl = new JLabel(localizedBundle.getString("addUser");
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		temp.add(lbl);
		return temp;
	}
	private JPanel createDataEntryFields()
	{
		JPanel temp = new JPanel();
		// set the layout for this panel
		temp.setLayout(new GridLayout(6,2));

		// data entry fields		
		JLabel bannerLabel = new JLabel(localizedBundle.getString("bannerID") + ": ");
		temp.add(bannerLabel);
		bannerTextField = new JTextField(20);
		bannerTextField.addActionListener(this);
		temp.add(bannerTextField);
		//
		JLabel firstNameLabel = new JLabel(localizedBundle.getString("firstName") + ": ");
		temp.add(firstNameLabel);
		firstNameTextField = new JTextField(20);
		firstNameTextField.addActionListener(this);
		temp.add(firstNameTextField);
		//
		JLabel lastNameLabel = new JLabel(localizedBundle.getString("lastName") + ": ");
		temp.add(lastNameLabel);
		lastNameTextField = new JTextField(20);
		lastNameTextField.addActionListener(this);
		temp.add(lastNameTextField);
		//
		JLabel phoneLabel = new JLabel(localizedBundle.getString("phoneNumber") + ": ");
		temp.add(phoneLabel);
		phoneTextField = new JTextField(20);
		phoneTextField.addActionListener(this);
		temp.add(phoneTextField);
		//
		JLabel emailLabel = new JLabel(localizedBundle.getString("email") +  ": ");
		temp.add(emailLabel);
		emailTextField = new JTextField(20);
		emailTextField.addActionListener(this);
		temp.add(emailTextField);


		return temp;
	}

	// Create the navigation buttons
	//-------------------------------------------------------------
	private JPanel createNavigationButtons()
	{
		JPanel temp = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(50);
		f1.setHgap(25);
		temp.setLayout(f1);

		// create the buttons, listen for events, add them to the panel
		submitButton = new JButton(localizedBundle.getString("submit");
		submitButton.addActionListener(this);
		temp.add(submitButton);
		
		doneButton = new JButton(localizedBundle.getString("done");
		doneButton.addActionListener(this);
		temp.add(doneButton);

		return temp;
	}
	// Create the status log field
	//-------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage)
	{

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}
	
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == submitButton)
		{
			
			if((bannerTextField.getText() == null || firstNameTextField.getText() == null) ||
				(lastNameTextField.getText() == null || phoneTextField.getText().length() < 7 || emailTextField.getText() == null))
			{
				displayErrorMessage("Error: User fields incorrect");
			}
			else
			{
					Properties workerProperties = new Properties();
					workerProperties.setProperty("bannerId",bannerTextField.getText());
					workerProperties.setProperty("firstName",firstNameTextField.getText());
					workerProperties.setProperty("lastName",lastNameTextField.getText());
					workerProperties.setProperty("phoneNumber",phoneTextField.getText());
					workerProperties.setProperty("email",emailTextField.getText());

					myPeon.processWorkerData(workerProperties);
					
					bannerTextField.setText("");
					firstNameTextField.setText("");
					lastNameTextField.setText("");
					phoneTextField.setText("");
					emailTextField.setText("");
				
			}
			
		}
		else if(event.getSource() == doneButton)
		{
			myPeon.workerDataDone();
		}
	}



}
