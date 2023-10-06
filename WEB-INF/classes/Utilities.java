import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")


public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "header.html") {
				result=result+"<div id='nav' style='inherit: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				result = result + "<li style='margin-top: 10px;'><a href='ViewOrder'><span class='glyphicon' style='font-size: initial;'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'style='font-size: initial'>Hello,"+username+"</span></a></li>"
					+ "<li><a href='Account'><span class='glyphicon' style='font-size: initial'>Account</span></a></li>"
					+ "<li><a href='Logout'><span class='glyphicon' style='font-size: initial'>Logout</span></a></li>";
			}
			else
				result = result +"<li><a href='ViewOrder'><span class='glyphicon' style='font-size: initial'>ViewOrder</span></a></li>"+ "<li><a href='Login'><span class='glyphicon' style='font-size: initial'>Login</span></a></li>";
				result = result +"<li><a href='Cart'><span class='glyphicon' style='font-size: initial'>Cart("+CartCount()+")</span></a></li></ul></div></div><div id='page'>";
				pw.print(result);
		} else
		
				pw.print(result);
	}

public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	}

public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{		
				FileInputStream fileInputStream=new FileInputStream(new File(TOMCAT_HOME+"//webapps//assignNew//UserDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				hm= (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{
			}	
		User user = hm.get(username());
		return user;
	}

	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{
				FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"//webapps//assignNew//PaymentDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				orderPayments = (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{
			
			}
			int size=0;
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
					 size=size + 1;
					 
			}
			return size;		
	}
	
/*  CartCount Function gets  the size of User Orders*/
	
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}
	

public void storeProduct(String name, String type, String maker) {
    if (!OrdersHashMap.orders.containsKey(username())) { 
        ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
        OrdersHashMap.orders.put(username(), arr);
    }
    ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
    
    switch(type.toLowerCase()) {
        case "smartdoorlock":
            SmartDoorLock sdl = SaxParserDataStore.smartDoorLocks.get(name);
            OrderItem sdlOrderItem = new OrderItem(sdl.getId(), sdl.getName(), sdl.getPrice(), sdl.getImage(), sdl.getManufacturer());
            orderItems.add(sdlOrderItem);
            break;

        case "smartdoorbell":
            SmartDoorLockList sdb = SaxParserDataStore.smartDoorBells.get(name);
            OrderItem sdbOrderItem = new OrderItem(sdb.getId(), sdb.getName(), sdb.getPrice(), sdb.getImage(), sdb.getManufacturer());
            orderItems.add(sdbOrderItem);
            break;

        case "smartspeaker":
            SmartSpeaker ss = SaxParserDataStore.smartSpeakers.get(name);
            OrderItem ssOrderItem = new OrderItem(ss.getId(), ss.getName(), ss.getPrice(), ss.getImage(), ss.getManufacturer());
            orderItems.add(ssOrderItem);
            break;

        case "smartlight":
            SmartLight sl = SaxParserDataStore.smartLights.get(name);
            OrderItem slOrderItem = new OrderItem(sl.getId(),sl.getName(), sl.getPrice(), sl.getImage(), sl.getManufacturer());
            orderItems.add(slOrderItem);
            break;

        case "smartthermostat":
            SmartThermostat st = SaxParserDataStore.smartThermostats.get(name);
            OrderItem stOrderItem = new OrderItem(st.getId(), st.getName(), st.getPrice(), st.getImage(), st.getManufacturer());
            orderItems.add(stOrderItem);
            break;

        default:
            System.out.println("Invalid type provided!");
            break;
    }
}


	public void storePayment(int orderId,
		String orderName,double orderPrice,String userAddress,String creditCardNo){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			// get the payment details file 
			try
			{
				FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"//webapps//Assignment1//PaymentDetails.txt"));
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				orderPayments = (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{
			
			}
			if(orderPayments==null)
			{
				orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
			}
			// if there exist order id already add it into same list for order id or create a new record with order id
			
			if(!orderPayments.containsKey(orderId)){	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(orderId, arr);
			}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);
		listOrderPayment.add(orderpayment);	
			
			// add order details into file

			try
			{	
				FileOutputStream fileOutputStream = new FileOutputStream(new File(TOMCAT_HOME+"//webapps//assignNew//PaymentDetails.txt"));
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            	objectOutputStream.writeObject(orderPayments);
				objectOutputStream.flush();
				objectOutputStream.close();       
				fileOutputStream.close();
			}
			catch(Exception e)
			{
				System.out.println("inside exception file not written properly");
			}	
	}


	/* getSmartDoorbells Function returns the HashMap with all SmartDoorbells in the store.*/

	public HashMap<String, SmartDoorLockList> getSmartDoorbells(){
		HashMap<String, SmartDoorLockList> hm = new HashMap<String, SmartDoorLockList>();
		hm.putAll(SaxParserDataStore.smartDoorBells);
		return hm;
}

