package esp.daniel.filmoteca_danielgarcia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        Button btnSitioWeb = (Button) findViewById(R.id.btnSitioWeb);
        btnSitioWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirigiendote al sitio web.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com"));
                startActivity(intent);
            }
        });

        Button btnSoporte = (Button) findViewById(R.id.btnSoporte);
        btnSoporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Enviando correo electronico...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Soporte Filmoteca.");
                intent.putExtra(Intent.EXTRA_TEXT, "Texto del correo.");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"alumnotarde1@gmail.com"});
                startActivity(intent);
            }
        });

        Button btnVolver = (Button) findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirigiendote a la página principal.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AboutActivity.this, FilmListActivity.class);
                startActivity(intent);
            }
        });
    }
}