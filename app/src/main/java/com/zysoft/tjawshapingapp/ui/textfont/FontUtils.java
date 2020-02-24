package com.zysoft.tjawshapingapp.ui.textfont;

import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.zysoft.tjawshapingapp.common.UIUtils;

import java.util.HashMap;
import java.util.Map;

public class FontUtils {

    private static final Map<String, Typeface> fontMap = new HashMap();
    private static Typeface getFontTypefaceFromAssert(String fontName) {
        try {
            synchronized (fontMap) {
                if (!fontMap.containsKey(fontName)) {
                    Typeface typface = Typeface.createFromAsset(UIUtils.getAssets(), fontName);
                    fontMap.put(fontName, typface);
                }
                Typeface typface = fontMap.get(fontName);
                return typface;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Typeface getFontTypefaceByName(String fontName) {
        return getFontTypefaceFromAssert(fontName);
    }

    public static Typeface getSFProBold() {
        return getFontTypefaceFromAssert("fonts/SF-Pro-Text-Bold.otf");
    }

    public static Typeface getSFProBoldItalic() {
        return getFontTypefaceFromAssert("fonts/SF-Pro-Text-BoldItalic.otf");
    }
    static public void changeFont(View element, Typeface tf){
        if (element instanceof TextView) {
            ((TextView) element).setTypeface(tf);
        }else if(element instanceof EditText){
            ((EditText)element).setTypeface(tf);
        }
    }
}