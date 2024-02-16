package esp.daniel.filmoteca_danielgarcia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FilmDataActivity extends AppCompatActivity {
    SQLiteDatabase db;

    private static final int EDIT_OPTION = 16;
    int position;
    ImageView imgView;
    TextView txtComentario, txtFormatoGenero, txtNumAnyo, txtNomDirector, txtNomPelicula;
    Button btnWebIMDB, btnVolverMenu, btnEditar;
    View mensaje_layout;
    ArrayList<Film> filmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_data_activity);

        filmList = new ArrayList<>();

        mensaje_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        db = openOrCreateDatabase("FilmSource", Context.MODE_PRIVATE, null);

        //Creamos el contenido del layout
        imgView = (ImageView) findViewById(R.id.imgPeli);
        txtNomPelicula = (TextView) findViewById(R.id.txtNomPelicula);
        txtNomDirector = (TextView) findViewById(R.id.txtNomDirector);
        txtNumAnyo = (TextView) findViewById(R.id.txtNumAnyo);
        txtFormatoGenero = (TextView) findViewById(R.id.txtFormatoGenero);
        txtComentario = (TextView) findViewById(R.id.txtComentario);
        txtComentario.setMovementMethod(new ScrollingMovementMethod());
        btnWebIMDB = (Button) findViewById(R.id.btnWebIMDB);
        btnVolverMenu = (Button) findViewById(R.id.btnVolverMenu);
        btnEditar = (Button) findViewById(R.id.btnEditar);

        //Coger la posición de la película mediante el intent
        Intent intent = getIntent();
        position = intent.getIntExtra("FILM_POSITION", 0);

        filmData(listFilmsFromBBDD());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_OPTION){
            if (resultCode == RESULT_OK){
                filmList.clear();
                filmData(listFilmsFromBBDD());
            } else {
                showToast("Los cambios han sido cancelados.");
            }
        }

    }

    private void showToast(String message){
        Toast mensajeFilmData = new Toast(FilmDataActivity.this);
        mensajeFilmData.setView(mensaje_layout);

        TextView texto = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
        texto.setText(message);
        mensajeFilmData.setDuration(Toast.LENGTH_SHORT);
        mensajeFilmData.show();
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
        c.close();
        return filmList.get(position);
    }

    private void filmData(Film film){

        if (film != null) {

            imgView.setImageResource(film.getImageResId());
            txtNomPelicula.setText(film.getTitle());
            txtNomDirector.setText(film.getDirector());
            String year = String.valueOf(film.getYear());
            txtNumAnyo.setText(year);

            String formato = getFormatoFromFilm(film);
            String genero = getGeneroFromFilm(film);
            txtFormatoGenero.setText(formato + genero);

            //Botón web IMDB
            btnWebIMDB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("Redirigiendote a la página de IMDB de la película:\n" + film.getTitle());

                    Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(film.getImdbURL()));
                    startActivity(intentWeb);
                }
            });

            txtComentario.setText(film.getComments());

            //Botón volver al menú
            btnVolverMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("Volviendo al menú principal");
                    setResult(RESULT_OK);
                    finish();
                }
            });

            //Botón guardar
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("Editar los datos de la pelicula:\n" + film.getTitle());

                    Intent intentFilmEditActivity = new Intent(FilmDataActivity.this, FilmEditActivity.class);
                    intentFilmEditActivity.putExtra("FILM_POSITION", position);
                    startActivityForResult(intentFilmEditActivity, EDIT_OPTION);
                }
            });
        }

    }

    public String getFormatoFromFilm(Film film){
        int posiconFormato = film.getFormat();

        switch (posiconFormato){
            case 0:
                return "DVD, ";
            case 1:
                return "Bluray, ";
            case 2:
                return "Digital, ";
            default:
                return "";
        }
    }

    public String getGeneroFromFilm(Film film){
        int posicioGenero = film.getGenre();

        switch (posicioGenero){
            case 0:
                return "Action";
            case 1:
                return "Comedy";
            case 2:
                return "Drama";
            case 3:
                return "Scifi";
            case 4:
                return "Horror";
            default:
                return "";
        }
    }
}