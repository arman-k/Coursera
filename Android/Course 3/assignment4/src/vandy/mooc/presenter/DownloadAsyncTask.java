package vandy.mooc.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

public class DownloadAsyncTask extends AsyncTask<Uri, Void, Uri>
{
	private final Context mContext; //TODO Alternative - Use WeakReference
	private final ImagePresenter mPresenter;
	private final FilterAsyncTask mFilterAsyncTask;
	
	//The pathname of the directory where the image will be downloaded
	private Uri mDirectoryPathname;
	
	//The URL of the image that is to be downloaded
	private Uri mImageUrl;
	
	DownloadAsyncTask(Context context, ImagePresenter presenter, FilterAsyncTask filterAsyncTask)
	{
		mContext = context;
		mPresenter = presenter;
		mFilterAsyncTask = filterAsyncTask;
	}
	
	//Use the downloadImage() method in the model layer to download the image in background
	//Return the filepath of the downloaded image
	protected Uri doInBackground(Uri... params)
	{
		mImageUrl = params[0];
		mDirectoryPathname = params[1];
		
		return mPresenter.getModel().
				downloadImage(mContext, mImageUrl, mDirectoryPathname);
	}
	
	//Execute the FilterAsyncTask to filter the image, 
	protected void onPostExecute(Uri pathToImageFile)
	{
		mFilterAsyncTask.executeOnExecutor(
				AsyncTask.THREAD_POOL_EXECUTOR, 
				mImageUrl, //TODO Alternative - won't be needing this
				pathToImageFile, //Filepath of the downloaded image
				mDirectoryPathname); //Directory where the filtered image will be saved, same as where it was downloaded
		//TODO Alternative - return what is returned by the executeOnExecutor
	}
}
