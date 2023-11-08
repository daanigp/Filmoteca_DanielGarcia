package esp.daniel.filmoteca_danielgarcia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FilmAdapter extends ArrayAdapter<Film> {

    private int mResource;
    private ArrayList<Film> misCiudades;

    public FilmAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Film> objects) {
        super(context, resource, objects);
        mResource = resource;
        misCiudades = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View miFila = inflater.inflate(mResource, parent, false);

        TextView txtNombrePelicula = miFila.findViewById(R.id.txtNombrePelicula);
        TextView txtNombreDirector = miFila.findViewById(R.id.txtNombreDirector);
        ImageView imgCiudad = miFila.findViewById(R.id.imgPelicula);

        txtNombrePelicula.setText(misCiudades.get(position).getTitle());
        txtNombreDirector.setText(misCiudades.get(position).getDirector());
        imgCiudad.setImageResource(misCiudades.get(position).getImageResId());

        return miFila;
    }


}
