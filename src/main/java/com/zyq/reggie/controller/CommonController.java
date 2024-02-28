package com.zyq.reggie.controller;

import com.zyq.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){//一定要与前端传过来的file组件的name一致
//        此file是个临时文件，需要转存到指定位置，否则本次请求完成后文件会被删除
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));//截取后缀
        String string = UUID.randomUUID().toString();//使用uuid创建一个随机标识码
        String filename = string+substring;
        //如果basepath不存在则创建目标文件夹
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath+filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(filename);//返回给后端完成的文件名，便于前端后续发送请求到后端将文件名存到数据库
    }

    @GetMapping("/download")
    public void download(String name , HttpServletResponse httpServletResponse){
        ServletOutputStream outputStream=null;
        FileInputStream fileInputStream=null;
        try {
//            输入流读取文件内容
            fileInputStream = new FileInputStream(new File(basePath+name));
            outputStream = httpServletResponse.getOutputStream();
//            别忘了修改传回数据类型
            httpServletResponse.setContentType("imag/jpeg");
            int length=0;
            byte[] bytes = new byte[1024];
            while ((length=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes, 0, length);
                outputStream.flush();
            }
//            关闭资源
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
