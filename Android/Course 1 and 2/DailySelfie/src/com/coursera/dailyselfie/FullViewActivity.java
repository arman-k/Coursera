package com.coursera.dailyselfie;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class FullViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_view);
		
		ImageView fullView = (ImageView) findViewById(R.id.full_view);
		fullView.setImageBitmap(BitmapFactory.decodeFile(getIntent()
				.getStringExtra("photo")));
		setTitle("Daily Selfie");
		
	}

}
