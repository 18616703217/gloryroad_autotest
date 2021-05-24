package com.gloryroad.demo.Vo;

import com.gloryroad.demo.constant.ResCode;

public class ResponseModel<T> {
    private boolean ret;
    private String errmsg;
    private int errcode;
    private T data;

    private ResponseModel(T t) {
        this.ret = true;
        this.data = t;
        this.errcode = ResCode.C0;
    }

    public ResponseModel() {
    }

    private ResponseModel(String errmsg, T t) {
        this.ret = false;
        this.errmsg = errmsg;
        this.data = t;
        this.errcode = -1;
    }

    private ResponseModel(int errcode, String errmsg, T t) {
        this.ret = false;
        this.errmsg = errmsg;
        this.errcode = errcode;
        this.data = t;
    }

    public static <T> ResponseModel<T> returnDefault(int errcode,String errmsg,T t) {
        if(errcode != ResCode.C0){
            return new ResponseModel(errcode,errmsg, null);
        }else{
            return new ResponseModel(t);
        }
    }

    public static <T> ResponseModel<T> returnSuccess() {
        return new ResponseModel((Object)null);
    }

    public static <T> ResponseModel<T> returnSuccess(T t) {
        return new ResponseModel(t);
    }

    public static <T> ResponseModel<T> returnFail(String errmsg) {
        return new ResponseModel(errmsg, (Object)null);
    }

    public static <T> ResponseModel<T> returnFail(String errmsg, T t) {
        return new ResponseModel(errmsg, t);
    }

    public static <T> ResponseModel<T> returnFail(int errcode, String errmsg) {
        return new ResponseModel(errcode, errmsg, (Object)null);
    }

    public static <T> ResponseModel<T> returnFail(int errcode, String errmsg, T t) {
        return new ResponseModel(errcode, errmsg, t);
    }
    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "ResponseModel [ret=" + this.ret + ", errmsg='" + this.errmsg + '\'' + ", errcode=" + this.errcode + ", data=" + this.data + ']';
    }
}
