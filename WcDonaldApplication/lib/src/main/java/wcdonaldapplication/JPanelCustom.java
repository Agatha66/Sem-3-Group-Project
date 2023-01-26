package wcdonaldapplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class JPanelCustom extends JPanel{ //custom panels
	protected ArrayList<JLabel> itemImage = new ArrayList<JLabel>();
	protected ArrayList<JLabel> itemName = new ArrayList<JLabel>();
	protected ArrayList<JLabel> itemPrice = new ArrayList<JLabel>();
	protected ArrayList<JSeparator> separatorlist = new ArrayList<JSeparator>();
	
	protected int index = 0;
	protected int newHeight = 5;
	
	JPanelCustom(JPanel parentPanel, int startingY){
		setBackground(new Color(255, 255, 217));
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int units = e.getUnitsToScroll()*6;
							
				if(getY() <= startingY && units > 0) {
					if(getY() - units > parentPanel.getHeight() - getHeight()) {
						setBounds(getX(), getY() - units, getWidth(), getHeight());
					}
					else {
						setBounds(getX(), parentPanel.getHeight() - getHeight(), getWidth(), getHeight());
					}
				}
				else if(getY() <= startingY && units < 0) {
					if(getY() - units < startingY) {
						setBounds(getX(), getY() - units, getWidth(), getHeight());
					}
					else {
						setBounds(getX(), startingY, getWidth(), getHeight());
					}
				}
			}
		});
		setLayout(null);
		parentPanel.add(this);
	}
}

@SuppressWarnings("serial")
class MenuListPanel extends JPanelCustom{ //for List of item in MenuPanel
	private ArrayList<JLabel> minusButton = new ArrayList<JLabel>();
	private ArrayList<JLabel> addButton = new ArrayList<JLabel>();
	private ArrayList<JTextField> quantityTF = new ArrayList<JTextField>();
	
	MenuListPanel(JPanel parentPanel, int startingY){
		super(parentPanel, startingY);
	}
	
	public void addComponent(int itemNo) { //add component into this panel
		if(newHeight > getHeight()) {
			setBounds(getX(), getY(), getWidth(), getHeight() + 130);
		}
		
		separatorlist.add(new JSeparator());
		separatorlist.get(index).setDoubleBuffered(true);
		separatorlist.get(index).setFocusable(true);
		separatorlist.get(index).setForeground(new Color(128, 0, 0));
		separatorlist.get(index).setBackground(new Color(255, 255, 217));
		separatorlist.get(index).setBounds(10, newHeight + 120, 690, 5);
		add(separatorlist.get(index));
		
		itemImage.add(new JLabel(""));
		itemImage.get(index).setBounds(20, newHeight, 110, 110);
		itemImage.get(index).setIcon(Main.resizeIcon(Main.class.getResource(Main.item.get(itemNo).getImageFileURL()), itemImage.get(index).getWidth(), itemImage.get(index).getHeight()));
		add(itemImage.get(index));
		
		itemName.add(new JLabel(Main.item.get(itemNo).getItemName()));
		itemName.get(index).setBounds(150, newHeight + 25, 400, 30);
		itemName.get(index).setHorizontalAlignment(SwingConstants.LEFT);
		itemName.get(index).setFont(new Font("SansSerif", Font.BOLD, 18));
		add(itemName.get(index));
		
		itemPrice.add(new JLabel("RM" + Main.item.get(itemNo).getPrice()));
		itemPrice.get(index).setForeground(new Color(115, 115, 115));
		itemPrice.get(index).setBounds(150, newHeight + 55, 100, 30);
		itemPrice.get(index).setHorizontalAlignment(SwingConstants.LEFT);
		itemPrice.get(index).setFont(new Font("SansSerif", Font.BOLD, 18));
		add(itemPrice.get(index));
		
		quantityTF.add(new JTextField(String.valueOf(Main.item.get(itemNo).getQuantity())));
		quantityTF.get(index).setHorizontalAlignment(SwingConstants.CENTER);
		quantityTF.get(index).setFont(new Font("SansSerif", Font.BOLD, 15));
		quantityTF.get(index).setBounds(593, newHeight + 39, 54, 33);
		quantityTF.get(index).setBorder(null);
		quantityTF.get(index).setBackground(new Color(220, 220, 220));
		quantityTF.get(index).setEditable(false);
		add(quantityTF.get(index));
		
		minusButton.add(new JLabel(""));
		minusButton.get(index).setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/minusButton.png"), 40, 40));
		minusButton.get(index).setBounds(560, newHeight + 35, 40, 40);
		add(minusButton.get(index));
		
		addButton.add(new JLabel(""));
		addButton.get(index).setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/addButton.png"), 40, 40));
		addButton.get(index).setBounds(640, newHeight + 35, 40, 40);
		add(addButton.get(index));
		
		index++;
		newHeight += 130;
	}
	
