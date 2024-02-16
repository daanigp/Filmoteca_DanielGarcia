package esp.daniel.filmoteca_danielgarcia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    SQLiteDatabase db;

    Button btnSignUp;
    EditText txtUser, txtPassword;
    View mensaje_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mensaje_layout = getLayoutInflater().inflate(R.layout.toast_customized, null);

        txtUser = (EditText) findViewById(R.id.txtUsernameSignUp);
        txtPassword = (EditText) findViewById(R.id.txtPasswordSignUp);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, password;
                user = txtUser.getText().toString();
                password = txtPassword.getText().toString();
                signup(user, password);
            }
        });

        db = openOrCreateDatabase("UsersFilmLibrary", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(User VARCHAR, Password VARCHAR);");
    }

    private void signup(String user, String pwd){
        Cursor c = db.rawQuery("SELECT * FROM users WHERE User = '" + user + "' AND Password = '" + pwd + "'", null);
        if (c.getCount() == 0){
            db.execSQL("INSERT INTO users VALUES('" + user + "', '" + pwd + "');");
            c.close();
            showToast("Se ha registrado correctamente un nuevo usuario.");
            finish();
        } else {
            showToast("Ya existe un registro con ese usuario -> " + user + " y esa contraseña -> " + pwd);
            c.close();
        }
    }

    private void showToast(String message){
        Toast mensajeSignup = new Toast(SignUpActivity.this);
        mensajeSignup.setView(mensaje_layout);

        TextView texto = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
        texto.setText(message);
        mensajeSignup.setDuration(Toast.LENGTH_SHORT);
        mensajeSignup.show();
    }
}