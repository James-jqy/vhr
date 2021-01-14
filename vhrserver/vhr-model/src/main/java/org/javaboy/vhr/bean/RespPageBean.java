package org.javaboy.vhr.bean;

import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @Classname RespPageBean
 * @Description TODO
 * @Date 2020/12/23 18:52
 * @Created by Jieqiyue
 */
public class RespPageBean {
    private long total;
    private List<?> data;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
