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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
	private List<KeywordEntry> keywords = new ArrayList<KeywordEntry>();
	private HashMap<String, KeywordEntry> keywordNameMap;

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

	public List<KeywordEntry> getKeywords() {
		return this.keywords;
	}

	public void setKeywords(List<KeywordEntry> keywords) {
		this.keywords = keywords;
	}

	/**
	 * add one keyword. It the keyword exists, add the weight or just create a
	 * new one
	 * 
	 * @param word
	 * @author Hongze Zhao
	 */
	public void addKeyword(String word) {
		if (this.keywordNameMap == null) {
			this.buildKeywordNameMap();
		}

		KeywordEntry entry = this.keywordNameMap.get(word);
		if (entry == null) {
			entry = new KeywordEntry();
			entry.setKeyword(word);
			entry.setWeigh(1);
			this.keywordNameMap.put(word, entry);
			this.keywords.add(entry);
		} else {
			entry.setWeigh(entry.getWeigh() + 1);
		}

	}

	private void buildKeywordNameMap() {
		this.keywordNameMap = new HashMap<String, KeywordEntry>();
		Iterator<KeywordEntry> key = this.keywords.iterator();
		KeywordEntry entry = null;
		while ((entry = key.next()) != null) {
			this.keywordNameMap.put(entry.getKeyword(), entry);
		}
	}

	public static void main(String[] args) {
		UserInfo ui = new UserInfo();
		ui.setName("test");
		// ui.getKeywords().add(new KeywordEntry());
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		session.persist(ui);
		session.getTransaction().commit();
		session.close();
	}
}
