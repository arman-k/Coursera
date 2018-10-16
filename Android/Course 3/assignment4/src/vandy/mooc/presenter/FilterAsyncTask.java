package vandy.mooc.presenter;

import vandy.mooc.common.BitmapUtils;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

public class FilterAsyncTask extends AsyncTask<Uri, Void, Uri>
{
	private final Context mContext;
	private final ImagePresenter mPresenter; //TODO Alternative - won't be needing this
	
	//Filepath of the image to be filtered
	private Uri mPathToImageFile;
	
	private Uri mImageUrl; //TODO Alternative - won't be needing this
	
	FilterAsyncTask(Context context, ImagePresenter presenter)
	{
		mContext = context;
		mPresenter = presenter; //TODO Alternative - won't be needing this
	}
	
	//Use the grayScaleFilter() method in BitmapUtils to filter the image
	//Return the filepath of the filtered image
	protected Uri doInBackground(Uri... params)
	{
		mImageUrl = params[0]; //TODO Alternative - won't be needing this
		mPathToImageFile = params[1]; //TODO Alternative - change back to params[0]
		
		//Directory where the filtered image will be saved
		Uri mDirectoryPathname = params[2]; //TODO Alternative - change back to params[1]
		
		return BitmapUtils.grayScaleFilter(
				mContext, 
				mPathToImageFile, 
				mDirectoryPathname); 
	}
	
	//Call onProcessingComplete() in Presenter layer to handle the image
	protected void onPostExecute(Uri mPathToFilteredFile)
	{
		mPresenter.onProcessingComplete(
				mImageUrl, //Needed in case the download failed. It will be displayed as a toast
				mPathToFilteredFile); //If download succeeded, will display it on the screen
		//TODO Alternative - return mPathToFilteredFile instead of above
	}
}
