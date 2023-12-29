package esp.daniel.filmoteca_danielgarcia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class FilmDataActivity extends AppCompatActivity {

    private static final int EDIT_OPTION = 16;
    String anyo, formatoGenero;
    int position, genero, formato;
    ImageView imgView;
    TextView txtComentario, txtFormatoGenero, txtNumAnyo, txtNomDirector, txtNomPelicula;
    Button btnWebIMDB, btnVolverMenu, btnEditar;
    View mensaje_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_data_activity);

        mensaje_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

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

        //Asignamos los valores de la película que pulsamos en la FilmListActivity al layout
        imgView.setImageResource(FilmDataSource.films.get(position).getImageResId());
        txtNomPelicula.setText(FilmDataSource.films.get(position).getTitle().toString());
        txtNomDirector.setText(FilmDataSource.films.get(position).getDirector().toString());

        anyo = String.valueOf(FilmDataSource.films.get(position).getYear());
        txtNumAnyo.setText(anyo);

        formatoGenero = "";

        formato = FilmDataSource.films.get(position).getFormat();
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

        genero = FilmDataSource.films.get(position).getGenre();
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

        //Botón web IMDB
        btnWebIMDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast mensajeWeb = new Toast(FilmDataActivity.this);
                mensajeWeb.setView(mensaje_layout);

                TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                text.setText("Redirigiendote a la página de IMDB de la película:\n" + FilmDataSource.films.get(position).getTitle());
                mensajeWeb.setDuration(Toast.LENGTH_SHORT);
                mensajeWeb.show();

                Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(FilmDataSource.films.get(position).getImdbURL()));
                startActivity(intentWeb);
            }
        });

        //Botón volver al menú
        btnVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast mensajeMenu = new Toast(FilmDataActivity.this);
                mensajeMenu.setView(mensaje_layout);

                TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                text.setText("Volviendo al menú principal");
                mensajeMenu.setDuration(Toast.LENGTH_SHORT);
                mensajeMenu.show();

                Intent intentVolver = new Intent();
                setResult(RESULT_OK, intentVolver);
                finish();
            }
        });

        //Botón guardar
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast mensajeEdit = new Toast(FilmDataActivity.this);
                mensajeEdit.setView(mensaje_layout);

                TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                text.setText("Editar los datos de la pelicula:\n" + FilmDataSource.films.get(position).getTitle());
                mensajeEdit.setDuration(Toast.LENGTH_SHORT);
                mensajeEdit.show();

                Intent intentFilmEditActivity = new Intent(FilmDataActivity.this, FilmEditActivity.class);
                intentFilmEditActivity.putExtra("FILM_POSITION", position);
                startActivityForResult(intentFilmEditActivity, EDIT_OPTION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_OPTION){
            if (resultCode == RESULT_OK){
                Toast mensajeCambiosAplicados = new Toast(FilmDataActivity.this);
                mensajeCambiosAplicados.setView(mensaje_layout);

                TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                text.setText("Cambios aplicados correctamente.");
                mensajeCambiosAplicados.setDuration(Toast.LENGTH_SHORT);
                mensajeCambiosAplicados.show();

                //Volvermos a cargar los datos del layout, por si se han editado algunos campos.
                txtNomPelicula.setText(FilmDataSource.films.get(position).getTitle().toString());
                txtNomDirector.setText(FilmDataSource.films.get(position).getDirector().toString());
                anyo = String.valueOf(FilmDataSource.films.get(position).getYear());
                txtNumAnyo.setText(anyo);
                btnWebIMDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast mensajeWeb = new Toast(FilmDataActivity.this);
                        mensajeWeb.setView(mensaje_layout);

                        TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                        text.setText("Redirigiendote a la página de IMDB de la película:\n" + FilmDataSource.films.get(position).getTitle());
                        mensajeWeb.setDuration(Toast.LENGTH_SHORT);
                        mensajeWeb.show();

                        Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(FilmDataSource.films.get(position).getImdbURL()));
                        startActivity(intentWeb);
                    }
                });
                txtComentario.setText(FilmDataSource.films.get(position).getComments().toString());

                formatoGenero = "";
                formato = FilmDataSource.films.get(position).getFormat();
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

                genero = FilmDataSource.films.get(position).getGenre();
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


            } else {
                Toast mensajeCambiosCancelados = new Toast(FilmDataActivity.this);
                mensajeCambiosCancelados.setView(mensaje_layout);

                TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                text.setText("Los cambios han sido cancelados.");
                mensajeCambiosCancelados.setDuration(Toast.LENGTH_SHORT);
                mensajeCambiosCancelados.show();
            }
        }

    }
}