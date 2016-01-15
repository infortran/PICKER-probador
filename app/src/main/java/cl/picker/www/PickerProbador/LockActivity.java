package cl.picker.www.PickerProbador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LockActivity extends volleyActivity {
    Handler handler;
    Runnable runnable;

    private String idUser, nombreUser, apellidoUser;

    private String idTienda, codTienda, nombreTienda;

    private String idProbador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        SharedPreferences prefs = getSharedPreferences("prefUserPicker", Context.MODE_PRIVATE);
        idTienda = prefs.getString("id_tienda", "ID Tienda");
        idUser = prefs.getString("id_user", "ID Administrador");
        idProbador = prefs.getString("id_probador", "ID Probador");

        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("before getProdInicial", idTienda +" = id tienda "+idProbador +" = id probador");
                getProductoInicial(idTienda, idProbador);
                handler.postDelayed(this,7000);
            }
        },7000);
    }



    private void getProductoInicial(String _idTienda, String _idProbador){
        Log.d("solicitando","en proceso...");
        String url = base_url+"pickerprobador/verificar_producto_inicial";

        Map<String, String> params = new HashMap<>();
        params.put("id_tienda", _idTienda);
        params.put("id_probador", _idProbador);

        CustomRequest request = new CustomRequest(Request.Method.POST,
                url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String resultado = jsonObject.getString("valid");

                    switch(resultado){
                        case "ok":
                            Log.d("case", "ok");

                            Intent i = new Intent(LockActivity.this, PickerActivity.class);

                            JSONObject productosJson = jsonObject.getJSONObject("productos_relacionados");

                            i.putExtra("productos_relacionados", productosJson.toString());
                            handler.removeCallbacks(runnable);
                            startActivity(i);
                            finish();
                            break;
                        case "err_tendencias":
                            Log.d("error", "tendencias");
                            break;
                        case "err_prods":
                            Log.d("error", "productos (no hay carrito)");
                            break;
                        case "err_probs":
                            Log.d("error", "probadores");
                            break;
                    }
                } catch (JSONException e) {
                    //Toast.makeText(LockActivity.this,"Error al cargar datos desde Json Object",Toast.LENGTH_SHORT).show();
                    Log.d("valid", "error");
                    Log.d("error", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("error al conectar", volleyError.toString());
            }
        });
        int socketTimeout = 7000;//7 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        addToQueue(request);
    }

    int contadorBack = 0;
    @Override
    public void onBackPressed() {

        if(contadorBack == 5){
            contadorBack = 0;
            super.onBackPressed();
            return;
        }
        this.contadorBack++;
        if(contadorBack == 1){
            Toast.makeText(getApplicationContext(),"No tiene autorización para salir", Toast.LENGTH_SHORT).show();
        }

        if(contadorBack == 5){
            Toast.makeText(getApplicationContext(),"Pulse una vez mas para salir", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                contadorBack = 0;
            }
        }, 15000);
    }
}
