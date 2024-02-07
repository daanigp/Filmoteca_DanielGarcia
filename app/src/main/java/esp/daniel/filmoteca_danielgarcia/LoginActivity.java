package esp.daniel.filmoteca_danielgarcia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText txtUsername, txtPassword;
    Button btnSignIn, btnSignUp;
    ImageView imgLogin;

    View mensaje_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mensaje_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        imgLogin = (ImageView) findViewById(R.id.imgLogin);
        imgLogin.setImageResource(R.drawable.logo);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, password;
                user = txtUsername.getText().toString();
                password = txtPassword.getText().toString();
                signIn(user, password);
            }
        });

        btnSignUp = (Button) findViewById(R.id.btnGoToSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        db = openOrCreateDatabase("UsersFilmLibrary", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(User VARCHAR, Password VARCHAR);");
    }

    private void signIn(String user, String pwd){
        Cursor c = db.rawQuery("SELECT * FROM users WHERE User = '" + user + "' AND Password = '" + pwd + "'", null);
        if (c.getCount() == 0){
            showToast("No existe ningun usuario con nombre -> " + user + " y contraseña -> " + pwd);
            c.close();
        } else {
            c.close();
            showToast("Usuario logeado correctamente...");
            Intent intentFilmListActivity = new Intent(LoginActivity.this, FilmListActivity.class);
            startActivity(intentFilmListActivity);
        }
    }

    private void signUp(){
        showToast("Crea tu nuevo usuario.");
        Intent intentSignUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intentSignUpActivity);
    }

    private void showToast(String message){
        Toast mensajeLogin = new Toast(LoginActivity.this);
        mensajeLogin.setView(mensaje_layout);

        TextView texto = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
        texto.setText(message);
        mensajeLogin.setDuration(Toast.LENGTH_SHORT);
        mensajeLogin.show();
    }
}