/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manager;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.prashant.resources.query;
import com.prashant.stocks.Home;
import com.prashant.stocks.SPLASH;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Prashant
 */
public class STOCKMANAGER {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SPLASH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SPLASH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SPLASH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SPLASH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SPLASH splash = new SPLASH();
        splash.setVisible(true);
        File f = new File("Data/DB.accdb");

        if (!(f.exists() && f.isFile())) {
            new File("Data").mkdir();
            try {
                Database db = DatabaseBuilder.create(Database.FileFormat.V2010, new File(f.getAbsolutePath()));
            } catch (IOException ioe) {
                ioe.printStackTrace(System.err);
            }
            query.runCUD("CREATE  TABLE `Products`(`PID` VARCHAR(45) UNIQUE ,`Name` VARCHAR(75) ,`Items` INT ,`Price` INT NULL ,`TotalWorth` INT NULL ,`LastUpdated` VARCHAR(50) NULL ,`Description` VARCHAR(1200) NULL ,`Image` VARCHAR(100))");
        } else {
            query.runR("SELECT * FROM Products");
        }

        new Home().setVisible(true);
        splash.setVisible(false);
    }

}
