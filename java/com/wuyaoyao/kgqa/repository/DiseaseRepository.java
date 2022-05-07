package com.wuyaoyao.kgqa.repository;

import com.wuyaoyao.kgqa.entity.Disease;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiseaseRepository extends Neo4jRepository<Disease, Long> {

    /**
     * 模板0：返回疾病症状 == nm 症状
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:has_symptom]->(n:Symptom) where m.name = $name return n.name")
    List<String> getSymptomByName(@Param("name") String name);

    /**
     * 模板1：返回疾病病因 == nm 病因
     * @param name
     * @return
     */
    @Query("match (m:Disease) where m.name = $name return m.cause")
    String getCauseByName(@Param("name") String name);

    /**
     * 模板2：返回疾病并发症 == nm 并发症
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:accompany_with]->(n:Disease) where m.name = $name return n.name")
    List<String> getAccompanyByName(@Param("name") String name);

    /**
     * 模板3：返回疾病忌吃 == nm 忌吃
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:no_eat]->(n:Food) where m.name = $name return n.name")
    List<String> getNotEatByName(@Param("name") String name);

    /**
     * 模板4：返回疾病宜吃 == nm 宜吃
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:do_eat]->(n:Food) where m.name = $name return n.name")
    List<String> getDoEatByName(@Param("name") String name);

    @Query("match (m:Disease)-[r:recommend_eat]->(n:Food) where m.name = $name return n.name")
    List<String> getRecommendEatByName(@Param("name") String name);

    /**
     * 模板5：返回食物忌吃对应的疾病 == nr 忌吃 疾病
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:no_eat]->(n:Food) where n.name = $name return m.name")
    List<String> getNotEatDiseaseByName(@Param("name") String name);

    /**
     * 模板6：返回食物宜吃对应的疾病 == nr 宜吃 疾病
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:do_eat]->(n:Food) where n.name = $name return m.name")
    List<String> getDoEatDiseaseByName(@Param("name") String name);

    /**
     * 模板6：返回食物推荐吃对应的疾病 == nr 推荐 疾病
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:recommend_eat]->(n:Food) where n.name = $name return m.name")
    List<String> getRecommendEatDiseaseByName(@Param("name") String name);

    /**
     * 模板7：返回疾病简介 == nm 简介
     * @param name
     * @return
     */
    @Query("match (m:Disease) where m.name = $name return m.desc")
    String getDescriptionByName(@Param("name") String name);

    /**
     * 模板8：返回疾病易感人群 == nm 易感人群
     * @param name
     * @return
     */
    @Query("match (m:Disease) where m.name = $name return m.easy_get")
    String getEasyByName(@Param("name") String name);

    /**
     * 模板9：返回疾病传播途径 == nm 传播途径
     * @param name
     * @return
     */
    @Query("match (m:Disease) where m.name = $name return m.get_way")
    String getWayByName(@Param("name") String name);


    /**
     * 模板10：返回疾病推荐药品 == nm 推荐药品
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:recommend_drug]->(n:Drug) where m.name = $name return n.name")
    List<String> getRecommendDrugByName(@Param("name") String name);

    /**
     * 模板11：返回推荐药品适用的疾病 == ng 适用疾病
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:recommend_drug]->(n:Drug) where n.name = $name return m.name")
    List<String> getRecommendDrugDiseaseByName(@Param("name") String name);

    /**
     * 模板12：返回疾病所需检查 == nm 所需检查
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:need_check]->(n:Check) where m.name = $name return n.name")
    List<String> getCheckByName(@Param("name") String name);

    /**
     * 模板13：返回检查项目适用的疾病 == nc 疾病
     * @param name
     * @return
     */
    @Query("match (m:Disease)-[r:need_check]->(n:Check) where n.name = $name return m.name")
    List<String> getCheckDiseaseByName(@Param("name") String name);

    /**
     * 模板14：返回疾病所属科室 == nm 所属科室
     * @param name
     * @return
     */
    @Query("match (m:Disease) where m.name = $name return m.cure_department")
    Object getDepartmentByName(@Param("name") String name);

    /**
     * 模板15：返回疾病预防措施 == nm 预防措施
     * @param name
     * @return
     */
    @Query("match (m:Disease) where m.name = $name return m.prevent")
    String getPreventByName(@Param("name") String name);

    /**
     * 模板16：返回疾病治疗周期 == nm 治疗周期
     * @param name
     * @return
     */
    @Query("match (m:Disease) where m.name = $name return m.cure_period")
    String getTimeByName(@Param("name") String name);

    /**
     * 模板17：返回疾病治愈概率 == nm 治愈概率
     * @param name
     * @return
     */
    @Query("match (m:Disease) where m.name = $name return m.cured_prob")
    String getProbabilityByName(@Param("name") String name);

    /**
     * 模板18：返回疾病治疗手段 == nm 治疗手段
     * @param name
     * @return
     */
    @Query("match (m:Disease) where m.name = $name return m.cure_way")
    Object getCureWayByName(@Param("name") String name);
}
