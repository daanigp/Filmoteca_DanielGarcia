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

        films.add(new Film(
            R.drawable.forrest_gump,
            "Forrest Gump",
            "Robert Zemeckis",
            1994,
            Film.GENRE_DRAMA,
            Film.FORMAT_BLURAY,
            "https://www.imdb.com/title/tt0109830/?ref_=chttp_t_11",
            "Un hombre amable y simpático vive una serie de acontecimientos extraordinarios, a la vez que inspira a los que le rodean con su permanente optimismo."
        ));

        films.add(new Film(
            R.drawable.cadena_perpetua,
            "Cadena perpetua",
            "Frank Darabont",
            1994,
            Film.GENRE_DRAMA,
            Film.FORMAT_BLURAY,
            "https://www.imdb.com/title/tt0111161/?ref_=chttp_t_1",
            "Andy Dufresne es encarcelado por matar a su esposa y al amante de esta. Tras una dura adaptación, intenta mejorar las condiciones de la prisión y dar esperanza a sus compañeros."
        ));

        films.add(new Film(
            R.drawable.doctor_strange,
            "Doctor Strange",
            "Scott Derrickson",
            2016,
            Film.GENRE_ACTION,
            Film.FORMAT_DVD,
            "https://www.imdb.com/title/tt1211837/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_Doctor%2520Stra",
            "Durante un viaje de curación física y espiritual, un brillante neurocirujano se adentra en el mundo de las artes místicas."
        ));

        films.add(new Film(
            R.drawable.avengers,
            "Los vengadores",
            "Joss Whedon",
            2012,
            Film.GENRE_ACTION,
            Film.FORMAT_DVD,
            "https://www.imdb.com/title/tt0848228/?ref_=nv_sr_srsg_6_tt_8_nm_0_q_Vengadores",
            "Los héroes más poderosos de la Tierra deben unirse y aprender a luchar en equipo si quieren evitar que Loki y su ejército alienígena esclavicen a la humanidad."
        ));

        films.add(new Film(
            R.drawable.avengers2,
            "Vengadores: La era de Ultrón",
            "Joss Whedon",
            2015,
            Film.GENRE_SCIFI,
            Film.FORMAT_DIGITAL,
            "https://www.imdb.com/title/tt2395427/?ref_=nv_sr_srsg_8_tt_8_nm_0_q_Avengers",
            "Cuando Tony Stark y Bruce Banner intentan poner en marcha un programa de mantenimiento de la paz inactivo llamado Ultrón, las cosas salen terriblemente mal y depende de los héroes más poderosos de la Tierra evitar que el villano Ultrón lleve a cabo su terrible plan."
        ));

        films.add(new Film(
            R.drawable.avengers3,
            "Vengadores: Infinity War",
            "Anthony Russo - Joe Russo",
            2018,
            Film.GENRE_ACTION,
            Film.FORMAT_DIGITAL,
            "https://www.imdb.com/title/tt4154756/?ref_=nv_sr_srsg_3_tt_8_nm_0_q_Vengadores",
            "Los Vengadores y sus aliados estarán dispuestos a sacrificarlo todo para derrotar al poderoso Thanos antes de que su devastación y ruina ponga fin al universo."
        ));

        films.add(new Film(
            R.drawable.avengers4,
            "Vengadores: End Game",
            "Anthony Russo - Joe Russo",
            2019,
            Film.GENRE_ACTION,
            Film.FORMAT_DIGITAL,
            "https://www.imdb.com/title/tt4154796/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_AVENGERS",
            "Después de que Thanos haya aniquilado a la mitad del universo, los Vengadores supervivientes deben hacer todo lo posible por deshacer tal atrocidad."
        ));

    }
}
