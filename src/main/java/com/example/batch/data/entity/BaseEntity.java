package com.example.batch.data.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Objects;


@MappedSuperclass
public class BaseEntity {
    @Column(name = "CREATEUSER", updatable = false)
    @CreatedBy
    protected String createUser;

    @Column(name = "CREATEDATE", updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createDate;

    @Column(name = "UPDATEUSER")
    @LastModifiedBy
    protected String updateUser;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "UPDATEDATE")
    protected Date updateDate;

    @Version
    @Column(name = "VERSIONID")
    protected Long versionId;

    public BaseEntity() {
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(createDate, createUser, updateDate, updateUser, versionId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseEntity other = (BaseEntity) obj;
        return Objects.equals(createDate, other.createDate)
                && Objects.equals(createUser, other.createUser) && Objects.equals(updateDate, other.updateDate)
                && Objects.equals(updateUser, other.updateUser) && Objects.equals(versionId, other.versionId);
    }

    @Override
    public String toString() {
        return "BaseEntity [createUser=" + createUser + ", createDate=" + createDate + ", updateUser=" + updateUser
                + ", updateDate=" + updateDate + ", versionId=" + versionId + "]";
    }

}
