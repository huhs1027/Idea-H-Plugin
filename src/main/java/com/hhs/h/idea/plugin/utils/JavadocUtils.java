package com.hhs.h.idea.plugin.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hhs.h.idea.plugin.module.javadoc.JavaTag;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.javadoc.PsiDocParamRef;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author hutao.hhs
 * @since 2021/4/13 22:37
 */
public class JavadocUtils {

    private static final List<String> MERGE_TAG_NAMES = Arrays.asList("param", "throws");

    private JavadocUtils() {
    }

    /**
     * Find java doc description.
     *
     * @param docComment the Doc comment
     * @return the description
     */
    @NotNull
    public static List<String> findDocDescription(@NotNull PsiDocComment docComment) {
        List<String> descriptions = new LinkedList<>();
        PsiElement[] descriptionElements = docComment.getDescriptionElements();
        for (PsiElement descriptionElement : descriptionElements) {
            descriptions.add(descriptionElement.getText());
        }
        return descriptions;
    }

    /**
     * 找到tag的描述
     *
     * @param docComment 注释
     * @param tagName    tag名字
     * @param paramName  参数名字
     * @return tag描述
     */
    @NotNull
    public static String findTagDesc(PsiDocComment docComment, String tagName, String paramName) {
        Map<String, List<JavaTag>> javaTagMap = getJavaTag(docComment);

        // 找
        List<JavaTag> javaTags = javaTagMap.get(tagName);

        if (!ArrayUtil.isEmpty(javaTags)) {
            for (JavaTag javaTag : javaTags) {
                if (paramName.equals(javaTag.getValue())) {
                    return javaTag.getDesc();
                }
            }
        }

        return "";
    }

    /**
     * psiDocComment -> javaTag
     *
     * @param docComment psiTag
     * @return Map<String, List < JavaTag>> key=tagName
     */
    @NotNull
    public static Map<String, List<JavaTag>> getJavaTag(@NotNull PsiDocComment docComment) {
        HashMap<String, List<JavaTag>> tagMap = Maps.newHashMap();

        for (PsiDocTag tag : docComment.getTags()) {
            String name = tag.getName();
            tagMap.computeIfAbsent(name, v -> Lists.newArrayList())
                    // 组装JavaTag
                    .add(createJavaTag(tag));
        }

        return tagMap;
    }

    /**
     * 创建JavaTag
     *
     * @param psiDocTag psiTag
     * @return JavaTag
     */
    public static JavaTag createJavaTag(@NotNull PsiDocTag psiDocTag) {
        JavaTag javaTag = new JavaTag();
        javaTag.setName(psiDocTag.getName());
        for (PsiElement dataElement : psiDocTag.getDataElements()) {
            // 引用
            if (dataElement instanceof PsiDocParamRef) {
                javaTag.setValue(dataElement.getText());
            }
            // 描述
            if (dataElement instanceof PsiDocComment) {
                javaTag.setDesc(dataElement.getText());
            }
        }

        return javaTag;
    }
}
