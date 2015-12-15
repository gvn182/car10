package simple.com.car10;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    ProgressDialog dialog;
    LoginButton loginButton;
    private UiLifecycleHelper uiHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.authButton);
        loginButton.setReadPermissions(Arrays.asList("email", "user_birthday", "public_profile"));
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        //PreparaMenu(savedInstanceState);

        Button btnEntrar = (Button)findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidaCampos())
                {
                    dialog = ProgressDialog.show(LoginActivity.this, "", "Carregando...", true);
                    EfetuaLogin();
                }

            }
        });


    }
    public void EfetuaLogin() {


        new Thread(new Runnable() {
            public void run() {
                DAL dal = new DAL();
                final  EditText txtUsuario = (EditText) findViewById(R.id.txtEmail);
                final  EditText txtSenha = (EditText) findViewById(R.id.txtSenha);
                String result =  dal.EfetuaLogin(txtUsuario.getText().toString(), txtSenha.getText().toString());

                try {


                    if(result != null) {
                       final  JSONObject object = new JSONObject(result);


                        if(object.getInt("Status") == 1)
                        {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = preferences.edit();

                                    try {

                                        dialog.hide();
                                        final JSONArray result =  object.getJSONArray("Data");
                                        final JSONObject obj = new JSONObject(result.getString(0));

                                        editor.putString("id",obj.getString("ID"));
                                        editor.putString("email",obj.getString("Email"));
                                        editor.putString("senha",txtSenha.getText().toString());
                                        editor.putString("nome",obj.getString("NomeCompleto"));
                                        editor.commit();
                                        Intent intent = new Intent(LoginActivity.this,OcorrenciaListaActivity.class);
                                        startActivity(intent);

                                    } catch (JSONException e) {
                                        e.printStackTrace();


                                    }




                                }
                            });

                        }
                        else if (object.getInt("Status") == 2)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Toast.makeText(getApplicationContext(),object.getString("Message"),Toast.LENGTH_LONG).show();
                                        dialog.hide();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        dialog.hide();
                                    }
                                }
                            });
                        }
                        else
                        {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(getApplicationContext(),"Ocorreu um problema ao efetuar o login", Toast.LENGTH_SHORT).show();
                                    dialog.hide();
                                }
                            });

                        }


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(getApplicationContext(),"Occorreu um problema ao efetuar o login", Toast.LENGTH_SHORT).show();
                            dialog.hide();
                        }
                    });
                }



            }
        }).start();


    }
    private boolean ValidaCampos()
    {
        EditText txtUsuario = (EditText)findViewById(R.id.txtEmail);
        EditText txtSenha = (EditText)findViewById(R.id.txtSenha);

        if(txtUsuario.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Preencha um e-mail válido",Toast.LENGTH_LONG).show();
            return false;
        }

        if(txtSenha.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Preencha a senha",Toast.LENGTH_LONG).show();
            return false;
        }

        if(!isEmailValid(txtUsuario.getText().toString()))
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
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    private void onSessionStateChange(Session session, SessionState state,
                                      Exception exception) {
        final  EditText txtUsuario = (EditText) findViewById(R.id.txtEmail);
        final  EditText txtSenha = (EditText) findViewById(R.id.txtSenha);
        // When Session is successfully opened (User logged-in)
        if (state.isOpened()) {

            Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user
                // object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {


                        txtUsuario.setText(user.getProperty("email").toString());
                        txtSenha.setText(user.getId());
                        dialog = ProgressDialog.show(LoginActivity.this, "", "Carregando...", true);
                        EfetuaLogin();


                    }
                }
            }).executeAsync();
        } else if (state.isClosed()) {

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
