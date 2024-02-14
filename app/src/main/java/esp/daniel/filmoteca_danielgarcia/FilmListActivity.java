package esp.daniel.filmoteca_danielgarcia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.util.ArrayList;

public class FilmListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SQLiteDatabase db;
    private final String CANAL_ID="33";
    private static int DATA_FILM = 2;
    FilmAdapter filmAdapter;
    ListView listaPeliculas;
    View mensaje_layout;

    ArrayList<Film> filmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        mensaje_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        //FilmDataSource.Inizialize();

        listarBBDD();

        listaPeliculas = (ListView) findViewById(R.id.listaPeliculas);
        //filmAdapter = new FilmAdapter(this, R.layout.item_film, FilmDataSource.films);
        filmAdapter = new FilmAdapter(this, R.layout.item_film, filmList);


        listaPeliculas.setAdapter(filmAdapter);
        listaPeliculas.setOnItemClickListener(this);

        //Menú contextual
        registerForContextMenu(listaPeliculas);

        db = openOrCreateDatabase("FilmSource", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS film(Id INTEGER PRIMARY KEY AUTOINCREMENT, Image INTEGER, Title VARCHAR, Director VARCHAR, Year INTEGER, Genre INTEGER, Format INTEGER, ImdURL VARCHAR, Comments VARCHAR);");
    }

    //Menú desplegable
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    //Menú desplegable
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.itemAcercaDe:
                //Nos lleva a la actividad de AboutActivity
                showToast("Has pulsado sobre Acerca de.");

                Intent intentAbout = new Intent(FilmListActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                return true;
            case R.id.itemNuevaPeli:
                //Añade una nueva película al ArrayList
                /*Toast mensaje2 = new Toast(FilmListActivity.this);
                mensaje2.setView(mensaje_layout);

                TextView texto2 = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                texto2.setText("Has pulsado sobre Añadir película.");
                mensaje2.setDuration(Toast.LENGTH_SHORT);
                mensaje2.show();*/
                showToast("Has pulsado sobre Añadir película.");

                Film pelicula = new Film(
                        R.drawable.icono_img,
                        "Agregar película",
                        "Agregar director",
                        0000,
                        Film.FORMAT_DVD,
                        Film.GENRE_ACTION,
                        "Agregar url",
                        "Agregar comentario"

                );
                //FilmDataSource.films.add(pelicula);
                filmList.add(pelicula);
                filmAdapter.notifyDataSetChanged();
                //listarBBDD();

                mostrarNotificacion(true, true, pelicula);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Cuando se pulsa sobre una película
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(FilmListActivity.this, FilmDataActivity.class);
        intent.putExtra("FILM_POSITION", position);
        startActivityForResult(intent, DATA_FILM);
    }

    //Para que cuando se pulse en el botón de volver en la actividad FilmDataActivity,
    //Se actualicen los datos a los datos que se han cambiado, si esque hay alguno cambiado/editado.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DATA_FILM){
            if (resultCode == RESULT_OK){
                filmAdapter.notifyDataSetChanged();
            }
        }
    }

    //Creación del menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //Utilizar el menú contextual
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Film pelicula = (Film) listaPeliculas.getItemAtPosition(info.position);
        int id = item.getItemId();

        if (id == R.id.eliminar){
            mostrarMenuEmergente(pelicula.getTitle(), pelicula);
        }

        return super.onContextItemSelected(item);
    }

    private void mostrarMenuEmergente(String titulo, Film pelicula){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Película");
        builder.setMessage("¿Estás seguro de eliminar la película con título " + titulo + "?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //FilmDataSource.films.remove(pelicula);
                if (borrarPelicula(pelicula)) {
                    filmAdapter.notifyDataSetChanged();
                    //listarBBDD();
                    showToast("Has eliminado -> " + titulo);
                } else {
                    showToast("No se puede eliminar la pelicula");
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("TRANQUILO! No has eliminado ninguna película");
            }
        });

        builder.show();
    }

    private void mostrarNotificacion(boolean expandible, boolean actividad, Film pelicula){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CANAL_ID);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);

        if (expandible && actividad){
            NotificationCompat.InboxStyle estilo = new NotificationCompat.InboxStyle();
            estilo.setBigContentTitle("Nueva pelicula creada");

            String[] lineas = new String[7];
            lineas[0] = pelicula.getTitle().toString();
            lineas[1] = pelicula.getDirector().toString();
            lineas[2] = String.valueOf(pelicula.getYear());
            lineas[3] = obtenerGenero(pelicula.getGenre());
            lineas[4] = obtenerFormato(pelicula.getFormat());
            lineas[5] = pelicula.getImdbURL().toString();
            lineas[6] = pelicula.getComments().toString();

            for (int i = 0; i<lineas.length; i++){
                estilo.addLine(lineas[i]);
            }

            builder.setStyle(estilo);

            //int position = FilmDataSource.films.size() - 1;
            int position = filmList.size() - 1;

            Intent intentEditFilm = new Intent(FilmListActivity.this, FilmEditActivity.class);
            intentEditFilm.putExtra("FILM_POSITION", position);

            PendingIntent pending = PendingIntent.getActivity(this, position, intentEditFilm, PendingIntent.FLAG_IMMUTABLE);

            builder.setContentIntent(pending);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal = new NotificationChannel(CANAL_ID, "Titulo del canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(canal);
        }

        Notification notification = builder.build();
        notificationManager.notify(Integer.parseInt(CANAL_ID), notification);
    }

    public String obtenerFormato(int posiconFormato){
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

    public String obtenerGenero(int posicioGenero){
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

    private void showToast(String message){
        Toast mensajeFilmList = new Toast(FilmListActivity.this);
        mensajeFilmList.setView(mensaje_layout);

        TextView texto = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
        texto.setText(message);
        mensajeFilmList.setDuration(Toast.LENGTH_SHORT);
        mensajeFilmList.show();
    }

    private void listarBBDD(){
        filmList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM film", null);
        if(c.getCount() != 0){
            while(c.moveToNext()){
                Film film = new Film();
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
    }

    private boolean borrarPelicula(Film pelicula){
        boolean borrado = false;
        Cursor c = db.rawQuery("SELECT * FROM film WHERE Title = '" + pelicula.getTitle() + "' AND Director = '" + pelicula.getDirector() + "';", null);
        if (c.getCount() != 0){
            db.execSQL("DELETE FROM film WHERE Title = '" + pelicula.getTitle() + "' AND Director = '" + pelicula.getDirector() + "';");
            borrado = true;
        }
        c.close();
        return borrado;
    }
}