package userinterface;

import java.awt.Dimension;
import java.awt.FlowLayout;
//import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.GroupLayout;

import model.LocaleConfig;
import model.Peon;
// system imports
// project imports

public class LoginView extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    // GUI stuff
    private Peon peon;
    private JTextField bannerID;
    private JPasswordField password;
    private JButton submitButton;
    private JButton cancelButton;
    private JRadioButton englshButton;
    private JRadioButton frenchButton;
    private JLabel titleLabel;
    private JLabel passwordLabel;
    private JLabel selectLanguageLabel;
    private JLabel useridLabel;

    public ResourceBundle localizedBundle;
    public Locale currentLocale;
    public String userName;

    public LoginView(Peon p) {
        peon = p;

        currentLocale = LocaleConfig.currentLocale();
        localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
        this.setPreferredSize(new Dimension(800, 300));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(greetingPanel());
        add(dataEntryFields());
        add(navigationButtons());
        populateFields();
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        bannerID.requestFocus();
    }
    
    private JPanel greetingPanel() {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        BufferedImage logo = null;
        BufferedImage reverseLogo = null;
        
        try {
            logo = ImageIO.read(new File("images/logo.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(logo));
            temp.add(picLabel);
            
            reverseLogo = ImageIO.read(new File("images/logo_reversed.jpg"));
            JLabel rightLabel = new JLabel(new ImageIcon(reverseLogo));
            temp.add(rightLabel);
        } catch(IOException e) {
            System.out.println("ERROR: Could not load logos");
        }
        
        titleLabel = new JLabel(localizedBundle.getString("greetings"));
        Font myFont = new Font("Arial", Font.BOLD, 24);
        titleLabel.setFont(myFont);
        temp.add(titleLabel);
        return temp;
    }

    public void populateFields() {
        bannerID.setText("");
        password.setText("");
        englshButton.setSelected(true);
        frenchButton.setSelected(false);
    }
    
    private JPanel dataEntryFields() {
        JPanel dataEntryPanel = new JPanel();
        // set the layout for this panel
        dataEntryPanel.setLayout(new BoxLayout(dataEntryPanel, BoxLayout.Y_AXIS));
         
        // data entry fields
        JPanel entryFieldPanel = new JPanel();
        //entryFieldPanel.setPreferredSize( new Dimension( 20, 60 ) );
        GroupLayout layout = new GroupLayout(entryFieldPanel);
        
        
        entryFieldPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        useridLabel = new JLabel("BannerID");
        entryFieldPanel.add(useridLabel);

        bannerID = new JTextField(20);
        bannerID.addActionListener(this);
        
            
        
        
        
        entryFieldPanel.add(bannerID);

        //dataEntryPanel.add(entryFieldPanel);

        //JPanel dataFieldPanel2 = new JPanel();
        //dataFieldPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));

        passwordLabel = new JLabel(localizedBundle.getString("password") + ": ");
        entryFieldPanel.add(passwordLabel);

        password = new JPasswordField(20);
        password.addActionListener(this);
        entryFieldPanel.add(password);
        
        
        
        
        
        
                        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(useridLabel)
              .addComponent(passwordLabel))
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(bannerID,120,140,160)
              .addComponent(password,120,140,160))
               );
               
               layout.setVerticalGroup(
             layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                   .addComponent(useridLabel)
                   .addComponent(bannerID))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                   .addComponent(passwordLabel)
                   .addComponent(password))
                   
            );
        
        
        
        
        
        
        
        

        dataEntryPanel.add(entryFieldPanel);

        JPanel dataFieldPanel3 = new JPanel();

        dataFieldPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        ButtonGroup LanguageButtons = new ButtonGroup();

        selectLanguageLabel = new JLabel(localizedBundle.getString("chooseLanguage"));
        dataFieldPanel3.add(selectLanguageLabel);
        englshButton = new JRadioButton(localizedBundle.getString("english"));
        englshButton.addActionListener(this);
        LanguageButtons.add(englshButton);
        dataFieldPanel3.add(englshButton);

        frenchButton = new JRadioButton(localizedBundle.getString("french"));
        frenchButton.addActionListener(this);
        LanguageButtons.add(frenchButton);
        dataFieldPanel3.add(frenchButton);
        dataEntryPanel.add(dataFieldPanel3);
        
        return dataEntryPanel;
    }
    
    private JPanel navigationButtons() {
        JPanel temp = new JPanel();     // default FlowLayout is fine
        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
        f1.setVgap(1);
        f1.setHgap(25);
        temp.setLayout(f1);

        // create the buttons, listen for events, add them to the panel
        cancelButton = new JButton(localizedBundle.getString("exit"));
        cancelButton.addActionListener(this);
        temp.add(cancelButton);

        //submitButton.setDefaultCapable(true);
        submitButton = new JButton(localizedBundle.getString("login"));
        //submitButton.setDefaultCapable(true);
        submitButton.addActionListener(this);
        //submitButton.addActionListener(this);
        temp.add(submitButton);
        //myFrame.getRootPane().setDefaultButton( submitButton );
        

        //JRootPane root = submitButton.getRootPane();
        //root.setDefaultButton(submitButton);

        return temp;
    }
    
    public void actionPerformed(ActionEvent evt) {
        String bannerIDEntered = bannerID.getText();

        if(evt.getSource() == submitButton) {
            if ((bannerIDEntered == null) || (bannerIDEntered.length() == 0)) {
                //displayErrorMessage("Please enter a Banner ID");
                peon.errorMessagePopup("bannerId");
                
            } else {
                char[] passwordValueEntered = password.getPassword();
                String passwordEntered = new String(passwordValueEntered);
                
                if ((passwordEntered == null) || passwordEntered.length() == 0) {
                    //displayErrorMessage("Please enter a Password");
                    peon.errorMessagePopup("password");
                } else {
                    for (int cnt = 0; cnt < passwordValueEntered.length; cnt++) {
                        passwordValueEntered[cnt] = 0;
                    }
                    processUserIDAndPassword(bannerIDEntered, passwordEntered);
                }
            }
        } 

        if(evt.getSource() == cancelButton) {
            peon.exitSystem();
        }

        if(evt.getSource() == frenchButton || evt.getSource() == englshButton) {
            if(evt.getSource() == frenchButton) {
                LocaleConfig.setLocale(new Locale("fr", "FR"));
                currentLocale = new Locale("fr", "FR");
            } else {
                LocaleConfig.setLocale(new Locale("en", "US"));
                currentLocale = new Locale("en", "US");
            }
            localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
            titleLabel.setText(localizedBundle.getString("greetings"));
            passwordLabel.setText(localizedBundle.getString("password"));
            cancelButton.setText(localizedBundle.getString("exit"));
            submitButton.setText(localizedBundle.getString("login"));
            selectLanguageLabel.setText(localizedBundle.getString("chooseLanguage"));
            useridLabel.setText(localizedBundle.getString("bannerID"));
            englshButton.setText(localizedBundle.getString("english"));
            frenchButton.setText(localizedBundle.getString("french"));
            selectLanguageLabel.revalidate();
            selectLanguageLabel.repaint();
            cancelButton.revalidate();
            cancelButton.repaint();
            submitButton.revalidate();
            submitButton.repaint();
            englshButton.revalidate();
            englshButton.repaint();
            frenchButton.revalidate();
            frenchButton.repaint();
            passwordLabel.revalidate();
            passwordLabel.repaint();
            titleLabel.revalidate();
            titleLabel.repaint();
        }
        return;
    }

    /**
     * Process userid and pwd supplied when Submit button is hit.
     * Action is to pass this info on to the teller object
     */
    //----------------------------------------------------------
    private void processUserIDAndPassword(String useridString,
            String passwordString) {
        Properties props = new Properties();
        props.setProperty("bannerId", useridString);
        props.setProperty("password", passwordString);

        // clear fields for next time around
        bannerID.setText("");
        password.setText("");
        if (englshButton.isSelected()) {
            LocaleConfig.setLocale(new Locale("en", "US"));
        } else {
            LocaleConfig.setLocale(new Locale("fr", "FR"));
        }
        //userName = props.getProperty("bannerId");
        //returnUserName();
        peon.authenticateLogin(props);
    }
    /*public String returnUserName()
    {
        return userName;
    }*/

    public void updateState(String key, Object value) {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("LoginError")) {
            // display the passed text
            System.out.println((String)value);
        }
    }
}
