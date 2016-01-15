package cl.picker.www.PickerProbador.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.picker.www.PickerProbador.CustomRequest;
import cl.picker.www.PickerProbador.FilaProducto;
import cl.picker.www.PickerProbador.ListAdapters.CarritoProdAdapter;
import cl.picker.www.PickerProbador.R;
import cl.picker.www.PickerProbador.VolleyDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarritoFragment extends VolleyDialogFragment {
    ListView listaCarrito;
    String idUser, emailUser, nombreUser, apellidoUser, idTienda, codTienda, nombreTienda, idProbador;
    public CarritoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, 0);
        SharedPreferences prefs = getActivity().getSharedPreferences("prefUserPicker", Context.MODE_PRIVATE);
        idUser = prefs.getString("id_user","ID USUARIO");
        emailUser = prefs.getString("email_user", "EMAIL USUARIO");
        nombreUser = prefs.getString("nombre_user", "NOMBRE USUARIO");
        apellidoUser = prefs.getString("apellido_user", "APELLIDO USUARIO");
        idTienda = prefs.getString("id_tienda", "ID TIENDA");
        codTienda = prefs.getString("cod_tienda", "CODIGO TIENDA");
        nombreTienda = prefs.getString("nombre_tienda", "NOMBRE TIENDA");
        idProbador = prefs.getString("id_probador", "ID PROBADOR");
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1000,600);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return d;
    }

    public static CarritoFragment newInstance() {

        Bundle args = new Bundle();

        CarritoFragment fragment = new CarritoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        listaCarrito = (ListView)view.findViewById(R.id.lista_carrito);

        leerCarrito(idTienda,idProbador);
        return view;
    }

    private void leerCarrito(String _idTienda, String _idProbador){
        String url = getBase_url()+"products_on_cart";

        Map<String, String> params = new HashMap<>();
        params.put("id_tienda", _idTienda);
        params.put("id_probador", _idProbador);

       /* final ProgressDialog dialog = new ProgressDialog(getActivity().getApplicationContext());
        dialog.setMessage("Cargando");
        dialog.setIndeterminate(false);
        dialog.show();*/

        CustomRequest request = new CustomRequest(
                Request.Method.POST,
                url,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String idProb = response.getString("id_probador");
                    String idTienda = response.getString("id_tienda");
                    Log.d("variables de json",idProb+" "+idTienda);
                }catch(JSONException e){
                    Log.e("respuesta del php", "error:"+e.toString());
                }

                List productosLista = new ArrayList();
                Log.d("legth del response" , ""+response.length());
                for(int i = 0; i < 2;i++){
                    String nombreProd = "prod_"+i;
                    try {
                        JSONObject productoSel = response.getJSONObject(nombreProd);
                        try{
                            String nombreProdList = productoSel.getString("nombre_prod");
                            String descripProdList = productoSel.getString("desc_breve_prod");
                            int precioProdList = productoSel.getInt("precio_venta_iva_prod");
                            int cantidadProdList = response.getInt("cantidad_prod_on_cart");
                            Log.d("variables", nombreProdList + " " + descripProdList);
                            productosLista.add(new FilaProducto(nombreProdList,descripProdList,precioProdList,cantidadProdList,0));
                        }catch(JSONException e){
                            Log.d("objeto omitido","valor "+i);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listaCarrito.setAdapter(new CarritoProdAdapter(getActivity(), productosLista));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        addToQueue(request);
       // dialog.dismiss();
    }
}
