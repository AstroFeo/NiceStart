package com.aperez.nicestart;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class Main extends AppCompatActivity {

    private WebView miVisorWeb;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        WebView mycontext = findViewById(R.id.vistaweb);
        //Hago que el contextMenu salga al mantener el text1
        registerForContextMenu(mycontext);

        // DENTRO del Oncreate
        // cast al Layout SwipeRefresh con el que rodeamos la vista
        // en el xml y le colocamos un listener
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.myswipe);
        swipeLayout.setOnRefreshListener(mOnRefreshListener);

        //La vista dentro es un webview con permiso para zoom
        miVisorWeb = findViewById(R.id.vistaweb);
//        miVisorWeb.getSettings().setJavaScriptEnabled(true);
//        miVisorWeb.getSettings().setBuiltInZoomControls(true);
        WebSettings webSettings = miVisorWeb.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        miVisorWeb.loadUrl("https://thispersondoesnotexist.com");


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void showAlertDialogButtonClicked(Main mainActivity) {

        // setup the alert builder
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);

//        //el dialogo estandar tiene título/icono pero podemos sustituirlo por un XML a medida
//        builder.setTitle("Achtung!");
//        builder.setMessage("Where do you go?");
        builder.setIcon(R.drawable.user);
        builder.setCancelable(true);

        // un XML a medida para el diálogo
        builder.setView(getLayoutInflater().inflate(R.layout.alertdialog_view, null));

        // add the buttons
        builder.setPositiveButton("Go to Profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something like...
                Toast toast = Toast.makeText(Main.this, "Enviandote al profile", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(Main.this, Profile.class);
                startActivity(intent);
                dialog.dismiss();

            }
        });

        builder.setNegativeButton("Do nothing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // do something like...

                dialog.dismiss();
            }
        });

        builder.setNeutralButton("SignOut", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // do something like...
                System.exit(0);

                dialog.dismiss();
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //Creo el Menu ContextMenu que sale al mantener el text1
    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected (MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
//            showAlertDialogButtonClicked(Main.this);

            Toast toast = Toast.makeText(this, "Copy", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (id == R.id.item2) {
            Toast toast = Toast.makeText(this, "Downloading..", Toast.LENGTH_SHORT);
            toast.show();
        }

        return super.onOptionsItemSelected(item);
    }


    //Creo el Menu OptionsMenu que aparece en el action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
//            showAlertDialogButtonClicked(Main.this);

            Toast toast = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT);
            toast.show();


            final ConstraintLayout mLayout = findViewById(R.id.main);

            Snackbar snackbar = Snackbar
                    .make(mLayout, "Has la pulsado settings?", Snackbar.LENGTH_LONG);
//                .setAction("UNDO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Snackbar snackbar1 = Snackbar.make(mLayout, "Action is restored!", Snackbar.LENGTH_SHORT);
//                        snackbar1.show();
//                    }
//                });

            snackbar.show();

        }
        if (id == R.id.item2) {
            showAlertDialogButtonClicked(Main.this);
        }

        return super.onOptionsItemSelected(item);
    }

    protected SwipeRefreshLayout.OnRefreshListener
            mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//            Toast toast0 = Toast.makeText(Main.this, "Hi there! I don't exist :)", Toast.LENGTH_LONG);
//            toast0.show();

            final ConstraintLayout mLayout = findViewById(R.id.main);

            Snackbar snackbar = Snackbar
                .make(mLayout, "fancy a Snack while you refresh?", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar snackbar1 = Snackbar.make(mLayout, "Action is restored!", Snackbar.LENGTH_SHORT);
                            snackbar1.show();
                        }
                    });


            snackbar.show();

            miVisorWeb.reload();
            swipeLayout.setRefreshing(false);
        }
    };
}