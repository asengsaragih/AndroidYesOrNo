package com.suncode.yesorno;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private ProgressBar progressBar;
    private ImageButton refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView_result);
        progressBar = findViewById(R.id.progressbar);
        refreshButton = findViewById(R.id.refresh);

        //hide action bar
        getSupportActionBar().hide();

        //transparent status/notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //change color text in status/notification bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        getData();

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                refreshButton.setVisibility(View.GONE);

                getData();
            }
        });
    }

    private void getData() {
        Service service = Generator.build().create(Service.class);
        Call<Model> call = service.getData();

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();

                //show image after drawable was loaded
                Glide.with(getApplicationContext())
                        .load(model.getImage())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.VISIBLE);
                                refreshButton.setVisibility(View.VISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.VISIBLE);
                                refreshButton.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(imageView);
                
                textView.setText(model.getAnswer());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }
}