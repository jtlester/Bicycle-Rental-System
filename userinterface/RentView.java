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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.awt.*;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


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
	private JDatePickerImpl rentDatePicker;
	private JDatePickerImpl dueDatePicker;
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
		JLabel bannerLabel = new JLabel(localizedBundle.getString("renterBannerId") + ": ");
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

		JLabel bikeLabel = new JLabel(localizedBundle.getString("bicycleSerialNumber") + ": ");
		bikeTextField = new JTextField(20);
		bikeTextField.addActionListener(this);
		findButton = new JButton(localizedBundle.getString("find"));
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
		JLabel statusLabel = new JLabel(localizedBundle.getString("status"));
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
	
	//Create Date
	private JPanel createDueDate()
	{
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel rentDateLabel = new JLabel(localizedBundle.getString("dueDate") + ": ");
		temp.add(rentDateLabel);

		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		dueDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		 
		temp.add(dueDatePicker);
		
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
				String day = String.valueOf(rentDatePicker.getModel().getDay());
				String month = String.valueOf(rentDatePicker.getModel().getMonth() + 1);
				String year = String.valueOf(rentDatePicker.getModel().getYear());
				
				String dayDue = String.valueOf(dueDatePicker.getModel().getDay());
				String monthDue = String.valueOf(dueDatePicker.getModel().getMonth() + 1);
				String yearDue = String.valueOf(dueDatePicker.getModel().getYear());
				
				Properties rentBikeProperties = new Properties();
				rentBikeProperties.setProperty("bikeId", bikeTextField.getText());
				rentBikeProperties.setProperty("bannerId", bannerTextField.getText());
				rentBikeProperties.setProperty("rentalDate", day + "-" + month + "-" + year);
				rentBikeProperties.setProperty("dueDate", dayDue + "-" + monthDue + "-" + yearDue);	
				
				peon.processRentData(rentBikeProperties);
				
				Properties statusChange = new Properties();
				statusChange.setProperty("bikeId", bikeTextField.getText());
				statusChange.setProperty("status", "Unavailable");
				peon.changeStatus(statusChange);
				
				bannerTextField.setText("");
				bikeTextField.setText("");
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
		} else if(event.getSource() == findButton) {
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