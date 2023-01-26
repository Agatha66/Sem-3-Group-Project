package wcdonaldapplication;

public class UserData { //store user data temporarily
	private String username;
	private String emailAddress;
	private String address;
	private String phoneNo;
	private double payment;
	
	public UserData() {
		username = "Unknown";
		emailAddress = "Unknown";
		address = "Unknown";
		phoneNo = "Unknown";
	}
	
	public UserData(String username, String emailAddress, String phoneNo, String address) {
		 setUsername(username);
		 setEmailAddress(emailAddress);
		 setAddress(address);
		 setPhoneNo(phoneNo);
	}
	
	public String getUsername() {
		return username;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public String getAddress() {
		return address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
}
