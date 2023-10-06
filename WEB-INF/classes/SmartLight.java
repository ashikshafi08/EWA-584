import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SmartLight")

public class SmartLight extends HttpServlet {
    private String id;
    private String name;
    private double price;
    private String image;
    private String manufacturer; 
    private double discount; // Retaining the discount field as it was present in the previous classes.

    public SmartLight(String id, String name, double price, String image, String manufacturer, double discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.manufacturer = manufacturer;
        this.discount = discount;
    }

    public SmartLight() {
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

    // Placeholder methods to handle HTTP requests

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle GET request logic here
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle POST request logic here
    }
}
