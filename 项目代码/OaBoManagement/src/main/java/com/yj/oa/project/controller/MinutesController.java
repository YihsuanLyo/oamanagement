package com.yj.oa.project.controller;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.*;
import com.yj.oa.project.service.Minutes.IMinutesService;
import com.yj.oa.project.service.meet.IMeetService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("/minutes")
public class MinutesController extends BaseController{
    private final static String prefix = "system/minutes";
    private static final Logger log = LoggerFactory.getLogger(UploadCertificateController.class);

    @Autowired
    IMinutesService minutesService;

    @Autowired
    IMeetService meetService;

    @Autowired
    IUserService iUserService;
    @Autowired
    IMeetingRoomService iMeetingRoomService;


    /**
     *
     * @描述: 跳转到列表页
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:13
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("minutes:list")
    public String toList(Model model)
    {
        List<MeetingRoom> meetingRooms = iMeetingRoomService.selectMeetRoomList(new MeetingRoom());
        List<User> users = iUserService.selectByUser(new User());
        List<Meet> meets = meetService.selectMyMeetList(getUserId());
        model.addAttribute("Rooms", meetingRooms);
        model.addAttribute("Users", users);
        model.addAttribute("Meets", meets);
        return prefix + "/minutes";
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
    public TableDataInfo tableList(Minutes minutes)
    {
        startPage();
        List<Meet> meets = new ArrayList<Meet>();
        List<Integer> meetIds = new ArrayList<Integer>();
        List<Minutes> minutess = new ArrayList<Minutes>();
        if(minutes.getMeetId()==null) {
            meets = meetService.selectMyMeetList(getUserId());
            for (Meet meet : meets) {
                Integer meetId = meet.getId();
                minutes.setMeetId(meetId);
                List<Minutes> ms = minutesService.selectByMinutesList(minutes);
                if (ms != null) {
                    for(Minutes ms_t:ms){
                    minutess.add(ms_t);
                }}
            }
        }else{
            minutess=minutesService.selectByMinutesList(minutes);

        }

        for (Minutes minutes1 : minutess)
        {
            User user = iUserService.selectByPrimaryKey(minutes1.getCreateBy());
            minutes1.setCreateBy(user.getName());
            Meet meet_temp = meetService.selectByPrimaryKey(minutes1.getMeetId());
            minutes1.setMeet(meet_temp);
            log.info("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈："+minutes1.getMeet().getTitle());
        }

        return getDataTable(minutess);

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
    @RequiresPermissions("minutes:add")
    public String toAdd(Model model)
    {
        List<MeetingRoom> meetingRooms = iMeetingRoomService.selectMeetRoomList(new MeetingRoom());
        List<Meet> meets = meetService.selectByMeetList(new Meet())
;        model.addAttribute("Meets", meets);
        return prefix + "/add";
    }

    /**
     *
     * @描述: 添加保存
     *
     * @params: minutes：会议室对象；userIds:开会用户id
     * @return:
     * @date: 2018/9/26 21:16
     */
    @RequestMapping("/addSave")
    @RequiresPermissions("minutes:add")
    @ResponseBody
    public AjaxResult addSave(Minutes minutes) throws Exception
    {


        minutes.setCreateBy(getUserId());
        minutes.setCreateTime(new Date());

        //把自己带上


        return result(minutesService.insertSelective(minutes));
    }

    /**
     *
     * @描述: 删除会议
     *
     * @params:
     * @return:
     * @date: 2018/9/27 22:02
     */
    @RequestMapping("/del")
    @RequiresPermissions("minutes:del")
    @Operlog(modal = "会议管理", descr = "删除会议")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {


        //2.判断是否是本人 getUserId()
        for (Integer id : ids){
            Minutes minutes = minutesService.selectByPrimaryKey(id);
            log.info("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈："+minutes.getCreateBy());
            if (!minutes.getCreateBy().equals(getUserId()))
            {
                return error("非会议负责人，无法操作！");
            }}

        return result(minutesService.deleteByPrimaryKeys(ids));
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
    @RequiresPermissions("minutes:update")
    public String toEdit(@PathVariable("id") Integer meetId, Model model)
    {
        Minutes minutes = minutesService.selectByPrimaryKey(meetId);
        Meet meet = meetService.selectByPrimaryKey(minutes.getMeetId());

        List<Meet> meets = meetService.selectByMeetList(new Meet());
        model.addAttribute("Minutes", minutes);
        model.addAttribute("Meet", meet);
        model.addAttribute("Meets", meets);

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
    @RequiresPermissions("minutes:update")
    @Operlog(modal = "会议管理", descr = "修改会议")
    @ResponseBody
    public AjaxResult editSave(Minutes minutes)
    {
        minutes.setCreateTime(new Date());

        return result(minutesService.updateByPrimaryKeySelective(minutes));
    }

    /**
     *
     * @描述: 通过会议id获取参加会议的员工 做编辑会会议的员工回显
     *
     * @params:
     * @return:
     * @date: 2018/9/27 21:02
     */
    @RequestMapping("/selectById/{minutesId}")
    @ResponseBody
    public Minutes selectById(@PathVariable("minutesId") Integer minutesId)
    {
        return minutesService.selectByPrimaryKey(minutesId);
    }



    /**
     *
     * @描述 查看详情页面
     *
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/detail/{id}")
    @RequiresPermissions("minutes:update")
    @Operlog(modal = "会议纪要详细",descr = "会议纪要详细")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Minutes minutes = minutesService.selectByPrimaryKey(id);
        Meet meet = meetService.selectByPrimaryKey(minutes.getMeetId());
        model.addAttribute("minutes", minutes);
        model.addAttribute("meet", meet);
        return prefix + "/detail";
    }

}
