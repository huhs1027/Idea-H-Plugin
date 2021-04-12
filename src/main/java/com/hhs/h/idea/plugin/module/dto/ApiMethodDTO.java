package com.hhs.h.idea.plugin.module.dto;

import java.util.List;

/**
 * 记录java方法
 *
 * @author hhs
 * @since 2021/3/31 14:31
 */
public class ApiMethodDTO {

    /**
     * 方法名字
     */
    private String qualifiedName;

    /**
     * 接口描述
     */
    private String desc;

    /**
     * 请求类型 post get等等
     */
    private String request;

    /**
     * 路径列表
     */
    private List<String> pathUrlList;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public List<String> getPathUrlList() {
        return pathUrlList;
    }

    public void setPathUrlList(List<String> pathUrlList) {
        this.pathUrlList = pathUrlList;
    }
}
