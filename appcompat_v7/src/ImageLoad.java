package com.uustock.dayi.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lxl.uustock_android_utils.SDUtil;

public class ImageLoad {

	BitmapUtils bitmapUtils;
	static List<BitmapUtils> BitmapUtilsList = new ArrayList<BitmapUtils>();

	public ImageLoad(Context context) {
		String cachePath = SDUtil.SDCardPath() + "/" + context.getPackageName();
		bitmapUtils = new BitmapUtils(context, cachePath);
		BitmapUtilsList.add(bitmapUtils);
		init();
	}

	/**
	 * bitmapUtils 初始配置。
	 */
	public void init() {
		bitmapUtils.configMemoryCacheEnabled(true);
		bitmapUtils.configDiskCacheEnabled(true);
		bitmapUtils.configThreadPoolSize(6);
	}

	/**
	 * 异步加载图片。
	 * 
	 * @param imageView
	 * @param url
	 */
	public void loadImg(ImageView imageView, String url) {
		loadImg(imageView, url, new CustomBitmapLoadCallBack());
	}

	/**
	 * 异步加载图片并设置回调。
	 * 
	 * @param imageView
	 * @param url
	 */
	public void loadImg(ImageView imageView, String url,
			BitmapLoadCallBack<ImageView> callBack) {
		bitmapUtils.display(imageView, url, callBack);
	}

	/**
	 * 清除缓存。
	 */
	public void clear() {
		for (BitmapUtils bitmapUtils : BitmapUtilsList) {
			bitmapUtils.clearCache();
		}
		BitmapUtilsList.clear();
	}

	public class CustomBitmapLoadCallBack extends BitmapLoadCallBack<ImageView> {

		@Override
		public void onLoading(ImageView container, String uri,
				BitmapDisplayConfig config, long total, long current) {
		}

		@Override
		public void onLoadCompleted(ImageView container, String uri,
				Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
			// super.onLoadCompleted(container, uri, bitmap, config, from);
			fadeInDisplay(container, bitmap);
		}

		@Override
		public void onLoadFailed(ImageView arg0, String arg1, Drawable arg2) {
			// TODO 自动生成的方法存根

		}
	}

	private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(
			android.R.color.transparent);

	private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
		final TransitionDrawable transitionDrawable = new TransitionDrawable(
				new Drawable[] { TRANSPARENT_DRAWABLE,
						new BitmapDrawable(imageView.getResources(), bitmap) });
		imageView.setImageDrawable(transitionDrawable);
		transitionDrawable.startTransition(500);
	}
}
