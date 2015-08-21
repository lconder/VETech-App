package lconde.vetech;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by mac on 11/06/15.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);;
        setContentView(R.layout.splash);

        if(Build.VERSION.SDK_INT >= 21)
          getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent i = new Intent("android.intent.action.MAINACTIVITY");
                    startActivity(i);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}