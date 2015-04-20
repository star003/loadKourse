import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	описание:
//		анализ и визуализация данных

public class annalizRes extends javax.swing.JFrame {
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = -4496562305890564706L;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public static void main(String[] args) throws SQLException, IOException {
		//getFromDB();
		System.out.println(priceBRENT.investing());
	} //public static void main(String[] args)
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	static void getFromDB() throws SQLException {
		String indicator = "Brent*";
		String sqDayRes = "SELECT val FROM res WHERE name = '"+indicator+"' ORDER BY date DESC;";
		ResultSet rs = function.getResult(sqDayRes);
		int columnCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				System.out.print(rs.getMetaData().getColumnName(columnIndex));
				System.out.print(" = ");
                System.out.println(rs.getObject(columnIndex));
            }
        }
	}//static void getFromDB()	
	
} //public class annalizRes
