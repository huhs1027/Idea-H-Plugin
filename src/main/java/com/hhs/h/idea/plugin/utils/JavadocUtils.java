package com.hhs.h.idea.plugin.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
}
