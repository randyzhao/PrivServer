/**  
 * Filename:    KAnalyzer.java  
 * Description:   
 * Copyright:   Copyright (c)2011 
 * Company:    company 
 * @author:     Hongze Zhao 
 * @version:    1.0  
 * Create at:   May 17, 2012 7:22:21 PM  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * May 17, 2012    Hongze Zhao   1.0         1.0 Version  
 */
package wordAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * Description:
 * 
 * @author Hongze Zhao Create At : May 17, 2012 7:22:21 PM
 */
public class KAnalyzer implements IWordAnalyzer {
	private static Logger log = Logger.getLogger(KAnalyzer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see wordAnalyzer.IWordAnalyzer#analyze(java.lang.String)
	 */
	@Override
	public List<String> analyze(String doc) {
		IKSegmenter seg = new IKSegmenter(new StringReader(doc), true);
		List<String> output = new ArrayList<String>();
		Lexeme lex = null;
		try {
			while ((lex = seg.next()) != null) {
				output.add(lex.getLexemeText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	public static void main(String[] args) {
		String text = "这只是一个测试。";
		IKSegmenter seg = new IKSegmenter(new StringReader(text), true);
		Lexeme lex = null;
		try {
			while ((lex = seg.next()) != null) {
				log.info(lex.getLexemeText());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Analyzer analyzer = new IKAnalyzer();
		// Directory dir = null;
		// IndexWriter iwriter = null;
		// IndexReader ireader = null;
		// IndexSearcher isearcher = null;
		// dir = new RAMDirectory();
		//
		// IndexWriterConfig iwcfg = new IndexWriterConfig(Version.LUCENE_34,
		// analyzer);
		// iwcfg.setOpenMode(OpenMode.CREATE_OR_APPEND);
		//
		// Document doc = new Document();
		// doc.add(new Field("ID", "10000", Field.Store.YES,
		// Field.Index.NOT_ANALYZED));
		// doc.add(new Field(fieldName, text, Field.Store.YES,
		// Field.Index.ANALYZED));
		// iwriter.addDocument(doc);
		// iwriter.close();
		// ireader = IndexReader.open(dir);
		// isearcher = new IndexSearcher(ireader);
		// String keyword = "中文"
	}
}
