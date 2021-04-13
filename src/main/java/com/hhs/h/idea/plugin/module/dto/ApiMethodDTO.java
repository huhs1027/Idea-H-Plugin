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
    private String name;

    /**
     * 方法标题
     */
    private String title;

    /**
     * 接口描述
     */
    private String desc;

    /**
     * 请求类型 post get等等
     */
    private String method;

    /**
     * 编码 表单，json
     */
    private String contentType;

    /**
     * 路径列表
     */
    private List<String> pathUrlList;

    /**
     * 方法参数列表
     */
    private List<ApiFieldDTO> paramList;

    /**
     * 方法返回值列表
     */
    private List<ApiFieldDTO> returnList;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<String> getPathUrlList() {
        return pathUrlList;
    }

    public void setPathUrlList(List<String> pathUrlList) {
        this.pathUrlList = pathUrlList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<ApiFieldDTO> getParamList() {
        return paramList;
    }

    public void setParamList(List<ApiFieldDTO> paramList) {
        this.paramList = paramList;
    }

    public List<ApiFieldDTO> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ApiFieldDTO> returnList) {
        this.returnList = returnList;
    }
}
