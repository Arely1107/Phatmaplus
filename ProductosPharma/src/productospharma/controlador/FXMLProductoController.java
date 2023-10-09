package productospharma.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;
import productospharma.Dao.ProductoDao;
import productospharma.modelo.Producto;

public class FXMLProductoController implements Initializable {
     @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField doublePrecio;

    @FXML
    private TextField numId;

    @FXML
    private TextField numStock;

    @FXML
    private TextArea txtAreaDescripcion;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtNombre;
    
    @FXML
    private TableView<Producto> tvProductos;
    
    private ProductoDao productoDao;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.productoDao= new ProductoDao();
        cargarProductos();
    }    
    
    @FXML
    void btnGuardarOnAction(ActionEvent event) {
        Producto producto=new Producto();
        
        producto.setNombre(txtNombre.getText());
        
        try {
            double precio = Double.parseDouble(doublePrecio.getText());
            producto.setPrecio(precio);

            if (precio > 0) {
                producto.setPrecio(precio);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("El precio debe ser mayor que $0.00");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            // Manejar el caso en el que el texto no sea un número válido
            System.err.println("Error al convertir el precio a double: " + e.getMessage());
        }
        
        try {
            int stock = Integer.parseInt(numStock.getText());
            producto.setStock(stock);
            if (stock > 0) {
                producto.setPrecio(stock);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("El Stock debe ser Mayor a 0 Piezas");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            // Manejar el caso en el que el texto no sea un número válido
            System.err.println("Error al convertir el stock a int: " + e.getMessage());
        }
        producto.setCategoria(txtCategoria.getText());
        producto.setDescripcion(txtAreaDescripcion.getText());
        
        System.out.println(producto.toString());
        
        boolean rsp = this.productoDao.registrar(producto);
        if(rsp){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
            alert.setTitle("Exito");
            alert.setHeaderText(null);
            alert.setContentText("Se registro Correctamente el Producto");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
            
            limpiarCampos();
            cargarProductos();
            
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Hubo un Error al registrar el Producto");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
        }
    }
    private void limpiarCampos(){
        txtNombre.setText("");
        doublePrecio.clear();
        numStock.clear();
        txtCategoria.setText("");
        txtAreaDescripcion.setText("");
    }
    
    public void cargarProductos(){
        tvProductos.getItems().clear();
        tvProductos.getColumns().clear();
        
        List<Producto> producto = this.productoDao.listar();
        
        ObservableList<Producto> data = FXCollections.observableArrayList(producto);
        
        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn nombreCol = new TableColumn("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory("nombre"));
        
        TableColumn precioCol = new TableColumn("Precio");
        precioCol.setCellValueFactory(new PropertyValueFactory("Precio"));
        
        TableColumn stockCol = new TableColumn("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory("stock"));
        
        TableColumn descrpcionCol = new TableColumn("Descripcion");
        descrpcionCol.setCellValueFactory(new PropertyValueFactory("descripcion"));
        
        tvProductos.setItems(data);
        tvProductos.getColumns().addAll(idCol,nombreCol,precioCol,stockCol,descrpcionCol);
    }
}
