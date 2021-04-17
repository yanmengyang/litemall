package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.core.util.ExeclUtil;
import org.linlinjava.litemall.db.service.AuntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Integer insertBatch(MultipartFile file){
        List<String> keyList=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            keyList.add(""+i);
        }
        try {
            List<Map<String,Object>> mapList=ExeclUtil.readExeclWithImg(file.getInputStream(),keyList);
            int row=0;
            for (Map<String, Object> map : mapList) {
                System.out.println("行数:"+row);
                row++;
                for (String s : map.keySet()) {
                    System.out.println("key:"+s);
                    System.out.println("value:"+map.get(s).toString());
                }
                System.out.println();
            }
            return mapList.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
