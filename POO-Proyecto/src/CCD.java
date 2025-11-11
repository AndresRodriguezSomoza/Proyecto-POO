package com.mycompany.colegio_amigos_de_don_bosco;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

public class CCD {
    public void InsertarCd(JTextPane titulo, JTextPane artista, JTextPane discografia, JTextPane year, JTextPane genero){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadCd objetoEntidadCd = new EntidadCd();
        
        String consulta = "insert into cd (cdidentificacion, titulo, artista, discografia, year, genero) values (?,?,?,?,?,?);";
        
        Random r = new Random();
        int max=99999,min=10000;
        
        int number = r.nextInt(max - min + 1) + min;
        
        String codCDA = "CDA" + number;
        
        try{
            objetoEntidadCd.setCodigo(codCDA);
            objetoEntidadCd.setTitulo(titulo.getText());
            objetoEntidadCd.setArtista(artista.getText());
            objetoEntidadCd.setDiscografia(discografia.getText());
            objetoEntidadCd.setYear(year.getText());
            objetoEntidadCd.setGenero(genero.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, objetoEntidadCd.getCodigo());
            cs.setString(2, objetoEntidadCd.getTitulo());
            cs.setString(3, objetoEntidadCd.getArtista());
            cs.setString(4, objetoEntidadCd.getDiscografia());
            cs.setString(5, objetoEntidadCd.getYear());
            cs.setString(6, objetoEntidadCd.getGenero());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se guardaron los cambios, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void VerCd(JTable TablaCd){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadCd objetoEntidadCd = new EntidadCd();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("id");
        modelo.addColumn("cdidentificacion");
        modelo.addColumn("titulo");
        modelo.addColumn("artista");
        modelo.addColumn("discografia");
        modelo.addColumn("year");
        modelo.addColumn("genero");
        
        TablaCd.setModel(modelo);
        
        String consulta = "select cd.id, cd.cdidentificacion, cd.titulo, cd.artista, cd.discografia, cd.year, cd.genero from cd;";
        
        try{
            Statement st = objetoConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(consulta);
            
            while(rs.next()){
                objetoEntidadCd.setId(rs.getInt("id"));
                objetoEntidadCd.setCodigo(rs.getString("cdidentificacion"));
                objetoEntidadCd.setTitulo(rs.getString("titulo"));
                objetoEntidadCd.setArtista(rs.getString("artista"));
                objetoEntidadCd.setDiscografia(rs.getString("discografia"));
                objetoEntidadCd.setYear(rs.getString("year"));
                objetoEntidadCd.setGenero(rs.getString("genero"));
                
                modelo.addRow(new Object[]{objetoEntidadCd.getId(), objetoEntidadCd.getCodigo(), objetoEntidadCd.getTitulo(), objetoEntidadCd.getArtista(), objetoEntidadCd.getDiscografia(), objetoEntidadCd.getYear(), objetoEntidadCd.getGenero()});
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void Seleccionar(JTable TablaCd, JTextPane id, JTextPane codigo, JTextPane titulo, JTextPane artista, JTextPane discografia, JTextPane year, JTextPane genero){
        int fila = TablaCd.getSelectedRow();
        
        try{
            if(fila>=0){
                id.setText(TablaCd.getValueAt(fila, 0).toString());
                codigo.setText(TablaCd.getValueAt(fila, 1).toString());
                titulo.setText(TablaCd.getValueAt(fila, 2).toString());
                artista.setText(TablaCd.getValueAt(fila, 3).toString());
                discografia.setText(TablaCd.getValueAt(fila, 4).toString());
                year.setText(TablaCd.getValueAt(fila, 5).toString());
                genero.setText(TablaCd.getValueAt(fila, 6).toString());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al selecciona, error: "+ e.toString());
        }
    }
    
    public void ActualizarCd(JTextPane id, JTextPane titulo, JTextPane artista, JTextPane discografia, JTextPane year, JTextPane genero){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadCd objetoEntidadCd = new EntidadCd();
        
        String consulta = "UPDATE cd SET cd.titulo = ?, cd.artista = ?, cd.discografia = ?, cd.year = ?, cd.genero = ? WHERE cd.id = ?;";
        
        try{
            objetoEntidadCd.setId(Integer.parseInt(id.getText()));
            objetoEntidadCd.setTitulo(titulo.getText());
            objetoEntidadCd.setArtista(artista.getText());
            objetoEntidadCd.setDiscografia(discografia.getText());
            objetoEntidadCd.setYear(year.getText());
            objetoEntidadCd.setGenero(genero.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, objetoEntidadCd.getTitulo());
            cs.setString(2, objetoEntidadCd.getArtista());
            cs.setString(3, objetoEntidadCd.getDiscografia());
            cs.setString(4, objetoEntidadCd.getYear());
            cs.setString(5, objetoEntidadCd.getGenero());
            cs.setInt(6, objetoEntidadCd.getId());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se modifico correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se modifico los cambios, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void LimpiarCamposCd(JTextPane id, JTextPane codigo, JTextPane titulo, JTextPane artista, JTextPane discografia, JTextPane year, JTextPane genero){
        id.setText("");
        codigo.setText("");
        titulo.setText("");
        artista.setText("");
        discografia.setText("");
        year.setText("");
        genero.setText("");
    }
    
    public void EliminarCd(JTextPane id){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadCd objetoEntidadCd = new EntidadCd();
        
        String consulta = "DELETE FROM cd WHERE cd.id = ?;";
        
        try{
            objetoEntidadCd.setId(Integer.parseInt(id.getText()));
                        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, objetoEntidadCd.getId());       
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
