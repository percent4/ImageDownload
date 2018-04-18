package com.hello.ImageDownload.Controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadDownload {
    private List<String> urls;
    private String dir;

    public MultiThreadDownload(List<String> urls, String dir) {
        this.urls = urls;
        this.dir = dir;
    }

    public String download(){

        try {
            // 利用多线程下载每个页面中的图片
            ExecutorService executor = Executors.newCachedThreadPool();

            // Create and launch 100 threads
            for (int i = 0; i < urls.size(); i++) {
                executor.execute(new ImageScrapy(urls.get(i), dir));
            }

            executor.shutdown();

            // wait until all tasks are finished
            while (!executor.isTerminated()) {
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return "Download pictures complete!";
    }
}
