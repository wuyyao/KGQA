package com.wuyaoyao.kgqa.config;

import com.wuyaoyao.kgqa.utils.CustomDictWordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * <p>全局配启动 -- 初始化项目时，执行命令，将相关额外的自定义词典加载下</p>
 */
@Component
public class ConfigRunner implements CommandLineRunner {

    @Value("${HanLP.CustomDictionary.path.diseaseDict}")
    private String diseaseDictPath;

    @Value("${HanLP.CustomDictionary.path.drugDict}")
    private String drugDictPath;

    @Value("${HanLP.CustomDictionary.path.foodDict}")
    private String foodDictPath;

    @Value("${HanLP.CustomDictionary.path.checkDict}")
    private String checkDictPath;

    @Value("${HanLP.CustomDictionary.cache.path}")
    private String cacheDictPath;

    @Override
    public void run(String... args){

        //先删除缓存
        File file = new File(cacheDictPath);
        if(file.exists()){
            file.delete();
            System.out.println("CustomDictionary.txt.bin delete success .");
        }

        /**加载自定义的【疾病】字典 == 设置词性 nm 0*/
        loadDict(diseaseDictPath,0);
        /**加载自定义的【药品】字典 == 设置词性 ng 0*/
        loadDict(drugDictPath,1);
        /**加载自定义的【食物】字典 == 设置词性 nr 0*/
        loadDict(foodDictPath,2);
        /**加载自定义的【检查】字典 == 设置词性 nc 0*/
        loadDict(checkDictPath,3);

    }

    /**
     * 加载自定义词性字典
     * @param path 字典路径
     * @param type 类型
     */
    public void loadDict(String path,Integer type) {
        File file = new File(path);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            addCustomDictionary(br, type);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }


    /**
     * 添加自定义分词及其词性，注意数字0表示频率，不能没有
     *
     * @param br 字节流（读）
     * @param type 字典类型
     */
    public void addCustomDictionary(BufferedReader br, int type) {
        String word;
        try {
            while ((word = br.readLine()) != null) {
                switch (type) {
                    /**设置疾病名词性 == nm 0*/
                    case 0:
                        CustomDictWordUtils.setNatureAndFrequency(word,"nm 0",true);
                        break;
                    /**设置药品类型名词 词性 == ng 0*/
                    case 1:
                        CustomDictWordUtils.setNatureAndFrequency(word,"ng 0",true);
                        break;
                    /**设置食物类型词 词性 == nr 0*/
                    case 2:
                        CustomDictWordUtils.setNatureAndFrequency(word,"nr 0",true);
                        break;
                    /**设置检查类型词 词性 == nc 0*/
                    case 3:
                        CustomDictWordUtils.setNatureAndFrequency(word,"nc 0",true);
                        break;
                    default:
                        break;
                }
            }
            br.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
