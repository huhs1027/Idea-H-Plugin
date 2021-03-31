package com.hhs.h.idea.plugin.module.dto;

import java.util.List;

/**
 * 记录api文档
 *
 * @author hhs
 * @since 2021/3/31 10:51
 */
public class ApiDocDTO {

    /**
     * 类列表
     */
    private List<ApiClassDTO> apiClassDTOList;

    public List<ApiClassDTO> getApiClassDTOList() {
        return apiClassDTOList;
    }

    public void setApiClassDTOList(List<ApiClassDTO> apiClassDTOList) {
        this.apiClassDTOList = apiClassDTOList;
    }
}
