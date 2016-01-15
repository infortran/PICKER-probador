package cl.picker.www.PickerProbador;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class TallasProductoFragment extends volleyFragment {

    ImageView imgTallaS, imgTallaM, imgTallaL, imgTallaXL,imgTallaXXL;
    LinearLayout contenedorTallas;

    public TallasProductoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tallas_producto, container, false);
        contenedorTallas = (LinearLayout)view.findViewById(R.id.contenedor_tallas);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void cargarTallas(String talla){
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(40,40);
        final ImageView nuevaTalla = new ImageView(getActivity());
        imageParams.leftMargin = 20;
        nuevaTalla.setLayoutParams(imageParams);
        switch(talla){
            case "s":
                nuevaTalla.setImageResource(R.drawable.size_s);
                nuevaTalla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nuevaTalla.setImageResource(R.drawable.size_s_sel);
                    }
                });
                break;
            case "m":
                nuevaTalla.setImageResource(R.drawable.size_m);
                nuevaTalla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nuevaTalla.setImageResource(R.drawable.size_m_sel);
                    }
                });
                break;
            case "l":
                nuevaTalla.setImageResource(R.drawable.size_l);
                nuevaTalla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nuevaTalla.setImageResource(R.drawable.size_l_sel);
                    }
                });
                break;
            case "xl":
                nuevaTalla.setImageResource(R.drawable.size_xl);
                nuevaTalla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nuevaTalla.setImageResource(R.drawable.size_xl_sel);
                    }
                });
                break;
        }
        contenedorTallas.addView(nuevaTalla);
    }
}
