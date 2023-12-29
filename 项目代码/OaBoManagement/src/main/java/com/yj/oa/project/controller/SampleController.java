package com.yj.oa.project.controller;
import com.yj.oa.project.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yj.oa.common.exception.file.FileNameLengthException;
import com.yj.oa.common.exception.file.FileSizeException;
import com.yj.oa.common.utils.file.FileUtil;
import com.yj.oa.common.utils.file.UploadFile;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Sample;
import com.yj.oa.project.service.sample.ISampleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Program;
import com.yj.oa.project.po.Schedule;
import com.yj.oa.project.service.Program.IProgramService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import com.yj.oa.project.service.schedule.IScheduleService;
import com.yj.oa.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("/sample")
public class SampleController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(UploadChainController.class);
    private String prefix = "system/sample/";

    @Autowired
    ISampleService iSampleService;

    @Autowired
    IUserService iUserService;

    /**
     *
     * @描述 页面跳转
     *
     * @date 2018/9/16 10:59
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("file:list")
    public String tolist()
    {
        return prefix + "sample";
    }


    /**
     *
     * @描述 表格列表
     *
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList/{programId}")
    @ResponseBody
    public TableDataInfo listPag(@PathVariable("programId") Integer programId, Sample sample, Model model)
    {
        startPage();
        sample.setProgramId(programId);
        List<Sample> samples = iSampleService.selectSampleList(sample);
        for (Sample sample1 : samples)
        {
            User user = iUserService.selectByPrimaryKey(sample1.getCreateBy());
            sample1.setCreateBy(user.getName());
        }
        return getDataTable(samples);
    }


    /**
     *
     * @描述 上传文件页面
     *
     * @date 2018/9/16 11:37
     */

    @RequestMapping("/add/{programId}")
    @RequiresPermissions("file:upload")
    public String Add(@PathVariable("programId") Integer programId, Model model)
    {

        model.addAttribute("programId", programId);
        return prefix + "add";
    }


    /**
     *
     * @描述 执行保存操作
     *
     * @date 2018/9/16 11:54
     */

    @RequestMapping("/addSave")
    @RequiresPermissions("file:upload")
    @Operlog(modal = "样品管理",descr = "添加样品")
    @ResponseBody
    public AjaxResult addSample(Sample sample)
    {
        sample.setCreateBy(getUserId());
        sample.setCreateTime(new Date());
        return result(iSampleService.insertSelective(sample));
    }


    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("meet:del")
    @Operlog(modal = "样品管理",descr = "删除样品")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        try
        {
            iSampleService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
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
    public String toEdit(@PathVariable("id") Integer sampleId, Model model)
    {

        Sample sample = iSampleService.selectByPrimaryKey(sampleId);
        if (!sample.getCreateBy().equals(getUserId()))
        {
            return "/error/unauth";
        }




        model.addAttribute("Sample", sample);

        return prefix + "/edit";
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
    @RequiresPermissions("program:update")
    @Operlog(modal = "日程管理",descr = "修改日程安排")
    @ResponseBody
    public AjaxResult editSave(Sample sample)
    {
        return result(iSampleService.updateByPrimaryKeySelective(sample));
    }

    /**
     *
     * @描述: 领用样品跳转
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:17
     */
    @RequestMapping("/apply/{id}")
    @RequiresPermissions("meet:update")
    public String toApply(@PathVariable("id") Integer sampleId,Model model)
    {
        Sample sample = iSampleService.selectByPrimaryKey(sampleId);
        String agentid=sample.getCreateBy();
        String applyid=getUserId();

        model.addAttribute("sid", sampleId);
        model.addAttribute("sname",sample.getTitle());

        model.addAttribute("agentid",agentid);
        model.addAttribute("isApply",1);

        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("users", users);

        return "system/leave/add";
    }



    /**
     *
     * @描述: 领用样品跳转
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:17
     */
    @RequestMapping("/return/{id}")
    @RequiresPermissions("meet:update")
    public String toReturn(@PathVariable("id") Integer sampleId,Model model)
    {
        Sample sample = iSampleService.selectByPrimaryKey(sampleId);
        String agentid=sample.getCreateBy();
        String applyid=getUserId();

        model.addAttribute("sid", sampleId);
        model.addAttribute("sname",sample.getTitle());

        model.addAttribute("agentid",agentid);
        model.addAttribute("isApply",0);
        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("users", users);

        return "system/return/add";
    }










}
