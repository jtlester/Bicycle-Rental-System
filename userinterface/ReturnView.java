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

public class ReturnView extends JPanel implements ActionListener {
	
	
	private Peon peon;
	private MessageView statusLog;
	private JTextField bannerTextField, bikeTextField;
	private JLabel bannerLabel, bikeLabel;
	private final JTextField date = new JTextField(20);
	private JButton backButton, submitButton;

    public ResourceBundle localizedBundle;
	
	public ReturnView(Peon p)
	{
		peon = p;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel mainLabel = new JLabel(localizedBundle.getString("returnBicycle"));
		Font lblFont = new Font("Helvetica", Font.BOLD, 20);
		mainLabel.setFont(lblFont);
		titlePanel.add(mainLabel);
		add(titlePanel);
		add(dataEntryPanel());
		add(createCalendar());
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
		entryPanel.setLayout(new GridLayout(3,2,10,10));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
		
		//ENTRY FIELDS 
		//BANNER ID
		JLabel bannerLabel = new JLabel("Banner ID of the renter:");
		bannerTextField = new JTextField(20);
		bannerTextField.addActionListener(this);
		entryPanel.add(bannerLabel);
		entryPanel.add(bannerTextField);
		
		//BIKE ID ENTRY FELDS
		JLabel bikeLabel = new JLabel("Bike ID:");
		bikeTextField = new JTextField(20);
		bikeTextField.addActionListener(this);
		entryPanel.add(bikeLabel);
		entryPanel.add(bikeTextField);
		
		return entryPanel;
	}
	
	//CREATE CALENDAR
	private JPanel createCalendar()
	{
		//MAKE CALENDAR
		final JPanel temp = new JPanel();
		//FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel returnLabel = new JLabel("Return Date");
		//date = new JTextField(20);
		JButton showCal = new JButton("...");
		temp.add(returnLabel);
		temp.add(date);
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
				Properties returnBikeProperties = new Properties();
				returnBikeProperties.setProperty("bikeId", bikeTextField.getText());
				returnBikeProperties.setProperty("bannerId", bannerTextField.getText());
				returnBikeProperties.setProperty("returnDate", date.getText());
				returnBikeProperties.setProperty("status", "Inactive");
				
				peon.processReturnData(returnBikeProperties);
				
				bannerTextField.setText("");
				bikeTextField.setText("");
				date.setText("");
			}
		}
		else if(event.getSource() == backButton)
		{
			peon.returnDataDone();
		}
		
	}
	
}
