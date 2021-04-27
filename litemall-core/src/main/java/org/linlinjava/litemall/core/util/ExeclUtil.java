package org.linlinjava.litemall.core.util;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.PictureData;
/**
 * @description:
 * @author: honglei
 * @time: 2021/4/12 0012 15:20
 */
public class ExeclUtil {
    public static void main(String[] args) {
        List<String> keyList=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            keyList.add(""+i);
        }

        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\ddd2.xls");
            List<Map<String,Object>> mapList=readExeclWithImg(fileInputStream,keyList);
            int row=0;
            for (Map<String, Object> map : mapList) {
                System.out.println("行数:"+row);
                for (String s : map.keySet()) {

                    System.out.println("key:"+s);
                    System.out.println("value:"+map.get(s).toString());
                }
                System.out.println();
                row++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    public static List<Map<String,Object>> readExeclWithImg(InputStream inputStream,List<String> keyList){
        List<Map<String,Object>> mapList=new ArrayList<>();
        try {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);// 工作簿

        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);// 工作表

        int lastRowNum = sheet.getLastRowNum();// 获取最后一行序号,从零开始

        Map<String, List<PictureData>> picMap = new HashMap<>();// 存储图片信息和坐标

        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();

        if (list != null && list.size() > 0) {// 处理获取图片信息和坐标
            list = list.stream().filter(item -> item instanceof HSSFPicture).collect(Collectors.toList());// 过滤出图片的数据
            for (HSSFShape hssfShape : list) {
                HSSFPicture hSSFPicture = (HSSFPicture) hssfShape;
                HSSFClientAnchor hSSFClientAnchor = (HSSFClientAnchor) hSSFPicture.getAnchor();
                PictureData pictureData = hSSFPicture.getPictureData();
                String point = hSSFClientAnchor.getRow1() + "," + hSSFClientAnchor.getCol1();
                // 如果存在这个坐标KEY表示相同单元格中的图片,直接集合添加该图片,不存在该坐标key直接创建添加
                if (picMap.containsKey(point)) {
                    picMap.get(point).add(pictureData);
                } else {
                    List<PictureData> arrayList = new ArrayList<PictureData>();
                    arrayList.add(pictureData);
                    picMap.put(point, arrayList);
                }
            }
        }
       /* 表头不读取，所以从1开始*/
        for (int i = 1; i <= lastRowNum; i++) {
            Map<String,Object> map=new HashMap<>();
            HSSFRow row = sheet.getRow(i);
            int lastCellNumber =row.getLastCellNum();
            if (lastCellNumber>keyList.size()){
                lastCellNumber=keyList.size();
            }
            for (int j = 0; j <= lastCellNumber; j++) {
                map.put(keyList.get(j),null);
                HSSFCell cell=row.getCell(j);
            // 获取图片数据
            String key=""+i + "," + j;
            List<PictureData> pictureDataList = picMap.get(key);
            if (pictureDataList != null) {
                String filePath=new File("").getAbsolutePath()+"/"+key;
                List<String> fileList=new ArrayList<>(pictureDataList.size());
                for (PictureData pictureData : pictureDataList) {
                    String suggestFileExtension = pictureData.suggestFileExtension();// 图片格式
                    String path =filePath+ pictureDataList.indexOf(pictureData) + "."
                            + suggestFileExtension;// 存储路径
                    FileOutputStream out = new FileOutputStream(path);// 流写入
                    out.write(pictureData.getData());
                    out.close();
                    fileList.add(path);
                }
                map.put(keyList.get(j),fileList);
                continue;
            }
            if (cell==null){
                continue;
            }
            switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_STRING:
                        map.put(keyList.get(j),cell.getRichStringCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        map.put(keyList.get(j),cell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        map.put(keyList.get(j),cell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        map.put(keyList.get(j),cell.getCellFormula());
                        break;
                    default:
                        map.put(keyList.get(j),null);
                }
            }
            mapList.add(map);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return mapList;
    }




}
