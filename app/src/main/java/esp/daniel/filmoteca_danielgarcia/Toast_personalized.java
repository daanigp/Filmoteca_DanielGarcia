package esp.daniel.filmoteca_danielgarcia;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Toast_personalized {
    private String message;
    private Activity activity;
    private View mensaje_layout;
    /*Toast mensajeNoFunciona = new Toast(FilmEditActivity.this);
                mensajeNoFunciona.setView(mensaje_layout);

    TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
    text.setText("Has concedido el permiso para usar la cámara");
    mensajeNoFunciona.setDuration(Toast.LENGTH_SHORT);
    mensajeNoFunciona.show();*/

    public Toast_personalized(String message, Activity activity, View mensaje_layout){
        this.message = message;
        this.activity = activity;
        this.mensaje_layout = mensaje_layout;
    }

    public void CreateToast(){
        Toast toast = new Toast(activity);
        toast.setView(mensaje_layout);

        TextView text = (TextView) mensaje_layout.findViewById(R.id.toastMessage);
        text.setText(message);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
