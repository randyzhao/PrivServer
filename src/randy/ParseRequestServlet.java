/**  
 * Filename:    ParseRequestServlet.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 16, 2012 6:45:00 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 16, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package randy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 16, 2012 6:45:00 PM
 */
public class ParseRequestServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(ParseRequestServlet.class);

	/**
	 * Constructor of the object.
	 */
	public ParseRequestServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		String username = request.getParameter("un");
		String passwordMd5 = request.getParameter("psmd5");
		String url = request.getParameter("url");
		if (username.equals("") || passwordMd5.equals("") || url.equals("")) {
			this.responseFailure(response);
			return;
		}
		if (this.authenticate(username, passwordMd5) == false) {
			this.responseFailure(response);
			return;
		}
		this.handleRequest(username, url);
		// now the input is right

		// PrintWriter out = response.getWriter();
		// out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		// out.println("<HTML>");
		// out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		// out.println("  <BODY>");
		// out.print("    This is ");
		// out.print(this.getClass());
		// out.println(", using the POST method");
		// out.println("  </BODY>");
		// out.println("</HTML>");
		// out.flush();
		// out.close();
	}

	private void responseFailure(HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		out.println("result=failured");
		out.flush();
		out.close();
	}

	/**
	 * handle parse request
	 * 
	 * @param username
	 * @param password
	 * @param url
	 * @author Hongze Zhao
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private void handleRequest(String username, String url) {
		HttpClient client = new DefaultHttpClient();
		try {
			String content = this.getHttpContent(url);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

	private String getHttpContent(String url) throws ClientProtocolException,
			IOException {
		HttpClient client = new DefaultHttpClient();
		HttpResponse res = client.execute(new HttpGet(url));
		BufferedReader bf = new BufferedReader(new InputStreamReader(res
				.getEntity().getContent()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		log.info("content: " + sb.toString());
		return sb.toString();
	}

	/**
	 * check if the username and password md5 is match
	 * 
	 * @param username
	 * @param passwordMD5
	 * @return
	 * @author Hongze Zhao
	 */
	private boolean authenticate(String username, String passwordMD5) {
		// TODO: add real authentication here
		log.info("User " + username + " is ok!");
		return true;
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

	public static void main(String[] args) {
		ParseRequestServlet servlet = new ParseRequestServlet();
		try {
			servlet.getHttpContent("http://www.renren.com");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
