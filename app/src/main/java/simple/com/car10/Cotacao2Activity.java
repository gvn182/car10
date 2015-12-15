package simple.com.car10;

import android.app.ProgressDialog;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;


public class Cotacao2Activity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_cotacao2);
        PreparaMenu(savedInstanceState);
        GoogleMap mMap;

            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(false);

        ReloadInfos();

        ImageView imgLocation = (ImageView)findViewById(R.id.imgLocation);
        imgLocation.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText txtCEP = (EditText)findViewById(R.id.txtCEP);

                if(txtCEP.getText().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Preencha o CEP",Toast.LENGTH_LONG).show();
                    return;
                }

                EfetuaPesquisa();


            }
        });



        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                                finish();
                                       }

                                   });

        Button btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();

                EditText txtCEP = (EditText) findViewById(R.id.txtCEP);
                TextView lblEndereco = (TextView) findViewById(R.id.lblEndereco);
                TextView txtNumero = (EditText) findViewById(R.id.txtNumero);
                if(txtCEP.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Preencha o CEP",Toast.LENGTH_LONG).show();
                    return;
                }
                if(lblEndereco.getText().toString().equals("Digite o CEP"))
                {
                    Toast.makeText(getApplicationContext(),"Preencha o CEP e pressione o botão para pesquisar",Toast.LENGTH_LONG).show();
                    return;
                }

                if(txtNumero.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Preencha o número",Toast.LENGTH_LONG).show();
                    return;
                }
                editor.putString("txtCEP",txtCEP.getText().toString());
                editor.putString("lblEndereco",lblEndereco.getText().toString());
                editor.putString("txtNumero",txtNumero.getText().toString());
                editor.commit();


                Intent intent = new Intent(Cotacao2Activity.this,Cotacao3Activity.class);
                startActivity(intent);
            }
        });





    }
    private void ReloadInfos() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        EditText txtCEP = (EditText) findViewById(R.id.txtCEP);
        TextView lblEndereco = (TextView) findViewById(R.id.lblEndereco);
        TextView txtNumero = (EditText) findViewById(R.id.txtNumero);

        txtCEP.setText(preferences.getString("txtCEP", ""));
        lblEndereco.setText(preferences.getString("lblEndereco", "Digite o CEP"));
        txtNumero.setText(preferences.getString("txtNumero", ""));

    }
    private void EfetuaPesquisa()
    {
        dialog = ProgressDialog.show(Cotacao2Activity.this, "", "Carregando...", true);
        new Thread(new Runnable() {
            public void run() {
              final  DAL dal = new DAL();
                EditText txtCEP = (EditText) findViewById(R.id.txtCEP);
                String result = dal.PegaEndereco(txtCEP.getText().toString());

                try {

                    if(!result.equals("") && result != null)
                    {
                     JSONObject object = new JSONObject(result);


                        final  String Rua = object.getString("streetName");
                        final  String Vila = object.getString("neighborhood");
                        final  String Cidade = object.getString("city");
                        final  String Estado = object.getString("state");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                EditText txtCEP = (EditText) findViewById(R.id.txtCEP);
                                TextView lblEndereco = (TextView) findViewById(R.id.lblEndereco);
                                lblEndereco.setText(Rua + ", " + Vila + ", " + Cidade + ", " + Estado);

                                String Pos = dal.LatitudeLongitudeBy(getApplicationContext(),txtCEP.getText().toString());
                                float latitude = Float.parseFloat(Pos.split(";")[0].replace(",","."));
                                float longitude = Float.parseFloat(Pos.split(";")[1].replace(",","."));


                                CameraUpdate center=
                                        CameraUpdateFactory.newLatLng(new LatLng(latitude,
                                                longitude));
                                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
                              GoogleMap  mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                                        .getMap();

                                Marker marker = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(latitude, longitude))
                                        .title("")
                                        .snippet(""));

                                mMap.moveCamera(center);
                                mMap.animateCamera(zoom);
                                dialog.hide();


                            }
                        });
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView lblEndereco = (TextView) findViewById(R.id.lblEndereco);
                                lblEndereco.setText("Digite o CEP");
                            }
                        });
                        dialog.hide();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView lblEndereco = (TextView) findViewById(R.id.lblEndereco);
                            lblEndereco.setText("Digite o CEP");
                            dialog.hide();
                        }
                    });
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
        inflater.inflate(R.menu.menu_cotacao, menu);
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
                Intent intent = new Intent(this,HomeActivity.class);
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
                Intent intent = new Intent(this,HomeActivity.class);
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
