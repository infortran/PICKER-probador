package cl.picker.www.PickerProbador;
import android.os.AsyncTask;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    EditText user_login;
    EditText pass_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginAsync login = new loginAsync();
        login.execute();
        btnLogin = (Button)findViewById(R.id.btn_login_fragment);
        user_login = (EditText)findViewById(R.id.email_login);
        pass_login = (EditText)findViewById(R.id.password_login);
    }


    public void loadLoginFragment(){
        LoginFragment login = new LoginFragment();
        try
        {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.add(R.id.dinamic_layout_main, login);
            trans.commit();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"falla",Toast.LENGTH_LONG).show();
        }
    }

    private class loginAsync extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            loadLoginFragment();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
