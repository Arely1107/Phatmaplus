package productospharma.Dao;

import com.mysql.cj.xdevapi.PreparableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import productospharma.conexion.ConexionMysql;
import productospharma.modelo.Producto;

public class ProductoDao {
    private ConexionMysql fabricaConexion;
    
    public ProductoDao(){
        this.fabricaConexion = new ConexionMysql();
            
    }
    
    public boolean registrar(Producto producto)
    {
        try {
            String SQL="insert into producto(nombre,precio, stock,"
                    + "categoria,descripcion)"
                    +"values(?,?,?,?,?)";
            Connection connection=this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            
            sentencia.setString(1,producto.getNombre());
            sentencia.setDouble(2,producto.getPrecio());
            sentencia.setInt(3,producto.getStock());
            sentencia.setString(4,producto.getDescripcion());
            sentencia.setString(5,producto.getCategoria());
            
            
            sentencia.execute();
            sentencia.close();
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Ocurrio al Registrar el Producto");
            System.err.println("Mensaje del error :" + e.getMessage());
            System.err.println("Detalles del error: ");
            
            e.printStackTrace();
            
            return false;
        }
    }
    
    public List<Producto> listar(){
        List<Producto> listaProdutos = new ArrayList<>();
        try {

            String SQL = "SELECT * FROM productos.producto;";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            
            while(data.next()== true){
                Producto producto = new Producto();
                producto.setId(data.getInt(1));
                producto.setNombre(data.getString(2));
                producto.setPrecio(data.getDouble(3));
                producto.setStock(data.getInt(4));
                producto.setDescripcion(data.getString(5));
                producto.setCategoria(data.getString(6));
                        
                listaProdutos.add(producto);
            }
            data.close();
            sentencia.close();
        } catch (Exception e) {
            System.err.println("Ocurrio al Mostrar Lista de Producto");
            System.err.println("Mensaje del error :" + e.getMessage());
            System.err.println("Detalles del error: ");
            
            e.printStackTrace();
        }
        return listaProdutos;
    }
}
