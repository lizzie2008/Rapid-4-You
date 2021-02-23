# 项目介绍

一款简单快速构建后台管理系统前后端的框架，基于 Spring Boot、 Spring Boot Jpa、 Spring Security、JWT、Redis、Elasticsearch、Vue.js、Element-UI等目前主流技术栈。

<div style="text-align: center">
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

**开发文档：** [http://docs.lancelot.tech/](http://docs.lancelot.tech/)

## 项目源码

|        | 前端源码                                                     | 后端源码                                                     |
| ------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Github | [Rapid-4-You/rapid-admin](https://github.com/lizzie2008/Rapid-4-You/tree/master/rapid-admin) | [Rapid-4-You/rapid-api](https://github.com/lizzie2008/Rapid-4-You/tree/master/rapid-api) |


本项目旨在方便开发人员快速搭建一套商业级、前后端分离的管理平台。技术上借鉴了Github两位大神的开源项目，并针对自己的业务特点进行了部分调整。

前端项目参考：[ vue-element-admin](https://panjiachen.github.io/vue-element-admin-site/zh/)

后端项目参考：[ el-admin](https://el-admin.vip/)

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

## 外部依赖

### 前端项目

- Vue.js-2.6.10： 渐进式 JavaScript 框架
- Vue-router-3.0.2： Vue.js 路由管理器
- Vuex-3.1.0： 管理共享状态
- Element-UI-2.14.1： 桌面端组件库
- Axios-0.18.1： 基于 promise 的 HTTP 库
- Mavon-Editor-1.5.0： markdown 文本解析

### 后端项目

- Spring Boot-2.1.0： 微服务框架
- Spring  Security-2.1.0： 安全集成框架
- Spring Data JPA-2.1.0：集成持久化 API
- Spring Data Redis-2.1.0：配置访问 redis 缓存服务
- Spring Data Elasticsearch-2.1.0：配置访问 Elasticsearch 服务
- Mapstruct-1.3.1：实体映射工具
- Lombok-1.18.2：代码自动构建插件
- Swagger-bootstrap-ui-1.9.6：API 文档增强UI
- Activiti-6.0.0：工作流引擎
- Maven-3.5.4： 依赖管理工具

### 数据存储

- MySQL-5.7.21： 关系数据库
- Redis-5.0.7：缓存数据库
- Elasticsearch-6.6.1： 站内全文检索

### 个人服务器

- 阿里云服务器（ECS）：2核 4GiB  3Mbps带宽
- 阿里云对象存储（OSS）：高可靠的云存储服务
- CentOS-7.1： 操作系统
- Nginx-1.17.6： 提供HTTP和反向代理服务
- OpenSSL-1.0.2： 协议加密
- Docker-1.13.1： 容器化部署
- Supervisor-3.3.4： linux进程监控工具

## 版本历史

**V1.0.3 版本**

- 2020/11/14 增加功能：工作流|代办任务
- 2020/11/13 增加功能：工作流|流程管理
- 2020/12/10 增加功能：工作流|模型管理

**V1.0.2 版本**

- 2020/11/18 优化功能：菜单项属性优化
- 2020/11/14 增加功能：博客|全文检索
- 2020/11/12 增加功能：博客|标签、分类、归档
- 2020/11/10 增加功能：博客|文章管理

**V1.0.1 版本**

- 2020/11/09 增加功能：存储|文件上传
- 2020/11/06 增加功能：存储|查看文件类型、大小
- 2020/11/05 增加功能：存储|目录管理
- 2020/11/01 增加功能：存储|阿里云OSS

**V1.0.0 版本**

- 2020/10/30 增加功能：系统API UI、接口调用日志
- 2020/10/28 增加功能：系统|岗位管理、部门管理
- 2020/10/25 增加功能：系统|菜单管理
- 2020/10/20 增加功能：系统|角色管理
- 2020/10/16 增加功能：系统|用户管理
- 2020/10/12 增加功能：登录与系统授权接口
- 2020/10/10 项目启动

## 联系作者

- [个人主页](https://www.lancelot.tech/)
- [GitHub](https://github.com/lizzie2008)
- [博客园](https://www.cnblogs.com/lizzie-xhu)
- [QQ邮箱](mailto:34878936@qq.com)
- qq：34878936
- 公众号：原子蛋Live+