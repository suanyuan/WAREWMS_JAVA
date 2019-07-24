package com.wms.result;

public class PdaResult {

    /**
     * 请求成功
     */
    public final static int CODE_SUCCESS = 200;

    /**
     * 请求失败
     */
    public final static int CODE_FAILURE = 400;

    /**
     * PDA 请求过来，try catch到的错误，需要定义个包含的字符串
     */
    public final static String PDA_FAILURE_IDENTIFIER = "##@@**@@##";

    private int errorCode;

    private String msg;

    public PdaResult() {
    }

    public PdaResult(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
