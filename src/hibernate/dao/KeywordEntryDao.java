/**  
 * Filename:    KeywordEntryDao.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 20, 2012 2:13:08 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 20, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package hibernate.dao;

import hibernate.bean.KeywordEntry;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 20, 2012 2:13:08 PM
 */
public class KeywordEntryDao extends BaseDao {
	private static Logger log = Logger.getLogger(KeywordEntryDao.class);

	public void persistAll(List<KeywordEntry> objs) {
		Session s = this.getSession();
		s.beginTransaction();
		Iterator<KeywordEntry> obji = objs.iterator();
		KeywordEntry obj = null;
		int k = 0;
		while ((obj = obji.next()) != null) {
			k++;
			if (k > 3) {
				break;
			}
			s.persist(obj);
		}
		s.getTransaction().commit();
	}
}
