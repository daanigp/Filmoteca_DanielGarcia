package esp.daniel.filmoteca_danielgarcia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FilmEditActivity extends AppCompatActivity {

    SQLiteDatabase db;
    private final int CANAL_ID = 33;
    private static final int CODIGO_PERMISOS_CAMARA = 123;
    int position;
    ImageView imgFilm;
    Button btnGuardar, btnCapturarImg, btnSelectImg, btnCancelar;
    Spinner spnFormato, spnGenero;
    EditText txtEditTitulo, txtEditDirector, txtEditAnyo, txtEditWeb, txtEditComentario;
    View mensaje_layout;
    ArrayList<Film> filmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_film);

        filmList = new ArrayList<>();
        db = openOrCreateDatabase("FilmSource", Context.MODE_PRIVATE, null);

        mensaje_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        //Posición del intent
        Intent intentFilmDataActivity = getIntent();
        position = intentFilmDataActivity.getIntExtra("FILM_POSITION", 0);

        //Para borrar la notificación cuando se pulse sobre ella
        ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).cancel(CANAL_ID);

        imgFilm = (ImageView) findViewById(R.id.imgFilm);
        txtEditTitulo = (EditText) findViewById(R.id.txtEditTitulo);
        txtEditDirector = (EditText) findViewById(R.id.txtEditDirector);
        txtEditAnyo = (EditText) findViewById(R.id.txtEditAnyo);
        txtEditWeb = (EditText) findViewById(R.id.txtEditWeb);
        txtEditComentario = (EditText) findViewById(R.id.txtEditComentario);
        spnGenero = (Spinner) findViewById(R.id.spnGenero);
        spnFormato = (Spinner) findViewById(R.id.spnFormato);

/*
        //Imagen película
        imgFilm.setImageResource(FilmDataSource.films.get(posicion).getImageResId());

        //EDIT TEXT (todos)
            //Titulo
        txtEditTitulo.setText(FilmDataSource.films.get(posicion).getTitle().toString());

            //Director
        txtEditDirector.setText(FilmDataSource.films.get(posicion).getDirector().toString());

            //Año
        String anyo = String.valueOf(FilmDataSource.films.get(posicion).getYear());
        txtEditAnyo.setText(anyo);

            //Web
        txtEditWeb.setText(FilmDataSource.films.get(posicion).getImdbURL().toString());

            //Comentario
        txtEditComentario.setText(FilmDataSource.films.get(posicion).getComments().toString());

        //Spinners (los 2)
            //Generos
        spnGenero.setSelection(FilmDataSource.films.get(posicion).getGenre());

            //Formatos
        spnFormato.setSelection(FilmDataSource.films.get(posicion).getFormat());
*/

        filmData(listFilmsFromBBDD());


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CODIGO_PERMISOS_CAMARA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Has concedido el permiso para usar la cámara");
            } else {
                showToast("Has denegado el permiso para usar la cámara");
            }
        }

    }

    private void showToast(String message){
        Toast mensajeFilmEdit = new Toast(FilmEditActivity.this);
        mensajeFilmEdit.setView(mensaje_layout);

        TextView texto = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
        texto.setText(message);
        mensajeFilmEdit.setDuration(Toast.LENGTH_SHORT);
        mensajeFilmEdit.show();
    }

    private Film listFilmsFromBBDD(){
        Cursor c = db.rawQuery("SELECT * FROM film", null);
        if(c.getCount() != 0){
            while(c.moveToNext()){
                Film film = new Film();
                film.setId(c.getInt(0));
                film.setImageResId(c.getInt(1));
                film.setTitle(c.getString(2));
                film.setDirector(c.getString(3));
                film.setYear(c.getInt(4));
                film.setGenre(c.getInt(5));
                film.setFormat(c.getInt(6));
                film.setImdbURL(c.getString(7));
                film.setComments(c.getString(8));

                filmList.add(film);
            }
        }

        return filmList.get(position);
    }

    private void filmData(Film film){
        //Cursor c = db.rawQuery("SELECT * FROM film WHERE Id = " + posicion + ";", null);
        if (film != null) {
            //Botón capturar imagen
            btnCapturarImg = (Button) findViewById(R.id.btnCapturarImg);
            btnCapturarImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*Toast mensajeNoFunciona = new Toast(FilmEditActivity.this);
                mensajeNoFunciona.setView(mensaje_layout);

                TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                text.setText("Funcionalidad no implementada.");
                mensajeNoFunciona.setDuration(Toast.LENGTH_SHORT);
                mensajeNoFunciona.show();*/
                    int estado = ContextCompat.checkSelfPermission(FilmEditActivity.this, Manifest.permission.CAMERA);

                    if (estado == PackageManager.PERMISSION_GRANTED){
                        Intent intentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivity(intentCamara);
                    } else {
                        ActivityCompat.requestPermissions(FilmEditActivity.this, new String[]{android.Manifest.permission.CAMERA}, CODIGO_PERMISOS_CAMARA);
                    }

                }
            });

            //Botón seleccionar imagen
            btnSelectImg = (Button) findViewById(R.id.btnSelectImg);
            btnSelectImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("Funcionalidad no implementada.");
                }
            });

            imgFilm.setImageResource(film.getImageResId());

            txtEditTitulo.setText(film.getTitle());
            txtEditDirector.setText(film.getDirector());
            txtEditAnyo.setText(String.valueOf(film.getYear()));

            spnGenero.setSelection(film.getGenre());
            spnFormato.setSelection(film.getFormat());

            txtEditWeb.setText(film.getImdbURL());

            txtEditComentario.setText(film.getComments());

            //Botón guardar
            btnGuardar = (Button) findViewById(R.id.btnGuardar);
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Film newFilm = new Film();
                    newFilm.setId(film.getId());
                    newFilm.setImageResId(film.getImageResId());
                    newFilm.setTitle(txtEditTitulo.getText().toString());
                    newFilm.setDirector(txtEditDirector.getText().toString());
                    newFilm.setYear(Integer.parseInt(txtEditAnyo.getText().toString()));
                    newFilm.setGenre(spnGenero.getSelectedItemPosition());
                    newFilm.setFormat(spnFormato.getSelectedItemPosition());
                    newFilm.setImdbURL(txtEditWeb.getText().toString());
                    newFilm.setComments(txtEditComentario.getText().toString());

                    //Actualizamos la película que teníamos creada con los nuevos datos que hemos recogido.
                    db.execSQL("UPDATE film SET Title = '" + newFilm.getTitle() + "',  Director = '" + newFilm.getDirector() + "', Year = " + newFilm.getYear() + ", Genre = " + newFilm.getGenre() + ", Format = " + newFilm.getFormat() + ", ImdURL = '" + newFilm.getImdbURL() + "', Comments = '" + newFilm.getComments() + "' " +
                                "WHERE Id = " + film.getId() + ";");

                    showToast("Pelicula actualizada correctamente.");

                    setResult(RESULT_OK, null);
                    finish();
                }
            });


            //Botón cancelar
            btnCancelar = (Button) findViewById(R.id.btnCancelar);
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
        }
    }
}