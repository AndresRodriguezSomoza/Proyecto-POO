package com.mycompany.colegio_amigos_de_don_bosco;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class conexiondb {
    Connection conn = null;
    
    String usuario = "root";
    String pwd = "";
    String bd = "amigosdedonbosco";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection establecerConexion(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(cadena, usuario, pwd);
            //JOptionPane.showMessageDialog(null, "Conexion exitosa");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Conexion fallida, error:"+ e.toString());
        }
        return conn;
    }
    
    public void cerrarConexion(){
        try{
            if(conn!=null && !conn.isClosed()){
                conn.close();
                //JOptionPane.showMessageDialog(null, "La conexión fue cerrada");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "La conexión no fue cerrada, error:"+ e.toString());
        }
    }
}
