import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SmartDoorbell")

public class SmartDoorbell extends HttpServlet {
	private String id;
	private String name;
	private double price;
	private String image;
	private String manufacturer; // Changed retailer to manufacturer
	private double discount; // You might want to handle this separately since the XML doesn't have it
	HashMap<String, String> accessories;

	public SmartDoorbell(String id, String name, double price, String image, String manufacturer, double discount) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.manufacturer = manufacturer;
		this.discount = discount;
		this.accessories = new HashMap<String, String>();
	}

HashMap<String, String> getAccessories() {
		return accessories;
	}

	public SmartDoorbell() {
		
	}

	// Getter and setter methods for all attributes

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	// You might add additional methods here as per your requirements.
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Handle GET request logic here
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Handle POST request logic here
	}
}
