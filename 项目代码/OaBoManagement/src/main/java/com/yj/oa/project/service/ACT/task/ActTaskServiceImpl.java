package com.yj.oa.project.service.ACT.task;


import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.project.controller.UploadProductController;
import com.yj.oa.project.mapper.*;
import com.yj.oa.project.po.*;
import com.yj.oa.project.service.ACT.actUtil.ActUtil;
import com.yj.oa.project.service.leavForm.ILeavFormService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import com.yj.oa.project.service.sample.ISampleService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class ActTaskServiceImpl implements IActTaskService{

    private static final Logger log = LoggerFactory.getLogger(UploadProductController.class);

    @Autowired
    ActTaskMapper act_taskMapper;

    @Autowired
    MeetingRoomMapper  meetingRoomMapper;

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    LeaveFormMapper leaveFormMapper;

    @Autowired
    SampleMapper sampleMapper;

    @Autowired
    ISampleService iSampleService;


    @Autowired
    ActHiActinstMapper actHiActinstMapper;

    @Autowired
    ActHiTaskInstMapper actHiTaskInstMapper;


    @Autowired
    ApplyRoomFormMapper applyRoomFormMapper;


    @Autowired
    HistoryService historyService;

    @Autowired
    ActHiProcinstMapper actHiProcinstMapper;
    @Autowired
    MeetingRoomMapper iMeetingRoomService;

    /**
     *
     * @描述 查询当前人的任务列表
     *
     * @date 2018/9/21 22:49
     */
    @Override
    public List<ActTask> selectACTTaskList(ActTask record)
    {
        return act_taskMapper.selectACTTaskList(record);
    }

    /**
     *
     * @描述: 请假审批
     *
     * @params:
     * @return:
     * @date: 2018/9/24 17:21
     */
    @Override
    public int LeaveApproval(LeaveForm leaveForm, String taskId)
    {

        log.info("哦哦哦哦哦噢噢噢噢哦哦哦哦哦哦哦哦："+leaveForm.getIsApply());
        String status = String.valueOf(leaveForm.getStatus());

        //同意
        if (CsEnum.leavForm.Leave_status_succe.getValue().equals(status))
        {

            Sample sample = iSampleService.selectByPrimaryKey(leaveForm.getTitle());
            Integer i=sample.getResidues()-leaveForm.getLeaveType();
            Integer j=sample.getResidues()+leaveForm.getLeaveType();
            if(i>=0){
            Map<String, Object> map = new HashMap<>();
            map.put(CsEnum.activiti.Leave_FLAG.getValue(), CsEnum.activiti.Leave_FLAG_FALSE.getValue());
            //任务完成，流程结束
            taskService.complete(taskId, map);
            //修改表单状态
            leaveForm.setStatus(2);
            //修改样品余量
            if(leaveForm.getIsApply()==1){
            sample.setResidues(i);}
            else{
                sample.setResidues(j);
            }
            iSampleService.updateByPrimaryKeySelective(sample);
            leaveFormMapper.updateByPrimaryKeySelective(leaveForm);
            return 1;}
            else{
                return 0;

            }




        }
        else
        {
            //拒绝请假，结束任务
            Map<String, Object> map = new HashMap<>();
            map.put(CsEnum.activiti.Leave_FLAG.getValue(), CsEnum.activiti.Leave_FLAG_FALSE.getValue());
            //拒绝审批
            taskService.complete(taskId, map);
            //修改请假表单状态
            leaveFormMapper.updateByPrimaryKeySelective(leaveForm);
            return 1;
        }
    }

    /**
     *
     * @描述: 删除待审任务
     *
     * @params:
     * @return:
     * @date: 2018/9/26 14:50
     */
    @Override
    public int deletByProcInstS(String[] ids)
    {

        //1.删除活动历史表 act_hi_actinst 数据
        actHiActinstMapper.deleteByProcInstId(ids);

        //2.删除历史任务表 act_hi_taskinst 数据
        actHiTaskInstMapper.deleteByprocInstIds(ids);

        //3.删除表单数据
        String formkey="";
        MeetingRoom meetingRoom = new MeetingRoom();
        for (String id : ids)
        {

            HistoricProcessInstance result = historyService.createHistoricProcessInstanceQuery().
                    processInstanceId(id).singleResult();
            formkey=result.getBusinessKey();
            //判断是哪个申请流程 （请假，会议室）
            if (formkey.indexOf("0") == 0)
            {
                //删除待审任务，先恢复会议室申请状态
                ApplyRoomForm applyRoomForm = applyRoomFormMapper.selectByPrimaryKey(ActUtil.getFormKeyFromHi(formkey));
                meetingRoom.setMeetRoomName(applyRoomForm.getRoomName());
                meetingRoom.setStatus(CsEnum.meetRoom.MEET_ROOM_STATUS_FREE.getValue());
                meetingRoomMapper.updateByRoomName(meetingRoom);

                applyRoomFormMapper.deleteByprocInstIds(ids);
            }
            else
            {
                leaveFormMapper.deleteByprocInstIds(ids);
            }
        }

        //5.删除任务表 act_hi_proceInst 数据
        act_taskMapper.deletByProcInstS(ids);
        //5.删除历史进程表 act_hi_proceInst 数据
        return actHiProcinstMapper.deleteByPrimaryKeys(ids);

    }


    /**
     *
     * @描述: 会议室审批
     *
     * @params:
     * @return:
     * @date: 2018/9/24 17:21
     */

    public void RoomApproval(ApplyRoomForm applyRoomForm, String taskId) throws Exception
    {
        //进行任务审批
        try
        {
            taskService.complete(taskId);
        }
        catch (Exception e)
        {
            throw new Exception("操作失败！");
        }


        //修改预约信息的表单 和 会议室的状态

        /** 0:空闲；1：预约中；2：使用中 */
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setMeetRoomName(applyRoomForm.getRoomName());

        Integer isAgree = applyRoomForm.getStatus();


        if (isAgree == CsEnum.ApplyRoomForm.APPLY_STATUS_AGREE.getValue())
        {
            // 设为使用中 描述 房间状态情况 0：空闲，1：预约中 2：使用中，3停用
            meetingRoom.setStatus(CsEnum.meetRoom.MEET_ROOM_STATUS_USING.getValue());
        }
        else
        {
            // 不同意 设为0
            meetingRoom.setStatus(CsEnum.meetRoom.MEET_ROOM_STATUS_FREE.getValue());
        }

        applyRoomForm.setEndTime(new Date());
        applyRoomFormMapper.updateByPrimaryKeySelective(applyRoomForm);
        iMeetingRoomService.updateByRoomName(meetingRoom);
    }


    /**
     *
     * @描述 :通过任务ID获取当前节点实例信息
     *
     * @params:
     *
     * @return：
     *
     * @date： 2018/9/23 12:29
     */
    @Override
    public Map<String, Object> getCurrentView(String taskId)
    {

        Map<String, Object> map = new HashMap<>();

        //根据任务Id 获取任务对象 （创建查询对象，通过id查询 返回唯一结果）
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        // 获取流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        //流程实例Id
        String processInstanceId = task.getProcessInstanceId();


        // 根据流程定义Id获取流程对象  创建流程定义查询 并且根据流程定义id查询 获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();


        //根据流程实例Id获取流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(
                processInstanceId).singleResult();


        //活动Id
        String activityId = processInstance.getActivityId();
        //根据活动Id获取活动对象
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(
                processDefinitionId);
        //流程活动
        ActivityImpl activity = processDefinitionEntity.findActivity(activityId);

        //任务
        map.put("task", task);
        //流程定义
        map.put("processDefinition", processDefinition);
        //流程实例
        map.put("processInstance", processInstance);
        //流程活动
        map.put("activity", activity);

        return map;
    }
}
