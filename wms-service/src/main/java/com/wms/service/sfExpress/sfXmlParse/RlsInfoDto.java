package com.wms.service.sfExpress.sfXmlParse;

/**
 * 密单信息资料
 */
public class RlsInfoDto {

    private String codingMapping;
    private String codingMappingOut;
    private String destRouteLabel;
    private String printIcon;
    private String proCode;
    private String qrcode;
    private String sourceTransferCode;
    private String proName;

    private String returnQrcode;
    private String returnestRouteLabel;


    private String returnSourceTransferCode;
    private String returnCodingMapping;
    private String returnCodingMappingOut;


    public String getReturnCodingMapping() {
        return returnCodingMapping;
    }

    public void setReturnCodingMapping(String returnCodingMapping) {
        this.returnCodingMapping = returnCodingMapping;
    }

    public String getReturnCodingMappingOut() {
        return returnCodingMappingOut;
    }

    public void setReturnCodingMappingOut(String returnCodingMappingOut) {
        this.returnCodingMappingOut = returnCodingMappingOut;
    }

    public String getReturnSourceTransferCode() {
        return returnSourceTransferCode;
    }

    public void setReturnSourceTransferCode(String returnSourceTransferCode) {
        this.returnSourceTransferCode = returnSourceTransferCode;
    }

    public String getReturnestRouteLabel() {
        return returnestRouteLabel;
    }

    public void setReturnestRouteLabel(String returnestRouteLabel) {
        this.returnestRouteLabel = returnestRouteLabel;
    }

    public String getReturnQrcode() {
        return returnQrcode;
    }

    public void setReturnQrcode(String returnQrcode) {
        this.returnQrcode = returnQrcode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getCodingMapping() {
        return codingMapping;
    }

    public void setCodingMapping(String codingMapping) {
        this.codingMapping = codingMapping;
    }

    public String getCodingMappingOut() {
        return codingMappingOut;
    }

    public void setCodingMappingOut(String codingMappingOut) {
        this.codingMappingOut = codingMappingOut;
    }

    public String getDestRouteLabel() {
        return destRouteLabel;
    }

    public void setDestRouteLabel(String destRouteLabel) {
        this.destRouteLabel = destRouteLabel;
    }

    public String getPrintIcon() {
        return printIcon;
    }

    public void setPrintIcon(String printIcon) {
        this.printIcon = printIcon;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getSourceTransferCode() {
        return sourceTransferCode;
    }

    public void setSourceTransferCode(String sourceTransferCode) {
        this.sourceTransferCode = sourceTransferCode;
    }

    @Override
    public String toString() {
        return "RlsInfoDto{" +
                "codingMapping='" + codingMapping + '\'' +
                ", codingMappingOut='" + codingMappingOut + '\'' +
                ", destRouteLabel='" + destRouteLabel + '\'' +
                ", printIcon='" + printIcon + '\'' +
                ", proCode='" + proCode + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", sourceTransferCode='" + sourceTransferCode + '\'' +
                ", proName='" + proName + '\'' +
                '}';
    }
}
