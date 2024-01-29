package esp.daniel.filmoteca_danielgarcia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    SQLiteDatabase db;
    String user, password;
    EditText txtUserName, txtPassword;
    Button btnSignIn, btnSignUp;
    ImageView imgLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgLogin = (ImageView) findViewById(R.id.imgLogin);
       // imgLogin.setImageDrawable(R.drawable.logo);

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        db = openOrCreateDatabase("UsersFilmLibrary", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(User VARCHAR, Password VARCHAR);");
    }

    private void signIn(){
        Toast.makeText(this, "SIGN IN", Toast.LENGTH_SHORT).show();
    }

    private void signUp(){
        Toast.makeText(this, "SIGN OUT", Toast.LENGTH_SHORT).show();
    }
}