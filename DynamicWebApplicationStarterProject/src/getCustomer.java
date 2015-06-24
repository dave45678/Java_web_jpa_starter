

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import customTools.DBUtil;
import model.DemoCustomer;

/**
 * Servlet implementation class getCustomer
 */
@WebServlet("/getCustomer")
public class getCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public getCustomer() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//print header
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		
		Long customerID;
		if (request.getParameter("cid").isEmpty())
			customerID = 1L;
		else
			customerID = Long.parseLong(request.getParameter("cid"));
		
		
		try
		{
			
			DemoCustomer cust = em.find(DemoCustomer.class, customerID);
			
			//print customerID, name, and credit limit
			//customer name should be clickable to call the servlet to look up the customer details by id
			
			String link = "<a href=\"getCustomerDetails?cid="+ cust.getCustomerId() + "\">" + cust.getCustFirstName() + " " + cust.getCustLastName() + "</a>";
			out.print(link);
			out.println(cust.getCreditLimit());
		} catch (Exception e) {
			out.println(e.getMessage());
		} finally {
			out.close();
		}
	}
}
