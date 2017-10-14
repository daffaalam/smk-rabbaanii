package id.sch.rbs.smk.rabbaanii;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class main extends AppCompatActivity {

    private static final String TAG = main.class.getSimpleName();
    private BottomNavigationView bottomNavBar;
    private Fragment fragment;
    private FragmentManager fragmentManager;

//    WebView web_rbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        web_rbs = (WebView) findViewById(R.id.web_rbs);
//
//        WebSettings web_set = web_rbs.getSettings();
//        web_set.setJavaScriptEnabled(true);
//        web_rbs.setWebViewClient(new WebViewClient());
//        web_rbs.loadUrl("http://rbs.sch.id");

        bottomNavBar = (BottomNavigationView) findViewById(R.id.bottom_navbar);
        bottomNavBar.inflateMenu(R.menu.bottom_navigation_items);
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.main_container, new fragment_home()).commit();

        bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id) {
                    case R.id.menu_home:
                        if (!isNetworkAvailable()) {
                            popup();
                        }
                        if (isNetworkAvailable()) {
                            fragment = new fragment_home();
                            break;
                        }
                }
                switch (id) {
                    case R.id.menu_about:
                        fragment = new fragment_about();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();

                return true;
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void popup() {
        AlertDialog.Builder alrtbuild = new AlertDialog.Builder(main.this);
        alrtbuild
                .setTitle("WARNING!")
                .setMessage("Pastikan Perangkat Anda Terhubung Internet")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!isNetworkAvailable()) {
                            popup();
                        }
                        if (isNetworkAvailable()) {

                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alrt = alrtbuild.create();
        alrt.show();
    }

//    public void about(View view){
//        startActivity(new Intent(getApplicationContext(), about.class));
//    }

//    public void home(View view){
//        startActivity(new Intent(getApplicationContext(), main.class));
//    }
}