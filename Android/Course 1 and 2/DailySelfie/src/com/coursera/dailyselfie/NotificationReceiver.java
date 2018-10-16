package com.coursera.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

public class NotificationReceiver extends BroadcastReceiver {

	private String contentTitle = "Daily Selfie";
	private String contentText = "Daily Selfie Reminder!";
	private String tickerText = "Selfie Time!";
	private long[] vibratePattern = {100, 500, 500, 500, 500, 500};
	private final int NOTIFICATION_ID = 12967;
	private Uri tone;
	private PendingIntent mIntent;
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		mIntent = PendingIntent.getActivity(context, 0, 
				new Intent(context, MainActivity.class), 
				Intent.FLAG_ACTIVITY_NEW_TASK);
		
		tone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		
		Notification.Builder builder = new Notification.Builder(context)
						.setContentTitle(contentTitle)
						.setContentText(contentText)
						.setTicker(tickerText)
						.setContentIntent(mIntent)
						.setSmallIcon(R.drawable.camera)
						.setVibrate(vibratePattern)
						.setSound(tone)
						.setAutoCancel(true);
		
		NotificationManager mManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mManager.notify(NOTIFICATION_ID, builder.build());
	}
}
