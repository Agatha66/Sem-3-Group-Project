package wcdonaldapplication;

public class Menu { //store menu data temporarily
	private String type;
	private String itemName;
	private double price;
	private String imageFileURL;
	private int quantity;
	
	Menu(String type, String itemName, double price, String imageFileURL){
		this.type = type;
		this.itemName = itemName;
		this.price = price;
		this.imageFileURL = imageFileURL;
		quantity = 0;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageFileURL() {
		return imageFileURL;
	}

	public void setImageFileURL(String imageFileURL) {
		this.imageFileURL = imageFileURL;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}