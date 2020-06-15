####Zookeeper数据模型
类似文件系统
/
  /test
    /test-children
  /test2
    /test2-children
###ZNODE
ZooKeeper树中的每个节点都称为znode。 Znodes维护一个统计信息结构，其中包括用于数据更改和acl更改的版本号。
stat结构也有时间戳。版本号和时间戳一起使ZooKeeper可以验证缓存并协调更新。 
znode的数据每次更改时，版本号都会增加。
例如，每当客户端检索数据时，它也会接收数据的版本。
并且，当客户端执行更新或删除时，它必须提供其更改的znode数据的版本。
如果它提供的版本与数据的实际版本不匹配，则更新将失败。
####Watches
客户端可以在znodes上设置监视。
对该znode的更改将触发监听，然后清除监听。
监视触发时，ZooKeeper向客户端发送通知。
####Data Access
原子地读取和写入存储在名称空间中每个znode上的数据。
读取将获取与znode关联的所有数据字节，而写入将替换所有数据。
每个节点都有一个访问控制列表（ACL），用于限制谁可以执行操作。
####节点类型
PERSISTENT
PERSISTENT_SEQUENTIAL
EPHEMERAL
EPHEMERAL_SEQUENTIAL
CONTAINER
PERSISTENT_WITH_TTL
PERSISTENT_SEQUENTIAL_WITH_TTL

####ZooKeeper Stat Structure
The Stat structure for each znode in ZooKeeper is made up of the following fields:

czxid The zxid of the change that caused this znode to be created.
mzxid The zxid of the change that last modified this znode.
pzxid The zxid of the change that last modified children of this znode.
ctime The time in milliseconds from epoch when this znode was created.
mtime The time in milliseconds from epoch when this znode was last modified.
version The number of changes to the data of this znode.
cversion The number of changes to the children of this znode.
aversion The number of changes to the ACL of this znode.
ephemeralOwner The session id of the owner of this znode if the znode is an ephemeral node. If it is not an ephemeral node, it will be zero.
dataLength The length of the data field of this znode.
numChildren The number of children of this znode.

####监听
Created event: Enabled with a call to exists.
Deleted event: Enabled with a call to exists, getData, and getChildren.
Changed event: Enabled with a call to exists and getData.
Child event: Enabled with a call to getChildren.

###一致性算法
####PAXOS
Paxos 协议中的三种角色
倡议者（Proposer）：倡议者可以提出提议（数值或者操作命令）以供投票表决
接受者（Acceptor）：接受者可以对倡议者提出的提议进行投票表决，提议有超半数的接受者投票即被选中
学习者（Learner）：学习者无投票权，只是从接受者那里获知哪个提议被选中
https://blog.csdn.net/yexiaomodemo/article/details/80286952
较有名的 Paxos 工程实现有 Google Chubby、 ZAB、微信的 PhxPaxos 等

###curator官方示例
https://github.com/apache/curator/tree/master/curator-examples/src/main/java