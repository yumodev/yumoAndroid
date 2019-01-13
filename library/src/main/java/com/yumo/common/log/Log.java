package com.yumo.common.log;

import android.text.TextUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by yumodev on 17/3/10.
 * 封装的日志类.
 */

public class Log {

    /**
     * 崩溃文件。
     */
    public static final String TAG_CRASH_LOG = "crash";

    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    /**
     * 是否打印日志
     */
    private static boolean mEnable = false;

    /**
     *
     */
    public static final String LIB_TAG = "ymlib";

    private static List<LogFileInfo> mListFiles = new ArrayList<>();
    /**
     * 设置是否打印日志。
     *
     * @param enable
     */
    public static void setEnable(boolean enable) {
        mEnable = enable;
    }

    /**
     * 获取是否打印日志
     *
     * @return
     */
    public static boolean isEnable() {
        return mEnable;
    }

    private Log() {
        mListFiles.add(new LogFileInfo(TAG_CRASH_LOG));
    }

    /**
     * Send a {@link #VERBOSE} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int v(String tag, String msg) {
        if (isEnable()) {
            return 0;
        }
        return println(LOG_ID_MAIN, VERBOSE, tag, msg, false);
    }

    /**
     * Send a {@link #VERBOSE} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static int v(String tag, String msg, Throwable tr) {
        if (isEnable()) {
            return 0;
        }
        return println(LOG_ID_MAIN, VERBOSE, tag, msg + '\n' + getStackTraceString(tr), false);
    }

    /**
     * Send a {@link #DEBUG} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int d(String tag, String msg) {
        if(isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, DEBUG, tag, msg, false);
    }

    /**
     * Send a {@link #DEBUG} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static int d(String tag, String msg, Throwable tr) {
        if(isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, DEBUG, tag, msg + '\n' + getStackTraceString(tr), false);
    }

    public static int d(String tag, String msg, boolean file){
        if (isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, DEBUG, tag, msg, file);
    }

    /**
     * Send an {@link #INFO} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int i(String tag, String msg) {
        if(isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, INFO, tag, msg, false);
    }

    /**
     * Send a {@link #INFO} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static int i(String tag, String msg, Throwable tr) {
        if(!isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, INFO, tag, msg + '\n' + getStackTraceString(tr), false);
    }

    public static int i(String tag, String msg, boolean file) {
        if(isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, INFO, tag, msg, file);
    }
    /**
     * Send a {@link #WARN} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int w(String tag, String msg) {
        if(!isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, WARN, tag, msg, false);
    }

    /**
     * Send a {@link #WARN} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static int w(String tag, String msg, Throwable tr) {
        if(!isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, WARN, tag, msg + '\n' + getStackTraceString(tr), false);
    }

    /*
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    public static int w(String tag, Throwable tr) {
        if(!isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, WARN, tag, getStackTraceString(tr), false);
    }

    /**
     * Send an {@link #ERROR} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int e(String tag, String msg) {
        if(!isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, ERROR, tag, msg, false);
    }

    public static int e(String tag, String msg, boolean file) {
        if(!isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, ERROR, tag, msg, file);
    }

    /**
     * Send a {@link #ERROR} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static int e(String tag, String msg, Throwable tr) {
        if(!isEnable()){
            return 0;
        }
        return println(LOG_ID_MAIN, ERROR, tag, msg + '\n' + getStackTraceString(tr), false);
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     *
     * @param tr An exception to log
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * Low-level logging call.
     *
     * @param priority The priority/type of this log message
     * @param tag      Used to identify the source of a log message.  It usually identifies
     *                 the class or activity where the log call occurs.
     * @param msg      The message you would like logged.
     * @return The number of bytes written.
     */
    public static int println(int priority, String tag, String msg) {
        return println(LOG_ID_MAIN, priority, tag, msg, false);
    }

    /**
     * @hide
     */
    public static final int LOG_ID_MAIN = 0;
    /**
     * @hide
     */
    public static final int LOG_ID_RADIO = 1;
    /**
     * @hide
     */
    public static final int LOG_ID_EVENTS = 2;
    /**
     * @hide
     */
    public static final int LOG_ID_SYSTEM = 3;
    /**
     * @hide
     */
    public static final int LOG_ID_CRASH = 4;

    public static int println(int bufID,
                              int priority, String tag, String msg, boolean file) {
        if (file){
            LogFileUtil.saveMessage(tag, msg, getFileInfo(tag));
        }

        switch (priority){
            case VERBOSE:{
                android.util.Log.v(tag, msg);
                break;
            }
            case DEBUG:{
                android.util.Log.d(tag, msg);
                break;
            }
            case INFO:{
                android.util.Log.i(tag, msg);
                break;
            }
            case WARN:{
                android.util.Log.w(tag, msg);
                break;
            }
            case ERROR:{
                android.util.Log.e(tag, msg);
                break;
            }
        }
        return 0;
    }

    /**
     * 添加日志文件
     * @param info
     */
    public static void addFileInfo(LogFileInfo info){
        mListFiles.add(info);
    }

    /**
     * 移除日志文件
     * @param info
     */
    public static void removeFileInfo(LogFileInfo info){
        mListFiles.remove(info);
    }

    public static LogFileInfo getFileInfo(String tag){
        LogFileInfo defaultInfo = null;
        for (LogFileInfo info : mListFiles){
            if (tag.equals(info.getTag())){
                return info;
            }

            if (TextUtils.isEmpty(tag)){
                defaultInfo = info;
            }
        }

        return defaultInfo;
    }

    /**
     * 通过tag删除文件
     * @param tag
     */
    public static void removeFileByTag(String tag){
        if (TextUtils.isEmpty(tag)){
            return;
        }

        ListIterator<LogFileInfo> iterator = mListFiles.listIterator();
        while (iterator.hasNext()){
            if (tag.equals(iterator.next().getTag())){
                iterator.remove();
            }
        }
    }

    public static void setLogRootDir(String dir){
        LogFileUtil.setLogDir(dir);
    }


}
