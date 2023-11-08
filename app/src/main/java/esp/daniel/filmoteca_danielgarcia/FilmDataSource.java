package esp.daniel.filmoteca_danielgarcia;

import java.util.ArrayList;

public class FilmDataSource {
    public static ArrayList<Film> films;
    public static void Inizialize(){
        films = new ArrayList<Film>();

        films.add(new Film(
            R.drawable.interestelar,
            "Interestelar",
            "Cristopher Nolan",
            2014,
            Film.GENRE_SCIFI,
            Film.FORMAT_DVD,
            "https://www.imdb.com/title/tt0816692/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_Inte",
            "Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento de garantizar la supervivencia de la humanidad."
        ));

    }
}
