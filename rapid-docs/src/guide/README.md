# 项目介绍

<p>一款简单快速构建后台管理系统前后端的框架，基于 Spring Boot、 Spring Boot Jpa、 Spring Security、JWT、Redis、Elasticsearch、Vue.js、Element-UI等目前主流技术栈。</p>

<center>

<div>
  <a target="_black" href="https://github.com/lizzie2008/Rapid-4-You/blob/master/LICENSE">
    <img alt="GitHub license" src="https://img.shields.io/github/license/lizzie2008/Rapid-4-You">
  </a>
  <a target="_black" href="https://github.com/lizzie2008/Rapid-4-You">
    <img alt="GitHub stars" src="https://img.shields.io/github/stars/lizzie2008/Rapid-4-You">
  </a>
  <a target="_black" href="https://github.com/lizzie2008/Rapid-4-You">
    <img alt="GitHub forks" src="https://img.shields.io/github/forks/lizzie2008/Rapid-4-You">
  </a>
</div>

</center>

## 项目源码

|        | 前端源码                                                     | 后端源码                                                     |
| ------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Github | [Rapid-4-You/rapid-admin](https://github.com/lizzie2008/Rapid-4-You/tree/master/rapid-admin) | [Rapid-4-You/rapid-api](https://github.com/lizzie2008/Rapid-4-You/tree/master/rapid-api) |

::: tip 说明
本项目旨在方便开发人员快速搭建一套商业级、前后端分离的管理平台。技术上借鉴了Github两位大神的开源项目，并针对自己的业务特点进行了部分调整。

前端项目参考：[ vue-element-admin](https://panjiachen.github.io/vue-element-admin-site/zh/)

后端项目参考：[ el-admin](https://el-admin.vip/)
:::

## 主要特性

- 使用当下最主流的技术栈，社区资源丰富，确保一段时间技术不被淘汰
- 通过注解，支持接口功能权限与数据权限，可快速对接口拦截与放行
- 对一些常用的方法封装，比如查询、分页、日志等
- 后端统一异常拦截处理，前端统一处理后端封装的返回信息
- 支持定义审计字段，并对用户操作进行日志记录，方便日后审计

## 主要功能

- 系统管理
  - 用户管理：提供用户的相关配置，新增用户后，默认密码为123456
  - 角色管理：对权限与菜单进行分配，可根据部门设置角色的数据权限
  - 菜单管理：已实现菜单动态路由，后端可配置化，支持多级菜单
  - 部门管理：可配置系统组织架构，树形表格展示
  - 岗位管理：配置各个部门的职位
- 博客管理
  - 文章列表：博客文章的增删改查，Markdown 格式的支持
  - 全文检索：集成 Elasticsearch ，按关键词进行全文检索并高亮显示
- 工作流设计
  - 模型管理：原生的 Activiti 模型设计界面，流程模型的编辑和保存
  - 流程管理：查看已部署的流程定义、发起流程、查看当前运行中流程和历史流程记录
  - 代办任务：针对已发起的流程，查看并处理当前登录用户代办的任务
- 系统工具
  - 阿里云OSS：支持图片、文档等文件上传阿里云，并以文件目录展示
- *其他功能：正在持续添加中。。。*

## 反馈交流

本项目开源，使用过程中如果遇到问题或者Bug可以在 Github 上提 [Issues](https://github.com/lizzie2008/Rapid-4-You/issues)

项目处于初期阶段，后续的新功能和对应的文档会继续完善...

QQ交流群：<待完善>

微信交流群：<待完善>

## 联系作者

- [个人主页](https://www.lancelot.tech/)
- [GitHub](https://github.com/lizzie2008)
- [博客园](https://www.cnblogs.com/lizzie-xhu)
- [QQ邮箱](mailto:34878936@qq.com)
- qq：34878936
- 公众号：原子蛋Live+