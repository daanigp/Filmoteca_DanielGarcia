package esp.daniel.filmoteca_danielgarcia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FilmEditActivity extends AppCompatActivity {

    int posicion;
    ImageView imgFilm;
    Button btnGuardar, btnCapturarImg, btnSelectImg, btnCancelar;
    Spinner spnFormato, spnGenero;
    EditText txtEditTitulo, txtEditDirector, txtEditAnyo, txtEditWeb, txtEditComentario;
    View mensaje_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_film);

        mensaje_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

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
                Toast mensajeNoFunciona = new Toast(FilmEditActivity.this);
                mensajeNoFunciona.setView(mensaje_layout);

                TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                text.setText("Funcionalidad no implementada.");
                mensajeNoFunciona.setDuration(Toast.LENGTH_SHORT);
                mensajeNoFunciona.show();
            }
        });

        //Botón seleccionar imagen
        btnSelectImg = (Button) findViewById(R.id.btnSelectImg);
        btnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast mensajeNoFunciona = new Toast(FilmEditActivity.this);
                mensajeNoFunciona.setView(mensaje_layout);

                TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                text.setText("Funcionalidad no implementada.");
                mensajeNoFunciona.setDuration(Toast.LENGTH_SHORT);
                mensajeNoFunciona.show();
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

        //Spinners (los 2)
            //Generos
        spnGenero = (Spinner) findViewById(R.id.spnGenero);
        spnGenero.setSelection(FilmDataSource.films.get(posicion).getGenre());

            //Formatos
        spnFormato = (Spinner) findViewById(R.id.spnFormato);
        spnFormato.setSelection(FilmDataSource.films.get(posicion).getFormat());

        //Botón guardar
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Actualizamos la película que teníamos creada con los nuevos datos que hemos recogido.
                FilmDataSource.films.get(posicion).setTitle(txtEditTitulo.getText().toString());
                FilmDataSource.films.get(posicion).setDirector(txtEditDirector.getText().toString());
                int year = Integer.parseInt(txtEditAnyo.getText().toString());
                FilmDataSource.films.get(posicion).setYear(year);
                FilmDataSource.films.get(posicion).setImdbURL(txtEditWeb.getText().toString());
                FilmDataSource.films.get(posicion).setComments(txtEditComentario.getText().toString());
                FilmDataSource.films.get(posicion).setFormat(spnFormato.getSelectedItemPosition());
                FilmDataSource.films.get(posicion).setGenre(spnGenero.getSelectedItemPosition());

                setResult(RESULT_OK, null);
                finish();
            }
        });


        //Botón cancelar
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCancel = new Intent();
                setResult(RESULT_CANCELED, intentCancel);
                finish();
            }
        });


    }
}