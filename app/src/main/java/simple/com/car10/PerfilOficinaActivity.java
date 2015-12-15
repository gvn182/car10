package simple.com.car10;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;


public class PerfilOficinaActivity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    ProgressDialog dialog;
    Activity Act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Act = this;
        dialog = ProgressDialog.show(this, "", "Carregando...", true);
        Bundle We = getIntent().getExtras();
        String ID = We.getString("EmpresaID");
        FillFields(ID);
        setContentView(R.layout.activity_perfiloficina);
        GoogleMap mMap;
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(false);
        PreparaMenu(savedInstanceState);

        Button btnDescricao = (Button)findViewById(R.id.btnDescricao);
        btnDescricao.setOnClickListener(
                new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View view) {

                        RelativeLayout layoutDescricao  = (RelativeLayout)findViewById(R.id.layoutDescricao);
                        layoutDescricao.setVisibility(View.VISIBLE);

                        LinearLayout layoutAvaliacao  = (LinearLayout)findViewById(R.id.layoutAvaliacoes);
                        layoutAvaliacao.setVisibility(View.INVISIBLE);

                        Button btnDescricao = (Button)findViewById(R.id.btnDescricao);
                        btnDescricao.setBackground(getResources().getDrawable(R.drawable.selectedbuttonshape));

                        Button btnAvaliacao = (Button)findViewById(R.id.btnAvaliacoes);
                        btnAvaliacao.setBackground(getResources().getDrawable(R.drawable.unselectedbuttonshape));

                    }
                }
        );


        Button btnAvaliacao = (Button)findViewById(R.id.btnAvaliacoes);
        btnAvaliacao.setOnClickListener(
                new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View view) {

                        RelativeLayout layoutDescricao  = (RelativeLayout)findViewById(R.id.layoutDescricao);
                        layoutDescricao.setVisibility(View.INVISIBLE);

                        LinearLayout layoutAvaliacao  = (LinearLayout)findViewById(R.id.layoutAvaliacoes);
                        layoutAvaliacao.setVisibility(View.VISIBLE);

                        Button btnDescricao = (Button)findViewById(R.id.btnDescricao);
                        btnDescricao.setBackground(getResources().getDrawable(R.drawable.unselectedbuttonshape));

                        Button btnAvaliacao = (Button)findViewById(R.id.btnAvaliacoes);
                        btnAvaliacao.setBackground(getResources().getDrawable(R.drawable.selectedbuttonshape));

                    }
                }
        );

    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void FillFields(final String id) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    DAL dal = new DAL();
                    final String Result = dal.GetOficinaBy(id);
                    final JSONObject object = new JSONObject(Result);
                    final JSONObject Data = object.getJSONObject("Data");
                    final JSONObject obj = Data.getJSONObject("Empresa");
                    final JSONObject aval = Data.getJSONObject("Avaliacao");
                    final JSONObject objAvaliacao = Data.getJSONObject("Avaliacao");
                    final JSONArray JsonComentarios = objAvaliacao.getJSONArray("Comentarios");


                   final Comentario[] comentario_data = new Comentario[JsonComentarios.length()];

                    for(int i=0; i< JsonComentarios.length(); i++)
                    {
                        final JSONObject objForma = new JSONObject(JsonComentarios.getString(i));
                        String Descricao = objForma.getString("Comentarios");
                        String Nota = objForma.getString("Nota");
                        comentario_data[i] = new Comentario(Descricao,Nota);
                    }


                    final String Rua = obj.getString("Endereco") + "," + obj.getString("Numero");
                    final String Endereco = obj.getString("Cidade") + "," + obj.getString("Estado") + " - " + obj.getString("CEP");
                    final String Telefone = "(" + obj.getString("DDD") + ") " + obj.getString("Telefone");
                    final String Descricao = obj.getString("Descricao");
                    final float latitude = obj.getLong("Latitude");
                    final float longitude = obj.getLong("Longitude");

                   final float media =  Math.round(aval.getLong("Media"));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            final ComentarioAdapter adapter = new ComentarioAdapter(Act,
                                    R.layout.listview_item_row_comentario, comentario_data);
                            ListView listView1 = (ListView)findViewById(R.id.list);
                            listView1.setAdapter(adapter);



                            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                    "/Car10";
                            File dir = new File(file_path);
                            File file = new File(dir, "oficina.png");
                            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            ((ImageView) findViewById(R.id.imgOficina)).setImageBitmap(myBitmap);

                            if (media == 0.0) {
                                ((ImageView) findViewById(R.id.imgAvaliacao)).setVisibility(View.GONE);
                                ((TextView) findViewById(R.id.txtAvaliacaoSem)).setVisibility(View.VISIBLE);

                            } else if (media == 1.0) {
                                ((ImageView) findViewById(R.id.imgAvaliacao)).setImageDrawable(getResources().getDrawable(R.drawable.s_1_star_rating));
                            } else if (media == 2.0) {
                                ((ImageView) findViewById(R.id.imgAvaliacao)).setImageDrawable(getResources().getDrawable(R.drawable.s_2_star_rating));
                            } else if (media == 3.0) {
                                ((ImageView) findViewById(R.id.imgAvaliacao)).setImageDrawable(getResources().getDrawable(R.drawable.s_3_star_rating));
                            } else if (media == 4.0) {
                                ((ImageView) findViewById(R.id.imgAvaliacao)).setImageDrawable(getResources().getDrawable(R.drawable.s_4_star_rating));
                            } else if (media == 5.0) {
                                ((ImageView) findViewById(R.id.imgAvaliacao)).setImageDrawable(getResources().getDrawable(R.drawable.s_5_star_rating));
                            }


                            ((TextView) findViewById(R.id.txtRua)).setText(Rua);
                            ((TextView) findViewById(R.id.txtEndereco)).setText(Endereco);
                            ((TextView) findViewById(R.id.txtTelefone)).setText(Telefone);
                            ((TextView) findViewById(R.id.txtDescricao)).setText(Descricao.equals("null") ? "" : Descricao);

                            CameraUpdate center =
                                    CameraUpdateFactory.newLatLng(new LatLng(latitude,
                                            longitude));
                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                            GoogleMap mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
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
                    catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Ocorreu algum erro, tente novamente!", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),"Logoff efetuado com sucesso",Toast.LENGTH_LONG).show();
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
