/**  
 * Filename:    KeywordEntry.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 16, 2012 9:26:49 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 16, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package hibernate.bean;

import hibernate.HibernateSessionFactory;

import org.hibernate.Session;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 16, 2012 9:26:49 PM
 */
public class KeywordEntry {
	private Long id;
	private String keyword;
	private Integer weigh;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getWeigh() {
		return this.weigh;
	}

	public void setWeigh(Integer weigh) {
		this.weigh = weigh;
	}

	public static void main(String[] args) {
		KeywordEntry ke = new KeywordEntry();
		ke.setKeyword("sgsg");
		ke.setWeigh(5);
		try {
			Session session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.persist(ke);
			session.getTransaction().commit();
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
