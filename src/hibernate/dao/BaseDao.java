/**  
 * Filename:    BaseDao.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 20, 2012 2:09:44 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 20, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package hibernate.dao;

import hibernate.HibernateSessionFactory;

import org.hibernate.Session;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 20, 2012 2:09:44 PM
 */
public abstract class BaseDao {
	private Session session = null;

	protected Session getSession() {
		if (this.session == null) {
			this.session = HibernateSessionFactory.getSession();
			this.session.beginTransaction();
		}
		return this.session;
	}

	public void persist(Object obj) {
		Session s = this.getSession();
		s.beginTransaction();
		s.persist(obj);
		s.getTransaction().commit();
	}

	public void close() {
		if (this.session != null) {
			this.session.close();
			this.session = null;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		if (this.session != null) {
			this.session.close();
		}
		super.finalize();

	}

}
