package cl.picker.www.PickerProbador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Freddy on 03-11-2015.
 */
public abstract class ListaRelacionadosAdapter extends BaseAdapter{
    private ArrayList<?> contenido;
    private Context contexto;
    private int R_layout_idview;

    public ListaRelacionadosAdapter (Context contexto, int R_layout_idView, ArrayList<?> contenido){
        super();
        this.contexto = contexto;
        this.R_layout_idview = R_layout_idView;
        this.contenido = contenido;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater vi = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R_layout_idview, null);
        }
        onEntrada(contenido.get(position),convertView);
        return convertView;
    }

    @Override
    public int getCount() {
        return contenido.size();
    }

    @Override
    public Object getItem(int position) {
        return contenido.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract void onEntrada(Object entrada, View view);
}
