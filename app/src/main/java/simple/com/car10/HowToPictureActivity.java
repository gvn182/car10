package simple.com.car10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class HowToPictureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtopicture);

        final Intent intent = this.getIntent();
        Button btnOkHowToTake = (Button) findViewById(R.id.btnOkHowToTake);
        btnOkHowToTake.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {


                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }



}
