package fi.oulu.unitour.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fi.oulu.unitour.R;
import fi.oulu.unitour.helpers.RoundedCornersTransform;

public class ImageGameActivity extends AppCompatActivity {

    ImageView img1, img2, img3, img4;
    ProgressBar img1Loading,img2Loading,img3Loading,img4Loading;

    final String imageUrl1 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/locations%2Fdatagarage.jpg?alt=media&token=923e3a43-c539-483b-a0e0-ac8e5cf3bb6b";
    final String imageUrl2 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/locations%2Fscience.jpg?alt=media&token=4f57180e-e236-41ec-a608-e575e999d406";
    final String imageUrl3 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/locations%2Ftechnology.jpg?alt=media&token=2c98ae44-6fb0-40ee-86fa-c171b78be415";
    final String imageUrl4 = "https://firebasestorage.googleapis.com/v0/b/unitour-7b1ed.appspot.com/o/locations%2Ftellus.jpg?alt=media&token=539b8230-975a-4982-977d-6ba551a9b044";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_game);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img1Loading = (ProgressBar) findViewById(R.id.img1Loading);
        img2Loading = (ProgressBar) findViewById(R.id.img2Loading);
        img3Loading = (ProgressBar) findViewById(R.id.img3Loading);
        img4Loading = (ProgressBar) findViewById(R.id.img4Loading);

        Callback callback1 = new Callback() {
            @Override
            public void onSuccess() {
                img1Loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
            }
        };

        Callback callback2 = new Callback() {
            @Override
            public void onSuccess() {
                img2Loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
            }
        };

        Callback callback3 = new Callback() {
            @Override
            public void onSuccess() {
                img3Loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
            }
        };

        Callback callback4 = new Callback() {
            @Override
            public void onSuccess() {
                img4Loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
            }
        };

        Picasso.with(ImageGameActivity.this).load(imageUrl1).fit().centerCrop().transform(new RoundedCornersTransform()).into(img1, callback1);
        Picasso.with(ImageGameActivity.this).load(imageUrl2).fit().centerCrop().transform(new RoundedCornersTransform()).into(img2, callback2);
        Picasso.with(ImageGameActivity.this).load(imageUrl3).fit().centerCrop().transform(new RoundedCornersTransform()).into(img3, callback3);
        Picasso.with(ImageGameActivity.this).load(imageUrl4).fit().centerCrop().transform(new RoundedCornersTransform()).into(img4, callback4);

    }
}
