package com.yj.oa.project.controller;

import com.yj.oa.common.json.JSONObject;
import com.alibaba.fastjson.*;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.*;
import com.yj.oa.project.service.attendCount.IAttendCountService;
import com.yj.oa.project.service.milestone.IMilestoneService;
import com.yj.oa.project.service.subtask.ISubtaskService;
import com.yj.oa.project.service.thtask.IThtaskService;
import com.yj.oa.project.service.workTime.IWorkTimeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

/**
 *
 */
@Controller
@RequestMapping("/program/statistics")
public class StatisticsController extends BaseController{
    private String prefix = "system/statistics/";
    @Autowired
    IAttendCountService iAttendCountService;

    @Autowired
    IWorkTimeService iWorkTimeService;

    @Autowired
    IMilestoneService iMilestoneService;

    @Autowired
    ISubtaskService iSubtaskService;

    @Autowired
    IThtaskService iThtaskService;



    /**
     *
     * @描述: 跳转
     *
     * @params:
     * @return:
     * @date: 2018/10/2 18:12
     */

    @RequestMapping("/gantt/{id}")
    public String toGantt(@PathVariable("id") Integer programId, Model model)
    {
        model.addAttribute("programId", programId);
        return prefix + "gantt";
    }


    /**
     *
     * @描述: 返回表格数据
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:15
     */
    @RequestMapping("/gantt/tableList/{programId}")
    @ResponseBody
    public Map<String, Object> tableListGantt(@PathVariable("programId") Integer programId)
    {
        Map<String, Object> map = new HashMap<String,Object>();
        Milestone milestone = new Milestone();
        milestone.setprogramId(programId);
        List<Milestone> milestones = iMilestoneService.selectMilestoneList(milestone);
        List<String> categories = new ArrayList<String>();
        List<List<Float>> data = new ArrayList<List<Float>>();
        List<Float> list1 = new ArrayList<Float>();
        List<Float> list2 = new ArrayList<Float>();
        for(Milestone m : milestones){
            categories.add(m.getTitle());
            Subtask s_s = new Subtask();
            s_s.setMilestoneId(m.getId());
            List<Subtask> subtasks = iSubtaskService.selectSubtaskList(s_s);
            List<Float> list3 = new ArrayList<Float>();
            List<Float> list4 = new ArrayList<Float>();
            Float mt_worktime = (float)0;
            Thtask t_thtask = new Thtask();
            for(Subtask st : subtasks){
                Float st_worktime=(float)0;
                categories.add(st.getTitle());
                t_thtask.setSubtaskId(st.getId());
                List<Thtask> t_thtasks = iThtaskService.selectThtaskList(t_thtask);
                for(Thtask q :t_thtasks){
                    st_worktime=st_worktime+q.getworktime();
                    mt_worktime=mt_worktime+q.getworktime();
                }
                list3.add(st_worktime);
                list4.add(st.getProgress()* st_worktime);
            }
            list1.add(mt_worktime);
            for(Float i : list3){
                list1.add(i);
            }
            list2.add(m.getProgress()* mt_worktime);
            for(Float j : list4){
                list2.add(j);
            }
            }
        Collections.reverse(categories);
        Collections.reverse(list1);
        Collections.reverse(list2);
        data.add(list1);
        data.add(list2);


        //放到实体类中
        map.put("categories",categories);
        map.put("data",data);
        //转换为Json数据
        return map;

    }


    /**
     *
     * @描述 表格列表
     *
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/bar")
    @ResponseBody
    public String toBar(Model model)
    {

        return prefix + "bar";
    }


    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("attendCount:del")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        try
        {
            iAttendCountService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
    }



}
