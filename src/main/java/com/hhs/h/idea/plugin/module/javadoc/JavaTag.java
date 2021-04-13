package com.hhs.h.idea.plugin.module.javadoc;

/**
 * @author hutao.hhs
 * @since 2021/4/13 14:51
 */
public class JavaTag {

    /**
     * tag名称
     */
    private String name;

    /**
     * tag值,对应参数的引用
     */
    private String value;

    /**
     * tag描述
     */
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
