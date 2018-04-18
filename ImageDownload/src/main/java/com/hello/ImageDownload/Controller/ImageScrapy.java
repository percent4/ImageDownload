package com.hello.ImageDownload.Controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/* ImageScrapy类实现Runnable接口
 * ImageScrapy类的构造参数： url:网址的网址, dir: 图片存储目录
 * ImageScrapy类实现了将网页中的图片下载到本地
 */

public class ImageScrapy implements Runnable{
    private String url;
    private String dir;

    public ImageScrapy(String url, String dir) {
        this.url = url;
        this.dir = dir;
    }

    // run()函数: 将网页中的电影图片下载到本地
    @Override
    public void run(){

        // 利用URL解析网址
        URL urlObj = null;
        try{
            urlObj = new URL(url);

        }
        catch(MalformedURLException e){
            System.out.println("The url was malformed!");
        }

        // URL连接
        URLConnection urlCon = null;
        try{
            // 打开URL连接
            urlCon = urlObj.openConnection();
            // 将HTML内容解析成UTF-8格式
            Document doc = Jsoup.parse(urlCon.getInputStream(), "utf-8", url);
            // 提取电影图片所在的HTML代码块
            Elements pic_block = doc.getElementsByTag("img");

            for(int i=0; i<pic_block.size(); i++) {
                try {
                    // 提取电影图片的url, name
                    String picture_url = pic_block.get(i).attr("src");

                    List<String>  splits = Arrays.asList(picture_url.split("[\\.]"));

                    String suffix = splits.get(splits.size()-1);

                    String name = pic_block.get(i).attr("alt");
                    String picture_name;

                    if(name.length() ==0){
                        picture_name = UUID.randomUUID().toString().
                                replaceAll("-", "").substring(0, 10)+'.'+suffix;
                    }
                    else{
                        picture_name = name+'.'+suffix;
                    }

                    System.out.println(picture_name + " " + picture_url);
                    // 用download()函数将电影图片下载到本地
                    download(picture_url, dir, picture_name);
                    System.out.println("第" + (i + 1) + "张图片下载完毕！");
                }
                catch (Exception e){
                    System.out.println("第" + (i + 1) + "张图片下载失败！");
                }
            }

        }
        catch(IOException e){
            System.out.println("There was an error connecting to the URL");
        }

    }

    // download()函数利用图片的url将图片下载到本地
    public static void download(String url, String dir, String filename) {
        try {

            /* httpurl: 图片的url
             * dirfile: 图片的储存目录
             */
            URL httpurl = new URL(url);
            File dirfile = new File(dir);

            // 如果图片储存的目录不存在，则新建该目录
            if (!dirfile.exists()) {
                dirfile.mkdirs();
            }

            // 利用FileUtils.copyURLToFile()实现图片下载
            FileUtils.copyURLToFile(httpurl, new File(dir+filename));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
