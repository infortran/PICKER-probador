package cl.picker.www.PickerProbador.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.picker.www.PickerProbador.FilaProducto;
import cl.picker.www.PickerProbador.R;


/**
 * Created by Freddy on 26-11-2015.
 */
public class CarritoProdAdapter extends BaseAdapter {
    private Context context;
    private List<FilaProducto> lista;

    public CarritoProdAdapter(Context context, List<FilaProducto> lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.fila_prod_carrito, parent, false);
        }

        // Set data into the view.
        TextView txtNombre = (TextView)rowView.findViewById(R.id.nombre_fila_prod_carrito);
        TextView txtDescrip = (TextView)rowView.findViewById(R.id.descripcion_breve_fila_prod_carrito);
        TextView txtPrecio = (TextView)rowView.findViewById(R.id.precio_fila_prod_carrito);
        TextView txtCantidad = (TextView)rowView.findViewById(R.id.cantidad_fila_prod_carrito);

        FilaProducto producto = lista.get(position);
        txtNombre.setText(producto.getNombre());
        txtDescrip.setText(producto.getDescripcion());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtCantidad.setText(String.valueOf(producto.getCantidad()));

        return rowView;
    }
}
