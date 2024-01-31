package com.example.testjava.thread.xianchengchi;

/**
 * @ClassName Thread
 * @Author TZY
 * @Date 2024/1/19 11:13
 **/
public class Thread {
    public enum State {
        //新建
        NEW,
        //可運行狀態
        RUNNABLE,
        //阻塞狀態
        BLOCKED,
        //無限等待狀態
        WAITING,
        //計時等待
        TIMED_WAITING,
        //終止
        TERMINATED,
    }

    public java.lang.Thread.State getState() {
//        return jdk.internal.misc.VM.toThreadState(threadStatus);
    return java.lang.Thread.currentThread().getState();
    }
}
