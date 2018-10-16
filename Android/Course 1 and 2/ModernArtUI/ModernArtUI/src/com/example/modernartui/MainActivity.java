package com.example.modernartui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SeekBar seekbar = (SeekBar) findViewById(R.id.slider);
		
		final TextView whiteFrag = (TextView) findViewById(R.id.whiteFrag);
		final TextView frag1 = (TextView) findViewById(R.id.frag1);
		final TextView frag2 = (TextView) findViewById(R.id.frag2);
		final TextView frag3 = (TextView) findViewById(R.id.frag3);
		final TextView frag4 = (TextView) findViewById(R.id.frag4);
		final TextView frag5 = (TextView) findViewById(R.id.frag5);
		final TextView frag6 = (TextView) findViewById(R.id.frag6);
		
		whiteFrag.setBackgroundColor(Color.rgb(255, 255, 255));
		frag1.setBackgroundColor(Color.rgb(0, 0, 255));
		frag2.setBackgroundColor(Color.rgb(255, 255, 0));
		frag3.setBackgroundColor(Color.rgb(0, 255, 0));
		frag4.setBackgroundColor(Color.rgb(255, 0, 255));
		frag5.setBackgroundColor(Color.rgb(255, 0, 0));
		frag6.setBackgroundColor(Color.rgb(0, 255, 255));
		
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				int [] frag1Color = {0, 0, 255};
				int [] frag2Color = {255, 255, 0};
				int [] frag3Color = {0, 255, 0};
				int [] frag4Color = {255, 0, 255};
				int [] frag5Color = {255, 0, 0};
				int [] frag6Color = {0, 255, 255};
				
				frag1Color[0] = (int) (frag1Color[0] + 1.1 * progress); 
				frag1Color[1] = (int) (frag1Color[1] + 1.5 * progress); 
				frag1Color[2] = (int) (frag1Color[2] - 1.25 * progress); 
				frag2Color[0] = (int) (frag2Color[0] - 1.15 * progress); 
				frag2Color[1] = (int) (frag2Color[1] - 1.45 * progress); 
				frag2Color[2] = (int) (frag2Color[2] + 1.8 * progress);
				frag3Color[0] = (int) (frag3Color[0] + 1.6 * progress); 
				frag3Color[1] = (int) (frag3Color[1] - 1.5 * progress); 
				frag3Color[2] = (int) (frag3Color[2] + 2 * progress);
				frag4Color[0] = (int) (frag4Color[0] - 1.4 * progress); 
				frag4Color[1] = (int) (frag4Color[1] + 1.4 * progress); 
				frag4Color[2] = (int) (frag4Color[2] - 1.4 * progress);
				frag5Color[0] = (int) (frag5Color[0] - 1.2 * progress); 
				frag5Color[1] = (int) (frag5Color[1] + 2.1 * progress); 
				frag5Color[2] = (int) (frag5Color[2] + 1.95 * progress);
				frag6Color[0] = (int) (frag6Color[0] + 2.5 * progress); 
				frag6Color[1] = (int) (frag6Color[1] - 2.5 * progress); 
				frag6Color[2] = (int) (frag6Color[2] - 1.1 * progress);
				
				frag1.setBackgroundColor(Color.rgb(frag1Color[0], frag1Color[1], frag1Color[2]));
				frag2.setBackgroundColor(Color.rgb(frag2Color[0], frag2Color[1], frag2Color[2]));
				frag3.setBackgroundColor(Color.rgb(frag3Color[0], frag3Color[1], frag3Color[2]));
				frag4.setBackgroundColor(Color.rgb(frag4Color[0], frag4Color[1], frag4Color[2]));
				frag5.setBackgroundColor(Color.rgb(frag5Color[0], frag5Color[1], frag5Color[2]));
				frag6.setBackgroundColor(Color.rgb(frag6Color[0], frag6Color[1], frag6Color[2]));
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		final String URL = "http://www.moma.org";
		
		if (id == R.id.more_info) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
			
			builder.setPositiveButton(R.string.visit_MOMA, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id)
				{
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
					startActivity(intent);
				}
			});
			
			builder.setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id)
				{
					
				}
			});
			
			
			builder.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
