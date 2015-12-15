package simple.com.car10;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by Giovanni on 01/07/2014.
 */
public class Orcamento {
    public String dataAbertura;
    public String distancia;
    public String cortesia;
    public String pecasATrocar;
    public String pecasARecuperar;
    public String totalMO;
    public String desconto;
    public String forma;
    public String myID;
    public Drawable imagemOficina;
    public String NomeOficina;
    public boolean naolido;
    public Drawable imgNota;
    public String TempoConserto;
    public String Validade;
    public String Valor;
    public float Aval;
    public String Tempo;
    public String EmpresaID;
    public String PecasATrocarValor;
    public Orcamento(){
        super();
    }


    public Orcamento(Drawable imagemOficina, String NomeOficina, boolean naolido, Drawable imgNota, String TempoConserto, String Validade, String Valor,
                     String dataAbertura, String distancia, String cortesia, String pecasATrocar, String pecasARecuperar, String totalMO, String desconto, String forma, String myID, float Aval, String Tempo,String EmpresaID, String PecasATrocarValor) {

        super();
        this.imagemOficina = imagemOficina;
        this.NomeOficina = NomeOficina;
        this.naolido = naolido;
        this.imgNota = imgNota;
        this.TempoConserto = TempoConserto;
        this.Validade = Validade;
        this.Valor = Valor;
        this.dataAbertura = dataAbertura;
        this.distancia = distancia;
        this.cortesia = cortesia;
        this.pecasATrocar = pecasATrocar;
        this.pecasARecuperar = pecasARecuperar;
        this.totalMO = totalMO;
        this.desconto = desconto;
        this.forma = forma;
        this.myID = myID;
        this.Aval = Aval;
        this.Tempo = Tempo;
        this.EmpresaID = EmpresaID;
        this.PecasATrocarValor = PecasATrocarValor;
    }
}