package com.mycompany.colegio_amigos_de_don_bosco;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

public class CLibro {
    public void InsertarLibro(JTextPane titulo, JTextPane autor, JTextPane editorial, JTextPane numpag, JTextPane year, JTextPane categoria){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadLibro objetoEntidadLibro = new EntidadLibro();
        
        String consulta = "insert into libro (cdidentificacion, titulo, autor, editorial, numpag, year, categoria) values (?,?,?,?,?,?,?);";
        
        Random r = new Random();
        int max=99999,min=10000;
        
        int number = r.nextInt(max - min + 1) + min;
        
        String codLIB = "LIB" + number;
        
        try{
            objetoEntidadLibro.setCodigo(codLIB);
            objetoEntidadLibro.setTitulo(titulo.getText());
            objetoEntidadLibro.setAutor(autor.getText());
            objetoEntidadLibro.setEditorial(editorial.getText());
            objetoEntidadLibro.setNumpag(numpag.getText());
            objetoEntidadLibro.setYear(year.getText());
            objetoEntidadLibro.setCategoria(categoria.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, objetoEntidadLibro.getCodigo());
            cs.setString(2, objetoEntidadLibro.getTitulo());
            cs.setString(3, objetoEntidadLibro.getAutor());
            cs.setString(4, objetoEntidadLibro.getEditorial());
            cs.setString(5, objetoEntidadLibro.getNumpag());
            cs.setString(6, objetoEntidadLibro.getYear());
            cs.setString(7, objetoEntidadLibro.getCategoria());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se guardaron los cambios, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void VerLibro(JTable TablaLibro){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadLibro objetoEntidadLibro = new EntidadLibro();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("id");
        modelo.addColumn("cdidentificacion");
        modelo.addColumn("titulo");
        modelo.addColumn("autor");
        modelo.addColumn("editorial");
        modelo.addColumn("numpag");
        modelo.addColumn("year");
        modelo.addColumn("categoria");
        
        TablaLibro.setModel(modelo);
        
        
        
        String consulta = "select libro.id, libro.cdidentificacion, libro.titulo, libro.autor, libro.editorial, libro.numpag, libro.year, libro.categoria from libro;";
        
        try{
            Statement st = objetoConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(consulta);
            
            while(rs.next()){
                objetoEntidadLibro.setId(rs.getInt("id"));
                objetoEntidadLibro.setCodigo(rs.getString("cdidentificacion"));
                objetoEntidadLibro.setTitulo(rs.getString("titulo"));
                objetoEntidadLibro.setAutor(rs.getString("autor"));
                objetoEntidadLibro.setEditorial(rs.getString("editorial"));
                objetoEntidadLibro.setNumpag(rs.getString("numpag"));
                objetoEntidadLibro.setYear(rs.getString("year"));
                objetoEntidadLibro.setCategoria(rs.getString("categoria"));
                
                modelo.addRow(new Object[]{objetoEntidadLibro.getId(), objetoEntidadLibro.getCodigo(), objetoEntidadLibro.getTitulo(), objetoEntidadLibro.getAutor(), objetoEntidadLibro.getEditorial(), objetoEntidadLibro.getNumpag(), objetoEntidadLibro.getYear(), objetoEntidadLibro.getCategoria()});
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void Seleccionar(JTable TablaLibro, JTextPane id, JTextPane codigo, JTextPane titulo, JTextPane autor, JTextPane editorial, JTextPane numpag, JTextPane year, JTextPane categoria){
        int fila = TablaLibro.getSelectedRow();
        
        try{
            if(fila>=0){
                id.setText(TablaLibro.getValueAt(fila, 0).toString());
                codigo.setText(TablaLibro.getValueAt(fila, 1).toString());
                titulo.setText(TablaLibro.getValueAt(fila, 2).toString());
                autor.setText(TablaLibro.getValueAt(fila, 3).toString());
                editorial.setText(TablaLibro.getValueAt(fila, 4).toString());
                numpag.setText(TablaLibro.getValueAt(fila, 5).toString());
                year.setText(TablaLibro.getValueAt(fila, 6).toString());
                categoria.setText(TablaLibro.getValueAt(fila, 7).toString());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al selecciona, error: "+ e.toString());
        }
    }
    
    public void ActualizarLibro(JTextPane id, JTextPane titulo, JTextPane autor, JTextPane editorial, JTextPane numpag, JTextPane year, JTextPane categoria){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadLibro objetoEntidadLibro = new EntidadLibro();
        
        String consulta = "UPDATE libro SET libro.titulo = ?, libro.autor = ?, libro.editorial = ?, libro.numpag = ?, libro.year = ?, libro.categoria = ? WHERE libro.id = ?;";
        
        try{
            objetoEntidadLibro.setId(Integer.parseInt(id.getText()));
            objetoEntidadLibro.setTitulo(titulo.getText());
            objetoEntidadLibro.setAutor(autor.getText());
            objetoEntidadLibro.setEditorial(editorial.getText());
            objetoEntidadLibro.setNumpag(numpag.getText());
            objetoEntidadLibro.setYear(year.getText());
            objetoEntidadLibro.setCategoria(categoria.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, objetoEntidadLibro.getTitulo());
            cs.setString(2, objetoEntidadLibro.getAutor());
            cs.setString(3, objetoEntidadLibro.getEditorial());
            cs.setString(4, objetoEntidadLibro.getNumpag());
            cs.setString(5, objetoEntidadLibro.getYear());
            cs.setString(6, objetoEntidadLibro.getCategoria());
            cs.setInt(7, objetoEntidadLibro.getId());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se modifico correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se modifico los cambios, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void LimpiarCamposLibros(JTextPane id, JTextPane codigo, JTextPane titulo, JTextPane autor, JTextPane editorial, JTextPane numpag, JTextPane year, JTextPane categoria){
        id.setText("");
        codigo.setText("");
        titulo.setText("");
        autor.setText("");
        editorial.setText("");
        numpag.setText("");
        year.setText("");
        categoria.setText("");
    }
    
    public void EliminarLibro(JTextPane id){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadLibro objetoEntidadLibro = new EntidadLibro();
        
        String consulta = "DELETE FROM libro WHERE libro.id = ?;";
        
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
