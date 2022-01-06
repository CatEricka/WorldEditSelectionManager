# WorldEdit Selection Manager

## Description

A bukkit plugin, auto selects any structures as a WorldEdit cuboid selection,
fork of WorldEdit Structure Selector, remove dependent libraries other than WorldEdit

WorldEdit Structure Selector: https://github.com/HexoCraft/WorldEditStructureSelector

## Permissions

wsm.admin: default op

## Command

- /wsm help
  - show help
- /wsm enable|1
  - enable auto select
- /wsm disable|0
  - disable auto select
- /wsm exclude_add|add
  - add Material name to exclude block list, witch while skip when searching
- /wsm exclude_clear|clear
  - reset exclude block list
- /wsm exclude_list|list
  - show Material names in exclude block list
- /wsm maxXZ|xz
  - set max x-z axis search size
  - maxDepth of RecursiveVisitor set to (xz*2 + 1)
- /wsm maxY|y
  - set max y axis search size
- /wsm reload
  - reload configuration
- /wsm cancel
  - cancel select task

## master branch build dependencies

- Spigot/PaperSpigot 1.16.5 (tested)
- FastAsyncWorldEdit 1.17.* or upper (tested with FastAsyncWorldEdit-Bukkit-1.17-419.jar)
  - lower than FastAsyncWorldEdit-Bukkit-2.*.*-SNAPSHOT-50.jar, because its move to jdk17
  - but PaperSpigot 1.16.5 only work with java16
- jdk 16 (because FAWE 1.17.* use jdk 16)

## release version different

- WorldEditSelectionManager-spigot1.16.5-jdk16-fawe1.17-*.jar
  - PaperSpigot 1.16.5
  - java16
  - FAWE 1.17.*
    - filename like: FastAsyncWorldEdit-Bukkit-1.17-{build version}.jar
- WorldEditSelectionManager-spigot1.17.1-jdk17-fawe2.0-*.jar
  - PaperSpigot 1.17.1
  - java17
  - FAWE 2.*

## TODO
  - i18n
  - add selection clipboard

## 介绍

bukkit 插件，启用后，使用木斧头点击方块，会沿方块相连开始递归搜索（最大递归深度为 maxXZ*2-1，最大垂直搜索范围为 maxY，
见配置和命令），并自动根据相连区域大小创建 WorldEdit 选区。

该插件为 WorldEdit Structure Selector（ https://github.com/HexoCraft/WorldEditStructureSelector/ ）的 fork。
本着不要重复造轮子的思想，复用了该插件的代码。由于原插件使用的 API 似乎无法在 1.16.5 工作，因此使用 FAWE API重写插件，
去掉了所有外部依赖（除 FAWE）。使用 FAWE 加速后选区速度提高了一个数量级，常规大小的选区操作可以在一秒内结束。

## Permissions

wsm.admin: default op

## 命令

- /wsm help
  - 查看帮助
- /wsm enable|1
  - 启用自动选区
- /wsm disable|0
  - 关闭自动选区
- /wsm exclude_add|add <Material ...>
  - 添加排除列表，列表中的方块会被递归搜索跳过，参数名必须为 Material 枚举名。（因为我很懒。）
- /wsm exclude_clear|clear
  - 将排除列表设为默认
- /wsm exclude_list|list
  - 显示排除列表
- /wsm maxXZ|xz
  - 设定最大 XZ 轴所搜范围
  - 实际上 maxXZ*2+1 会作为 WorldEdit 的 RecursiveVisitor 类的 maxDepth 参数
- /wsm maxY|y
  - 设定最大 Y 轴搜索范围，既垂直搜索范围
- /wsm reload
  - 重载配置（其实没什么配置可以重载的）
- /wsm cancel
  - 取消搜索任务（在搜索跑飞的情况下使用，比如你点了地板……）

## master 分支构建依赖

- Spigot/PaperSpigot 1.16.5 (更高版本未测试)
- FastAsyncWorldEdit 1.17.* (测试版本：FastAsyncWorldEdit-Bukkit-1.17-419.jar)
  - 注意，最新的 FastAsyncWorldEdit-Bukkit-2.*.*-SNAPSHOT-*.jar 可能无法兼容该插件
  - 因为 FAWE2.0 切换到了 jdk17，PaperSpigot 1.16.5 不支持 java16。
- jdk 16 (因为 FAWE 1.17.* 用了 jdk16)

## Release 版本区别

- WorldEditSelectionManager-spigot1.16.5-jdk16-fawe1.17-*.jar
  - minecraft 测试版本为 PaperSpigot 1.16.5
  - 使用 jdk16 构建，大概意味着需要使用 java16 才能运行
  - 兼容 FAWE 1.17.*
    - 其典型文件名为 FastAsyncWorldEdit-Bukkit-1.17-{构建版本号}.jar
    - 不兼容 FAWE 2.*
- WorldEditSelectionManager-spigot1.17.1-jdk17-fawe2.0-*.jar
  - minecraft 版本为 PaperSpigot 1.17.1
  - 使用 jdk17 构建
  - 兼容 FAWE 2.*

除 PaperSpigot 1.16.5, jdk16, FAWE 1.17.* 以外所有版本都未经测试。
  
## 开发计划

- [ ] 本地化翻译
- [ ] 实现选区剪贴板
  
