package com.hhs.h.idea.plugin.module.dto;

import java.util.List;

/**
 * 记录java字段
 *
 * @author hhs
 * @since 2021/3/31 14:31
 */
public class ApiFieldDTO {

    /**
     * 级别
     */
    private Integer level;

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 字段说明
     */
    private String desc;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 子字段
     */
    private List<ApiFieldDTO> children;

    /**
     * JSON
     */
    private String json;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public List<ApiFieldDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ApiFieldDTO> children) {
        this.children = children;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
