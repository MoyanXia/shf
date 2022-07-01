package com.atguigu.controller;


import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends BaseController {

    private static final String LIST_ACTION = "redirect:/house/";
    @Reference
    private HouseImageService houseImageService;

    private static final String PAGE_UPLOAD = "house/upload";

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable Long houseId, @PathVariable Long type,Model model){
        model.addAttribute("houseId",houseId);
        model.addAttribute("type",type);
        return PAGE_UPLOAD;
    }
    
    @ResponseBody
    @PostMapping("/upload/{houseId}/{type}")
    public Result upload(@RequestParam("file") MultipartFile[] files, @PathVariable Long houseId, @PathVariable Integer type) throws IOException {
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString().replace("-", "") + "." + originalFilename.split("\\.")[1];
            QiniuUtils.upload2Qiniu(file.getBytes(),fileName);
            HouseImage houseImage = new HouseImage();
            houseImage.setHouseId(houseId);
            houseImage.setType(type);
            houseImage.setImageName(fileName);
            houseImage.setImageUrl(QiniuUtils.getUrl(fileName));
            houseImageService.insert(houseImage);
        }
        return Result.ok();
    }
    
    @RequestMapping("/delete/{houseId}/{imageId}")
    public String delete(@PathVariable Long houseId, @PathVariable Long imageId){
        HouseImage houseImage = houseImageService.getById(imageId);
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());
        houseImageService.delete(houseImage.getId());
        
        return LIST_ACTION+houseId;
    }
}
