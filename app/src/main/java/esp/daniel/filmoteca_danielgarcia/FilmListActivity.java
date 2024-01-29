package esp.daniel.filmoteca_danielgarcia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;

public class FilmListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static int DATA_FILM = 2;
    FilmAdapter filmAdapter;
    ListView listaPeliculas;
    View mensaje_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        mensaje_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        FilmDataSource.Inizialize();

        listaPeliculas = (ListView) findViewById(R.id.listaPeliculas);
        filmAdapter = new FilmAdapter(this, R.layout.item_film, FilmDataSource.films);

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
                Toast mensaje = new Toast(FilmListActivity.this);
                mensaje.setView(mensaje_layout);

                TextView texto = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                texto.setText("Has pulsado sobre Acerca de.");
                mensaje.setDuration(Toast.LENGTH_SHORT);
                mensaje.show();
                Intent intentAbout = new Intent(FilmListActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                return true;
            case R.id.itemNuevaPeli:
                //Añade una nueva película al ArrayList
                Toast mensaje2 = new Toast(FilmListActivity.this);
                mensaje2.setView(mensaje_layout);

                TextView texto2 = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                texto2.setText("Has pulsado sobre Añadir película.");
                mensaje2.setDuration(Toast.LENGTH_SHORT);
                mensaje2.show();
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
                FilmDataSource.films.add(pelicula);
                filmAdapter.notifyDataSetChanged();
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
                Toast mensaje = new Toast(FilmListActivity.this);
                mensaje.setView(mensaje_layout);

                TextView texto = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                texto.setText("Has eliminado -> " + titulo);
                mensaje.setDuration(Toast.LENGTH_SHORT);
                mensaje.show();
                FilmDataSource.films.remove(pelicula);
                filmAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast mensaje = new Toast(FilmListActivity.this);
                mensaje.setView(mensaje_layout);

                TextView texto = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
                texto.setText("TRANQUILO! No has eliminado ninguna película");
                mensaje.setDuration(Toast.LENGTH_SHORT);
                mensaje.show();
            }
        });

        builder.show();
    }
}