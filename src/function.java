/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class function {
	
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static ResultSet getResult(String sql) {
		try {
			/*
			 * универсальная функция , возвращает результат запроса @sql
			 */
			try {
				Class.forName("org.sqlite.JDBC");
			} 
			catch (ClassNotFoundException ex) {
				System.out.println("ошибка инициализации драйвера");
				Logger.getLogger(function.class.getName()).log(Level.SEVERE, null, ex);
			}
			Connection bd;
			try {
				bd = DriverManager.getConnection("jdbc:sqlite:usd.db");
			} catch (SQLException ex) {
				System.out.println("ошибка инициализации базы данных");
				bd = null;
			}
			//ResultSet resultSet = null;
			Statement st;
			try {
				st = bd.createStatement();
			} catch (SQLException ex) {
				st = null;
				System.out.println("ошибка инициализации оператора базы");
			}
			st.setQueryTimeout(60);
			return st.executeQuery( sql );
		} catch (SQLException ex) {
			System.out.println("ошибка выполнения запрока к бд");
			return null;
		}
	}  //static ResultSet getResult(String sql) 

 	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 	static public void executeSQL(String sq) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection bd = DriverManager.getConnection("jdbc:sqlite:usd.db");
        Statement st = bd.createStatement();
        st.setQueryTimeout(60);
        st.execute(sq);
        st.close();
  	}//static public void executeSQL(String sq)
 
 	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 	static void getDataUSD() throws IOException, ParserConfigurationException, SAXException, SQLException, ClassNotFoundException {
        List<String> row            = Arrays.asList("BOARDID", "DECIMALS", "HIGH", "LAST", "LASTCHANGEPRCNT", "LOW", "NUMTRADES", "OPEN", "SECID", "SEQNUM", "SHORTNAME", "SYSTIME", "UPDATETIME", "VALTODAY", "VOLTODAY", "WAPRICE", "WAPTOPREVWAPRICE", "WAPTOPREVWAPRICEPRCNT");
        List<String> key            = new ArrayList<String>();
        URL url                     = new URL("http://www.micex.ru/issrpc/marketdata/currency/selt/daily/short/result_2014_03_20.xml?boardid=CETS&secid=USD000UTSTOM");
        URLConnection conn          = url.openConnection();
        InputStreamReader rd        = new InputStreamReader(conn.getInputStream(), "UTF-8");
        DocumentBuilderFactory dbf  = DocumentBuilderFactory.newInstance();
        DocumentBuilder db          = dbf.newDocumentBuilder();
        Document doc                = db.parse(new InputSource(rd));
        doc.getDocumentElement().normalize();
        NodeList nodeList           = doc.getElementsByTagName("data");
        for (int z = 0; z < nodeList.getLength(); z++) {
            Node node = nodeList.item(z);
            NodeList dataList = node.getChildNodes();
            for (int i1 = 0; i1 < dataList.getLength(); i1++) {
                Node nodeRow = dataList.item(i1);
                if (nodeRow.getNodeName().equals("row")) {
                    System.out.println(nodeRow.getNodeName());
                    NamedNodeMap x = nodeRow.getAttributes();
                    for (int i = 0; i < x.getLength(); i++) {
                        Node rowItem    = x.item(i);
                        String st       = rowItem.getNodeName();
                        String rz       = rowItem.getNodeValue();
                        key.add(row.indexOf(st), rz);
                    }
                }
            }
        }
        /*
         сгенерируем запрос на добавление записи в базу данных
         */
        String rezQw = "INSERT INTO arhiv (";
        for (String et : row) {
            rezQw += et + ",";
        }
        rezQw += ",";
        rezQw = rezQw.replace(",,", ")");
        rezQw += "VALUES(";
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            rezQw += "'" + it.next() + "',";
        }
        rezQw += ",";
        rezQw = rezQw.replace(",,", ")");
        rezQw += ";";
        System.out.println("ok");
        recordData(rezQw);
        getDataRes();
    }//static void getDataUSD()throws IOException, ParserConfigurationException, SAXException, SQLException, ClassNotFoundException 
 
 	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	//	описание:
 	//		цена определенного ресурса
 	static void getDataRes() throws IOException, ClassNotFoundException, SQLException {
 		ArrayList<String> resItem = new ArrayList<String>();
 		resItem.add("Brent*");
 		resItem.add("Золото");
 		for (String j:resItem) {
 			new priceBRENT(j);
 			System.out.println(priceBRENT.getVal());
 			String rezQw = "INSERT INTO res (name,date,val) VALUES('"+j+"','"+priceBRENT.currTime+"','"+priceBRENT.getVal()+"');";
 			recordData(rezQw);
 		}	
 	}//static void getDataRes()
 	
 	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
	static void recordData(String sq) throws SQLException, ClassNotFoundException {
        /*
         запись данных в базу
         */
        Class.forName("org.sqlite.JDBC");
        Connection bd = DriverManager.getConnection("jdbc:sqlite:usd.db");
        try (Statement st = bd.createStatement()) {
            st.setQueryTimeout(60);
            st.execute(sq);
        }
    }//static void recordData(String sq) throws SQLException, ClassNotFoundException
    
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    public static DefaultTableModel buildTableModel(ResultSet rs,boolean emtyModel)  {
		if (emtyModel == true) {
			//**случай , когда нужно получить просто пустую таблицу
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("item");
			columnNames.add("value");
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			return new DefaultTableModel(data, columnNames);
		}
	    ResultSetMetaData metaData;
        try {
            metaData = rs.getMetaData();
        } catch (SQLException ex) {
            metaData =null;
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount;
        try {
            columnCount = metaData.getColumnCount();
        } catch (SQLException ex) {
            columnCount = 0;
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
	    for (int column = 1; column <= columnCount; column++) {
	    	try {
	    		columnNames.add(metaData.getColumnName(column));
            }
	    	catch (SQLException ex) {
	    		columnNames.add(null);
	    		Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
	    }
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        try {
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
	    return new DefaultTableModel(data, columnNames);
	}//public static DefaultTableModel buildTableModel(ResultSet rs,boolean emtyModel)
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    static String getTime(String s){
		String[] x = s.split(" ");
		return x[1]; 
	}//static String getTme(String s)
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}