	public void switchPanel(JPanelCustom newPanel, boolean animateRight) { //for animation to switch category of the items in MenuPanel
		if(animateRight) {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						int j = 770;
						for(int i = 60; i >= -650; i-=10) {
							Thread.sleep(1);
							setLocation(i, getY());
							newPanel.setLocation(j, newPanel.getY());
							j-=10;
						}
					} catch ( Exception e) {
						e.printStackTrace();
					}
				}
			}; t.start();
		}
		else {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						int j = -650;
						for(int i = 60; i <= 770; i+=10) {
							Thread.sleep(1);
							setLocation(i, getY());
							newPanel.setLocation(j, newPanel.getY());
							j+=10;
						}
					} catch ( Exception e) {
						e.printStackTrace();
					}
				}
			}; t.start();
		}
	}
	
	public void addButtonAction(int itemNo) { //add action for button added in the panel
		int i = index - 1;
		addButton.get(i).addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Main.item.get(itemNo).setQuantity(Main.item.get(itemNo).getQuantity() + 1);
				quantityTF.get(i).setText(String.valueOf(Main.item.get(itemNo).getQuantity()));
			}
		});
		minusButton.get(i).addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(Integer.valueOf(quantityTF.get(i).getText()) > 0) {
					Main.item.get(itemNo).setQuantity(Main.item.get(itemNo).getQuantity() - 1);
					quantityTF.get(i).setText(String.valueOf(Main.item.get(itemNo).getQuantity()));
				}
			}
		});
	}
	
	public void resetComponent() { //reset this panel component(used to refresh the panel)
		itemImage.clear();
		itemName.clear();
		itemPrice.clear();
		separatorlist.clear();
		minusButton.clear();
		addButton.clear();
		quantityTF.clear();
		
		index = 0;
		newHeight = 5;
	}
}

@SuppressWarnings("serial")
class CartListPanel extends JPanelCustom { //for cart list in CheckoutPanel
	private ArrayList<JLabel> quantity = new ArrayList<JLabel>();
	
	CartListPanel(JPanel parentPanel, int startingY){
		super(parentPanel, startingY);
	}
	
