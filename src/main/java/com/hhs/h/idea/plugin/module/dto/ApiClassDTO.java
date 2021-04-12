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
    private String qualifiedName;

    /**
     * 类下的方法列表
     */
    private List<ApiMethodDTO> methodList;

    public List<ApiMethodDTO> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<ApiMethodDTO> methodList) {
        this.methodList = methodList;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
}
