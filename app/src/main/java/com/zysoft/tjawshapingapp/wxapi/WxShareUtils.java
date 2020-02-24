package com.zysoft.tjawshapingapp.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;


import java.io.ByteArrayOutputStream;

public class WxShareUtils {


    /**
     * 分享网页类型至微信
     *
     * @param context 上下文
     * @param title   网页标题
     * @param content 网页描述
     * @param bitmap  位图
     */
    public static void shareWeb(Context context, String title, String content, Bitmap bitmap,String projectId) {
        try {
// 通过appId得到IWXAPI这个对象
            IWXAPI wxapi = WXAPIFactory.createWXAPI(context, WXIDConstants.APP_ID);
            // 检查手机或者模拟器是否安装了微信
            if (!wxapi.isWXAppInstalled()) {
                UIUtils.showToast("您还没有安装微信");

                return;
            }

            // 初始化一个WXWebpageObject对象
            WXWebpageObject webpageObject = new WXWebpageObject();
            webpageObject.webpageUrl = "https://beauty521.com/web/#/homeDetails?id="+projectId;
            WXMediaMessage msg = new WXMediaMessage(webpageObject);
            msg.title = title;
            msg.description = content;
            if (bitmap!=null){
                msg.thumbData = bmpToByteArray1(bitmap,false);
            }
            // 构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            // transaction用于唯一标识一个请求（可自定义）
            req.transaction = "webpage";
            // 上文的WXMediaMessage对象
            req.message = msg;
            // SendMessageToWX.Req.WXSceneSession是分享到好友会话
//            req.scene = SendMessageToWX.Req.WXSceneTimeline; //是分享到朋友圈
            req.scene = SendMessageToWX.Req.WXSceneSession;
            // 向微信发送请求
            wxapi.sendReq(req);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * 分享网页类型至微信
     *
     * @param context 上下文
     * @param title   网页标题
     * @param content 网页描述
     */
    public static void shareContent(Context context, String title, String content,String msgId) {
        try {
// 通过appId得到IWXAPI这个对象
            IWXAPI wxapi = WXAPIFactory.createWXAPI(context, WXIDConstants.APP_ID);
            // 检查手机或者模拟器是否安装了微信
            if (!wxapi.isWXAppInstalled()) {
                UIUtils.showToast("您还没有安装微信");
                return;
            }

            // 初始化一个WXWebpageObject对象
            WXWebpageObject webpageObject = new WXWebpageObject();
//            webpageObject.webpageUrl = "http://91bianminfuwu.com/bind/#/xiangqing?userid=" + AppConstant.LOGIN_BEAN.getId()+"&msgId="+msgId;

            WXMediaMessage msg = new WXMediaMessage(webpageObject);
            msg.title = title;
            msg.description = content;
            // 构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            // transaction用于唯一标识一个请求（可自定义）
            req.transaction = "webpage";
            // 上文的WXMediaMessage对象
            req.message = msg;
            // SendMessageToWX.Req.WXSceneSession是分享到好友会话
//            req.scene = SendMessageToWX.Req.WXSceneTimeline; //是分享到朋友圈
            req.scene = SendMessageToWX.Req.WXSceneSession;
            // 向微信发送请求
            wxapi.sendReq(req);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * 分享网页类型至微信
     */
    public static void shareImage(Context context, Bitmap bitmap) {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(context, WXIDConstants.APP_ID);
        // 检查手机或者模拟器是否安装了微信
        if (!wxapi.isWXAppInstalled()) {
            UIUtils.showToast("您还没有安装微信");
            return;
        }
//        第二步：创建WXImageObject，并包装bitmap
        WXImageObject imgObj = new WXImageObject(bitmap);
//        第三步：创建WXMediaMessage对象，并包装WXimageObjext对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
//        第四步：压缩图片
        Bitmap thumBitmap = bitmap.createScaledBitmap(bitmap, 120, 150, true);
//        释放图片占用的内存资源
        bitmap.recycle();
        msg.thumbData = bmpToByteArray(thumBitmap, 32);//压缩图
//        第五步：创建SendMessageTo.Req对象，发送数据
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // 上文的WXMediaMessage对象
        req.message = msg;
//         SendMessageToWX.Req.WXSceneSession 是分享到好友会话
//            req.scene = SendMessageToWX.Req.WXSceneTimeline; //是分享到朋友圈
        req.scene = SendMessageToWX.Req.WXSceneSession;
        // 向微信发送请求
        wxapi.sendReq(req);
//        唯一标识
        req.transaction = "123";
//        发送的内容或者对象
        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        wxapi.sendReq(req);
    }


    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @param maxKb  32
     * @return
     */
    public static byte[] bmpToByteArray(Bitmap bitmap, int maxKb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, maxKb, output);
        int options = 100;
        while (output.toByteArray().length > maxKb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    public static byte[] bmpToByteArray1(final Bitmap bmp, final boolean needRecycle) {

        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0,i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 10,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                //F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }
}
