/**  
 * Filename:    IWordAnalyzer.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 17, 2012 12:05:19 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 17, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package wordAnalyzer;

import java.util.List;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 17, 2012 12:05:19 PM
 */
public interface IWordAnalyzer {
	/**
	 * analyze a document to a list of String
	 * 
	 * @param doc
	 * @return
	 * @author Hongze Zhao
	 */
	List<String> analyze(String doc);
}
