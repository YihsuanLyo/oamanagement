package com.yj.oa.project.service.Program;

import com.yj.oa.project.po.Program;
import com.yj.oa.project.po.Schedule;

import java.util.List;

/**
 *
 */
public interface IProgramService {
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Program record, String[] userIds);

    Program selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Program record, String[] userIds);

    List<Program> selectProgramList(Program program);

    List<Program> selectMyProgramList(String uId);

    int updateComplete(Program program);
}
