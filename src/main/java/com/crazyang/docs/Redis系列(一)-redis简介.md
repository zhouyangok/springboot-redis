# 一、Redis简介
Redis是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，
并提供多种语言的API。

redis是一个key-value存储系统。与Memcached类似，它支持存储的value类型相对更多，包括5种数据类型。
redis和memcached一样，为了保证效率，数据都是缓存在内存中。redis会周期性的把更新的数据写入
磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步。



# 二、Redis安装 
redis安装，参考博客: [redis安装](https://www.jianshu.com/p/af33284aa57a)   

注：redis修改配置文件后启动时，要加载配置文件一起启动，redis后台启动：  /usr/local/bin/redis-server /usr/local/etc/redis.conf & 

# 三、redis数据结构
**redis提供五种数据类型：string，hash，list，set及zset(sorted set)**   
  
- 1.string  
  String类型的数据存储字符串、整数或者浮点数 ，操作如下：   
 
  ```  
  
  127.0.0.1:6379> set redisString yang
OK
127.0.0.1:6379> get redisString
"yang"
127.0.0.1:6379> incr redisString
(error) ERR value is not an integer or out of range
127.0.0.1:6379> set num 1
OK
127.0.0.1:6379> incr num
(integer) 2
127.0.0.1:6379> get num
"2"
127.0.0.1:6379> del num
(integer) 1
127.0.0.1:6379> get num
(nil)
127.0.0.1:6379> 
  ```   
  设置键值对：set key value   
  根据键获取值：get key  
  删除键：del key  
  将 key 中储存的数字值增一:incr key<font color='red'>该操作值必须是int类型的</font>  
    
    
- 2.hash    
Redis hash 是一个string类型的field和value的映射表，hash特别适合用于存储对象。  

Redis 中每个 hash 可以存储 232 - 1 键值对（40多亿）。  
   
```
127.0.0.1:6379> hset yang name 'hello' age 10
(integer) 1
127.0.0.1:6379> hget yang name
"hello"
127.0.0.1:6379> hget yang age
"10"
127.0.0.1:6379> hgetall yang
1) "name"
2) "hello"
3) "description"
4) "hset"
5) "visitors"
6) "1000"
7) "age"
8) "10"
```    
设置键值对:hset key param1 value1 param2 value2  
获取hash值：hget key param1  
获取所有值：hgetall key

- 3.list   
 
``` 
127.0.0.1:6379> Lpush lyang theone
(integer) 1
127.0.0.1:6379> lpush lyang thetwo
(integer) 2
127.0.0.1:6379> lpush lyang thethree
(integer) 3
127.0.0.1:6379> lrange lyang 0 3
1) "thethree"
2) "thetwo"
3) "theone"
127.0.0.1:6379> lrange lyang 0 2
1) "thethree"
2) "thetwo"
3) "theone"
127.0.0.1:6379> lrange lyang 1 2
1) "thetwo"
2) "theone"
127.0.0.1:6379> rpush lyang right
(integer) 4
127.0.0.1:6379> lrange 0 3
(error) ERR wrong number of arguments for 'lrange' command
127.0.0.1:6379> lrange lyang 0 3
1) "thethree"
2) "thetwo"
3) "theone"
4) "right"
127.0.0.1:6379> 
```

LPUSH(RPUSH)	将给定值推入列表的左端(右端)  
LPOP(RPOP)	从列表的左端(右端)弹出一个值，并返回被弹出的值  
LINDEX	获取列表在给定位置上的单个值  
LRANGE	获取列表在给定范围上的所有值  

- 4.set  （无序集合，去重） 

Redis 的 Set 是 String 类型的**无序集合**。集合成员是唯一的，这就意味着**集合中不能出现重复**的数据。

Redis 中集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。  
 
 ```
 127.0.0.1:6379> sadd set1 test
(integer) 1
127.0.0.1:6379> sadd set1 test1
(integer) 1
127.0.0.1:6379> sadd set1 test2
(integer) 1
127.0.0.1:6379> smembbers set1
(error) ERR unknown command 'smembbers'
127.0.0.1:6379> smembers set1
1) "test2"
2) "test1"
3) "test"
127.0.0.1:6379> sadd set1 test
(integer) 0
127.0.0.1:6379> smembers set1
1) "test2"
2) "test1"
3) "test"
127.0.0.1:6379> sadd set1 hello
(integer) 1
127.0.0.1:6379> sadd set1 xulie
(integer) 1
127.0.0.1:6379> smembers set1
1) "hello"
2) "test2"
3) "test1"
4) "test"
5) "xulie"
127.0.0.1:6379> 
 ```  
 SADD命令返回1表示成功添加到集合中，返回0表示该元素已存在于集合中.  
 
 
- 5.zset(有序集合，去重排序)  

有序集合和散列一样，都用于存储键值对：其中有序集合的每个键称为成员（member），都是独一无二的，而有序集合的每个值称为分值（score），都必须是浮点数。有序集合是Redis里面唯一既可以根据成员访问元素（这一点和散列一样），又可以根据分值以及分值的排列顺序来访问元素的结构。  

``` 
127.0.0.1:6379> zadd test 10 test1
(integer) 1
127.0.0.1:6379> zadd test 100 test2
(integer) 1
127.0.0.1:6379> zadd test 100 test2
(integer) 0
127.0.0.1:6379> zadd test101 test2
(error) ERR wrong number of arguments for 'zadd' command
127.0.0.1:6379> zadd test 101 test2
(integer) 0
127.0.0.1:6379> zrange test 0 100
1) "test1"
2) "test2"
127.0.0.1:6379> zrangebyscore test 0 10
1) "test1"
127.0.0.1:6379> 
```  
在尝试向有序集合添加元素的时候，ZADD命令会返回新添加元素的数量；ZRANGE命令获取有序集合包含的所有元素，这些元素会按照分值进行排序，Python客户端会将这些分值转换成浮点数；ZRANGEBYSCORE命令也可以根据分值来获取有序集合的其中一部分元素；ZREM命令在移除有序集合元素的时候，命令会返回被移除元素的数量。
