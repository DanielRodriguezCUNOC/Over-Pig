package com.piglatin.common.application.dto;

import lombok.Getter;

/**
 * This enum contains the type language supported
 */
@Getter
public enum LanguageType {

    PIGLATIN("pig"),
    Y("y"),
    ZETARIANO("z");

    private final String extension;
    LanguageType(String extension) {
        this.extension = extension;
    }

    public static LanguageType fromExtension(String extension) {
        for (LanguageType languageType : LanguageType.values()) {
            if (languageType.getExtension().equals(extension))
                return languageType;
        }
        throw new IllegalArgumentException("Extension " + extension + " not supported");
    }
}
