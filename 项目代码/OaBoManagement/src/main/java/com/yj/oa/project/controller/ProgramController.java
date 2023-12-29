package com.yj.oa.project.controller;

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

/**
 *
 */
@Controller
@RequestMapping("/program")
public class ProgramController extends BaseController{
    private final static String prefix = "system/program";

    @Autowired
    IProgramService iProgramService;

    @Autowired
    IUserService iUserService;
    @Autowired
    IMeetingRoomService iMeetingRoomService;


    /**
     *
     * @描述: 跳转到列表页
     *
     *
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("program:list")
    public String toList()
    {
        return prefix + "/program";
    }


    /**
     *
     * @描述: 修改日程工作完成状态
     *
     *
     */
    @RequestMapping("/updateComplete")
    @RequiresPermissions("program:update")
    @Operlog(modal = "日程管理",descr = "记录工作完成")
    @ResponseBody
    public AjaxResult updateComplete(Program program)
    {
       program.setIsComplete(CsEnum.scheduled.SCHEDULE_YES_COMPLETE.getValue());
        return result(iProgramService.updateComplete(program));
    }


    /**
     *
     * @描述: ajaxgetList
     *
     *
     */
    @RequestMapping("/ajaxgetMap")
    @ResponseBody
    public Map<String, String> ajaxgetMap()
    {
        Map<String, String> map = new HashMap<>();
        List<Program> programs = iProgramService.selectProgramList(new Program());
        for (Program s : programs)
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
     *
     */
    @RequestMapping("/editMore/{date}")
    @RequiresPermissions("program:update")
    @Operlog(modal = "日程管理",descr = "查看当天日程安排")
    public String editMore(@PathVariable("date") String date, Model model)
    {
        Program program = new Program();
        //查询未完成的
        program.setIsComplete(CsEnum.scheduled.SCHEDULE_NO_COMPLETE.getValue());
        program.setStartTime(DateUtils.StrToDate(date));
        System.out.println(DateUtils.DateToSTr(DateUtils.StrToDate(date)));
        List<Program> programs = iProgramService.selectProgramList(program);
        model.addAttribute("programs", programs);
        return prefix + "/editMore";
    }


    /**
     *
     * @描述: 返回表格数据
     *
     *
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo tableList(Program program)
    {
        startPage();
        program.setCreateBy(getUserId());
        List<Program> programs = iProgramService.selectProgramList(program);

        return getDataTable(programs);
    }


    /**
     *
     * @描述: 添加页面
     *
     *
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("program:add")
    public String toAdd()
    {
        return prefix + "/add";
    }

    /**
     *
     * @描述: 添加保存
     *
     *
     */
    @RequestMapping("/addSave")
    @RequiresPermissions("program:add")
    @Operlog(modal = "项目管理",descr = "项目安排")
    @ResponseBody
    public AjaxResult addSave(Program program, String[] userIds) throws Exception
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
            return result(iProgramService.insertSelective(program, arrNew));
        }

        return result(iProgramService.insertSelective(program, userIds));


    }

    /**
     *
     * @描述: 删除
     *
     *
     */
    @RequestMapping("/del")
    @RequiresPermissions("program:del")
    @Operlog(modal = "日程管理",descr = "日程删除")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        return result(iProgramService.deleteByPrimaryKeys(ids));
    }


    /**
     *
     * @描述: 编辑页面
     *
     *
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("program:update")
    public String toEdit(@PathVariable("id") Integer id, Model model)

    {

        Program program = iProgramService.selectByPrimaryKey(id);
        if(!getUserId().equals(program.getCreateBy())){
            return "/error/unauth";
        }
        System.out.println(program);
        model.addAttribute("program", program);
        return prefix + "/edit";
    }


    /**
     *
     * @描述: 通过id获取单条
     *
     *
     */
    @RequestMapping("/selectById/{id}")
    @ResponseBody
    public Program selectById(@PathVariable("id") Integer id)
    {
        return iProgramService.selectByPrimaryKey(id);
    }


    /**
     *
     * @描述: 修改保存
     *
     *
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("program:update")
    @Operlog(modal = "日程管理",descr = "修改日程安排")
    @ResponseBody
    public AjaxResult editSave(Program program, String[] userIds)
    {
        return result(iProgramService.updateByPrimaryKeySelective(program, userIds));
    }

    /**
     *
     * @描述 查看认证资料页面
     *
     *
     */
    @RequestMapping("/certificate/{id}")
    @RequiresPermissions("program:update")
    @Operlog(modal = "项目认证",descr = "项目认证")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Program program = iProgramService.selectByPrimaryKey(id);
        model.addAttribute("programId", id);
        return prefix + "/certificate";
    }


    /**
     *
     * @描述 查看产品资料页面
     *
     *
     */
    @RequestMapping("/product/{id}")
    @RequiresPermissions("program:update")
    @Operlog(modal = "项目认证",descr = "项目认证")
    public String product_file(@PathVariable("id") Integer id, Model model)
    {
        Program program = iProgramService.selectByPrimaryKey(id);
        model.addAttribute("programId", id);
        return prefix + "/product";
    }

    /**
     *
     * @描述 查看产品资料页面
     *
     *
     */
    @RequestMapping("/design/{id}")
    @RequiresPermissions("program:update")
    @Operlog(modal = "项目认证",descr = "项目认证")
    public String design_file(@PathVariable("id") Integer id, Model model)
    {
        Program program = iProgramService.selectByPrimaryKey(id);
        model.addAttribute("programId", id);
        return prefix + "/design";
    }
    /**
     *
     * @描述 查看产品资料页面
     *
     *
     */
    @RequestMapping("/chain/{id}")
    @RequiresPermissions("program:update")
    @Operlog(modal = "项目认证",descr = "项目认证")
    public String chain_file(@PathVariable("id") Integer id, Model model)
    {
        Program program = iProgramService.selectByPrimaryKey(id);
        model.addAttribute("programId", id);
        return prefix + "/chain";
    }

    /**
     *
     * @描述 查看产品资料页面
     *
     *
     */
    @RequestMapping("/hardware/{id}")
    @RequiresPermissions("program:update")
    @Operlog(modal = "项目认证",descr = "项目认证")
    public String hardware_file(@PathVariable("id") Integer id, Model model)
    {
        Program program = iProgramService.selectByPrimaryKey(id);
        model.addAttribute("programId", id);
        return prefix + "/hardware";
    }


    /**
     *
     * @描述 查看产品资料页面
     *
     *
     */
    @RequestMapping("/sample/{id}")
    @RequiresPermissions("program:update")
    @Operlog(modal = "样品管理",descr = "样品管理")
    public String sample(@PathVariable("id") Integer id, Model model)
    {
        Program program = iProgramService.selectByPrimaryKey(id);
        model.addAttribute("programId", id);
        return prefix + "/sample";
    }


}
