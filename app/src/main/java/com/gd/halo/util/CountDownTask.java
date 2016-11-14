package com.gd.halo.util;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zhouxin on 2016/10/26.
 * Description:
 */
public class CountDownTask {
    private Map<Long, IntervalTask> taskMap;

    private CountDownTask(){
        taskMap = new HashMap<>();
    }

    public static CountDownTask create(){
        return new CountDownTask();
    }

    private IntervalTask get(long countDownInterval) {
        return taskMap.get(countDownInterval);
    }

    public IntervalTask getIntervalTask(long countDownInterval) {
        IntervalTask task = get(countDownInterval);
        if(task == null) {
            //创建定时任务
            task = new IntervalTask(countDownInterval);
            taskMap.put(countDownInterval, task);
        }
        return task;
    }

    /**
     * 取消某间隔的计时
     * @param countDownInterval 计时间隔
     */
    public void cancel(long countDownInterval) {
        IntervalTask task = get(countDownInterval);
        if(task != null) {
            task.remove();
            taskMap.remove(countDownInterval);
        }
    }

    /**
     * 取消计时
     */
    public void cancel() {
        for(Map.Entry<Long, IntervalTask> entry : taskMap.entrySet()) {
            IntervalTask intervalTask = entry.getValue();
            intervalTask.remove();
        }
        taskMap.clear();
    }

    public static class IntervalTask {

        private final CountDownTimer mCountDownTimer;
        private final long countDownInterval;
        private final List<Task> mList;

        public IntervalTask(long interval){
            this.countDownInterval = interval;
            mList = new CopyOnWriteArrayList<>();
            mCountDownTimer = new CountDownTimer(Long.MAX_VALUE, countDownInterval) {
                public void onTick(long millisUntilFinished) {
                    L.d("IntervalTask:" + countDownInterval  + " seconds remaining:" + millisUntilFinished / 1000);
                    for(Task task : mList) {
                        if(task.tick()) {
                            mList.remove(task);
                        }
                    }
                    if(mList.isEmpty()) {
                        mCountDownTimer.cancel();
                    }
                }

                public void onFinish() {
                    L.d("IntervalTask:" + countDownInterval  + "done!");
                }
            };
            mCountDownTimer.start();
        }

        /**
         * 加入到倒计时
         * @param textView   计时的文本
         * @param countDownTime 计时时间
         */
        public void add(TextView textView, long countDownTime) {
            add(textView, countDownTime, null);
        }

        /**
         * 加入到倒计时
         * @param textView   计时的文本
         * @param countDownTime 计时时间
         * @param listener 计时回调
         */
        public void add(TextView textView, long countDownTime, OnCountDownListener listener) {
            Task task = new Task(textView, countDownTime, countDownInterval, listener);
            mList.add(task);
            L.e("add:" + mList + " textView:" + textView);
        }

        /**
         * 从倒计时移除
         * @param textView
         */
        public void remove(TextView textView) {
            Task task = get(textView);
            if(task != null) {
                mList.remove(task);
            }
            L.e("remove:" + mList + " textView:" + textView);
        }

        private Task get(TextView tv_time) {
            for (Task task : mList) {
                if(task.textView.equals(tv_time)) {
                    return task;
                }
            }
            return null;
        }

        public void remove() {
            mCountDownTimer.cancel();
            mList.clear();
        }
    }

    public static class Task {
        private final TextView textView;
        //倒计时总时间
        private final long countDownTime;
        //倒计时时间间隔
        private final long countDownInterval;
        //倒计时剩余时间
        private long time;
        //倒计时触发回调
        private final OnCountDownListener listener;

        public Task(TextView tv_time, long countDownTime, long countDownInterval) {
            this(tv_time, countDownTime, countDownInterval, null);
        }

        public Task(TextView tv_time, long countDownTime, long countDownInterval, OnCountDownListener listener) {
            this.textView = tv_time;
            this.countDownTime = countDownTime;
            this.countDownInterval = countDownInterval;
            this.listener = listener;
            time = countDownTime;
            //创建时更新下时间
            onTick(time);
        }

        public boolean tick(){
            time -=  countDownInterval;
            boolean isFinish = time <= 0;
            if(isFinish) {
                onFinish();
            } else {
                onTick(time);
            }
            return isFinish;
        }

        private void onTick(long millisUntilFinished) {
            L.e("onTick:" + this);
            if(listener != null) {
                listener.onTick(textView, millisUntilFinished);
            } else {
                textView.setText(transferToTime(millisUntilFinished / 1000));
            }
        }

        private void onFinish() {
            if(listener != null) {
                listener.onFinish(textView);
            } else {
                textView.setText("时间到");
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                    "textView=" + textView +
                    ", countDownTime=" + countDownTime +
                    ", countDownInterval=" + countDownInterval +
                    ", time=" + time +
                    '}';
        }
    }

    //单位 s
    public static String transferToTime(long time) {
        long m = time / 60;
        long s = time % 60;
        StringBuilder sb = new StringBuilder();
        if(m != 0) {
            sb.append(m).append("分");
        }
        sb.append(s).append("秒");
        return sb.toString();
    }

    public interface OnCountDownListener {
        void onTick(View view, long millisUntilFinished);

        void onFinish(View view);
    }
}
