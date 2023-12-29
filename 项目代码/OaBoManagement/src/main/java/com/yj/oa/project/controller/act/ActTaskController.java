package com.yj.oa.project.controller.act;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.controller.UploadCertificateController;
import com.yj.oa.project.po.*;
import com.yj.oa.project.service.ACT.applyRoom.IActApplyRoomFormService;
import com.yj.oa.project.service.ACT.task.IActTaskService;
import com.yj.oa.project.service.leavForm.ILeavFormService;
import com.yj.oa.project.service.sample.ISampleService;
import com.yj.oa.project.service.user.IUserService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("/task")
public class ActTaskController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(UploadCertificateController.class);

    private String prefix = "system/actTask/";

    @Autowired
    IActTaskService iacttaskService;

    @Autowired
    ILeavFormService iLeavFormService;
    @Autowired
    IUserService iUserService;

    @Autowired
    ISampleService iSampleService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    HistoryService historyService;

    @Autowired
    TaskService taskService;

    @Autowired
    IActApplyRoomFormService iActApplyRoomFormService;

    @Autowired
    IActApplyRoomFormService iact_applyRoomFormService;


    /**
     *
     * @描述 跳转到任务列表页面
     *
     * @date 2018/9/22 11:00
     */
    @RequestMapping("/toMyTaskList")
    public String toTask()
    {
        return prefix + "actTask";
    }


    /**
     *
     * @描述: 任务表格数据
     *
     * @params:
     * @return:
     * @date: 2018/9/24 17:26
     */
    @RequestMapping("/TableMyTasklList")
    @ResponseBody
    public TableDataInfo taskList(ActTask actTask)
    {
        startPage(); //20180909000000_111  20180914-2
        actTask.setAssignee(getUserId());
        List<ActTask> actTasks = iacttaskService.selectACTTaskList(actTask);
        for (ActTask task : actTasks)
        {
            User user = iUserService.selectByPrimaryKey(task.getAssignee());
            task.setAssignee(user.getName());




        }
        return getDataTable(actTasks);
    }


    /**
     *
     * @描述 查看申请表单内容
     *
     * @date 2018/9/22 12:48
     */
    @RequestMapping("/edit/{formKey}/{procInstId}/{taskId}")
    @Operlog(modal = "流程审批",descr = "查看待审表单信息")
    public String edit(@PathVariable("formKey") String formId,
                       @PathVariable("taskId") String taskId,
                       @PathVariable("procInstId") String procInstId,

                       Model model)
    {

        log.info("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");

        //判断任务审批是请假类型 还是 会议室申请 通过 BusinessKey来判断
        HistoricProcessInstance result = historyService.createHistoricProcessInstanceQuery().
                processInstanceId(procInstId).singleResult();

        String key = result.getBusinessKey();

        if (key.indexOf("0") == 0)
        {
            //会议室申请
            ApplyRoomForm applyRoomForm = iActApplyRoomFormService.selectByPrimaryKey(formId);
            User user = iUserService.selectByPrimaryKey(applyRoomForm.getProposer_Id());
            applyRoomForm.setProposer_Id(user.getName());
            model.addAttribute("Form", applyRoomForm);
            model.addAttribute("taskId", taskId);
            return prefix + "editApplyRoomForm";
        }else{

        //请假申请
        LeaveForm leaveForm = iLeavFormService.selectByPrimaryKey(Integer.valueOf(formId));
        System.out.println(leaveForm);
        User user = iUserService.selectByPrimaryKey(leaveForm.getProposer_Id());

        Sample sample= iSampleService.selectByPrimaryKey(leaveForm.getTitle()) ;

        leaveForm.setProposer_Id(user.getName());
        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("Form", leaveForm);
        model.addAttribute("taskId", taskId);
        model.addAttribute("users", users);
        model.addAttribute("user", user);
        model.addAttribute("sample", sample);
        log.info("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"+sample.getTitle());

        if(leaveForm.getIsApply()==1){
        return prefix + "editLeaveForm";}
        return prefix+"editReturnForm";}
    }


    /**
     *
     * @描述 compelete 会议申请 审批
     *
     * @date 2018/9/22 12:52
     */
    @RequestMapping("/RoomApproval")
    @ResponseBody
    @Operlog(modal = "流程审批",descr = "会议室审批")
    public AjaxResult approval(ApplyRoomForm applyRoomForm, String taskId)
    {
        try
        {
            iacttaskService.RoomApproval(applyRoomForm, taskId);

        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     *
     * @描述 compelete 请假申请 审批
     *
     * @date 2018/9/22 12:52
     */
    @RequestMapping("/LeaveApproval")
    @Operlog(modal = "流程审批",descr = "请假审批")
    @ResponseBody
    public AjaxResult LeaveApproval(LeaveForm leaveForm, String taskId)
    {

        try
        {
            int i=iacttaskService.LeaveApproval(leaveForm, taskId);
            log.info("嘿嘿嘿嘿嘿嘿嘿嘿嘿："+leaveForm.getIsApply());
            if(i==1){
                return success();
            }
            else{
                return error("库存不足！");
            }



        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }




    }


    /**
     *
     * @描述: 删除待审任务
     *
     * @params:
     * @return:
     * @date: 2018/9/26 15:01
     */
    @RequestMapping("/del")
    @Operlog(modal = "流程审批",descr = "删除待审任务")
    @ResponseBody
    public AjaxResult del(String[] ids)
    {
        return result(iacttaskService.deletByProcInstS(ids));
    }


}
