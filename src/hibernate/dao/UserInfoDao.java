/**  
 * Filename:    UserInfoDao.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 17, 2012 8:21:08 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 17, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package hibernate.dao;

import hibernate.bean.KeywordEntry;
import hibernate.bean.UserInfo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 17, 2012 8:21:08 PM
 */
public class UserInfoDao extends BaseDao {

	private static Logger log = Logger.getLogger(UserInfoDao.class);

	/**
	 * get UserInfo instance by username
	 * 
	 * @param username
	 * @return
	 * @author Hongze Zhao
	 */
	public UserInfo getUserInfoByName(String username) {
		Session s = this.getSession();
		Query q = s.createQuery(this.getUserInfoByNameQuery(username));
		List<UserInfo> users = q.list();
		if (users.size() != 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	private String getUserInfoByNameQuery(String username) {
		StringBuilder sb = new StringBuilder();
		sb.append("from UserInfo where name='");
		sb.append(username);
		sb.append("'");
		return sb.toString();
	}

	public void persistAllKeywordEntry(UserInfo user) {
		KeywordEntryDao dao = new KeywordEntryDao();
		List<KeywordEntry> entrys = user.getKeywords();
		log.info("persist " + entrys.size() + " words");
		try {
			dao.persistAll(entrys);
		} catch (Exception ex) {
			log.info(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		UserInfoDao dao = new UserInfoDao();
		log.info(dao.getUserInfoByNameQuery("test"));
	}

}
