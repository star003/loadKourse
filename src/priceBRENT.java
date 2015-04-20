import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/////////////////////////////////////////////////////////////////////////////////////////////////////////
//	описание:
//		получение данных о цене на нефть

public class priceBRENT {
	static ArrayList<String> item = new ArrayList<String>();
	static ArrayList<String> itemVal = new ArrayList<String>();
	final String url = "http://www.finam.ru/";
	static String indicator;
	static String indicatorValue;
	static String currTime;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	priceBRENT() throws IOException {
		Document doc  = Jsoup.connect(url).get();
		Elements metaElements = doc.select("span.usd.sm.pl05");
		for (Element x:metaElements) {
			itemVal.add(x.text());
		}
		Elements metaElementsName = doc.select("td.fst-col");
		
		for (Element x:metaElementsName) {
			item.add(x.text());
		}
		currTime = currTime();
	}//priceBRENT() throws IOException
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	priceBRENT(String indicator) throws IOException ,SocketTimeoutException {
		priceBRENT.indicatorValue = indicator;
		Document doc  = Jsoup.connect(url).get();
		Elements metaElements = doc.select("span.usd.sm.pl05");
		for (Element x:metaElements) {
			itemVal.add(x.text());
		}
		int i = 0;
		Elements metaElementsName = doc.select("td.fst-col");
		for (Element x:metaElementsName) {
			if (x.text().indexOf(indicator)>0){
				priceBRENT.indicatorValue = itemVal.get(i);
			}
			item.add(x.text());
			i++;
		}
		currTime = currTime();
	}//priceBRENT(String indicator) throws IOException
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String currTime() {
		Calendar currentTime = Calendar.getInstance();
	    return String.valueOf(currentTime.get(1))+"-"
	    		+ (currentTime.get(2) + 1 >= 10 ? String.valueOf(currentTime.get(2) + 1) : "0"+String.valueOf(currentTime.get(2) + 1)) +"-"
	    		+(currentTime.get(5) >=10 ? String.valueOf(currentTime.get(5))  : "0"+String.valueOf(currentTime.get(5)))+" "
	    		+((currentTime.get(11)) >=10 ? String.valueOf(currentTime.get(11))  : "0"+String.valueOf(currentTime.get(11)))+":"
	    		+((currentTime.get(12)) >=10 ? String.valueOf(currentTime.get(12))  : "0"+String.valueOf(currentTime.get(12)))+":"
	    		+((currentTime.get(13)) >=10 ? String.valueOf(currentTime.get(13))  : "0"+String.valueOf(currentTime.get(13)));
	    				
	}//public static String currTime()
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		
		new priceBRENT("Brent*");
		System.out.println(priceBRENT.indicatorValue);
		System.out.println(currTime());
		function.getDataRes();
	}//public static void main(String[] args) throws IOException

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String getVal() {
		return indicatorValue;
	}//public static String getVal()

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String investing () throws IOException {
		Document doc  = Jsoup.connect("http://www.investing.com/commodities/brent-oil")
				.userAgent("Mozilla")
				.get();
		Elements metaElements = doc.select("span.arial_26.pid-8833-last");
		for (Element x:metaElements) {
			return x.text();
		}
		return "";
	}//public static String investing () throws IOException
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String tinkoff() throws IOException {
		Document doc  = Jsoup.connect("http://www.londonstockexchange.com/exchange/prices-and-markets/stocks/summary/company-summary.html?fourWayKey=US87238U2033USUSDIOBE")
				.userAgent("Mozilla")
				.get();
		Elements metaElements = doc.select("tr.odd");
				
		return metaElements.first().text().split(" ")[0];
	}//public static String tinkoff()
	
}//public class priceBRENT
