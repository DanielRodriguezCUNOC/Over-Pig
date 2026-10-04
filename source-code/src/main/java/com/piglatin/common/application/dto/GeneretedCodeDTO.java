package com.piglatin.common.application.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains the C3D code an C executable code
 */
@Getter
@Setter
public class GeneretedCodeDTO {
    private String c3dCode;
    private String cCode;
    private String entryPoint;

    public GeneretedCodeDTO() {
    }

    public GeneretedCodeDTO(String c3dCode, String cCode, String entryPoint) {
        this.c3dCode = c3dCode;
        this.cCode = cCode;
        this.entryPoint = entryPoint;
    }
}
