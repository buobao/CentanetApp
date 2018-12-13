package com.cetnaline.findproperty.model.network.bean;

public class BaseResponseBean<T> {
    //请求成功
    public static final int SUCCESS_CODE = 0;
    //请求成功但接口业务处理失败
    public static final int FAILE_CODE = -1;
    //请求接口异常
    public static final int REQUEST_ERROR_CODE = -2;
    //请求超时
    public static final int REQUEST_OVERTIME_CODE = -3;
    //服务器无法连接
    public static final int REQUEST_NOT_CONNECTION_CODE = -4;

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
