package wcdonaldapplication;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

//import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JLayeredPane;
import javax.swing.JSeparator;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;

public class Main {

	private JFrame frame;
	private JPanel panel;
	
	private JLayeredPane layeredPane;
	private JPanel LoginPanel; //Panel1
	private JPanel ImagePanel;
	private JLabel Image;
	private JTextField usernameTF;
	private JPasswordField passwordTF;
	private JLabel passwordLabel;
	private JLabel usernameLabel;
	private JLabel HidePwd;
	private JLabel ShowPwd;
	private JLabel LoginLabel;
	private JSeparator separatorUsername;
	private JSeparator separatorPassword;
	private JButton LoginButton;
	private JButton SignInButton;
	private JPanel SignInPanel; //Panel2
	private JLabel SignInusernameLabel;
	private JLabel SignInPhoneNoLabel;
	private JLabel SignInEmailLabel;
	private JLabel SignInAddressLabel;
	private JLabel SignInCreatePassowrd;
	private JTextField SignInUsernameTF;
	private JTextField SignInEmailTF;
	private JTextField SignInPhoneNoTF;
	private JTextField SignInAddressTF;
	private JTextField SignInCreatePasswordTF;
	private JButton SignUpButton;
	private JButton SignInCancelButton;
	private JLabel SignUpLabel;
	private JSeparator separatorSignInUsername;
	private JSeparator separatorSignInEmail;
	private JSeparator separatorSignInPhoneNo;
	private JSeparator separatorSignInAddress;
	private JSeparator separatorSignInCreatePassword;

	private JLayeredPane layeredPane_1;
	private JPanel HomePanel; //Panel1
	private JPanel TopPanel;
	private SlidingAds adsPanel;
	private JLabel[] ads = new JLabel[3];
	private JLabel adsLeftSide;
	private JLabel adsRightSide;
	private JLabel OrderImgLabel;
	private JLabel WcDeliveryLabel;
	private JLabel OrderNowButton;
	private JLabel TrackOrderButton;
	private JPanel MenuPanel; //Panel2
	private JPanel menuTopPanel;
	private JLabel cartLabel;
	private JPanel sidePanel;
	private JLabel sideBarLabel;
	private JLabel HomeLabel;
	private JLabel TrackOrderLabel;
	private JLabel LogOutLabel;
	private JPanel CategoryPanel;
	private JSeparator separator;
	private JSeparator separator2;
	private JPanel yellowSelectedCategoryPanel;
	private JLabel PromotionLabel;
	private JLabel SuperValueMealsLabel;
	private JLabel DessertAndSidesLabel;
	private MenuListPanel PromotionPanel;
	private MenuListPanel SuperMealsPanel;
	private MenuListPanel DessertAndSidesPanel;
	private JPanel CartPanel; //Panel3
	private JPanel cartTopPanel;
	private JPanel cartSidePanel;
	private JLabel cartSideBarLabel;
	private JLabel cartHomeLabel;
	private JLabel cartTrackOrder;
	private JLabel cartLogOutLabel;
	private CartListPanel checkoutPanel; 
	private JPanel checkTopPanel;
	private JLabel checkoutLabel;
	private JLabel cartWcDeliveryLabel;
	private JLabel backButton;
	private JLabel redDot;
	private JLabel orderQuantity;
	private JPanel TrackPanel; //Panel4
	private JPanel trackSidePanel;
	private JLabel trackSideBarLabel;
	private JLabel trackHomeLabel;
	private JLabel trackMenuLabel;
	private JLabel trackLogOutLabel;
	private JPanel trackTopPanel;
	private JLabel trackWcDeliveryLabel;
	private JPanel trackOrderTopPanel;
	private JLabel trackOrderLabel;
	private TrackOrderListPanel trackListPanel;
	
	static UserData user = new UserData(); //to store user data temporarily
	static ArrayList<Menu> item = new ArrayList<Menu>(); //to store items data temporarily
	
	private final boolean animateRight = true, animateLeft = false;
	private int adsNo = 0;
	private Timer timer = new Timer();
	private TimerTask slideShow = new TimerTask() { //for ads slideshow
		@Override
		public void run() {
			if(ads[adsNo].getX() == 0) {
				int currentAds = adsNo;
				if(adsNo == 2) {
					adsNo = 0;
				}
				else
					adsNo++;
				adsPanel.switchAds(ads[currentAds], ads[adsNo], animateRight, adsNo);
			}
		}
	};
	
	static boolean checkOrder = false;
	Thread updateRedDot = new Thread() { //for red dot on the cart
		@Override
		public void run() {
			try {
				while(checkOrder) {
					Thread.sleep(200);
					boolean notEmpty = false;
					int order = 0;
					for(Menu Item: item) {
						if(Item.getQuantity() > 0) {
							notEmpty = true;
							order++;
						}
					}
					
					if(notEmpty) {
						menuTopPanel.remove(cartLabel);
						menuTopPanel.add(orderQuantity);
						menuTopPanel.add(redDot);
						menuTopPanel.add(cartLabel);
					}
					else {
						menuTopPanel.remove(redDot);
						menuTopPanel.remove(orderQuantity);
					}
					orderQuantity.setText(String.valueOf(order));
				}
			} catch ( Exception e) {
				e.printStackTrace();
			}
		}
	}; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() throws IOException, GeneralSecurityException{
		initialize();
	}
	
	//to switch panels
	public void switchPanels(JLayeredPane layeredPanel, JPanel panel) {
		layeredPanel.removeAll();
		layeredPanel.add(panel);
		layeredPanel.repaint();
		layeredPanel.revalidate();
	}
	
	//to switch layeredPanels
	public void switchLayeredPanel(JLayeredPane layeredPanel) {
		panel.removeAll();
		panel.add(layeredPanel);
		panel.repaint();
		panel.revalidate();
	}
	
	//to resize icon image
	public static ImageIcon resizeIcon(URL src, int maxHeight, int maxWidth) {
		int priorHeight = 0, priorWidth = 0;
        int newHeight = 0, newWidth = 0;
        BufferedImage image = null;
        ImageIcon sizeImage;

        try {
            image = ImageIO.read(src); // read the image
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane
                    .showMessageDialog(null, "Image could not be found!");
        }
        //set icon to image
        sizeImage = new ImageIcon(image);
        //set old dimensions
        if(sizeImage != null)
	    {
	        priorHeight = sizeImage.getIconHeight(); 
	        priorWidth = sizeImage.getIconWidth();
	    }

	    // Calculate the correct new height and width
	    if((float)priorHeight/(float)priorWidth > (float)maxHeight/(float)maxWidth)
	    {
	        newHeight = maxHeight;
	        newWidth = (int)(((float)priorWidth/(float)priorHeight)*(float)newHeight);
	    }
	    else 
	    {
	        newWidth = maxWidth;
	        newHeight = (int)(((float)priorHeight/(float)priorWidth)*(float)newWidth);
	    }

        //Create a new Buffered Image and Graphic2D object
        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = resizedImg.createGraphics();

        //Use the Graphic object to draw a new image to the image in the buffer
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, newWidth, newHeight, null);
        //need to dispose after creating Graphics instance
        g2.dispose();

