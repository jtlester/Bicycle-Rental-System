// specify the package
package userinterface;
// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Properties;
import java.util.EventObject;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
// project imports
import impresario.IModel;
import model.*;

public class BicycleView extends JPanel implements ActionListener
{
	private Peon myPeon;
	private JLabel makeLabel;
	private JTextField makeTextField;
	private JLabel modelLabel;
	private JTextField modelTextField;
	private JLabel bikeConditionLabel;
	private JTextField bikeConditionTextField;
	private JLabel colorLabel;
	private JTextField colorTextField;
	private JLabel serialNumberLabel;
	private JTextField serialNumberTextField;
	private JLabel locationOnCampusLabel;
	private JTextField locationOnCampusTextField;
	private JLabel descriptionLabel;
	private JTextField desccriptionTextField;
	private JComboBox rentalComboBox;
	private JButton submitButton;
	private JButton doneButton;
	private MessageView statusLog;

    public Locale currentLocale;
    public ResourceBundle localizedBundle;
	
	//-----------------------------------------------------------------
	public BicycleView(Peon otherPeon)
	{
		myPeon = otherPeon;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createTitle());
		add(createDataEntryFields());
		add(createChoiceBox());
		add(createNavigationButtons());
		add(createStatusLog(" "));

		String language;
     	String country;
		if (args.length != 2) {
            language = new String("en");
            country = new String("US");
        } else {
            language = new String(args[0]);
            country = new String(args[1]);
        }

        currentLocale = new Locale(language, country);
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
	}
	
	// Create the labels and fields
	//-------------------------------------------------------------
	private JPanel createTitle()
	{
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lbl = new JLabel(localizedBundle.getString("addBicycle"));
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		temp.add(lbl);
		return temp;
	}
	
	// Create the main data entry fields
	//-------------------------------------------------------------
	private JPanel createDataEntryFields()
	{

		JPanel temp = new JPanel();
		// set the layout for this panel
		temp.setLayout(new GridLayout(6,2));
		// data entry fields
		JLabel makeLabel = new JLabel(localizedBundle.getString("make") + ": ";
		temp.add(makeLabel);
		bannerTextField = new JTextField(20);
		bannerTextField.addActionListener(this);
		temp.add(makeTextField);
		//
		JLabel modelLabel = new JLabel(localizedBundle.getString("model") + ": ");
		temp.add(modelLabel);
		passwordTextField = new JTextField(20);
		passwordTextField.addActionListener(this);
		temp.add(modelTextField);
		//
		JLabel bikeConditionLabel = new JLabel(localizedBundle.getString("condition") + ": ");
		temp.add(bikeConditionLabel);
		firstNameTextField = new JTextField(20);
		firstNameTextField.addActionListener(this);
		temp.add(bikeConditionTextField);
		//
		JLabel colorLabel = new JLabel(localizedBundle.getString("color") + ": ");
		temp.add(colorLabel);
		lastNameTextField = new JTextField(20);
		lastNameTextField.addActionListener(this);
		temp.add(colorTextField);
		//
		JLabel serialNumberLabel = new JLabel(localizedBundle.getString("serialNumber") + ": ");
		temp.add(serialNumberLabel);
		phoneTextField = new JTextField(20);
		phoneTextField.addActionListener(this);
		temp.add(serialNumberTextField);
		//
		JLabel locationOnCampusLabel = new JLabel(localizedBundle.getString("campusLocation") + ": ");
		temp.add(locationOnCampusLabel);
		emailTextField = new JTextField(20);
		emailTextField.addActionListener(this);
		temp.add(locationOnCampusTextField);
		return temp;
		//
		JLabel descriptionLabel = new JLabel(localizedBundle.getString("description") + ": ");
		temp.add(descriptionLabel);
		emailTextField = new JTextField(20);
		emailTextField.addActionListener(this);
		temp.add(descriptionTextField);
		return temp;
	}
	
	// Create the navigation buttons
	//-------------------------------------------------------------
	private JPanel createNavigationButtons()
	{
		JPanel temp = new JPanel(); // default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(50);
		f1.setHgap(25);
		temp.setLayout(f1);
		// create the buttons, listen for events, add them to the panel
		submitButton = new JButton(localizedBundle.getString("submit") + ": ");
		submitButton.addActionListener(this);
		temp.add(submitButton);
		doneButton = new JButton(localizedBundle.getString("Done") + ": ");
		doneButton.addActionListener(this);
		temp.add(doneButton);
		return temp;
	}
	// Create the combo box
	private JPanel createChoiceBox()
	{
		JPanel temp = new JPanel(); // default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(1);
		f1.setHgap(25);
		temp.setLayout(f1);
		JLabel rentalLabel = new JLabel(localizedBundle.getString("inOrOut") + ": ");
		temp.add(rentalLabel);
		String [] rentalPossibilites = { localizedBundle.getString('in') + ": ", localizedBundle.getString("Out") + ": " };
		rentalComboBox = new JComboBox(rentalPossibilites);
		rentalComboBox.addActionListener(this);
		temp.add(rentalComboBox);
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
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
	//-----------------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}
	//------------------------------------------------------------------
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == submitButton)
		{
			if((makeTextField.getText() == null || modelTextField.getText() == null || bikeConditionTextField.getText() == null) ||
					(colorTextField.getText() == null || serialNumberTextField.getText().length() != 10 || locationOnCampusTextField.getText() == null)
					|| descriptionTextField.getText());
			{
				displayErrorMessage("Error: Bicycle fields incorrect");
			}
			else
			{
				Properties bicycleProperties = new Properties();
				bicycleProperties.setProperty("make",makeTextField.getText());
				bicycleProperties.setProperty("model",modelTextField.getText());
				bicycleProperties.setProperty("rentalLevel",(String)rentalComboBox.getSelectedItem());
				bicycleProperties.setProperty("bikeCondition",bikeConditionTextField.getText());
				bicycleProperties.setProperty("color",colorTextField.getText());
				bicycleProperties.setProperty("serialNumber",serialNumberTextField.getText());
				bicycleProperties.setProperty("locationOnCampus",locationOnCampusTextField.getText());
				bicycleProperties.setProperty("description",descriptionTextField.getText());
				myPeon.processBicycleData(bicycleProperties);
				makeTextField.setText("");
				modelTextField.setText("");
				bikeConditionTextField.setText("");
				colorTextField.setText("");
				serialNumberTextField.setText("");
				locationOnCampusTextField.setText("");
				descriptionTextField.setText("");
			}
		}
		else if(event.getSource() == doneButton)
		{
			myPeon.bicycleDataDone();
		}
	}