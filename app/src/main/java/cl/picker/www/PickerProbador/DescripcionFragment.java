package cl.picker.www.PickerProbador;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescripcionFragment extends volleyFragment {

    private static final String TXT_PARAM = "txt_descripcion";
    TextView txtDescripcion;
    private String stringDescripcion;

    public static DescripcionFragment newInstance(String param1){
        DescripcionFragment fragment = new DescripcionFragment();
        Bundle args = new Bundle();
        args.putString(TXT_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public DescripcionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            //Toast.makeText(getActivity().getApplicationContext(),"argumentos distintos de null",Toast.LENGTH_LONG).show();
            stringDescripcion = getArguments().getString(TXT_PARAM);
            Log.d("contenido descripcion",stringDescripcion);
        }else{
            Toast.makeText(getActivity().getApplicationContext(),"No hay argumentos",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_descripcion, container, false);

        txtDescripcion = (TextView)view.findViewById(R.id.descripcion_dinamica);
        try{
            Log.d("debugueando descripcion",stringDescripcion);
            txtDescripcion.setText(stringDescripcion);
        }catch(Exception e){
            Log.d("Excepcion",e.toString());
        }
        return view;
    }


}