	public void addComponent(int itemNo) {
		if(newHeight > getHeight()) {
			setBounds(getX(), getY(), getWidth(), getHeight() + 130);
		}
		
		separatorlist.add(new JSeparator());
		separatorlist.get(index).setDoubleBuffered(true);
		separatorlist.get(index).setFocusable(true);
		separatorlist.get(index).setForeground(new Color(128, 0, 0));
		separatorlist.get(index).setBackground(new Color(255, 255, 217));
		separatorlist.get(index).setBounds(10, newHeight + 120, 690, 5);
		add(separatorlist.get(index));
		
		itemImage.add(new JLabel(""));
		itemImage.get(index).setBounds(20, newHeight, 110, 110);
		itemImage.get(index).setIcon(Main.resizeIcon(Main.class.getResource(Main.item.get(itemNo).getImageFileURL()), itemImage.get(index).getWidth(), itemImage.get(index).getHeight()));
		add(itemImage.get(index));
		
		itemName.add(new JLabel(Main.item.get(itemNo).getItemName()));
		itemName.get(index).setBounds(150, newHeight + 25, 400, 30);
		itemName.get(index).setHorizontalAlignment(SwingConstants.LEFT);
		itemName.get(index).setFont(new Font("SansSerif", Font.BOLD, 18));
		add(itemName.get(index));
		
		itemPrice.add(new JLabel("RM" + Main.item.get(itemNo).getPrice()));
		itemPrice.get(index).setForeground(new Color(115, 115, 115));
		itemPrice.get(index).setBounds(150, newHeight + 55, 100, 30);
		itemPrice.get(index).setHorizontalAlignment(SwingConstants.LEFT);
		itemPrice.get(index).setFont(new Font("SansSerif", Font.BOLD, 18));
		add(itemPrice.get(index));
		
		quantity.add(new JLabel(String.valueOf(Main.item.get(itemNo).getQuantity())));
		quantity.get(index).setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/multiply-sign.png"), 15, 15));
		quantity.get(index).setIconTextGap(10);
		quantity.get(index).setForeground(new Color(115, 115, 115));
		quantity.get(index).setBounds(570, newHeight + 35, 100, 40);
		quantity.get(index).setHorizontalAlignment(SwingConstants.CENTER);
		quantity.get(index).setFont(new Font("SansSerif", Font.BOLD, 22));
		add(quantity.get(index));
		
		newHeight += 130;
		index++;
	}
	
	public void showEmptyCart() { //if cart is empty (no item added)
		JLabel img = new JLabel("");
		img.setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/emptyCartImg.png"), 250, 250));
		img.setBounds(230, 30, 250, 250);
		add(img);
		
		JLabel emptyCartText = new JLabel("Your Shopping Cart is empty!");
		emptyCartText.setBounds(30, 250, 650, 20);
		emptyCartText.setHorizontalAlignment(SwingConstants.CENTER);
		emptyCartText.setFont(new Font("SansSerif", Font.BOLD, 18));
		add(emptyCartText);
		
		JLabel addItemText = new JLabel("Add items to continue shopping.");
		addItemText.setBounds(30, 270, 650, 20);
		addItemText.setHorizontalAlignment(SwingConstants.CENTER);
		addItemText.setFont(new Font("SansSerif", Font.BOLD, 18));
		add(addItemText);
	}
	
