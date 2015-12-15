package simple.com.car10;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Cotacao4Activity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    ProgressDialog dialog;
    String UserID = "";
    private UiLifecycleHelper uiHelper;
    private View otherView;
    LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_cotacao4);
        loginButton = (LoginButton) findViewById(R.id.authButton);
        loginButton.setReadPermissions(Arrays.asList("email", "user_birthday", "public_profile"));
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        PreparaMenu(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        UserID = extras.getString("id","-1");
        else
        UserID = "-1";



        ReloadInfos();
        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ValidaCampos()) {
                    return;
                }
                SalvaRequisicao();


            }
        });

        ((TextView)findViewById(R.id.lblTermos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cotacao4Activity.this, TermoAceiteActivity.class);
                startActivity(intent);

            }
        });
        ((TextView)findViewById(R.id.lblPolitica)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cotacao4Activity.this, PoliticaPrivacidadeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ReloadInfos() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

      String ID =  preferences.getString("id","-1");
       String Email = preferences.getString("email","");
        String Senha = preferences.getString("senha","");
    String Nome =   preferences.getString("nome","");

        if(!ID.equals("-1")) {

            EditText txtNomeCompleto = (EditText) findViewById(R.id.txtNomeCompleto);
            EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);
            EditText txtDDD = (EditText) findViewById(R.id.txtDDD);
            EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
            EditText txtSenha = (EditText) findViewById(R.id.txtSenha);
            EditText txtSenha2 = (EditText) findViewById(R.id.txtConfirmacao);

            txtNomeCompleto.setText(Nome);
            txtEmail.setText(Email);
            txtSenha.setText(Senha);
            txtSenha2.setText(Senha);
            txtTelefone.setText(preferences.getString("telefone",""));
            txtDDD.setText(preferences.getString("ddd",""));
            txtEmail.setEnabled(false);
            txtSenha.setEnabled(false);
            txtSenha2.setEnabled(false);
            txtNomeCompleto.setEnabled(false);

            LinearLayout lay = (LinearLayout)findViewById(R.id.layoutFace);
            lay.setVisibility(View.INVISIBLE);

        }

    }

    private void SalvaRequisicao() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        //Cotacao4
        final EditText txtNomeCompleto = (EditText) findViewById(R.id.txtNomeCompleto);
        final EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        final EditText txtDDD = (EditText) findViewById(R.id.txtDDD);
        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        final EditText txtSenha = (EditText) findViewById(R.id.txtSenha);


        //Cotacao2
        final String txtCEP;
        final String lblEndereco;
        final String txtNumero;

        txtCEP = (preferences.getString("txtCEP", ""));
        lblEndereco = (preferences.getString("lblEndereco", "Digite o CEP"));
        txtNumero = (preferences.getString("txtNumero", ""));

        //Cotacao3
        final String txtPlaca;
        final String ddMarca;
        final String ddModelo;
        final String txtAnoModelo;
        final String ddCor;
        final String ddPintura;
        final String ddSeguradora;
        final boolean chkBlindado;
        final boolean chkSeguro;

        txtPlaca = (preferences.getString("txtPlaca", ""));
        txtAnoModelo = (preferences.getString("txtAnoModelo", ""));
        ddMarca = preferences.getString("ddMarca", "");
        ddModelo = preferences.getString("ddModelo", "");
        ddCor = preferences.getString("ddCor", "");
        ddPintura = preferences.getString("ddPintura", "");
        ddSeguradora = preferences.getString("ddSeguradora", "");
        chkBlindado = preferences.getBoolean("chkBlindado", false);
        chkSeguro = preferences.getBoolean("chkSeguro", false);

        dialog = ProgressDialog.show(Cotacao4Activity.this, "", "Carregando...", true);
        new Thread(new Runnable() {
            public void run() {
                DAL dal = new DAL();
                final String Result = dal.SalvaCotacao(txtCEP, lblEndereco, txtNumero, txtPlaca, ddMarca, ddModelo, txtAnoModelo, ddCor, ddPintura, ddSeguradora, chkBlindado, chkSeguro, txtNomeCompleto, txtDDD, txtTelefone, txtEmail, txtSenha);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Result != null) {
                            ClearPrefs();
                            dialog.hide();



                            if(UserID.equals("-1"))
                            {
                                Toast.makeText(getApplicationContext(), "Seu login foi criado, por favor confirme seu e-mail e efetue o login.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Cotacao4Activity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                            else {
                                Intent intent = new Intent(Cotacao4Activity.this, OcorrenciaListaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Ocorreu algum erro, tente novamente!", Toast.LENGTH_LONG).show();
                            dialog.hide();
                        }
                    }
                });
            }
        }).start();
    }
    private void ClearPrefs() {
           SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
           SharedPreferences.Editor editor = preferences.edit();

        final EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        final EditText txtDDD = (EditText) findViewById(R.id.txtDDD);

        editor.putString("telefone",txtTelefone.getText().toString());
        editor.putString("ddd",txtDDD.getText().toString());
        editor.remove("txtCEP");
        editor.remove("lblEndereco");
        editor.remove("txtNumero");
        editor.remove("txtPlaca");
        editor.remove("txtAnoModelo");
        editor.remove("ddMarca");
        editor.remove("ddModelo");
        editor.remove("ddCor");
        editor.remove("ddPintura");
        editor.remove("ddSeguradora");
        editor.remove("chkBlindado");
        editor.remove("chkSeguro");

        editor.commit();

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Car10");
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }

    }

    private boolean ValidaCampos() {

        EditText txtNomeCompleto = (EditText)findViewById(R.id.txtNomeCompleto);
        if(txtNomeCompleto.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Preencha o nome completo", Toast.LENGTH_LONG).show();
            return false;
        }

        EditText txtDDD =  (EditText)findViewById(R.id.txtDDD);
        if(txtDDD.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Preencha o DDD",Toast.LENGTH_LONG).show();
            return false;
        }

        EditText txtTelefone = (EditText)findViewById(R.id.txtTelefone);
        if(txtTelefone.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Preencha o telefone",Toast.LENGTH_LONG).show();
            return false;
        }

        EditText txtEmail = (EditText)findViewById(R.id.txtEmail);
        if(txtEmail.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Preencha o e-mail",Toast.LENGTH_LONG).show();
            return false;
        }

        EditText txtSenha = (EditText)findViewById(R.id.txtSenha);
        if(txtSenha.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Preencha a senha",Toast.LENGTH_LONG).show();
            return false;
        }

        EditText txtConfirmacao = (EditText)findViewById(R.id.txtConfirmacao);
        if(txtConfirmacao.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Preencha a confirmação",Toast.LENGTH_LONG).show();
            return false;
        }

        if(!txtSenha.getText().toString().equals(txtConfirmacao.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"As duas senhas devem ser iguais",Toast.LENGTH_LONG).show();
            return false;
        }

        if(!isEmailValid(txtEmail.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Preencha um e-mail válido",Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }
    private boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
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
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    private void onSessionStateChange(Session session, SessionState state,
                                      Exception exception) {
        final EditText txtNomeCompleto = (EditText) findViewById(R.id.txtNomeCompleto);
        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        final EditText txtSenha = (EditText) findViewById(R.id.txtSenha);
        final EditText txtSenha2 = (EditText) findViewById(R.id.txtConfirmacao);
        // When Session is successfully opened (User logged-in)
        if (state.isOpened()) {
            // make request to the /me API to get Graph user
            Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user
                // object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {


                        txtNomeCompleto.setText(user.getName() + " " + user.getLastName());
                        txtEmail.setText(user.getProperty("email").toString());
                        txtSenha.setText(user.getId());
                        txtSenha2.setText(user.getId());

                        txtNomeCompleto.setEnabled(false);
                        txtEmail.setEnabled(false);
                        txtSenha.setEnabled(false);
                        txtSenha2.setEnabled(false);

                    }
                }
            }).executeAsync();
        } else if (state.isClosed()) {

            txtNomeCompleto.setText("");
            txtEmail.setText("");
            txtSenha.setText("");
            txtSenha2.setText("");
            txtNomeCompleto.setEnabled(true);
            txtEmail.setEnabled(true);
            txtSenha.setEnabled(true);
            txtSenha2.setEnabled(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}
