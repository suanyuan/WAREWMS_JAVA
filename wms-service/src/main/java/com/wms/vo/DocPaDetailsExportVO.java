package com.wms.vo;

import com.wms.entity.DocPaDetails;
import lombok.Data;

@Data
public class DocPaDetailsExportVO extends DocPaDetails {

    private String reservedfield01;

    private String descrc;

    private String lotatt01;

    private String lotatt02;

    private String lotatt04;

    private String lotatt05;

    private String lotatt07;

    private String locationid;
}
