/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package nenlineaBackend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Emilio
 */
public class Conector {
    
    public Connection conector;

    public Conector() {
    }
    
    
    public Conector(Connection conector) {
        this.conector = conector;
    }
    
    
    public void Conectar() throws SQLException{
        String cadena = "jdbc:postgresql://localhost:5432/nenlinea";
             String user ="postgres";
             String pass = "12345";
             
             
              try {
                  Class.forName("org.postgresql.Driver");
                  Connection conex = DriverManager.getConnection(cadena,user,pass);
                  java.sql.Statement st = conex.createStatement();
                  
                  Statement consulta = (Statement) conex.createStatement();
                //consulta.executeUpdate("insert into juego values('{" + name +"}')");
                
                  String sql =
                      "SELECT * FROM juego ";
                  ResultSet result = st.executeQuery(sql);
                  while(result.next()) {
                      String usuario = result.getString("tablero");
                      
                      System.out.println("User: "+usuario);
                  }
                  result.close();
                  st.close();
                  conex.close();
              } catch(Exception exc) {
                  System.out.println("Errorx:"+exc.getMessage());
              }
    }
        
        
    public Connection getConexion(){
        return this.conector;
               
    }
    public void insertarBD(String id, String j1, String j2, String dato) throws SQLException{
        System.out.println(" INSERTAR_DB");
        String cadena = "jdbc:postgresql://localhost:5432/nenlinea";
        String user ="postgres";
        String pass = "12345";
        
        Connection conex = DriverManager.getConnection(cadena,user,pass);
        
        Statement consulta = (Statement) conex.createStatement();
        consulta.executeUpdate("insert into juego values('"+id+"', '"+j1+"', '"+j2+"', '"+dato+"')");

    }
    
    
    public String consultarDB(String id) throws SQLException, ClassNotFoundException{
        
        System.out.println("CONSULTAR DB");
        String cadena = "jdbc:postgresql://localhost:5432/nenlinea";
        String user ="postgres";
        String pass = "12345";
        
        String juego="";

        try (Connection conex = DriverManager.getConnection(cadena,user,pass); java.sql.Statement st = conex.createStatement()) {
            
            Statement consulta = (Statement) conex.createStatement();
            
            String sql ="SELECT * FROM juego WHERE id='"+id+"'";
            try (ResultSet result = st.executeQuery(sql)) {
                while(result.next()) {
                    String usuario = result.getString("tablero");
                    
                    System.out.println("User: "+usuario);
                    return usuario;
                }
            }
        }
        return juego;
    
    }
    
    
    
    public static String viewTable() throws SQLException {
        
        System.out.println(" VIEW ROWS");
        String cadena = "jdbc:postgresql://localhost:5432/nenlinea";
        String user ="postgres";
        String pass = "12345";

        Connection con = DriverManager.getConnection(cadena,user,pass);
        String query = "select tablero from juego WHERE id='1'";

        try (Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String resultado = rs.getString("tablero");

                System.out.println(resultado);
                return resultado;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return cadena;
    }
    
    
    
}
