package org.linlinjava.litemall.admin.service;

import org.apache.commons.io.FileUtils;
import org.linlinjava.litemall.core.storage.StorageService;
import org.linlinjava.litemall.core.util.ExeclUtil;
import org.linlinjava.litemall.db.domain.Aunt;
import org.linlinjava.litemall.db.domain.LitemallStorage;
import org.linlinjava.litemall.db.service.AuntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: honglei
 * @time: 2021/4/12 0012 16:27
 */
@Service
public class AdminAuntService {
    @Autowired
    AuntService service;
    @Autowired
    private StorageService storageService;

    public Integer insertBatch(MultipartFile file){
        List<String> keyList=new ArrayList<>();
        keyList.add("nickName");//
        keyList.add("sex");//
        keyList.add("age");//
        keyList.add("idcard");//
//        keyList.add("birthday");//
        keyList.add("nativePlace");//'籍贯'
        keyList.add("experience");//'从业经验时长'
        keyList.add("education");//'学历'
//        keyList.add("sign");//属相
        keyList.add("mobile");//
        keyList.add("zodiac");//'生肖'
        keyList.add("nation");//'民族'
        keyList.add("constellation");//'星座'
        keyList.add("identityApprove");//'身份认证'
        keyList.add("skillApprove");//'技能认证'
        keyList.add("backgroundApprove");//'背景调查'
        keyList.add("trainApprove");//'职业培训'
        keyList.add("resumeApprove");//'简历真实'
        keyList.add("examinationApprove");//'体检报告'
        keyList.add("insuranceApprove");//'商户保险'
        keyList.add("selfIntroduction");//'自我介绍'
        keyList.add("jobIntention");//'求职意向'
        keyList.add("workExperience");//'工作经历'
        keyList.add("des");//'培训经历'
        keyList.add("flag");//人物标签
        keyList.add("expertin");//对应字典主要技能
        keyList.add("type");//类别矩阵
        keyList.add("headUrl");//
        keyList.add("personalPresentation");//'个人展示'

        try {
            List<Map<String,Object>> mapList=ExeclUtil.readExeclWithImg(file.getInputStream(),keyList);
            formatData(mapList);
            return mapList.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    void formatData(List<Map<String,Object>> mapList){
        for (Map<String, Object> map : mapList) {
            List<String> fileList= map.get("headUrl")==null?null: (List<String>) map.get("headUrl");

            List<String> fileList2= map.get("personalPresentation")==null?null: (List<String>) map.get("personalPresentation");
            fss(fileList);
            fss(fileList2);
        }
    }

    private void fss(List<String> fileList){
        if (!CollectionUtils.isEmpty(fileList)){
            for (String s : fileList) {
                File file=new File(s);
                try {
                    InputStream inputStream=new FileInputStream(s);
                    LitemallStorage litemallStorage = storageService.store(inputStream, file.length(),
                            "png", file.getName());
                    inputStream.close();
                    s=litemallStorage.getUrl();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
