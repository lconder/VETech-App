package lconde.vetech;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    public static final String VETERINARIAS="veterinarias";
    public static final String TIPS="tips";
    public static final String EVENTOS="eventos";

    Toolbar toolbar;
    ViewPager mPager;
    SlidingTabLayout mTabs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.md_orange_800);
            }
        });
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        mTabs.setViewPager(mPager);




        if(Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
            /*ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height=0;*/
        }

        //if(Build.VERSION.SDK_INT >= 21)
          //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new));


        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.button)
                .build();



        ImageView itemI = new ImageView(this);
        itemI.setImageResource(R.mipmap.icon);//EVENTOS

        ImageView itemII = new ImageView(this);
        itemII.setImageResource(R.mipmap.tips);//TIPS

        ImageView itemIII = new ImageView(this);
        itemIII.setImageResource(R.mipmap.vet);//VETERINARIAS

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton bt1 = itemBuilder.setContentView(itemI).build();
        SubActionButton bt2 = itemBuilder.setContentView(itemII).build();
        SubActionButton bt3 = itemBuilder.setContentView(itemIII).build();

        bt1.setTag(EVENTOS);
        bt2.setTag(TIPS);
        bt3.setTag(VETERINARIAS);


        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(bt1)
                .addSubActionView(bt2)
                .addSubActionView(bt3)
                     
                .attachTo(actionButton)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v.getTag().equals(VETERINARIAS))
        {
            Toast.makeText(getApplicationContext(), "Veterinarias", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MapVet.class);
            startActivity(intent);
        }
        if(v.getTag().equals(TIPS))
        {
            Toast.makeText(getApplicationContext(), "Eventos", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Eventos.class);
            startActivity(intent);
        }
        if(v.getTag().equals(EVENTOS))
        {
            Toast.makeText(getApplicationContext(), "Rastreo de Collar ", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, nfcReader.class);
            startActivity(intent);
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter
    {
        int icons[] = {R.mipmap.bone,R.mipmap.question,R.mipmap.heart};
        FragmentManager fragmentManager;


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager=null;
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment =  null;

            switch (position) {
                case 0:
                    fragment =  Tab1.newInstance("", "");
                    break;
                case 1:
                    fragment = Tab2.newInstance("", "");
                    break;
                case 2:
                    fragment = Tab3.newInstance("", "");
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            Drawable drawable= getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,96,96);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString=new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount()
        {
            return 3;
        }
    }


}
