package org.javaboy.vhr.bean;

import java.io.Serializable;

/**
 * @Classname Meta
 * @Description TODO
 * @Date 2020/12/12 14:20
 * @Created by Jieqiyue
 */
public class Meta implements Serializable {
    private Boolean keepAlive;

    private Boolean requireAuth;

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(Boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
