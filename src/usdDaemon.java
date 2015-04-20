/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * читает и пишет курсы в базу данных
 * @author Адм
 */
public class usdDaemon extends Thread {
    public static void startD() throws java.lang.Exception {
    SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //***тут надо стартовать поток обновления****
                while (true) {
                    Thread m = new dt(true);
                    m.start();
                    try {
                        Thread.sleep(600000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

class dt extends Thread {
    boolean started ;
    dt(boolean st) {
        this.started = st;
    }
    public void run() {
        try {
            function.getDataUSD();
        } catch (IOException ex) {
            Logger.getLogger(dt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(dt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(dt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(dt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} //class dt extends Thread
