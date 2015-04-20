import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;
import javax.swing.JScrollPane;
 
public class plotGraph {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		double mx 		= 999999;
		String currVal 	= "";
		try {
			ResultSet r = function.getResult("SELECT * FROM (SELECT last,SYSTIME,open FROM arhiv WHERE SYSTIME Like '%"+dtTofay()+"%' ORDER BY SYSTIME DESC) ORDER BY SYSTIME LIMIT 1;");
			
			double oldMx 	= 999999;
			while (r.next()) {
				try {
					if (Double.valueOf(r.getString("open")) < mx) {
						oldMx 	=mx;
						mx = Double.valueOf(r.getString("open"));
						
					}
				}
				catch (NumberFormatException e){
					mx =oldMx;
				}
			}
			r = function.getResult("SELECT last,SYSTIME,open FROM arhiv WHERE SYSTIME Like '%"+dtTofay()+"%' ORDER BY SYSTIME DESC LIMIT 1;");
			while (r.next()) {
				currVal 	=r.getString("last");
			}
			
			System.out.println(mx);
			r = function.getResult("SELECT * FROM (SELECT last,SYSTIME FROM arhiv WHERE SYSTIME Like '%"+dtTofay()+"%' ORDER BY SYSTIME) ORDER BY SYSTIME;");
			Calendar currentTime = Calendar.getInstance();
			System.out.println(currentTime.get(11));
			int i = currentTime.get(11) >13 ? 2:1;
			//System.out.println(r.getInt("last"));
			int itr = 0;
			while (r.next()) {
				try {
					if (itr % i == 0 ) {
						double d = Double.valueOf(r.getString("last")) - mx;
						String st = function.getTime(r.getString("SYSTIME"));
						dataset.addValue(d, "ryb/usd", st);
					//	System.out.println(d);
					}	
				} catch (NumberFormatException ex) {

				}
				itr ++;
			}
		} catch (SQLException ex) {
			Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null,ex);
		}
		JFreeChart chart = ChartFactory.createBarChart("course", "time", "delta", dataset, PlotOrientation.VERTICAL, false, false, false);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
		JFrame frame = new JFrame("MinimalStaticChart");
		frame.getContentPane().setLayout(null);
		
		JLabel lblOpen = new JLabel("open: "+String.valueOf(mx));
		lblOpen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOpen.setBounds(10, 14, 142, 14);
		frame.getContentPane().add(lblOpen);
		
		JLabel lblCurr = new JLabel("curr: "+currVal);
		lblCurr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCurr.setBounds(162, 14, 157, 14);
		frame.getContentPane().add(lblCurr);
		
		JLabel lblDelta = new JLabel("Delta: " + String.valueOf(
				-1*(mx-Double.valueOf(currVal))));
		lblDelta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDelta.setBounds(329, 14, 122, 14);
		frame.getContentPane().add(lblDelta);
		
		JLabel lblTinkoff = new JLabel("Tinkoff: "+priceBRENT.tinkoff());
		lblTinkoff.setBounds(461, 16, 201, 14);
		frame.getContentPane().add(lblTinkoff);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 602, 1354, -600);
		
		frame.getContentPane().add(scrollPane);
		ChartPanel chartPanel = new ChartPanel(chart);
		frame.getContentPane().add(chartPanel);
		chartPanel.setBounds(10, 39, 1352, 570);
		//frame.getContentPane().add(chartPanel);
		frame.setSize(1380,647);
		frame.show();
		System.out.println(dtTofay());
	}//public static void main(String[] args)
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		вернет сегодн€шнюю дату дл€ запроса
	static public String dtTofay(){
		Calendar currentTime = Calendar.getInstance();
	    return String.valueOf(currentTime.get(1))+"-"
	    		+ (currentTime.get(2) + 1 >= 10 ? String.valueOf(currentTime.get(2) + 1) : "0"+String.valueOf(currentTime.get(2) + 1)) +"-"
	    		+(currentTime.get(5) >=10 ? String.valueOf(currentTime.get(5))  : "0"+String.valueOf(currentTime.get(5)));
	}//static public String dtTofay()
}//public class plotGraph