package cl.picker.www.PickerProbador;

import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by Freddy Perez on 26-11-2015.
 */
public class VolleyDialogFragment extends DialogFragment {
    VolleyS volley;
    RequestQueue requestQueue;

    //private String base_url = "http://172.20.10.2:8080/picker/pickerprobador/";
    //private String base_url_assets = "http://172.20.10.2:8080/picker/assets/";
    private String base_url = "http://www.picker.cl/pickerprobador/";
    private String base_url_assets = "http://www.picker.cl/assets/";

    public String getBase_url() {
        return base_url;
    }

    public String getBase_url_assets() {
        return base_url_assets;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volley = VolleyS.getInstance(getActivity().getApplicationContext());
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
            onPreStartConnection();
            requestQueue.add(request);
        }
    }

    public void onPreStartConnection() {
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    public void onConnectionFinished() {
        getActivity().setProgressBarIndeterminateVisibility(false);
    }

    public void onConnectionFailed(String error) {
        getActivity().setProgressBarIndeterminateVisibility(false);
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
