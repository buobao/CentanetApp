package com.cetnaline.findproperty.model.network.bean;

public class BaseResponseBean<T> {

    public static final int SUCCESS_CODE = 0;

    public static final int FAILE_CODE = -1;

    public static final int REQUEST_ERROR_CODE = -2;

    public static final int REQUEST_OVERTIME_CODE = -3;

    private int ResultNo;

    private String Message;

    private int Total;

    public int getResultNo() {
        return ResultNo;
    }

    public void setResultNo(int resultNo) {
        ResultNo = resultNo;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    private T Result;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    public boolean isSuccess() {
        return this.ResultNo == SUCCESS_CODE;
    }

}
