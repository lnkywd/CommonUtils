package common.utils.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import common.utils.R;


public class ShowUtil {
	public static Toast mToast;
	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mOnlyToast.cancel();
		}
	};

	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static Toast showToast1(Context context, String text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
		return toast;
	}

	public static void showOnlyToast(Context context, String text){
		if(mOnlyToast == null) {
			mOnlyToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			mHandler.postDelayed(r, 3000);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	private static Toast mOnlyToast;

	public static void showToast(Context context, int id) {
		Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
	}

	public static boolean isNull(String s){
		if(s == null || s.length() < 1){
			return true;
		}else{
			return false;
		}
	}

	public static  void ShowUtil(Context context, CharSequence text, int duration) {
		View v = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
		TextView textView = (TextView) v.findViewById(R.id.textView1);
		textView.setText(text);
		mToast = new Toast(context);
		mToast.setDuration(duration);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.setView(v);
		mToast.show();
	}

	public static  void ShowUtil3(Context context, CharSequence text) {
		View v = LayoutInflater.from(context).inflate(R.layout.layout_step_toast, null);
		TextView textView = (TextView) v.findViewById(R.id.textView1);
		textView.setText(text);
		mToast = new Toast(context);
		mToast.setDuration(Toast.LENGTH_LONG);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.setView(v);
		mToast.show();
	}
}
