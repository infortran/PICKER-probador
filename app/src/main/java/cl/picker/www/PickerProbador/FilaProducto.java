package cl.picker.www.PickerProbador;

/**
 * Created by Freddy on 26-11-2015.
 */
public class FilaProducto {
    private String nombre;
    private String descripcion;
    private int precio;
    private int cantidad;
    private int ventaPicker;

    public FilaProducto() {
    }

    public FilaProducto(String nombre, String descripcion, int precio, int cantidad, int ventaPicker) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.ventaPicker = ventaPicker;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getVentaPicker() {
        return ventaPicker;
    }

    public void setVentaPicker(int ventaPicker) {
        this.ventaPicker = ventaPicker;
    }
}
