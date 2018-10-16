package com.coursera.dailyselfie;

import android.graphics.Bitmap;

public class PhotoRecord {

	private String mPhotoName;
	private String mPath;
	private Bitmap mPhotoBitmap;
	
	public PhotoRecord(String mPhotoName, String mPath, Bitmap mPhotoBitmap) {
		this.mPhotoName = mPhotoName;
		this.mPath = mPath;
        this.mPhotoBitmap = mPhotoBitmap;
	}

	public PhotoRecord() {
	}
	
	public String getPhotoName() {
		return mPhotoName;
	}

	public void setPhotoName(String photoName) {
		this.mPhotoName = photoName;
	}
	
	public String getPath() {
		return mPath;
	}

	public void setPath(String path) {
		this.mPath = path;
	}

	public Bitmap getPhotoBitmap() {
		return mPhotoBitmap;
	}

	public void setPhotoBitmap(Bitmap photoBitmap) {
		this.mPhotoBitmap = photoBitmap;
	}

	@Override
	public String toString(){
		return "Photo: " + mPhotoName;
	}
}