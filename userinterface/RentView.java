// specify the package
package userinterface;
// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Locale;
import java.awt.GridLayout;
import java.util.Properties;
import java.util.EventObject;
import java.util.Date;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.awt.*;
import javax.swing.*;
// project imports
import impresario.IModel;
import model.*;

public class RentView extends JPanel implements ActionListener {
	
	
	private Peon peon;
	private MessageView statusLog;
	private JTextField bannerTextField, statusTextField, bikeTextField, makeTextField, modelTextField, colorTextField, serialNumberTextField, locationOnCampusTextField, descriptionTextField;
	private JLabel bannerLabel, bikeLabel, makeLabel, modelLabel, colorLabel, serialNumberLabel, locationOnCampusLabel, descriptionLabel;
	private JButton backButton, submitButton, findButton;
	public Bicycle newBike;
	private JComboBox dueDateDayComboBox, dueDateMonthComboBox, dueDateYearComboBox, rentDateDayComboBox, rentDateMonthComboBox, rentDateYearComboBox;

    public ResourceBundle localizedBundle;
	
	public RentView(Peon p)
	{
		peon = p;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel mainLabel = new JLabel(localizedBundle.getString("rentBicycle"));
		Font lblFont = new Font("Helvetica", Font.BOLD, 20);
		mainLabel.setFont(lblFont);
		titlePanel.add(mainLabel);
		add(titlePanel);
		add(dataEntryPanel());
		add(createBikeSearchFunction());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(bicycleField());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(createRentDate());
		add(createDueDate());
		add(navigationPanel());
		
		
		add(createStatusLog("                      "));
		
	}
	
