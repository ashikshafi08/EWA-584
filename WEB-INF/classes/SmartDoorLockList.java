import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class SmartDoorLockList extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String CategoryName = request.getParameter("manufacturer");
        

		/* Checks the DoorBell manufacturer*/

		HashMap<String, SmartDoorLock> hm = new HashMap<String, SmartDoorLock>();
		if(CategoryName==null){
			hm.putAll(SaxParserDataStore.smartDoorLocks);
			name = "";
		}
		else
		{
		   if(CategoryName.equals("Yale"))
		   {
			 for(Map.Entry<String,SmartDoorLock> entry : SaxParserDataStore.smartDoorLocks.entrySet())
			 {
				if(entry.getValue().getManufacturer().equals("Yale"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
			 }
				name = "Yale";
		   }
		   else if(CategoryName.equals("Schlage"))
		    {
			for(Map.Entry<String,SmartDoorLock> entry : SaxParserDataStore.smartDoorLocks.entrySet())
				{
				 if(entry.getValue().getManufacturer().equals("Schlage"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				 name = "Schlage";
			}
			 else if(CategoryName.equals("U-Bolt"))
		    {
			for(Map.Entry<String,SmartDoorLock> entry : SaxParserDataStore.smartDoorLocks.entrySet())
				{
				 if(entry.getValue().getManufacturer().equals("U-Bolt"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				 name = "U-Bolt";
			}


		}


		Utilities utility = new Utilities(request,pw);
		utility.printHtml("header.html");
		utility.printHtml("LeftNavBar.html");
		pw.print("<div id='container'>");
		pw.print("<a style='font-size: 24px;'>"+name+" SmartDoorLock</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, SmartDoorLock> entry : hm.entrySet())
		{
			SmartDoorLock va = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+va.getName()+"</h3>");
			pw.print("<strong>$"+va.getPrice()+"</strong><ul>");
			pw.print("<li id='item'><img src='images/smrtdoorlocks/"+va.getImage()+"' alt='' /></li>");
			
			pw.print("<form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='vas'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form>");
			
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}	
		pw.print("</table></div></div></div>");
   
		utility.printHtml("footer.html");
		
	}
}
