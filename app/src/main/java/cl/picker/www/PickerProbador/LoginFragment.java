package cl.picker.www.PickerProbador;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class LoginFragment extends volleyFragment {
    Button loginButton;
    EditText email_user;
    EditText pass_user;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String usuarioPicker;
    private String mParam2;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            usuarioPicker = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginButton = (Button) view.findViewById(R.id.btn_login_fragment);
        email_user = (EditText)view.findViewById(R.id.email_login);
        pass_user = (EditText)view.findViewById(R.id.password_login);
        return view;
    }

   

    private void loginRequest(){
        String url = getBase_url()+"check_login_and_get_user_data";

        Map<String, String> params = new HashMap<String,String>();
        params.put("email", email_user.getText().toString());
        params.put("password", pass_user.getText().toString());

        final ProgressDialog pDialog = new ProgressDialog(LoginFragment.this.getActivity());
        pDialog.setMessage("Validando");
        pDialog.setIndeterminate(false);
        pDialog.show();

        CustomRequest jsonObjReq = new CustomRequest(
                Request.Method.POST,
                url,params,
                new Response.Listener<JSONObject>() {
                    String valid;
                    String email_err;
                    String pass_err;
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            valid = jsonObject.getString("valid");

                            Log.d("valid",valid);
                            switch(valid){
                                case "ok":
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    String userId = data.getString("user_id");
                                    String userEmail = data.getString("user_email");
                                    String userName = data.getString("user_name");
                                    String userLastName = data.getString("user_last_name");
                                    //Toast.makeText(getActivity().getApplicationContext(), "valido", Toast.LENGTH_SHORT).show();

                                    /*SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("id_admin", userId);
                                    editor.putString("email_admin", userEmail);
                                    editor.putString("name_admin", userName);
                                    editor.putString("last_name_admin",userLastName);*/

                                    Bundle bundle = new Bundle();

                                    Log.v("id user", userId);

                                    bundle.putString("id_user_picker", userId);
                                    bundle.putString("email_user_picker",userEmail);
                                    bundle.putString("name_user_picker", userName);
                                    bundle.putString("last_name_user_picker", userLastName);

                                    ConfigTiendaFragment config = new ConfigTiendaFragment();
                                    config.setArguments(bundle);
                                    FragmentManager manager = getFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                    transaction.setCustomAnimations(R.anim.gla_there_come, R.anim.gla_there_gone);
                                    transaction.replace(R.id.dinamic_layout_main, config);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    break;
                                case "err_pass":
                                    Toast.makeText(getActivity().getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                                    break;
                                case "err_user":
                                    Toast.makeText(getActivity().getApplicationContext(), "Este usuario no se encuentra registrado en el sistema", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity().getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            email_err = jsonObject.getString("err_email");
                            Log.d("email error", email_err);
                            switch(email_err){
                                case "err001":
                                    Toast.makeText(getActivity().getApplicationContext(),"El campo Email es obligatorio",Toast.LENGTH_LONG).show();
                                    break;
                                case "err004":
                                    Toast.makeText(getActivity().getApplicationContext(),"Ingrese un correo válido ejemplo@dominio.com",Toast.LENGTH_LONG).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            pass_err = jsonObject.getString("err_password");
                            Log.d("error password",pass_err);
                            if(pass_err.equals("err001")){
                                Toast.makeText(getActivity().getApplicationContext(),"El campo Contraseña es obligatorio",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pDialog.dismiss();
                        onConnectionFinished();
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

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loginRequest();
            }
        });
    }
}
