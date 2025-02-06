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
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FilmListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SQLiteDatabase db;
    private final String CANAL_ID="33";
    private static int DATA_FILM = 2;
    FilmAdapter filmAdapter;
    ListView listaPeliculas;
    View mensaje_layout;
    ArrayList<Film> filmList;
    Random rnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        filmList = new ArrayList<>();
        rnd = new Random();

        mensaje_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        db = openOrCreateDatabase("FilmSource", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS film(Id INTEGER PRIMARY KEY, Image INTEGER, Title VARCHAR, Director VARCHAR, Year INTEGER, Genre INTEGER, Format INTEGER, ImdURL VARCHAR, Comments VARCHAR);");

        listarBBDD();

        listaPeliculas = (ListView) findViewById(R.id.listaPeliculas);
        filmAdapter = new FilmAdapter(getApplicationContext(), R.layout.item_film, filmList);

        listaPeliculas.setAdapter(filmAdapter);
        listaPeliculas.setOnItemClickListener(this);

        //Menú contextual
        registerForContextMenu(listaPeliculas);

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
            case R.id.itemNuevaPeliVacia:
                //Añade una nueva película al ArrayList
                showToast("Has pulsado sobre Añadir película vacía.");

                Film pelicula = new Film(
                        filmList.size(),
                        selectImg(),
                        "Agregar película",
                        "Agregar director",
                        0000,
                        selectFormat(),
                        selectGenre(),
                        "Agregar url",
                        "Agregar comentario"

                );
                insertFilmToBBDD(pelicula);
                filmList.add(pelicula);
                filmAdapter.notifyDataSetChanged();

                mostrarNotificacion(true, true, pelicula);
                return true;

            case R.id.itemMasInfo:
                //Nos lleva a la actividad MoreActivity
                Intent intentMasInfo = new Intent(FilmListActivity.this, MoreActivity.class);
                startActivity(intentMasInfo);
                return true;

            case R.id.itemSalir:
                Intent intentSalir = new Intent(FilmListActivity.this, LoginActivity.class);
                startActivity(intentSalir);
                return true;

            case R.id.itemAdddefaultFilms:
                menuNumberDefaultFilms();
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
                filmList.clear();
                listarBBDD();
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
                if (borrarPelicula(pelicula)) {
                    filmList.remove(pelicula);
                    filmAdapter.notifyDataSetChanged();
                    showToast("Has eliminado -> " + titulo);
                } else {
                    showToast("No se puede eliminar la pelicula");
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("¡TRANQUILO! No has eliminado ninguna película");
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
    }

    public void insertFilmToBBDD(Film f){
        db.execSQL("INSERT INTO film (Image, Title, Director, Year, Genre, Format, ImdURL, Comments) VALUES (" + f.getImageResId() + ", '" + f.getTitle() + "', '" + f.getDirector() + "', " + f.getYear() + ", " + f.getGenre() + ", " + f.getFormat() + ", '" + f.getImdbURL() + "', '" + f.getComments() + "')");
    }

    private boolean borrarPelicula(Film pelicula){
        boolean borrado = false;
        Cursor c = db.rawQuery("SELECT * FROM film WHERE id = " + pelicula.getId() + ";", null);
        if (c.getCount() != 0){
            db.execSQL("DELETE FROM film WHERE id = " + pelicula.getId() + ";");
            borrado = true;
        }
        c.close();
        return borrado;
    }

    private int selectImg(){
        int numRandom = rnd.nextInt(5);
        switch (numRandom) {
            case 1:
                return R.drawable.icono_img;
            case 2:
                return R.drawable.icono_img2;
            case 3:
                return R.drawable.icono_img3;
            case 4:
                return R.drawable.icono_img4;
            default:
                return R.drawable.icono_img5;
        }
    }

    private int selectFormat(){
        int num = rnd.nextInt(3);
        switch (num){
            case 1:
                return Film.FORMAT_DVD;
            case 2:
                return Film.FORMAT_BLURAY;
            default:
                return Film.FORMAT_DIGITAL;
        }
    }

    private int selectGenre(){
        int num = rnd.nextInt(5);
        switch (num){
            case 1:
                return Film.GENRE_ACTION;
            case 2:
                return Film.GENRE_COMEDY;
            case 3:
                return Film.GENRE_DRAMA;
            case 4:
                return Film.GENRE_SCIFI;
            default:
                return Film.GENRE_HORROR;
        }
    }

    private void menuNumberDefaultFilms() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir películas por defecto");
        builder.setMessage("¿Cuántas películas por defecto deseas añadir?");

        final EditText input = new EditText(this);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String filmsNum = input.getText().toString();
                if (!filmsNum.isEmpty()) {
                    int filmsNumber = Integer.parseInt(filmsNum);
                    addDefaultFilms(filmsNumber);
                } else {
                    showToast("Introduce un número válido");
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("¡TRANQUILO! No has creado ninguna película");
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addDefaultFilms(int num) {
        FilmDataSource.Inizialize();

        ArrayList<Film> totalFilms = FilmDataSource.films;
        Collections.shuffle(totalFilms);

        for (int x = 0; x < num; x++) {
            Film f = totalFilms.get(x);
            if (filmExists(f)) {
                num = num + 1;
            } else {
                f.setId(filmList.size());
                insertFilmToBBDD(f);
                filmList.add(f);
            }
        }

        filmAdapter.notifyDataSetChanged();
    }

    private boolean filmExists(Film film) {
        Cursor c = db.rawQuery("SELECT * FROM film WHERE Title = '" + film.getTitle() + "' AND Director = '" + film.getDirector() + "';", null);
        if (c.getCount() != 0) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }
}