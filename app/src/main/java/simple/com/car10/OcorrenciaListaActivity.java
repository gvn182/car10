package simple.com.car10;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class OcorrenciaListaActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private Context Act;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencia_lista);
        this.Act = this;

        PreparaMenu(savedInstanceState);
        dialog = ProgressDialog.show(this, "", "Carregando...", true);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ID = preferences.getString("id","-1");

        if(ID.equals("-1"))
        {
            dialog.hide();
            Toast.makeText(this,"É necessário efetuar login para verificar suas ocorrências",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        else
        {
            GetOcorrencias(ID);
        }

    }


    private void GetOcorrencias(final String idUser) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    DAL dal = new DAL();
                    final String Result = dal.GetOcorrencias(idUser);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Result != null) {
                                try {

                                    final JSONObject object = new JSONObject(Result);
                                    if(object.getString("Status").equals("1"))
                                    {
                                        final JSONArray result =  object.getJSONArray("Data");
                                        Weather weather_data[] = new Weather[result.length()];

                                        for(int i=0; i< result.length(); i++)
                                        {
                                            final JSONObject obj = new JSONObject(result.getString(i));
                                            String Placa = obj.getString("Placa");
                                            String Status = obj.getString("Status");
                                            String ID = obj.getString("ID");
                                            String DataReg = obj.getString("DataRegistro");

                                            String Ano = DataReg.substring(0,4);
                                            String Mes = DataReg.substring(5,7);
                                            String Dia = DataReg.substring(8,10);
                                            DataReg = Dia + "/" + Mes + "/" + Ano;

                                            String Resource = "icon_aguardando_orcamento";
                                            String Hex = "#979898";
                                            if(Status.equals("Aguardando orçamentos"))
                                            {
                                                Resource = "icon_aguardando_orcamento";
                                                Hex = "#979898";
                                            }
                                            if(Status.equals("Orçamentos disponíveis"))
                                            {
                                                Status = "2 Orçamentos disponíveis";
                                                Resource = "icon_orcamentos_disponiveis";
                                                Hex = "#0064FF";
                                            }
                                            if(Status.equals("Orçamento aprovado"))
                                            {
                                                Resource = "icon_orcamento_aprovado";
                                                Hex = "#FF681B";
                                            }
                                            if(Status.equals("Cancelada"))
                                            {
                                                Resource = "icon_ocorrencia_cancelada";
                                                Hex = "#E42320";
                                            }
                                            weather_data[i] = new  Weather(ID,"Ocorrencia:" + ID,DataReg,Placa, Resource,Status,Hex);
                                        }
                                        final WeatherAdapter adapter = new WeatherAdapter(Act,
                                                R.layout.listview_item_row_cotacao, weather_data);
                                        ListView listView1 = (ListView)findViewById(R.id.list);
                                        listView1.setAdapter(adapter);
                                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                Weather We = adapter.getItem(i);

                                                //if(!We.titulo.equals("Orçamento aprovado")) {

                                                    Intent intent = new Intent(Act, OrcamentoListaActivity.class);

                                                    String Status = "0";
                                                if(We.titulo.equals("Orçamento aprovado") || We.titulo.equals("Cancelada"))
                                                {
                                                    Status = "1";
                                                }
                                                    intent.putExtra("OrcamentoID", We.id);
                                                    intent.putExtra("Placa", We.placa);
                                                    intent.putExtra("Data", We.data);
                                                    intent.putExtra("Status", Status);
                                                    startActivity(intent);
                                                //}
                                            }
                                        });

                                        dialog.hide();
                                        //Toast.makeText(getApplicationContext(),Placas,Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),object.getString("Message"),Toast.LENGTH_LONG).show();
                                        dialog.hide();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),"Ocorreu um problema ao extrair as informações, tente novamente",Toast.LENGTH_LONG).show();
                                    dialog.hide();
                                }


                            } else {
                                Toast.makeText(getApplicationContext(),"Ocorreu um problema ao extrair as informações, tente novamente",Toast.LENGTH_LONG).show();
                                dialog.hide();
                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.hide();
                }
            }
        }).start();
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
