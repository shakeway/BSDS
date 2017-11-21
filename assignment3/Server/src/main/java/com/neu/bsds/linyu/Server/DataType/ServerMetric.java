package com.neu.bsds.linyu.Server.DataType;

/**
 * Created by linyuyu on 11/21/17.
 */
public class ServerMetric {

    private int responseTime;
    private int errorNum;
    private int flag; //0:post, 1:get

    public ServerMetric() {
    }

    public ServerMetric(int responseTime, int errorNum, int flag) {
        this.responseTime = responseTime;
        this.errorNum = errorNum;
        this.flag = flag;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}

