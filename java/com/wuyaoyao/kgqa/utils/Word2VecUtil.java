package com.wuyaoyao.kgqa.utils;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;


public class Word2VecUtil {

    private static Logger log = LoggerFactory.getLogger(Word2VecUtil.class);
    private static String outputPath = "C:\\Users\\Administrator\\IdeaProjects\\KGQA\\src\\main\\resources\\model\\word2vec.txt";
    public static void main(String[] args) {
        //输入文本文件的目录
        File inputTxt = new File("E:\\pythonProject\\data\\corpus.txt");
        log.info("开始加载数据...."+inputTxt.getName());
        //加载数据
        SentenceIterator iter = new LineSentenceIterator(inputTxt);
        log.info("训练模型....");
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(5)//词在语料中必须出现的最少次数
                .iterations(1)
                .layerSize(100)  //向量维度
                .seed(42)
                .windowSize(10) //窗口大小
                .iterate(iter)
                .build();
        log.info("配置模型....");
        vec.fit();
        log.info("输出词向量....");
        WordVectorSerializer.writeWord2VecModel(vec,outputPath);
        log.info("相似的词:");
        //获取相似的词
        Collection<String> lst = vec.wordsNearest("婴儿热痱", 10);
        System.out.println(lst);
        //获取某词对应的向量
        log.info("向量获取:");
        double[] wordVector = vec.getWordVector("day");
        System.out.println(Arrays.toString(wordVector));
    }
}
