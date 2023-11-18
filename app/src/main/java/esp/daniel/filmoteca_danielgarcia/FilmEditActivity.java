package esp.daniel.filmoteca_danielgarcia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class FilmEditActivity extends AppCompatActivity {

    int posicion;
    ImageView imgFilm;
    Button btnGuardar, btnCapturarImg, btnSelectImg, btnCancelar;
    Spinner spnFormato, spnGenero;
    EditText txtEditTitulo, txtEditDirector, txtEditAnyo, txtEditWeb, txtEditComentario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_film);

        //Posición del intent
        Intent intentFilmDataActivity = getIntent();
        posicion = intentFilmDataActivity.getIntExtra("FILM_POSITION", 0);

        //Imagen película
        imgFilm = (ImageView) findViewById(R.id.imgFilm);
        imgFilm.setImageResource(FilmDataSource.films.get(posicion).getImageResId());

        //Botón capturar imagen
        btnCapturarImg = (Button) findViewById(R.id.btnCapturarImg);
        btnCapturarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Funcionalidad no implementada", Toast.LENGTH_SHORT).show();
            }
        });

        //Botón seleccionar imagen
        btnSelectImg = (Button) findViewById(R.id.btnSelectImg);
        btnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Funcionalidad no implementada", Toast.LENGTH_SHORT).show();
            }
        });

        //EDIT TEXT (todos)
            //Titulo
        txtEditTitulo = (EditText) findViewById(R.id.txtEditTitulo);
        txtEditTitulo.setText(FilmDataSource.films.get(posicion).getTitle().toString());

            //Director
        txtEditDirector = (EditText) findViewById(R.id.txtEditDirector);
        txtEditDirector.setText(FilmDataSource.films.get(posicion).getDirector().toString());

            //Año
        txtEditAnyo = (EditText) findViewById(R.id.txtEditAnyo);
        String anyo = String.valueOf(FilmDataSource.films.get(posicion).getYear());
        txtEditAnyo.setText(anyo);

            //Web
        txtEditWeb = (EditText) findViewById(R.id.txtEditWeb);
        txtEditWeb.setText(FilmDataSource.films.get(posicion).getImdbURL().toString());

            //Comentario
        txtEditComentario = (EditText) findViewById(R.id.txtEditComentario);
        txtEditComentario.setText(FilmDataSource.films.get(posicion).getComments().toString());
        


    }
}