	public void addPaymentComponent(double price, int orderQuantity, JLabel confirmOrderButton) { //add component for resit
		
		if(newHeight == 265) {
			setBounds(getX(), getY(), getWidth(), getHeight() + 130);
		}
		else if(newHeight >= 395){
			setBounds(getX(), getY(), getWidth(), getHeight() + 260);
		}
		
		JLabel name = new JLabel("Name: " + Main.user.getUsername());
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setForeground(new Color(115, 115, 115));
		name.setFont(new Font("SansSerif", Font.BOLD, 16));
		name.setBounds(30, newHeight + 10, 650, 20);
		add(name);
		
		JLabel phoneNo = new JLabel("Phone Number: " + Main.user.getPhoneNo());
		phoneNo.setHorizontalAlignment(SwingConstants.CENTER);
		phoneNo.setForeground(new Color(115, 115, 115));
		phoneNo.setFont(new Font("SansSerif", Font.BOLD, 16));
		phoneNo.setBounds(30, newHeight + 30, 650, 20);
		add(phoneNo);
		
		JLabel address = new JLabel("Delivery Address: " + Main.user.getAddress());
		address.setHorizontalAlignment(SwingConstants.CENTER);
		address.setForeground(new Color(115, 115, 115));
		address.setFont(new Font("SansSerif", Font.BOLD, 16));
		address.setBounds(30, newHeight + 50, 650, 20);
		add(address);
		
		JLabel totalPrice = new JLabel(String.format("%-11s" + " :" + "%30s","Total price", "RM"+String.format("%.2f", price)));
		totalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		totalPrice.setForeground(new Color(115, 115, 115));
		totalPrice.setFont(new Font("SansSerif", Font.BOLD, 16));
		totalPrice.setBounds(30, newHeight + 80, 650, 20);
		add(totalPrice);
		
		JLabel gst = new JLabel(String.format("%-11s" + ":" + "%30s","6% GST", "RM"+ String.format("%.2f", price*0.06)));
		gst.setHorizontalAlignment(SwingConstants.CENTER);
		gst.setForeground(new Color(115, 115, 115));
		gst.setFont(new Font("SansSerif", Font.BOLD, 16));
		gst.setBounds(30, newHeight + 100, 650, 20);
		add(gst);
		
		JLabel serviceTax = new JLabel(String.format("%-11s" + ":" + "%30s","Service tax", "RM"+String.format("%.2f", orderQuantity*0.7)));
		serviceTax.setHorizontalAlignment(SwingConstants.CENTER);
		serviceTax.setForeground(new Color(115, 115, 115));
		serviceTax.setFont(new Font("SansSerif", Font.BOLD, 16));
		serviceTax.setBounds(30, newHeight + 120, 650, 20);
		add(serviceTax);
		
		double payment = price*1.06 + orderQuantity*0.7;
		JLabel totalPayment = new JLabel(String.format("%-12s" + ":" + "%23s","Total", "RM"+String.format("%.2f", payment)));
		totalPayment.setHorizontalAlignment(SwingConstants.CENTER);
		totalPayment.setFont(new Font("SansSerif", Font.BOLD, 20));
		totalPayment.setBounds(30, newHeight + 145, 650, 20);
		add(totalPayment);
		
		JLabel paymentMethod = new JLabel("(Payment method cash-on-delivery only)");
		paymentMethod.setHorizontalAlignment(SwingConstants.CENTER);
		paymentMethod.setFont(new Font("SansSerif", Font.PLAIN, 10));
		paymentMethod.setBounds(30, newHeight + 175, 650, 10);
		add(paymentMethod);
		
		Main.user.setPayment(payment);
		confirmOrderButton.setBounds(220, newHeight + 185, 270, 50);
		add(confirmOrderButton);
	}
	
	public void resetComponent() { //reset this panel component(used to refresh the panel)
		itemImage.clear();
		itemName.clear();
		itemPrice.clear();
		separatorlist.clear();
		quantity.clear();
		
		index = 0;
		newHeight = 5;
	};
}

@SuppressWarnings("serial")
class TrackOrderListPanel extends JPanelCustom { //for list of order made in TrackPanel
	private ArrayList<JLabel> dateTimeLabel = new ArrayList<JLabel>();
	private ArrayList<JLabel> dateTime = new ArrayList<JLabel>();
	private ArrayList<JLabel> paymentLabel = new ArrayList<JLabel>();
	private ArrayList<JLabel> payment = new ArrayList<JLabel>();
	private ArrayList<JLabel> statusLabel = new ArrayList<JLabel>();
	private ArrayList<JLabel> currentStatus = new ArrayList<JLabel>();
	private ArrayList<JLabel> img = new ArrayList<JLabel>();
	
	TrackOrderListPanel(JPanel parentPanel, int startingY){
		super(parentPanel, startingY);
	}
	
