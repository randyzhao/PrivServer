/**  
 * Filename:    ParseRequestAction.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 23, 2012 2:17:39 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 23, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package randy;

import hibernate.bean.UserInfo;
import hibernate.dao.UserInfoDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import wordAnalyzer.IWordAnalyzer;
import wordAnalyzer.KAnalyzer;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 23, 2012 2:17:39 PM
 */
public class ParseRequestAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7895269973798024725L;
	private static Logger log = Logger.getLogger(ParseRequestAction.class);
	private IWordAnalyzer analyzer = new KAnalyzer();

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return super.execute();
	}

	public String parse() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		log.info("Into doPost");
		response.setContentType("text/html");
		String username = request.getParameter("un");
		String passwordMd5 = request.getParameter("psmd5");
		String url = request.getParameter("url");
		if (username.equals("") || passwordMd5.equals("") || url.equals("")) {
			this.responseFailure(response);
			return ERROR;
		}
		if (this.authenticate(username, passwordMd5) == false) {
			this.responseFailure(response);
			return ERROR;
		}
		this.handleRequest(username, url);
		return NONE;
	}

	private void responseFailure(HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		out.println("result=failured");
		out.flush();
		out.close();
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
	 * handle parse request
	 * 
	 * @param username
	 * @param password
	 * @param url
	 * @author Hongze Zhao
	 * @throws IOException
	 */
	private void handleRequest(String username, String url) {
		try {
			String content = this.getHttpContent(url);
			List<String> words = this.analyzer.analyze(content);
			UserInfoDao userInfoDao = new UserInfoDao();
			UserInfo user = userInfoDao.getUserInfoByName(username);
			if (user == null) {
				return;
			}
			for (int i = 0; i < words.size(); i++) {
				user.addKeyword(words.get(i));
			}
			userInfoDao.persistAllKeywordEntry(user);
			userInfoDao.persist(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

	private String getHttpContent(String url) throws IOException {
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
}
