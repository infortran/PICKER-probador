package cl.picker.www.PickerProbador.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import cl.picker.www.PickerProbador.ListoFragment;
import cl.picker.www.PickerProbador.R;
import cl.picker.www.PickerProbador.volleyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class inicioTiendasFragment extends volleyFragment {

    private static final String EMAIL_USER = "email_user_picker";
    private static final String NAME_USER = "name_user_picker";
    private static final String LAST_NAME_USER = "last_name_user_picker";
    private static final String ID_USER = "id_user_picker";
    private static final String ID_TIENDA = "id_tienda";
    private static final String COD_TIENDA = "cod_tienda";
    private static final String NOMBRE_TIENDA = "nombre_tienda";
    private static final String ID_PROBADOR = "id_probador";

    private String userIdPicker, userEmailPicker, userNamePicker, userLastNamePicker, idTienda, codTienda, nombreTienda, idProbador;

    TextView nombreTiendaTv, codTiendaTv;
    Button btn_confirmar_tienda;

    public inicioTiendasFragment() {
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
            idTienda = getArguments().getString(ID_TIENDA);
            codTienda = getArguments().getString(COD_TIENDA);
            nombreTienda = getArguments().getString(NOMBRE_TIENDA);
            idProbador = getArguments().getString(ID_PROBADOR);
            Log.v("argumentos",""+userIdPicker+" "+userEmailPicker+" "+userNamePicker+" "+idTienda+" "+nombreTienda);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio_tiendas, container, false);
        nombreTiendaTv = (TextView)view.findViewById(R.id.txt_nombre_tienda_select_picker);
        codTiendaTv = (TextView)view.findViewById(R.id.txt_cod_tienda_select_picker);
        btn_confirmar_tienda = (Button)view.findViewById(R.id.btn_confirmar_select_picker);
        //listaTiendas = (ListView)view.findViewById(R.id.lista_inicio_tiendas);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nombreTiendaTv.setText(nombreTienda);
        codTiendaTv.setText(codTienda);
        btn_confirmar_tienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("prefUserPicker", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id_user", userIdPicker);
                editor.putString("email_user", userEmailPicker);
                editor.putString("nombre_user", userNamePicker);
                editor.putString("apellido_user", userLastNamePicker);
                editor.putString("id_tienda", idTienda);
                editor.putString("cod_tienda", codTienda);
                editor.putString("nombre_tienda", nombreTienda);
                editor.putString("id_probador", idProbador);
                editor.commit();
                //id tienda cod tienda nombre tienda
                ListoFragment listo = new ListoFragment();

                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.gla_there_come, R.anim.gla_there_gone);
                transaction.replace(R.id.dinamic_layout_main, listo);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
    }
}
