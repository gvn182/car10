package simple.com.car10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Cotacao1Activity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    AnimationDrawable Anim;
    ImageView imageCar;
    private static final int MAX_CLICK_DURATION = 200;
    private long startClickTime;
    ImageView imgCapo;
    ImageView imgParachoque;
    ImageView imgvidrofront;

    ImageView imglateral_direita;
    ImageView imglateral_direita_frontal;
    ImageView imglateral_direita_roda_frontal;
    ImageView imglateral_direita_roda_traseira;
    ImageView imglateral_direita_traseira;

    ImageView imglateral_esquerda;
    ImageView imglateral_esquerda_frontal;
    ImageView imglateral_esquerda_roda_frontal;
    ImageView imglateral_esquerda_roda_traseira;
    ImageView imglateral_esquerda_traseira;

    ImageView imgtraseira;
    ImageView imgvidro_traseiro;
    GestureDetector gdt;
    GestureDetector gdtP;
    String Title;
    String ID;
    int index = 0;
    boolean right = false;

    List<String> Partes = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotacao1);
        imageCar = (ImageView) findViewById(R.id.imgCar);
        imageCar.setDrawingCacheEnabled(true);
        imgCapo = (ImageView) findViewById(R.id.imgCapo);
        imgCapo.setDrawingCacheEnabled(true);
        imgParachoque = (ImageView) findViewById(R.id.imgParachoque);
        imgParachoque.setDrawingCacheEnabled(true);

        imglateral_direita = (ImageView) findViewById(R.id.imglateraldireita);
        imglateral_direita.setDrawingCacheEnabled(true);
        imglateral_direita_frontal = (ImageView) findViewById(R.id.imglateraldireitafrontal);
        imglateral_direita_frontal.setDrawingCacheEnabled(true);
        imglateral_direita_roda_frontal = (ImageView) findViewById(R.id.imglateraldireitarodafrontal);
        imglateral_direita_roda_frontal.setDrawingCacheEnabled(true);
        imglateral_direita_roda_traseira = (ImageView) findViewById(R.id.imglateraldireitarodatraseira);
        imglateral_direita_roda_traseira.setDrawingCacheEnabled(true);
        imglateral_direita_traseira = (ImageView) findViewById(R.id.imglateraldireitatraseira);
        imglateral_direita_traseira.setDrawingCacheEnabled(true);

        imglateral_esquerda = (ImageView) findViewById(R.id.imglateralesquerda);
        imglateral_esquerda.setDrawingCacheEnabled(true);
        imglateral_esquerda_frontal = (ImageView) findViewById(R.id.imglateralesquerdafrontal);
        imglateral_esquerda_frontal.setDrawingCacheEnabled(true);
        imglateral_esquerda_roda_frontal = (ImageView) findViewById(R.id.imglateralesquerdarodafrontal);
        imglateral_esquerda_roda_frontal.setDrawingCacheEnabled(true);
        imglateral_esquerda_roda_traseira = (ImageView) findViewById(R.id.imglateralesquerdarodatraseira);
        imglateral_esquerda_roda_traseira.setDrawingCacheEnabled(true);
        imglateral_esquerda_traseira = (ImageView) findViewById(R.id.imglateralesquerdatraseira);
        imglateral_esquerda_traseira.setDrawingCacheEnabled(true);

        imgvidrofront = (ImageView) findViewById(R.id.imgvidrofront);
        imgvidrofront.setDrawingCacheEnabled(true);

        imgtraseira = (ImageView) findViewById(R.id.imgtraseira);
        imgtraseira.setDrawingCacheEnabled(true);
        imgvidro_traseiro = (ImageView) findViewById(R.id.imgvidrotraseiro);
        imgvidro_traseiro.setDrawingCacheEnabled(true);

        PreparaMenu(savedInstanceState);

         gdt = new GestureDetector(new GestureListener());
        gdtP = new GestureDetector(new GestureListenerParts());

        Button btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Car10");
                String[] children = dir.list();
                if(children.length==0)
                {
                    Toast.makeText(getApplicationContext(),"Pelo menos uma foto ou um video é obrigatório",Toast.LENGTH_LONG).show();
                    return;
                }


                Intent intent = new Intent(Cotacao1Activity.this,Cotacao2Activity.class);
                startActivity(intent);
            }
        });

        SetClick(imgCapo,"Capô","capo");
        SetClick(imgParachoque,"Dianteira","dianteira");

        SetClick(imglateral_direita,"Lateral direita","lateral_direita");
        SetClick(imglateral_direita_frontal,"Lateral dianteira direita","lateral_dianteira_direita");
        SetClick(imglateral_direita_roda_frontal,"Roda dianteira direita","roda_dianteira_direita");
        SetClick(imglateral_direita_roda_traseira,"Roda traseira direita","roda_traseira_direita");
        SetClick(imglateral_direita_traseira,"Lateral traseira direita","lateral_traseira_direita");

        SetClick(imglateral_esquerda, "Lateral esquerda", "lateral_esquerda");
        SetClick(imglateral_esquerda_frontal,"Lateral dianteira esquerda","lateral_dianteira_esquerda");
        SetClick(imglateral_esquerda_roda_frontal,"Roda dianteira esquerda","roda_dianteira_esquerda");
        SetClick(imglateral_esquerda_roda_traseira,"Roda traseira esquerda","roda_traseira_esquerda");
        SetClick(imglateral_esquerda_traseira,"Lateral traseira esquerda","lateral_traseira_esquerda");

        SetClick(imgtraseira,"Traseira","traseira");
        SetClick(imgvidro_traseiro,"Parabrisas traseiro","parabrisas_traseiro");
        SetClick(imgvidrofront,"Parabrisas dianteiro","parabrisas_dianteiro");

        imageCar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            }
        });

        SetPartsQtd();
        SumirFixos();
        ShowStaticImages();



    }

    private void SetClick(ImageView img, final String title, final String id) {

             img.setOnTouchListener(new
                                           View.OnTouchListener() {
                                               @Override
                                               public boolean onTouch(View v, MotionEvent event) {
                                                   gdtP.onTouchEvent(event);
                                                   Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
                                                   int color = bmp.getPixel((int) event.getX(), (int) event.getY());
                                                   if (color != Color.TRANSPARENT) {
                                                       Title = title;
                                                       ID = id;
                                                       return true;
                                                   }
                                                   return false;
                                               }
                                           });


    }


    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                SwingRightToLeft(velocityX);
                return false; // Right to left
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                SwingLeftToRight(velocityX);
                return false; // Left to right
            }

            if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                //  SwingRightToLeft(velocityX);
                return false; // Bottom to top
            }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                //Toast.makeText(getApplicationContext(),"TopToBottom",Toast.LENGTH_SHORT).show();;
                return false; // Top to bottom
            }
            return false;
        }


    }
    private class GestureListenerParts extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed (MotionEvent e)
        {
            View vCover = (View) findViewById(R.id.vCover);
            vCover.setVisibility(View.VISIBLE);
            Intent intent = new Intent(Cotacao1Activity.this, HowToPictureActivity.class);
            intent.putExtra("local", Title);
            intent.putExtra("localID", ID);
            startActivityForResult(intent, 1);
                return false;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                SwingRightToLeft(velocityX);
                return false; // Right to left
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                SwingLeftToRight(velocityX);
                return false; // Left to right
            }

            if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                //  SwingRightToLeft(velocityX);
                return false; // Bottom to top
            }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                //Toast.makeText(getApplicationContext(),"TopToBottom",Toast.LENGTH_SHORT).show();;
                return false; // Top to bottom
            }
            return false;
        }


    }
    private void SwingRightToLeft(float velocityX) {
        index++;
        if(index == 5)
            index = 1;

        right = false;

        PlayAnim();
    }

    private void SwingLeftToRight(float velocityX) {
        index--;
        if(index == -5)
            index = -1;

        right = true;

        PlayAnim();
    }

    private void PlayAnim() {

        SumirFixos();
        switch (index)
        {

            case 0:
            {
                if(!right)
                    imageCar.setBackgroundResource(R.drawable.direitaparafrente_);
                else
                    imageCar.setBackgroundResource(R.drawable.esquerdaparafrente);

            };break;

            case 1:
            {
                if(!right)
                imageCar.setBackgroundResource(R.drawable.frenteesquerda_);
                else
                    imageCar.setBackgroundResource(R.drawable.traseiraesquerda);
            };break;
            case 2:
            {
                if(!right)
                imageCar.setBackgroundResource(R.drawable.esquerdatraseira_);
                else
                    imageCar.setBackgroundResource(R.drawable.direitatraseira);
            };break;
            case 3:
            {
                if(!right)
                imageCar.setBackgroundResource(R.drawable.traseiradireita_);
                else
                    imageCar.setBackgroundResource(R.drawable.frenteparadireita);

            };break;
            case 4:
            {
                if(!right)
                imageCar.setBackgroundResource(R.drawable.direitaparafrente_);
                else
                imageCar.setBackgroundResource(R.drawable.frenteparadireita);
            };break;
            case -1: {

                if(right)
                imageCar.setBackgroundResource(R.drawable.frenteparadireita);
                else
                imageCar.setBackgroundResource(R.drawable.traseiradireita_);

            };break;
            case -2: {
                if(right)
                imageCar.setBackgroundResource(R.drawable.direitatraseira);
                else
                imageCar.setBackgroundResource(R.drawable.esquerdatraseira_);

            };break;

            case -3: {
                    if(right)
                        imageCar.setBackgroundResource(R.drawable.traseiraesquerda);
                    else
                        imageCar.setBackgroundResource(R.drawable.frenteesquerda_);


            };break;

            case -4: {
                if(right)
                imageCar.setBackgroundResource(R.drawable.esquerdaparafrente);
                else
                    imageCar.setBackgroundResource(R.drawable.frenteesquerda_);

            };break;
        }




 /*        Anim.setCallback(new AnimationDrawableCallback(Anim, imageCar) {
            @Override
            public void onAnimationComplete() {
                ShowStaticImages();
            }
        });
        Anim.start(); */

        Anim = (AnimationDrawable) imageCar.getBackground();
        CustomAnimationDrawableNew cad = new CustomAnimationDrawableNew(Anim) {
            @Override
            void onAnimationFinish() {
                ShowStaticImages();
            }
        };
        imageCar.setBackgroundDrawable(cad);

        cad.start();
    }
 private Drawable GetDraw(String name)
 {
     Resources resources = this.getResources();
     final int resourceId = resources.getIdentifier(name, "drawable",
             this.getPackageName());
     return resources.getDrawable(resourceId);
 }
    private void ShowStaticImages() {


        if (index == -4 || index ==0 || index ==4) {
            imgCapo.setBackground(GetDraw("capo" + Partes.get(0)));
            imgParachoque.setBackground(GetDraw("parachoque" + Partes.get(1)));
            imgvidrofront.setBackground(GetDraw("vidro_front" + Partes.get(8)));


            imgCapo.setVisibility(View.VISIBLE);
            imgParachoque.setVisibility(View.VISIBLE);
            imgvidrofront.setVisibility(View.VISIBLE);

        }
        else if (index == -1  || index == 3 || index ==4) {

            imglateral_direita.setBackground(GetDraw("lateral_direita" + Partes.get(4)));
            imglateral_direita_frontal.setBackground(GetDraw("lateral_direita_frontal" + Partes.get(2)));
            imglateral_direita_roda_frontal.setBackground(GetDraw("lateral_direita_roda_frontal" + Partes.get(10)));
            imglateral_direita_roda_traseira.setBackground(GetDraw("lateral_direita_roda_traseira" + Partes.get(12)));
            imglateral_direita_traseira.setBackground(GetDraw("lateral_direita_traseira" + Partes.get(6)));



            imglateral_direita.setVisibility(View.VISIBLE);
            imglateral_direita_frontal.setVisibility(View.VISIBLE);
            imglateral_direita_roda_frontal.setVisibility(View.VISIBLE);
            imglateral_direita_roda_traseira.setVisibility(View.VISIBLE);
            imglateral_direita_traseira.setVisibility(View.VISIBLE);
        }
        else if (index == -2  || index == 2) {

            imgtraseira.setBackground(GetDraw("porta_malas" + Partes.get(15)));
            imgvidro_traseiro.setBackground(GetDraw("vidro_traseiro" + Partes.get(9)));

            imgtraseira.setVisibility(View.VISIBLE);
            imgvidro_traseiro.setVisibility(View.VISIBLE);
        }
        else if (index == -3 || index ==1 || index ==-4) {


            imglateral_esquerda.setBackground(GetDraw("lateral_esquerda" + Partes.get(5)));
            imglateral_esquerda_frontal.setBackground(GetDraw("lateral_esquerda_frontal" + Partes.get(3)));
            imglateral_esquerda_roda_frontal.setBackground(GetDraw("lateral_esquerda_roda_frontal" + Partes.get(11)));
            imglateral_esquerda_roda_traseira.setBackground(GetDraw("lateral_esquerda_roda_traseira" + Partes.get(13)));
            imglateral_esquerda_traseira.setBackground(GetDraw("lateral_esquerda_traseira" + Partes.get(7)));

            imglateral_esquerda.setVisibility(View.VISIBLE);
            imglateral_esquerda_frontal.setVisibility(View.VISIBLE);
            imglateral_esquerda_roda_frontal.setVisibility(View.VISIBLE);
            imglateral_esquerda_roda_traseira.setVisibility(View.VISIBLE);
            imglateral_esquerda_traseira.setVisibility(View.VISIBLE);
        }
    }



    private void SumirFixos() {
        imgCapo.setVisibility(View.GONE);
        imgParachoque.setVisibility(View.GONE);

        imglateral_direita.setVisibility(View.GONE);
        imglateral_direita_frontal.setVisibility(View.GONE);
        imglateral_direita_roda_frontal.setVisibility(View.GONE);
        imglateral_direita_roda_traseira.setVisibility(View.GONE);
        imglateral_direita_traseira.setVisibility(View.GONE);

        imglateral_esquerda.setVisibility(View.GONE);
        imglateral_esquerda_frontal.setVisibility(View.GONE);
        imglateral_esquerda_roda_frontal.setVisibility(View.GONE);
        imglateral_esquerda_roda_traseira.setVisibility(View.GONE);
        imglateral_esquerda_traseira.setVisibility(View.GONE);

        imgtraseira.setVisibility(View.GONE);
        imgvidro_traseiro.setVisibility(View.GONE);
        imgvidrofront.setVisibility(View.GONE);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        View vCover = (View)findViewById(R.id.vCover);
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                Intent intent = new Intent(Cotacao1Activity.this,GetPicturesActivity.class);
                intent.putExtra("local", data.getStringExtra("local"));
                intent.putExtra("localID", data.getStringExtra("localID"));
                startActivityForResult(intent, 2);

            }
            else
            {
                vCover.setVisibility(View.INVISIBLE);
            }
        }
        else if (requestCode == 2)
        {
            if (resultCode == RESULT_OK) {

                vCover.setVisibility(View.INVISIBLE);
                SetPartsQtd();
                ShowStaticImages();

            }
            else
            {
                vCover.setVisibility(View.INVISIBLE);
            }

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

    public void SetPartsQtd() {
        Partes.clear();
        Partes.add("capo");
        Partes.add("dianteira");
        Partes.add("lateral_dianteira_direita");
        Partes.add("lateral_dianteira_esquerda");
        Partes.add("lateral_direita");
        Partes.add("lateral_esquerda");
        Partes.add("lateral_traseira_direita");
        Partes.add("lateral_traseira_esquerda");
        Partes.add("parabrisas_dianteiro");
        Partes.add("parabrisas_traseiro");
        Partes.add("roda_dianteira_direita");
        Partes.add("roda_dianteira_esquerda");
        Partes.add("roda_traseira_direita");
        Partes.add("roda_traseira_esquerda");
        Partes.add("teto");
        Partes.add("traseira");
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Car10";
        File dir = new File(file_path);
        for (int i = 0; i < Partes.size(); i++) {
            int qtd = 0;
            for (int k = 1; k <= 3; k++) {
                File file = new File(dir, "foto_" + Partes.get(i) + "_" + k + "_" + ".png");
                if (file.exists()) {
                    qtd++;
                }
                file = new File(dir, "video_" + Partes.get(i) + ".mp4");
                if (file.exists()) {
                    qtd++;
                }
            }
            if(qtd > 0)
            Partes.set(Partes.indexOf(Partes.get(i)),"_" + qtd );
            else
            Partes.set(Partes.indexOf(Partes.get(i)),"");



        }
    }

}
