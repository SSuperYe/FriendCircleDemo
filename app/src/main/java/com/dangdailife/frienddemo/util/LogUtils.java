package com.dangdailife.frienddemo.util;

import android.util.Log;

import com.dangdailife.frienddemo.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

/**
 * Created by szXue on 2017/8/21 0021.
 * E-mail:896944961@qq.com
 */

public class LogUtils {

    private LogUtils() {
        throw new UnsupportedOperationException("cannot be init");
    }

    /**
     * 是否表示Debug模式
     */
    private static boolean isDebug = BuildConfig.DEBUG;
    /**
     * JSON格式化缩进长度
     */
    private static final int JSON_FORMAT = 4;

    /**
     * 绘制表格数据
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;
    private static String LINE_SEPARATOR = System.getProperty("line.separator"); //等价于"\n\r"
    private static String TAB = "    ";
    private static final String TAG_NAME = "MY_LOGGER";
    private static final char V = 'V', D = 'D', I = 'I', W = 'W', E = 'E', A = 'A';


    /**
     * 默认TAG的日志打印
     */
    public static void v(String... msg) {
        if (isDebug) {
            printLog(V, null, msg);
        }
    }

    public static void d(String... msg) {
        if (isDebug) {
            printLog(D, null, msg);
        }
    }

    public static void i(String... msg) {
        if (isDebug) {
            printLog(I, null, msg);
        }
    }

    public static void w(String... msg) {
        if (isDebug) {
            printLog(W, null, msg);
        }
    }

    public static void e(String... msg) {
        if (isDebug) {
            printLog(E, null, msg);
        }
    }

    public static void a(String... msg) {
        if (isDebug) {
            printLog(A, null, msg);
        }
    }

    /**
     * 默认的带Throwable的日志打印
     */
    public static void v(Throwable tr, String... msg) {
        if (isDebug) {
            printLog(V, tr, msg);
        }
    }

    public static void d(Throwable tr, String... msg) {
        if (isDebug) {
            printLog(D, tr, msg);
        }
    }

    public static void i(Throwable tr, String... msg) {
        if (isDebug) {
            printLog(I, tr, msg);
        }
    }

    public static void w(Throwable tr, String... msg) {
        if (isDebug) {
            printLog(W, tr, msg);
        }
    }

    public static void e(Throwable tr, String... msg) {
        if (isDebug) {
            printLog(E, tr, msg);
        }
    }

    public static void a(Throwable tr, String... msg) {
        if (isDebug) {
            printLog(A, tr, msg);
        }
    }


    /**
     * 输入TAG的日志打印
     */
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void a(String tag, String msg) {
        if (isDebug)
            Log.wtf(tag, msg);
    }

    /**
     * JSON日志打印，级别E
     *
     * @param jsonStr 待格式化的Json数据
     */
    public static void j(String jsonStr) {
        if (isDebug) {
            StringBuilder message = new StringBuilder();
            try {
                if (jsonStr.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    message.append(jsonObject.toString(JSON_FORMAT));
                } else if (jsonStr.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    message.append(jsonArray.toString(JSON_FORMAT));
                } else {
                    message.append(jsonStr);
                }
                printLog(E, null, message.toString().split(LINE_SEPARATOR));
            } catch (JSONException e) {
                message.append(e.getMessage());
                printLog(E, e, message.toString().split(LINE_SEPARATOR));
            }
        }
    }

    /**
     * MAP日志打印，级别E
     *
     * @param map 带格式化的MAP
     * @param <K> key
     * @param <V> value
     */
    public static <K, V> void m(Map<K, V> map) {
        Set<Map.Entry<K, V>> entrySet = map.entrySet();
        if (entrySet.size() < 1) {
            return;
        }
        int i = 0;
        String[] s = new String[entrySet.size()];
        for (Map.Entry<K, V> entry : entrySet) {
            K key = entry.getKey();
            V value = entry.getValue();
            StringBuilder sb = new StringBuilder();
            sb.append("key--->").append(TAB).append(key).append("=").append(value).append(TAB)
                    .append("<---value").append("\n");
            s[i] = sb.toString();
            i++;
        }
        printLog(E, null, s);
    }

    /**
     * 定位代码
     *
     * @param msg
     */
    private static void codeLocation(char type, String... msg) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        int i = 0;
        for (StackTraceElement stack : stacks) {
            String name = stack.getClassName();
            if (!name.equals(LogUtils.class.getName())) {
                i++;
            } else {
                break;
            }
        }
        i += 3;
        String className = stacks[i].getFileName();
        String methodName = stacks[i].getMethodName();
        int lineNumber = stacks[i].getLineNumber();
        StringBuilder sb = new StringBuilder();
        sb.append(HORIZONTAL_DOUBLE_LINE).append(TAB).append("LOCATION:").append(TAB).append(className).append(".")
                .append(methodName).append(TAB).append("(").append(className).append(":").append(lineNumber).append(")");
        acceptPrint(type, sb.toString());
        acceptPrint(type, msg == null || msg.length == 0 ? BOTTOM_BORDER : MIDDLE_BORDER);
    }

    /**
     * 打印头部信息(运行的线程)
     *
     * @param type
     */
    private static void printHeader(char type) {
        acceptPrint(type, TOP_BORDER);
        acceptPrint(type, HORIZONTAL_DOUBLE_LINE + TAB + "THREAD:" + TAB + Thread.currentThread().getName());
        acceptPrint(type, MIDDLE_BORDER);
    }

    /**
     * 打印日志信息
     *
     * @param type
     * @param tr
     * @param msg
     */
    private static void printLog(char type, Throwable tr, String... msg) {
        printHeader(type);
        codeLocation(type, msg);
        if (msg == null || msg.length == 0) {
            return;
        }
        printMsg(type, msg);
        if (tr != null) {
            StringWriter stringWriters = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriters);
            tr.printStackTrace(printWriter);
            printError(stringWriters.toString().split(LINE_SEPARATOR));
        }
    }

    /**
     * 打印错误的信息
     *
     * @param tr
     */
    private static void printError(String... tr) {
        acceptPrint(E, HORIZONTAL_DOUBLE_LINE + TAB + "ERROR:");
        for (String str : tr) {
            acceptPrint(E, HORIZONTAL_DOUBLE_LINE + TAB + str);
        }
        acceptPrint(E, BOTTOM_BORDER);
    }

    /**
     * 打印具体数组的信息
     *
     * @param type
     * @param msg
     */
    private static void printMsg(char type, String... msg) {
        acceptPrint(type, HORIZONTAL_DOUBLE_LINE + TAB + "MSG:");
        for (String str : msg) {
            acceptPrint(type, HORIZONTAL_DOUBLE_LINE + TAB + str);
        }
        acceptPrint(type, BOTTOM_BORDER);
    }

    /**
     * 接受打印信息
     *
     * @param type 打印方式
     * @param str  打印内容
     */
    private static void acceptPrint(char type, String str) {
        switch (type) {
            case V:
                Log.v(TAG_NAME, str);
                break;
            case D:
                Log.d(TAG_NAME, str);
                break;
            case I:
                Log.i(TAG_NAME, str);
                break;
            case W:
                Log.w(TAG_NAME, str);
                break;
            case E:
                Log.e(TAG_NAME, str);
                break;
            case A:
                Log.wtf(TAG_NAME, str);
                break;
            default:
                break;
        }
    }
}
