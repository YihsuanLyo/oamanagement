package com.yj.oa.project.service.Minutes;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.MinutesMapper;
import com.yj.oa.project.mapper.UserMinutesMapper;
import com.yj.oa.project.po.Minutes;
import com.yj.oa.project.po.UserMinutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Transactional
public class MinutesServiceImpl implements IMinutesService{

    @Autowired
    MinutesMapper minutesMapper;
    @Autowired
    UserMinutesMapper userMinutesMapper;

    /**
     *
     * @描述: 根据主键查询
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:06
     */
    @Override
    public Minutes selectByPrimaryKey(Integer id)
    {
        return minutesMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述: 批量删除
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:08
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] ids)
    {
        //删除中间表信息


        return minutesMapper.deleteByPrimaryKeys(ids);
    }


    /**
     *
     * @描述: 添加
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:09
     */
    @Override
    public int insertSelective(Minutes record)
    {
        int i = minutesMapper.insertSelective(record);




        return i;
    }


    /**
     *
     * @描述: 修改
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:09
     */
    @Override
    public int updateByPrimaryKeySelective(Minutes record)
    {
        //1.先删除原来的用户




        return minutesMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * @描述: 数据列表
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:11
     */
    @Override
    public List<Minutes> selectByMinutesList(Minutes minutes)
    {
        return minutesMapper.selectByMinutesList(minutes);
    }

    /**
     *
     * @描述:  我的会议 首页通知
     *
     * @params:
     * @return:
     * @date: 2018/9/28 12:18
    */
    @Override
    public List<Minutes> selectMyMinutesList(String uId)
    {
        return minutesMapper.selectMyMinutesList(uId);
    }


    public static List<UserMinutes> getListUserMinutes(Integer minutesId, String[] userIds)
    {
        List<UserMinutes> list = new ArrayList<>();
        if (!StringUtils.isEmpty(userIds) && userIds.length > 0)
        {
            for (String uId : userIds)
            {
                UserMinutes userMinutes = new UserMinutes();
                userMinutes.setMinutesId(minutesId);
                userMinutes.setUserId(uId);
                list.add(userMinutes);
            }
        }
        return list;
    }
}
