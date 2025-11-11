package com.mycompany.colegio_amigos_de_don_bosco;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

public class CObra {
    public void InsertarObra(JTextPane titulo, JTextPane autor, JTextPane editorial, JTextPane year, JTextPane genero){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadObra objetoEntidadObra = new EntidadObra();
        
        String consulta = "insert into obra (cdidentificacion, titulo, autor, editorial, year, genero) values (?,?,?,?,?,?);";
        
        Random r = new Random();
        int max=99999,min=10000;
        
        int number = r.nextInt(max - min + 1) + min;
        
        String codOBR = "OBR" + number;
        
        try{
            objetoEntidadObra.setCodigo(codOBR);
            objetoEntidadObra.setTitulo(titulo.getText());
            objetoEntidadObra.setAutor(autor.getText());
            objetoEntidadObra.setEditorial(editorial.getText());
            objetoEntidadObra.setYear(year.getText());
            objetoEntidadObra.setGenero(genero.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, objetoEntidadObra.getCodigo());
            cs.setString(2, objetoEntidadObra.getTitulo());
            cs.setString(3, objetoEntidadObra.getAutor());
            cs.setString(4, objetoEntidadObra.getEditorial());
            cs.setString(5, objetoEntidadObra.getYear());
            cs.setString(6, objetoEntidadObra.getGenero());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se guardaron los cambios, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void VerObra(JTable TablaObra){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadObra objetoEntidadObra = new EntidadObra();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("id");
        modelo.addColumn("cdidentificacion");
        modelo.addColumn("titulo");
        modelo.addColumn("autor");
        modelo.addColumn("editorial");
        modelo.addColumn("year");
        modelo.addColumn("genero");
        
        TablaObra.setModel(modelo);
        
        String consulta = "select obra.id, obra.cdidentificacion, obra.titulo, obra.autor, obra.editorial, obra.year, obra.genero from obra;";
        
        try{
            Statement st = objetoConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(consulta);
            
            while(rs.next()){
                objetoEntidadObra.setId(rs.getInt("id"));
                objetoEntidadObra.setCodigo(rs.getString("cdidentificacion"));
                objetoEntidadObra.setTitulo(rs.getString("titulo"));
                objetoEntidadObra.setAutor(rs.getString("autor"));
                objetoEntidadObra.setEditorial(rs.getString("editorial"));
                objetoEntidadObra.setYear(rs.getString("year"));
                objetoEntidadObra.setGenero(rs.getString("genero"));
                
                modelo.addRow(new Object[]{objetoEntidadObra.getId(), objetoEntidadObra.getCodigo(), objetoEntidadObra.getTitulo(), objetoEntidadObra.getAutor(), objetoEntidadObra.getEditorial(), objetoEntidadObra.getYear(), objetoEntidadObra.getGenero()});
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void Seleccionar(JTable TablaObra, JTextPane id, JTextPane codigo, JTextPane titulo, JTextPane autor, JTextPane editorial, JTextPane year, JTextPane genero){
        int fila = TablaObra.getSelectedRow();
        
        try{
            if(fila>=0){
                id.setText(TablaObra.getValueAt(fila, 0).toString());
                codigo.setText(TablaObra.getValueAt(fila, 1).toString());
                titulo.setText(TablaObra.getValueAt(fila, 2).toString());
                autor.setText(TablaObra.getValueAt(fila, 3).toString());
                editorial.setText(TablaObra.getValueAt(fila, 4).toString());
                year.setText(TablaObra.getValueAt(fila, 5).toString());
                genero.setText(TablaObra.getValueAt(fila, 6).toString());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al selecciona, error: "+ e.toString());
        }
    }
    
    public void ActualizarObra(JTextPane id, JTextPane titulo, JTextPane autor, JTextPane editorial, JTextPane year, JTextPane genero){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadObra objetoEntidadObra = new EntidadObra();
        
        String consulta = "UPDATE obra SET obra.titulo = ?, obra.autor = ?, obra.editorial = ?, obra.year = ?, obra.genero = ? WHERE obra.id = ?;";
        
        try{
            objetoEntidadObra.setId(Integer.parseInt(id.getText()));
            objetoEntidadObra.setTitulo(titulo.getText());
            objetoEntidadObra.setAutor(autor.getText());
            objetoEntidadObra.setEditorial(editorial.getText());
            objetoEntidadObra.setYear(year.getText());
            objetoEntidadObra.setGenero(genero.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, objetoEntidadObra.getTitulo());
            cs.setString(2, objetoEntidadObra.getAutor());
            cs.setString(3, objetoEntidadObra.getEditorial());
            cs.setString(4, objetoEntidadObra.getYear());
            cs.setString(5, objetoEntidadObra.getGenero());
            cs.setInt(6, objetoEntidadObra.getId());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se modifico correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se modifico los cambios, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void LimpiarCamposObra(JTextPane id, JTextPane codigo, JTextPane titulo, JTextPane autor, JTextPane editorial, JTextPane year, JTextPane genero){
        id.setText("");
        codigo.setText("");
        titulo.setText("");
        autor.setText("");
        editorial.setText("");
        year.setText("");
        genero.setText("");
    }
    
    public void EliminarObra(JTextPane id){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadObra objetoEntidadObra = new EntidadObra();
        
        String consulta = "DELETE FROM obra WHERE obra.id = ?;";
        
        try{
            objetoEntidadObra.setId(Integer.parseInt(id.getText()));
                        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, objetoEntidadObra.getId());       
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