/* getSmartDoorLocks Function returns the HashMap with all SmartDoorLocks in the store.*/

public HashMap<String, SmartDoorLock> getSmartDoorLocks(){
		HashMap<String, SmartDoorLock> hm = new HashMap<String, SmartDoorLock>();
		hm.putAll(SaxParserDataStore.smartDoorLocks);
		return hm;
}

/* getSmartLights Function returns the HashMap with all SmartLights in the store.*/

public HashMap<String, SmartLight> getSmartLights(){
		HashMap<String, SmartLight> hm = new HashMap<String, SmartLight>();
		hm.putAll(SaxParserDataStore.smartLights);
		return hm;
}

/* getSmartSpeakers Function returns the HashMap with all SmartSpeakers in the store.*/

public HashMap<String, SmartSpeaker> getSmartSpeakers(){
		HashMap<String, SmartSpeaker> hm = new HashMap<String, SmartSpeaker>();
		hm.putAll(SaxParserDataStore.smartSpeakers);
		return hm;
}

/* getSmartThermostats Function returns the HashMap with all SmartThermostats in the store.*/

public HashMap<String, SmartThermostat> getSmartThermostats(){
		HashMap<String, SmartThermostat> hm = new HashMap<String, SmartThermostat>();
		hm.putAll(SaxParserDataStore.smartThermostats);
		return hm;
}

/* getProductsSmartDoorbells Function returns the ArrayList of SmartDoorbells in the store.*/

public ArrayList<String> getProductsSmartDoorbells(){
	ArrayList<String> ar = new ArrayList<String>();
	for(Map.Entry<String, SmartDoorLockList> entry : getSmartDoorbells().entrySet()){			
		ar.add(entry.getValue().getName());
	}
	return ar;
}

/* getProductsSmartDoorLocks Function returns the ArrayList of SmartDoorLocks in the store.*/

public ArrayList<String> getProductsSmartDoorLocks(){		
	ArrayList<String> ar = new ArrayList<String>();
	for(Map.Entry<String, SmartDoorLock> entry : getSmartDoorLocks().entrySet()){
		ar.add(entry.getValue().getName());
	}
	return ar;
}

/* getProductsSmartLights Function returns the ArrayList of SmartLights in the store.*/

public ArrayList<String> getProductsSmartLights(){		
	ArrayList<String> ar = new ArrayList<String>();
	for(Map.Entry<String, SmartLight> entry : getSmartLights().entrySet()){
		ar.add(entry.getValue().getName());
	}
	return ar;
}

/* getProductsSmartSpeakers Function returns the ArrayList of SmartSpeakers in the store.*/

public ArrayList<String> getProductsSmartSpeakers(){		
	ArrayList<String> ar = new ArrayList<String>();
	for(Map.Entry<String, SmartSpeaker> entry : getSmartSpeakers().entrySet()){
		ar.add(entry.getValue().getName());
	}
	return ar;
}

/* getProductsSmartThermostats Function returns the ArrayList of SmartThermostats in the store.*/

public ArrayList<String> getProductsSmartThermostats(){		
	ArrayList<String> ar = new ArrayList<String>();
	for(Map.Entry<String, SmartThermostat> entry : getSmartThermostats().entrySet()){
		ar.add(entry.getValue().getName());
	}
	return ar;
}

}