package com.mycompany.colegio_amigos_de_don_bosco;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

public class CRevistas {
    public void InsertarRevista(JTextPane titulo, JTextPane editorial, JTextPane edicion, JTextPane year, JTextPane periocidad){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadRevistas objetoEntidadRevista = new EntidadRevistas();
        
        String consulta = "insert into revista (cdidentificacion, titulo, editorial, edicion, year, periocidad) values (?,?,?,?,?,?);";
        
        Random r = new Random();
        int max=99999,min=10000;
        
        int number = r.nextInt(max - min + 1) + min;
        
        String codLIB = "REV" + number;
        
        try{
            objetoEntidadRevista.setCodigo(codLIB);
            objetoEntidadRevista.setTitulo(titulo.getText());
            objetoEntidadRevista.setEditorial(editorial.getText());
            objetoEntidadRevista.setEdicion(edicion.getText());
            objetoEntidadRevista.setYear(year.getText());
            objetoEntidadRevista.setPeriocidad(periocidad.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, objetoEntidadRevista.getCodigo());
            cs.setString(2, objetoEntidadRevista.getTitulo());
            cs.setString(3, objetoEntidadRevista.getEditorial());
            cs.setString(4, objetoEntidadRevista.getEdicion());
            cs.setString(5, objetoEntidadRevista.getYear());
            cs.setString(6, objetoEntidadRevista.getPeriocidad());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se guardaron los cambios, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void VerRevista(JTable TablaRevista){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadRevistas objetoEntidadRevistas = new EntidadRevistas();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("id");
        modelo.addColumn("cdidentificacion");
        modelo.addColumn("titulo");
        modelo.addColumn("editorial");
        modelo.addColumn("edicion");
        modelo.addColumn("year");
        modelo.addColumn("periocidad");
        
        TablaRevista.setModel(modelo);
        
        String consulta = "select revista.id, revista.cdidentificacion, revista.titulo, revista.editorial, revista.edicion, revista.year, revista.periocidad from revista;";
        
        try{
            Statement st = objetoConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(consulta);
            
            while(rs.next()){
                objetoEntidadRevistas.setId(rs.getInt("id"));
                objetoEntidadRevistas.setCodigo(rs.getString("cdidentificacion"));
                objetoEntidadRevistas.setTitulo(rs.getString("titulo"));
                objetoEntidadRevistas.setEditorial(rs.getString("editorial"));
                objetoEntidadRevistas.setEdicion(rs.getString("edicion"));
                objetoEntidadRevistas.setYear(rs.getString("year"));
                objetoEntidadRevistas.setPeriocidad(rs.getString("periocidad"));
                
                modelo.addRow(new Object[]{objetoEntidadRevistas.getId(), objetoEntidadRevistas.getCodigo(), objetoEntidadRevistas.getTitulo(), objetoEntidadRevistas.getEditorial(), objetoEntidadRevistas.getEdicion(), objetoEntidadRevistas.getYear(), objetoEntidadRevistas.getPeriocidad()});
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void Seleccionar(JTable TablaRevista, JTextPane id, JTextPane codigo, JTextPane titulo, JTextPane editorial, JTextPane edicion, JTextPane year, JTextPane periocidad){
        int fila = TablaRevista.getSelectedRow();
        
        try{
            if(fila>=0){
                id.setText(TablaRevista.getValueAt(fila, 0).toString());
                codigo.setText(TablaRevista.getValueAt(fila, 1).toString());
                titulo.setText(TablaRevista.getValueAt(fila, 2).toString());
                editorial.setText(TablaRevista.getValueAt(fila, 3).toString());
                edicion.setText(TablaRevista.getValueAt(fila, 4).toString());
                year.setText(TablaRevista.getValueAt(fila, 5).toString());
                periocidad.setText(TablaRevista.getValueAt(fila, 6).toString());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al selecciona, error: "+ e.toString());
        }
    }
    
    public void ActualizarRevista(JTextPane id, JTextPane titulo, JTextPane editorial, JTextPane edicion, JTextPane year, JTextPane periocidad){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadRevistas objetoEntidadRevistas = new EntidadRevistas();
        
        String consulta = "UPDATE revista SET revista.titulo = ?, revista.editorial = ?, revista.edicion = ?, revista.year = ?, revista.periocidad = ? WHERE revista.id = ?;";
        
        try{
            objetoEntidadRevistas.setId(Integer.parseInt(id.getText()));
            objetoEntidadRevistas.setTitulo(titulo.getText());
            objetoEntidadRevistas.setEditorial(editorial.getText());
            objetoEntidadRevistas.setEdicion(edicion.getText());
            objetoEntidadRevistas.setYear(year.getText());
            objetoEntidadRevistas.setPeriocidad(periocidad.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, objetoEntidadRevistas.getTitulo());
            cs.setString(2, objetoEntidadRevistas.getEditorial());
            cs.setString(3, objetoEntidadRevistas.getEdicion());
            cs.setString(4, objetoEntidadRevistas.getYear());
            cs.setString(5, objetoEntidadRevistas.getPeriocidad());
            cs.setInt(6, objetoEntidadRevistas.getId());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se modifico correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se modifico los cambios, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void LimpiarCamposRevista(JTextPane id, JTextPane codigo, JTextPane titulo, JTextPane editorial, JTextPane edicion, JTextPane year, JTextPane periocidad){
        id.setText("");
        codigo.setText("");
        titulo.setText("");
        editorial.setText("");
        edicion.setText("");
        year.setText("");
        periocidad.setText("");
    }
    
    public void EliminarLibro(JTextPane id){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadLibro objetoEntidadLibro = new EntidadLibro();
        
        String consulta = "DELETE FROM revista WHERE revista.id = ?;";
        
        try{
            objetoEntidadLibro.setId(Integer.parseInt(id.getText()));
                        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, objetoEntidadLibro.getId());       
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se elimino correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se elimino, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
}
