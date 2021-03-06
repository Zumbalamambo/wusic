package com.wangsz.wusic.application;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.ConsolePrinter;
import com.elvishew.xlog.printer.Printer;
import com.wangsz.wusic.BuildConfig;
import com.wangsz.wusic.manager.MusicServiceManager;
import com.wangsz.wusic.utils.PropertiesUtil;


/**
 * author: wangsz
 * date: On 2018/6/5 0005
 */
public class App extends Application {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;

        initLog();

        if (!MusicServiceManager.isMusicServiceProcess(sApplication)) {
            MusicServiceManager.init(sApplication);
            // 在assets文件夹中创建appkey.properties文件
            // LeanCloud初始化,参数依次为 this, AppId, AppKey
            AVOSCloud.initialize(this, PropertiesUtil.load("leancloud_appid"), PropertiesUtil.load("leancloud_appkey"));
            AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG);
        }


    }

    private void initLog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL             // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
                        : LogLevel.NONE)
                .tag("wusic")                                         // 指定 TAG，默认为 "X-LOG"
//                .t()                                                   // 允许打印线程信息，默认禁止
//                .st(2)                                                 // 允许打印深度为2的调用栈信息，默认禁止
//                .b()                                                   // 允许打印日志边框，默认禁止
                .build();

        XLog.init(config);
    }

    public static Application getInstance() {
        return sApplication;
    }

}
