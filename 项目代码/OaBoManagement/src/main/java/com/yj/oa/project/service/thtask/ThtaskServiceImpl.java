package com.yj.oa.project.service.thtask;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.controller.UploadDesignController;
import com.yj.oa.project.mapper.SubtaskMapper;
import com.yj.oa.project.mapper.SubtaskUserMapper;
import com.yj.oa.project.mapper.ThtaskMapper;
import com.yj.oa.project.mapper.ThtaskUserMapper;
import com.yj.oa.project.po.Subtask;
import com.yj.oa.project.po.SubtaskUser;
import com.yj.oa.project.po.Thtask;
import com.yj.oa.project.po.ThtaskUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ThtaskServiceImpl implements IThtaskService {
    @Autowired
    ThtaskMapper thtaskMapper;

    @Autowired
    ThtaskUserMapper thtaskUserMapper;

    private static final Logger log = LoggerFactory.getLogger(UploadDesignController.class);

    /**
     *
     * @描述: 批量删除
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] id)
    {
        //删除中间表

        return thtaskMapper.deleteByPrimaryKeys(id);
    }





    /**
     *
     * @描述: 添加
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
     */
    @Override
    public int insertSelective(Thtask record)
    {
        int i = thtaskMapper.insertSelective(record);


        return i;
    }


    /**
     *
     * @描述: 主键查询
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
     */
    @Override
    public Thtask selectByPrimaryKey(Integer id)
    {

        return thtaskMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述: 修改
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:45
     */
    @Override
    public int updateByPrimaryKeySelective(Thtask record)
    {
        //删除原有的



        return thtaskMapper.updateByPrimaryKeySelective(record);
    }



    /**
     *
     * @描述:  修改日程工作完成状态
     *
     * @params:
     * @return:
     * @date: 2018/9/29 14:02
    */
    public int updateComplete(Thtask subtask){
        return thtaskMapper.updateByPrimaryKeySelective(subtask);
    }





    /**
     *
     * @描述: 列表
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:50
     */
    @Override
    public List<Thtask> selectThtaskList(Thtask subtask)
    {
        return thtaskMapper.selectThtaskList(subtask);
    }


    /**
     *
     * @描述:  我的项目
     *
     * @params:
     * @return:
     * @date: 2018/9/28 12:18
     */
    @Override
    public List<Thtask> selectMyThtaskList(String uid)
    {
        return thtaskMapper.selectMyThtaskList(uid);
    }

    /**
     *
     * @描述:  我的项目
     *
     * @params:
     * @return:
     * @date: 2018/9/28 12:18
     */
    @Override
    public List<Thtask> selectMyThtaskList_all(String uid)
    {
        return thtaskMapper.selectMyThtaskList_all(uid);
    }

    private static List<ThtaskUser>  getListThtaskList(Integer sId, String[] userIds)
    {
        List<ThtaskUser> objects = new ArrayList<>();
        for (String id:userIds)
        {
            ThtaskUser thtaskUser = new ThtaskUser();
            thtaskUser.setsId(sId);
            thtaskUser.setSuId(id);
            objects.add(thtaskUser);
        }
        return objects;
    }
}

