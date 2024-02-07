package esp.daniel.filmoteca_danielgarcia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    View mensaje_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        mensaje_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        Button btnSitioWeb = (Button) findViewById(R.id.btnSitioWeb);
        btnSitioWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Redirigiendote al sitio web...");

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com"));
                startActivity(intent);
            }
        });

        Button btnSoporte = (Button) findViewById(R.id.btnSoporte);
        btnSoporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Enviando correo electrónico...");

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Soporte Filmoteca.");
                intent.putExtra(Intent.EXTRA_TEXT, "Texto del correo.");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"filmoteca@pmdm.es"});
                startActivity(intent);
            }
        });

        Button btnVolver = (Button) findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Redirigiendote a la página principal.");
                finish();
            }
        });
    }

    private void showToast(String message){
        Toast mensajeAbout = new Toast(AboutActivity.this);
        mensajeAbout.setView(mensaje_layout);

        TextView texto = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
        texto.setText(message);
        mensajeAbout.setDuration(Toast.LENGTH_SHORT);
        mensajeAbout.show();
    }
}