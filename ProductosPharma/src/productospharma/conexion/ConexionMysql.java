package productospharma.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionMysql {
    private Connection connection;
    private String usuario = "root";
    private String password="UACM123";
    private String servidor="localhost";
    private String puerto="3307";
    private String nombreBD="productos";
    
    private String url = "jdbc:mysql://" + servidor + ":" + puerto + "/" + nombreBD + "?serverTimezone=UTC";

    private String driver="com.mysql.cj.jdbc.Driver";
    
    public ConexionMysql() {
        try {
            Class.forName(driver);
            connection=DriverManager.getConnection(url,usuario,password);
            if(connection != null){
                System.out.println("Conexion Realizada Correctamente");
            }
        } catch (Exception e) {
            System.err.println("Ocurrio un Error en la conexion");
            System.err.println("Mensaje del error :" + e.getMessage());
            System.err.println("Detalles del error: ");
            
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
    
    
}
