package com.example.allreader.utils.Manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {

    private ThreadPoolManager() {

    }

    private static class Holder {
        static ExecutorService executorService = Executors.newSingleThreadExecutor();
        static ExecutorService shareService = Executors.newSingleThreadExecutor();
    }

    public static ExecutorService getSingleExecutor() {
        return Holder.executorService;
    }

    public static ExecutorService getShareExecutor() {
        return Holder.shareService;
    }

}
