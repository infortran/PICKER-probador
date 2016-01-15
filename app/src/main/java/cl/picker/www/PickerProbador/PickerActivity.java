package cl.picker.www.PickerProbador;

import android.app.FragmentTransaction;
import android.app.FragmentManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class PickerActivity extends AppCompatActivity {
    Handler handle = new Handler();
    Handler handlerPrefs = new Handler();
    Bundle bundle, id_prod_bundle;
    String[] prodsRelacionados;
    String idUser, emailUser, nombreUser, apellidoUser, idTienda, codTienda, nombreTienda, idProbador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        bundle = getIntent().getExtras();
        getSharedPrefs();
        cargarFragments();
    }

    protected void cargarFragments(){
        final Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                handle.post(cargarFragmentsRunnable);
            }
        };
        thread.start();
    }

    protected void getSharedPrefs(){
        final Thread threadPrefs = new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                handlerPrefs.post(loadSharedPrefs);
            }
        };
        threadPrefs.start();
    }

    final Runnable cargarFragmentsRunnable = new Runnable() {
        @Override
        public void run() {
            loadLeftFragment();
            loadCenterFragment();
            loadRightFragment();
        }
    };

    final Runnable loadSharedPrefs = new Runnable() {
        @Override
        public void run() {
            SharedPreferences prefs = getSharedPreferences("prefUserPicker", Context.MODE_PRIVATE);
            idUser = prefs.getString("id_user","ID USUARIO");
            emailUser = prefs.getString("email_user", "EMAIL USUARIO");
            nombreUser = prefs.getString("nombre_user", "NOMBRE USUARIO");
            apellidoUser = prefs.getString("apellido_user", "APELLIDO USUARIO");
            idTienda = prefs.getString("id_tienda", "ID TIENDA");
            codTienda = prefs.getString("cod_tienda", "CODIGO TIENDA");
            nombreTienda = prefs.getString("nombre_tienda", "NOMBRE TIENDA");
            idProbador = prefs.getString("id_probador", "ID PROBADOR");

            Log.d("id user prefs",prefs.getString("id_user","ID USUARIO"));

            //id_prod_bundle = getIntent().getExtras();//recibe los ids de los produuctos relacionados

            Intent intent = getIntent();
            String jsonString = intent.getStringExtra("productos_relacionados");
            Log.d("jsonString", jsonString);

            bundle.putString("id_user", idUser);
            bundle.putString("email_user", emailUser);
            bundle.putString("nombre_user", nombreUser);
            bundle.putString("apellido_user", apellidoUser);
            bundle.putString("id_tienda", idTienda);
            bundle.putString("cod_tienda", codTienda);
            bundle.putString("nombre_tienda", nombreTienda);
            bundle.putString("id_probador", idProbador);
            bundle.putString("productos_relacionados", jsonString);
        }
    };

    public void loadLeftFragment(){
        LeftBodyFragment left = LeftBodyFragment.newInstance(bundle);

        try{
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tr = fm.beginTransaction();
            tr.add(R.id.left_body_picker, left);
            tr.commit();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Error al cargar el fragment",Toast.LENGTH_LONG).show();
        }
    }

    public void loadCenterFragment(){
        CenterBodyFragment center = CenterBodyFragment.newInstance(bundle);
        try{
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tr = fm.beginTransaction();
            tr.add(R.id.center_body_picker, center);
            tr.commit();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Error al cargar el fragment",Toast.LENGTH_LONG).show();
        }
    }

    public void loadRightFragment(){
        RightBodyFragment right = RightBodyFragment.newInstance(bundle);
        try{
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tr = fm.beginTransaction();
            tr.add(R.id.right_body_picker, right);
            tr.commit();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Error al cargar el fragment",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
