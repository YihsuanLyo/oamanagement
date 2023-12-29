package com.yj.oa.project.service.Program;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.ProgramMapper;
import com.yj.oa.project.mapper.ProgramUserMapper;
import com.yj.oa.project.mapper.ScheduleMapper;
import com.yj.oa.project.mapper.ScheduleUserMapper;
import com.yj.oa.project.po.Program;
import com.yj.oa.project.po.ProgramUser;
import com.yj.oa.project.po.Schedule;
import com.yj.oa.project.po.ScheduleUser;
import com.yj.oa.project.service.schedule.IScheduleService;
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
public class ProgramServiceImpl implements IProgramService{
    @Autowired
    ProgramMapper programMapper;

    @Autowired
    ProgramUserMapper programUserMapper;


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
        programUserMapper.deleteByPrimaryKeys(id);
        return programMapper.deleteByPrimaryKeys(id);
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
    public int insertSelective(Program record, String[] userIds)
    {
        int i = programMapper.insertSelective(record);
        if (!StringUtils.isEmpty(userIds) && userIds.length>0)
        {
            List<ProgramUser> listProgramList = getListProgramList(record.getId(),userIds);
            programUserMapper.insertSelective(listProgramList);
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
    public Program selectByPrimaryKey(Integer id)
    {

        return programMapper.selectByPrimaryKey(id);
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
    public int updateByPrimaryKeySelective(Program record,String[] userIds)
    {
        //删除原有的
        Integer[] ids={record.getId()};
        programUserMapper.deleteByPrimaryKeys(ids);

        if (!StringUtils.isEmpty(userIds) && userIds.length>0)
        {
            //插入新的
            List<ProgramUser> listProgramList = getListProgramList(record.getId(),userIds);
            programUserMapper.insertSelective(listProgramList);
        }

        return programMapper.updateByPrimaryKeySelective(record);
    }



    /**
     *
     * @描述:  修改日程工作完成状态
     *
     * @params:
     * @return:
     * @date: 2018/9/29 14:02
    */
    public int updateComplete(Program program){
        return programMapper.updateByPrimaryKeySelective(program);
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
    public List<Program> selectProgramList(Program program)
    {
        return programMapper.selectProgramList(program);
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
    public List<Program> selectMyProgramList(String uId)
    {
        return programMapper.selectMyProgramList(uId);
    }

    private static List<ProgramUser>  getListProgramList(Integer sId, String[] userIds)
    {
        List<ProgramUser> objects = new ArrayList<>();
        for (String id:userIds)
        {
            ProgramUser programUser = new ProgramUser();
            programUser.setsId(sId);
            programUser.setSuId(id);
            objects.add(programUser);
        }
        return objects;
    }
}

