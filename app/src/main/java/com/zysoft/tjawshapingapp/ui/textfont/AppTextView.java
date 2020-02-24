package com.zysoft.tjawshapingapp.ui.textfont;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


import com.zysoft.tjawshapingapp.applaction.CustomApplaction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AppTextView extends AppCompatTextView {
    public static class TypefaceValue{
		public static final int Bold = 0;
		public static final int BoldItalic = 1;
		public static final int Heavy = 2;
	}

	@IntDef({TypefaceValue.Bold, TypefaceValue.BoldItalic, TypefaceValue.Heavy})
	@Retention(RetentionPolicy.SOURCE)
	public @interface TypeFace {
	}

	public AppTextView(Context context) {
		this(context,null);
		setTypeface(CustomApplaction.getTypeface());
	}

	public AppTextView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		setTypeface(CustomApplaction.getTypeface());

	}

	public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setTypeface(CustomApplaction.getTypeface());
	}


}