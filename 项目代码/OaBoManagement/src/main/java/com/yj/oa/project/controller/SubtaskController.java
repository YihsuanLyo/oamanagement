package com.yj.oa.project.controller;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Milestone;
import com.yj.oa.project.po.Program;
import com.yj.oa.project.po.Subtask;
import com.yj.oa.project.service.Program.IProgramService;
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
@RequestMapping("/subtask")
public class SubtaskController extends BaseController{
    private final static String prefix = "system/subtask";

    private static final Logger log = LoggerFactory.getLogger(UploadCertificateController.class);

    @Autowired
    ISubtaskService iSubtaskService;

    @Autowired
    IUserService iUserService;
    @Autowired
    IMilestoneService iMilestoneService;



    /**
     *
     * @描述: 跳转到列表页
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:13
     */
    @RequestMapping("/tolist_unfinished")
    @RequiresPermissions("program:list")
    public String toList_unfinished()
    {
        return prefix + "/subtask_unfinished";
    }

    /**
     *
     * @描述: 跳转到列表页
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:13
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("program:list")
    public String toList()
    {
        return prefix + "/subtask";
    }


    /**
     *
     * @描述: 修改日程工作完成状态
     *
     * @params:
     * @return:
     * @date: 2018/9/29 14:00
     */
    @RequestMapping("/updateComplete")
    @RequiresPermissions("meet:update")
    @Operlog(modal = "日程管理",descr = "记录工作完成")
    @ResponseBody
    public AjaxResult updateComplete(Subtask subtask)
    {


       subtask.setIsComplete(CsEnum.scheduled.SCHEDULE_YES_COMPLETE.getValue());
        return result(iSubtaskService.updateComplete(subtask));
    }


    /**
     *
     * @描述: ajaxgetList
     *
     * @params:
     * @return:
     * @date: 2018/9/29 10:09
     */
    @RequestMapping("/ajaxgetMap")
    @ResponseBody
    public Map<String, String> ajaxgetMap()
    {
        Map<String, String> map = new HashMap<>();
        List<Subtask> subtasks = iSubtaskService.selectSubtaskList(new Subtask());
        for (Subtask s : subtasks)
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
     * @date: 2018/9/29 11:21
     */
    @RequestMapping("/editMore/{date}")
    @RequiresPermissions("meet:update")
    @Operlog(modal = "日程管理",descr = "查看当天日程安排")
    public String editMore(@PathVariable("date") String date, Model model)
    {
        Subtask subtask = new Subtask();
        //查询未完成的
        subtask.setIsComplete(CsEnum.scheduled.SCHEDULE_NO_COMPLETE.getValue());
        subtask.setStartTime(DateUtils.StrToDate(date));
        System.out.println(DateUtils.DateToSTr(DateUtils.StrToDate(date)));
        List<Subtask> subtasks = iSubtaskService.selectSubtaskList(subtask);
        model.addAttribute("subtasks", subtasks);
        return prefix + "/editMore";
    }


    /**
     *
     * @描述: 返回表格数据
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:15
     */
    @RequestMapping("/tableList_unfinished")
    @ResponseBody
    public TableDataInfo tableList_unfinished(Subtask subtask)
    {
        startPage();


        List<Subtask> subtasks = iSubtaskService.selectMySubtaskList(getUserId());
        for(Subtask ms : subtasks){
        Milestone milestone = iMilestoneService.selectByPrimaryKey(ms.getMilestoneId());
        ms.setMilestone(milestone);}
        return getDataTable(subtasks);
    }

    /**
     *
     * @描述: 返回表格数据
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:15
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo tableList(Subtask subtask)
    {
        startPage();


        List<Subtask> subtasks = iSubtaskService.selectMySubtaskList_all(getUserId());
        log.info("哈哈哈哈哈哈哈哈哈哈哈哈：");
        for(Subtask ms : subtasks){
            Milestone milestone= iMilestoneService.selectByPrimaryKey(ms.getMilestoneId());
            ms.setMilestone(milestone);

        }
        return getDataTable(subtasks);


    }


    /**
     *
     * @描述: 添加页面
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:15
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("meet:add")
    public String toAdd(Model model)
    {


        List<Milestone> milestones = iMilestoneService.selectMilestoneList(new Milestone());
        model.addAttribute("Milestones", milestones);return prefix + "/add";
    }

    /**
     *
     * @描述: 添加保存
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:16
     */
    @RequestMapping("/addSave")
    @RequiresPermissions("meet:add")
    @Operlog(modal = "项目管理",descr = "项目安排")
    @ResponseBody
    public AjaxResult addSave(Subtask program, String[] userIds) throws Exception
    {
        if (StringUtils.isEmpty(userIds))
    {
        return error("请选择项目成员！");
    }

        program.setCreateBy(getUserId());
        program.setCreateTime(new Date());
        log.info(
                "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈："+program.getMilestoneId()
        );



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
            return result(iSubtaskService.insertSelective(program, arrNew));
        }

        return result(iSubtaskService.insertSelective(program, userIds));


    }

    /**
     *
     * @描述: 删除
     *
     * @params:
     * @return:
     * @date: 2018/9/27 22:02
     */
    @RequestMapping("/del")
    @RequiresPermissions("meet:del")
    @Operlog(modal = "日程管理",descr = "日程删除")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        return result(iSubtaskService.deleteByPrimaryKeys(ids));
    }


    /**
     *
     * @描述: 编辑页面
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:17
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("meet:update")
    public String toEdit(@PathVariable("id") Integer id, Model model)
    {
        Subtask  subtask = iSubtaskService.selectByPrimaryKey(id);


        List<Milestone> milestones = iMilestoneService.selectMilestoneList(new Milestone());

        model.addAttribute("subtask", subtask);
        model.addAttribute("Milestones", milestones);
        model.addAttribute("worktime", subtask.getworktime());

        return prefix + "/edit";
    }


    /**
     *
     * @描述: 通过id获取单条
     *
     * @params:
     * @return:
     * @date: 2018/9/28 23:42
     */
    @RequestMapping("/selectById/{id}")
    @ResponseBody
    public Subtask selectById(@PathVariable("id") Integer id)
    {
        return iSubtaskService.selectByPrimaryKey(id);
    }


    /**
     *
     * @描述: 修改保存
     *
     * @params:
     * @return:
     * @date: 2018/9/27 21:01
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("meet:update")
    @Operlog(modal = "日程管理",descr = "修改日程安排")
    @ResponseBody
    public AjaxResult editSave(Subtask program, String[] userIds)
    {
        return result(iSubtaskService.updateByPrimaryKeySelective(program, userIds));
    }



}
