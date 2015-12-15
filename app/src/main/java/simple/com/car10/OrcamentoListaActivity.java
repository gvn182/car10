package simple.com.car10;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class OrcamentoListaActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private Context Act;
    ProgressDialog dialog;
    String DataAbertura;
    String OrcamentoID;
    LinearLayout layoutSemOrcamento;
    SharedPreferences preferences;
    Orcamento[] ocorrencia_data = null;
    List<String> ArrayPecasID;
    List<String> ArrayPecas;
    String Status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orcamento_lista);
        this.Act = this;
        layoutSemOrcamento = (LinearLayout) findViewById(R.id.layoutSemOrcamento);
        layoutSemOrcamento.setVisibility(View.GONE);
        Intent intent = getIntent();
        OrcamentoID = intent.getStringExtra("OrcamentoID");
        String Placa = intent.getStringExtra("Placa");
        DataAbertura = intent.getStringExtra("Data");
        Status = intent.getStringExtra("Status");
        ((TextView)(findViewById(R.id.txtPlaca))).setText(Placa);
        ((TextView)(findViewById(R.id.txtOcorrencia))).setText(OrcamentoID);
        ((TextView)(findViewById(R.id.txtData))).setText(DataAbertura);
        FillSort();
        FillPecaID();
        FillPeca();



        PreparaMenu(savedInstanceState);
        dialog = ProgressDialog.show(this, "", "Carregando...", true);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String  ID = preferences.getString("id", "-1");

        if (ID.equals("-1")) {
            dialog.hide();
            Toast.makeText(this, "É necessário efetuar login para verificar suas ocorrências", Toast.LENGTH_LONG).show();
            Intent reintent = new Intent(this, LoginActivity.class);
            startActivity(reintent);
            finish();
            return;
        }

        GetOcorrencia();

        Spinner  ddOrdenacao =  (Spinner)findViewById(R.id.ddOrdenacao);
        ddOrdenacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                OrdenacaoChanged(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }

    private void FillPeca() {
        ArrayPecas =  new ArrayList<String>();

        ArrayPecas.add("Abraçadeira");
        ArrayPecas.add("Abraçadeira plástica");
        ArrayPecas.add("Acabamento da coluna interna");
        ArrayPecas.add("Acabamento da saia dianteira");
        ArrayPecas.add("Acabamento do botão do banco");
        ArrayPecas.add("Acabamento do friso lateral");
        ArrayPecas.add("Acabamento do suporte do banco");
        ArrayPecas.add("Acabamento do vidro traseiro");
        ArrayPecas.add("Acabamento externo do para-brisa");
        ArrayPecas.add("Acabamento externo retrovisor");
        ArrayPecas.add("Acabamento interno vidro traseiro");
        ArrayPecas.add("Acendedor de cigarros");
        ArrayPecas.add("Adaptador da máquina de vidro");
        ArrayPecas.add("Adaptador de alto-falante");
        ArrayPecas.add("Adaptador de lâmpada");
        ArrayPecas.add("Adesivo coluna da porta");
        ArrayPecas.add("Adesivo com leds");
        ArrayPecas.add("Adesivo de coluna");
        ArrayPecas.add("Adesivo refletor");
        ArrayPecas.add("Aditivo do combustível");
        ArrayPecas.add("Aditivo do óleo do motor");
        ArrayPecas.add("Aditivo para radiador");
        ArrayPecas.add("Aerofólio");
        ArrayPecas.add("Alarme para automóveis");
        ArrayPecas.add("Alarme para Caminhões");
        ArrayPecas.add("Alarme para motos");
        ArrayPecas.add("Alavanca da chave de seta");
        ArrayPecas.add("Alavanca de regulagem do banco");
        ArrayPecas.add("Alça do teto");
        ArrayPecas.add("Algodão para polimento");
        ArrayPecas.add("Alma de aço para-choque dianteiro");
        ArrayPecas.add("Alma de aço para-choque tras.");
        ArrayPecas.add("Alma do para-choque dianteiro");
        ArrayPecas.add("Almofada do para-choque");
        ArrayPecas.add("Alto-falante");
        ArrayPecas.add("Amortecedor da direção");
        ArrayPecas.add("Amortecedor dianteiro");
        ArrayPecas.add("Amortecedor do porta-malas");
        ArrayPecas.add("Amortecedor traseiro");
        ArrayPecas.add("Amplificador de potência");
        ArrayPecas.add("Anel da maçaneta de vidro");
        ArrayPecas.add("Anel da maçaneta do capô traseiro");
        ArrayPecas.add("Antena");
        ArrayPecas.add("Antena do teto");
        ArrayPecas.add("Anti-embaçante");
        ArrayPecas.add("Anti-ruído");
        ArrayPecas.add("Anti-ruído do capô");
        ArrayPecas.add("Aplique aro do farol de milha");
        ArrayPecas.add("Aplique capa do freio de mão");
        ArrayPecas.add("Aplique capa do retrovisor");
        ArrayPecas.add("Aplique da grade");
        ArrayPecas.add("Aplique da grade do para-choque");
        ArrayPecas.add("Aplique da soleira");
        ArrayPecas.add("Aplique da tampa tanque combustível");
        ArrayPecas.add("Aplique do capô");
        ArrayPecas.add("Aplique do para-choque");
        ArrayPecas.add("Aplique do tapete");
        ArrayPecas.add("Apoio de cabeça");
        ArrayPecas.add("Arame de solda");
        ArrayPecas.add("Aro da lanterna");
        ArrayPecas.add("Aro da lanterna traseira");
        ArrayPecas.add("Aro do farol");
        ArrayPecas.add("Aro do quebra vento");
        ArrayPecas.add("Arremate do vidro porta traseira");
        ArrayPecas.add("Arruela");
        ArrayPecas.add("Arruela de pressão");
        ArrayPecas.add("Arruela para calota");
        ArrayPecas.add("Aspirador de pó");
        ArrayPecas.add("Assento cadeirinha automotiva");
        ArrayPecas.add("Assoalho");
        ArrayPecas.add("Assoalho da bateria");
        ArrayPecas.add("Assoalho da caçamba");
        ArrayPecas.add("Assoalho da mala");
        ArrayPecas.add("Atuador de marcha lenta");
        ArrayPecas.add("Auto rádio");
        ArrayPecas.add("Auto rádio mp3 player");
        ArrayPecas.add("Balancim");
        ArrayPecas.add("Bandeja da bateria");
        ArrayPecas.add("Bandeja da suspensão");
        ArrayPecas.add("Barra do limpador do para-brisa");
        ArrayPecas.add("Base da lente do retrovisor");
        ArrayPecas.add("Batente da fechadura capô traseiro");
        ArrayPecas.add("Batente da fechadura da porta");
        ArrayPecas.add("Batente do porta-luvas");
        ArrayPecas.add("Bateria");
        ArrayPecas.add("Bigode de lata");
        ArrayPecas.add("Bloco ótico do farol de milha");
        ArrayPecas.add("Bloqueador automatico");
        ArrayPecas.add("Bobina de ignição");
        ArrayPecas.add("Bóia do tanque de combustível");
        ArrayPecas.add("Boina para polimento");
        ArrayPecas.add("Bolsa da porta");
        ArrayPecas.add("Bomba d´água");
        ArrayPecas.add("Bomba de combustível");
        ArrayPecas.add("Bomba de combustível elétrica");
        ArrayPecas.add("Bomba de óleo");
        ArrayPecas.add("Bomba para calibrar pneus");
        ArrayPecas.add("Bomba para tirar combustível");
        ArrayPecas.add("Bomba reservatório água/gasolina");
        ArrayPecas.add("Borracha crua");
        ArrayPecas.add("Borracha da churrasqueira");
        ArrayPecas.add("Borracha da janela lateral traseira");
        ArrayPecas.add("Borracha do para-brisa");
        ArrayPecas.add("Borracha do para-choque");
        ArrayPecas.add("Borracha do quebra-vento");
        ArrayPecas.add("Borracha do vidro traseiro");
        ArrayPecas.add("Borracha entre os vidros laterais");
        ArrayPecas.add("Borracha interruptor de porta");
        ArrayPecas.add("Borracha rosca do batente");
        ArrayPecas.add("Borracha superior da porta");
        ArrayPecas.add("Botão chave do alarme");
        ArrayPecas.add("Botão da maçaneta");
        ArrayPecas.add("Botão do afogador");
        ArrayPecas.add("Botão do ar");
        ArrayPecas.add("Botão do ar condicionado");
        ArrayPecas.add("Botão do interruptor do farol");
        ArrayPecas.add("Botão do interruptor espelho");
        ArrayPecas.add("Botão do porta-luvas");
        ArrayPecas.add("Botão trava banco traseiro");
        ArrayPecas.add("Braço auxiliar da direção");
        ArrayPecas.add("Braço do capô");
        ArrayPecas.add("Braço do retrovisor");
        ArrayPecas.add("Brake-light");
        ArrayPecas.add("Brilho final");
        ArrayPecas.add("Broca aço rápido");
        ArrayPecas.add("Bronzina da biela");
        ArrayPecas.add("Bronzina de mancal");
        ArrayPecas.add("Bucha da bandeja");
        ArrayPecas.add("Bucha da grade");
        ArrayPecas.add("Bucha da máquina de vidro");
        ArrayPecas.add("Bucha de adaptação p/ volante");
        ArrayPecas.add("Bucha do farol");
        ArrayPecas.add("Bucha do grampo do spoiler");
        ArrayPecas.add("Bucha do para-choque");
        ArrayPecas.add("Bucha do reservatório");
        ArrayPecas.add("Bucha fixa vareta do capô");
        ArrayPecas.add("Bucha pino de porta");
        ArrayPecas.add("Buzina");
        ArrayPecas.add("Cabide da coluna");
        ArrayPecas.add("Cabo");
        ArrayPecas.add("Cabo da embreagem");
        ArrayPecas.add("Cabo da fechadura");
        ArrayPecas.add("Cabo da tampa traseira");
        ArrayPecas.add("Cabo de abertura do capô");
        ArrayPecas.add("Cabo de vela");
        ArrayPecas.add("Cabo do acelerador");
        ArrayPecas.add("Cabo do afogador");
        ArrayPecas.add("Cabo do banco");
        ArrayPecas.add("Cabo do freio de mão");
        ArrayPecas.add("Cabo do velocímetro");
        ArrayPecas.add("Cabo p/ trancar estepe e motos");
        ArrayPecas.add("Cadeirinha automotiva");
        ArrayPecas.add("Caixa acústica");
        ArrayPecas.add("Caixa de fusíveis");
        ArrayPecas.add("Caixa de som");
        ArrayPecas.add("Caixa do porta-luvas");
        ArrayPecas.add("Caixa externa");
        ArrayPecas.add("Caixa interna");
        ArrayPecas.add("Caixa organizadora");
        ArrayPecas.add("Caixa para ferramentas");
        ArrayPecas.add("Caixa satélite");
        ArrayPecas.add("Calço da maçaneta externa");
        ArrayPecas.add("Calço de borracha com rosca do capô");
        ArrayPecas.add("Calço do capô dianteiro");
        ArrayPecas.add("Calha de chuva");
        ArrayPecas.add("Calota aro 13");
        ArrayPecas.add("Calota aro 13 Chevrolet");
        ArrayPecas.add("Calota aro 14");
        ArrayPecas.add("Calota aro 14 Chevrolet");
        ArrayPecas.add("Calota aro 14 Ford");
        ArrayPecas.add("Calota aro 15");
        ArrayPecas.add("Calota aro 15 Chevrolet");
        ArrayPecas.add("Calota do centro da roda");
        ArrayPecas.add("Calota do centro da roda Fiat");
        ArrayPecas.add("Calota do centro da roda Ford");
        ArrayPecas.add("Calota do centro da roda GM");
        ArrayPecas.add("Calota do centro da roda VW");
        ArrayPecas.add("Câmera de marcha ré");
        ArrayPecas.add("Canaleta");
        ArrayPecas.add("Capa da haste do limpador");
        ArrayPecas.add("Capa da maçaneta");
        ArrayPecas.add("Capa de acionamento da chave");
        ArrayPecas.add("Capa de carro");
        ArrayPecas.add("Capa de estepe");
        ArrayPecas.add("Capa de parafuso");
        ArrayPecas.add("Capa de pedal");
        ArrayPecas.add("Capa do retrovisor");
        ArrayPecas.add("Capa do volante");
        ArrayPecas.add("Capa para banco");
        ArrayPecas.add("Capa para motociclista");
        ArrayPecas.add("Capa para motos");
        ArrayPecas.add("Capa para válvula da roda");
        ArrayPecas.add("Capa parafuso da roda");
        ArrayPecas.add("Capa parafuso Revestimento");
        ArrayPecas.add("Capa protetora");
        ArrayPecas.add("Capa regulagem do banco");
        ArrayPecas.add("Capa ventilação do capô");
        ArrayPecas.add("Capacete");
        ArrayPecas.add("Capô dianteiro");
        ArrayPecas.add("Capô traseiro");
        ArrayPecas.add("Carcaça da lanterna traseira");
        ArrayPecas.add("Carcaça de aço da lanterna");
        ArrayPecas.add("Carcaça do farol");
        ArrayPecas.add("Carregador de bateria");
        ArrayPecas.add("Cavalete");
        ArrayPecas.add("CD player");
        ArrayPecas.add("Cebolão do radiador");
        ArrayPecas.add("Celuloide para aplicar massa");
        ArrayPecas.add("Central multimídia");
        ArrayPecas.add("Centralina");
        ArrayPecas.add("Cera");
        ArrayPecas.add("Chapa de aço para remendo");
        ArrayPecas.add("Chapinha");
        ArrayPecas.add("Chapinha da moldura do pára-lama");
        ArrayPecas.add("Chapinha do aro do farol");
        ArrayPecas.add("Chapinha do pára-lama");
        ArrayPecas.add("Chave combinada");
        ArrayPecas.add("Chave de fenda");
        ArrayPecas.add("Chave de roda");
        ArrayPecas.add("Chave de seta");
        ArrayPecas.add("Chave de vela");
        ArrayPecas.add("Chave do limpador do vidro");
        ArrayPecas.add("Chave estrela");
        ArrayPecas.add("Chave L");
        ArrayPecas.add("Chave saca pino de porta");
        ArrayPecas.add("Chave sextavada canhão");
        ArrayPecas.add("Chicote do rádio");
        ArrayPecas.add("Chupeta de bateria");
        ArrayPecas.add("Churrasqueira");
        ArrayPecas.add("Cilindro da chave da porta");
        ArrayPecas.add("Cilindro da chave do contato");
        ArrayPecas.add("Cilindro da roda");
        ArrayPecas.add("Cilindro do capô traseiro");
        ArrayPecas.add("Cilindro mestre do freio");
        ArrayPecas.add("Cinta para rack");
        ArrayPecas.add("Cinto de segurança");
        ArrayPecas.add("Cobertura da lanterna de placa");
        ArrayPecas.add("Coifa da alavanca de câmbio");
        ArrayPecas.add("Coifa da alavanca do freio de mão");
        ArrayPecas.add("Cola de para-brisa");
        ArrayPecas.add("Comando da maçaneta da porta");
        ArrayPecas.add("Componente B (catalizador)");
        ArrayPecas.add("Compressor/Aspirador de ar");
        ArrayPecas.add("Comutador de partida");
        ArrayPecas.add("Conector adaptador para radio");
        ArrayPecas.add("Console");
        ArrayPecas.add("Controle para alarme");
        ArrayPecas.add("Conversor de impedância");
        ArrayPecas.add("Corda do tampão do porta-malas");
        ArrayPecas.add("Corneta");
        ArrayPecas.add("Corneta para alto-falante");
        ArrayPecas.add("Coroa pinhão");
        ArrayPecas.add("Correia com perfil em V");
        ArrayPecas.add("Correia com perfil Poly-V");
        ArrayPecas.add("Correia para comando de válvulas");
        ArrayPecas.add("Coxim do escapamento");
        ArrayPecas.add("Coxim do radiador");
        ArrayPecas.add("Cristalizador de vidros");
        ArrayPecas.add("Cruzeta");
        ArrayPecas.add("Cubo de roda");
        ArrayPecas.add("Cubo para volante");
        ArrayPecas.add("Defletor do pára-choque");
        ArrayPecas.add("Descansa braço");
        ArrayPecas.add("Desodorante");
        ArrayPecas.add("Difusor de roda");
        ArrayPecas.add("Disco de borracha");
        ArrayPecas.add("Disco de freio");
        ArrayPecas.add("Distribuidor T");
        ArrayPecas.add("Dobradiça da portinhola");
        ArrayPecas.add("Dobradiça do quebra-vento");
        ArrayPecas.add("Dobradiça tampa do porta luvas");
        ArrayPecas.add("DVD player");
        ArrayPecas.add("Eixo do limpador de para-brisa");
        ArrayPecas.add("Elástico do bagageiro");
        ArrayPecas.add("Elimina riscos");
        ArrayPecas.add("Emblema");
        ArrayPecas.add("Encosto de cabeça com tela LCD");
        ArrayPecas.add("Enfeite imitação");
        ArrayPecas.add("Engate");
        ArrayPecas.add("Entrada de ar do painel");
        ArrayPecas.add("Entrada de ar lateral externa");
        ArrayPecas.add("Esguicho de água do para-brisa");
        ArrayPecas.add("Esguicho de água do vidro traseiro");
        ArrayPecas.add("Espanador de poeira");
        ArrayPecas.add("Espelho olho de boi");
        ArrayPecas.add("Esponja");
        ArrayPecas.add("Estopa");
        ArrayPecas.add("Estribo");
        ArrayPecas.add("Extensão da lanterna traseira");
        ArrayPecas.add("Extintor de incêndio");
        ArrayPecas.add("Faixa adesiva");
        ArrayPecas.add("Faixa branca do pneu");
        ArrayPecas.add("Farol direito");
        ArrayPecas.add("Farol de milha direito");
        ArrayPecas.add("Farol de socorro");
        ArrayPecas.add("Farol tuning");
        ArrayPecas.add("Fechadura da porta");
        ArrayPecas.add("Fechadura do capô traseiro");
        ArrayPecas.add("Feltro");
        ArrayPecas.add("Filtro da cabine");
        ArrayPecas.add("Filtro de ar do motor");
        ArrayPecas.add("Filtro de combustível");
        ArrayPecas.add("Filtro de óleo");
        ArrayPecas.add("Fivela do cinto segurança retrátil");
        ArrayPecas.add("Fixador janela lateral móvel");
        ArrayPecas.add("Folha de porta");
        ArrayPecas.add("Forração do teto (tapeçaria)");
        ArrayPecas.add("Friso colante");
        ArrayPecas.add("Friso da caçamba");
        ArrayPecas.add("Friso da calha do teto");
        ArrayPecas.add("Friso da grade dianteira");
        ArrayPecas.add("Friso da janela");
        ArrayPecas.add("Friso da lanterna dianteira direira");
        ArrayPecas.add("Friso da lanterna traseira direita");
        ArrayPecas.add("Friso da saia dianteira");
        ArrayPecas.add("Friso da tampa traseira");
        ArrayPecas.add("Friso do capô dianteiro");
        ArrayPecas.add("Friso do capô traseiro");
        ArrayPecas.add("Friso do contorno do pára-lama");
        ArrayPecas.add("Friso do estribo");
        ArrayPecas.add("Friso do farol");
        ArrayPecas.add("Friso do farol de milha");
        ArrayPecas.add("Friso do para-brisa/vidro traseiro");
        ArrayPecas.add("Friso do para-choque");
        ArrayPecas.add("Friso do teto");
        ArrayPecas.add("Friso lateral direito");
        ArrayPecas.add("Fusível");
        ArrayPecas.add("Gancho da caçamba");
        ArrayPecas.add("Gatilho da maçaneta interna");
        ArrayPecas.add("Gel de glicerina");
        ArrayPecas.add("Giroflex 12v com ima");
        ArrayPecas.add("GPS (navegador)");
        ArrayPecas.add("Grade da coluna da porta");
        ArrayPecas.add("Grade dianteira");
        ArrayPecas.add("Grade do para-choque");
        ArrayPecas.add("Grade lateral fixa");
        ArrayPecas.add("Grampo acabamento retrovisor");
        ArrayPecas.add("Grampo apoio da vareta do capô");
        ArrayPecas.add("Grampo da borracha da saia");
        ArrayPecas.add("Grampo da borracha para-choque");
        ArrayPecas.add("Grampo da grade");
        ArrayPecas.add("Grampo da haste da fechadura");
        ArrayPecas.add("Grampo da lanterna dianteira");
        ArrayPecas.add("Grampo da maçaneta");
        ArrayPecas.add("Grampo da maçaneta de vidro");
        ArrayPecas.add("Grampo da moldura do pára-lama");
        ArrayPecas.add("Grampo da pestana");
        ArrayPecas.add("Grampo da placa");
        ArrayPecas.add("Grampo de cabos");
        ArrayPecas.add("Grampo descanso vareta do capô");
        ArrayPecas.add("Grampo do aro do farol");
        ArrayPecas.add("Grampo do banco");
        ArrayPecas.add("Grampo do carpete");
        ArrayPecas.add("Grampo do descansa braço");
        ArrayPecas.add("Grampo do farol");
        ArrayPecas.add("Grampo do fio da vela");
        ArrayPecas.add("Grampo do friso da soleira");
        ArrayPecas.add("Grampo do friso do estribo");
        ArrayPecas.add("Grampo do friso do teto");
        ArrayPecas.add("Grampo do friso lateral");
        ArrayPecas.add("Grampo do para-barro");
        ArrayPecas.add("Grampo do para-choque");
        ArrayPecas.add("Grampo do revestimento capô dianteiro");
        ArrayPecas.add("Grampo do revestimento capô traseiro");
        ArrayPecas.add("Grampo do revestimento da porta");
        ArrayPecas.add("Grampo do revestimento do teto");
        ArrayPecas.add("Grampo do revestimento lateral");
        ArrayPecas.add("Grampo do revestimento lateral mala");
        ArrayPecas.add("Grampo do spoiler lateral");
        ArrayPecas.add("Grampo suporte do bagagito");
        ArrayPecas.add("Graxa");
        ArrayPecas.add("Haste da antena do teto");
        ArrayPecas.add("Haste do limpador");
        ArrayPecas.add("Impermeabilizador de tecidos");
        ArrayPecas.add("Interruptor de emergência");
        ArrayPecas.add("Interruptor de porta");
        ArrayPecas.add("Interruptor do desembaçador");
        ArrayPecas.add("Interruptor do farol");
        ArrayPecas.add("Interruptor do farol de milha");
        ArrayPecas.add("Interruptor do retrovisor");
        ArrayPecas.add("Interruptor do vidro");
        ArrayPecas.add("Interruptor liga/desliga");
        ArrayPecas.add("Interruptor ventilador do ar");
        ArrayPecas.add("Jogo de anéis");
        ArrayPecas.add("Junta de carter");
        ArrayPecas.add("Junta homocinética");
        ArrayPecas.add("Junta tampa de válvulas");
        ArrayPecas.add("Kit alto-falante");
        ArrayPecas.add("Kit anti-furto p/ calota centro da roda");
        ArrayPecas.add("Kit cristalizador para autos");
        ArrayPecas.add("Kit da lâmpada do farol");
        ArrayPecas.add("Kit de embreagem");
        ArrayPecas.add("Kit de farol de milha");
        ArrayPecas.add("Kit de fixação da janela movel");
        ArrayPecas.add("Kit do amortecedor dianteiro");
        ArrayPecas.add("Kit do amortecedor traseiro");
        ArrayPecas.add("Kit do apoio de cabeça");
        ArrayPecas.add("Kit eixo comando de válvulas");
        ArrayPecas.add("Kit eixo limpador para-brisa");
        ArrayPecas.add("Kit junta do motor");
        ArrayPecas.add("Kit motor");
        ArrayPecas.add("Kit para encerar");
        ArrayPecas.add("Kit para fixação da viseira");
        ArrayPecas.add("Kit para lavar carro");
        ArrayPecas.add("Kit relação");
        ArrayPecas.add("Kit rolamento da roda traseira");
        ArrayPecas.add("Kit rolamento roda dianteira");
        ArrayPecas.add("Kit vidro elétrico");
        ArrayPecas.add("Lâmpada");
        ArrayPecas.add("Lâmpada do farol");
        ArrayPecas.add("Lanterna adaptação");
        ArrayPecas.add("Lanterna auxiliar");
        ArrayPecas.add("Lanterna da placa");
        ArrayPecas.add("Lanterna de teto");
        ArrayPecas.add("Lanterna dianteira direita");
        ArrayPecas.add("Lanterna dianteira tuning");
        ArrayPecas.add("Lanterna do para-choque");
        ArrayPecas.add("Lanterna do pára-lama");
        ArrayPecas.add("Lanterna do revestimento porta");
        ArrayPecas.add("Lanterna para socorro");
        ArrayPecas.add("Lanterna traseira direita");
        ArrayPecas.add("Lanterna traseira tuning");
        ArrayPecas.add("Lanterna traseira direita");
        ArrayPecas.add("Lava auto a seco");
        ArrayPecas.add("Lente da lanterna dianteira");
        ArrayPecas.add("Lente da lanterna do teto");
        ArrayPecas.add("Lente da lanterna traseira");
        ArrayPecas.add("Lente do farol");
        ArrayPecas.add("Lente do farol de milha");
        ArrayPecas.add("Lente do retrovisor com base");
        ArrayPecas.add("Lente do retrovisor sem base");
        ArrayPecas.add("Lente lanterna da placa");
        ArrayPecas.add("Lente lanterna do para-choque");
        ArrayPecas.add("Letreiro");
        ArrayPecas.add("Limitador de porta");
        ArrayPecas.add("Limpa carter");
        ArrayPecas.add("Limpa couro");
        ArrayPecas.add("Limpa plástico");
        ArrayPecas.add("Limpa pneu");
        ArrayPecas.add("Limpa radiador");
        ArrayPecas.add("Limpa vidros");
        ArrayPecas.add("Limpador");
        ArrayPecas.add("Limpador de ar condicionado");
        ArrayPecas.add("Limpador do para-brisa");
        ArrayPecas.add("Limpador do vidro traseiro");
        ArrayPecas.add("Limpador do vigia com haste");
        ArrayPecas.add("Lixa");
        ArrayPecas.add("Lona(sapata) de freio");
        ArrayPecas.add("Luminoso Táxi");
        ArrayPecas.add("Luva para lavar carro");
        ArrayPecas.add("Macaco");
        ArrayPecas.add("Maçaneta da tampa da caçamba");
        ArrayPecas.add("Maçaneta da tampa traseira");
        ArrayPecas.add("Maçaneta do vidro");
        ArrayPecas.add("Maçaneta externa da porta");
        ArrayPecas.add("Maçaneta interna da porta");
        ArrayPecas.add("Macarrão para acabamento");
        ArrayPecas.add("Mangueira de combustível");
        ArrayPecas.add("Mangueira do filtro de ar");
        ArrayPecas.add("Mangueira esguicho para-brisa");
        ArrayPecas.add("Manopla de regulagem do banco");
        ArrayPecas.add("Manopla do câmbio");
        ArrayPecas.add("Manopla do freio de mão");
        ArrayPecas.add("Manopla que levanta o banco");
        ArrayPecas.add("Máquina de vidro");
        ArrayPecas.add("Maquina para massa de mastic");
        ArrayPecas.add("Massa anti-ruído");
        ArrayPecas.add("Massa de calafetar");
        ArrayPecas.add("Massa de polir");
        ArrayPecas.add("Massa KPO");
        ArrayPecas.add("Massa plástica");
        ArrayPecas.add("Massa poliester");
        ArrayPecas.add("Massa primer rápido");
        ArrayPecas.add("Massa Rápida");
        ArrayPecas.add("Mata-junta do friso");
        ArrayPecas.add("Mata-junta do friso da janela");
        ArrayPecas.add("Módulo do vidro elétrico");
        ArrayPecas.add("Módulo para trava elétrica");
        ArrayPecas.add("Mola de suspensão");
        ArrayPecas.add("Mola do capô traseiro");
        ArrayPecas.add("Moldura da grade");
        ArrayPecas.add("Moldura da lanterna dianteira");
        ArrayPecas.add("Moldura da maçaneta da caçamba");
        ArrayPecas.add("Moldura da maçaneta interna");
        ArrayPecas.add("Moldura da placa");
        ArrayPecas.add("Moldura de central multimídia");
        ArrayPecas.add("Moldura do farol");
        ArrayPecas.add("Moldura do farol de milha");
        ArrayPecas.add("Moldura do painel do rádio");
        ArrayPecas.add("Moldura do para-lama");
        ArrayPecas.add("Monitor com tela de LCD");
        ArrayPecas.add("Motor da máquina de vidro");
        ArrayPecas.add("Óculos");
        ArrayPecas.add("Óleo da direção hidráulica");
        ArrayPecas.add("Óleo do câmbio");
        ArrayPecas.add("Óleo do freio");
        ArrayPecas.add("Óleo do motor");
        ArrayPecas.add("Olho de gato");
        ArrayPecas.add("Organizador automotivo");
        ArrayPecas.add("Painel de instrumento");
        ArrayPecas.add("Painel dianteiro");
        ArrayPecas.add("Painel traseiro");
        ArrayPecas.add("Papel para polimento");
        ArrayPecas.add("Papelão do bagagito");
        ArrayPecas.add("Papelão do porta-malas");
        ArrayPecas.add("Para-barro do para-lama dianteiro");
        ArrayPecas.add("Para-choque dianteiro");
        ArrayPecas.add("Para-choque traseiro");
        ArrayPecas.add("Parafuso da roda");
        ArrayPecas.add("Parafuso do cinto de segurança");
        ArrayPecas.add("Para-lama dianteiro direito");
        ArrayPecas.add("Para-sol do para-brisa");
        ArrayPecas.add("Para-sol lateral");
        ArrayPecas.add("Pasta para lavar a mão");
        ArrayPecas.add("Pastilha de freio");
        ArrayPecas.add("Pedal do acelerador");
        ArrayPecas.add("Pedaleira");
        ArrayPecas.add("Pestana externa");
        ArrayPecas.add("Pestana interna");
        ArrayPecas.add("Pino da dobradiça da porta");
        ArrayPecas.add("Pino da trava da porta");
        ArrayPecas.add("Pistão com anel");
        ArrayPecas.add("Pistola para cola de silicone");
        ArrayPecas.add("Pivô da suspensão");
        ArrayPecas.add("Platinado");
        ArrayPecas.add("Pneu");
        ArrayPecas.add("Polidor");
        ArrayPecas.add("Ponteira do para-choque dianteira");
        ArrayPecas.add("Ponteira do para-choque traseira");
        ArrayPecas.add("Porca da moldura do pára-lama");
        ArrayPecas.add("Porca da roda");
        ArrayPecas.add("Porca do estepe");
        ArrayPecas.add("Porta dianteira direita");
        ArrayPecas.add("Porta bagagem para rack");
        ArrayPecas.add("Porta CD");
        ArrayPecas.add("Porta celular");
        ArrayPecas.add("Porta copo");
        ArrayPecas.add("Porta fusível");
        ArrayPecas.add("Porta óculos");
        ArrayPecas.add("Portinhola de combustível");
        ArrayPecas.add("Pré-amplificador");
        ArrayPecas.add("Prismas para veículo");
        ArrayPecas.add("Protetor da porta");
        ArrayPecas.add("Protetor de cárter");
        ArrayPecas.add("Protetor de porta removivel");
        ArrayPecas.add("Protetor do cinto de segurança");
        ArrayPecas.add("Protetor do pára-choque");
        ArrayPecas.add("Purificador de Ar");
        ArrayPecas.add("Puxador do capô");
        ArrayPecas.add("Quadro do farol");
        ArrayPecas.add("Quebra-sol");
        ArrayPecas.add("Rack longitudinal (longarina)");
        ArrayPecas.add("Rack transversal");
        ArrayPecas.add("Radiador do motor");
        ArrayPecas.add("Rebitadeira");
        ArrayPecas.add("Rebite");
        ArrayPecas.add("Receptor automotivo para TV");
        ArrayPecas.add("Refil bomba de combustível");
        ArrayPecas.add("Refil do limpador");
        ArrayPecas.add("Regulador de voltagem");
        ArrayPecas.add("Regulador elétrico multi fução");
        ArrayPecas.add("Rele auxiliar");
        ArrayPecas.add("Rele de buzina");
        ArrayPecas.add("Rele do farol");
        ArrayPecas.add("Relógio");
        ArrayPecas.add("Remendo da caixa de estepe");
        ArrayPecas.add("Remendo de porta");
        ArrayPecas.add("Remendo do assoalho");
        ArrayPecas.add("Remendo lateral");
        ArrayPecas.add("Remendo painel traseiro");
        ArrayPecas.add("Reserv. de água do radiador");
        ArrayPecas.add("Reservatório de água do para-brisa");
        ArrayPecas.add("Reservatório de direção hidráulica");
        ArrayPecas.add("Reservatório de partida fria");
        ArrayPecas.add("Reservatório do óleo de freio");
        ArrayPecas.add("Restaurador de couro");
        ArrayPecas.add("Retentor caixa de mudança");
        ArrayPecas.add("Retentor dianteiro virabrequim");
        ArrayPecas.add("Retentor traseiro virabrequim");
        ArrayPecas.add("Retrovisor externo");
        ArrayPecas.add("Retrovisor interno");
        ArrayPecas.add("Revestimento de porta");
        ArrayPecas.add("Revestimento lateral traseiro");
        ArrayPecas.add("Rolamento apoio da correia");
        ArrayPecas.add("Rolamento da roda");
        ArrayPecas.add("Rolamento diferencial");
        ArrayPecas.add("Rolamento do alternador");
        ArrayPecas.add("Rolamento lateral da coroa");
        ArrayPecas.add("Rolamento tensor da correia");
        ArrayPecas.add("Roldana do banco");
        ArrayPecas.add("Rotor do distribuidor");
        ArrayPecas.add("Saca pino da porta");
        ArrayPecas.add("Saia dianteira");
        ArrayPecas.add("Saia traseira");
        ArrayPecas.add("Sealed bean");
        ArrayPecas.add("Sensor de estacionamento");
        ArrayPecas.add("Sensor de pressão");
        ArrayPecas.add("Sensor de rotação");
        ArrayPecas.add("Sensor lambda");
        ArrayPecas.add("Silicone");
        ArrayPecas.add("Sirene para alarme");
        ArrayPecas.add("Soleira");
        ArrayPecas.add("Soquete da lâmpada do painel");
        ArrayPecas.add("Soquete da lanterna de placa");
        ArrayPecas.add("Soquete da lanterna dianteira");
        ArrayPecas.add("Soquete da lanterna do farol");
        ArrayPecas.add("Soquete da lanterna traseira");
        ArrayPecas.add("Soquete do capô");
        ArrayPecas.add("Soquete do farol");
        ArrayPecas.add("Spoiler dianteiro");
        ArrayPecas.add("Spoiler lateral");
        ArrayPecas.add("Spoiler traseiro");
        ArrayPecas.add("Spot mini");
        ArrayPecas.add("Subwoofer");
        ArrayPecas.add("Suporte barra tensor");
        ArrayPecas.add("Suporte da placa");
        ArrayPecas.add("Suporte da ponta do para-choque");
        ArrayPecas.add("Suporte da trava elétrica");
        ArrayPecas.add("Suporte do extintor");
        ArrayPecas.add("Suporte do pára-choque");
        ArrayPecas.add("Suporte do quebra-sol");
        ArrayPecas.add("Suporte do vidro da porta");
        ArrayPecas.add("Suporte para bicicleta");
        ArrayPecas.add("Suporte para encaixe da chave");
        ArrayPecas.add("Suporte para GPS (navegador)");
        ArrayPecas.add("Suporte tampão do porta-malas");
        ArrayPecas.add("Tampa botão regulagem do banco");
        ArrayPecas.add("Tampa buraco do rádio");
        ArrayPecas.add("Tampa da caçamba");
        ArrayPecas.add("Tampa da caixa de fusíveis");
        ArrayPecas.add("Tampa do distribuidor");
        ArrayPecas.add("Tampa do farol de milha");
        ArrayPecas.add("Tampa do óleo do motor");
        ArrayPecas.add("Tampa do porta-luvas");
        ArrayPecas.add("Tampa do porta-malas");
        ArrayPecas.add("Tampa do reboque");
        ArrayPecas.add("Tampa do reservatório do radiador");
        ArrayPecas.add("Tampa do tanque de combustível");
        ArrayPecas.add("Tampa reservatório água para-brisa");
        ArrayPecas.add("Tampão do porta-malas");
        ArrayPecas.add("Tampinha do furo da antena");
        ArrayPecas.add("Tanque de combustível");
        ArrayPecas.add("Tapa furo do pneu");
        ArrayPecas.add("Tapete da caçamba");
        ArrayPecas.add("Tapete de borracha");
        ArrayPecas.add("Tapete de carpete+borracha");
        ArrayPecas.add("Tapete do porta-malas");
        ArrayPecas.add("Tapete do porta-malas de borracha");
        ArrayPecas.add("Tapete do túnel");
        ArrayPecas.add("Tapete tuning");
        ArrayPecas.add("Tela do alto-falante");
        ArrayPecas.add("Tensor da correia");
        ArrayPecas.add("Terminal adaptador de antena");
        ArrayPecas.add("Terminal de direção");
        ArrayPecas.add("Terminal de encaixe");
        ArrayPecas.add("Toalha");
        ArrayPecas.add("Tranca do capô dianteiro");
        ArrayPecas.add("Trava anti-furto para volante");
        ArrayPecas.add("Trava de segurança");
        ArrayPecas.add("Trava do cinto de segurança");
        ArrayPecas.add("Trava do quebra-vento");
        ArrayPecas.add("Trava elétrica");
        ArrayPecas.add("Trava maçaneta da porta");
        ArrayPecas.add("Trava pega-ladrão");
        ArrayPecas.add("Travessa do painel dianteiro");
        ArrayPecas.add("Travessa do radiador");
        ArrayPecas.add("Travessa p/ rack longitudinal");
        ArrayPecas.add("Triângulo");
        ArrayPecas.add("Trilho da máquina de vidro");
        ArrayPecas.add("Tubo do escape");
        ArrayPecas.add("Tucho hidraúlico");
        ArrayPecas.add("Tweeter do alto-falante");
        ArrayPecas.add("Válvula termostática");
        ArrayPecas.add("Vareta do capô");
        ArrayPecas.add("Vela de ignição");
        ArrayPecas.add("Ventilador");
        ArrayPecas.add("Volante");
        ArrayPecas.add("Xampu");
        ArrayPecas.add("Farol esquerdo");
        ArrayPecas.add("Farol de milha esquerdo");
        ArrayPecas.add("Friso da lanterna dianteira esquerda");
        ArrayPecas.add("Friso da lanterna traseira esquerda");
        ArrayPecas.add("Friso lateral esquerdo");
        ArrayPecas.add("Lanterna dianteira esquerda");
        ArrayPecas.add("Lanterna traseira esquerda");
        ArrayPecas.add("Lateral traseira esquerda");
        ArrayPecas.add("Para-lama dianteiro esquerdo");
        ArrayPecas.add("Para-lama traseiro direito");
        ArrayPecas.add("Para-lama traseiro esquerdo");
        ArrayPecas.add("Porta dianteira esquerda");
        ArrayPecas.add("Porta traseira direito");
        ArrayPecas.add("Porta traseira esquerda");
        ArrayPecas.add("Roda dianteira direita");
        ArrayPecas.add("Roda dianteira esquerda");
        ArrayPecas.add("Roda traseira direita");
        ArrayPecas.add("Roda traseira esquerda");
        ArrayPecas.add("Lateral traseira direita");
        ArrayPecas.add("Lateral dianteira direita");
        ArrayPecas.add("Lateral dianteira esquerda");
        ArrayPecas.add("Parabrisa");
        ArrayPecas.add("Teto");

    }

    private void FillPecaID() {

        ArrayPecasID =  new ArrayList<String>();
        ArrayPecasID.add("7");
        ArrayPecasID.add("8");
        ArrayPecasID.add("9");
        ArrayPecasID.add("10");
        ArrayPecasID.add("11");
        ArrayPecasID.add("12");
        ArrayPecasID.add("13");
        ArrayPecasID.add("14");
        ArrayPecasID.add("15");
        ArrayPecasID.add("16");
        ArrayPecasID.add("17");
        ArrayPecasID.add("18");
        ArrayPecasID.add("19");
        ArrayPecasID.add("20");
        ArrayPecasID.add("21");
        ArrayPecasID.add("22");
        ArrayPecasID.add("23");
        ArrayPecasID.add("24");
        ArrayPecasID.add("25");
        ArrayPecasID.add("26");
        ArrayPecasID.add("27");
        ArrayPecasID.add("28");
        ArrayPecasID.add("29");
        ArrayPecasID.add("30");
        ArrayPecasID.add("31");
        ArrayPecasID.add("32");
        ArrayPecasID.add("33");
        ArrayPecasID.add("34");
        ArrayPecasID.add("35");
        ArrayPecasID.add("36");
        ArrayPecasID.add("37");
        ArrayPecasID.add("38");
        ArrayPecasID.add("39");
        ArrayPecasID.add("40");
        ArrayPecasID.add("41");
        ArrayPecasID.add("42");
        ArrayPecasID.add("43");
        ArrayPecasID.add("44");
        ArrayPecasID.add("45");
        ArrayPecasID.add("46");
        ArrayPecasID.add("47");
        ArrayPecasID.add("48");
        ArrayPecasID.add("49");
        ArrayPecasID.add("50");
        ArrayPecasID.add("51");
        ArrayPecasID.add("52");
        ArrayPecasID.add("53");
        ArrayPecasID.add("54");
        ArrayPecasID.add("55");
        ArrayPecasID.add("56");
        ArrayPecasID.add("57");
        ArrayPecasID.add("58");
        ArrayPecasID.add("59");
        ArrayPecasID.add("60");
        ArrayPecasID.add("61");
        ArrayPecasID.add("62");
        ArrayPecasID.add("63");
        ArrayPecasID.add("64");
        ArrayPecasID.add("65");
        ArrayPecasID.add("66");
        ArrayPecasID.add("67");
        ArrayPecasID.add("68");
        ArrayPecasID.add("69");
        ArrayPecasID.add("70");
        ArrayPecasID.add("71");
        ArrayPecasID.add("72");
        ArrayPecasID.add("73");
        ArrayPecasID.add("74");
        ArrayPecasID.add("75");
        ArrayPecasID.add("76");
        ArrayPecasID.add("77");
        ArrayPecasID.add("78");
        ArrayPecasID.add("79");
        ArrayPecasID.add("80");
        ArrayPecasID.add("81");
        ArrayPecasID.add("82");
        ArrayPecasID.add("83");
        ArrayPecasID.add("84");
        ArrayPecasID.add("85");
        ArrayPecasID.add("86");
        ArrayPecasID.add("87");
        ArrayPecasID.add("88");
        ArrayPecasID.add("89");
        ArrayPecasID.add("90");
        ArrayPecasID.add("91");
        ArrayPecasID.add("92");
        ArrayPecasID.add("93");
        ArrayPecasID.add("94");
        ArrayPecasID.add("95");
        ArrayPecasID.add("96");
        ArrayPecasID.add("97");
        ArrayPecasID.add("98");
        ArrayPecasID.add("99");
        ArrayPecasID.add("100");
        ArrayPecasID.add("101");
        ArrayPecasID.add("102");
        ArrayPecasID.add("103");
        ArrayPecasID.add("104");
        ArrayPecasID.add("105");
        ArrayPecasID.add("106");
        ArrayPecasID.add("107");
        ArrayPecasID.add("108");
        ArrayPecasID.add("109");
        ArrayPecasID.add("110");
        ArrayPecasID.add("111");
        ArrayPecasID.add("112");
        ArrayPecasID.add("113");
        ArrayPecasID.add("114");
        ArrayPecasID.add("115");
        ArrayPecasID.add("116");
        ArrayPecasID.add("117");
        ArrayPecasID.add("118");
        ArrayPecasID.add("119");
        ArrayPecasID.add("120");
        ArrayPecasID.add("121");
        ArrayPecasID.add("122");
        ArrayPecasID.add("123");
        ArrayPecasID.add("124");
        ArrayPecasID.add("125");
        ArrayPecasID.add("126");
        ArrayPecasID.add("127");
        ArrayPecasID.add("128");
        ArrayPecasID.add("129");
        ArrayPecasID.add("130");
        ArrayPecasID.add("131");
        ArrayPecasID.add("132");
        ArrayPecasID.add("133");
        ArrayPecasID.add("134");
        ArrayPecasID.add("135");
        ArrayPecasID.add("136");
        ArrayPecasID.add("137");
        ArrayPecasID.add("138");
        ArrayPecasID.add("139");
        ArrayPecasID.add("140");
        ArrayPecasID.add("141");
        ArrayPecasID.add("142");
        ArrayPecasID.add("143");
        ArrayPecasID.add("144");
        ArrayPecasID.add("145");
        ArrayPecasID.add("146");
        ArrayPecasID.add("147");
        ArrayPecasID.add("148");
        ArrayPecasID.add("149");
        ArrayPecasID.add("150");
        ArrayPecasID.add("151");
        ArrayPecasID.add("152");
        ArrayPecasID.add("153");
        ArrayPecasID.add("154");
        ArrayPecasID.add("155");
        ArrayPecasID.add("156");
        ArrayPecasID.add("157");
        ArrayPecasID.add("158");
        ArrayPecasID.add("159");
        ArrayPecasID.add("160");
        ArrayPecasID.add("161");
        ArrayPecasID.add("162");
        ArrayPecasID.add("163");
        ArrayPecasID.add("164");
        ArrayPecasID.add("165");
        ArrayPecasID.add("166");
        ArrayPecasID.add("167");
        ArrayPecasID.add("168");
        ArrayPecasID.add("169");
        ArrayPecasID.add("170");
        ArrayPecasID.add("171");
        ArrayPecasID.add("172");
        ArrayPecasID.add("173");
        ArrayPecasID.add("174");
        ArrayPecasID.add("175");
        ArrayPecasID.add("176");
        ArrayPecasID.add("177");
        ArrayPecasID.add("178");
        ArrayPecasID.add("179");
        ArrayPecasID.add("180");
        ArrayPecasID.add("181");
        ArrayPecasID.add("182");
        ArrayPecasID.add("183");
        ArrayPecasID.add("184");
        ArrayPecasID.add("185");
        ArrayPecasID.add("186");
        ArrayPecasID.add("187");
        ArrayPecasID.add("188");
        ArrayPecasID.add("189");
        ArrayPecasID.add("190");
        ArrayPecasID.add("191");
        ArrayPecasID.add("192");
        ArrayPecasID.add("193");
        ArrayPecasID.add("194");
        ArrayPecasID.add("195");
        ArrayPecasID.add("196");
        ArrayPecasID.add("197");
        ArrayPecasID.add("198");
        ArrayPecasID.add("199");
        ArrayPecasID.add("200");
        ArrayPecasID.add("201");
        ArrayPecasID.add("202");
        ArrayPecasID.add("203");
        ArrayPecasID.add("204");
        ArrayPecasID.add("205");
        ArrayPecasID.add("206");
        ArrayPecasID.add("207");
        ArrayPecasID.add("208");
        ArrayPecasID.add("209");
        ArrayPecasID.add("210");
        ArrayPecasID.add("211");
        ArrayPecasID.add("212");
        ArrayPecasID.add("213");
        ArrayPecasID.add("214");
        ArrayPecasID.add("215");
        ArrayPecasID.add("216");
        ArrayPecasID.add("217");
        ArrayPecasID.add("218");
        ArrayPecasID.add("219");
        ArrayPecasID.add("220");
        ArrayPecasID.add("221");
        ArrayPecasID.add("222");
        ArrayPecasID.add("223");
        ArrayPecasID.add("224");
        ArrayPecasID.add("225");
        ArrayPecasID.add("226");
        ArrayPecasID.add("227");
        ArrayPecasID.add("228");
        ArrayPecasID.add("229");
        ArrayPecasID.add("230");
        ArrayPecasID.add("231");
        ArrayPecasID.add("232");
        ArrayPecasID.add("233");
        ArrayPecasID.add("234");
        ArrayPecasID.add("235");
        ArrayPecasID.add("236");
        ArrayPecasID.add("237");
        ArrayPecasID.add("238");
        ArrayPecasID.add("239");
        ArrayPecasID.add("240");
        ArrayPecasID.add("241");
        ArrayPecasID.add("242");
        ArrayPecasID.add("243");
        ArrayPecasID.add("244");
        ArrayPecasID.add("245");
        ArrayPecasID.add("246");
        ArrayPecasID.add("247");
        ArrayPecasID.add("248");
        ArrayPecasID.add("249");
        ArrayPecasID.add("250");
        ArrayPecasID.add("251");
        ArrayPecasID.add("252");
        ArrayPecasID.add("253");
        ArrayPecasID.add("254");
        ArrayPecasID.add("255");
        ArrayPecasID.add("256");
        ArrayPecasID.add("257");
        ArrayPecasID.add("258");
        ArrayPecasID.add("259");
        ArrayPecasID.add("260");
        ArrayPecasID.add("261");
        ArrayPecasID.add("262");
        ArrayPecasID.add("263");
        ArrayPecasID.add("264");
        ArrayPecasID.add("265");
        ArrayPecasID.add("266");
        ArrayPecasID.add("267");
        ArrayPecasID.add("268");
        ArrayPecasID.add("269");
        ArrayPecasID.add("270");
        ArrayPecasID.add("271");
        ArrayPecasID.add("272");
        ArrayPecasID.add("273");
        ArrayPecasID.add("274");
        ArrayPecasID.add("275");
        ArrayPecasID.add("276");
        ArrayPecasID.add("277");
        ArrayPecasID.add("278");
        ArrayPecasID.add("279");
        ArrayPecasID.add("280");
        ArrayPecasID.add("281");
        ArrayPecasID.add("282");
        ArrayPecasID.add("283");
        ArrayPecasID.add("284");
        ArrayPecasID.add("285");
        ArrayPecasID.add("286");
        ArrayPecasID.add("287");
        ArrayPecasID.add("288");
        ArrayPecasID.add("289");
        ArrayPecasID.add("290");
        ArrayPecasID.add("291");
        ArrayPecasID.add("292");
        ArrayPecasID.add("293");
        ArrayPecasID.add("294");
        ArrayPecasID.add("295");
        ArrayPecasID.add("296");
        ArrayPecasID.add("297");
        ArrayPecasID.add("298");
        ArrayPecasID.add("299");
        ArrayPecasID.add("300");
        ArrayPecasID.add("301");
        ArrayPecasID.add("302");
        ArrayPecasID.add("303");
        ArrayPecasID.add("304");
        ArrayPecasID.add("305");
        ArrayPecasID.add("306");
        ArrayPecasID.add("307");
        ArrayPecasID.add("308");
        ArrayPecasID.add("309");
        ArrayPecasID.add("310");
        ArrayPecasID.add("311");
        ArrayPecasID.add("312");
        ArrayPecasID.add("313");
        ArrayPecasID.add("314");
        ArrayPecasID.add("315");
        ArrayPecasID.add("316");
        ArrayPecasID.add("317");
        ArrayPecasID.add("318");
        ArrayPecasID.add("319");
        ArrayPecasID.add("320");
        ArrayPecasID.add("321");
        ArrayPecasID.add("322");
        ArrayPecasID.add("323");
        ArrayPecasID.add("324");
        ArrayPecasID.add("325");
        ArrayPecasID.add("326");
        ArrayPecasID.add("327");
        ArrayPecasID.add("328");
        ArrayPecasID.add("329");
        ArrayPecasID.add("330");
        ArrayPecasID.add("331");
        ArrayPecasID.add("332");
        ArrayPecasID.add("333");
        ArrayPecasID.add("334");
        ArrayPecasID.add("335");
        ArrayPecasID.add("336");
        ArrayPecasID.add("337");
        ArrayPecasID.add("338");
        ArrayPecasID.add("339");
        ArrayPecasID.add("340");
        ArrayPecasID.add("341");
        ArrayPecasID.add("342");
        ArrayPecasID.add("343");
        ArrayPecasID.add("344");
        ArrayPecasID.add("345");
        ArrayPecasID.add("346");
        ArrayPecasID.add("347");
        ArrayPecasID.add("348");
        ArrayPecasID.add("349");
        ArrayPecasID.add("350");
        ArrayPecasID.add("351");
        ArrayPecasID.add("352");
        ArrayPecasID.add("353");
        ArrayPecasID.add("354");
        ArrayPecasID.add("355");
        ArrayPecasID.add("356");
        ArrayPecasID.add("357");
        ArrayPecasID.add("358");
        ArrayPecasID.add("359");
        ArrayPecasID.add("360");
        ArrayPecasID.add("361");
        ArrayPecasID.add("362");
        ArrayPecasID.add("363");
        ArrayPecasID.add("364");
        ArrayPecasID.add("365");
        ArrayPecasID.add("366");
        ArrayPecasID.add("367");
        ArrayPecasID.add("368");
        ArrayPecasID.add("369");
        ArrayPecasID.add("370");
        ArrayPecasID.add("371");
        ArrayPecasID.add("372");
        ArrayPecasID.add("373");
        ArrayPecasID.add("374");
        ArrayPecasID.add("375");
        ArrayPecasID.add("376");
        ArrayPecasID.add("377");
        ArrayPecasID.add("378");
        ArrayPecasID.add("379");
        ArrayPecasID.add("380");
        ArrayPecasID.add("381");
        ArrayPecasID.add("382");
        ArrayPecasID.add("383");
        ArrayPecasID.add("384");
        ArrayPecasID.add("385");
        ArrayPecasID.add("386");
        ArrayPecasID.add("387");
        ArrayPecasID.add("388");
        ArrayPecasID.add("389");
        ArrayPecasID.add("390");
        ArrayPecasID.add("391");
        ArrayPecasID.add("392");
        ArrayPecasID.add("393");
        ArrayPecasID.add("394");
        ArrayPecasID.add("395");
        ArrayPecasID.add("396");
        ArrayPecasID.add("397");
        ArrayPecasID.add("398");
        ArrayPecasID.add("399");
        ArrayPecasID.add("400");
        ArrayPecasID.add("401");
        ArrayPecasID.add("402");
        ArrayPecasID.add("403");
        ArrayPecasID.add("404");
        ArrayPecasID.add("405");
        ArrayPecasID.add("406");
        ArrayPecasID.add("407");
        ArrayPecasID.add("408");
        ArrayPecasID.add("409");
        ArrayPecasID.add("410");
        ArrayPecasID.add("411");
        ArrayPecasID.add("412");
        ArrayPecasID.add("413");
        ArrayPecasID.add("414");
        ArrayPecasID.add("415");
        ArrayPecasID.add("416");
        ArrayPecasID.add("417");
        ArrayPecasID.add("418");
        ArrayPecasID.add("419");
        ArrayPecasID.add("420");
        ArrayPecasID.add("421");
        ArrayPecasID.add("422");
        ArrayPecasID.add("423");
        ArrayPecasID.add("424");
        ArrayPecasID.add("425");
        ArrayPecasID.add("426");
        ArrayPecasID.add("427");
        ArrayPecasID.add("428");
        ArrayPecasID.add("429");
        ArrayPecasID.add("430");
        ArrayPecasID.add("431");
        ArrayPecasID.add("432");
        ArrayPecasID.add("433");
        ArrayPecasID.add("434");
        ArrayPecasID.add("435");
        ArrayPecasID.add("436");
        ArrayPecasID.add("437");
        ArrayPecasID.add("438");
        ArrayPecasID.add("439");
        ArrayPecasID.add("440");
        ArrayPecasID.add("441");
        ArrayPecasID.add("442");
        ArrayPecasID.add("443");
        ArrayPecasID.add("444");
        ArrayPecasID.add("445");
        ArrayPecasID.add("446");
        ArrayPecasID.add("447");
        ArrayPecasID.add("448");
        ArrayPecasID.add("449");
        ArrayPecasID.add("450");
        ArrayPecasID.add("451");
        ArrayPecasID.add("452");
        ArrayPecasID.add("453");
        ArrayPecasID.add("454");
        ArrayPecasID.add("455");
        ArrayPecasID.add("456");
        ArrayPecasID.add("457");
        ArrayPecasID.add("458");
        ArrayPecasID.add("459");
        ArrayPecasID.add("460");
        ArrayPecasID.add("461");
        ArrayPecasID.add("462");
        ArrayPecasID.add("463");
        ArrayPecasID.add("464");
        ArrayPecasID.add("465");
        ArrayPecasID.add("466");
        ArrayPecasID.add("467");
        ArrayPecasID.add("468");
        ArrayPecasID.add("469");
        ArrayPecasID.add("470");
        ArrayPecasID.add("471");
        ArrayPecasID.add("472");
        ArrayPecasID.add("473");
        ArrayPecasID.add("474");
        ArrayPecasID.add("475");
        ArrayPecasID.add("476");
        ArrayPecasID.add("477");
        ArrayPecasID.add("478");
        ArrayPecasID.add("479");
        ArrayPecasID.add("480");
        ArrayPecasID.add("481");
        ArrayPecasID.add("482");
        ArrayPecasID.add("483");
        ArrayPecasID.add("484");
        ArrayPecasID.add("485");
        ArrayPecasID.add("486");
        ArrayPecasID.add("487");
        ArrayPecasID.add("488");
        ArrayPecasID.add("489");
        ArrayPecasID.add("490");
        ArrayPecasID.add("491");
        ArrayPecasID.add("492");
        ArrayPecasID.add("493");
        ArrayPecasID.add("494");
        ArrayPecasID.add("495");
        ArrayPecasID.add("496");
        ArrayPecasID.add("497");
        ArrayPecasID.add("498");
        ArrayPecasID.add("499");
        ArrayPecasID.add("500");
        ArrayPecasID.add("501");
        ArrayPecasID.add("502");
        ArrayPecasID.add("503");
        ArrayPecasID.add("504");
        ArrayPecasID.add("505");
        ArrayPecasID.add("506");
        ArrayPecasID.add("507");
        ArrayPecasID.add("508");
        ArrayPecasID.add("509");
        ArrayPecasID.add("510");
        ArrayPecasID.add("511");
        ArrayPecasID.add("512");
        ArrayPecasID.add("513");
        ArrayPecasID.add("514");
        ArrayPecasID.add("515");
        ArrayPecasID.add("516");
        ArrayPecasID.add("517");
        ArrayPecasID.add("518");
        ArrayPecasID.add("519");
        ArrayPecasID.add("520");
        ArrayPecasID.add("521");
        ArrayPecasID.add("522");
        ArrayPecasID.add("523");
        ArrayPecasID.add("524");
        ArrayPecasID.add("525");
        ArrayPecasID.add("526");
        ArrayPecasID.add("527");
        ArrayPecasID.add("528");
        ArrayPecasID.add("529");
        ArrayPecasID.add("530");
        ArrayPecasID.add("531");
        ArrayPecasID.add("532");
        ArrayPecasID.add("533");
        ArrayPecasID.add("534");
        ArrayPecasID.add("535");
        ArrayPecasID.add("536");
        ArrayPecasID.add("537");
        ArrayPecasID.add("538");
        ArrayPecasID.add("539");
        ArrayPecasID.add("540");
        ArrayPecasID.add("541");
        ArrayPecasID.add("542");
        ArrayPecasID.add("543");
        ArrayPecasID.add("544");
        ArrayPecasID.add("545");
        ArrayPecasID.add("546");
        ArrayPecasID.add("547");
        ArrayPecasID.add("548");
        ArrayPecasID.add("549");
        ArrayPecasID.add("550");
        ArrayPecasID.add("551");
        ArrayPecasID.add("552");
        ArrayPecasID.add("553");
        ArrayPecasID.add("554");
        ArrayPecasID.add("555");
        ArrayPecasID.add("556");
        ArrayPecasID.add("557");
        ArrayPecasID.add("558");
        ArrayPecasID.add("559");
        ArrayPecasID.add("560");
        ArrayPecasID.add("561");
        ArrayPecasID.add("562");
        ArrayPecasID.add("563");
        ArrayPecasID.add("564");
        ArrayPecasID.add("565");
        ArrayPecasID.add("566");
        ArrayPecasID.add("567");
        ArrayPecasID.add("568");
        ArrayPecasID.add("569");
        ArrayPecasID.add("570");
        ArrayPecasID.add("571");
        ArrayPecasID.add("572");
        ArrayPecasID.add("573");
        ArrayPecasID.add("574");
        ArrayPecasID.add("575");
        ArrayPecasID.add("576");
        ArrayPecasID.add("577");
        ArrayPecasID.add("578");
        ArrayPecasID.add("579");
        ArrayPecasID.add("580");
        ArrayPecasID.add("581");
        ArrayPecasID.add("582");
        ArrayPecasID.add("583");
        ArrayPecasID.add("584");
        ArrayPecasID.add("585");
        ArrayPecasID.add("586");
        ArrayPecasID.add("587");
        ArrayPecasID.add("588");
        ArrayPecasID.add("589");
        ArrayPecasID.add("590");
        ArrayPecasID.add("591");
        ArrayPecasID.add("592");
        ArrayPecasID.add("593");
        ArrayPecasID.add("594");
        ArrayPecasID.add("595");
        ArrayPecasID.add("596");
        ArrayPecasID.add("597");
        ArrayPecasID.add("598");
        ArrayPecasID.add("599");
        ArrayPecasID.add("600");
        ArrayPecasID.add("601");
        ArrayPecasID.add("602");
        ArrayPecasID.add("603");
        ArrayPecasID.add("604");
        ArrayPecasID.add("605");
        ArrayPecasID.add("606");
        ArrayPecasID.add("607");
        ArrayPecasID.add("608");
        ArrayPecasID.add("609");
        ArrayPecasID.add("610");
        ArrayPecasID.add("611");
        ArrayPecasID.add("612");
        ArrayPecasID.add("613");
        ArrayPecasID.add("614");
        ArrayPecasID.add("615");
        ArrayPecasID.add("616");
        ArrayPecasID.add("617");
        ArrayPecasID.add("618");
        ArrayPecasID.add("619");
        ArrayPecasID.add("620");
        ArrayPecasID.add("621");
        ArrayPecasID.add("622");
        ArrayPecasID.add("623");
        ArrayPecasID.add("624");
        ArrayPecasID.add("625");
        ArrayPecasID.add("626");
        ArrayPecasID.add("627");
        ArrayPecasID.add("628");
        ArrayPecasID.add("629");
        ArrayPecasID.add("630");
        ArrayPecasID.add("631");
        ArrayPecasID.add("632");
        ArrayPecasID.add("633");
        ArrayPecasID.add("634");
        ArrayPecasID.add("635");
        ArrayPecasID.add("636");
        ArrayPecasID.add("637");
        ArrayPecasID.add("638");
        ArrayPecasID.add("639");
        ArrayPecasID.add("640");
        ArrayPecasID.add("641");
        ArrayPecasID.add("642");
        ArrayPecasID.add("643");
        ArrayPecasID.add("644");
        ArrayPecasID.add("645");
        ArrayPecasID.add("646");
        ArrayPecasID.add("647");
        ArrayPecasID.add("648");
        ArrayPecasID.add("649");
        ArrayPecasID.add("650");
        ArrayPecasID.add("651");
        ArrayPecasID.add("652");
        ArrayPecasID.add("653");
        ArrayPecasID.add("654");
        ArrayPecasID.add("655");
        ArrayPecasID.add("656");
        ArrayPecasID.add("657");
        ArrayPecasID.add("658");
        ArrayPecasID.add("659");
        ArrayPecasID.add("660");
        ArrayPecasID.add("661");
        ArrayPecasID.add("662");
        ArrayPecasID.add("663");
        ArrayPecasID.add("664");
        ArrayPecasID.add("665");
        ArrayPecasID.add("666");
        ArrayPecasID.add("667");
        ArrayPecasID.add("668");
        ArrayPecasID.add("669");
        ArrayPecasID.add("670");
        ArrayPecasID.add("671");
        ArrayPecasID.add("672");
        ArrayPecasID.add("673");
        ArrayPecasID.add("674");
        ArrayPecasID.add("675");
        ArrayPecasID.add("676");
        ArrayPecasID.add("677");
        ArrayPecasID.add("678");
        ArrayPecasID.add("679");
        ArrayPecasID.add("680");
        ArrayPecasID.add("681");
        ArrayPecasID.add("682");
        ArrayPecasID.add("683");
        ArrayPecasID.add("684");
        ArrayPecasID.add("685");
        ArrayPecasID.add("686");
        ArrayPecasID.add("687");
        ArrayPecasID.add("688");
        ArrayPecasID.add("689");
        ArrayPecasID.add("690");
        ArrayPecasID.add("691");
        ArrayPecasID.add("692");
        ArrayPecasID.add("693");
        ArrayPecasID.add("694");
        ArrayPecasID.add("695");
        ArrayPecasID.add("696");
        ArrayPecasID.add("697");
        ArrayPecasID.add("698");
        ArrayPecasID.add("699");
        ArrayPecasID.add("700");
        ArrayPecasID.add("701");
        ArrayPecasID.add("702");
        ArrayPecasID.add("703");
        ArrayPecasID.add("704");
        ArrayPecasID.add("705");
        ArrayPecasID.add("707");
        ArrayPecasID.add("708");


    }

    private void OrdenacaoChanged(int position) {

        if(ocorrencia_data == null || ocorrencia_data.length == 0)
            return;


        switch (position)
        {
            case 0:{
               Collections.sort(Arrays.asList(ocorrencia_data), new CustomComparatorID());
            };break;
            case 1: {
                Collections.sort(Arrays.asList(ocorrencia_data), new CustomComparatorValor());
            };break;

            case 2: {
                Collections.sort(Arrays.asList(ocorrencia_data), new CustomComparatorAval());
            };break;

            case 3: {
                Collections.sort(Arrays.asList(ocorrencia_data), new CustomComparatorTempo());
            };break;
            case 4: {
                Collections.sort(Arrays.asList(ocorrencia_data), new CustomComparatorDist());
            };break;
        }

        AplicaLista();

    }
    public class CustomComparatorValor implements Comparator<Orcamento> {
        @Override
        public int compare(Orcamento o1, Orcamento o2) {
            float Valor1 = Float.parseFloat(o1.Valor);
            float Valor2 = Float.parseFloat(o2.Valor);
            return Float.compare(Valor1,Valor2);
        }
    }
    public class CustomComparatorTempo implements Comparator<Orcamento> {

        @Override
        public int compare(Orcamento o1, Orcamento o2) {
            return Integer.compare(Integer.parseInt(o1.Tempo),Integer.parseInt(o2.Tempo));

        }
    }
    public class CustomComparatorID implements Comparator<Orcamento> {
        @Override
        public int compare(Orcamento o1, Orcamento o2) {
            return Integer.compare(Integer.parseInt(o1.myID),Integer.parseInt(o2.myID));

        }
    }

    public class CustomComparatorAval implements Comparator<Orcamento> {
        @Override
        public int compare(Orcamento o1, Orcamento o2) {
            return Float.compare(o2.Aval,o1.Aval);

        }
    }

    public class CustomComparatorDist implements Comparator<Orcamento> {
        @Override
        public int compare(Orcamento o1, Orcamento o2) {

            Float Dist1 = Float.parseFloat(o1.distancia.replace("KM", "").trim());
            Float Dist2 = Float.parseFloat(o2.distancia.replace("KM", "").trim());
            return Float.compare(Dist1,Dist2);

        }
    }

    private void FillSort() {
       ArrayList<String> spinnerArraySort = new ArrayList<String>();
        spinnerArraySort.add("Ordem de recebimento");
        spinnerArraySort.add("Menor valor");
        spinnerArraySort.add("Melhores avaliações");
        spinnerArraySort.add("Melhor tempo de conserto");
        spinnerArraySort.add("Menor Distância");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArraySort);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.ddOrdenacao);
        sItems.setAdapter(adapter);

    }

    private void GetOcorrencia() {
        final String[] Result = new String[1];
        new Thread(new Runnable() {
            public void run() {
                try {
                    DAL dal = new DAL();
                     Result[0] = dal.GetOcorrenciaBy(OrcamentoID);

                    final JSONObject object = new JSONObject(Result[0]);
                    if (object.getString("Status").equals("1")) {
                        final JSONArray data = object.getJSONArray("Data");
                        ocorrencia_data = new Orcamento[data.length()];

                        for (int i = 0; i < data.length(); i++) {
                            final JSONObject obj = new JSONObject(data.getString(i));
                            final JSONObject result2 = obj.getJSONObject("Orcamento");
                            final JSONArray JsonForma = obj.getJSONArray("FormaPagto");
                            final JSONArray JsonCorte = obj.getJSONArray("Cortesias");
                            final JSONArray JsonPecasRecuperar = obj.getJSONArray("PecasRecuperar");
                            final JSONArray JsonPecasTrocar = obj.getJSONArray("PecasTrocar");





                            String Nomeoficina = result2.getString("Nome");



                            String Tempo =  result2.getString("Prazo");
                            String Prazo = Tempo + " dias";
                            String DataReg = result2.getString("DataValidade");
                            String Ano = DataReg.substring(0, 4);
                            String Mes = DataReg.substring(5, 7);
                            String Dia = DataReg.substring(8, 10);
                            DataReg = Dia + "/" + Mes + "/" + Ano;
                            String TotalGeral = result2.getString("TotalGeral");
                            String Distancia = result2.getString("Distancia");
                            String TotalMO = result2.getString("TotalMaoDeObra");
                            String Desconto = result2.getString("Desconto");

                            String MyID = result2.getString("ID");
                            Drawable ImgNota = null;

                            long Aval;
                            if(!result2.getString("Avaliacao").equals("null"))
                            {
                                Aval = Math.round(result2.getLong("Avaliacao"));
                            }
                            else
                            {
                                Aval = 0;
                            }


                            if(Aval == 0)
                            {
                                ImgNota = null;
                            }
                            else if(Aval == 1.0)
                            {
                                ImgNota = (getResources().getDrawable(R.drawable.s_1_star_rating));
                            }
                            else if(Aval == 2.0)
                            {
                                ImgNota = (getResources().getDrawable(R.drawable.s_2_star_rating));

                            }
                            else if(Aval == 3.0)
                            {
                                ImgNota = (getResources().getDrawable(R.drawable.s_3_star_rating));

                            }
                            else if(Aval == 4.0)
                            {
                                ImgNota = (getResources().getDrawable(R.drawable.s_4_star_rating));

                            }
                            else if(Aval == 5.0)
                            {
                                ImgNota = (getResources().getDrawable(R.drawable.s_5_star_rating));

                            }


                            boolean read = false;
                            if (!preferences.getString(MyID, "-1").equals("-1")) {
                                read = true;
                            }


                            String Cortesia = "";
                            String PecasATrocarValor = result2.getString("TotalPecas");
                            String PecasATrocar = "";
                            String PecasARecuperar = "";
                            String Forma = "";
                            for (int k = 0; k < JsonForma.length(); k++) {
                                final JSONObject objForma = new JSONObject(JsonForma.getString(k));
                                Forma += objForma.getString("FormaPagtoID") + ";";

                            }
                            for (int k = 0; k < JsonCorte.length(); k++) {
                                final JSONObject objForma = new JSONObject(JsonCorte.getString(k));
                                Cortesia += objForma.getString("OrcamentoCategoriaID") + ";";

                            }
                            for (int k = 0; k < JsonPecasRecuperar.length(); k++) {
                                final JSONObject objForma = new JSONObject(JsonPecasRecuperar.getString(k));
                                PecasARecuperar += ArrayPecas.get(ArrayPecasID.indexOf(objForma.getString("OrcamentoCategoriaID"))) + ";";

                            }

                            for (int k = 0; k < JsonPecasTrocar.length(); k++) {
                                final JSONObject objForma = new JSONObject(JsonPecasTrocar.getString(k));
                                PecasATrocar +=   ArrayPecas.get(ArrayPecasID.indexOf(objForma.getString("OrcamentoCategoriaID"))) + "," +  objForma.getString("Tag") + ";";

                            }
                            String EmpresaID = result2.getString("EmpresaID");

                            String UrlImage = "http://homologacao.car10.com.br/repository/company/" + EmpresaID + "/" + EmpresaID + ".jpg";
                            Drawable Img = LoadImageFromWebOperations(UrlImage);

                            if(Img.equals(null))
                            {
                                UrlImage = "http://homologacao.car10.com.br/imagens/no_image.jpg";
                                Img = LoadImageFromWebOperations(UrlImage);
                            }
                            ocorrencia_data[i] = new Orcamento(Img, Nomeoficina, read, ImgNota, Prazo, DataReg, TotalGeral, DataAbertura, Distancia, Cortesia, PecasATrocar, PecasARecuperar, TotalMO, Desconto, Forma, MyID, Aval, Tempo, EmpresaID, PecasATrocarValor);

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                AplicaLista();



                            }
                        });
                    }
                }
                catch (Exception e) {
                        e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(getApplicationContext(),"Ocorreu um problema ao extrair as informações, tente novamente",Toast.LENGTH_LONG).show();
                            dialog.hide();



                        }
                    });


                }

            }
        }).start();

    }
    public void AplicaLista()
    {
        if(ocorrencia_data.length == 0)
        {
            layoutSemOrcamento.setVisibility(View.VISIBLE);
            dialog.hide();
            return;
        }
        final OrcamentoAdpter adapter = new OrcamentoAdpter(Act,
                R.layout.listview_item_row_orcamento, ocorrencia_data);
        ListView listView1 = (ListView)findViewById(R.id.list);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Orcamento We = adapter.getItem(i);
                Intent intent = new Intent(Act,DetalhesOrcamentoActivity.class);
                intent.putExtra("dataAbertura",We.dataAbertura);
                intent.putExtra("distancia",We.distancia);
                intent.putExtra("cortesia",We.cortesia);
                intent.putExtra("pecasATrocar",We.pecasATrocar);
                intent.putExtra("pecasATrocarValor",We.PecasATrocarValor);
                intent.putExtra("pecasARecuperar",We.pecasARecuperar);
                intent.putExtra("totalMO",We.totalMO);
                intent.putExtra("desconto",We.desconto);
                intent.putExtra("forma",We.forma);
                intent.putExtra("myID",We.myID);
                intent.putExtra("EmpresaID",We.EmpresaID);
                intent.putExtra("Status", Status);

                String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Car10";
                File dir = new File(file_path);
                File file = new File(dir, "oficina.png");
                FileOutputStream fOut = null;
                Bitmap bmpImageOficina = ((BitmapDrawable)We.imagemOficina).getBitmap();

                try {
                    fOut = new FileOutputStream(file);

                    bmpImageOficina.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if(We.imgNota != null) {
                    Bitmap bmpImageNota = ((BitmapDrawable) We.imgNota).getBitmap();
                    ByteArrayOutputStream oaos = new ByteArrayOutputStream();
                    bmpImageNota.compress(Bitmap.CompressFormat.PNG, 100, oaos);
                    byte[] b_bmpImageNota = oaos.toByteArray();
                    intent.putExtra("imgNota",b_bmpImageNota);
                }
                else
                {
                    intent.putExtra("imgNota","");
                }
                intent.putExtra("NomeOficina",We.NomeOficina);
                intent.putExtra("naolido",We.naolido);
                intent.putExtra("TempoConserto",We.TempoConserto);
                intent.putExtra("Validade",We.Validade);
                intent.putExtra("Valor",We.Valor);
                startActivity(intent);



            }
        });
        dialog.hide();
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }


    private void PreparaMenu(Bundle savedInstanceState) {
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.btn_menu,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ocorrencia, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {

            case R.id.action_info: {
                Intent intent = new Intent(this,VisualizarOrcamentoActivity.class);
                intent.putExtra("OrcamentoID",OrcamentoID);
                intent.putExtra("Status", Status);
                startActivity(intent);

            };break;

        }

        return super.onOptionsItemSelected(item);
    }
    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    private void selectItem(int position) {
        switch (position)
        {
            case 0: {
                Intent intent = new Intent(this,Cotacao1Activity.class);
                startActivity(intent);
                finish();
            }break;
            case 1: {
                Intent intent = new Intent(this,OcorrenciaListaActivity.class);
                startActivity(intent);
                finish();
            }break;
            case 2: {
                Intent intent = new Intent(this,AtualizaDadosActivity.class);
                startActivity(intent);
                finish();
            }break;
            case 3: {
                Intent intent = new Intent(this,SobreActivity.class);
                startActivity(intent);
                finish();
            }break;
            case 4: {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("id");
                editor.remove("email");
                editor.remove("senha");
                editor.remove("nome");
                editor.commit();
                Toast.makeText(getApplicationContext(), "Logoff efetuado com sucesso", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
                finish();
            }break;
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
