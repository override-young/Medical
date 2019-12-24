package allen.frame.tools;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.icu.text.DateTimePatternGenerator.DAY;

public class DateUtils {

    /**
     * yyyy-MM-dd HH:mm:ss 转换成 个性时间
     *
     * @param date
     * @return
     */
    public static String getChDate(String date) {
        int y, m, d;
        String hh, mm;
        if (StringUtils.notEmpty(date)) {
            if (date.contains(" ")) {
                String[] das = date.split(" ");
                String[] da1s = das[0].split("-");
                String[] da2s = das[1].split(":");
                y = Integer.parseInt(da1s[0]);
                m = Integer.parseInt(da1s[1]);
                d = Integer.parseInt(da1s[2]);
                hh = da2s[0];
                mm = da2s[1];
                Time time = new Time();
                time.setToNow();
                if (y == time.year) {
                    return m + "月" + d + "日 " + hh + ":" + mm;
                } else {
                    return da1s[0] + "年" + m + "月" + d + "日 " + hh + ":" + mm;
                }
            }
        }
        return StringUtils.null2Empty(date);
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Date类型转化为String类型.
     *
     * @param date   the date
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String String类型日期时间
     */
    public static String getStringByFormat(Date date, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 获得本月第一天0点时间
     *
     * @return
     */
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH),
				0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static Date getFirstdayofThisMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取当前日期
     * @param format //yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);// HH:mm:ss
        //获取当前日期
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    /**
     * 获得两个日期间距多少天
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getDataDistance(Date beginDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(beginDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, fromCalendar.getMinimum(Calendar.HOUR_OF_DAY));
        fromCalendar.set(Calendar.MINUTE, fromCalendar.getMinimum(Calendar.MINUTE));
        fromCalendar.set(Calendar.SECOND, fromCalendar.getMinimum(Calendar.SECOND));
        fromCalendar.set(Calendar.MILLISECOND, fromCalendar.getMinimum(Calendar.MILLISECOND));

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, fromCalendar.getMinimum(Calendar.HOUR_OF_DAY));
        toCalendar.set(Calendar.MINUTE, fromCalendar.getMinimum(Calendar.MINUTE));
        toCalendar.set(Calendar.SECOND, fromCalendar.getMinimum(Calendar.SECOND));
        toCalendar.set(Calendar.MILLISECOND, fromCalendar.getMinimum(Calendar.MILLISECOND));

        long dayDistance = ((toCalendar.getTime().getTime() / 1000) - (fromCalendar.getTime()
				.getTime() / 1000)) / 3600 / 24;
//		dayDistance = Math.abs(dayDistance);//绝对值

        return dayDistance;
    }

    public static void doTimeDateSetting(final Context context, Handler handler) {
        // textView.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // doSetTimeDialog(context, textView);
        // doSetDateDialog(context, textView);
        // }
        // });

        doSetTimeDialog(context, handler);
        doSetDateDialog(context, handler);
    }

    public static void doTimeDateSetting(final Context context, final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSetTimeDialog(context, textView);
                doSetDateDialog(context, textView);
            }
        });

        // doSetTimeDialog(context, textView);
        // doSetDateDialog(context, textView);
    }

    /**
     * 设置其一个日期的
     *
     * @param context
     * @param handler
     */
    @SuppressLint("NewApi")
    public static void doSetDateDialog(Context context, final Handler handler) {
        final DatePicker mDatePicker = new DatePicker(context);
        AlertDialog.Builder timeDialog = new AlertDialog.Builder(context);
        timeDialog.setTitle("选择日期");
        timeDialog.setIcon(null);
        timeDialog.setCancelable(false);
        mDatePicker.setCalendarViewShown(false);
        timeDialog.setView(mDatePicker); // 只需将您时间控件的view引入
        timeDialog.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取出TimePicker设定的时间就行了
                int year = mDatePicker.getYear();
                int monthOfYear = mDatePicker.getMonth();
                int dayOfMonth = mDatePicker.getDayOfMonth();
                Message msg = new Message();
                if (monthOfYear < 9 && dayOfMonth < 10) {
                    msg.obj = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                    // textView.setText(year + "-0" + (monthOfYear + 1) + "-0" +
                    // dayOfMonth);
                } else if (dayOfMonth < 10) {
                    msg.obj = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                } else if (monthOfYear < 9) {
                    msg.obj = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                } else {
                    msg.obj = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                }
                msg.what = 100;
                handler.sendMessage(msg);
            }
        }).create();
        timeDialog.show();
    }

    // 显示其 时间 dialog
    public static void doSetTimeDialog(Context context, final Handler handler) {
        final TimePicker timePicker = new TimePicker(context);
        AlertDialog.Builder timeDialog = new AlertDialog.Builder(context);
        timeDialog.setTitle("选择时间");
        timeDialog.setIcon(null);
        timeDialog.setCancelable(false);
        timeDialog.setView(timePicker); // 只需将您时间控件的view引入
        timeDialog.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取出TimePicker设定的时间就行了
                int hhs = timePicker.getCurrentHour();
                int mms = timePicker.getCurrentMinute();
                Message msg = new Message();
                msg.obj = " " + hhs + ":" + mms + ":00";
                msg.what = 101;
                handler.sendMessage(msg);
                // textView.append(" " + hhs + ":" + mms + ":00");
            }
        }).create();
        timeDialog.show();

    }

    /**
     * 设置其一个日期的
     *
     * @param context
     * @param textView
     */
    @SuppressLint("NewApi")
    public static void doSetDateDialog(Context context, final TextView textView) {
        final DatePicker mDatePicker = new DatePicker(context);
        AlertDialog.Builder timeDialog = new AlertDialog.Builder(context);
        timeDialog.setTitle("选择日期");
        timeDialog.setIcon(null);
        timeDialog.setCancelable(false);
        mDatePicker.setCalendarViewShown(false);
        timeDialog.setView(mDatePicker); // 只需将您时间控件的view引入
        timeDialog.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取出TimePicker设定的时间就行了
                int year = mDatePicker.getYear();
                int monthOfYear = mDatePicker.getMonth();
                int dayOfMonth = mDatePicker.getDayOfMonth();
//				Message msg = new Message();
                if (monthOfYear < 9 && dayOfMonth < 10) {
//					msg.obj = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                    textView.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                } else if (dayOfMonth < 10) {
//					msg.obj = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                    textView.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                } else if (monthOfYear < 9) {
//					msg.obj = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                    textView.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                } else {
//					msg.obj = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                }
            }
        }).create();
        timeDialog.show();
    }


    // 显示其 时间 dialog
    public static void doSetTimeDialog(Context context, final TextView textView) {
        final TimePicker timePicker = new TimePicker(context);
        AlertDialog.Builder timeDialog = new AlertDialog.Builder(context);
        timeDialog.setTitle("选择时间");
        timeDialog.setIcon(null);
        timeDialog.setCancelable(false);
        timeDialog.setView(timePicker); // 只需将您时间控件的view引入
        timeDialog.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取出TimePicker设定的时间就行了
                int hhs = timePicker.getCurrentHour();
                int mms = timePicker.getCurrentMinute();
                // Message msg = new Message();
                // msg.obj = " " + hhs + ":" + mms + ":00";
                // msg.what = 101;
                textView.append(" " + hhs + ":" + mms + ":00");
            }
        }).create();
        timeDialog.show();

    }
}
