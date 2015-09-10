package com.moonwu.mobilemanager;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.moonwu.mobilemanager.utils.StreamTool;

import org.apache.http.HttpConnection;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SplashActivity extends Activity {

    private static final String TAG = "SplashActivity";
    private TextView tv_splash_version = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash_version = (TextView)findViewById(R.id.tv_splash_version);
        tv_splash_version.setText("版本号:" + getVersionName());

        //检查更新
        checkUpdate();
    }


    /**
     * 检查是否存在更新,如果有就更新
     */
    private void checkUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(getString(R.string.serverurl));
                    //连接网络
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (200 == code) {

                        //联网成功
                        InputStream is = conn.getInputStream();
                        String result = StreamTool.readInputStream(is);
                        Log.i(TAG, "联网成功" + result);

                        //json解析
                        JSONObject job = new JSONObject(result);
                        String version = (String) job.get("version");
                        String description = (String) job.get("description");
                        String apkurl = (String) job.get("apkurl");
                        Log.i(TAG, "解析成功: " + version + "|" + description +
                                "|" + apkurl);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
