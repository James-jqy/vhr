package org.javaboy.vhr.mapper;

import org.apache.ibatis.annotations.Param;
import org.javaboy.vhr.bean.SysOperLog;
import java.util.List;

/**
 * @Classname ISysOperLogService
 * @Description TODO
 * @Date 2021/1/14 11:00
 * @Created by Jieqiyue
 */
public interface SysOperLogMapper
{
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public Integer insertOperlog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    public List<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public SysOperLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    public void cleanOperLog();

    /**
     * 获取所有操作日志的数量
     * @return
     */
    long getTotal();

    /**
     * 分页获取操作日志
     * @param page
     * @param size
     * @return
     */
    List<SysOperLog> getSysOperLogByPage(@Param("page") Integer page, @Param("size") Integer size);
}
