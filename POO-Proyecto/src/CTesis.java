package com.mycompany.colegio_amigos_de_don_bosco;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

public class CTesis {
    public void InsertarTesis(JTextPane titulo, JTextPane autor, JTextPane director, JTextPane institucion, JTextPane year, JTextPane academico){
        conexiondb objetoConexion = new conexiondb();
        EntidadTesis objetoEntidadTesis = new EntidadTesis();
        
        String consulta = "insert into tesis (cdidentificacion, titulo, autor, director, institucion, year, gradoacademico) values (?,?,?,?,?,?,?);";
        
        Random r = new Random();
        int max=99999,min=10000;
        
        int number = r.nextInt(max - min + 1) + min;
        
        String codTES = "TES" + number;
        
        try{
            objetoEntidadTesis.setCodigo(codTES);
            objetoEntidadTesis.setTitulo(titulo.getText());
            objetoEntidadTesis.setAutor(autor.getText());
            objetoEntidadTesis.setDirector(director.getText());
            objetoEntidadTesis.setInstitucion(institucion.getText());
            objetoEntidadTesis.setYear(year.getText());
            objetoEntidadTesis.setGradoacademico(academico.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, objetoEntidadTesis.getCodigo());
            cs.setString(2, objetoEntidadTesis.getTitulo());
            cs.setString(3, objetoEntidadTesis.getAutor());
            cs.setString(4, objetoEntidadTesis.getDirector());
            cs.setString(5, objetoEntidadTesis.getInstitucion());
            cs.setString(6, objetoEntidadTesis.getYear());
            cs.setString(7, objetoEntidadTesis.getGradoacademico());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se guardaron los cambios, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void VerTesis(JTable TablaTesis){
        conexiondb objetoConexion = new conexiondb();
        EntidadTesis objetoEntidadTesis = new EntidadTesis();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("id");
        modelo.addColumn("cdidentificacion");
        modelo.addColumn("titulo");
        modelo.addColumn("autor");
        modelo.addColumn("director");
        modelo.addColumn("institucion");
        modelo.addColumn("publicado");
        modelo.addColumn("nivel");
        
        TablaTesis.setModel(modelo);
        
        String consulta = "select tesis.id, tesis.cdidentificacion, tesis.titulo, tesis.autor, tesis.director, tesis.institucion, tesis.year, tesis.gradoacademico from tesis;";
        
        try{
            Statement st = objetoConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(consulta);
            
            while(rs.next()){
                objetoEntidadTesis.setId(rs.getInt("id"));
                objetoEntidadTesis.setCodigo(rs.getString("cdidentificacion"));
                objetoEntidadTesis.setTitulo(rs.getString("titulo"));
                objetoEntidadTesis.setAutor(rs.getString("autor"));
                objetoEntidadTesis.setDirector(rs.getString("director"));
                objetoEntidadTesis.setInstitucion(rs.getString("institucion"));
                objetoEntidadTesis.setYear(rs.getString("year"));
                objetoEntidadTesis.setGradoacademico(rs.getString("gradoacademico"));
                
                modelo.addRow(new Object[]{objetoEntidadTesis.getId(), objetoEntidadTesis.getCodigo(), objetoEntidadTesis.getTitulo(), objetoEntidadTesis.getAutor(), objetoEntidadTesis.getDirector(), objetoEntidadTesis.getInstitucion(), objetoEntidadTesis.getYear(), objetoEntidadTesis.getGradoacademico()});
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void Seleccionar(JTable TablaTesis, JTextPane id, JTextPane codigo, JTextPane titulo, JTextPane autor, JTextPane director, JTextPane institucion, JTextPane year, JTextPane academico){
        int fila = TablaTesis.getSelectedRow();
        
        try{
            if(fila>=0){
                id.setText(TablaTesis.getValueAt(fila, 0).toString());
                codigo.setText(TablaTesis.getValueAt(fila, 1).toString());
                titulo.setText(TablaTesis.getValueAt(fila, 2).toString());
                autor.setText(TablaTesis.getValueAt(fila, 3).toString());
                director.setText(TablaTesis.getValueAt(fila, 4).toString());
                institucion.setText(TablaTesis.getValueAt(fila, 5).toString());
                year.setText(TablaTesis.getValueAt(fila, 6).toString());
                academico.setText(TablaTesis.getValueAt(fila, 7).toString());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al selecciona, error: "+ e.toString());
        }
    }
    
    public void ActualizarTesis(JTextPane id, JTextPane titulo, JTextPane autor, JTextPane director, JTextPane institucion, JTextPane year, JTextPane academico){
        conexiondb objetoConexion = new conexiondb();
        EntidadTesis objetoEntidadTesis = new EntidadTesis();
        
        String consulta = "UPDATE tesis SET tesis.titulo = ?, tesis.autor = ?, tesis.director = ?, tesis.institucion = ?, tesis.year = ?, tesis.gradoacademico = ? WHERE tesis.id = ?;";
        
        try{
            objetoEntidadTesis.setId(Integer.parseInt(id.getText()));
            objetoEntidadTesis.setTitulo(titulo.getText());
            objetoEntidadTesis.setAutor(autor.getText());
            objetoEntidadTesis.setDirector(director.getText());
            objetoEntidadTesis.setInstitucion(institucion.getText());
            objetoEntidadTesis.setYear(year.getText());
            objetoEntidadTesis.setGradoacademico(academico.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, objetoEntidadTesis.getTitulo());
            cs.setString(2, objetoEntidadTesis.getAutor());
            cs.setString(3, objetoEntidadTesis.getDirector());
            cs.setString(4, objetoEntidadTesis.getInstitucion());
            cs.setString(5, objetoEntidadTesis.getYear());
            cs.setString(6, objetoEntidadTesis.getGradoacademico());
            cs.setInt(7, objetoEntidadTesis.getId());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se modifico correctamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se modifico los cambios, error: "+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
    public void LimpiarCamposTesis(JTextPane id, JTextPane codigo, JTextPane titulo, JTextPane autor, JTextPane director, JTextPane institucion, JTextPane year, JTextPane academico){
        id.setText("");
        codigo.setText("");
        titulo.setText("");
        autor.setText("");
        director.setText("");
        institucion.setText("");
        year.setText("");
        academico.setText("");
    }
    
    public void EliminarTesis(JTextPane id){
        
        conexiondb objetoConexion = new conexiondb();
        EntidadTesis objetoEntidadTesis = new EntidadTesis();
        
        String consulta = "DELETE FROM tesis WHERE tesis.id = ?;";
        
        try{
            objetoEntidadTesis.setId(Integer.parseInt(id.getText()));
                        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, objetoEntidadTesis.getId());       
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
