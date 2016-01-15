package cl.picker.www.PickerProbador;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cl.picker.www.PickerProbador.Fragments.inicioTiendasFragment;


public class ConfigTiendaFragment extends volleyFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EMAIL_USER = "email_user_picker";
    private static final String NAME_USER = "name_user_picker";
    private static final String LAST_NAME_USER = "last_name_user_picker";
    private static final String ID_USER = "id_user_picker";

    // TODO: Rename and change types of parameters
    private String userIdPicker;
    private String userEmailPicker;
    private String userNamePicker;
    private String userLastNamePicker;

    EditText txt_id_tienda, txt_id_picker;
    Button btn_asociar;
    TextView nombreUserPicker;



    // TODO: Rename and change types and number of parameters
    /*public static ConfigTiendaFragment newInstance(String param1, String param2) {
        ConfigTiendaFragment fragment = new ConfigTiendaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    public ConfigTiendaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userIdPicker = getArguments().getString(ID_USER);
            userEmailPicker = getArguments().getString(EMAIL_USER);
            userNamePicker = getArguments().getString(NAME_USER);
            userLastNamePicker = getArguments().getString(LAST_NAME_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        txt_id_picker = (EditText)view.findViewById(R.id.txt_id_picker);
        txt_id_tienda = (EditText)view.findViewById(R.id.txt_id_tienda);
        btn_asociar = (Button)view.findViewById(R.id.btn_asociar_ids);
        nombreUserPicker = (TextView)view.findViewById(R.id.texto_usuario);
        nombreUserPicker.setText("Bienvenido "+userNamePicker);
        return view;
    }





    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_asociar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresarIdPicker(txt_id_picker.getText().toString(), txt_id_tienda.getText().toString());
            }
        });
    }



    private void ingresarIdPicker(final String idPicker, String idTienda){
        String url = getBase_url()+"ingresar_id_picker";

        Map<String, String> params = new HashMap<>();
        params.put("id_picker",idPicker);
        params.put("id_tienda", idTienda);
        params.put("id_user", userIdPicker);

        Log.d("id user", userIdPicker);

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Asociando su módulo PICKER");
        pDialog.setIndeterminate(false);
        pDialog.show();

        CustomRequest jsonObjReq = new CustomRequest(
                Request.Method.POST,
                url,params,
                new Response.Listener<JSONObject>() {
                    String valid;
                    String id_tienda_err;
                    String id_picker_err;
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            valid = jsonObject.getString("valid");
                            switch(valid){
                                case "ok":
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id_user_picker",userIdPicker);
                                    bundle.putString("email_user_picker",userEmailPicker);
                                    bundle.putString("name_user_picker",userNamePicker);
                                    bundle.putString("last_name_user_picker",userLastNamePicker);
                                    bundle.putString("id_tienda", data.getString("id_tienda"));
                                    bundle.putString("cod_tienda", data.getString("cod_tienda"));
                                    bundle.putString("nombre_tienda", data.getString("nombre_tienda"));
                                    bundle.putString("id_probador", idPicker);


                                    inicioTiendasFragment inicio = new inicioTiendasFragment();
                                    inicio.setArguments(bundle);
                                    FragmentManager manager = getFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.setCustomAnimations(R.anim.gla_there_come,R.anim.gla_there_gone);
                                    transaction.replace(R.id.dinamic_layout_main, inicio);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    pDialog.setMessage("Conectando con servidores PICKER");
                                    break;
                                case "err_tienda":
                                    Toast.makeText(getActivity().getApplicationContext(), "Este ID de tienda no esta registrado a su nombre", Toast.LENGTH_LONG).show();
                                    break;
                                case "err_picker":
                                    Toast.makeText(getActivity().getApplicationContext(), "Este ID Picker no esta registrado a su nombre", Toast.LENGTH_LONG).show();
                                    break;
                                case "errNoPicker":
                                    Toast.makeText(getActivity().getApplicationContext(), "Este id no esta registrado en su sistema", Toast.LENGTH_SHORT).show();
                                    break;
                                case "errTienda":
                                    Toast.makeText(getActivity().getApplicationContext(), "Esta tienda no esta registrada en su sistema", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity().getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();


                            }

                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Log.d("valido", "no hay variable valid");
                        }

                        try {
                            id_tienda_err = jsonObject.getString("err_id_tienda");
                            switch(id_tienda_err){
                                case "err001":
                                    Toast.makeText(getActivity().getApplicationContext(),"El campo ID Tienda es obligatorio",Toast.LENGTH_LONG).show();
                                    break;
                                case "err007":
                                    Toast.makeText(getActivity().getApplicationContext(),"Ha sobrepasado el límite de caracteres",Toast.LENGTH_LONG).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Log.d("id tienda","no hay errores de tienda");
                        }

                        try {
                            id_picker_err = jsonObject.getString("err_id_picker");
                            switch(id_picker_err){
                                case "err001":
                                    Toast.makeText(getActivity().getApplicationContext(),"El campo ID Picker es obligatorio",Toast.LENGTH_LONG).show();
                                    break;
                                case "err007":
                                    Toast.makeText(getActivity().getApplicationContext(),"Ha sobrepasado el límite de caracteres",Toast.LENGTH_LONG).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Log.d("id tienda", "no hay errores de ID PICKER");
                        }
                        pDialog.dismiss();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error de conexión")
                                .setMessage("Verifique su conexión a internet")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                        volleyError.printStackTrace();
                        pDialog.dismiss();
                    }
                }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/x-www-form-urlencoded"); not used this

                Log.d("getHeaders", params.toString());
                return params;
            }


        };

        addToQueue(jsonObjReq);
    }

    private void checkearPicker(String idPicker, String idTienda){
        String url = getBase_url()+"checkear_picker";

        Map<String, String> params = new HashMap<>();
        params.put("id_picker",idPicker);
        params.put("id_tienda",idTienda);
        params.put("id_user", userIdPicker);

        CustomRequest jsonObjReq = new CustomRequest(
                Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/x-www-form-urlencoded"); not used this

                Log.d("getHeaders", params.toString());
                return params;
            }


        };
        addToQueue(jsonObjReq);

    }


}
