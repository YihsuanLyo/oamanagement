package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;

public class ApplySample extends BasePo{

    private String id;


    private Integer sid;
    /** 发起人Id */
    private String proposer_Id;
    /** 实例ID*/
    private String procInstanId;

    /** 代理人ID */
    private String agent_Id;


    /**申请数量*/
    private Integer applyquan;

    /** 审批发起时间 */
    private Date createTime;

    /**审批完成结束时间*/
    private Date endTime;

    /** 申领时间*/
    private Date applytime;



    /** 申领理由 */
    private String applyreason;

    /**回复内容*/
    private String reply;

    /** 状态 */
    private Integer status;

    private Date currDate;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Integer getsId()
    {
        return sid;
    }

    public void setsId(Integer sid)
    {
        this.sid = sid;
    }

    public String getProposer_Id()
    {
        return proposer_Id;
    }

    public void setProposer_Id(String proposer_Id)
    {
        this.proposer_Id = proposer_Id;
    }

    public String getProcInstanId()
    {
        return procInstanId;
    }

    public void setProcInstanId(String procInstanId)
    {
        this.procInstanId = procInstanId;
    }

    public String getAgent_Id()
    {
        return agent_Id;
    }

    public void setAgent_Id(String agent_Id)
    {
        this.agent_Id = agent_Id;
    }

    public Integer getApplyquan()
    {
        return applyquan;
    }

    public void setApplyquan(Integer applyquan)
    {
        this.applyquan = applyquan;
    }

    public String getApplyreason()
    {
        return applyreason;
    }

    public void setApplyreason(String applyreason)
    {
        this.applyreason = applyreason;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getApplytime()
    {
        return applytime;
    }

    public void setApplytime(Date applytime)
    {
        this.applytime = applytime;
    }





    public String getReply()
    {
        return reply;
    }

    public void setReply(String reply)
    {
        this.reply = reply;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Date getCurrDate()
    {
        return currDate;
    }

    public void setCurrDate(Date currDate)
    {
        this.currDate = currDate;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("ApplySample{");
        sb.append("id='").append(id).append('\'');
        sb.append(", proposer_Id='").append(proposer_Id).append('\'');
        sb.append(", procInstanId='").append(procInstanId).append('\'');
        sb.append(", agent_Id='").append(agent_Id).append('\'');
        sb.append(", applyreason='").append(applyreason).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", applyTime='").append(applytime).append('\'');
        sb.append(", applyreason='").append(applyreason).append('\'');
        sb.append(", reply='").append(reply).append('\'');
        sb.append(", status=").append(status);
        sb.append(", applyquan=").append(applyquan);
        sb.append('}');
        return sb.toString();
    }
}