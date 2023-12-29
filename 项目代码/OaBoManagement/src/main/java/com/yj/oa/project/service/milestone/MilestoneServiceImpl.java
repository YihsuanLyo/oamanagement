package com.yj.oa.project.service.milestone;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.*;
import com.yj.oa.project.po.Milestone;
import com.yj.oa.project.po.MilestoneUser;
import com.yj.oa.project.po.Program;
import com.yj.oa.project.po.ProgramUser;
import com.yj.oa.project.service.subtask.ISubtaskService;
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
public class MilestoneServiceImpl implements IMilestoneService {
    @Autowired
    MilestoneMapper milestoneMapper;

    @Autowired
    MilestoneUserMapper milestoneUserMapper;

    @Autowired
    SubtaskMapper subtaskMapper;

    @Autowired
    ISubtaskService subtaskService;


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
        subtaskService.deleteByMilestoneid(id);
        milestoneUserMapper.deleteByPrimaryKeys(id);

        return milestoneMapper.deleteByPrimaryKeys(id);
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
    public int insertSelective(Milestone record, String[] userIds)
    {
        int i = milestoneMapper.insertSelective(record);
        if (!StringUtils.isEmpty(userIds) && userIds.length>0)
        {
            List<MilestoneUser> listMilestoneList = getListMilestoneList(record.getId(),userIds);
            milestoneUserMapper.insertSelective(listMilestoneList);
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
    public Milestone selectByPrimaryKey(Integer id)
    {

        return milestoneMapper.selectByPrimaryKey(id);
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
    public int updateByPrimaryKeySelective(Milestone record,String[] userIds)
    {
        //删除原有的
        Integer[] ids={record.getId()};
        milestoneUserMapper.deleteByPrimaryKeys(ids);

        if (!StringUtils.isEmpty(userIds) && userIds.length>0)
        {
            //插入新的
            List<MilestoneUser> listMilestoneList = getListMilestoneList(record.getId(),userIds);
            milestoneUserMapper.insertSelective(listMilestoneList);
        }

        return milestoneMapper.updateByPrimaryKeySelective(record);
    }



    /**
     *
     * @描述:  修改日程工作完成状态
     *
     * @params:
     * @return:
     * @date: 2018/9/29 14:02
    */
    public int updateComplete(Milestone program){
        return milestoneMapper.updateByPrimaryKeySelective(program);
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
    public List<Milestone> selectMilestoneList(Milestone program)
    {
        return milestoneMapper.selectMilestoneList(program);
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
    public List<Milestone> selectMyMilestoneList(String uid)
    {
        return milestoneMapper.selectMyMilestoneList(uid);
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
    public List<Milestone> selectMyMilestoneList_all(String uid)
    {
        return milestoneMapper.selectMyMilestoneList_all(uid);
    }

    private static List<MilestoneUser>  getListMilestoneList(Integer sId, String[] userIds)
    {
        List<MilestoneUser> objects = new ArrayList<>();
        for (String id:userIds)
        {
            MilestoneUser milestoneUser = new MilestoneUser();
            milestoneUser.setsId(sId);
            milestoneUser.setSuId(id);
            objects.add(milestoneUser);
        }
        return objects;
    }
}

