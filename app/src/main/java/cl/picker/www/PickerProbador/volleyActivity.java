package cl.picker.www.PickerProbador;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by Freddy on 28-12-2015.
 */
public class volleyActivity extends Activity {

    VolleyS volley;
    RequestQueue requestQueue;

    //public String base_url = "http://172.20.10.2:8080/picker/";
    public String base_url = "http://www.picker.cl/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volley = VolleyS.getInstance(this);
        requestQueue = volley.getRequestQueue();
    }

    public void addToQueue(Request request){
        if (request != null) {
            request.setTag(this);
            if (requestQueue == null)
                requestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }
    }
}
