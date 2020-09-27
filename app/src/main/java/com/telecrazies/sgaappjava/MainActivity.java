package com.telecrazies.sgaappjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ProgressBar barraDeProgreso;
    ImageView imagenDePagina;
    WebView navegador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barraDeProgreso = findViewById(R.id.MyProgressBar);
        barraDeProgreso.setMax(100);

        imagenDePagina = findViewById(R.id.MyImageView);

        navegador = findViewById(R.id.MyWebView);
        navegador.loadUrl("https://www.google.com/");
        navegador.getSettings().setJavaScriptEnabled(true);
        navegador.setWebViewClient(new WebViewClient());
        navegador.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                barraDeProgreso.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                imagenDePagina.setImageBitmap(icon);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (navegador.canGoBack()){
            navegador.goBack();
        }
        else{
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_goBack:
                onBackPressed();
                break;
            case R.id.menu_goForward:
                onForwardPressed();
                break;
            case R.id.menu_refreshUrl:
                navegador.reload();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void onForwardPressed(){
        if(navegador.canGoForward()){
            navegador.goForward();
        }
        else {
            Toast.makeText(this,"No hay página guardada", Toast.LENGTH_SHORT).show();
        }
    }
}