package com.hhs.h.idea.plugin.module.dto;

import java.util.List;

/**
 * 记录java类
 *
 * @author hhs
 * @since 2021/3/31 10:51
 */
public class ApiClassDTO {

    /**
     * 类全限定名
     */
    private String name;

    /**
     * 类接口标题
     */
    private String title;

    /**
     * 类接口描述
     */
    private String docDesc;

    /**
     * 类下的方法列表
     */
    private List<ApiMethodDTO> methodList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc;
    }

    public List<ApiMethodDTO> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<ApiMethodDTO> methodList) {
        this.methodList = methodList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
