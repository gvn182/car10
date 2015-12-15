package simple.com.car10;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GetPicturesActivity extends Activity {
    ImageView imgTirarFoto1;
    ImageView imgTirarFoto2;
    ImageView imgTirarFoto3;
    ImageView imgGravarVideo;
    String ID;
    String Nome;
    File dir;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getpictures);

        final Intent intent = this.getIntent();
        ID = intent.getStringExtra("localID");
        Nome = intent.getStringExtra("local");

        ((TextView) findViewById(R.id.lblComoTirarFoto)).setText(Nome);

        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Car10";
        dir = new File(file_path);

        imgTirarFoto1 = (ImageView)findViewById(R.id.imgTirarFoto1);

        imgTirarFoto1.setOnClickListener(new
                                         View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 if(!imgTirarFoto1.getTag().equals("thumb")){
                                                     File file = new File(dir, "foto_" + ID + "_1_" + ".png");
                                                     file.delete();
                                                     imgTirarFoto1.setImageDrawable(getResources().getDrawable(R.drawable.thumb));
                                                     imgTirarFoto1.setTag("thumb");
                                                 }
                                                 else {

                                                     Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                     if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                                         startActivityForResult(takePictureIntent, 1);
                                                     }
                                                 }
                                             }
                                         });

        imgTirarFoto2 = (ImageView)findViewById(R.id.imgTirarFoto2);

        imgTirarFoto2.setOnClickListener(new
                                                 View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View view) {



                                                         if(!imgTirarFoto2.getTag().equals("thumb")){
                                                             File file = new File(dir, "foto_" + ID + "_2_" + ".png");
                                                             file.delete();
                                                             imgTirarFoto2.setImageDrawable(getResources().getDrawable(R.drawable.thumb));
                                                             imgTirarFoto2.setTag("thumb");
                                                         }
                                                         else {
                                                             Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                             if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                                                 startActivityForResult(takePictureIntent, 2);
                                                             }

                                                         }
                                                     }
                                                 });


        imgTirarFoto3 = (ImageView)findViewById(R.id.imgTirarFoto3);

        imgTirarFoto3.setOnClickListener(new
                                                 View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View view) {


                                                         if(!imgTirarFoto3.getTag().equals("thumb")){
                                                             File file = new File(dir, "foto_" + ID + "_3_" + ".png");
                                                             file.delete();
                                                             imgTirarFoto3.setImageDrawable(getResources().getDrawable(R.drawable.thumb));
                                                             imgTirarFoto3.setTag("thumb");
                                                         }
                                                         else {
                                                             Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                             if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                                                 startActivityForResult(takePictureIntent, 3);
                                                             }
                                                         }
                                                     }
                                                 });

        imgGravarVideo = (ImageView)findViewById(R.id.imgGravarVideo);

        imgGravarVideo.setOnClickListener(new
                                                 View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View view) {

                                                         if(!imgGravarVideo.getTag().equals("thumb")){
                                                             File file = new File(dir, "videothumb_" + ID + ".png");
                                                             file.delete();
                                                             file = new File(dir, "video_" + ID + ".mp4");
                                                             file.delete();
                                                             imgGravarVideo.setImageDrawable(getResources().getDrawable(R.drawable.thumb));
                                                             imgGravarVideo.setTag("thumb");
                                                         }
                                                         else {


                                                             Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                                             intent.putExtra("android.intent.extra.durationLimit", 10);
                                                             intent.putExtra("android.intent.extra.videoQuality", 0);
                                                             File file = new File(dir, "video_" + ID + ".mp4");
                                                             Uri output = Uri.fromFile(file);
                                                             intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                                                             startActivityForResult(intent, 4);
                                                         }
                                                     }
                                                 });







        Button btnOkGetPictures = (Button) findViewById(R.id.btnOkGetPictures);
        btnOkGetPictures.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {


                setResult(RESULT_OK, intent);
                finish();
            }
        });

        CheckAllIHave(ID);


    }

    private void CheckAllIHave(String id) {
        File file;

        file = new File(dir, "foto_" + ID + "_1_" + ".png");
        if(file.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imgTirarFoto1.setImageBitmap(myBitmap);
            imgTirarFoto1.setTag("taken");
        }
        file = new File(dir, "foto_" + ID + "_2_" + ".png");
        if(file.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imgTirarFoto2.setImageBitmap(myBitmap);
            imgTirarFoto2.setTag("taken");
        }
        file = new File(dir, "foto_" + ID + "_3_" + ".png");
        if(file.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imgTirarFoto3.setImageBitmap(myBitmap);
            imgTirarFoto3.setTag("taken");
        }

        file = new File(dir, "videothumb_" + ID + ".png");
        if(file.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imgGravarVideo.setImageBitmap(myBitmap);
            imgGravarVideo.setTag("taken");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {




            switch (requestCode)
            {

                case 1:{
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imgTirarFoto1.setImageBitmap(imageBitmap);
                    imgTirarFoto1.setTag("taken");
                    File file = new File(dir, "foto_" + ID + "_1_" + ".png");
                    FileOutputStream fOut = null;
                    try {
                        fOut = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }break;

                case 2:{
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imgTirarFoto2.setImageBitmap(imageBitmap);
                    imgTirarFoto2.setTag("taken");
                    File file = new File(dir, "foto_" + ID + "_2_" + ".png");
                    FileOutputStream fOut = null;
                    try {
                        fOut = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }break;

                case 3:{
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imgTirarFoto3.setImageBitmap(imageBitmap);
                    imgTirarFoto3.setTag("taken");
                    File file = new File(dir, "foto_" + ID + "_3_" + ".png");
                          FileOutputStream fOut = null;
                          try {
                              fOut = new FileOutputStream(file);
                              imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                              fOut.flush();
                              fOut.close();
                          } catch (FileNotFoundException e) {
                              e.printStackTrace();
                          } catch (IOException e) {
                              e.printStackTrace();
                          }


                }break;
                case 4:{
                    File fileVideo = new File(dir, "video_" + ID + ".mp4");
                    Bitmap thumb = ThumbnailUtils.createVideoThumbnail(fileVideo.getAbsolutePath(),
                            MediaStore.Images.Thumbnails.MINI_KIND);
                    imgGravarVideo.setImageBitmap(thumb);
                    imgGravarVideo.setTag("taken");
                    File fileThumb = new File(dir, "videothumb_" + ID + ".png");
                    FileOutputStream fOut = null;
                    try {
                        fOut = new FileOutputStream(fileThumb);
                        thumb.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

              break;
            }
        }
    }

}
