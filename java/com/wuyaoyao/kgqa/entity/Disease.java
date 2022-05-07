package com.wuyaoyao.kgqa.entity;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Disease extends BaseEntity{

    @Property(name = "name")
    private String name;

    @Property(name = "cause")
    private String cause;

    @Property(name = "cure_department")
    private Object cure_department;

    @Property(name = "cure_period")
    private String cure_period;

    @Property(name = "cure_way")
    private Object cure_way;

    @Property(name = "cured_prob")
    private String cured_prob;

    @Property(name = "desc")
    private String desc;

    @Property(name = "easy_get")
    private String easy_get;

    @Property(name = "get_way")
    private String get_way;

    @Property(name = "prevent")
    private String prevent;

    @Override
    public String toString() {
        return "Disease{" +
                "name='" + name + '\'' +
                ", cause='" + cause + '\'' +
                ", cure_department='" + cure_department.toString() + '\'' +
                ", cure_period='" + cure_period + '\'' +
                ", cure_way='" + cure_way.toString() + '\'' +
                ", cured_prob='" + cured_prob + '\'' +
                ", desc='" + desc + '\'' +
                ", easy_get='" + easy_get + '\'' +
                ", get_way='" + get_way + '\'' +
                ", prevent='" + prevent + '\'' +
                '}';
    }
}
