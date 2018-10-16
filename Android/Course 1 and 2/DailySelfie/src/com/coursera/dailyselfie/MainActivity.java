package com.coursera.dailyselfie;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	public static final String TAG = "Daily-Selfie";
	public static final int REQUEST_IMAGE_CAPTURE = 1;
	private final long ALARM_DELAY = 2 * 60 * 1000L;
	protected String mCurrentPhotoPath;
	protected File photoFile;
	private PhotoViewAdapter mAdapter;
	private AlarmManager mAlarmManager;
	private Intent mNotificationReceiverIntent;
	private PendingIntent mNRIPendingIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.show();
		
		mAdapter = new PhotoViewAdapter(getApplicationContext());
		setListAdapter(mAdapter);
		
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mNotificationReceiverIntent = new Intent(MainActivity.this, 
				NotificationReceiver.class);
		mNRIPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 
				0, mNotificationReceiverIntent, 0);
		mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, 
				SystemClock.elapsedRealtime() + ALARM_DELAY, 
				ALARM_DELAY, mNRIPendingIntent);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		mAdapter.removeAllViews();
		File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File folder = new File(storageDir + "/DailySelfie");
		if (!folder.exists())
		{
			folder.mkdir();
		}
		File fileList[] = null;
		
		if (folder != null)
		{
			fileList = folder.listFiles();
			
			for (int i = 0; i < fileList.length; i++)
			{
				mAdapter.add(new PhotoRecord(fileList[i].getName(), 
						fileList[i].getAbsolutePath(),
						getScaledBitmap(fileList[i].getAbsolutePath())));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.open_camera) 
		{
			openCamera();
		}
		else if (id == R.id.remove_all)
		{
			removeAll();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	protected void openCamera() 
	{
		Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (capture.resolveActivity(getPackageManager()) != null)
		{
			try
			{
				photoFile = createImageFile();
			}
			catch (IOException ex)
			{
				Toast.makeText(getApplicationContext(), "Couldn't create file", Toast.LENGTH_LONG).show();
			}
			
			if (photoFile != null)
			{
				capture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
				startActivityForResult(capture, REQUEST_IMAGE_CAPTURE);
				galleryAddPic();
			}
		}
	}
	
	protected File createImageFile() throws IOException
	{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "Selfie_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File folder = new File(storageDir + "/DailySelfie");
		
		File image = File.createTempFile(imageFileName, ".jpg", folder);
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		
		return image;
	}
	
	private void galleryAddPic() 
	{
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(mCurrentPhotoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_CANCELED)
		{
			photoFile.delete();
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		PhotoRecord photo = (PhotoRecord) mAdapter.getItem(position);
		Intent openPhoto = new Intent(v.getContext(), FullViewActivity.class);
		openPhoto.putExtra("photo", photo.getPath());
		
		startActivity(openPhoto);
	}
	
	public void removeAll()
	{
		mAdapter.removeAllViews();
		File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File folder = new File(storageDir + "/DailySelfie");
		
		if (folder.exists())
		{
			File fileList[] = folder.listFiles();
			
			for (int i = 0; i < fileList.length; i++)
			{
				fileList[i].delete();
			}
			folder.delete();
		}
	}
	
	public static Bitmap getScaledBitmap(String path)
	{
	    Bitmap bitmap = BitmapFactory.decodeFile(path);
	    
	    if (bitmap != null)
	    {
	        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
	    }
	    
	    return bitmap;
	}
}
