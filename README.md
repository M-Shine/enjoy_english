# 乐享英语（经验学习与分享系统）
## 一、概述
项目基于 Springboot 2.2.5 + Spring Security + JPA 构建
## 二、接口
> /**  接口无需登录即可访问<br>/api/**  接口普通用户和管理员都可以访问<br>/management/**  接口仅管理员可以访问
### 1. 账号操作相关接口（已完成）
|序号|接口名称|URL|备注|
|:-:|:-|:-|:-|
|1|获取验证码图片|/verificationCode|
|2|登录|/login|
|3|登出|/logout|
|4|注册|/register|
|5|查询用户个人资料|/api/getUser|
|6|查询所有用户个人资料|/management/getUsers|
|7|修改用户个人资料|/api/updateUser|
|8|删除用户|/management/deleteUser|

### 2. 数据操作相关接口
|序号|接口名称|URL|备注|
|:-:|:-|:-|:-|
|1|获取菜单选项|/api/getMenus|
|2|条件查询菜单选项|/management/searchMenu/|未完成
|3|添加菜单选项|/management/addMenu|
|4|删除菜单选项|/management/deleteMenu|
|5|修改菜单选项|/management/updateMenu|
|6|获取QA资料|/api/getQA|
|7|条件查询QA资料|/management/searchQA|未完成
|8|添加QA资料|/management/addQA|
|9|修改QA资料|/management/updateQA|
|10|删除QA资料|/management/deleteQA|

### 3. 用户反馈操作相关接口（未完成）
|序号|接口名称|URL|备注|
|:-:|:-|:-|:-|