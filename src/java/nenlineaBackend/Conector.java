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
    Connection conector;
    
    public Conector(){
        this.conector=conector;
    }
    
    public void Conectar() throws SQLException{
        String cadena = "jdbc:postgresql://localhost:5432/nenlinea";
             String user ="postgres";
             String pass = "12345";
             String name="ssd, Azofeifa, auron, 1234";
             
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
                      //String clave = result.getString("cla_usu");
                      System.out.println("User: "+usuario);
                  }
                  result.close();
                  st.close();
                  conex.close();
              } catch(Exception exc) {
                  System.out.println("Errorx:"+exc.getMessage());
              }
    }
        
//        String cadenaConexion= "jdbc:posgresql://localhost:5432/nenlinea";
//        String usuario="postgres";
//        String pass="12345";
//        String driverClassName = "org.postgresql.Driver"; 
////        
//        
//        //String cadenaDriver="org.posgresql.Driver";
//        String consultaSQL="SELECT * FROM juego";
//        System.out.println("Select a la base: "+consultaSQL);
//        
//        try{
//            System.out.println("try");
//            Class.forName(driverClassName);
//            System.out.println("Driver");
//            this.conector=DriverManager.getConnection(cadenaConexion, usuario, pass);
//            System.out.println("Conexion correcta");
//        
//        }catch(SQLException ex){
//            
//        }catch (ClassNotFoundException ex) {
//            //Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        }
        
    public Connection getConexion(){
        return this.conector;
               
    }
    public void insertarBD(String id,String dato) throws SQLException{
        String cadena = "jdbc:postgresql://localhost:5432/nenlinea";
        String user ="postgres";
        String pass = "12345";
        //String dato="Kepa, Izabiz, kep, ki12";
        Connection conex = DriverManager.getConnection(cadena,user,pass);
        java.sql.Statement st = conex.createStatement();
                  
                  
        Statement consulta = (Statement) conex.createStatement();
        consulta.executeUpdate("insert into juego values('"+id+"','{"+dato+"}')");
//        consulta.executeUpdate("insert into juego values('id+","+{" + dato +"}')");
//                                ('1','{Emilio, Bermudez, siuuu, e10000}');
    }
    
    
    
}
