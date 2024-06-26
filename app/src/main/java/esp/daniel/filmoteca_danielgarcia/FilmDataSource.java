package esp.daniel.filmoteca_danielgarcia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FilmDataSource {
    public static ArrayList<Film> films;
    public static void Inizialize(){
        films = new ArrayList<Film>();

        films.add(new Film(
            1,
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
            2,
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
            3,
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
            4,
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
            5,
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
            6,
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
            7,
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
            8,
            R.drawable.avengers4,
            "Vengadores: End Game",
            "Anthony Russo - Joe Russo",
            2019,
            Film.GENRE_ACTION,
            Film.FORMAT_DIGITAL,
            "https://www.imdb.com/title/tt4154796/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_AVENGERS",
            "Después de que Thanos haya aniquilado a la mitad del universo, los Vengadores supervivientes deben hacer todo lo posible por deshacer tal atrocidad."
        ));

        films.add(new Film(
            9,
            R.drawable.ted,
            "Ted",
            "Seth MacFarlane",
            2012,
            Film.GENRE_COMEDY,
            Film.FORMAT_DVD,
            "https://www.imdb.com/title/tt1637725/?ref_=nv_sr_srsg_3_tt_4_nm_4_q_ted",
            "John Bennett, un hombre cuyo deseo de la infancia de dar vida a su oso de peluche se hizo realidad, ahora debe decidir entre mantener la relación con el oso, Ted, o su novia, Lori."
        ));

        films.add(new Film(
                10,
                R.drawable.ted2,
                "Ted 2",
                "Seth MacFarlane",
                2015,
                Film.GENRE_COMEDY,
                Film.FORMAT_DIGITAL,
                "https://www.imdb.com/title/tt2637276/?ref_=nv_sr_srsg_8_tt_4_nm_4_q_ted",
                "La pareja de recién casados Ted y Tami-Lynn quieren tener un bebé, pero para poder ser padre, Ted tendrá que demostrar que es una persona en un tribunal de justicia."
        ));

        films.add(new Film(
                11,
                R.drawable.oppenheimer,
                "Oppenheimer",
                "Christopher Nolan",
                2023,
                Film.GENRE_DRAMA,
                Film.FORMAT_DIGITAL,
                "https://www.imdb.com/title/tt15398776/?ref_=chttp_i_57",
                "La historia del científico J. Robert Oppenheimer y su rol en el desarrollo de la bomba atómica."
        ));

        films.add(new Film(
                12,
                R.drawable.en_busca_del_arca_perdida,
                "En busca del arca perdida",
                "Steven Spielberg",
                1981,
                Film.GENRE_ACTION,
                Film.FORMAT_BLURAY,
                "https://www.imdb.com/title/tt0082971/?ref_=chttp_t_58",
                "En 1936, el arqueólogo y aventurero Indiana Jones es contratado por el Gobierno de Estados Unidos para encontrar el Arca de la Alianza, antes de que los nazis de Adolf Hitler puedan obtener sus impresionantes poderes."
        ));

        films.add(new Film(
                13,
                R.drawable.loca_academia_de_policia,
                "Loca academia de policia",
                "Hugh Wilson",
                1984,
                Film.GENRE_COMEDY,
                Film.FORMAT_BLURAY,
                "https://www.imdb.com/title/tt0087928/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_loca%2520academia%2520de%2520policia",
                "Un grupo de inadaptados de buen corazón, pero incompetentes, entra en la academia de policía, pero los instructores de la misma no van a soportar sus bromas."

        ));

        films.add(new Film(
                14,
                R.drawable.saw,
                "Saw",
                "James Wan",
                2004,
                Film.GENRE_HORROR,
                Film.FORMAT_DVD,
                "https://www.imdb.com/title/tt0387564/?ref_=nv_sr_srsg_3_tt_8_nm_0_q_saw",
                "Dos extraños, que se despiertan en una habitación sin recordar cómo llegaron allí, pronto descubren que son peones en un juego mortal perpetrado por un famoso asesino en serie."
        ));

        films.add(new Film(
                15,
                R.drawable.jurassic_park,
                "Jurassic Park",
                "Steven Spielberg",
                1993,
                Film.GENRE_SCIFI,
                Film.FORMAT_BLURAY,
                "https://www.imdb.com/title/tt0107290/?ref_=chttp_t_143",
                "Gracias al ADN fosilizado en ámbar, John Hammond da vida a varias especies de dinosaurios y crea Jurassic Park, un parque temático en una isla de Costa Rica. Pero lo que parecía un sueño se convierte rápidamente en pesadilla."
        ));

        films.add(new Film(
                16,
                R.drawable.jurassic_world,
                "Jurassic World",
                "Colin Trevorrow",
                2015,
                Film.GENRE_ACTION,
                Film.FORMAT_DIGITAL,
                "https://www.imdb.com/title/tt0369610/?ref_=nv_sr_srsg_3_tt_8_nm_0_q_jurassic",
                "Un nuevo parque temático, construido en el emplazamiento original de Jurassic Park, crea un dinosaurio híbrido modificado genéticamente, el Indominus rex, que escapa a la contención y se lanza a la matanza."

        ));

        films.add(new Film(
                17,
                R.drawable.milla_verde,
                "La milla verde",
                "Frank Darabont",
                1999,
                Film.GENRE_DRAMA,
                Film.FORMAT_BLURAY,
                "https://www.imdb.com/title/tt0120689/?ref_=nv_sr_srsg_0_tt_3_nm_5_q_la%2520milla%2520verde",
                "Las vidas de los guardias del corredor de la muerte se ven afectadas por uno de sus reclusos: un hombre negro acusado de asesinato y violación de niños, pero que tiene un misterioso don."
        ));

    }
}
