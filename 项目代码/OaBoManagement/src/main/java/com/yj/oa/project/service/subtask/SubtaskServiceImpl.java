package com.yj.oa.project.service.subtask;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.*;
import com.yj.oa.project.po.Milestone;
import com.yj.oa.project.po.MilestoneUser;
import com.yj.oa.project.po.Subtask;
import com.yj.oa.project.po.SubtaskUser;
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
public class SubtaskServiceImpl implements ISubtaskService {
    @Autowired
    SubtaskMapper subtaskMapper;
    @Autowired
    ThtaskMapper thtaskMapper;

    @Autowired
    SubtaskUserMapper subtaskUserMapper;


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
        for(Integer i : id){
            thtaskMapper.deleteBySubtaskid(i);
        }
        subtaskUserMapper.deleteByPrimaryKeys(id);
        return subtaskMapper.deleteByPrimaryKeys(id);
    }

    /**
     *
     * @描述: 批量删除
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
     */
    @Override
    public int deleteByMilestoneid(Integer[] ids)
    {
        for(Integer id : ids){
            List<Subtask> subtasks = subtaskMapper.selectByMilestoneid(id);
            for (Subtask s : subtasks){
                thtaskMapper.deleteBySubtaskid(s.getId());

                Integer[] i = {s.getId()};
                subtaskUserMapper.deleteByPrimaryKeys(i);
            }
        }
        //删除中间表
        return(subtaskMapper.deleteByMilestoneid(ids));
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
    public int insertSelective(Subtask record, String[] userIds)
    {
        int i = subtaskMapper.insertSelective(record);
        if (!StringUtils.isEmpty(userIds) && userIds.length>0)
        {
            List<SubtaskUser> listSubtaskList = getListSubtaskList(record.getId(),userIds);
            subtaskUserMapper.insertSelective(listSubtaskList);
        }
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
    public Subtask selectByPrimaryKey(Integer id)
    {

        return subtaskMapper.selectByPrimaryKey(id);
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
    public int updateByPrimaryKeySelective(Subtask record,String[] userIds)
    {
        //删除原有的
        Integer[] ids={record.getId()};
        subtaskUserMapper.deleteByPrimaryKeys(ids);

        if (!StringUtils.isEmpty(userIds) && userIds.length>0)
        {
            //插入新的
            List<SubtaskUser> listSubtaskList = getListSubtaskList(record.getId(),userIds);
            subtaskUserMapper.insertSelective(listSubtaskList);
        }

        return subtaskMapper.updateByPrimaryKeySelective(record);
    }



    /**
     *
     * @描述:  修改日程工作完成状态
     *
     * @params:
     * @return:
     * @date: 2018/9/29 14:02
    */
    public int updateComplete(Subtask subtask){
        return subtaskMapper.updateByPrimaryKeySelective(subtask);
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
    public List<Subtask> selectSubtaskList(Subtask subtask)
    {
        return subtaskMapper.selectSubtaskList(subtask);
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
    public List<Subtask> selectMySubtaskList(String uid)
    {
        return subtaskMapper.selectMySubtaskList(uid);
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
    public List<Subtask> selectMySubtaskList_all(String uid)
    {
        return subtaskMapper.selectMySubtaskList_all(uid);
    }

    private static List<SubtaskUser>  getListSubtaskList(Integer sId, String[] userIds)
    {
        List<SubtaskUser> objects = new ArrayList<>();
        for (String id:userIds)
        {
            SubtaskUser subtaskUser = new SubtaskUser();
            subtaskUser.setsId(sId);
            subtaskUser.setSuId(id);
            objects.add(subtaskUser);
        }
        return objects;
    }
}

