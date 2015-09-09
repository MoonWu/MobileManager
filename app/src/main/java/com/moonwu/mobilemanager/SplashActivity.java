package com.moonwu.mobilemanager;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends Activity {

    private TextView tv_splash_version = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash_version = (TextView)findViewById(R.id.tv_splash_version);
        tv_splash_version.setText("版本号:" + getVersionName());
    }

    /**
     * 获取应用程序版本号
     */
    private String getVersionName(){
        //管理手机APK
        PackageManager pm = getPackageManager();
        try {
            //得到指定APK的功能清单文件
            PackageInfo info = pm.getPackageInfo(getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
