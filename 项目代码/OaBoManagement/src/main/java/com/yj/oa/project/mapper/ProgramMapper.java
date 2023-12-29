package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Program;
import com.yj.oa.project.po.Schedule;

import java.util.List;

public interface ProgramMapper {

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);

    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Program record);

    /**
     * 主键查询
     * @param id
     * @return
     */
    Program selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Program record);

    /**
     * 列表
     * @param program
     * @return
     */
    List<Program> selectProgramList(Program program);

    /**
     * 根据用户id获取个人会议
     * @param uId
     * @return
     */
    List<Program> selectMyProgramList(String uId);
}
