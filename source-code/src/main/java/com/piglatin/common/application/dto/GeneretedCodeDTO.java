package com.piglatin.common.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Código generado: C3D y RISC-V
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneretedCodeDTO {
    private String c3dCode;
    /** Ensamblador RISC-V producido por C3DToRiscVConverter */
    private String asmCode;
    private String entryPoint;
    /** Ruta del archivo .3d exportado */
    private String c3dFilePath;
    /** Ruta del archivo .asm exportado */
    private String asmFilePath;

}