	private JPanel createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}
	
	private JPanel dataEntryPanel()
	{
		JPanel entryPanel = new JPanel();
		
		//SET LAYOUT
		entryPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,5,10,25));
		
		//ENTRY FIELDS 
		//BANNER ID
		JLabel bannerLabel = new JLabel("Banner ID of the renter:");
		bannerTextField = new JTextField(20);
		bannerTextField.addActionListener(this);
		entryPanel.add(bannerLabel);
		entryPanel.add(bannerTextField);
		
		return entryPanel;
	}
	
	private JPanel createBikeSearchFunction()
	{
				//BIKE ID ENTRY FELDS
		JPanel temp = new JPanel();
		FlowLayout layoutOne = new FlowLayout(FlowLayout.CENTER);
		//layoutOne.setHgap(2);
		temp.setLayout(layoutOne);
		//temp.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

		JLabel bikeLabel = new JLabel("Bike ID:");
		bikeTextField = new JTextField(20);
		bikeTextField.addActionListener(this);
		findButton = new JButton("Find");
		findButton.addActionListener(this);
		temp.add(bikeLabel);
		temp.add(bikeTextField);
		temp.add(findButton);
		
		return temp;
	}
	
	private JPanel bicycleField()
	{
		JPanel entryPanel = new JPanel();
		// set the layout for this panel
		entryPanel.setLayout(new GridLayout(7,2,20,20));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
		
		//Make
		JLabel makeLabel = new JLabel(localizedBundle.getString("make") + ": ");
		makeTextField = new JTextField(20);
		makeTextField.addActionListener(this);
		makeTextField.setEditable(false);
		entryPanel.add(makeLabel);
		entryPanel.add(makeTextField);
		
		//Model
		JLabel modelLabel = new JLabel(localizedBundle.getString("model") + ": ");
		modelTextField = new JTextField(20);
		modelTextField.addActionListener(this);
		modelTextField.setEditable(false);
		entryPanel.add(modelLabel);
		entryPanel.add(modelTextField);
		
		//Color
		JLabel colorLabel = new JLabel(localizedBundle.getString("color") + ": ");
		colorTextField = new JTextField(20);
		colorTextField.addActionListener(this);
		colorTextField.setEditable(false);
		entryPanel.add(colorLabel);
		entryPanel.add(colorTextField);
		
		//Serial Number
		JLabel serialNumberLabel = new JLabel(localizedBundle.getString("serialNumber") + ": ");
		entryPanel.add(serialNumberLabel);
		serialNumberTextField = new JTextField(20);
		serialNumberTextField.addActionListener(this);
		serialNumberTextField.setEditable(false);
		entryPanel.add(serialNumberTextField);
		
		//Location
		JLabel locationOnCampusLabel = new JLabel(localizedBundle.getString("campusLocation") + ": ");
		entryPanel.add(locationOnCampusLabel);
		locationOnCampusTextField = new JTextField(20);
		locationOnCampusTextField.addActionListener(this);
		locationOnCampusTextField.setEditable(false);
		entryPanel.add(locationOnCampusTextField);
		
		//Description
		JLabel descriptionLabel = new JLabel(localizedBundle.getString("description") + ": ");
		entryPanel.add(descriptionLabel);
		descriptionTextField = new JTextField(20);
		descriptionTextField.addActionListener(this);
		descriptionTextField.setEditable(false);
		entryPanel.add(descriptionTextField); 
		
		//Status
		JLabel statusLabel = new JLabel("Status");
		entryPanel.add(statusLabel);
		statusTextField = new JTextField(20);
		statusTextField.addActionListener(this);
		statusTextField.setEditable(false);
		entryPanel.add(statusTextField);
		
		return entryPanel;
		
	}
	
	//Create Date
	private JPanel createRentDate()
	{
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		//Date
		//dayComboBox = new JComboBox();
		
		JLabel rentDateLabel = new JLabel("Rent Date (dd-mm-yyyy): ");
		String [] rentDayPossibilities = {"---", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
		"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		rentDateDayComboBox = new JComboBox(rentDayPossibilities);
		rentDateDayComboBox.addActionListener(this);
		temp.add(rentDateLabel);
		temp.add(rentDateDayComboBox);
		
		//monthComboBox = new JComboBox();
		String [] rentMonthPossibilities = {"---", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		rentDateMonthComboBox = new JComboBox(rentMonthPossibilities);
		rentDateMonthComboBox.addActionListener(this);
		temp.add(rentDateMonthComboBox);
		
		//yearComboBox = new JComboBox();
		String [] rentYearPossibilities = {"---", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};
		rentDateYearComboBox = new JComboBox(rentYearPossibilities);
		rentDateYearComboBox.addActionListener(this);
		temp.add(rentDateYearComboBox);
		
		return temp;
	}
	
	//Create Date
	private JPanel createDueDate()
	{
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		//Date
		//dayComboBox = new JComboBox();
		
		JLabel dueDateLabel = new JLabel("Due Date (dd-mm-yyyy): ");
		String [] dayPossibilities = {"---", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
		"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		dueDateDayComboBox = new JComboBox(dayPossibilities);
		dueDateDayComboBox.addActionListener(this);
		temp.add(dueDateLabel);
		temp.add(dueDateDayComboBox);
		
		//monthComboBox = new JComboBox();
		String [] monthPossibilities = {"---", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		dueDateMonthComboBox = new JComboBox(monthPossibilities);
		dueDateMonthComboBox.addActionListener(this);
		temp.add(dueDateMonthComboBox);
		
		//yearComboBox = new JComboBox();
		String [] yearPossibilities = {"---", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};
		dueDateYearComboBox = new JComboBox(yearPossibilities);
		dueDateYearComboBox.addActionListener(this);
		temp.add(dueDateYearComboBox);
		
		return temp;
	}
	
	// Create the navigation buttons
	//-------------------------------------------------------------
	private JPanel navigationPanel()
	{
		JPanel navPanel = new JPanel();		// default FlowLayout is fine
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
	
	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}
	
	public void displayMessage(String message) {
		statusLog.displayMessage(message);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == submitButton)
		{
			if(bannerTextField.getText().equals(""))
			{
				peon.errorMessagePopup("bannerId");
			}
			else if(bikeTextField.getText().equals(""))
			{
				peon.errorMessagePopup("bikeId");
			}
			else
			{
				String month = (String)rentDateMonthComboBox.getSelectedItem();
				String year = (String)rentDateYearComboBox.getSelectedItem();
				String day = (String)rentDateDayComboBox.getSelectedItem();
				
				String dayDue = (String)dueDateDayComboBox.getSelectedItem();
				String yearDue = (String)dueDateYearComboBox.getSelectedItem();
				String monthDue = (String)dueDateMonthComboBox.getSelectedItem();
				
				Properties rentBikeProperties = new Properties();
				rentBikeProperties.setProperty("bikeId", bikeTextField.getText());
				rentBikeProperties.setProperty("bannerId", bannerTextField.getText());
				rentBikeProperties.setProperty("rentalDate", day + "-" + month + "-" + year);
				rentBikeProperties.setProperty("dueDate", dayDue + "-" + monthDue + "-" + yearDue);
				//returnBikeProperties.setProperty("status", "Inactive");
				
				peon.processRentData(rentBikeProperties);
				
				Properties statusChange = new Properties();
				statusChange.setProperty("bikeId", bikeTextField.getText());
				statusChange.setProperty("status", "Unavailable");
				peon.changeStatus(statusChange);
				
				bannerTextField.setText("");
				bikeTextField.setText("");
				//date.setText("");
				//dueDate.setText("");
				makeTextField.setText("");
				modelTextField.setText("");
				colorTextField.setText("");
				serialNumberTextField.setText("");
				locationOnCampusTextField.setText("");
				descriptionTextField.setText("");
				rentDateDayComboBox.setSelectedIndex(0);
				rentDateMonthComboBox.setSelectedIndex(0);
				rentDateYearComboBox.setSelectedIndex(0);
				dueDateDayComboBox.setSelectedIndex(0);
				dueDateMonthComboBox.setSelectedIndex(0);
				dueDateYearComboBox.setSelectedIndex(0);
			}
		}
		else if(event.getSource() == findButton)
		{
			//System.out.println("Clicked find button");
			Properties bicycleId = new Properties();
			bicycleId.setProperty("bikeId", bikeTextField.getText());
			newBike = new Bicycle(bicycleId);
			newBike.getBikeInfo(bicycleId);
			
			makeTextField.setText(newBike.getMake());
			modelTextField.setText(newBike.getModel());
			colorTextField.setText(newBike.getColor());
			serialNumberTextField.setText(newBike.getSerial());
			locationOnCampusTextField.setText(newBike.getLocation());
			descriptionTextField.setText(newBike.getDescription());
			statusTextField.setText(newBike.getStatus());
		}
		else if(event.getSource() == backButton)
		{
			peon.returnDataDone();
		}
		
	}
	
}