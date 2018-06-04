package com.jx.example.entity;

import com.jx.example.base.BaseEntity;

public class Permission extends BaseEntity {
    private String permissionName;

    private String permissionDescription;

    private Byte permissionType;

    private String parentPermissionName;

    private static final long serialVersionUID = 1L;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription == null ? null : permissionDescription.trim();
    }

    public Byte getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Byte permissionType) {
        this.permissionType = permissionType;
    }

    public String getParentPermissionName() {
        return parentPermissionName;
    }

    public void setParentPermissionName(String parentPermissionName) {
        this.parentPermissionName = parentPermissionName == null ? null : parentPermissionName.trim();
    }
}