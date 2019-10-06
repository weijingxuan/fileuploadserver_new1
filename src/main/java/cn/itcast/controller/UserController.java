package cn.itcast.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    /**-
     * springmvc方式
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/fileupload2")
    public String fileupload2(HttpServletRequest request, MultipartFile upload) throws Exception{
        System.out.println("springmvc文件上传..");
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        String filename = upload.getOriginalFilename();
        //说明上传文件项
        //把文件的名称设置成唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename=uuid+"_"+filename;
        //完成文件上传
        upload.transferTo(new File(path,filename));

        return "success";
    }

    /**
     * 传统方式
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/fileupload1")
    public String fileupload1(HttpServletRequest request) throws Exception{
        System.out.println("文件上传..");
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
            System.out.println("执行了这一步");
        }
        //解析request对象，获取上传文件项
        DiskFileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);
        for(FileItem item:items){
            if(item.isFormField()){
                //说明普通表单项

            }else{
                //说明上传文件项
                String filename = item.getName();
                //把文件的名称设置成唯一值，uuid
                String uuid = UUID.randomUUID().toString().replace("-", "");
                filename=uuid+"_"+filename;
                //完成文件上传
                item.write(new File(path,filename));
                System.out.println("也执行了这一步");
                item.delete();
            }
        }

        return "success";
    }
}
