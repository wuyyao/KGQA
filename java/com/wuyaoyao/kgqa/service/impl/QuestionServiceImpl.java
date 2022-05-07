package com.wuyaoyao.kgqa.service.impl;

import com.wuyaoyao.kgqa.core.CoreProcessor;
import com.wuyaoyao.kgqa.repository.DiseaseRepository;
import com.wuyaoyao.kgqa.service.QuestionService;
import com.wuyaoyao.kgqa.utils.DiseaseIfExist;
import com.wuyaoyao.kgqa.utils.SimFeatureUtils;
import com.wuyaoyao.kgqa.utils.SimilarUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private CoreProcessor coreProcessor;

    @Autowired
    private DiseaseRepository diseaseRepository;

    /**
     * 问题搜索
     * 基于分类器计算问题与模板的匹配概率找到答案
     *
     * @param question
     * @return
     * @throws Exception
     */
    @Override
    public String answer(String question) throws Exception {
        List<String> reStrings = coreProcessor.analysis(question);
        int modelIndex = Integer.valueOf(reStrings.get(0));
        String answer = null;

        /**匹配问题模板*/
        switch (modelIndex) {
            case 0://nm 症状
                answer = getDiseaseSymptom(reStrings);
                break;
            case 1://nm 病因
                answer = getDiseaseCause(reStrings);
                break;
            case 2://nm 并发症
                answer = getDiseaseAccompany(reStrings);
                break;
            case 3://nm 忌吃
                answer = getDiseaseNotEat(reStrings);
                break;
            case 4://nm 宜吃
                answer = getDiseaseDoEat(reStrings);
                break;
            case 5://nr 疾病忌吃
                answer = getDiseaseNotEatFood(reStrings);
                break;
            case 6://nr 疾病宜吃
                answer = getDiseaseDoEatFood(reStrings);
                break;
            case 7://nm 简介
                answer = getDiseaseDescription(reStrings);
                break;
            case 8://nm 易感人群
                answer = getDiseaseEasyGet(reStrings);
                break;
            case 9://nm 传播途径
                answer = getDiseaseGetWay(reStrings);
                break;
            case 10://nm 常用药
                answer = getDiseaseDrug(reStrings);
                break;
            case 11://ng 疾病用药
                answer = getDrugDisease(reStrings);
                break;
            case 12://nm 所需检查
                answer = getDiseaseCheck(reStrings);
                break;
            case 13://nc 疾病检查
                answer = getCheckDisease(reStrings);
                break;
            case 14://nm 所属科室
                answer = getDiseaseDepartment(reStrings);
                break;
            case 15://nm 预防措施
                answer = getDiseasePrevent(reStrings);
                break;
            case 16://nm 治疗周期
                answer = getDiseaseTime(reStrings);
                break;
            case 17://nm 治愈概率
                answer = getDiseaseProbability(reStrings);
                break;
            case 18://nm 治疗手段
                answer = getDiseaseCureWay(reStrings);
                break;
            default:
                break;
        }
        System.out.println(answer);
        if (answer != null && !"".equals(answer) && !("\\N").equals(answer)) {
            return answer;
        } else {
            return "抱歉，小瑶能力有限，请咨询医生！";
        }
    }

    /*0:nm 症状*/
    private String getDiseaseSymptom(List<String> reStrings) {
        String name = reStrings.get(1);
        if (!DiseaseIfExist.ifExist(name)) {
            name = SimFeatureUtils.similar(name);
        }
        String answer = "";
        if (name != ""){
            List<String> symptom = diseaseRepository.getSymptomByName(name);
            if (symptom != null) {
                for (String one : symptom) {
                    answer = answer + one + " ";
                }
            } else {
                answer = null;
            }
        } else {
            answer = null;
        }
        return answer;
    }

    /*1:nm 病因*/
    private String getDiseaseCause(List<String> reStrings) {
        String name = reStrings.get(1);
        String cause = diseaseRepository.getCauseByName(name);
        String answer;
        if (cause != null) {
            answer = cause;
        } else {
            answer = null;
        }
        return answer;
    }

    /*2:nm 并发症*/
    private String getDiseaseAccompany(List<String> reStrings) {
        String name = reStrings.get(1);
        List<String> accompany = diseaseRepository.getAccompanyByName(name);
        String answer = "";
        if (accompany != null) {
            for (String one : accompany) {
                answer = answer + one + " ";
            }
        } else {
            answer = null;
        }
        return answer;
    }

    /*3:nm 忌吃*/
    private String getDiseaseNotEat(List<String> reStrings) {
        String name = reStrings.get(1);
        List<String> food = diseaseRepository.getNotEatByName(name);
        String answer = "";
        if (food != null) {
            for (String one : food) {
                answer = answer + one + " ";
            }
        } else {
            answer = null;
        }
        return answer;
    }

    /*4:nm 宜吃*/
    private String getDiseaseDoEat(List<String> reStrings) {
        String name = reStrings.get(1);
        List<String> food = diseaseRepository.getDoEatByName(name);
        String answer = "";
        if (food != null) {
            for (String one : food) {
                answer = answer + one + " ";
            }
        } else {
            answer = null;
        }
        return answer;
    }

    /*5:nr 疾病忌吃*/
    private String getDiseaseNotEatFood(List<String> reStrings) {
        String foodName = reStrings.get(1);
        List<String> food = diseaseRepository.getNotEatDiseaseByName(foodName);
        String answer = "";
        if (food != null) {
            for (String one : food) {
                answer = answer + one + " ";
            }
        } else {
            answer = null;
        }
        return answer;
    }

    /*6:nr 疾病宜吃*/
    private String getDiseaseDoEatFood(List<String> reStrings) {
        String foodName = reStrings.get(1);
        List<String> food = diseaseRepository.getDoEatDiseaseByName(foodName);
        List<String> food_recommend = diseaseRepository.getRecommendEatDiseaseByName(foodName);
        String answer = "";
        if (food != null) {
            for (String one : food) {
                answer = answer + one + " ";
            }
        }
        if (food_recommend != null) {
            for (String one : food_recommend) {
                answer = answer + one + " ";
            }
        }
        if (food == null && food_recommend == null) {
            answer = null;
        }
        return answer;
    }

    /*7:nm 简介*/
    private String getDiseaseDescription(List<String> reStrings) {
        String name = reStrings.get(1);
        String description = diseaseRepository.getDescriptionByName(name);
        String answer = "";
        if (description != null) {
            answer = description;
        } else {
            answer = null;
        }
        return answer;
    }

    /*8:nm 易感人群*/
    private String getDiseaseEasyGet(List<String> reStrings) {
        String name = reStrings.get(1);
        String easy = diseaseRepository.getEasyByName(name);
        String answer = "";
        if (easy != null) {
            answer = easy;
        } else {
            answer = null;
        }
        return answer;
    }

    /*9:nm 传播途径*/
    private String getDiseaseGetWay(List<String> reStrings) {
        String name = reStrings.get(1);
        String way = diseaseRepository.getWayByName(name);
        String answer = "";
        if (way != null) {
            answer = way;
        } else {
            answer = null;
        }
        return answer;
    }

    /*10:nm 常用药*/
    private String getDiseaseDrug(List<String> reStrings) {
        String name = reStrings.get(1);
        List<String> recommend_drug = diseaseRepository.getRecommendDrugByName(name);
        String answer = "";
        if (recommend_drug != null) {
            for (String one : recommend_drug) {
                answer = answer + one + " ";
            }
        } else {
            answer = null;
        }
        return answer;
    }

    /*11:ng 疾病用药*/
    private String getDrugDisease(List<String> reStrings) {
        String drugName = reStrings.get(1);
        List<String> recommend_drug = diseaseRepository.getRecommendDrugDiseaseByName(drugName);
        String answer = "";
        if (recommend_drug != null) {
            for (String one : recommend_drug) {
                answer = answer + one + " ";
            }
        } else {
            answer = null;
        }
        return answer;
    }

    /*12:nm 所需检查*/
    private String getDiseaseCheck(List<String> reStrings) {
        String name = reStrings.get(1);
        List<String> check = diseaseRepository.getCheckByName(name);
        String answer = "";
        if (check != null) {
            for (String one : check) {
                answer = answer + one + " ";
            }
        } else {
            answer = null;
        }
        return answer;
    }

    /*13:nc 疾病检查*/
    private String getCheckDisease(List<String> reStrings) {
        String checkName = reStrings.get(1);
        List<String> check = diseaseRepository.getCheckDiseaseByName(checkName);
        String answer = "";
        if (check != null) {
            for (String one : check) {
                answer = answer + one + " ";
            }
        } else {
            answer = null;
        }
        return answer;
    }

    /*14:nm 所属科室*/
    private String getDiseaseDepartment(List<String> reStrings) {
        String name = reStrings.get(1);
        Object department = diseaseRepository.getDepartmentByName(name);
        String answer = "";
        if (department != null) {
            answer = department.toString().replace("Optional[[\"", "").replace("\"]]", "").replace("\", \"", " ");
        } else {
            answer = null;
        }
        return answer;
    }

    /*15:nm 预防措施*/
    private String getDiseasePrevent(List<String> reStrings) {
        String name = reStrings.get(1);
        String prevent = diseaseRepository.getPreventByName(name);
        String answer = "";
        if (prevent != null) {
            answer = prevent;
        } else {
            answer = null;
        }
        return answer;
    }

    /*16:nm 治疗周期*/
    private String getDiseaseTime(List<String> reStrings) {
        String name = reStrings.get(1);
        String time = diseaseRepository.getTimeByName(name);
        String answer = "";
        if (time != null) {
            answer = time;
        } else {
            answer = null;
        }
        return answer;
    }

    /*17:nm 治愈概率*/
    private String getDiseaseProbability(List<String> reStrings) {
        String name = reStrings.get(1);
        String probability = diseaseRepository.getProbabilityByName(name);
        String answer = "";
        if (probability != null) {
            answer = probability;
        } else {
            answer = null;
        }
        return answer;
    }

    /*18:nm 治疗手段*/
    private String getDiseaseCureWay(List<String> reStrings) {
        String name = reStrings.get(1);
        Object way = diseaseRepository.getCureWayByName(name);
        String answer = "";
        if (way != null) {
            answer = way.toString().replace("Optional[[\"", "").replace("\"]]", "").replace("\", \"", " ");
        } else {
            answer = null;
        }
        return answer;
    }

    /**
     * 知识图谱
     *
     * @param diseaseName
     * @return
     * @throws JSONException
     */
    @Override
    public String knowledgeGraph(String diseaseName) throws JSONException {
        JSONObject result = new JSONObject();
        JSONObject show = new JSONObject();
        JSONObject info = new JSONObject();
        if (!DiseaseIfExist.ifExist(diseaseName)) {
            diseaseName = SimFeatureUtils.similar(diseaseName);
        }
        List<String> accompanyWith = diseaseRepository.getAccompanyByName(diseaseName);
        List<String> doEat = diseaseRepository.getDoEatByName(diseaseName);
        List<String> noEat = diseaseRepository.getNotEatByName(diseaseName);
        List<String> recommendEat = diseaseRepository.getRecommendEatByName(diseaseName);
        List<String> recommendDrug = diseaseRepository.getRecommendDrugByName(diseaseName);
        List<String> symptom = diseaseRepository.getSymptomByName(diseaseName);
        show.put("name", diseaseName);
        show.put("accompanyWith", accompanyWith);
        show.put("doEat", doEat);
        show.put("noEat", noEat);
        show.put("recommendEat", recommendEat);
        show.put("recommendDrug", recommendDrug);
        show.put("symptom", symptom);
        String desc = diseaseRepository.getDescriptionByName(diseaseName);
        String cause = diseaseRepository.getCauseByName(diseaseName);
        String department = diseaseRepository.getDepartmentByName(diseaseName).toString().replace("Optional[[\"", "").replace("\"]]", "").replace("\", \"", " ");
        String time = diseaseRepository.getTimeByName(diseaseName);
        String way = diseaseRepository.getWayByName(diseaseName);
        String easy = diseaseRepository.getEasyByName(diseaseName);
        String prob = diseaseRepository.getProbabilityByName(diseaseName);
        String prevent = diseaseRepository.getPreventByName(diseaseName);
        String cure = diseaseRepository.getCureWayByName(diseaseName).toString().replace("Optional[[\"", "").replace("\"]]", "").replace("\", \"", " ");
        info.put("name", diseaseName);
        info.put("desc", desc);
        info.put("cause", cause);
        info.put("department", department);
        info.put("time", time);
        info.put("way", way);
        info.put("easy", easy);
        info.put("prob", prob);
        info.put("prevent", prevent);
        //info.put("symptom", symptom);
        info.put("cure", cure);
        result.put("info", info);
        result.put("show", show);
        System.out.println(result);
        return result.toString();
    }
}
