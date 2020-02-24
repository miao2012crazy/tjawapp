package com.zysoft.tjawshapingapp.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;


import com.zysoft.tjawshapingapp.applaction.CustomApplaction;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UIUtils {

    private Toast toast;

    /**
     * @return 返回上下文环境
     */
    public static Context getContext() {
        return CustomApplaction.getContext();
    }


    /**
     * @return 获取res文件夹
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * @param colorId 根据id获取色值
     * @return
     */
    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    /**
     * @param drawableId 根据id
     * @return 获取图片
     */
    public static Drawable getDrawable(int drawableId) {
        return getResources().getDrawable(drawableId);
    }

    /**
     * @param layoutId
     * @return
     */
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    //获取string中的字符串
    public static String getString(int stringId) {
        return getResources().getString(stringId);
    }

    /**
     * 验证输入的身份证号是否合法
     */
    public static boolean isLegalId(String id) {
        return id.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)");
    }

    /**
     * 手机号号段校验，
     * 第1位：1；
     * 第2位：{3、4、5、6、7、8}任意数字；
     * 第3—11位：0—9任意数字
     *
     * @param value
     * @return
     */
    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }

    /**
     * @param msg 吐司 (短) 显示toast 提示信息
     */
    public static void showToast(String msg) {
        Toast.makeText(UIUtils.getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */

    public static int lastButtonId = -1;
    public static long lastClickTime = 0;

    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }


    /**
     * 从APK中读取签名
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static List<String> getSignaturesFromApk(File file) throws IOException {
        List<String> signatures = new ArrayList<String>();
        JarFile jarFile = new JarFile(file);
        try {
            JarEntry je = jarFile.getJarEntry("AndroidManifest.xml");
            byte[] readBuffer = new byte[8192];
            Certificate[] certs = loadCertificates(jarFile, je, readBuffer);
            if (certs != null) {
                for (Certificate c : certs) {
                    String sig = toCharsString(c.getEncoded());
                    signatures.add(sig);
                }
            }
        } catch (Exception ex) {
        }
        return signatures;
    }

    /**
     * 加载签名
     *
     * @param jarFile
     * @param je
     * @param readBuffer
     * @return
     */
    private static Certificate[] loadCertificates(JarFile jarFile, JarEntry je, byte[] readBuffer) {
        try {
            InputStream is = jarFile.getInputStream(je);
            while (is.read(readBuffer, 0, readBuffer.length) != -1) {
            }
            is.close();
            return je != null ? je.getCertificates() : null;
        } catch (IOException e) {
        }
        return null;
    }


    /**
     * 将签名转成转成可见字符串
     *
     * @param sigBytes
     * @return
     */
    private static String toCharsString(byte[] sigBytes) {
        byte[] sig = sigBytes;
        final int N = sig.length;
        final int N2 = N * 2;
        char[] text = new char[N2];
        for (int j = 0; j < N; j++) {
            byte v = sig[j];
            int d = (v >> 4) & 0xf;
            text[j * 2] = (char) (d >= 10 ? ('a' + d - 10) : ('0' + d));
            d = v & 0xf;
            text[j * 2 + 1] = (char) (d >= 10 ? ('a' + d - 10) : ('0' + d));
        }
        return new String(text);
    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }


    /**
     * 将Bitmap转换成文件
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return path + fileName;
    }

    /**
     * getTime方法返回的就是10位的时间戳
     *
     * @return
     */
    public static long getTime() {
        long time = (System.currentTimeMillis() - 8 * 60 * 60 * 1000) / 1000;//获取系统时间的10位的时间戳
        return time;
    }

    /**
     * 获取addday天后是周几
     * 当前时间+多少天
     *
     * @param addDay 天数
     * @return
     */
    public static String getTimeForDay(int addDay) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, addDay);// 今天+1天
        Date tomorrow = c.getTime();
//        try {
//            String s = dateToStamp(String.valueOf(sf.format(tomorrow)));
        return getWeek(tomorrow);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return "";
//        }
    }


    /**
     * 当前时间+多少分钟 之后的时间戳
     * 根据时间戳返回 hh：mm
     *
     * @param addMinuate
     * @return
     */
    public static long getTimeSamp(long timestamp, int addMinuate) {
        long l = timestamp * 1000;
        long l1 = l + addMinuate * 60 * 1000;
        Date today = new Date(l1);
        long time = today.getTime();

        return time / 1000;
    }


    /**
     * 根据时间戳返回 HH：MM
     * 当前时间+多少分钟
     * 根据时间戳返回 hh：mm
     *
     * @param addMinuate
     * @return
     */
    public static String getTimeForHM(long current_stamp, int addMinuate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long l = current_stamp + addMinuate * 60;
        l += 8 * 60 * 60;
        String format = sdf.format(Long.valueOf(l + "000"));
        String[] split = format.split("-");
        return split[3] + ":" + split[4];
    }


    /**
     * 获取当前时间的年月日
     * 返回 Y-M-D
     *
     * @param addDay 增加addday天之后的 时间
     * @param hm     几点几分
     * @return
     */
    public static long getTimeForYMDSamp(int addDay, String hm) {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, addDay);// 当前时间+20分钟
        String[] split = hm.split("-");
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(split[1]));
        long time = c.getTime().getTime() - 8 * 60 * 60 * 1000;
        return time / 1000;
    }


    /**
     * 获取 addDay天后是几号
     *
     * @param addDay
     * @return
     */
    public static String getTimeForMD(int addDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, addDay);// 当前时间+addday天
        String format = sdf.format(c.getTime());
        return format;
    }


    /**
     * 获取时间差
     * 根据时间戳返回 hh：mm
     *
     * @param addMinuate
     * @return
     */
    public static int getTimeMDisc(int addMinuate) {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MINUTE, addMinuate);// 当前时间+20分钟
        int NN = c.get(Calendar.MINUTE);
        if (NN < 15) {
            return 15 - NN;
        } else if (NN < 30) {
            return 30 - 15;
        } else if (NN < 45) {
            return 45 - NN;
        } else if (NN < 60) {
            return 60 - NN;
        } else {
            return 0;
        }
    }


    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     *
     * @return
     */
    private static String getWeek(Date date) {
        String Week = "";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int wek = c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "周日";
        }
        if (wek == 2) {
            Week += "周一";
        }
        if (wek == 3) {
            Week += "周二";
        }
        if (wek == 4) {
            Week += "周三";
        }
        if (wek == 5) {
            Week += "周四";
        }
        if (wek == 6) {
            Week += "周五";
        }
        if (wek == 7) {
            Week += "周六";
        }
        return Week;
    }


    public static String getSha1() {
        try {
            PackageInfo info = UIUtils.getContext().getPackageManager().getPackageInfo(
                    UIUtils.getContext().getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result=hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    public static String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/appfire/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }



    /**
     * 获取当前本地apk的版本
     *
     * @return
     */
    public static int getVerCode() {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = getContext().getPackageManager().
                    getPackageInfo(getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @return
     */
    public static String getVerName() {
        String verName = "";
        try {
            verName = getContext().getPackageManager().
                    getPackageInfo(getContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }


    /**
     * @return 获取res文件夹
     */
    public static AssetManager getAssets() {
        return getContext().getAssets();
    }
}
