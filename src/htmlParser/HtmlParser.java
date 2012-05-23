/**  
 * Filename:    HtmlParser.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 23, 2012 10:39:08 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 23, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package htmlParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 23, 2012 10:39:08 PM
 */
public class HtmlParser {
	private static Logger log = Logger.getLogger(HtmlParser.class);

	public static String parseHtml(String path) throws ParserException,
			IOException {
		// StringBean sb = new StringBean();
		// sb.setLinks(false);
		// sb.setReplaceNonBreakingSpaces(true);
		// sb.setCollapse(true);
		// sb.setURL(path); // the HTTP is performed here
		// log.info(sb.getConnection().getContentEncoding());
		// return sb.getStrings();
		URL url = new URL(path);
		HttpURLConnection uc = (HttpURLConnection) url.openConnection();
		String encoding = uc.getContentEncoding();
		log.info("encoding: " + encoding);
		if (encoding == null || encoding.equals("")) {
			encoding = "UTF-8";
		}
		Parser parser = new Parser(path);
		parser.setEncoding(encoding);
		TextExtractingVisitor visitor = new TextExtractingVisitor();
		parser.visitAllNodesWith(visitor);
		String s = visitor.getExtractedText();
		return s;
	}
}
