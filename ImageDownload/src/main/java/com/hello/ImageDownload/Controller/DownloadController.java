package com.hello.ImageDownload.Controller;

import com.hello.ImageDownload.entity.Param;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class DownloadController {

    @GetMapping("/download")
    public String greetingForm(Model model) {
        model.addAttribute("param", new Param());
        return "download";
    }

    @PostMapping("/download")
    public void greetingSubmit(@ModelAttribute Param param) {

        String dir = param.getDirectory();

        String a = param.getUrls().replaceAll("[\\n+\\r+]", "@");

        List<String> urls = Arrays.asList(a.split("@+"));

        System.out.println("Directory: "+dir);
        System.out.println("URLS: ");
        for(String url: urls){
            System.out.println(url);
        }

        System.out.println("Begin downloading pictures......");

        MultiThreadDownload mt = new MultiThreadDownload(urls, dir);

        String status = mt.download();

        System.out.println(status);

    }
}
