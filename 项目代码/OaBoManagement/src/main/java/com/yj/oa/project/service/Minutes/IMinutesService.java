package com.yj.oa.project.service.Minutes;

import com.yj.oa.project.po.Meet;
import com.yj.oa.project.po.Minutes;

import java.util.List;

/**
 *
 */
public interface IMinutesService {

    Minutes selectByPrimaryKey(Integer id);

    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Minutes record);

    int updateByPrimaryKeySelective(Minutes record);

    List<Minutes> selectByMinutesList(Minutes minutes);

    List<Minutes> selectMyMinutesList(String uId);
}
