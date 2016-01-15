package cl.picker.www.PickerProbador.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.picker.www.PickerProbador.Filas.FilaTienda;
import cl.picker.www.PickerProbador.R;

/**
 * Created by Freddy on 09-12-2015.
 */
public class tiendaAdapter extends BaseAdapter {
    private Context context;
    private List<FilaTienda> lista;

    public tiendaAdapter(Context context, List<FilaTienda> lista){
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
        TextView txtNombre = (TextView)rowView.findViewById(R.id.nombre_tienda_fila);
        TextView txtOwner = (TextView)rowView.findViewById(R.id.owner_tienda_fila);
        TextView txtCod = (TextView)rowView.findViewById(R.id.cod_tienda_fila);


        FilaTienda tienda = lista.get(position);
        txtNombre.setText(tienda.getNombreTienda());
        txtOwner.setText(tienda.getOwnerTienda());
        txtCod.setText(tienda.getCodTienda());


        return rowView;
    }
}
