package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;
import java.util.List;

public class Sample extends BasePo{
    private Integer id;

    private String createBy;

    private String title;

    private String descr;

    private Date startTime;

    private Integer total_mount;
    private Date createTime;



    private Integer residues;
    private Integer programId;





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
        this.createBy = createBy;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescr()
    {
        return descr;
    }

    public void setDescr(String descr)
    {
        this.descr = descr;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }
    public void setTotal_mount(Integer total_mount)
    {
        this.total_mount = total_mount;
    }

    public Integer getTotal_mount() {
        return total_mount;
    }

    public void setResidues(Integer residues)
    {
        this.residues = residues;
    }

    public Integer getResidues() {
        return residues;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }





    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("Meet{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", createBy='").append(createBy).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", descr='").append(descr).append('\'');
        sb.append(", total_mount=").append(total_mount);
        sb.append(", residues=").append(residues);
        sb.append(", programId=").append(programId);

        sb.append('}');
        return sb.toString();
    }
}