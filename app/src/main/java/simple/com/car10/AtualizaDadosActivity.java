package simple.com.car10;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompatSideChannelService;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AtualizaDadosActivity extends FragmentActivity  {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    List<String> spinnerArraySexo;
    List<String> spinnerArraySexoID;
    List<String> spinnerArrayEstadoCivil;
    List<String> spinnerArrayEstadoCivilID;
    ProgressDialog dialog;
    String Oficina = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_atualizadados);

    PreparaMenu(savedInstanceState);
        dialog = ProgressDialog.show(this, "", "Carregando...", true);
        FillSexo();
        FillEstadoCivil();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ID = preferences.getString("id","-1");
        Bundle We = getIntent().getExtras();
        if(We != null)
        Oficina =  We.getString("NomeOficina","");
        else
        Oficina = "";






        final FragmentActivity frag = this;
        TextView txtDataNascimento = (TextView)findViewById(R.id.txtDataNascimento);
        txtDataNascimento.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerDialogFragment();
                 newFragment.show(getFragmentManager(),"datepicker");

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
                if (!ValidaCampos()) {
                    return;
                }
                dialog.show();
                AtualizaUsuario();

            }
        });
        if(ID.equals("-1"))
        {
            dialog.hide();
            Toast.makeText(this,"É necessário efetuar login para atualizar os dados",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        else
        {
            LoadData(ID);
        }

    }
private void AtualizaUsuario()
{

    new Thread(new Runnable() {
        public void run() {
            DAL dal = new DAL();
            Spinner ddSexo  = (Spinner)findViewById(R.id.ddSexo);
            Spinner ddEstadoCivil  = (Spinner)findViewById(R.id.ddEstadoCivil);
            EditText txtNome = (EditText) findViewById(R.id.txtNomeCompleto);
            EditText txtCpf = (EditText) findViewById(R.id.txtCPF);
            EditText txtDDD = (EditText) findViewById(R.id.txtDDD);
            EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);
            EditText txtDDD2 = (EditText) findViewById(R.id.txtDDD2);
            EditText txtTelefone2 = (EditText) findViewById(R.id.txtTelefone2);
            EditText txtDDD3 = (EditText) findViewById(R.id.txtDDD3);
            EditText txtTelefone3 = (EditText) findViewById(R.id.txtTelefone3);
            TextView txtDataNascimento = (TextView) findViewById(R.id.txtDataNascimento);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
           String ID = preferences.getString("id","-1");
            String Nome =preferences.getString("nome","");
            final String Result = dal.AtualizaUsuario(ID,Nome,txtCpf.getText().toString(),txtDataNascimento.getText().toString(),spinnerArraySexoID.get(ddSexo.getSelectedItemPosition()),spinnerArrayEstadoCivilID.get(ddEstadoCivil.getSelectedItemPosition()),
                    txtDDD.getText().toString(),txtTelefone.getText().toString(),txtDDD2.getText().toString(),txtTelefone2.getText().toString(),txtDDD3.getText().toString(),txtTelefone3.getText().toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Result != null) {
                        try {

                            final JSONObject object = new JSONObject(Result);
                            if(object.getString("Status").equals("1"))
                            {

                                dialog.hide();

                                if(!Oficina.equals("")) {
                                    Intent intent = new Intent(AtualizaDadosActivity.this, VoucherActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                                    intent.putExtra("NomeOficina", Oficina);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Intent intent = new Intent(AtualizaDadosActivity.this, OcorrenciaListaActivity.class);
                                    startActivity(intent);
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),object.getString("Message"),Toast.LENGTH_LONG).show();
                                dialog.hide();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Ocorreu um problema ao salvar os dados, tente novamente",Toast.LENGTH_LONG).show();
                            dialog.hide();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(),"Ocorreu um problema ao salvar os dados, tente novamente",Toast.LENGTH_LONG).show();
                        dialog.hide();
                    }
                }
            });
        }
    }).start();

}
    private void LoadData(final String ID) {
        DAL dal = new DAL();
        new Thread(new Runnable() {
            public void run() {
                DAL dal = new DAL();
                final String Result = dal.GetUser(ID);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Result != null) {
                            try {
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                Spinner ddSexo  = (Spinner)findViewById(R.id.ddSexo);
                                Spinner ddEstadoCivil  = (Spinner)findViewById(R.id.ddEstadoCivil);
                                EditText txtNome = (EditText) findViewById(R.id.txtNomeCompleto);
                                EditText txtCpf = (EditText) findViewById(R.id.txtCPF);
                                EditText txtDDD = (EditText) findViewById(R.id.txtDDD);
                                EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);
                                EditText txtDDD2 = (EditText) findViewById(R.id.txtDDD2);
                                EditText txtTelefone2 = (EditText) findViewById(R.id.txtTelefone2);
                                EditText txtDDD3 = (EditText) findViewById(R.id.txtDDD3);
                                EditText txtTelefone3 = (EditText) findViewById(R.id.txtTelefone3);
                                TextView txtDataNascimento = (TextView) findViewById(R.id.txtDataNascimento);
                                final JSONObject object = new JSONObject(Result);
                                final JSONArray result =  object.getJSONArray("Data");
                                final JSONObject obj = new JSONObject(result.getString(0));

                                txtNome.setText(preferences.getString("nome",""));
                                String Cpf = obj.getString("CPF").equals("null") ? "" : obj.getString("CPF");
                                txtCpf.setText(Cpf);
                                String DDD1 = obj.getString("DDD1").equals("null") ? "" : obj.getString("DDD1");
                                String Tel1 = obj.getString("Tel1").equals("null") ? "" : obj.getString("Tel1");
                                String DDD2 = obj.getString("DDD2").equals("null") ? "" : obj.getString("DDD2");
                                String Tel2 = obj.getString("Tel2").equals("null") ? "" : obj.getString("Tel2");
                                String DDD3 = obj.getString("DDD3").equals("null") ? "" : obj.getString("DDD3");
                                String Tel3 = obj.getString("Tel3").equals("null") ? "" : obj.getString("Tel3");
                                txtDDD.setText(DDD1);
                                txtTelefone.setText(Tel1);
                                txtDDD2.setText(DDD2);
                                txtTelefone2.setText(Tel2);
                                txtDDD3.setText(DDD3);
                                txtTelefone3.setText(Tel3);
                                String DataNas = obj.getString("DataNascimento").equals("null") ? "Selecione..." : obj.getString("DataNascimento");

                                if(!DataNas.equals("Selecione..."))
                                {
                                    String Ano = DataNas.substring(0,4);
                                    String Mes = DataNas.substring(5,7);
                                    String Dia = DataNas.substring(8,10);
                                    DataNas = Dia + "/" + Mes + "/" + Ano;

                                }
                                txtDataNascimento.setText(DataNas);

                                if(!obj.getString("Sexo").equals("null"))
                                ddSexo.setSelection(spinnerArraySexoID.indexOf(obj.getString("Sexo")));

                                if(!obj.getString("EstadoCivil").equals("null"))
                                    ddEstadoCivil.setSelection(spinnerArraySexoID.indexOf(obj.getString("EstadoCivil")));






                            } catch (JSONException e) {

                            }
                            dialog.hide();

                        } else {
                            dialog.hide();
                        }
                    }
                });
            }
        }).start();

    }

    private boolean ValidaCampos() {
        EditText txtNome = (EditText) findViewById(R.id.txtNomeCompleto);
        if (txtNome.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha seu nome completo", Toast.LENGTH_LONG).show();
            return false;
        }
        EditText txtCpf = (EditText) findViewById(R.id.txtCPF);
        if (txtCpf.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o CPF", Toast.LENGTH_LONG).show();
            return false;
        }


        TextView txtDataNascimento = (TextView) findViewById(R.id.txtDataNascimento);
        if (txtDataNascimento.getText().toString().equals("Selecione...")) {
            Toast.makeText(getApplicationContext(), "Preencha a data de nascimento", Toast.LENGTH_LONG).show();
            return false;
        }

        EditText txtDDD = (EditText) findViewById(R.id.txtDDD);
        if (txtDDD.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o DDD 1", Toast.LENGTH_LONG).show();
            return false;
        }
        EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        if (txtTelefone.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o Telefone 1", Toast.LENGTH_LONG).show();
            return false;
        }

        EditText txtDDD2 = (EditText) findViewById(R.id.txtDDD2);
        if (txtDDD2.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o DDD 2", Toast.LENGTH_LONG).show();
            return false;
        }
        EditText txtTelefone2 = (EditText) findViewById(R.id.txtTelefone2);
        if (txtTelefone2.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o Telefone 2", Toast.LENGTH_LONG).show();
            return false;
        }

        EditText txtDDD3 = (EditText) findViewById(R.id.txtDDD3);
        if (txtDDD3.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o DDD 3", Toast.LENGTH_LONG).show();
            return false;
        }
        EditText txtTelefone3 = (EditText) findViewById(R.id.txtTelefone3);
        if (txtTelefone3.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o Telefone 3", Toast.LENGTH_LONG).show();
            return false;
        }

      /*  EditText txtDDD2 = (EditText) findViewById(R.id.txtDDD2);
        EditText txtTelefone2 = (EditText) findViewById(R.id.txtTelefone2);
        EditText txtDDD3 = (EditText) findViewById(R.id.txtDDD3);
        EditText txtTelefone3 = (EditText) findViewById(R.id.txtTelefone3);

        String DDD2 = txtDDD2.getText().toString();
        String Telefone2 = txtTelefone2.getText().toString();
        String DDD3 = txtDDD3.getText().toString();
        String Telefone3 = txtTelefone3.getText().toString();

        if(!Telefone2.equals("") && DDD2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Preencha o DD2", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!Telefone3.equals("") && DDD3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Preencha o DDD3", Toast.LENGTH_LONG).show();
            return false;
        }
      */



        return  true;
    }

    private void FillSexo() {
        spinnerArraySexo = new ArrayList<String>();
        spinnerArraySexo.add("Masculino");
        spinnerArraySexo.add("Feminino");

        spinnerArraySexoID = new ArrayList<String>();
        spinnerArraySexoID.add("M");
        spinnerArraySexoID.add("F");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArraySexo);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.ddSexo);
        sItems.setAdapter(adapter);
    }
    private void FillEstadoCivil() {
        spinnerArrayEstadoCivil = new ArrayList<String>();
        spinnerArrayEstadoCivil.add("Solteiro(a)");
        spinnerArrayEstadoCivil.add("Casado(a)");
        spinnerArrayEstadoCivil.add("Divorciado(a)");
        spinnerArrayEstadoCivil.add("Viúvo(a)");

        spinnerArrayEstadoCivilID = new ArrayList<String>();
        spinnerArrayEstadoCivilID.add("solteiro");
        spinnerArrayEstadoCivilID.add("casado");
        spinnerArrayEstadoCivilID.add("divorciado");
        spinnerArrayEstadoCivilID.add("viuvo");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayEstadoCivil);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.ddEstadoCivil);
        sItems.setAdapter(adapter);
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
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
            }break;
            case 1: {
                Intent intent = new Intent(this,OcorrenciaListaActivity.class);
                startActivity(intent);
            }break;
            case 2: {

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
