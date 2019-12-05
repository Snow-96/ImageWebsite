package com.csci430.website.controller;

import com.csci430.website.entity.Image;
import com.csci430.website.function.SateToken;
import com.csci430.website.repository.ImageRepository;
import com.csci430.website.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GalleryController {
    private final ImageRepository imageRepository;
    @Value("${imagesPath}")
    private String imagesPath;
    @Value("${imagesDir}")
    private String imagesDir;
    @Value("${downloadPath}")
    private String downloadPath;

    @Autowired
    public GalleryController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/gallery")
    public String gallery(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        model.addAttribute("imagesList", imageRepository.findByUserEmail(userEmail));
        return "gallery";
    }

    @PostMapping("/uploadImage")
    public @ResponseBody
    Map<String, String> uploadImage(HttpSession session, @RequestParam(value = "file") MultipartFile file) throws RuntimeException {
        Map<String, String> map = new HashMap<>();
        if (file == null || file.isEmpty()) {
            map.put("result", "fail");
            map.put("message", "Fail! File is empty");
            return map;
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("FileName: " + fileName);
        System.out.println("FilePath: " + imagesPath);
        String safeToken = "PIC" + SateToken.generateSafeToken(20);
        File dest = new File(imagesPath + safeToken + suffixName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            String userEmail = (String) session.getAttribute(WebSecurityConfig.SESSION_KEY);
            imageRepository.save(new Image(imagesDir + safeToken + suffixName, userEmail));
            map.put("result", "success");
            map.put("message", "Success");
            return map;
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        map.put("result", "fail");
        map.put("message", "Error");
        return map;
    }

    @GetMapping("/downloadImage")
    public void downloadImage(HttpServletResponse response, @RequestParam String url) {
        File file = new File(downloadPath + url);
        if (file.exists()) {
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + url);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download Success: " + url);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