	public void addComponent(String DateTime, String totalPayment, String status) {
		if(newHeight > getHeight()) {
			setBounds(getX(), getY(), getWidth(), getHeight() + 130);
		}
		
		separatorlist.add(new JSeparator());
		separatorlist.get(index).setDoubleBuffered(true);
		separatorlist.get(index).setFocusable(true);
		separatorlist.get(index).setForeground(new Color(128, 0, 0));
		separatorlist.get(index).setBackground(new Color(255, 255, 217));
		separatorlist.get(index).setBounds(10, newHeight + 120, 690, 5);
		add(separatorlist.get(index));
		
		dateTimeLabel.add(new JLabel("Date and Time:"));
		dateTimeLabel.get(index).setBounds(20, newHeight + 25, 200, 20);
		dateTimeLabel.get(index).setHorizontalAlignment(SwingConstants.LEFT);
		dateTimeLabel.get(index).setFont(new Font("SansSerif", Font.BOLD, 18));
		add(dateTimeLabel.get(index));
		
		dateTime.add(new JLabel(DateTime));
		dateTime.get(index).setForeground(new Color(115, 115, 115));
		dateTime.get(index).setBounds(20, newHeight + 55, 200, 20);
		dateTime.get(index).setHorizontalAlignment(SwingConstants.LEFT);
		dateTime.get(index).setFont(new Font("SansSerif", Font.BOLD, 18));
		add(dateTime.get(index));
		
		paymentLabel.add(new JLabel("Total Payment:"));
		paymentLabel.get(index).setBounds(220, newHeight + 25, 200, 20);
		paymentLabel.get(index).setHorizontalAlignment(SwingConstants.LEFT);
		paymentLabel.get(index).setFont(new Font("SansSerif", Font.BOLD, 18));
		add(paymentLabel.get(index));
		
		payment.add(new JLabel(totalPayment));
		payment.get(index).setForeground(new Color(115, 115, 115));
		payment.get(index).setBounds(220, newHeight + 55, 200, 20);
		payment.get(index).setHorizontalAlignment(SwingConstants.LEFT);
		payment.get(index).setFont(new Font("SansSerif", Font.BOLD, 18));
		add(payment.get(index));
		
		statusLabel.add(new JLabel("Status:"));
		statusLabel.get(index).setBounds(420, newHeight + 25, 140, 20);
		statusLabel.get(index).setHorizontalAlignment(SwingConstants.LEFT);
		statusLabel.get(index).setFont(new Font("SansSerif", Font.BOLD, 18));
		add(statusLabel.get(index));
		
		currentStatus.add(new JLabel(status));
		currentStatus.get(index).setForeground(new Color(115, 115, 115));
		currentStatus.get(index).setBounds(420, newHeight + 55, 140, 20);
		currentStatus.get(index).setHorizontalAlignment(SwingConstants.LEFT);
		currentStatus.get(index).setFont(new Font("SansSerif", Font.BOLD, 18));
		add(currentStatus.get(index));
		
		img.add(new JLabel(""));
		img.get(index).setBounds(580, newHeight + 20, 80, 80);
		if(status.equals("Processing")) {
			img.get(index).setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/processing.png"), 80, 80));
		}
		else if(status.equals("On Delivery")) {
			img.get(index).setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/on-delivery.png"), 80, 80));
		}
		else
			img.get(index).setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/delivered.png"), 80, 80));
		add(img.get(index));
		
		newHeight+=130;
		index++;
	}
	
	public void showEmptyOrder() { //if the user did not made any order yet before
		JLabel img = new JLabel("");
		img.setIcon(Main.resizeIcon(Main.class.getResource("/wcdonaldapplication/emptyCartImg.png"), 250, 250));
		img.setBounds(230, 30, 250, 250);
		add(img);
		
		JLabel emptyCartText = new JLabel("Your order is empty!");
		emptyCartText.setBounds(30, 250, 650, 20);
		emptyCartText.setHorizontalAlignment(SwingConstants.CENTER);
		emptyCartText.setFont(new Font("SansSerif", Font.BOLD, 18));
		add(emptyCartText);
		
		JLabel addItemText = new JLabel("Check out items to order.");
		addItemText.setBounds(30, 270, 650, 20);
		addItemText.setHorizontalAlignment(SwingConstants.CENTER);
		addItemText.setFont(new Font("SansSerif", Font.BOLD, 18));
		add(addItemText);
	}
	
	public void resetComponent() { //reset this panel component(used to refresh the panel)
		dateTimeLabel.clear();
		dateTime.clear();
		paymentLabel.clear();
		payment.clear();
		statusLabel.clear();
		currentStatus.clear();
		img.clear();
		
		index = 0;
		newHeight = 5;
	}
}
