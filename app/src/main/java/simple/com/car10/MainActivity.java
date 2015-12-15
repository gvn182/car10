package simple.com.car10;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Car10";
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();

           SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
           SharedPreferences.Editor editor = preferences.edit();
            if(preferences.getString("primeiravez","true").equals("true")) {
                editor.putString("primeiravez", "false");
                editor.commit();
                Intent intent = new Intent(MainActivity.this, TutorialActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        else
            {
                String  ID = preferences.getString("id", "-1");
                if(ID.equals("-1")) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, OcorrenciaListaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            }
    }
}
