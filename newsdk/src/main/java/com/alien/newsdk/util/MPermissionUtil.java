package com.alien.newsdk.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laughing on 2017/5/31.
 *
 */

public class MPermissionUtil {
    private static int mRequestCode = -1;

    private static SparseArray<String> codeReasonSparseArray = new SparseArray<>();

    public static void requestPermissionsResult(Activity activity, int requestCode
            , String[] permission, OnPermissionListener callback) {
        if (activity == null)
            return;
        requestPermissions(activity, null, requestCode, permission, callback);
    }

    public static void requestPermissionsResult(Activity activity, String requestReason, int requestCode
            , String[] permission, OnPermissionListener callback) {
        if (activity == null)
            return;
        requestPermissions(activity, requestReason, requestCode, permission, callback);
    }


    /**
     * 请求权限处理
     *
     * @param object      activity or fragment
     *                    !!!该Activity必须继承KHBaseActivity或者在OnActivityResult中调用
     * @param requestCode 请求码
     * @param permissions 需要请求的权限
     * @param callback    结果回调
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void requestPermissions(final Activity object, final String reason, final int requestCode
            , String[] permissions, OnPermissionListener callback) {

        codeReasonSparseArray.put(requestCode, reason);
        mOnPermissionListener = callback;

        if (checkPermissions(object, permissions)) {
            if (mOnPermissionListener != null)
                mOnPermissionListener.onPermissionGranted();
        } else {
            final List<String> deniedPermissions = getDeniedPermissions(object, permissions);
            if (deniedPermissions.size() > 0) {
                realRequest(object, requestCode, deniedPermissions);
                mRequestCode = requestCode;

            }
        }
    }


    private static void realRequest(Activity activity, int requestCode, List<String> deniedPermissions) {
        ActivityCompat.requestPermissions(activity, deniedPermissions.toArray(new String[deniedPermissions.size()]),
                requestCode);
    }

    /**
     * 请求权限结果，对应onRequestPermissionsResult()方法。
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Activity activity) {
        if (mRequestCode != -1 && requestCode == mRequestCode && mOnPermissionListener != null) {
            if (verifyPermissions(grantResults)) {
                mOnPermissionListener.onPermissionGrantedFirstTime();
            } else {
                if (!shouldShowRequestPermissionRationale(activity, permissions)) {
                    mOnPermissionListener.onPermissionDenied();
//                    if (mOnPermissionListener.showDeniedDefaultDialog()
//                            && !TextUtils.isEmpty(codeReasonSparseArray.get(requestCode))) {
//                        MSingleDialogHelper.createPermissionBuild(activity)
//                                .setMessage(codeReasonSparseArray.get(requestCode))
//                                .show();
//                    }
                } else mOnPermissionListener.onPermissionCancel();
            }
        }
        mOnPermissionListener = null;
        mRequestCode = -1;
    }

    /**
     * 启动当前应用设置页面
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 验证权限是否都已经授权
     */
    private static boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    private static boolean shouldShowRequestPermissionRationale(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                return true;
        }
        return false;
    }

    /**
     * 获取权限列表中所有需要授权的权限
     *
     * @param context     上下文
     * @param permissions 权限列表
     * @return
     */
    private static List<String> getDeniedPermissions(Context context, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }


    /**
     * 检查所有的权限是否已经被授权
     *
     * @param permissions 权限列表
     * @return true代表已获得
     */
    public static boolean checkPermissions(Context context, String... permissions) {
        if (isOverMarshmallow()) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断当前手机API版本是否 >= 6.0
     */
    private static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public interface OnPermissionListener {

        /**
         * 第一次获取到权限，代表通过系统弹框获取到的。可以再此做些额外的初始化操作（即不想每次都进行的操作）
         */
        void onPermissionGrantedFirstTime();

        /**
         * 每次获取到权限都会调用
         */
        void onPermissionGranted();

        /**
         * 申请权限被拒绝，原生系统拒绝后再次request还会弹出系统权限请求框，但是拒绝一次后这个框会
         * 多一个checkBox示意用户是否不再请求，如check再拒绝，会调用此方法。
         * 国内MIUI系统没有这个checkBox，每次拒绝都会调用此方法。
         * 当此方法被调用即意味着你再也无法调起系统权限申请框了
         */
        void onPermissionDenied();

        /**
         * 如果用户拒绝了，但下次还能调用系统权限申请框，会回调此方法。
         */
        void onPermissionCancel();

        /**
         * 是否展示默认的拒绝后信息提示框，如果要使用，请在
         * {@link MPermissionUtil#requestPermissions(Activity, String, int, String[], OnPermissionListener)}
         * 此方法中传入reason
         *
         * @return
         */
        boolean showDeniedDefaultDialog();

    }

    private static OnPermissionListener mOnPermissionListener;
}
