### 一个密码管理工具
**文件**
PropertiesUtil：存取配置文件
AESUtil：AES256算法
PassManage：入口类

本工具的密码加解密用的AES256算法，密码存储在properties文件里，如下：
```
  qq = onQ9HCrmFpi9veRwVUys4wLLJu1ssBTDt0fb9pf5fHw=                           
  weixin = 5Eb2RRgpEEd05vptsqZxuwGwhhqGHGY/VZb7wMnIAoU=
  baiduyunpan = HacMLaEDGXGEwXgTgYYLbXyN13w6gQgpUtSQqZ7pX+E=
```

密码的存取都是通过程序完成，使用方法如下。
**使用方法**
首先需要输入一个主密码，程序不会存储主密码到文件，所以自己一定要记得这个密码。
```
Please enter the master password: testmain
***********Begin*************
get item					Get the password for item.
get item1 item2...	Get the password for items. Items are separated by spaces.
set item ***			Set the password for item.
getall					Get the password for all items.
quit						Exit the program.
***********End*************
Please enter the command to execute: get qq
qq=aa123
Please enter the command to execute: set yy yuyu
yy=f612WetoETx4C3thtCVupOLXDqDzgeMGNfWILG1UhAA=
Please enter the command to execute: get yy
yy=yuyu
Please enter the command to execute: quit
```



