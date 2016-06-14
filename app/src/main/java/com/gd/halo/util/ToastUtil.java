package com.gd.halo.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gd.halo.R;


/**
 * toast tool class
 *
 */
public class ToastUtil
{
	private static final String TAG = ToastUtil.class.getSimpleName();

	/**
	 * 显示短时间的Toast
	 * @param context 使用的context，通常是正在使用的android.app.Application后者 android.app.Activity对象
	 * @param message 要显示的内容
	 */
	public static void showShortToast(Context context, String message)
	{
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public static void showShortToast(Context context, int messageId)
	{
		Toast toast = Toast.makeText(context, messageId, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	
	/**
	 * 显示长时间的Toast
	 * @param context 使用的context，通常是正在使用的android.app.Application后者 android.app.Activity对象
	 * @param message 要显示的内容
	 */
	public static void showLongToast(Context context, String message)
	{
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.show();
	}
	
	public static void showLongToast(Context context, int messageId)
	{
		Toast toast = Toast.makeText(context, messageId, Toast.LENGTH_LONG);
		toast.show();
	}
	
	/**
	 * 显示短时间的Toast
	 * @param context 使用的context，通常是正在使用的android.app.Application后者 android.app.Activity对象
	 * @param message 要显示的内容
	 */
	public static void showCenterShortToast(Context context, String message)
	{
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public static void showCenterShortToast(Context context, int messageId)
	{
		Toast toast = Toast.makeText(context, messageId, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0,10);
		toast.show();
	}
	
	
	/**
	 * 显示长时间的Toast
	 * @param context 使用的context，通常是正在使用的android.app.Application后者 android.app.Activity对象
	 * @param message 要显示的内容
	 */
	public static void showCenterLongToast(Context context, String message)
	{
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public static void showCenterLongToast(Context context, int messageId)
	{
		Toast toast = Toast.makeText(context, messageId, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static Toast createCustomLayout(Context context, int id) {
		return createCustomLayout(context, context.getString(id));
	}

	public static Toast createCustomLayout(Context context, String message) {
		if(TextUtils.isEmpty(message)) {
			L.e(TAG, "message is empty");
			return null;
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.layout_toast, null);
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(message);
		Toast toast = new Toast(context.getApplicationContext());
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		return toast;
	}

	public static Toast show(Toast oldToast, Toast toast) {
		//把之前的取消掉，避免一直显示着
		if (oldToast != null) {
			oldToast.cancel();
		}
		if(toast != null) {
			toast.show();
		}
		return toast;
	}
}
