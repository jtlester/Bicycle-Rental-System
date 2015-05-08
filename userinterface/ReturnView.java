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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.DateLabelFormatter;
import model.LocaleConfig;
import model.Peon;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
// project imports

public class ReturnView extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Peon peon;
	private MessageView statusLog;
	private JTextField bannerTextField, bikeTextField;
	private JButton backButton, submitButton;
	private JDatePickerImpl returnDatePicker;

    public ResourceBundle localizedBundle;
	
	public ReturnView(Peon p) {
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
		add(datePanel());
		add(navigationPanel());		
	}
	
	private JPanel dataEntryPanel() {
		JPanel entryPanel = new JPanel();
		
		//SET LAYOUT
		entryPanel.setLayout(new GridLayout(3,2,10,10));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
		
//		//ENTRY FIELDS 
//		//BANNER ID
//		JLabel bannerLabel = new JLabel(localizedBundle.getString("renterBannerId") + ": ");
//		bannerTextField = new JTextField(20);
//		bannerTextField.addActionListener(this);
//		entryPanel.add(bannerLabel);
//		entryPanel.add(bannerTextField);
		
		//BIKE ID ENTRY FELDS
		JLabel bikeLabel = new JLabel(localizedBundle.getString("bicycleSerialNumber") + ": ");
		bikeTextField = new JTextField(20);
		bikeTextField.addActionListener(this);
		entryPanel.add(bikeLabel);
		entryPanel.add(bikeTextField);
		
		return entryPanel;
	}
	
	//Create Date
	private JPanel datePanel() {
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));		
		JLabel returnDateLabel = new JLabel(localizedBundle.getString("returnDate") + ": ");
		temp.add(returnDateLabel);

		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		returnDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		 
		temp.add(returnDatePicker);
		
		return temp;
	}
	
	// Create the navigation buttons
	private JPanel navigationPanel() {
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
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == submitButton) {
			if(bannerTextField.getText().equals("")) {
				peon.errorMessagePopup("bannerId");
			} else if(bikeTextField.getText().equals("")) {
				peon.errorMessagePopup("bikeId");
			} else {
				String day = String.valueOf(returnDatePicker.getModel().getDay());
				String month = String.valueOf(returnDatePicker.getModel().getMonth() + 1);
				String year = String.valueOf(returnDatePicker.getModel().getYear());
				
				Properties returnBikeProperties = new Properties();
				returnBikeProperties.setProperty("bikeId", bikeTextField.getText());
				returnBikeProperties.setProperty("bannerId", bannerTextField.getText());
				returnBikeProperties.setProperty("returnDate", day + "-" + month + "-" + year);
				returnBikeProperties.setProperty("status", "Inactive");
				peon.processReturnData(returnBikeProperties);
				
				Properties statusChange = new Properties();
				statusChange.setProperty("bikeId", bikeTextField.getText());
				statusChange.setProperty("status", "Available");
				peon.changeStatus(statusChange);
				
				bannerTextField.setText("");
				bikeTextField.setText("");
			}
		} else if(event.getSource() == backButton) {
			peon.createAndShowMainMenuView();
		}
	}
}