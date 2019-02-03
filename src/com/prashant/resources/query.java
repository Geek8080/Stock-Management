/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prashant.resources;

/**
 *
 * @author Prashant
 */
import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ucanaccess.jdbc.*;

public class query {

    public static void runCUD(String query) {
        String driver = "net.ucanaccess.jdbc.UcanccessDriver";
        try {
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + (new File("Data/DB.accdb").getAbsolutePath()));
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            dir.writeFile("SQLLog", query);
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date today = java.util.Calendar.getInstance().getTime();
            String date = df.format(today);
            dir.writeFile("SQLerrLog", date + " : \n\tSQLException : " + ex.getMessage() + "\n\tSQLState : " + ex.getSQLState() + "\n\tVendorError : " + ex.getErrorCode() + "\n\tQuery : " + query + "\n----------------------------------------");
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            dir.writeFile("errLog", "error in RCUD : " + query);
        }
    }
    
    public static ResultSet runR(String query){
        String driver = "net.ucanaccess.jdbc.UcanccessDriver";
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + (new File("Data/DB.accdb").getAbsolutePath()));
            Statement st = conn.createStatement();
            dir.writeFile("SQLLog", query);
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date today = java.util.Calendar.getInstance().getTime();
            String date = df.format(today);
            dir.writeFile("SQLerrLog", date + " : \n\tSQLException : " + ex.getMessage() + "\n\tSQLState : " + ex.getSQLState() + "\n\tVendorError : " + ex.getErrorCode() + "\n\tQuery : " + query + "\n----------------------------------------");
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            dir.writeFile("errLog", "error in RCUD : " + query);
        }
        
        return rs;
    }

}
