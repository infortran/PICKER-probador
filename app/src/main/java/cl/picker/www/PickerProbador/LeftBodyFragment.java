package cl.picker.www.PickerProbador;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LeftBodyFragment extends volleyFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "productos_relacionados";


    // TODO: Rename and change types of parameters
    private String[] idsProdRelacionados;

    ImageView btn_agregar_producto, btn_solicitar_producto;

    public LeftBodyFragment() {
        // Required empty public constructor
    }

    public static LeftBodyFragment newInstance(Bundle bundle) {

        LeftBodyFragment fragment = new LeftBodyFragment();
        if(bundle != null){
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //idsProdRelacionados = getArguments().getStringArray(ARG_PARAM1);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left_body, container, false);
        btn_agregar_producto = (ImageView)view.findViewById(R.id.btn_agregar_al_carrito);
        btn_solicitar_producto = (ImageView)view.findViewById(R.id.btn_solicitar_producto);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
