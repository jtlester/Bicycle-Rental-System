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
	private JTextField bannerTextField, bikeTextField, makeTextField, modelTextField, colorTextField, serialNumberTextField, locationOnCampusTextField, descriptionTextField;
	private JLabel bannerLabel, bikeLabel, makeLabel, modelLabel, colorLabel, serialNumberLabel, locationOnCampusLabel, descriptionLabel;
	private final JTextField date = new JTextField(20);
	private final JTextField dueDate = new JTextField(20);
	private JButton backButton, submitButton, findButton;
	public Bicycle newBike;

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
		add(createCalendar());
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
		
		return entryPanel;
		
	}
	
	//CREATE CALENDAR
	private JPanel createCalendar()
	{
		//MAKE CALENDAR
		final JPanel temp = new JPanel();
		//FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel rentLabel = new JLabel("Rent Date:");
		//date = new JTextField(20);
		JButton showCal = new JButton("...");
		temp.add(rentLabel);
		temp.add(date);
		temp.add(showCal);
		showCal.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ae) {
											date.setText(new DatePicker(temp).setPickedDate());
									}
								});
		return temp;
	}
	
	private JPanel createDueDate()
	{
		//MAKE CALENDAR
		final JPanel temp = new JPanel();
		//FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel dueDateLabel = new JLabel("Due Date:");
		//dueDate = new JTextField(20);
		JButton showCal = new JButton("...");
		temp.add(dueDateLabel);
		temp.add(dueDate);
		temp.add(showCal);
		showCal.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ae) {
											date.setText(new DatePicker(temp).setPickedDate());
									}
								});
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
			else if(date.getText().equals(""))
			{
				peon.errorMessagePopup("noDate");
			}
			else
			{
				Properties rentBikeProperties = new Properties();
				rentBikeProperties.setProperty("bikeId", bikeTextField.getText());
				rentBikeProperties.setProperty("bannerId", bannerTextField.getText());
				rentBikeProperties.setProperty("rentalDate", date.getText());
				rentBikeProperties.setProperty("dueDate", dueDate.getText());
				//returnBikeProperties.setProperty("status", "Inactive");
				
				peon.processRentData(rentBikeProperties);
				
				bannerTextField.setText("");
				bikeTextField.setText("");
				date.setText("");
				dueDate.setText("");
				makeTextField.setText("");
				modelTextField.setText("");
				colorTextField.setText("");
				serialNumberTextField.setText("");
				locationOnCampusTextField.setText("");
				descriptionTextField.setText("");
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
		}
		else if(event.getSource() == backButton)
		{
			peon.returnDataDone();
		}
		
	}
	
}
