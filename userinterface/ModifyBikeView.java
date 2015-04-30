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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import model.Bicycle;
import model.DateLabelFormatter;
import model.LocaleConfig;
import model.Peon;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class ModifyBikeView extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private Peon peon;
	private JComboBox colorComboBox, conditionComboBox;
	private JTextField descriptionTextField, bikeTextField;
	private JButton backButton, findButton, submitButton;
	Bicycle newBike;
	
	public ResourceBundle localizedBundle;
	
	public ModifyBikeView(Peon p)
	{
		peon = p;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel mainLabel = new JLabel("Modify Bicycle Information");
		Font lblFont = new Font("Helvetica", Font.BOLD, 20);
		mainLabel.setFont(lblFont);
		titlePanel.add(mainLabel);
		add(titlePanel);
		add(createBikeSearchFunction());
		add(bicycleFields());
		add(navigationPanel());
		
	}
	
	private JPanel createBikeSearchFunction()
	{
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel bikeLabel = new JLabel("BikeID: ");
		bikeTextField = new JTextField(3);
		bikeTextField.addActionListener(this);
		findButton = new JButton(localizedBundle.getString("find"));
		findButton.addActionListener(this);
		searchPanel.add(bikeLabel);
		searchPanel.add(bikeTextField);
		searchPanel.add(findButton);
		
		return searchPanel;
	}
	
	private JPanel bicycleFields()
	{
		JPanel entryPanel = new JPanel();
		
		entryPanel.setLayout(new GridLayout(7,2,10,10));
		entryPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
		
		JLabel colorLabel = new JLabel(localizedBundle.getString("color") + ": ");
		String [] colors = {"Red", "Blue"};
		colorComboBox = new JComboBox(colors);
		
		entryPanel.add(colorLabel);
		entryPanel.add(colorComboBox);
		
		JLabel conditionLabel = new JLabel(localizedBundle.getString("condition") + ": ");
		String [] conditions = {"New", "Good", "Fair", "Poor"};
		conditionComboBox = new JComboBox(conditions);
		
		entryPanel.add(conditionLabel);
		entryPanel.add(conditionComboBox);
		
		JLabel descriptionLabel = new JLabel(localizedBundle.getString("description") + ": ");
		descriptionTextField = new JTextField(20);
		descriptionTextField.addActionListener(this);
		
		entryPanel.add(descriptionLabel);
		entryPanel.add(descriptionTextField);
		
		return entryPanel;
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
	
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == submitButton)
		{
			if(bikeTextField.getText().equals(""))
			{
				peon.errorMessagePopup("bikeId");
			}
			else
				System.out.println("UPDATE BIKE");
		}
		
		else if(event.getSource() == findButton)
		{
			Properties bicycleId = new Properties();
			bicycleId.setProperty("bikeId", bikeTextField.getText());
			newBike = new Bicycle(bicycleId);
			newBike.getBikeInfo(bicycleId);
			
			descriptionTextField.setText(newBike.getDescription());
		}
		else if(event.getSource() == backButton)
		{
			peon.returnDataDone();
		}
	}
	
	
}