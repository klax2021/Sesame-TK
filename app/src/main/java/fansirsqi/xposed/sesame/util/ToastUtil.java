package fansirsqi.xposed.sesame.util;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import fansirsqi.xposed.sesame.R;
import fansirsqi.xposed.sesame.model.BaseModel;
public class ToastUtil {
    private static Context appContext;
    /**
     * 初始化全局 Context。建议在 Application 类中调用。
     *
     * @param context 应用上下文
     */
    public static void init(Context context) {
        if (context != null) {
            appContext = context.getApplicationContext();
        }
    }
    /**
     * 获取当前环境的 Context
     *
     * @return Context
     */
    private static Context getContext() {
        if (appContext == null) {
            throw new IllegalStateException("ToastUtil is not initialized. Call ToastUtil.init(context) in Application.");
        }
        return appContext;
    }
    /**
     * 显示自定义 Toast
     *
     * @param message 显示的消息
     */
    public static void showToast(String message) {
        showToast(getContext(), message);
    }
    /**
     * 显示自定义 Toast
     *
     * @param context 上下文
     * @param message 显示的消息
     */
    public static void showToast(Context context, String message) {
        Log.runtime("try showToast: " + message);
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View layout = inflater.inflate(R.layout.toast, null);
        // 设置自定义文字内容
        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(message);
        toast.setView(layout);
        toast.setGravity(toast.getGravity(), toast.getXOffset(), BaseModel.getToastOffsetY().getValue());
        toast.show();
    }
    /**
     * 创建自定义 Toast
     *
     * @param message  显示的消息
     * @param duration 显示时长
     * @return Toast 对象
     */
    public static Toast makeText(String message, int duration) {
        return makeText(getContext(), message, duration);
    }
    /**
     * 创建自定义 Toast
     *
     * @param context  上下文
     * @param message  显示的消息
     * @param duration 显示时长
     * @return Toast 对象
     */
    public static Toast makeText(Context context, String message, int duration) {
        try {
            Log.runtime("makeText Context: " + context.getClass().getSimpleName());
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams")
            View layout = inflater.inflate(R.layout.toast, null);
            TextView toastText = layout.findViewById(R.id.toast_text);
            toastText.setText(message);
            Toast toast = new Toast(context);
            toast.setView(layout);
            toast.setDuration(duration);
            toast.setGravity(toast.getGravity(), toast.getXOffset(), BaseModel.getToastOffsetY().getValue());
            return toast;
        } catch (Exception e) {
            Log.printStackTrace(e);
            // 回退到原生 Toast
            return Toast.makeText(context, message, duration);
        }
    }
    public static void showToastWithDelay(Context context, String message, int delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            makeText(context, message, Toast.LENGTH_SHORT).show();
        }, delayMillis);
    }
    public static void showToastWithDelay(String message, int delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            makeText(message, Toast.LENGTH_SHORT).show();
        }, delayMillis);
    }
}
