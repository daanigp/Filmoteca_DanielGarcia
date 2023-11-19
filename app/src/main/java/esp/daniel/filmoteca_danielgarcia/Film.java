package esp.daniel.filmoteca_danielgarcia;

public class Film {

    //Tipos de formatos
    //Almacena el índice en el que se encuentra el valor de cada formato en el recurso arrays.
    public static int FORMAT_DVD = 0;
    public static int FORMAT_BLURAY = 1;
    public static int FORMAT_DIGITAL = 2;

    //Tipos de géneros
    //Almacena el índice en el que se encuentra el valor de cada formato en el recurso arrays
    public static int GENRE_ACTION = 0;
    public static int GENRE_COMEDY = 1;
    public static int GENRE_DRAMA = 2;
    public static int GENRE_SCIFI = 3;
    public static int GENRE_HORROR = 4;

    //Propiedades de la clase
    private int imageResId;
    private String title;
    private String director;
    private int year;
    private int genre;
    private int format;
    private String imdbURL;
    private String comments;

    //Constructor por defecto
   // public Film() {
   // }

    //Constructor por parámetros
    public Film(int imageResId, String title, String director, int year, int genre, int format, String imdbURL, String comments) {
        this.imageResId = imageResId;
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
        this.format = format;
        this.imdbURL = imdbURL;
        this.comments = comments;
    }

    //Getters y setters
    public int getGENRE_ACTION() {
        return GENRE_ACTION;
    }

    public int getGENRE_COMEDY() {
        return GENRE_COMEDY;
    }

    public int getGENRE_DRAMA() {
        return GENRE_DRAMA;
    }

    public int getGENRE_SCIFI() {
        return GENRE_SCIFI;
    }

    public int getGENRE_HORROR() {
        return GENRE_HORROR;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public String getImdbURL() {
        return imdbURL;
    }

    public void setImdbURL(String imdbURL) {
        this.imdbURL = imdbURL;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    //Método para imprimir por pantalla
    @Override
    public String toString() {
        return "Film{" +
                "imageResId=" + imageResId +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", year=" + year +
                ", genre=" + genre +
                ", format=" + format +
                ", imdbURL='" + imdbURL + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
