/**  
 * Filename:    RegisterAction.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 16, 2012 11:07:01 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 16, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package randy;

import hibernate.HibernateSessionFactory;
import hibernate.bean.UserInfo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 16, 2012 11:07:01 PM
 */
public class RegisterAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(RegisterAction.class);
	private String username;
	private String password;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * generate md5 code from ori code
	 * 
	 * @param ori
	 * @return
	 * @author Hongze Zhao
	 */
	private String getMD5(String ori) {
		// return DigestUtils.md5Hex(ori);
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		md.reset();
		try {
			md.update(ori.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				sb.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			} else {
				sb.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return sb.toString();
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String submitRegister() throws Exception {
		log.info("user registered" + this.username + " , md5:"
				+ this.getMD5(this.password));
		UserInfo ui = new UserInfo();
		ui.setName(this.username);
		ui.setPwmd5(this.getMD5(this.password));
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		session.persist(ui);
		session.getTransaction().commit();
		session.close();
		return SUCCESS;
	}

}
