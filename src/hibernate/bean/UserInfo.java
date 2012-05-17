/**  
 * Filename:    UserInfo.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 16, 2012 9:31:05 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 16, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package hibernate.bean;

import hibernate.HibernateSessionFactory;

import java.util.HashSet;

import org.hibernate.Session;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 16, 2012 9:31:05 PM
 */
public class UserInfo {
	private Long id;
	private String name;
	private String pwmd5;
	private HashSet<KeywordEntry> keywords = new HashSet<KeywordEntry>();

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwmd5() {
		return this.pwmd5;
	}

	public void setPwmd5(String pwmd5) {
		this.pwmd5 = pwmd5;
	}

	public HashSet<KeywordEntry> getKeywords() {
		return this.keywords;
	}

	public void setKeywords(HashSet<KeywordEntry> keywords) {
		this.keywords = keywords;
	}

	public static void main(String[] args) {
		UserInfo ui = new UserInfo();
		ui.setName("test");
		ui.getKeywords().add(new KeywordEntry());
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		session.persist(ui);
		session.getTransaction().commit();
		session.close();
	}
}
