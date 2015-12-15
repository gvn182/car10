package simple.com.car10;

/**
 * Created by Giovanni on 01/07/2014.
 */
public class Weather {
    public String ocorrencia;
    public String data;
    public String placa;
    public String img;
    public String titulo;
    public String id;
    public String textColorHex;
    public Weather(){
        super();
    }

    public Weather(String id, String ocorrencia, String data, String placa, String img, String textoimg, String textColorHex) {
        super();
        this.ocorrencia = ocorrencia;
        this.data = data;
        this.placa = placa;
        this.img = img;
        this.titulo = textoimg;
        this.id = id;
        this.textColorHex = textColorHex;

    }
}