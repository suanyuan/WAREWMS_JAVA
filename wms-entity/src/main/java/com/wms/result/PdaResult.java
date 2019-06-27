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

    private int errorCode;

    private String msg;

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
