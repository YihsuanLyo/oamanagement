package com.yj.oa.project.controller;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.MeetingRoom;
import com.yj.oa.project.po.Milestone;
import com.yj.oa.project.po.Program;
import com.yj.oa.project.service.Minutes.IMinutesService;
import com.yj.oa.project.service.Program.IProgramService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import com.yj.oa.project.service.milestone.IMilestoneService;
import com.yj.oa.project.service.subtask.ISubtaskService;
import com.yj.oa.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 *
 */
@Controller
@RequestMapping("/milestone")
public class MilestoneController extends BaseController{
    private final static String prefix = "system/milestone";

    private static final Logger log = LoggerFactory.getLogger(UploadCertificateController.class);

    @Autowired
    IMilestoneService iMilestoneService;

    @Autowired
    IUserService iUserService;
    @Autowired
    IProgramService iProgramService;

    @Autowired
    ISubtaskService iSubtaskService;




    /**
     *
     * @描述: 跳转到列表页
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/tolist_unfinished")
    @RequiresPermissions("program:list")
    public String toList_unfinished()
    {
        return prefix + "/milestone_unfinished";
    }

    /**
     *
     * @描述: 跳转到列表页
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("program:list")
    public String toList()
    {
        return prefix + "/milestone";
    }


    /**
     *
     * @描述: 修改日程工作完成状态
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/updateComplete")
    @RequiresPermissions("program:update")
    @Operlog(modal = "日程管理",descr = "记录工作完成")
    @ResponseBody
    public AjaxResult updateComplete(Milestone milestone)
    {
       milestone.setIsComplete(CsEnum.scheduled.SCHEDULE_YES_COMPLETE.getValue());
        return result(iMilestoneService.updateComplete(milestone));
    }


    /**
     *
     * @描述: ajaxgetList
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/ajaxgetMap")
    @ResponseBody
    public Map<String, String> ajaxgetMap()
    {
        Map<String, String> map = new HashMap<>();
        List<Milestone> milestones = iMilestoneService.selectMilestoneList(new Milestone());
        for (Milestone s : milestones)
        {
            if (s.getIsComplete() == CsEnum.scheduled.SCHEDULE_NO_COMPLETE.getValue())
            {
                map.put(DateUtils.DateToSTr(s.getStartTime()).substring(0, 10), s.getTitle());
            }
        }
        return map;
    }


    /**
     *
     * @描述: 通过安排日期模糊查询出当天的安排
     *     返回多个数据
     * @params:
     * @return:
     *
     */
    @RequestMapping("/editMore/{date}")
    @RequiresPermissions("program:update")
    @Operlog(modal = "日程管理",descr = "查看当天日程安排")
    public String editMore(@PathVariable("date") String date, Model model)
    {
        Milestone program = new Milestone();
        //查询未完成的
        program.setIsComplete(CsEnum.scheduled.SCHEDULE_NO_COMPLETE.getValue());
        program.setStartTime(DateUtils.StrToDate(date));
        System.out.println(DateUtils.DateToSTr(DateUtils.StrToDate(date)));
        List<Milestone> programs = iMilestoneService.selectMilestoneList(program);
        model.addAttribute("milestones", programs);
        return prefix + "/editMore";
    }


    /**
     *
     * @描述: 返回表格数据
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/tableList_unfinished")
    @ResponseBody
    public TableDataInfo tableList_unfinished(Milestone milestone)
    {
        startPage();


        List<Milestone> milestones = iMilestoneService.selectMyMilestoneList(getUserId());
        for(Milestone ms : milestones){
        Program program = iProgramService.selectByPrimaryKey(ms.getprogramId());
        ms.setProgram(program);}
        return getDataTable(milestones);
    }

    /**
     *
     * @描述: 返回表格数据
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo tableList(Milestone milestone)
    {
        startPage();


        List<Milestone> milestones = iMilestoneService.selectMyMilestoneList_all(getUserId());
        for(Milestone ms : milestones){
            Program program = iProgramService.selectByPrimaryKey(ms.getprogramId());
            ms.setProgram(program);}
        return getDataTable(milestones);
    }


    /**
     *
     * @描述: 添加页面
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("program:add")
    public String toAdd(Model model)
    {


        List<Program> programs = iProgramService.selectProgramList(new Program());
        model.addAttribute("Programs", programs);return prefix + "/add";
    }

    /**
     *
     * @描述: 添加保存
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/addSave")
    @RequiresPermissions("program:add")
    @Operlog(modal = "项目管理",descr = "项目安排")
    @ResponseBody
    public AjaxResult addSave(Milestone program, String[] userIds) throws Exception
    {
        if (StringUtils.isEmpty(userIds))
    {
        return error("请选择项目成员！");
    }

        program.setCreateBy(getUserId());
        program.setCreateTime(new Date());



        //把自己带上
        boolean contains = Arrays.asList(userIds).contains(getUserId());
        if (!contains)
        {
            String[] arrNew = new String[userIds.length + 1];
            for (int i=0;i<userIds.length;i++)
            {
                arrNew[i]=userIds[i];
            }
            arrNew[arrNew.length-1]=getUserId();
            return result(iMilestoneService.insertSelective(program, arrNew));
        }

        return result(iMilestoneService.insertSelective(program, userIds));


    }

    /**
     *
     * @描述: 删除
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/del")
    @RequiresPermissions("program:del")
    @Operlog(modal = "日程管理",descr = "日程删除")
    @ResponseBody
    public AjaxResult del(Integer[] ids)

    {


        return result(iMilestoneService.deleteByPrimaryKeys(ids));
    }


    /**
     *
     * @描述: 编辑页面
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("program:update")
    public String toEdit(@PathVariable("id") Integer id, Model model)
    {
        Milestone milestone = iMilestoneService.selectByPrimaryKey(id);
        log.info("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈："+milestone.getCreateBy());
        log.info("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈："+getUserId());

        if(getUserId().equals(milestone.getCreateBy())){




        List<Program> programs = iProgramService.selectProgramList(new Program());

        model.addAttribute("milestone", milestone);
        model.addAttribute("Programs", programs);
        model.addAttribute("worktime", milestone.getworktime());

        return prefix + "/edit";}
        else{
            return "/error/unauth";
        }
    }


    /**
     *
     * @描述: 通过id获取单条
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/selectById/{id}")
    @ResponseBody
    public Milestone selectById(@PathVariable("id") Integer id)
    {
        return iMilestoneService.selectByPrimaryKey(id);
    }


    /**
     *
     * @描述: 修改保存
     *
     * @params:
     * @return:
     *
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("program:update")
    @Operlog(modal = "日程管理",descr = "修改日程安排")
    @ResponseBody
    public AjaxResult editSave(Milestone program, String[] userIds)
    {
        return result(iMilestoneService.updateByPrimaryKeySelective(program, userIds));
    }



}
