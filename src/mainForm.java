/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class mainForm extends javax.swing.JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ResultSet rs        = null;
    boolean emtyModel   = true;
    static JTable jt    = new JTable();
    static goDaemon x1  = new goDaemon();
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
    static String sqDel = "DELETE FROM arhiv WHERE systime LIKE '% 0%:%\'";
    static String sqDel1 = "DELETE FROM arhiv WHERE systime LIKE '% 23:59%\'";
    
    /////////////////////////////////////////////////////////////////////////////
    
    public mainForm() {
        initComponents();
    }
    
    /////////////////////////////////////////////////////////////////////////////
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                toClose(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                forLoad(evt);
            }
        });

        jPanel1.setAutoscrolls(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Текущий курс");

        jTable1.setModel(function.buildTableModel(function.getResult(sq),false));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(function.buildTableModel(function.getResult(sqOne),false));
        jScrollPane2.setViewportView(jTable2);

        jTable3.setModel(function.buildTableModel(function.getResult(sqDay),false));
        jScrollPane3.setViewportView(jTable3);

        jButton1.setText("получить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("очистить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setAutoscrolls(true);
        jPanel2.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel2.setMaximumSize(new java.awt.Dimension(100, 100));
        jPanel2.setName(""); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(707, 250));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 707, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(jPanel2);

        jButton3.setText("график");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1186, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addGap(18, 18, 18)
                            .addComponent(jButton2)
                            .addGap(18, 18, 18)
                            .addComponent(jButton3))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    /////////////////////////////////////////////////////////////////////////////
    //описание
    //  инициализация таймера
    private void forLoad(java.awt.event.WindowEvent evt) {                         
        
        Timer timer = new Timer(0, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTable();
            //System.out.println("luntik");
        }
        });
        timer.setDelay(30000); // delay for 30 seconds
        timer.start();
    }                        

    /////////////////////////////////////////////////////////////////////////////
    
    @SuppressWarnings("deprecation")
	private void toClose(java.awt.event.WindowEvent evt) {                         
        x1.stop();
    }                        

    /////////////////////////////////////////////////////////////////////////////
    //описание
    //  кнопка обновить
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        refreshTable();
    }                                        

    /////////////////////////////////////////////////////////////////////////////
    //описание:
    //  кнопка "очистить"
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        delTr();
    }                                        

    /////////////////////////////////////////////////////////////////////////////
    //описание:
    //  кнопка График
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        refreshGr();
    }                                        

    /////////////////////////////////////////////////////////////////////////////
    //описание:
    //  обновляет таблицы на форме
    void refreshTable() {
        jTable1.setModel(function.buildTableModel(function.getResult(sq),false));
        jTable2.setModel(function.buildTableModel(function.getResult(sqOne),false));
        jTable3.setModel(function.buildTableModel(function.getResult(sqDay),false));
        //refreshGr();
    }//void refreshTable()
    
    /////////////////////////////////////////////////////////////////////////////
    //описание:
    //  обновляет график на форме
    void refreshGr() {
       //jPanel2.remove(this);
       //jPanel2 = new javax.swing.JPanel();
       DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            ResultSet r =  function.getResult("SELECT * FROM (SELECT last,SYSTIME FROM arhiv ORDER BY SYSTIME DESC LIMIT 20) ORDER BY SYSTIME;");
            double mx = 999999;
            while (r.next()) {
                if (Double.valueOf(r.getString("last"))<mx) {
                    mx = Double.valueOf(r.getString("last"));
                }
            }
            System.out.println("*************");
            System.out.println(mx);
            System.out.println("*************");
            r =  function.getResult("SELECT * FROM (SELECT last,SYSTIME FROM arhiv ORDER BY SYSTIME DESC LIMIT 40) ORDER BY SYSTIME;");
            while (r.next()) {
            	try {
            		double d= Double.valueOf(r.getString("last"))-mx;
            		String st= function.getTime(r.getString("SYSTIME"));
            		dataset.addValue(d, "course", st);
            		System.out.println(d);
            	} catch (NumberFormatException ex) {
            		
            	}
            		
            }
            System.out.println("-------------------");
        } catch (SQLException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
            //createBarChart
        }
        JFreeChart chart = ChartFactory
        .   createBarChart("course", "time", "delta",
                           dataset, 
                           PlotOrientation.VERTICAL,
                           false, false, false);
        CategoryPlot plot = (CategoryPlot)chart.getPlot();
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        jPanel2.setLayout(new java.awt.BorderLayout());
        //jPanel2.removeAll();
        ChartPanel CP = new ChartPanel(chart);
        CP.setSize(jPanel2.getSize());
        jPanel2.add(CP);
        //jPanel2.validate();
        jPanel2.updateUI();
    }//void refreshGr()
    
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
    
    /////////////////////////////////////////////////////////////////////////////
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainForm().setVisible(true);
            }
        });
        //**мое
        x1.started = true;
        x1.start();
    }//public static void main(String args[]) 
    
    /////////////////////////////////////////////////////////////////////////////
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration                   
}//public class mainForm extends javax.swing.JFrame

/////////////////////////////////////////////////////////////////////////////
//описание:
//  в фоне грузит в базу данные 
class goDaemon extends Thread {
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
