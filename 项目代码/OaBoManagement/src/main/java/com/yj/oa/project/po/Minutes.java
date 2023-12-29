package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;
import java.util.List;

public class Minutes extends BasePo{
    private Integer id;

    private String createBy;

    private Integer meetId;

    private Meet meet;



    private String descr;





    private Date createTime;







    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }



    public String getDescr()
    {
        return descr;
    }

    public void setDescr(String descr)
    {
        this.descr = descr;
    }






    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getMeetId() {
        return meetId;
    }

    public void setMeetId(Integer meetId) {
        this.meetId = meetId;
    }

    public Meet getMeet() {
        return meet;
    }

    public void setMeet(Meet meet) {
        this.meet = meet;
    }



    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("Minutes{");
        sb.append("id=").append(id);
        sb.append(", createBy='").append(createBy).append('\'');
        sb.append(", descr='").append(descr).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", meetId=").append(meetId);
        sb.append(", meet=").append(meet);
        sb.append('}');
        return sb.toString();
    }
}