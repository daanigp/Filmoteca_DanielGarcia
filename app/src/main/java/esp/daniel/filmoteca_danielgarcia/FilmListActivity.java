package esp.daniel.filmoteca_danielgarcia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class FilmListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        FilmDataSource.Inizialize();

        ListView listaPeliculas = (ListView) findViewById(R.id.listaPeliculas);
        FilmAdapter adapter = new FilmAdapter(this, R.layout.item_film, FilmDataSource.films);

        listaPeliculas.setAdapter(adapter);

        listaPeliculas.setOnItemClickListener(this);
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
                Toast.makeText(getApplicationContext(), "Has pulsado sobre Acerca de.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FilmListActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.itemNuevaPeli:
                Toast.makeText(getApplicationContext(), "Has pulsado sobre Añadir película.", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, "Has pulsado sobre la pelicula -> " + FilmDataSource.films.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(FilmListActivity.this, FilmDataActivity.class);
        intent.putExtra("FILM_POSITION", position);
        startActivity(intent);
    }
}