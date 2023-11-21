# 调优策略

## Redis缓存

为了提高项目的访问速度，使用缓存是很正常的，可以减少请求打到MySQL中，在提高响应速度的同时减少了数据库的压力，但是使用Redis缓存将需要面临如下三个问题，下面三个小章节介绍了我们是如何解决这三个问题的。

### 可用方案：

### SpringCache集成Redis

### RedisCache

### 缓存穿透

### 缓存并发

### 缓存失效

### 与MySQL的数据一致性问题

[[三种缓存的读写策略]](https://javaguide.cn/database/redis/3-commonly-used-cache-read-and-write-strategies.html)

[[Redis和MySQL双写一致性如何保证]](https://www.iocoder.cn/Fight/How-Redis-and-MySQL-double-write-consistency-guarantee/?yudao)

[[缓存和数据库一致性问题详细版]](https://mp.weixin.qq.com/s?__biz=MzIyOTYxNDI5OA==&mid=2247487312&idx=1&sn=fa19566f5729d6598155b5c676eee62d&chksm=e8beb8e5dfc931f3e35655da9da0b61c79f2843101c130cf38996446975014f958a6481aacf1&scene=178&cur_album_id=1699766580538032128#rd)

### 实际使用方式

## 请求路径的封装

我们的项目使用的是RESTFul风格来发送请求，那么这就意味着有编程基础的人，以及对项目有恶意的人可以很容易的了解我们项目的路径设置规则，一次为了防止这种恶意攻击，我们对真正展示在浏览器地址栏的路径进行了封装

比如原本的一个请求路径：http://123.123.123.123:8080/user/get可能就会变成如下一个请求

http://123.123.123.123:8080/lasd123/a123

## 多线程发送任务

我们知道我们的项目中的消息的任务是有时间的，因此我们可以按照时间对任务进行排序，并且使用多线程去发送这些任务。那么可以考虑允许多线程写读的ConcurrentLinkedQueue，既保证了FIFO，并且可以保证多线程下的线程安全策略。

## 任务的封装

任务可以使用Future陪着Semaphore来实现所有任务同时完成后的应答，同时Future在任务完成后是有返回值的，我们就知道那些任务是失败了的，就可以进行失败重试。

![https://cdn.nlark.com/yuque/0/2023/png/34806522/1678779397411-26d86cc7-67e4-400f-ab54-23c01a353de8.png](https://www.notion.so/image/https%3A%2F%2Fcdn.nlark.com%2Fyuque%2F0%2F2023%2Fpng%2F34806522%2F1678779397411-26d86cc7-67e4-400f-ab54-23c01a353de8.png?id=044d5fef-dfe8-4f17-9742-9b60875f784b&table=block&spaceId=b64c4d64-f403-4087-86e0-a1e8fdb8361f&width=2000&userId=c25e5a34-6e8a-4a43-ba9f-f420d2f8d2b7&cache=v2)

## 线程池动态调优

[线程池线程数量动态调优](https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html)

在我们买的面试题里面有对线程池的动态调优方法

## 任务获取数量调优

忽然想到 我们的每次查询出来的任务数量应该是动态可变的 也就是先select count 然后分片

得考虑如何做到动态sql

因为如果说任务很多 在某一个时间段就会查询出来特别多的任务 有点危

## 异步任务

异步任务就是为了解耦来做的，当一个线程的执行结果不依赖之前的执行结果的时候，为了提高性能，我们就可以使用异步任务来完成接下来的任务，比如日志的记录等。

[SpringBoot异步任务入门](https://www.iocoder.cn/Spring-Boot/Async-Job/)

其中需要注意的一点是，对于全局异步任务异常处理器。

```
* 只能拦截返回类型非 Future 的异步调用方法。
* 查看AsyncExecutionAspectSupport#handleError(Throwable ex, Method method, Object... params)
* 的源码，可以很容易得到这个结论
* 所以返回类型为 Future 的异步调用方法，
* 需要通过 [异步回调] 来处理。也就是设定addCallBack()方法来处理
* 并且这个方法只能对直接拥有@Async的方法扔出的报错能进行处理
* 而再方法中调用了包含@Async的方法是不能的
```

这里我解释一下最后两行是什么意思，也就是，虽然我在测试中调用了testAsyncWithCallBack方法，并且这个方法调用了一个异步方法，但是这个异步方法发出的错误并不会被全局异步任务处理器给处理，我们必须直接调用包含了@Async注解的方法，其报错才会被全局异步任务处理器所处理。
 

![https://cdn.nlark.com/yuque/0/2023/png/34806522/1679631291313-f51c4a07-a017-430c-aa44-3c97f756c3a0.png](https://www.notion.so/image/https%3A%2F%2Fcdn.nlark.com%2Fyuque%2F0%2F2023%2Fpng%2F34806522%2F1679631291313-f51c4a07-a017-430c-aa44-3c97f756c3a0.png?id=2440b21e-3fd7-4c3b-b5f9-da706a16e68b&table=block&spaceId=b64c4d64-f403-4087-86e0-a1e8fdb8361f&width=2000&userId=c25e5a34-6e8a-4a43-ba9f-f420d2f8d2b7&cache=v2)