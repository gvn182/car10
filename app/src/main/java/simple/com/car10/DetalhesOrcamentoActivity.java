package simple.com.car10;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class DetalhesOrcamentoActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    ProgressDialog dialog;
    private String MyID;
    private String NomeOficina;
    String EmpresaID;
    String Status;
    Pecas[] lstPecas;
    Pecas[] lstPecasRecuperar;

    Activity Act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_detalhesorcamento);
        Act = this;





        PreparaMenu(savedInstanceState);


        FillCampos();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MyID,"1");
        editor.commit();

        RelativeLayout imageLayout = (RelativeLayout) findViewById(R.id.imageLayout);
        imageLayout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalhesOrcamentoActivity.this,PerfilOficinaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EmpresaID",EmpresaID);
                startActivity(intent);
            }
        });

        ImageView imgClickListaPecas = (ImageView)findViewById(R.id.imgPecasTrocar);
        imgClickListaPecas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetalhesOrcamentoActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.list_layout, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Peças a trocar");
                ListView lv = (ListView) convertView.findViewById(R.id.listView1);
                final PecasAdapter adapter = new PecasAdapter(Act,
                        R.layout.listview_item_row_peca, lstPecas);
                lv.setAdapter(adapter);
                alertDialog.show();
            }
        });

        ImageView imgClickListaPecasRecuperar = (ImageView)findViewById(R.id.imgPecasRecuperar);
        imgClickListaPecasRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetalhesOrcamentoActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.list_layout, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Peças a recuperar");
                ListView lv = (ListView) convertView.findViewById(R.id.listView1);
                final PecasAdapter adapter = new PecasAdapter(Act,
                        R.layout.listview_item_row_peca, lstPecasRecuperar);
                lv.setAdapter(adapter);
                alertDialog.show();
            }
        });



        Button btnNext = (Button) findViewById(R.id.btnAprovar);
        if(Status.equals("1"))
        {
            btnNext.setVisibility(View.GONE);
        }

        btnNext.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                SalvaRequisicao();


            }
        });


        ((ImageView)findViewById(R.id.imgCortesia_1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Cristalização",Toast.LENGTH_SHORT).show();
            }
        });
        ((ImageView)findViewById(R.id.imgCortesia_2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Higienização",Toast.LENGTH_SHORT).show();
            }
        });
        ((ImageView)findViewById(R.id.imgCortesia_3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Lavagem completa",Toast.LENGTH_SHORT).show();
            }
        });
        ((ImageView)findViewById(R.id.imgCortesia_4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Lavagem simples",Toast.LENGTH_SHORT).show();
            }
        });
        ((ImageView)findViewById(R.id.imgCortesia_5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Leva e traz",Toast.LENGTH_SHORT).show();
            }
        });
        ((ImageView)findViewById(R.id.imgCortesia_6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Polimento",Toast.LENGTH_SHORT).show();
            }
        });



            ((ImageView)findViewById(R.id.imgBoleto)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Boleto",Toast.LENGTH_SHORT).show();
                }
            });


            ((ImageView)findViewById(R.id.imgDebito)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Débito",Toast.LENGTH_SHORT).show();
                }
            });

            ((ImageView)findViewById(R.id.imgCredito)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Crédito",Toast.LENGTH_SHORT).show();
                }
            });

            ((ImageView)findViewById(R.id.imgParcelamento)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Parcelamento",Toast.LENGTH_SHORT).show();
                }
            });

            ((ImageView)findViewById(R.id.imgCheque)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Cheque",Toast.LENGTH_SHORT).show();
                }
            });

            ((ImageView)findViewById(R.id.imgDinheiro)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Dinheiro",Toast.LENGTH_SHORT).show();
                }
            });

    }

    private void SalvaRequisicao() {
        dialog = ProgressDialog.show(this, "", "Carregando...", true);
        new Thread(new Runnable() {
            public void run() {
                DAL dal = new DAL();
                final String Result = dal.AprovaCotacao(MyID);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Result != null) {
                            dialog.hide();
                            Intent intent = new Intent(DetalhesOrcamentoActivity.this,AtualizaDadosActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("NomeOficina",NomeOficina);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Ocorreu algum erro, tente novamente!", Toast.LENGTH_LONG).show();
                            dialog.hide();
                        }
                    }
                });
            }
        }).start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void FillCampos() {

        Bundle We = getIntent().getExtras();
        Status = We.getString("Status");
        String dataAbertura = We.getString("dataAbertura");
        String distancia = We.getString("distancia");
        String cortesia = We.getString("cortesia");
        String pecasATrocar = We.getString("pecasATrocar");
        String pecasARecuperar = We.getString("pecasARecuperar");
        String pecasATrocarValor =  We.getString("pecasATrocarValor");
        String totalMO = We.getString("totalMO");
        String desconto = We.getString("desconto");
        String forma = We.getString("forma");

        NomeOficina = We.getString("NomeOficina");
        EmpresaID = We.getString("EmpresaID");
        boolean naolido = We.getBoolean("naolido");

        String TempoConserto = We.getString("TempoConserto");
        String Validade = We.getString("Validade");
        String Valor = We.getString("Valor");
        MyID = We.getString("myID");

        ((TextView)findViewById(R.id.lblOficina)).setText(NomeOficina);
        ((TextView)findViewById(R.id.lblTempo)).setText("Tempo para conserto: " + TempoConserto);
        ((TextView)findViewById(R.id.lblValor)).setText(Valor);
        ((TextView)findViewById(R.id.lblOficina)).setText(NomeOficina);

        ((TextView)findViewById(R.id.lblAbertura)).setText(dataAbertura);
        ((TextView)findViewById(R.id.lblValidade)).setText(Validade);

        ((TextView)findViewById(R.id.lblDistancia)).setText(distancia + " KM");

        ((TextView)findViewById(R.id.lblPecasAtrocar)).setText(pecasATrocarValor);

        //((TextView)findViewById(R.id.lblPecasARecuperar)).setText(pecasARecuperar);



        ((TextView)findViewById(R.id.lblTotalMaoDeObra)).setText(totalMO);
        ((TextView)findViewById(R.id.lblDesconto)).setText(desconto);


        if(cortesia.contains("1"))
        {
            ((ImageView)findViewById(R.id.imgCortesia_1)).setImageResource( R.drawable.icon_cortesia_limpo_active);
        }
        if(cortesia.contains("2"))
        {
            ((ImageView)findViewById(R.id.imgCortesia_2)).setImageResource( R.drawable.icon_cortesia_aspirador_active);
        }
        if(cortesia.contains("3"))
        {
            ((ImageView)findViewById(R.id.imgCortesia_3)).setImageResource( R.drawable.icon_cortesia_lavagem_active);
        }
        if(cortesia.contains("4"))
        {
            ((ImageView)findViewById(R.id.imgCortesia_4)).setImageResource( R.drawable.icon_cortesia_ducha_active);
        }
        if(cortesia.contains("5"))
        {
            ((ImageView)findViewById(R.id.imgCortesia_5)).setImageResource( R.drawable.icon_cortesia_levatras_active);
        }
        if(cortesia.contains("6"))
        {
            ((ImageView)findViewById(R.id.imgCortesia_6)).setImageResource( R.drawable.icon_cortesia_polimento_active);
        }


        if(!forma.contains("boleto"))
        {
            ((ImageView)findViewById(R.id.imgBoleto)).setVisibility(View.GONE);

        }

        if(!forma.contains("cartao_debito"))
        {
            ((ImageView)findViewById(R.id.imgDebito)).setVisibility(View.GONE);
        }

        if(!forma.contains("cartao_credito"))
        {
            ((ImageView)findViewById(R.id.imgCredito)).setVisibility(View.GONE);
        }

        if(!forma.contains("parcelado"))
        {
            ((ImageView)findViewById(R.id.imgParcelamento)).setVisibility(View.GONE);
        }

        if(!forma.contains("cheque"))
        {
            ((ImageView)findViewById(R.id.imgCheque)).setVisibility(View.GONE);
        }

        if(!forma.contains("dinheiro"))
        {
            ((ImageView)findViewById(R.id.imgDinheiro)).setVisibility(View.GONE);
        }

        if(!We.get("imgNota").equals("")) {
            byte[] b = We.getByteArray("imgNota");
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            ImageView image = (ImageView) findViewById(R.id.ratingBar);
            image.setImageBitmap(bmp);
        }
        else
        {
            ImageView image = (ImageView) findViewById(R.id.ratingBar);
            image.setVisibility(View.GONE);
        }
        RelativeLayout layOfc = (RelativeLayout) findViewById(R.id.imageLayout);
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Car10";
        File dir = new File(file_path);
        File file = new File(dir, "oficina.png");
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        BitmapDrawable background = new BitmapDrawable(myBitmap);
        layOfc.setBackground(background);




         String[] PecSplit = pecasATrocar.split(";");
         lstPecas = new Pecas[PecSplit.length];


        for (int i=0; i< PecSplit.length; i++)
        {

            String Titulo = PecSplit[i].split(",")[0];
            String Tag = "";

            if(PecSplit[i].split(",").length == 2)
            Tag =  PecSplit[i].split(",")[1];
            lstPecas[i] = new Pecas (Titulo,Tag);
        }


        PecSplit = pecasARecuperar.split(";");
        lstPecasRecuperar = new Pecas[PecSplit.length];


        for (int i=0; i< PecSplit.length; i++)
        {
            lstPecasRecuperar[i] = new Pecas(PecSplit[i],"");
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
        inflater.inflate(R.menu.menu_main, menu);
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

            case R.id.action_new: {
                Intent intent = new Intent(this,Cotacao1Activity.class);
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
            }break;
            case 1: {
                Intent intent = new Intent(this,OcorrenciaListaActivity.class);
                startActivity(intent);
            }break;
            case 2: {
                Intent intent = new Intent(this,AtualizaDadosActivity.class);
                startActivity(intent);
            }break;
            case 3: {
                Intent intent = new Intent(this,SobreActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            }break;
        }


    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
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
