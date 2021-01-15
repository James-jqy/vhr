package org.javaboy.vhr.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname SysOperLog
 * @Description TODO
 * @Date 2021/1/14 10:05
 * @Created by Jieqiyue
 */
public class SysOperLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 日志主键 */
    private Long operId;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    private Integer businessType;

    private String title;
    /** 请求方法 */
    private String method;

    /** 请求方式 */
    private String requestMethod;

    /** 操作人员 */
    private String operName;

    /** 部门名称 */
    private String deptName;

    /** 操作地点 */
    private String operLocation;

    /** 请求参数 */
    private String operParam;

    /** 返回参数 */
    private String jsonResult;

    /** 操作地址 */
    private String operIp;

    /** 操作状态（0正常 1异常） */
    private Integer status;

    /** 错误消息 */
    private String errorMsg;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperLocation() {
        return operLocation;
    }

    public void setOperLocation(String operLocation) {
        this.operLocation = operLocation;
    }

    public String getOperParam() {
        return operParam;
    }

    public void setOperParam(String operParam) {
        this.operParam = operParam;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    @Override
    public String toString() {
        return "SysOperLog{" +
                "operId=" + operId +
                ", businessType=" + businessType +
                ", title='" + title + '\'' +
                ", method='" + method + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", operName='" + operName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", operLocation='" + operLocation + '\'' +
                ", operParam='" + operParam + '\'' +
                ", jsonResult='" + jsonResult + '\'' +
                ", operIp='" + operIp + '\'' +
                ", status=" + status +
                ", errorMsg='" + errorMsg + '\'' +
                ", operTime=" + operTime +
                '}';
    }
}
