package nl.kevinvanmierlo.picassoblurtest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity
{
    private ImageView imageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Picasso.with(MainActivity.this)
                        .load("http://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Done.png/480px-Done.png")
                        .placeholder(R.drawable.questionmark)
                        .error(R.drawable.error)
                        .transform(new BlurTransformation(MainActivity.this))
                        .into(imageView);
            }
        }, 2000);
    }
}
