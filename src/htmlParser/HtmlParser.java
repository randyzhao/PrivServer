package htmlParser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.Tag;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.util.ParserException;

public class HtmlParser {

	private static String title;

	public static void main(String[] args) throws Exception {

		String path;

		if (0 >= args.length) {

			path =

			/** 以下是测试地址 * */

			// "http://www.ibm.com/developerworks/cn/webservices/0901_haoxf_humantask/";
			// "http://developers.sun.com.cn/Java/xref_index.html";
			"http://blog.renren.com/share/319985936/13140119392?from=0101010202&ref=hotnewsfeed&sfet=102&fin=11&ff_id=319985936";

			// "http://www.sina.com.cn/";

			// "http://www.ibm.com/";

			// "http://hao861002.iteye.com/blog/301581";

		} else {

			path = args[0];

		}

		/**
		 * 
		 * 构造 URL ，并打开网络链接。
		 * 
		 */

		/** 对该网页进行解析 * */

		String body = parseHTML(path);

		/** 打印解析后的内容 * */

		System.out.println("body=" + body);

	}

	/**
	 * 
	 * 解析网页内容
	 * 
	 * @param uc
	 *            传入一个 HttpURLConnection 链接对象
	 * 
	 * @throws ParserException
	 * @throws IOException
	 * 
	 */

	public static String parseHTML(String path) throws ParserException,
			IOException {

		/** 声明节点 * */
		URL url = new URL(path);

		HttpURLConnection uc = (HttpURLConnection) url.openConnection();

		Node node;

		String stringText;

		StringBuilder body = new StringBuilder();

		/***********************************************************************
		 * 从 head 头获取网页编码格式。该方式取决于服务器是否设置 charSet 值，如果没有，该
		 * 
		 * 方式将无法获取 charSet 值
		 **********************************************************************/

		String contentType = uc.getContentType();

		String charSet = getCharset(contentType);

		Lexer lexer = null;

		if (charSet == null) {

			charSet = "UTF-8";

		}

		try {

			lexer = new Lexer(new Page(uc.getInputStream(), charSet));

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		}

		/** 对网页内容进行解析 * */

		lexer.setNodeFactory(new PrototypicalNodeFactory());

		/** 设置开关，决定网页是否重新解析 * */

		boolean tryAgain = false;

		while (null != (node = lexer.nextNode())) {

			/** 以下是判断节点的类型，并作相应的处理 * */

			if (node instanceof ScriptTag) {

				while (null != (node = lexer.nextNode())) {

					if (node instanceof Tag) {

						Tag tag = (Tag) node;

						if (tag.isEndTag() && "SCRIPT".equals(tag.getTagName())) {

							break;

						}

					}

				}

				if (null == node) {
					break;
				}

			} else if (node instanceof StyleTag) {

				while (null != (node = lexer.nextNode())) {

					if (node instanceof Tag) {

						Tag tag = (Tag) node;

						if (tag.isEndTag()) {
							break;
						}

					}

				}

				if (null == node) {
					break;
				}

			} else if (node instanceof TextNode) {

				stringText = node.toPlainTextString();

				if ("".equals(title)) {

					continue;
				}

				else if (node instanceof TextNode) {

					stringText = node.toPlainTextString();

					if ("".equals(title)) {
						continue;
					}

					stringText = stringText.replaceAll("[ \t\n\f\r 　 ]+", " ")
							.trim().trim();
					if (!"".equals(stringText)) {

						body.append(stringText);

						body.append(" ");

					}

				} else if (node instanceof TagNode) {

					TagNode tagNode = (TagNode) node;

					String name = ((TagNode) node).getTagName();

					if (name.equals("TITLE") && !tagNode.isEndTag()) {

						node = lexer.nextNode();

						stringText = node.toPlainTextString().trim();

						if (!"".equals(stringText)) {

							title = stringText;

						}

					} else if (name.equals("META")) {

						String contentCharSet = tagNode.getAttribute("CONTENT");

						// System.out.println("contentCharset="+contentCharSet);

						int b = contentCharSet.toLowerCase().indexOf("charset");

						if (b > -1) {

							String newCharSet = getCharset(contentCharSet);

							// System.out.println("newCharSet=" + newCharSet);

							if (!charSet.equals(newCharSet)) {

								tryAgain = true;

								charSet = newCharSet;

								// System.out.println("charSet=" + charSet);

								// System.out.println("newCharSet=" +
								// newCharSet);

								break;

							}

						}

					}

				}

			}

			/***********************************************************************
			 * 如果在 Meta 信息中检测到新的字符编码，则需要按照 meta 信息中的编码再次解析网页 。
			 **********************************************************************/

			if (tryAgain) {

				body = new StringBuilder();

				try {

					uc = (HttpURLConnection) uc.getURL().openConnection();

					lexer = new Lexer(new Page(uc.getInputStream(), charSet));

				} catch (Exception e) {

					e.printStackTrace();

				}

				lexer.setNodeFactory(new PrototypicalNodeFactory());

				while (null != (node = lexer.nextNode())) {

					if (node instanceof TextNode) {

						stringText = node.toPlainTextString();

						if ("".equals(title)) {
							continue;
						}

						stringText = stringText.replaceAll("[ \t\n\f\r 　 ]+",
								" ").trim();

						if (!"".equals(stringText)) {

							body.append(stringText);

							body.append(" ");

						}

					}

				}

			}
		}
		return body.toString();
	}

	/**
	 * 
	 * 找出最终的网页编码
	 * 
	 * @param name
	 *            经过 getCharset 方法处理后 meta 标签的值
	 * 
	 * @param _default
	 *            默认的编码集
	 * 
	 * @return
	 * 
	 */

	public static String findCharset(String name, String _default) {

		String ret;

		try {

			Class<java.nio.charset.Charset> cls;

			Method method;

			Object object;

			cls = java.nio.charset.Charset.class;

			method = cls.getMethod("forName", new Class[] { String.class });

			object = method.invoke(null, new Object[] { name });

			method = cls.getMethod("name", new Class[] {});

			object = method.invoke(object, new Object[] {});

			ret = (String) object;

		} catch (NoSuchMethodException nsme) {

			ret = name;

		} catch (IllegalAccessException ia) {

			ret = name;

		} catch (InvocationTargetException ita) {

			ret = _default;

			System.out

			.println("unable to determine cannonical charset name for "

			+ name + " - using " + _default);

		}

		return (ret);

	}

	/**
	 * 
	 * 处理 meta 中的内容，并调用 findCharset() 方法获取编码值
	 * 
	 * @param content
	 *            Meta 中的内容
	 * 
	 * @return
	 * 
	 */

	public static String getCharset(String content) {

		final String CHARSET_STRING = "charset";

		int index;

		String ret;

		ret = null;

		if (null != content) {

			index = content.indexOf(CHARSET_STRING);

			if (index != -1) {

				content = content.substring(index + CHARSET_STRING.length())

				.trim();

				if (content.startsWith("=")) {

					content = content.substring(1).trim();

					index = content.indexOf(";");

					if (index != -1) {
						content = content.substring(0, index);
					}

					if (content.startsWith("\"") && content.endsWith("\"")

					&& (1 < content.length())) {
						content = content.substring(1, content.length() - 1);
					}

					if (content.startsWith("'") && content.endsWith("'")

					&& (1 < content.length())) {
						content = content.substring(1, content.length() - 1);
					}

					ret = findCharset(content, ret);

				}

			}

		}

		return (ret);

	}

}