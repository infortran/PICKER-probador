package cl.picker.www.PickerProbador;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CenterBodyFragment extends volleyFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PROD_REL = "productos_relacionados";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imagenDinamica,imagenPause;
    ProgressBar barraProgreso;
    private int estadoProgreso = 0;
    String prodRelacionados;
    String id_user;

    // TODO: Rename and change types and number of parameters
    public static CenterBodyFragment newInstance(Bundle bundle) {
        CenterBodyFragment fragment = new CenterBodyFragment();
        if(bundle != null){
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public CenterBodyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onCreate", "ok");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            prodRelacionados = getArguments().getString(PROD_REL);
            id_user = getArguments().getString("id_user");
            Log.d("argument id user",id_user);
            Log.d("prod_rel oncreate",prodRelacionados);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_center_body, container, false);

        imagenDinamica = (ImageView)view.findViewById(R.id.img_producto_centro);
        imagenPause = (ImageView)view.findViewById(R.id.icon_pause);

        barraProgreso = (ProgressBar)view.findViewById(R.id.barra_progreso_relacionados);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagenDinamica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagenPause.getVisibility() == View.VISIBLE) {
                    imagenPause.setVisibility(View.INVISIBLE);
                }

               // Toast.makeText(getActivity(), "boton pulsado", Toast.LENGTH_SHORT).show();
                Log.d("prodrel_", prodRelacionados);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(200);
                        }catch(InterruptedException ex){}
                        loadProductsRels(prodRelacionados);
                    }
                }).start();
            }
        });
    }

    private void loadProductsRels(String jsonString){
        try{
            JSONObject json = new JSONObject(jsonString);
            //JSONObject prods = json.getJSONObject("prod_1");
            //String url = getBase_url_assets()+"img/default-user.png";
            //imgRequest(imagenDinamica, url);
            //putDescripcionFragment(prods.getString("descripcion_prod"));
            for (int i = 0; i < json.length() + 1; i++){
                try{
                    JSONObject prods = json.getJSONObject("prod_"+i);

                }catch(JSONException e){
                    Log.v("Json loadprodRel","Omitido:"+e.toString());
                }

            }
        }catch(JSONException e){
            Log.d("Json error", e.toString());
        }

    }

    private void putDescripcionFragment(String descrip){
        Bundle datosDescripcion = new Bundle();
        datosDescripcion.putString("txt_descripcion",descrip);

        DescripcionFragment descripcion = new DescripcionFragment();

        descripcion.setArguments(datosDescripcion);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.layout_descripcion_producto, descripcion);

        ft.commit();
    }

    private void putColoresFragment(){
        //Bundle datosColores = new Bundle();

        ColoresProductoFragment colores = new ColoresProductoFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.layout_colores_producto,colores);
        ft.commit();
    }

    private void putTallasFragment(){
        TallasProductoFragment tallas = new TallasProductoFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.layout_tallas_producto,tallas);
        ft.commit();
    }

    private void imgRequest(final ImageView img, String url){
        ImageRequest imagen = new ImageRequest(
                url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                img.setImageBitmap(response);
            }
        }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),"Error en la conexion con la imagen",Toast.LENGTH_LONG).show();
            }
        });
        addToQueue(imagen);
    }
}
