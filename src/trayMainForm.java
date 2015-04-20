
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.Timer;
import javax.swing.JLabel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class trayMainForm extends javax.swing.JFrame {
	private static final long serialVersionUID = 20150330;
	TrayIcon trayIcon;
	SystemTray tray;
	static boolean goReading = false;
	private JTable jtable1;
	private JTable jtable2;
	private JTable jtable3;
	
	ResultSet rs        = null;
    boolean emtyModel   = true;
    static JTable jt    = new JTable();
    static goDaemon1 x1  = new goDaemon1();
    static String sq = "SELECT  high,last,lastchangeprcnt,\n" +
    					"		low,open,systime,\n" +
    					"		valtoday,voltoday\n" +
    					"FROM \n" +
    					"		(SELECT *,COUNT(systime) AS povtor FROM arhiv \n" +
    					"		GROUP BY updatetime \n" +
    					"		ORDER BY systime DESC) \n" +
    					"WHERE povtor =1;";
    static String sqOne = "SELECT HIGH,LAST,\n" +
                "LASTCHANGEPRCNT,LOW,OPEN,\n" +
                "SYSTIME,\n" +
                "VALTODAY,VOLTODAY\n" +
                "FROM arhiv ORDER BY id DESC LIMIT 1;";
    static String sqDay = "SELECT SUBSTR(systime,0,11) AS c,voltoday  \n" +
                "FROM arhiv \n" +
                "GROUP BY c\n" +
                "ORDER BY systime DESC ;";
    static String sqDayRes = "SELECT name,date,val FROM res GROUP BY name ORDER BY DATE DESC;";
    static String sqDel = "DELETE FROM arhiv WHERE systime LIKE '% 0%:%\'";
    static String sqDel1 = "DELETE FROM arhiv WHERE systime LIKE '% 23:59%\'";
    private JTable jtable4;
    
	trayMainForm() {
		super("Динамика курса ММВБ");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();

			Image image = Toolkit.getDefaultToolkit().getImage("rub.png");
			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			};
			PopupMenu popup 		= new PopupMenu();
			MenuItem defaultItem 			= new MenuItem("График");
			defaultItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						plotGraph.main(null);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			popup.add(defaultItem);
			
			defaultItem 			= new MenuItem("ММВБ РОБОТ");
			defaultItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					setExtendedState(JFrame.NORMAL);
				}
			});
			popup.add(defaultItem);
			
			defaultItem 	= new MenuItem("ВЫХОД");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);
			trayIcon = new TrayIcon(image, "Робот", popup);
			trayIcon.setImageAutoSize(true);
		} 
		else {
		}
		
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == ICONIFIED) {
					try {
						tray.add(trayIcon);
						setVisible(false);
					} catch (AWTException ex) {
					}
				}
				if (e.getNewState() == 7) {
					try {
						tray.add(trayIcon);
						setVisible(false);
					} catch (AWTException ex) {
					}
				}
				if (e.getNewState() == MAXIMIZED_BOTH) {
					tray.remove(trayIcon);
					setVisible(true);
				}
				if (e.getNewState() == NORMAL) {
					tray.remove(trayIcon);
					setVisible(true);
				}
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("yap.png"));
		//***
		initComponents();
		setVisible(true);
		setSize(1053, 644);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//trayMainForm()
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void initComponents() {
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            @SuppressWarnings("deprecation")
			public void windowClosing(java.awt.event.WindowEvent evt) {
            	x1.stop();
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                forLoad(evt);
            }
        });
		
		JPanel panel = new JPanel();
		
		JScrollPane scrollPaneHistory = new JScrollPane();
		jtable1 = new JTable();
		jtable1.setBounds(10, 209, 1017, -208);
		jtable1.setModel(function.buildTableModel(function.getResult(sq),false));
		scrollPaneHistory.setViewportView(jtable1);
		
		JScrollPane scrollPaneCurrentState = new JScrollPane();
		jtable2 = new JTable();
		jtable2.setBounds(10, 0, 1, 1);
		jtable2.setModel(function.buildTableModel(function.getResult(sqOne),false));
		scrollPaneCurrentState.setViewportView(jtable2);
		
		JScrollPane scrollPaneHistoryToDay = new JScrollPane();
		jtable3 = new JTable();
		jtable3.setModel(function.buildTableModel(function.getResult(sqDay),false));
		scrollPaneHistoryToDay.setViewportView(jtable3);
		
		
		JScrollPane scrollPaneHistoryDayRes= new JScrollPane();
		jtable4 = new JTable();
		jtable4.setModel(function.buildTableModel(function.getResult(sqDayRes),false));
		scrollPaneHistoryDayRes.setViewportView(jtable4);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 1027, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JButton jButton1 = new JButton("Получить");
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshTable();
			}
		});
		
		JButton jButton2 = new JButton("Очистить");
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delTr();
			}
		});
		
		JButton jButton3 = new JButton("График");
		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					plotGraph.main(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JLabel label = new JLabel("История сессии");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel label_1 = new JLabel("Текущее состояние");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(59)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_1)
						.addComponent(scrollPaneHistory, GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
						.addComponent(scrollPaneHistoryToDay, GroupLayout.PREFERRED_SIZE, 455, GroupLayout.PREFERRED_SIZE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(jButton1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jButton2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jButton3))
						.addComponent(scrollPaneCurrentState, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
						.addComponent(scrollPaneHistoryDayRes, GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneHistory, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneCurrentState, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPaneHistoryDayRes, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPaneHistoryToDay, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(jButton1)
						.addComponent(jButton2)
						.addComponent(jButton3))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		
		
		
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
    
	}//private void initComponents()
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		new trayMainForm();
		x1.started = true;
        x1.start();
	}//public static void main(String[] args)
	
	/////////////////////////////////////////////////////////////////////////////
	//описание:
	//  обновляет таблицы на форме
	void refreshTable()   {
		//function.getDataUSD();
		jtable1.setModel(function.buildTableModel(function.getResult(sq),false));
		jtable2.setModel(function.buildTableModel(function.getResult(sqOne),false));
		jtable3.setModel(function.buildTableModel(function.getResult(sqDay),false));
		jtable4.setModel(function.buildTableModel(function.getResult(sqDayRes),false));
		//refreshGr();
	}//void refreshTable()

	/////////////////////////////////////////////////////////////////////////////
	//описание
	//  инициализация таймера
	private void forLoad(java.awt.event.WindowEvent evt) {                         

		Timer timer = new Timer(0, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refreshTable();
				//System.out.println(priceBRENT.currTime());
			}
		});
		timer.setDelay(30000); // delay for 30 seconds
		timer.start();
	}   //private void forLoad(java.awt.event.WindowEvent evt)                     

	/////////////////////////////////////////////////////////////////////////////
	//описание:
	//  очищает мусор в таблицах
	void delTr() {
		try {
			function.executeSQL(sqDel);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			function.executeSQL(sqDel1);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
		}
	}//void delTr()
}//public class trayMainForm extends JFrame

/////////////////////////////////////////////////////////////////////////////
//описание:
//в фоне грузит в базу данные 
class goDaemon1 extends Thread {
	boolean started;
	public void run() {
		while (true) {
			try {
				function.getDataUSD();
				System.out.println("goDaemon");
			} catch (IOException ex) {
				Logger.getLogger(goDaemon.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ParserConfigurationException ex) {
				Logger.getLogger(goDaemon.class.getName()).log(Level.SEVERE, null, ex);
			} catch (SAXException ex) {
				Logger.getLogger(goDaemon.class.getName()).log(Level.SEVERE, null, ex);
			} catch (SQLException ex) {
				Logger.getLogger(goDaemon.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(goDaemon.class.getName()).log(Level.SEVERE, null, ex);
			}
			try {
				System.out.println("sleep");
				Thread.sleep(300000);
			} catch (InterruptedException ex) {
				Logger.getLogger(goDaemon.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	} //public void run()

	/////////////////////////////////////////////////////////////////////////////

	void beginStart() {
		started = true;
	}//void beginStart()

	/////////////////////////////////////////////////////////////////////////////

	boolean startedTr() {
		return started;
	}//boolean startedTr()

}//class goNmea extends Thread
