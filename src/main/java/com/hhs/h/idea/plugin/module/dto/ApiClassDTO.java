package com.hhs.h.idea.plugin.module.dto;

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

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
}
