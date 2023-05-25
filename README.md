# WorldEdit Selection Manager

## Description

A bukkit plugin, auto selects any structures as a WorldEdit cuboid selection,
fork of WorldEdit Structure Selector, remove dependent libraries other than WorldEdit.

Rewrite to use FAWE API.

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
- /wsm|wss exclude_add_hand|addhand
  - exclude block in main hand when selecting
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

## Branche

- master: Spigot/PaperSpigot 1.16.5+, need JDK 17
  - tested on: 1.16.5, 1.17.1, 1.18.2, 1.19.4 with `FastAsyncWorldEdit-Bukkit-2.6.2-SNAPSHOT-437.jar`

Note that the latest version of FAWE (`FastAsyncWorldEdit-Bukkit-2.6.2-SNAPSHOT-437.jar`) needs JDK 17+.

## Changelogs

[Download](https://github.com/CatEricka/WorldEditSelectionManager/releases)

### Version 2.0.0:

- File name: `WorldEditSelectionManager-spigot1.16.5-jdk17-2.0.0.jar`
- Support PaperSpigot 1.16.5+
- Compatible with FAWE 2.* and Java 17+

Added a new command `/wsm|wss exclude_add_hand|addhand`,
which can add the block on the main hand to the exclude_list.

Tested on PaperSpigot 1.16.5, 1.17.1, 1.18.2, 1.19.4 with `FastAsyncWorldEdit-Bukkit-2.6.2-SNAPSHOT-437.jar` .

Note: PaperSpigot 1.16.5 needs to turn off the java version check to start with Java 17.
For example: `java -DPaper.IgnoreJavaVersion=true -jar $PAPER_JAR -nogui`

### Version 1.2.0

This version are not recommended.

If you are using Java 16 or lower with PaperSpigot 1.16.5, specific versions of FAWE (FAWE 1.17.*) can be used with
`WorldEditSelectionManager-spigot1.16.5-jdk16-fawe1.17-1.2.0.jar`.

- File name:
  - `WorldEditSelectionManager-spigot1.16.5-jdk16-fawe1.17-1.2.0.jar`
  - `WorldEditSelectionManager-spigot1.17.1-jdk17-fawe2.0-1.2.0.jar`

## TODO
  - i18n
  - add selection clipboard

------

## 介绍

bukkit 插件，启用后，使用木斧头点击方块，会沿方块相连开始递归搜索（最大递归深度为 maxXZ*2-1，最大垂直搜索范围为 maxY，
见配置和命令），并自动根据相连区域大小创建 WorldEdit 选区。

该插件为 WorldEdit Structure Selector（ https://github.com/HexoCraft/WorldEditStructureSelector/ ）的 fork。
本着不要重复造轮子的思想，复用了该插件的代码。（虽然这么说不过基本上全重写了）

由于原插件使用的 API 似乎无法在 1.16.5 工作，因此使用 FAWE API重写插件，去掉了所有外部依赖（除 FAWE）。
使用 FAWE 加速后选区速度提高了一个数量级，常规大小的选区操作可以在一秒内结束。

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
- /wsm|wss exclude_add_hand|addhand
  - 把主手上的方块加入到排除列表。很实用的命令。
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

## 分支

- master: Spigot/PaperSpigot 1.16.5+, 要求 JDK 17
  - 在这些版本的 PaperSpigot 上测试过: 1.16.5, 1.17.1, 1.18.2, 1.19.4
  - 测试使用的 FAWE 版本为 `FastAsyncWorldEdit-Bukkit-2.6.2-SNAPSHOT-437.jar`

需要注意的是最新版本的 FAWE (`FastAsyncWorldEdit-Bukkit-2.6.2-SNAPSHOT-437.jar`) 需要 JDK 17+，

## 更新记录

[下载](https://github.com/CatEricka/WorldEditSelectionManager/releases)

### 版本 2.0.0:

推荐使用这个版本。

- 文件名: `WorldEditSelectionManager-spigot1.16.5-jdk17-2.0.0.jar`
- 支持 PaperSpigot 1.16.5+
- 和 FAWE 2.* 以及 Java 17+ 兼容。

添加了新命令 `/wsm|wss exclude_add_hand|addhand`, 可以将手上的方块添加到排除列表。

在 PaperSpigot 1.16.5, 1.17.1, 1.18.2, 1.19.4 上与  `FastAsyncWorldEdit-Bukkit-2.6.2-SNAPSHOT-437.jar` 测试，工作正常。

注意：PaperSpigot 1.16.5 需要关闭 Java 版本检查才能使用 Java 17。
参数示例：`java -DPaper.IgnoreJavaVersion=true -jar $PAPER_JAR -nogui`

### 版本 1.2.0

不推荐使用这个版本。

如果你正在使用 Java 16 或更低版本，`WorldEditSelectionManager-spigot1.16.5-jdk16-fawe1.17-1.2.0.jar`
可以和特定版本的 FAWE (FAWE 1.17.*) 一起工作。

- 文件名:
  - `WorldEditSelectionManager-spigot1.16.5-jdk16-fawe1.17-1.2.0.jar`
  - `WorldEditSelectionManager-spigot1.17.1-jdk17-fawe2.0-1.2.0.jar`
  
## 开发计划

- [ ] 本地化翻译
- [ ] 实现选区剪贴板
  
