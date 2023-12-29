package com.yj.oa.project.controller;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.mapper.MilestoneMapper;
import com.yj.oa.project.mapper.SubtaskMapper;
import com.yj.oa.project.mapper.ThtaskMapper;
import com.yj.oa.project.po.Milestone;
import com.yj.oa.project.po.Subtask;
import com.yj.oa.project.po.Thtask;
import com.yj.oa.project.service.milestone.IMilestoneService;
import com.yj.oa.project.service.subtask.ISubtaskService;
import com.yj.oa.project.service.thtask.IThtaskService;
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

import java.text.DecimalFormat;
import java.util.*;

/**
 *
 */
@Controller
@RequestMapping("/thtask")
public class ThtaskController extends BaseController{
    private final static String prefix = "system/thtask";

    private static final Logger log = LoggerFactory.getLogger(UploadCertificateController.class);

    @Autowired
    IThtaskService iThtaskService;

    @Autowired
    IUserService iUserService;
    @Autowired
    ISubtaskService iSubtaskService;

    @Autowired
    IMilestoneService iMilestoneService;


    @Autowired
    SubtaskMapper subtaskMapper;

    @Autowired
    ThtaskMapper thtaskMapper;

    @Autowired
    MilestoneMapper milestoneMapper;



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
        return prefix + "/thtask_unfinished";
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
        return prefix + "/thtask";
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
    public AjaxResult updateComplete(Thtask subtask)
    {

        Thtask t=iThtaskService.selectByPrimaryKey(subtask.getId());
        Thtask t_t=new Thtask();
        Subtask s = iSubtaskService.selectByPrimaryKey(t.getSubtaskId());
        t_t.setSubtaskId(t.getSubtaskId());

        //查找该个人任务所属部门任务下的所有个人任务，并将它们的工时加总
        List<Thtask> thtasks = thtaskMapper.selectThtaskList(t_t);
        Float st_worktime = (float)0;
        for(Thtask ts:thtasks){
            st_worktime=st_worktime+ts.getworktime();
        }
        //计算该部门任务的新进度，并在数据库中更新
        Float p = t.getworktime()/st_worktime;
        s.setProgress(s.getProgress()+p);
        subtaskMapper.updateByPrimaryKeySelective(s);
       subtask.setIsComplete(CsEnum.scheduled.SCHEDULE_YES_COMPLETE.getValue());

       //查找该个人任务所属里程碑下的所有个人任务，并将它们的工时加总
       Milestone milestone = iMilestoneService.selectByPrimaryKey(s.getMilestoneId());
       Subtask s_s = new Subtask();
       s_s.setMilestoneId(s.getMilestoneId());
       List<Subtask> subtasks = iSubtaskService.selectSubtaskList(s_s);
       Float mt_worktime = (float)0;
        Thtask t_thtask = new Thtask();
       for(Subtask st : subtasks){
           t_thtask.setSubtaskId(st.getId());
           List<Thtask> t_thtasks = iThtaskService.selectThtaskList(t_thtask);
           for(Thtask q :t_thtasks){
               mt_worktime=mt_worktime+q.getworktime();
           }
       }
       //计算该里程碑的新进度，并在数据库中更新
       Float m_progress = t.getworktime()/mt_worktime;
       milestone.setProgress(milestone.getProgress()+m_progress);
       milestoneMapper.updateByPrimaryKeySelective(milestone);
        return result(iThtaskService.updateComplete(subtask));
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
        List<Thtask> subtasks = iThtaskService.selectThtaskList(new Thtask());
        for (Thtask s : subtasks)
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
        Thtask subtask = new Thtask();
        //查询未完成的
        subtask.setIsComplete(CsEnum.scheduled.SCHEDULE_NO_COMPLETE.getValue());
        subtask.setStartTime(DateUtils.StrToDate(date));
        System.out.println(DateUtils.DateToSTr(DateUtils.StrToDate(date)));
        List<Thtask> subtasks = iThtaskService.selectThtaskList(subtask);
        model.addAttribute("thtasks", subtasks);
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
    public TableDataInfo tableList_unfinished(Thtask thtask)
    {
        startPage();


        List<Thtask> thtasks = iThtaskService.selectMyThtaskList(getUserId());
        for(Thtask   ms : thtasks){
        Subtask subtask = iSubtaskService.selectByPrimaryKey(ms.getSubtaskId());
        ms.setSubtask(subtask);}
        return getDataTable(thtasks);
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
    public TableDataInfo tableList(Thtask thtask)
    {
        startPage();


        List<Thtask> thtasks = iThtaskService.selectMyThtaskList_all(getUserId());
        log.info("哈哈哈哈哈哈哈哈哈哈哈哈：");
        for(Thtask ms : thtasks){
            Subtask subtask= iSubtaskService.selectByPrimaryKey(ms.getSubtaskId());
            ms.setSubtask(subtask);

        }
        return getDataTable(thtasks);


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


        List<Subtask> subtasks = iSubtaskService.selectSubtaskList(new Subtask());
        model.addAttribute("Subtasks", subtasks);return prefix + "/add";
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
    public AjaxResult addSave(Thtask program) throws Exception
    {


        program.setCreateBy(getUserId());
        program.setCreateTime(new Date());




        //把自己带上



        return result(iThtaskService.insertSelective(program));


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
        Thtask  thtask = iThtaskService.selectByPrimaryKey(id);


        List<Subtask> subtasks = iSubtaskService.selectSubtaskList(new Subtask());

        model.addAttribute("thtask", thtask);
        model.addAttribute("Subtasks", subtasks);
        model.addAttribute("worktime", thtask.getworktime());

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
    public Thtask selectById(@PathVariable("id") Integer id)
    {
        return iThtaskService.selectByPrimaryKey(id);
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
    public AjaxResult editSave(Thtask program)
    {
        program.setCreateBy(getUserId());
        return result(iThtaskService.updateByPrimaryKeySelective(program));
    }



}
