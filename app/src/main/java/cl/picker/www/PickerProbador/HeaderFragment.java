package cl.picker.www.PickerProbador;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cl.picker.www.PickerProbador.Fragments.CarritoFragment;


public class HeaderFragment extends volleyFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String idUser, emailUser, nombreUser, apellidoUser, idTienda, codTienda, nombreTienda, idProbador;

    TextView cantProdEnCarro;
    ImageView bagProd;
    TextView wordProd;


    public HeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getActivity().getSharedPreferences("prefUserPicker", Context.MODE_PRIVATE);
        idUser = prefs.getString("id_user", "ID USUARIO");
        emailUser = prefs.getString("email_user", "EMAIL USUARIO");
        nombreUser = prefs.getString("nombre_user", "NOMBRE USUARIO");
        apellidoUser = prefs.getString("apellido_user", "APELLIDO USUARIO");
        idTienda = prefs.getString("id_tienda", "ID TIENDA");
        codTienda = prefs.getString("cod_tienda", "CODIGO TIENDA");
        nombreTienda = prefs.getString("nombre_tienda", "NOMBRE TIENDA");
        idProbador = prefs.getString("id_probador", "ID PROBADOR");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_header, container, false);
        bagProd = (ImageView)view.findViewById(R.id.btn_bag_prod);
        wordProd = (TextView)view.findViewById(R.id.productos_word);

        bagProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCart();
            }
        });

        wordProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showCart();
            }
        });

        cantProdEnCarro = (TextView)view.findViewById(R.id.num_productos_carrito);
        productsOnCart(idTienda,idProbador);
        return view;
    }

    private void showCart(){
        CarritoFragment carritoFragment = CarritoFragment.newInstance();
        carritoFragment.setShowsDialog(true);
        carritoFragment.show(getFragmentManager(),"Dialogo");
    }

    private void productsOnCart(String idTienda, String idProbador){
        String url = getBase_url()+"products_on_cart";

        Map<String, String> params = new HashMap<>();
        params.put("id_tienda", idTienda);
        params.put("id_probador", idProbador);

        CustomRequest request = new CustomRequest(
                Request.Method.POST,
                url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int cantOnCart = response.getInt("cantidad_prod_on_cart");
                    cantProdEnCarro.setText(String.valueOf(cantOnCart));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        addToQueue(request);
    }

}
