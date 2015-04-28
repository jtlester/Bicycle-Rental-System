// specify the package
package userinterface;
// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import model.DateLabelFormatter;
import model.LocaleConfig;
import model.Peon;

public class BicycleView extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Peon peon;
	private JTextField makeTextField;
	private JTextField modelTextField;
	private JComboBox bikeConditionComboBox;
	//private JTextField colorTextField;
	private JComboBox colorComboBox;
	private JTextField serialNumberTextField;
	private JTextField locationOnCampusTextField;
	private JTextField descriptionTextField;
	private JButton submitButton;
	private JButton backButton;
	private MessageView statusLog;
	private JComboBox dayComboBox, monthComboBox, yearComboBox;

    public ResourceBundle localizedBundle;
    
	private JDatePickerImpl rentDatePicker;

	
	public BicycleView(Peon p) {
		peon = p;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lbl = new JLabel(localizedBundle.getString("addBicycle"));
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		titlePanel.add(lbl);
		add(titlePanel);

		add(dataEntryPanel());
		add(createDate());
		add(navigationPanel());
		add(createStatusLog("                          "));
	}
	private JPanel createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}
	// Create the main data entry fields
	//-------------------------------------------------------------
	private JPanel dataEntryPanel() {
		JPanel entryPanel = new JPanel();
		// set the layout for this panel
		entryPanel.setLayout(new GridLayout(7,2,20,20));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
		
		//Make
		JLabel makeLabel = new JLabel(localizedBundle.getString("make") + ": ");
		makeTextField = new JTextField(20);
		makeTextField.addActionListener(this);
		entryPanel.add(makeLabel);
		entryPanel.add(makeTextField);
		
		//Model
		JLabel modelLabel = new JLabel(localizedBundle.getString("model") + ": ");
		modelTextField = new JTextField(20);
		modelTextField.addActionListener(this);
		entryPanel.add(modelLabel);
		entryPanel.add(modelTextField);
		
		//Condition
		JLabel bikeConditionLabel = new JLabel(localizedBundle.getString("condition") + ": ");
		//bikeConditionTextField = new JTextField(20);
		//bikeConditionTextField.addActionListener(this);
		bikeConditionComboBox = new JComboBox();
		String [] conditionPossibilities = {"---", localizedBundle.getString("new"), localizedBundle.getString("good"),
				localizedBundle.getString("fair"), localizedBundle.getString("poor")};
		bikeConditionComboBox = new JComboBox(conditionPossibilities);
		bikeConditionComboBox.addActionListener(this);
		entryPanel.add(bikeConditionLabel);
		entryPanel.add(bikeConditionComboBox);
		//entryPanel.add(bikeConditionTextField);
		
		//Color
		JLabel colorLabel = new JLabel(localizedBundle.getString("color") + ": ");
		//colorTextField = new JTextField(20);
		//colorTextField.addActionListener(this);
		colorComboBox = new JComboBox();
		String [] colorPossibilities = {"---", localizedBundle.getString("red"), localizedBundle.getString("orange"), localizedBundle.getString("yellow"),
			localizedBundle.getString("green"), localizedBundle.getString("blue"), localizedBundle.getString("purple"), localizedBundle.getString("brown"),
				localizedBundle.getString("black"), localizedBundle.getString("white")};
		colorComboBox = new JComboBox(colorPossibilities);
		colorComboBox.addActionListener(this);
		entryPanel.add(colorLabel);
		entryPanel.add(colorComboBox);
		//entryPanel.add(colorTextField);
		
		//Serial Number
		JLabel serialNumberLabel = new JLabel(localizedBundle.getString("serialNumber") + ": ");
		entryPanel.add(serialNumberLabel);
		serialNumberTextField = new JTextField(20);
		serialNumberTextField.addActionListener(this);
		entryPanel.add(serialNumberTextField);
		
		//Location
		JLabel locationOnCampusLabel = new JLabel(localizedBundle.getString("campusLocation") + ": ");
		entryPanel.add(locationOnCampusLabel);
		locationOnCampusTextField = new JTextField(20);
		locationOnCampusTextField.addActionListener(this);
		entryPanel.add(locationOnCampusTextField);
		
		//Description
		JLabel descriptionLabel = new JLabel(localizedBundle.getString("description") + ": ");
		entryPanel.add(descriptionLabel);
		descriptionTextField = new JTextField(20);
		descriptionTextField.addActionListener(this);
		entryPanel.add(descriptionTextField); 
		return entryPanel;
	}
	
		//Create Date
	private JPanel createDate()
	{
		//Why is there a rental date for add bicycle? It shouldn't be rented by default when we add one to the database
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel rentDateLabel = new JLabel(localizedBundle.getString("rentDate") + ": ");
		temp.add(rentDateLabel);

		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		rentDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		 
		temp.add(rentDatePicker);
		
		
		return temp;
	}
	
	// Create the navigation buttons
	private JPanel navigationPanel() {
		JPanel navPanel = new JPanel(); // default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(50);
		f1.setHgap(25);
		navPanel.setLayout(f1);
		
		// create the buttons, listen for events, add them to the panel
		backButton = new JButton(localizedBundle.getString("back"));
		backButton.addActionListener(this);
		navPanel.add(backButton);

		submitButton = new JButton(localizedBundle.getString("submit"));
		submitButton.addActionListener(this);
		navPanel.add(submitButton);
		return navPanel;
	}

	// Create the combo box
	/*private JPanel choiceBox() {
		JPanel cBox = new JPanel(); // default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(8);
		f1.setHgap(25);
		cBox.setLayout(f1);
		JLabel rentalLabel = new JLabel(localizedBundle.getString("inOrOut"));
		cBox.add(rentalLabel);
		String [] rentalPossibilites = { localizedBundle.getString("in"), localizedBundle.getString("out")};
		rentalComboBox = new JComboBox(rentalPossibilites);
		rentalComboBox.addActionListener(this);
		cBox.add(rentalComboBox);
		return cBox;
	}*/
	
	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}

	public void displayMessage(String message) {
		statusLog.displayMessage(message);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == submitButton) {
			if(makeTextField.getText().equals(""))
			{
				peon.errorMessagePopup("make");
			}
			else if(modelTextField.getText().equals(""))
			{
				peon.errorMessagePopup("model");
			}
			else if(serialNumberTextField.getText().length() != 10)
			{
				peon.errorMessagePopup("serialNumber");
			}
			else if(locationOnCampusTextField.getText().equals(""))
			{
				peon.errorMessagePopup("location");
			}
			else {
				String day = String.valueOf(rentDatePicker.getModel().getDay());
				String month = String.valueOf(rentDatePicker.getModel().getMonth() + 1);
				String year = String.valueOf(rentDatePicker.getModel().getYear());
				
				Properties bicycleProperties = new Properties();
				bicycleProperties.setProperty("make",makeTextField.getText());
				bicycleProperties.setProperty("model",modelTextField.getText());
				bicycleProperties.setProperty("bikeCondition", (String)bikeConditionComboBox.getSelectedItem());
				bicycleProperties.setProperty("color",(String)colorComboBox.getSelectedItem());
				bicycleProperties.setProperty("serialNumber",serialNumberTextField.getText());
				bicycleProperties.setProperty("locationOnCampus",locationOnCampusTextField.getText());
				bicycleProperties.setProperty("description",descriptionTextField.getText());
				bicycleProperties.setProperty("status", "in");
				bicycleProperties.setProperty("dateRegistered", day + "-" + month + "-" + year);
				peon.processBicycleData(bicycleProperties);
				makeTextField.setText("");
				modelTextField.setText("");
				//bikeConditionTextField.setText("");
				//colorTextField.setText("");
				serialNumberTextField.setText("");
				locationOnCampusTextField.setText("");
				descriptionTextField.setText("");
				dayComboBox.setSelectedIndex(0);
				monthComboBox.setSelectedIndex(0);
				yearComboBox.setSelectedIndex(0);
			}
		}
		else if(event.getSource() == backButton) {
			peon.bicycleDataDone();
		}
	}
}