package com.tt.automationTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ViewUtils {
	public static void setDialogAttribute(Context ctx, Dialog dialog, int dip,
			int gravity, boolean isSetWidth) {
		Window window = dialog.getWindow();
		
		LayoutParams params = new LayoutParams();
		params.gravity = gravity;
		DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
		final float scale = dm.density;
		params.y = (int) (dip * scale);
		window.setAttributes(params);
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getSw(Context context) { 
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return  wm.getDefaultDisplay().getWidth();
	}

	public static int getSh(Context context) { 

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int sh =  wm.getDefaultDisplay().getHeight();
		return  sh;
	}

	public static boolean is1080pScreen(Context c) {
		String sw = String.valueOf(getSw(c));
		String sh = String.valueOf(getSh(c));
		if (sw.equals("1080")) {
			return true;
		}
		if (sh.equals("1920")) {
			return true;
		}
		return false;
	}

	public static boolean is480pScreen(Context c) {
		String sw = String.valueOf(getSw(c));
		String sh = String.valueOf(getSh(c));
		if (sw.equals("480")) {
			return true;
		}
		if (sh.equals("850") || sh.equals("800")) {
			return true;
		}
		return false;
	}

	public static void setToggleBtnBg(Context ctx, Button btn)
			throws IOException {


	}

	public static void setBtnSelectorBackground(boolean btnState,View btn, Drawable drawableNormal, Drawable drawablePressed) {
		StateListDrawable sld = new StateListDrawable();
		if(btnState){//true  press ,false check
			sld.addState(new int[] { android.R.attr.state_pressed}, drawablePressed);
		}else{
			sld.addState(new int[] { android.R.attr.state_checked}, drawablePressed);
		}
		sld.addState(new int[0], drawableNormal);
		btn.setBackgroundDrawable(sld);
	}


	public static void bindButtonNinePatchDrawable(Context context,
			View button, String nornalImageFilePath, String pressedImageFilePath) {
		StateListDrawable stateListDrawable = new StateListDrawable();
		Drawable normalDrawable = getNinePatchDrawable(context, button,
				nornalImageFilePath);
		Drawable pressedDrawable = getNinePatchDrawable(context, button,
				pressedImageFilePath);
		stateListDrawable.addState(new int[] { android.R.attr.state_active },
				normalDrawable);
		stateListDrawable.addState(new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled }, pressedDrawable);
		stateListDrawable.addState(new int[] { android.R.attr.state_enabled },
				normalDrawable);
		stateListDrawable.addState(new int[] { android.R.attr.state_focused,
				android.R.attr.state_enabled }, normalDrawable);
		button.setBackgroundDrawable(stateListDrawable);
	}

	public static void invokeResourceByPath(View v, String resName) {

	}

	public static void invokeResourceByPath(ImageView v, String resName) {

	}

	public static Drawable getDrawableFromPath(String resName) {
		return null;
	}

	public static InputStream getDrawableInputStream(String resName) {

		return null;
	}

	public static File readFileByPath(Context ctx, String fileName) {
		File f = new File(fileName);
		return f;
	}

	public static void invokeNinePatchByPath(Context c, View v, String resName) {
		return;
	}

	public static Drawable getNinePatchDrawable(Context c, View v,
			String resName) {
		return null;
	}

	public static Drawable getDrawable(Context c, View v, String path) {
		File f = new File(path);
		FileInputStream fi = null;
		Drawable d = null;
		try {
			fi = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(fi);
			d = new BitmapDrawable(bitmap);
			fi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	public static void setBtnEnabledFalse(Button btn) {
		btn.setEnabled(false);
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#BFBFBF"));
		btn.setBackgroundDrawable(colorDrawable);
	}

	public static void patchWindow(Context ctx, Dialog d) {
		Window window = d.getWindow();
		LayoutParams params = new LayoutParams();
		params.flags |= LayoutParams.FLAG_SECURE;
		DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
		final float scale = dm.density;
		params.y = (int) (100 * scale + 0.5f);
		window.setAttributes(params);
	}

	public static void setBtnByBitmap(Button btn, String pressBgResName,
			String normalBgResName) {
		Drawable drawablePressed = getDrawableFromPath(pressBgResName);
		Drawable drawableNormal = getDrawableFromPath(normalBgResName);
		setBtnSelectorBackground(true,btn, drawableNormal, drawablePressed);
	}

	public static void setBtnBgByColor(Button btn, String normalColor,
			String pressColor) throws IOException {
		ColorDrawable normalCd = new ColorDrawable(
				Color.parseColor(normalColor));
		ColorDrawable pressCd = new ColorDrawable(Color.parseColor(pressColor));
		setBtnSelectorBackground(true,btn, normalCd, pressCd);
	}

	public static NinePatchDrawable get9PatchDrawable(Context ctx, Bitmap bmp) {
		return new NinePatchDrawable(ctx.getResources(), bmp,
				bmp.getNinePatchChunk(), new Rect(), null);
	}

	public static void addSplitLine(Context ctx,LinearLayout LL) {

		LinearLayout.LayoutParams splitImaParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout	sliptLayout = new LinearLayout(ctx);
		sliptLayout.setBackgroundColor(Color.parseColor("#A9A9A9"));
		splitImaParam.height = ViewUtils.dip2px(ctx, 1.0f);
		sliptLayout.setLayoutParams(splitImaParam);
		LL.addView(sliptLayout);
	}
	public static void setTitleMidlleTextSize(Context ctx,TextView tv) {
		tv.setTextColor(Color.parseColor("#FFFFFFFF"));
		tv.setTypeface(Typeface.DEFAULT_BOLD); 
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ViewUtils.getSw(ctx) * 20 / 320);
	}

	public static void setViewShape(View v){
		final float RADIUS = 10; 
		GradientDrawable gd = new GradientDrawable( ) ; 
		//    	gd.setStroke(1,stroke);
		gd.setCornerRadius (RADIUS) ; 
		gd.setColor (Color.WHITE) ; 
		v.setBackgroundDrawable(gd) ;  
	}



	public  static void setViewBgColor(View v){
		v.setBackgroundColor(Color.rgb(235, 235, 235));
	}

}
