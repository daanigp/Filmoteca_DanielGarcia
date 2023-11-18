package esp.daniel.filmoteca_danielgarcia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FilmDataActivity extends AppCompatActivity {

    int position;
    ImageView imgView;
    TextView txtComentario, txtFormatoGenero, txtNumAnyo, txtNomDirector, txtNomPelicula;
    Button btnWebIMDB, btnVolverMenu, btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_data_activity);

        imgView = (ImageView) findViewById(R.id.imgPeli);
        txtNomPelicula = (TextView) findViewById(R.id.txtNomPelicula);
        txtNomDirector = (TextView) findViewById(R.id.txtNomDirector);
        txtNumAnyo = (TextView) findViewById(R.id.txtNumAnyo);
        txtFormatoGenero = (TextView) findViewById(R.id.txtFormatoGenero);
        txtComentario = (TextView) findViewById(R.id.txtComentario);
        btnWebIMDB = (Button) findViewById(R.id.btnWebIMDB);
        btnVolverMenu = (Button) findViewById(R.id.btnVolverMenu);
        btnEditar = (Button) findViewById(R.id.btnEditar);


        //Coger la posición de la película mediante el intent
        Intent intent = getIntent();
        position = intent.getIntExtra("FILM_POSITION", 0);
        imgView.setImageResource(FilmDataSource.films.get(position).getImageResId());
        txtNomPelicula.setText(FilmDataSource.films.get(position).getTitle().toString());
        txtNomDirector.setText(FilmDataSource.films.get(position).getDirector().toString());

        String anyo = String.valueOf(FilmDataSource.films.get(position).getYear());
        txtNumAnyo.setText(anyo);

        String formatoGenero = "";

        int formato = FilmDataSource.films.get(position).getFormat();
        switch (formato){
            case 0:
                formatoGenero = "DVD, ";
                break;
            case 1:
                formatoGenero = "Bluray, ";
                break;
            case 2:
                formatoGenero = "Digital, ";
                break;
        }

        int genero = FilmDataSource.films.get(position).getGenre();
        switch (genero){
            case 0:
                formatoGenero = formatoGenero + "Action";
                break;
            case 1:
                formatoGenero = formatoGenero + "Comedy";
                break;
            case 2:
                formatoGenero = formatoGenero + "Drama";
                break;
            case 3:
                formatoGenero = formatoGenero + "Scifi";
                break;
            case 4:
                formatoGenero = formatoGenero + "Horror";
                break;
        }

        txtFormatoGenero.setText(formatoGenero);

        txtComentario.setText(FilmDataSource.films.get(position).getComments().toString());

        btnWebIMDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirigiendote a la página de IMDB de la película...", Toast.LENGTH_SHORT).show();
                Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(FilmDataSource.films.get(position).getImdbURL()));
                startActivity(intentWeb);
            }
        });

        btnVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Volviendo al menú principal", Toast.LENGTH_SHORT).show();
                Intent intentVolverMenu = new Intent(FilmDataActivity.this, FilmListActivity.class);
                startActivity(intentVolverMenu);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Editar los datos de la Película", Toast.LENGTH_SHORT).show();
            }
        });



    }
}