        // Convert the buffered image into an ImageIcon for return
        return (new ImageIcon(resizedImg));
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(); //this is the only frame for this application
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/wcdonaldapplication/logo.png")));
		frame.setResizable(false);
		frame.setTitle("WcDelivery");
		frame.setBounds(100, 100, 784, 515);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 770, 478);
		panel.add(layeredPane);
		
		ImagePanel = new JPanel();
		ImagePanel.setBounds(0, 0, 300, 478);
		layeredPane.add(ImagePanel);
		ImagePanel.setLayout(null);
		
		Image = new JLabel("New label");
		Image.setForeground(new Color(172, 0, 0));
		Image.setFont(new Font("SansSerif", Font.BOLD, 14));
		Image.setIcon(new ImageIcon(Main.class.getResource("/wcdonaldapplication/WcdonaldLogo.png")));
		Image.setBounds(0, 0, 300, 478);
		ImagePanel.add(Image);
		
		LoginPanel = new JPanel();
		LoginPanel.setBounds(300, 0, 470, 478);
		layeredPane.add(LoginPanel);
		layeredPane.setLayer(LoginPanel, 0);
		LoginPanel.setBackground(new Color(255, 225, 157));
		LoginPanel.setLayout(null);
		
		//content in LogInPanel
		usernameTF = new JTextField();
		usernameTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						passwordTF.requestFocus();
				}
			}
		});
		usernameTF.setFont(new Font("SansSerif", Font.BOLD, 12));
		usernameTF.setCaretColor(new Color(172, 0, 0));
		usernameTF.setForeground(new Color(172, 0, 0));
		usernameTF.setBorder(null);
		usernameTF.setBackground(new Color(255, 225, 157));
		usernameTF.setBounds(82, 197, 306, 25);
		LoginPanel.add(usernameTF);
		usernameTF.setColumns(10);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(new Color(172, 0, 0));
		passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		passwordLabel.setBounds(82, 222, 78, 25);
		LoginPanel.add(passwordLabel);
		
		passwordTF = new JPasswordField();
		passwordTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoginButton.doClick();
				}
			}
		});
		passwordTF.setFont(new Font("SansSerif", Font.BOLD, 12));
		passwordTF.setForeground(new Color(172, 0, 0));
		passwordTF.setCaretColor(new Color(172, 0, 0));
		passwordTF.setBackground(new Color(255, 225, 157));
		passwordTF.setBorder(null);
		passwordTF.setToolTipText("");
		passwordTF.setBounds(82, 247, 281, 25);
		LoginPanel.add(passwordTF);
		
		usernameLabel = new JLabel("Username");
		usernameLabel.setBackground(new Color(172, 0, 0));
		usernameLabel.setForeground(new Color(172, 0, 0));
		usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		usernameLabel.setBounds(82, 172, 85, 25);
		LoginPanel.add(usernameLabel);
		
		HidePwd = new JLabel("");
		HidePwd.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/hide.png"), 20, 20));
		HidePwd.setBounds(368, 247, 20, 20);
		char dft = passwordTF.getEchoChar();
		HidePwd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HidePwd.setVisible(false);
				ShowPwd.setVisible(true);
				passwordTF.setEchoChar((char)0);
			}
		});
		LoginPanel.add(HidePwd);
		
		ShowPwd = new JLabel("");
		ShowPwd.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/show.png"), 20, 20));
		ShowPwd.setBounds(368, 247, 20, 20);
		ShowPwd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ShowPwd.setVisible(false);
				HidePwd.setVisible(true);
				passwordTF.setEchoChar(dft);
			}
		});
		LoginPanel.add(ShowPwd);
		
		LoginLabel = new JLabel("Log In");
		LoginLabel.setForeground(new Color(172, 0, 0));
		LoginLabel.setBackground(new Color(0, 0, 0));
		LoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LoginLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		LoginLabel.setBounds(187, 120, 96, 40);
		LoginPanel.add(LoginLabel);
		
		separatorUsername = new JSeparator();
		separatorUsername.setDoubleBuffered(true);
		separatorUsername.setFocusable(true);
		separatorUsername.setForeground(new Color(172, 0, 0));
		separatorUsername.setBackground(new Color(255, 225, 157));
		separatorUsername.setBounds(82, 222, 306, 10);
		LoginPanel.add(separatorUsername);
		
		separatorPassword = new JSeparator();
		separatorPassword.setForeground(new Color(172, 0, 0));
		separatorPassword.setFocusable(true);
		separatorPassword.setDoubleBuffered(true);
		separatorPassword.setBackground(new Color(255, 225, 157));
		separatorPassword.setBounds(82, 272, 306, 10);
		LoginPanel.add(separatorPassword);
		
		LoginButton = new JButton("Log In");
		LoginButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		LoginButton.setForeground(new Color(255, 225, 157));
		LoginButton.setBackground(new Color(172, 0, 0));
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SpreadSheets.sheetsService = SpreadSheets.getSheetsService();
				} catch (IOException | GeneralSecurityException e2) {
					e2.printStackTrace();
				}
				
				ValueRange response = null;
				try {
					response = SpreadSheets.sheetsService.spreadsheets().values()
							.get(SpreadSheets.SPREADSHEET_ID, "UserData!A2:E100")
							.execute();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot connect to the network! Please check your connection.");
				}
				
				List<List<Object>> values = response.getValues();
				
				if(values == null || values.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Wrong username or password!");
				}
				else {
					boolean found = false;
					for(List<?> row : values) {
						if(usernameTF.getText().equals(row.get(0)) && String.valueOf(passwordTF.getPassword()).equals(row.get(4))) {
							found = true;
							
							user = new UserData(row.get(0).toString(), row.get(1).toString(), row.get(2).toString(), row.get(3).toString());
							usernameTF.setText("");
							passwordTF.setText("");
							switchLayeredPanel(layeredPane_1);
							layeredPane_1.add(HomePanel);
							timer.schedule(slideShow, 3000, 3000);
							break;
						}	
					}
					
					if(!found) {
						JOptionPane.showMessageDialog(null, "Wrong username or password!");
					}
				}
			}
		});
		LoginButton.setBounds(130, 282, 100, 30);
		LoginButton.setFocusable(false);
		LoginPanel.add(LoginButton);
		
		SignInPanel = new JPanel();
		SignInPanel.setBounds(300, 0, 470, 478);
		SignInPanel.setBackground(new Color(255, 225, 157));
		SignInPanel.setLayout(null);
		
		SignInButton = new JButton("Sign Up");
		SignInButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		SignInButton.setForeground(new Color(255, 225, 157));
		SignInButton.setBackground(new Color(172, 0, 0));
		SignInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignInUsernameTF.setText("");
				SignInEmailTF.setText("");
				SignInPhoneNoTF.setText("");
				SignInAddressTF.setText("");
				SignInCreatePasswordTF.setText("");
				
				switchPanels(layeredPane, SignInPanel);
				layeredPane.add(ImagePanel);
				layeredPane.repaint();
				layeredPane.revalidate();
				SignInUsernameTF.requestFocus();
			}
		});
		SignInButton.setFocusable(false);
		SignInButton.setBounds(240, 282, 100, 30);
		LoginPanel.add(SignInButton);
		
		SignUpLabel = new JLabel("Sign Up");
		SignUpLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		SignUpLabel.setForeground(new Color(172, 0, 0));
		SignUpLabel.setBounds(187, 62, 96, 40);
		SignInPanel.add(SignUpLabel);
		
		SignInusernameLabel = new JLabel("Username");
		SignInusernameLabel.setForeground(new Color(172, 0, 0));
		SignInusernameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		SignInusernameLabel.setBounds(82, 114, 85, 25);
		SignInPanel.add(SignInusernameLabel);
		
		SignInPhoneNoLabel = new JLabel("Phone Number");
		SignInPhoneNoLabel.setForeground(new Color(172, 0, 0));
		SignInPhoneNoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		SignInPhoneNoLabel.setBounds(82, 214, 124, 25);
		SignInPanel.add(SignInPhoneNoLabel);
		
		SignInEmailLabel = new JLabel("Email Address");
		SignInEmailLabel.setForeground(new Color(172, 0, 0));
		SignInEmailLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		SignInEmailLabel.setBounds(82, 164, 124, 25);
		SignInPanel.add(SignInEmailLabel);
		
		SignInAddressLabel = new JLabel("Address");
		SignInAddressLabel.setForeground(new Color(172, 0, 0));
		SignInAddressLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		SignInAddressLabel.setBounds(82, 264, 124, 25);
		SignInPanel.add(SignInAddressLabel);
		
		SignInCreatePassowrd = new JLabel("Create Password");
		SignInCreatePassowrd.setForeground(new Color(172, 0, 0));
		SignInCreatePassowrd.setFont(new Font("SansSerif", Font.BOLD, 14));
		SignInCreatePassowrd.setBounds(82, 314, 124, 25);
		SignInPanel.add(SignInCreatePassowrd);
		
		SignInUsernameTF = new JTextField();
		SignInUsernameTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					SignInEmailTF.requestFocus();
				}
			}
		});
		SignInUsernameTF.setFont(new Font("SansSerif", Font.BOLD, 12));
		SignInUsernameTF.setCaretColor(new Color(172, 0, 0));
		SignInUsernameTF.setForeground(new Color(172, 0, 0));
		SignInUsernameTF.setColumns(10);
		SignInUsernameTF.setBorder(null);
		SignInUsernameTF.setBackground(new Color(255, 225, 157));
		SignInUsernameTF.setBounds(82, 139, 306, 25);
		SignInPanel.add(SignInUsernameTF);
		SignInUsernameTF.setColumns(10);
		
		SignInEmailTF = new JTextField();
		SignInEmailTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					SignInPhoneNoTF.requestFocus();
				}
			}
		});
		SignInEmailTF.setFont(new Font("SansSerif", Font.BOLD, 12));
		SignInEmailTF.setCaretColor(new Color(172, 0, 0));
		SignInEmailTF.setForeground(new Color(172, 0, 0));
		SignInEmailTF.setColumns(10);
		SignInEmailTF.setBorder(null);
		SignInEmailTF.setBackground(new Color(255, 225, 157));
		SignInEmailTF.setBounds(82, 189, 306, 25);
		SignInPanel.add(SignInEmailTF);
		SignInEmailTF.setColumns(10);
		
		SignInPhoneNoTF = new JTextField();
		SignInPhoneNoTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					SignInAddressTF.requestFocus();
				}
			}
		});
		SignInPhoneNoTF.setFont(new Font("SansSerif", Font.BOLD, 12));
		SignInPhoneNoTF.setCaretColor(new Color(172, 0, 0));
		SignInPhoneNoTF.setForeground(new Color(172, 0, 0));
		SignInPhoneNoTF.setColumns(10);
		SignInPhoneNoTF.setBorder(null);
		SignInPhoneNoTF.setBackground(new Color(255, 225, 157));
		SignInPhoneNoTF.setBounds(82, 239, 306, 25);
		SignInPanel.add(SignInPhoneNoTF);
		SignInPhoneNoTF.setColumns(10);
		
		SignInAddressTF = new JTextField();
		SignInAddressTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					SignInCreatePasswordTF.requestFocus();
				}
			}
		});
		SignInAddressTF.setFont(new Font("SansSerif", Font.BOLD, 12));
		SignInAddressTF.setForeground(new Color(172, 0, 0));
		SignInAddressTF.setColumns(10);
		SignInAddressTF.setCaretColor(new Color(172, 0, 0));
		SignInAddressTF.setBorder(null);
		SignInAddressTF.setBackground(new Color(255, 225, 157));
		SignInAddressTF.setBounds(82, 289, 306, 25);
		SignInPanel.add(SignInAddressTF);
		SignInAddressTF.setColumns(10);
		
		SignInCreatePasswordTF = new JTextField();
		SignInCreatePasswordTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					SignUpButton.doClick();
				}
			}
		});
		SignInCreatePasswordTF.setFont(new Font("SansSerif", Font.BOLD, 12));
		SignInCreatePasswordTF.setCaretColor(new Color(172, 0, 0));
		SignInCreatePasswordTF.setForeground(new Color(172, 0, 0));
		SignInCreatePasswordTF.setColumns(10);
		SignInCreatePasswordTF.setBorder(null);
		SignInCreatePasswordTF.setBackground(new Color(255, 225, 157));
		SignInCreatePasswordTF.setBounds(82, 339, 306, 25);
		SignInPanel.add(SignInCreatePasswordTF);
		SignInCreatePasswordTF.setColumns(10);
		
		SignUpButton = new JButton("Sign Up");
		SignUpButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		SignUpButton.setForeground(new Color(255, 225, 157));
		SignUpButton.setBackground(new Color(172, 0, 0));
		SignUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SpreadSheets.sheetsService = SpreadSheets.getSheetsService(); //get sheets service
				} catch (IOException | GeneralSecurityException e1) {
					e1.printStackTrace();
				}
				
				String range = "UserData!A2:D100";
				
				ValueRange response = null;
				try {
					response = SpreadSheets.sheetsService.spreadsheets().values()
							.get(SpreadSheets.SPREADSHEET_ID, range)
							.execute(); //get data from spreadsheets
				} catch (IOException e1) { //if there is no network connection
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot connect to the network! Please check your connection.");
				}
				
				List<List<Object>> values = response.getValues();
				
				boolean found = false;
				if(SignInCreatePasswordTF.getText().contains(" ")) {
					JOptionPane.showMessageDialog(null, "Password cannot have a space!");
				}
				else if(SignInCreatePasswordTF.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "Password is too long! Maximum length = 20");
				}
				else if(!SignInUsernameTF.getText().isBlank() && !SignInEmailTF.getText().isBlank() 
					 && !SignInPhoneNoTF.getText().isBlank() && !SignInAddressTF.getText().isBlank() 
					 && !SignInCreatePasswordTF.getText().isBlank()) {
					for(List<?> row: values) {
						if(SignInUsernameTF.getText().equals(row.get(0))) {
							found = true;
							JOptionPane.showMessageDialog(null, "Username is already existed! Please use another username.");
						}
					}
					
					if(!found) {
						ValueRange appendbody = new ValueRange()
								.setValues(Arrays.asList(Arrays.asList(
										SignInUsernameTF.getText(), 
										SignInEmailTF.getText(), 
										SignInPhoneNoTF.getText(), 
										SignInAddressTF.getText(),
										SignInCreatePasswordTF.getText()
										))); 
						
						try {
							SpreadSheets.sheetsService.spreadsheets().values()
									.append(SpreadSheets.SPREADSHEET_ID, "UserData", appendbody)
									.setValueInputOption("USER_ENTERED")
									.setInsertDataOption("INSERT_ROWS")
									.setIncludeValuesInResponse(true)
									.execute(); //insert new user into spreadsheets
						} catch (IOException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Cannot connect to the network! Please check your connection.");
						}
						
						user = new UserData(SignInUsernameTF.getText(), SignInEmailTF.getText(), SignInPhoneNoTF.getText(), SignInCreatePasswordTF.getText());
						JOptionPane.showMessageDialog(null, "New user has been registered! \nWelcome " + SignInUsernameTF.getText());
						
						switchLayeredPanel(layeredPane_1); //switch to layeredPane_1
						layeredPane_1.add(HomePanel); //show HomePanel
						timer.schedule(slideShow, 3000, 3000); //start slideshow
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Please fill all the information."); //if all the textfield is not filled yet
			}
		});
		SignUpButton.setFocusable(false);
		SignUpButton.setBounds(240, 374, 100, 30);
		SignInPanel.add(SignUpButton);
		
		SignInCancelButton = new JButton("Cancel");
		SignInCancelButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		SignInCancelButton.setForeground(new Color(255, 225, 157));
		SignInCancelButton.setBackground(new Color(172, 0, 0));
		SignInCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//reset string in textfield
				usernameTF.setText("");
				passwordTF.setText("");
				
				switchPanels(layeredPane, LoginPanel); //switch to LoginPanel
				layeredPane.add(ImagePanel);
				layeredPane.repaint();
				layeredPane.revalidate();
				usernameTF.requestFocus();
			}
		});
		SignInCancelButton.setBounds(130, 374, 100, 30);
		SignInCancelButton.setFocusable(false);
		SignInPanel.add(SignInCancelButton);
		
		separatorSignInUsername = new JSeparator();
		separatorSignInUsername.setForeground(new Color(172, 0, 0));
		separatorSignInUsername.setFocusable(true);
		separatorSignInUsername.setDoubleBuffered(true);
		separatorSignInUsername.setBackground(new Color(255, 225, 157));
		separatorSignInUsername.setBounds(82, 164, 306, 10);
		SignInPanel.add(separatorSignInUsername);
		
		separatorSignInEmail = new JSeparator();
		separatorSignInEmail.setForeground(new Color(172, 0, 0));
		separatorSignInEmail.setFocusable(true);
		separatorSignInEmail.setDoubleBuffered(true);
		separatorSignInEmail.setBackground(new Color(255, 225, 157));
		separatorSignInEmail.setBounds(82, 214, 306, 10);
		SignInPanel.add(separatorSignInEmail);
		
		separatorSignInPhoneNo = new JSeparator();
		separatorSignInPhoneNo.setForeground(new Color(172, 0, 0));
		separatorSignInPhoneNo.setFocusable(true);
		separatorSignInPhoneNo.setDoubleBuffered(true);
		separatorSignInPhoneNo.setBackground(new Color(255, 225, 157));
		separatorSignInPhoneNo.setBounds(82, 264, 306, 10);
		SignInPanel.add(separatorSignInPhoneNo);
		
		separatorSignInAddress = new JSeparator();
		separatorSignInAddress.setForeground(new Color(172, 0, 0));
		separatorSignInAddress.setFocusable(true);
		separatorSignInAddress.setDoubleBuffered(true);
		separatorSignInAddress.setBackground(new Color(255, 225, 157));
		separatorSignInAddress.setBounds(82, 314, 306, 10);
		SignInPanel.add(separatorSignInAddress);
		
		separatorSignInCreatePassword = new JSeparator();
		separatorSignInCreatePassword.setForeground(new Color(172, 0, 0));
		separatorSignInCreatePassword.setFocusable(true);
		separatorSignInCreatePassword.setDoubleBuffered(true);
		separatorSignInCreatePassword.setBackground(new Color(255, 225, 157));
		separatorSignInCreatePassword.setBounds(82, 364, 306, 10);
		SignInPanel.add(separatorSignInCreatePassword);
		
		layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(0, 0, 770, 478);
		panel.add(layeredPane_1);
		layeredPane_1.setLayout(null);
		
		HomePanel = new JPanel();
		HomePanel.setBounds(0, 0, 770, 478);
		HomePanel.setBackground(new Color(252, 211, 172));
		layeredPane_1.setLayer(HomePanel, 3);
		HomePanel.setLayout(null);
		
		TopPanel = new JPanel();
		TopPanel.setBounds(0, 0, 770, 50);
		TopPanel.setBackground(new Color(172, 0, 0));
		HomePanel.add(TopPanel);
		TopPanel.setLayout(null);
		
		WcDeliveryLabel = new JLabel("cDelivery");
		WcDeliveryLabel.setForeground(new Color(255, 186, 4));
		WcDeliveryLabel.setIconTextGap(-12);
		WcDeliveryLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/wcdonald.png"), 55, 55));
		WcDeliveryLabel.setHorizontalAlignment(SwingConstants.LEFT);
		WcDeliveryLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		WcDeliveryLabel.setBounds(10, 0, 400, 50);
		TopPanel.add(WcDeliveryLabel);
		
		adsLeftSide = new JLabel("");
		adsLeftSide.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { //move to ads in the left side
				if(ads[adsNo].getX() == 0) {
					int currentAds = adsNo;
					if(adsNo == 0) {
						adsNo = 2;
					}
					else
						adsNo--;
					adsPanel.switchAds(ads[currentAds], ads[adsNo], animateLeft, adsNo);
				}
				
				slideShow.cancel(); //to reset the timer
				slideShow = new TimerTask() {
					@Override
					public void run() {
						if(ads[adsNo].getX() == 0) {
							int currentAds = adsNo;
							if(adsNo == 2) {
								adsNo = 0;
							}
							else
								adsNo++;
							adsPanel.switchAds(ads[currentAds], ads[adsNo], animateRight, adsNo);
						}
					}
				};
				timer.schedule(slideShow, 3000, 3000);
			}
		});
		adsLeftSide.setBounds(0, 50, 214, 428);
		HomePanel.add(adsLeftSide);
		
		adsRightSide = new JLabel("");
		adsRightSide.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { //move to ads in the right side
				if(ads[adsNo].getX() == 0) {
					int currentAds = adsNo;
					if(adsNo == 2) {
						adsNo = 0;
					}
					else
						adsNo++;
					adsPanel.switchAds(ads[currentAds], ads[adsNo], animateRight, adsNo);
				}
				
				slideShow.cancel();
				slideShow = new TimerTask() {
					@Override
					public void run() {
						if(ads[adsNo].getX() == 0) {
							int currentAds = adsNo;
							if(adsNo == 2) {
								adsNo = 0;
							}
							else
								adsNo++;
							adsPanel.switchAds(ads[currentAds], ads[adsNo], animateRight, adsNo);
						}
					}
				};
				timer.schedule(slideShow, 3000, 3000);
			}
		});
		adsRightSide.setBounds(214, 50, 214, 428);
		HomePanel.add(adsRightSide);
		
		adsPanel = new SlidingAds(0, 50, 428, 428);
		HomePanel.add(adsPanel);
		
		ads[0] = new JLabel("");
		ads[0].setBounds(0, 0, adsPanel.getWidth(), adsPanel.getHeight());
		ads[0].setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/McDonalds-Prosperity-Burger-Deal.jpg"), 428, 428));
		adsPanel.add(ads[0]);
		
		ads[1] = new JLabel("");
		ads[1].setBounds(428, 0, adsPanel.getWidth(), adsPanel.getHeight());
		ads[1].setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/mcdonalds-creamy-butter-chicken.jpg"), 428, 428));
		adsPanel.add(ads[1]);
		
		ads[2] = new JLabel("");
		ads[2].setBounds(428, 0, adsPanel.getWidth(), adsPanel.getHeight());
		ads[2].setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/mcdonalds-mcflurry-choco-bits.jpg"), 428, 428));
		adsPanel.add(ads[2]);
		
		OrderImgLabel = new JLabel("");
		OrderImgLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/WcDonaldOrderImage.png"), 342, 342));
		OrderImgLabel.setBounds(428, 60, 342, 342);
		HomePanel.add(OrderImgLabel);
		
		OrderNowButton = new JLabel("");
		OrderNowButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PromotionPanel.setBounds(60, 100, 710, 378);
				SuperMealsPanel.setBounds(770, 100, 710, 378);
				DessertAndSidesPanel.setBounds(770, 100, 710, 378);
				
				ValueRange response = null;
				
				try {
					response = SpreadSheets.sheetsService.spreadsheets().values()
							.get(SpreadSheets.SPREADSHEET_ID, "Menu!A2:D100")
							.execute();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot connect to the network! Please check your connection.");
				}
				
				List<List<Object>> values = response.getValues();
				
				int itemNo = 0;
				for(List<?> row : values) {
					item.add(new Menu(row.get(0).toString(), row.get(1).toString(), Double.valueOf(row.get(2).toString().substring(2)), row.get(3).toString()));
					
					if(row.get(0).toString().equals("Promotion")) {
						PromotionPanel.addComponent(itemNo);
						PromotionPanel.addButtonAction(itemNo);
						
					}
					else if(row.get(0).toString().equals("Super Value Meals")) {
						SuperMealsPanel.addComponent(itemNo);
						SuperMealsPanel.addButtonAction(itemNo);
					}
					else if(row.get(0).toString().equals("Desserts and Sides")) {
						DessertAndSidesPanel.addComponent(itemNo);
						DessertAndSidesPanel.addButtonAction(itemNo);
					}
					itemNo++;
				}
				
				yellowSelectedCategoryPanel.setLocation(0, 45);
				menuTopPanel.add(WcDeliveryLabel);
				switchPanels(layeredPane_1, MenuPanel);
				checkOrder = true;
				updateRedDot.start();
			}
		});
		OrderNowButton.setIcon(new ImageIcon(Main.class.getResource("/wcdonaldapplication/OrderNowButton.png")));
		OrderNowButton.setBounds(611, 410, 113, 40);
		HomePanel.add(OrderNowButton);
		
		TrackOrderButton = new JLabel("");
		TrackOrderButton.setIcon(new ImageIcon(Main.class.getResource("/wcdonaldapplication/TrackOrderButton.png")));
		TrackOrderButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				trackListPanel.removeAll();
				trackListPanel.resetComponent();
				
				trackListPanel.setBounds(60, 100, 710, 378);
				ValueRange response = null;
				
				try {
					response = SpreadSheets.sheetsService.spreadsheets().values()
							.get(SpreadSheets.SPREADSHEET_ID, "OrderList!A2:F100")
							.execute();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot connect to the network! Please check your connection.");
				}
				
				List<List<Object>> values = response.getValues();
				
				boolean found = false;
				for(List<?> row : values) {
					if(row.get(1).toString().equals(user.getUsername())) {
						trackListPanel.addComponent(row.get(0).toString(), row.get(4).toString(), row.get(5).toString());
						found = true;
					}
				}
				
				if(!found) {
					trackListPanel.showEmptyOrder();
				}
				
				item.clear();
				switchPanels(layeredPane_1, TrackPanel);
				sidePanel.setLocation(-165, 50);
				trackSidePanel.setLocation(-165, 50);;
			}
		});
		TrackOrderButton.setBounds(476, 410, 124, 40);
		HomePanel.add(TrackOrderButton);
		
		MenuPanel = new JPanel();
		MenuPanel.setBounds(0, 0, 770, 478);
		MenuPanel.setLayout(null);
		
		menuTopPanel = new JPanel();
		menuTopPanel.setBounds(0, 0, 770, 50);
		menuTopPanel.setBackground(new Color(172, 0, 0));
		menuTopPanel.setLayout(null);
		MenuPanel.add(menuTopPanel);
		
		orderQuantity = new JLabel("0");
		orderQuantity.setFont(new Font("SansSerif", Font.BOLD, 10));
		orderQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		orderQuantity.setForeground(new Color(255, 255, 255));
		orderQuantity.setBounds(747, 7, 15, 15);
		
		redDot = new JLabel("");
		redDot.setBounds(747, 7, 15, 15);
		redDot.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/redDot.png"), 15, 15));
		
		JLabel confirmOrderButton = new JLabel("");
		confirmOrderButton.setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/confirmOrderButton.png"), 50, 270));
		confirmOrderButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
				
				ValueRange appendbody = new ValueRange()
						.setValues(Arrays.asList(Arrays.asList(
								dtf.format(LocalDateTime.now()),
								user.getUsername(),
								user.getPhoneNo(),
								user.getAddress(),
								String.format("RM%.2f", user.getPayment()),
								"Processing"
								)));
				
				try {
					SpreadSheets.sheetsService.spreadsheets().values()
							.append(SpreadSheets.SPREADSHEET_ID, "OrderList", appendbody)
							.setValueInputOption("USER_ENTERED")
							.setInsertDataOption("INSERT_ROWS")
							.setIncludeValuesInResponse(true)
							.execute();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot connect to the network! Please check your connection.");
				}
				
				JOptionPane.showMessageDialog(null, "Order accepted! Thank you for ordering.");
				
				trackListPanel.removeAll();
				trackListPanel.resetComponent();
				
				trackListPanel.setBounds(60, 100, 710, 378);
				ValueRange response = null;
				
				try {
					response = SpreadSheets.sheetsService.spreadsheets().values()
							.get(SpreadSheets.SPREADSHEET_ID, "OrderList!A2:F100")
							.execute();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot connect to the network! Please check your connection.");
				}
				
				List<List<Object>> values = response.getValues();
				
				boolean found = false;
				for(List<?> row : values) {
					if(row.get(1).toString().equals(user.getUsername())) {
						trackListPanel.addComponent(row.get(0).toString(), row.get(4).toString(), row.get(5).toString());
						found = true;
					}
				}
				
				if(!found) {
					trackListPanel.showEmptyOrder();
				}
				
				switchPanels(layeredPane_1, TrackPanel);
				checkoutPanel.removeAll();
				checkoutPanel.resetComponent();
			}
		});
		
		cartLabel = new JLabel("");
		cartLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkoutPanel.setBounds(60, 100, 720, 378);
				
				int itemNo = 0;
				double price = 0;
				int orderQuantity = 0;
				for(Menu Item: item) {
					if(Item.getQuantity() > 0) {
						checkoutPanel.addComponent(itemNo);
						price += Item.getPrice()*Item.getQuantity();
						orderQuantity += Item.getQuantity();
					}
					itemNo++;
				}
				
				if(orderQuantity == 0)
					checkoutPanel.showEmptyCart();
				else
					checkoutPanel.addPaymentComponent(price, orderQuantity, confirmOrderButton);
					
				
				switchPanels(layeredPane_1, CartPanel);
			}
		});
		cartLabel.setBounds(725, 10, 30, 30);
		cartLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/cart.png"), 30, 30));
		menuTopPanel.add(cartLabel);
		
		sidePanel = new JPanel();
		sidePanel.setBounds(-165, 50, 225, 428);
		sidePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { //open side panel
				if(sidePanel.getX() != 0) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = -165; i <= 0; i+=5) {
									Thread.sleep(1);
									sidePanel.setLocation(i, 50);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) { //close side panel
				if(!sidePanel.getVisibleRect().contains(e.getPoint())) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 0; i >= -165; i-=5) {
									Thread.sleep(1);
									sidePanel.setLocation(i, 50);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
				}
			}
		});
		sidePanel.setBackground(new Color(172, 0, 0));
		MenuPanel.add(sidePanel);
		
		sideBarLabel = new JLabel("");
		sideBarLabel.setBounds(175, 5, 40, 40);
		sidePanel.setLayout(null);
		sideBarLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/menu-bar.png"), 40, 40));
		sidePanel.add(sideBarLabel);
		
		HomeLabel = new JLabel("Home");
		HomeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PromotionPanel.removeAll();
				PromotionPanel.resetComponent();
				SuperMealsPanel.removeAll();
				SuperMealsPanel.resetComponent();
				DessertAndSidesPanel.removeAll();
				DessertAndSidesPanel.resetComponent();
				
				item.clear();
				switchPanels(layeredPane_1, HomePanel);
				HomePanel.add(TopPanel);
				TopPanel.add(WcDeliveryLabel);
				sidePanel.setBounds(-165, 50, 225, 428);
			}
		});
		HomeLabel.setForeground(Color.WHITE);
		HomeLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/home.png"), 30, 30));
		HomeLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		HomeLabel.setBounds(10, 55, 133, 30);
		sidePanel.add(HomeLabel);
		
		TrackOrderLabel = new JLabel("Track Order");
		TrackOrderLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PromotionPanel.removeAll();
				PromotionPanel.resetComponent();
				SuperMealsPanel.removeAll();
				SuperMealsPanel.resetComponent();
				DessertAndSidesPanel.removeAll();
				DessertAndSidesPanel.resetComponent();
				trackListPanel.removeAll();
				trackListPanel.resetComponent();
				
				trackListPanel.setBounds(60, 100, 710, 378);
				ValueRange response = null;
				
				try {
					response = SpreadSheets.sheetsService.spreadsheets().values()
							.get(SpreadSheets.SPREADSHEET_ID, "OrderList!A2:F100")
							.execute();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot connect to the network! Please check your connection.");
				}
				
				List<List<Object>> values = response.getValues();
				
				boolean found = false;
				for(List<?> row : values) {
					if(row.get(1).toString().equals(user.getUsername())) {
						trackListPanel.addComponent(row.get(0).toString(), row.get(4).toString(), row.get(5).toString());
						found = true;
					}
				}
				
				if(!found) {
					trackListPanel.showEmptyOrder();
				}
				
				item.clear();
				switchPanels(layeredPane_1, TrackPanel);
				sidePanel.setLocation(-165, 50);
				trackSidePanel.setLocation(0, 50);;
			}
		});
		TrackOrderLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/food-delivery.png"), 30, 30));
		TrackOrderLabel.setForeground(Color.WHITE);
		TrackOrderLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		TrackOrderLabel.setBounds(10, 95, 133, 30);
		sidePanel.add(TrackOrderLabel);
		
		LogOutLabel = new JLabel("Log Out");
		LogOutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PromotionPanel.removeAll();
				PromotionPanel.resetComponent();
				SuperMealsPanel.removeAll();
				SuperMealsPanel.resetComponent();
				DessertAndSidesPanel.removeAll();
				DessertAndSidesPanel.resetComponent();
				trackListPanel.removeAll();
				trackListPanel.resetComponent();
				
				item.clear();
				
				usernameTF.setText("");
				passwordTF.setText("");
				switchLayeredPanel(layeredPane);
				sidePanel.setBounds(-165, 50, 225, 428);
				TopPanel.add(WcDeliveryLabel);
				usernameTF.requestFocus();
			}
		});
		LogOutLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/logout.png"), 30, 30));
		LogOutLabel.setForeground(new Color(255, 255, 255));
		LogOutLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		LogOutLabel.setBounds(10, 388, 133, 30);
		sidePanel.add(LogOutLabel);
		
		CategoryPanel = new JPanel();
		CategoryPanel.setBounds(60, 50, 710, 50);
		CategoryPanel.setBackground(new Color(128, 0, 0));
		MenuPanel.add(CategoryPanel);
		CategoryPanel.setLayout(null);
		
		separator = new JSeparator();
		separator.setBackground(new Color(128, 0, 0));
		separator.setForeground(new Color(255, 255, 255));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(236, 5, 2, 40);
		CategoryPanel.add(separator);
		
		separator2 = new JSeparator();
		separator2.setBackground(new Color(128, 0, 0));
		separator2.setOrientation(SwingConstants.VERTICAL);
		separator2.setForeground(new Color(255, 255, 255));
		separator2.setBounds(473, 5, 2, 40);
		CategoryPanel.add(separator2);
		
		yellowSelectedCategoryPanel = new JPanel();
		yellowSelectedCategoryPanel.setForeground(new Color(255, 186, 4));
		yellowSelectedCategoryPanel.setBackground(new Color(255, 186, 4));
		yellowSelectedCategoryPanel.setBounds(0, 45, 236, 5);
		CategoryPanel.add(yellowSelectedCategoryPanel);
		
		PromotionLabel = new JLabel("Promotion");
		PromotionLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(yellowSelectedCategoryPanel.getX() == 237) { //animation for selected category
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 237; i >= 0; i-=3) {
									Thread.sleep(1);
									yellowSelectedCategoryPanel.setLocation(i, 45);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
					PromotionPanel.setBounds(-650, PromotionPanel.getY(), PromotionPanel.getWidth(), PromotionPanel.getHeight());
					SuperMealsPanel.switchPanel(PromotionPanel, animateLeft);
				}
				else if(yellowSelectedCategoryPanel.getX() == 474) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 474; i >= 0; i -= 6) {
									Thread.sleep(1);
									yellowSelectedCategoryPanel.setLocation(i, 45);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
					PromotionPanel.setBounds(-650, PromotionPanel.getY(), PromotionPanel.getWidth(), PromotionPanel.getHeight());
					DessertAndSidesPanel.switchPanel(PromotionPanel, animateLeft);
				}
				yellowSelectedCategoryPanel.setBounds(0, 45, 236, 5);
				MenuPanel.repaint();
				MenuPanel.revalidate();
			}
		});
		PromotionLabel.setForeground(new Color(255, 255, 255));
		PromotionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PromotionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		PromotionLabel.setBounds(0, 0, 238, 50);
		CategoryPanel.add(PromotionLabel);
		
		SuperValueMealsLabel = new JLabel("Super Value Meals");
		SuperValueMealsLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(yellowSelectedCategoryPanel.getX() == 0) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 0; i <= 237; i += 3) {
									Thread.sleep(1);
									yellowSelectedCategoryPanel.setLocation(i, 45);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
					SuperMealsPanel.setBounds(770, SuperMealsPanel.getY(), SuperMealsPanel.getWidth(), SuperMealsPanel.getHeight());
					PromotionPanel.switchPanel(SuperMealsPanel, animateRight);
				}
				else if(yellowSelectedCategoryPanel.getX() == 474) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 474; i >= 237; i -= 3) {
									Thread.sleep(1);
									yellowSelectedCategoryPanel.setLocation(i, 45);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
					SuperMealsPanel.setBounds(770, SuperMealsPanel.getY(), SuperMealsPanel.getWidth(), SuperMealsPanel.getHeight());
					DessertAndSidesPanel.switchPanel(SuperMealsPanel, animateLeft);
				}
				yellowSelectedCategoryPanel.setBounds(237, 45, 236, 5);
				MenuPanel.repaint();
				MenuPanel.revalidate();
			}
		});
		SuperValueMealsLabel.setForeground(new Color(255, 255, 255));
		SuperValueMealsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SuperValueMealsLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		SuperValueMealsLabel.setBounds(236, 0, 239, 50);
		CategoryPanel.add(SuperValueMealsLabel);
		
		DessertAndSidesLabel = new JLabel("Desserts & Sides");
		DessertAndSidesLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(yellowSelectedCategoryPanel.getX() == 0) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 0; i <= 474; i += 6) {
									Thread.sleep(1);
									yellowSelectedCategoryPanel.setLocation(i, 45);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
					DessertAndSidesPanel.setBounds(770, DessertAndSidesPanel.getY(), DessertAndSidesPanel.getWidth(), DessertAndSidesPanel.getHeight());
					PromotionPanel.switchPanel(DessertAndSidesPanel, animateRight);
				}
				else if(yellowSelectedCategoryPanel.getX() == 237) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 237; i <= 474; i += 3) {
									Thread.sleep(1);
									yellowSelectedCategoryPanel.setLocation(i, 45);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
					DessertAndSidesPanel.setBounds(770, DessertAndSidesPanel.getY(), DessertAndSidesPanel.getWidth(), DessertAndSidesPanel.getHeight());
					SuperMealsPanel.switchPanel(DessertAndSidesPanel, animateRight);
				}
				yellowSelectedCategoryPanel.setBounds(474, 45, 236, 5);
				MenuPanel.repaint();
				MenuPanel.revalidate();
			}
		});
		DessertAndSidesLabel.setForeground(new Color(255, 255, 255));
		DessertAndSidesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		DessertAndSidesLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		DessertAndSidesLabel.setBounds(473, 0, 237, 50);
		CategoryPanel.add(DessertAndSidesLabel);
		
		PromotionPanel = new MenuListPanel(MenuPanel, 100);
		
		SuperMealsPanel = new MenuListPanel(MenuPanel, 100);
		
		DessertAndSidesPanel = new MenuListPanel(MenuPanel, 100);
		
		CartPanel = new JPanel();
		CartPanel.setBounds(0, 0, 770, 478);
		CartPanel.setLayout(null);
		
		cartTopPanel = new JPanel();
		cartTopPanel.setBounds(0, 0, 770, 50);
		cartTopPanel.setBackground(new Color(172, 0, 0));
		cartTopPanel.setLayout(null);
		CartPanel.add(cartTopPanel);
		
		cartWcDeliveryLabel = new JLabel("cDelivery");
		cartWcDeliveryLabel.setBounds(0, 0, 45, 13);
		cartWcDeliveryLabel.setForeground(new Color(255, 186, 4));
		cartWcDeliveryLabel.setIconTextGap(-12);
		cartWcDeliveryLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/wcdonald.png"), 55, 55));
		cartWcDeliveryLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cartWcDeliveryLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		cartWcDeliveryLabel.setBounds(10, 0, 400, 50);
		cartTopPanel.add(cartWcDeliveryLabel);
		
		backButton = new JLabel("");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkoutPanel.removeAll();
				checkoutPanel.resetComponent();
				switchPanels(layeredPane_1, MenuPanel);
			}
		});
		backButton.setBounds(725, 10, 30, 30);
		backButton.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/backButton.png"), 30, 30));
		cartTopPanel.add(backButton);
		
		cartSidePanel = new JPanel();
		cartSidePanel.setBounds(-165, 50, 225, 428);
		cartSidePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(cartSidePanel.getX() != 0) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = -165; i <= 0; i+=5) {
									Thread.sleep(1);
									cartSidePanel.setLocation(i, 50);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!cartSidePanel.getVisibleRect().contains(e.getPoint())) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 0; i >= -165; i-=5) {
									Thread.sleep(1);
									cartSidePanel.setLocation(i, 50);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
				}
			}
		});
		cartSidePanel.setBackground(new Color(172, 0, 0));
		cartSidePanel.setLayout(null);
		CartPanel.add(cartSidePanel);
		
		cartSideBarLabel = new JLabel("");
		cartSideBarLabel.setBounds(175, 5, 40, 40);
		cartSideBarLabel.setLayout(null);
		cartSideBarLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/menu-bar.png"), 40, 40));
		cartSidePanel.add(cartSideBarLabel);
		
		cartHomeLabel = new JLabel("Home");
		cartHomeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PromotionPanel.removeAll();
				PromotionPanel.resetComponent();
				SuperMealsPanel.removeAll();
				SuperMealsPanel.resetComponent();
				DessertAndSidesPanel.removeAll();
				DessertAndSidesPanel.resetComponent();
				trackListPanel.removeAll();
				trackListPanel.resetComponent();
				
				item.clear();
				switchPanels(layeredPane_1, HomePanel);
				HomePanel.add(TopPanel);
				TopPanel.add(WcDeliveryLabel);
				cartSidePanel.setBounds(-165, 50, 225, 428);
			}
		});
		cartHomeLabel.setForeground(Color.WHITE);
		cartHomeLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/home.png"), 30, 30));
		cartHomeLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		cartHomeLabel.setBounds(10, 55, 133, 30);
		cartSidePanel.add(cartHomeLabel);
		
		cartTrackOrder = new JLabel("Track Order");
		cartTrackOrder.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/food-delivery.png"), 30, 30));
		cartTrackOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PromotionPanel.removeAll();
				PromotionPanel.resetComponent();
				SuperMealsPanel.removeAll();
				SuperMealsPanel.resetComponent();
				DessertAndSidesPanel.removeAll();
				DessertAndSidesPanel.resetComponent();
				checkoutPanel.removeAll();
				checkoutPanel.resetComponent();
				trackListPanel.removeAll();
				trackListPanel.resetComponent();

				trackListPanel.setBounds(60, 100, 710, 378);
				ValueRange response = null;
				
				try {
					response = SpreadSheets.sheetsService.spreadsheets().values()
							.get(SpreadSheets.SPREADSHEET_ID, "OrderList!A2:F100")
							.execute();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot connect to the network! Please check your connection.");
				}
				
				List<List<Object>> values = response.getValues();
				
				boolean found = false;
				for(List<?> row : values) {
					if(row.get(1).toString().equals(user.getUsername())) {
						trackListPanel.addComponent(row.get(0).toString(), row.get(4).toString(), row.get(5).toString());
						found = true;
					}
				}
				
				if(!found) {
					trackListPanel.showEmptyOrder();
				}
				
				item.clear();
				switchPanels(layeredPane_1, TrackPanel);
				sidePanel.setLocation(-165, 50);
				trackSidePanel.setLocation(0, 50);;
			}
		});
		cartTrackOrder.setForeground(Color.WHITE);
		cartTrackOrder.setFont(new Font("SansSerif", Font.BOLD, 15));
		cartTrackOrder.setBounds(10, 95, 133, 30);
		cartSidePanel.add(cartTrackOrder);
		
		cartLogOutLabel = new JLabel("Log Out");
		cartLogOutLabel.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
				PromotionPanel.removeAll();
				PromotionPanel.resetComponent();
				SuperMealsPanel.removeAll();
				SuperMealsPanel.resetComponent();
				DessertAndSidesPanel.removeAll();
				DessertAndSidesPanel.resetComponent();
				trackListPanel.removeAll();
				trackListPanel.resetComponent();
				
				item.clear();
				
				usernameTF.setText("");
				passwordTF.setText("");
				switchLayeredPanel(layeredPane);
				cartSidePanel.setBounds(-165, 50, 225, 428);
				TopPanel.add(WcDeliveryLabel);
				usernameTF.requestFocus();
			}
		});
		cartLogOutLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/logout.png"), 30, 30));
		cartLogOutLabel.setForeground(new Color(255, 255, 255));
		cartLogOutLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		cartLogOutLabel.setBounds(10, 388, 133, 30);
		cartSidePanel.add(cartLogOutLabel);
		
		checkTopPanel = new JPanel();
		checkTopPanel.setBounds(60, 50, 710, 50);
		checkTopPanel.setBackground(new Color(128, 0, 0));
		checkTopPanel.setLayout(null);
		CartPanel.add(checkTopPanel);
		
		checkoutLabel = new JLabel("Checkout");
		checkoutLabel.setForeground(new Color(255, 255, 255));
		checkoutLabel.setHorizontalAlignment(SwingConstants.CENTER);
		checkoutLabel.setBounds(275, 10, 160, 30);
		checkoutLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		checkTopPanel.add(checkoutLabel);
		
		checkoutPanel = new CartListPanel(CartPanel, 100);
		
		TrackPanel = new JPanel();
		TrackPanel.setBounds(0, 0, 770, 478);
		TrackPanel.setLayout(null);
		
		trackSidePanel = new JPanel();
		trackSidePanel.setBounds(-165, 50, 225, 428);
		trackSidePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(trackSidePanel.getX() != 0) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = -165; i <= 0; i+=5) {
									Thread.sleep(1);
									trackSidePanel.setLocation(i, 50);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!trackSidePanel.getVisibleRect().contains(e.getPoint())) {
					Thread t = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 0; i >= -165; i-=5) {
									Thread.sleep(1);
									trackSidePanel.setLocation(i, 50);
								}
							} catch ( Exception e) {
								e.printStackTrace();
							}
						}
					}; t.start();
				}
			}
		});
		trackSidePanel.setBackground(new Color(172, 0, 0));
		trackSidePanel.setLayout(null);
		TrackPanel.add(trackSidePanel);
		
		trackSideBarLabel = new JLabel("");
		trackSideBarLabel.setBounds(175, 5, 40, 40);
		trackSideBarLabel.setLayout(null);
		trackSideBarLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/menu-bar.png"), 40, 40));
		trackSidePanel.add(trackSideBarLabel);
		
		trackHomeLabel = new JLabel("Home");
		trackHomeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PromotionPanel.removeAll();
				PromotionPanel.resetComponent();
				SuperMealsPanel.removeAll();
				SuperMealsPanel.resetComponent();
				DessertAndSidesPanel.removeAll();
				DessertAndSidesPanel.resetComponent();
				trackListPanel.removeAll();
				trackListPanel.resetComponent();
								
				item.clear();
				switchPanels(layeredPane_1, HomePanel);
				HomePanel.add(TopPanel);
				TopPanel.add(WcDeliveryLabel);
				trackSidePanel.setBounds(-165, 50, 225, 428);
			}
		});
		trackHomeLabel.setForeground(Color.WHITE);
		trackHomeLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/home.png"), 30, 30));
		trackHomeLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		trackHomeLabel.setBounds(10, 55, 133, 30);
		trackSidePanel.add(trackHomeLabel);
		
		trackMenuLabel = new JLabel("Menu");
		trackMenuLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/menu.png"), 30, 30));
		trackMenuLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PromotionPanel.setBounds(60, 100, 710, 378);
				SuperMealsPanel.setBounds(770, 100, 710, 378);
				DessertAndSidesPanel.setBounds(770, 100, 710, 378);
				
				ValueRange response = null;
				
				try {
					response = SpreadSheets.sheetsService.spreadsheets().values()
							.get(SpreadSheets.SPREADSHEET_ID, "Menu!A2:D100")
							.execute();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot connect to the network! Please check your connection.");
				}
				
				List<List<Object>> values = response.getValues();
				
				int itemNo = 0;
				for(List<?> row : values) {
					item.add(new Menu(row.get(0).toString(), row.get(1).toString(), Double.valueOf(row.get(2).toString().substring(2)), row.get(3).toString()));
					
					if(row.get(0).toString().equals("Promotion")) {
						PromotionPanel.addComponent(itemNo);
						PromotionPanel.addButtonAction(itemNo);
						
					}
					else if(row.get(0).toString().equals("Super Value Meals")) {
						SuperMealsPanel.addComponent(itemNo);
						SuperMealsPanel.addButtonAction(itemNo);
					}
					else if(row.get(0).toString().equals("Desserts and Sides")) {
						DessertAndSidesPanel.addComponent(itemNo);
						DessertAndSidesPanel.addButtonAction(itemNo);
					}
					itemNo++;
				}
				
				yellowSelectedCategoryPanel.setLocation(0, 45);
				menuTopPanel.add(WcDeliveryLabel);
				switchPanels(layeredPane_1, MenuPanel);
				sidePanel.setLocation(0, 50);
				trackSidePanel.setLocation(-165, 50);
				checkOrder = true;
				updateRedDot.start();
			}
		});
		trackMenuLabel.setForeground(Color.WHITE);
		trackMenuLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		trackMenuLabel.setBounds(10, 95, 133, 30);
		trackSidePanel.add(trackMenuLabel);
		
		trackLogOutLabel = new JLabel("Log Out");
		trackLogOutLabel.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
				PromotionPanel.removeAll();
				PromotionPanel.resetComponent();
				SuperMealsPanel.removeAll();
				SuperMealsPanel.resetComponent();
				DessertAndSidesPanel.removeAll();
				DessertAndSidesPanel.resetComponent();
				trackListPanel.removeAll();
				trackListPanel.resetComponent();
				
				item.clear();
				
				usernameTF.setText("");
				passwordTF.setText("");
				switchLayeredPanel(layeredPane);
				trackSidePanel.setBounds(-165, 50, 225, 428);
				TopPanel.add(WcDeliveryLabel);
				usernameTF.requestFocus();
			}
		});
		trackLogOutLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/logout.png"), 30, 30));
		trackLogOutLabel.setForeground(new Color(255, 255, 255));
		trackLogOutLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		trackLogOutLabel.setBounds(10, 388, 133, 30);
		trackSidePanel.add(trackLogOutLabel);
		
		trackTopPanel = new JPanel();
		trackTopPanel.setBounds(0, 0, 770, 50);
		trackTopPanel.setBackground(new Color(172, 0, 0));
		trackTopPanel.setLayout(null);
		TrackPanel.add(trackTopPanel);
		
		trackWcDeliveryLabel = new JLabel("cDelivery");
		trackWcDeliveryLabel.setBounds(0, 0, 45, 13);
		trackWcDeliveryLabel.setForeground(new Color(255, 186, 4));
		trackWcDeliveryLabel.setIconTextGap(-12);
		trackWcDeliveryLabel.setIcon(resizeIcon(Main.class.getResource("/wcdonaldapplication/wcdonald.png"), 55, 55));
		trackWcDeliveryLabel.setHorizontalAlignment(SwingConstants.LEFT);
		trackWcDeliveryLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		trackWcDeliveryLabel.setBounds(10, 0, 400, 50);
		trackTopPanel.add(trackWcDeliveryLabel);
		
		trackOrderTopPanel = new JPanel();
		trackOrderTopPanel.setBounds(60, 50, 710, 50);
		trackOrderTopPanel.setBackground(new Color(128, 0, 0));
		trackOrderTopPanel.setLayout(null);
		TrackPanel.add(trackOrderTopPanel);
		
		trackOrderLabel = new JLabel("Track Order");
		trackOrderLabel.setForeground(new Color(255, 255, 255));
		trackOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trackOrderLabel.setBounds(275, 10, 160, 30);
		trackOrderLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		trackOrderTopPanel.add(trackOrderLabel);
		
		trackListPanel = new TrackOrderListPanel(TrackPanel, 100);
	}
}
