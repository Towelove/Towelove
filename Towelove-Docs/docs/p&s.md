# 遇到的问题及解决方法：

## 工具

### [[Apifox] Request path contains unescaped characters](https://blog.csdn.net/m0_52133525/article/details/127996328)、

### [IDEA加载卡死问题]

再IDEA加载JDK或者maven的时候点击暂停，此时去进行配置，把本来默认的加载的路径，设置为你本地maven或者JDK的路径，如果去加载的是C盘的，那么很可能会卡死。

### [[maven项目依赖注入失败Could not transfer artifact解决方案]](https://blog.csdn.net/qq_50790981/article/details/125139450)

## 前端

## 后端

### [[SpringBoot jar方式启动报找不到main方法问题]](https://blog.csdn.net/MiLan_1/article/details/120437974)

### [[mybatis/mybatis plus报错：Invalid bound statement (not found) 解决方法汇总]](https://blog.csdn.net/mashangzhifu/article/details/122808181)

### [[SpringCloud Gateway自定义过滤器中获取ServerHttpRequest的body中的数据为NULL的问题]](https://blog.csdn.net/Zhangsama1/article/details/129444854?spm=1001.2014.3001.5502)

### [请求事务的处理]

[事务的传播行为](https://javaguide.cn/system-design/framework/spring/spring-knowledge-and-questions-summary.html#spring-%E4%BA%8B%E5%8A%A1%E4%B8%AD%E5%93%AA%E5%87%A0%E7%A7%8D%E4%BA%8B%E5%8A%A1%E4%BC%A0%E6%92%AD%E8%A1%8C%E4%B8%BA)

### [数据库中存储的smtp的密码是铭文，有危险，可以考虑加密]

### [[Mapstruct使用的时候没有生成对应的Impl实现类的解决方式]](https://www.qetool.com/scripts/view/8442.html)

### [[MapStuct使用时转换对象都为null的解决方式]](https://blog.csdn.net/qq_41169544/article/details/127675917)

### [[If you want an embedded database (H2, HSQL or Derby), please put it on the classpath.]](https://blog.csdn.net/Zhangsama1/article/details/129355872?spm=1001.2014.3001.5502)

我们出现的这个问题在于当前项目没有扫描到你的对数据库的配置文件，我们出错的原因是因为我们再maven里面设定了一个resouces标签，并且该标签没有加载我们的boostrap.yml配置文件，从而导致我们的数据库配置没有生效。解决方式及删除maven中的resouces标签或者对标签加载的路径进行重新的编写。

### [Redis健康检查问题]（待解决）

在连接云服务器redis的时候，出现健康检查失败，默认连接本地服务器，问题还在排查中，猜测是连接池lettuce问题，目前使用本地redis进行代替。

### [用户重复登入问题]（待优化）

用户在登入的时候，由于登入请求是拦截器白名单中的，gateway网关的拦截器不会对其进行校验，所以会导致重复登入问题，我们目前选择在Login请求中手动校验header中的Authorization参数。可以解决普通用户重复登入问题

**需要优化的地方在于：应对非法请求，如使用apiFox等软件自定义请求头，不携带Authorization参数仍然可以重复登入，导致redis中储存多个token**

### [sentinel集成nocos双写配置]

由于sentinel的限流规则保存到内存中，每次重新启动sentinel都会导致配置丢失，故我们使用nacos的配置中心作为sentinel的数据源进行持久化操作。在对sentinel控制台的源码进行修改后可以实现双写操作：在控制台中修改规则可以持久化到nacos中，在nacos中修改云端配置可以同步到sentinel中。

### [sentinel整合gateway]

sentinel整合gateway网关颗粒度较大，可以做第一重限流，注意在使用gateway网关做限流的时候限流规则与普通服务略有不同，详细参考Sentinel提供的GateFlowRules类，resourceMode配置可以决定资源类型的RouteID or APIID

### [maven打包编译问题]

我们在给父工程的子模块进行打包操作的时候，需要先到父模块中执行install操作，然后再去子模块的打包编译。

是由于父工程依赖传递给子模块，我们在给子模块打包的时候，有些依赖的不能确定，需要父工程install操作后子模块才能确定依赖位置以及版本。

### [MybatisPlus查询时间区间内的任务]

如果我们直接使用MybatisPlus使用DateTime类型去向数据库中查询数据，会发现查询出来的记录数为0，原因是Java中的Date类型与Mysql中的类型对应不上

![https://cdn.nlark.com/yuque/0/2023/png/34317889/1678896159288-60ec753c-2b64-4ac6-ad9f-d380087d75df.png](https://www.notion.so/image/https%3A%2F%2Fcdn.nlark.com%2Fyuque%2F0%2F2023%2Fpng%2F34317889%2F1678896159288-60ec753c-2b64-4ac6-ad9f-d380087d75df.png?id=2166a988-2190-41fa-a6ec-5a0120d1cbdb&table=block&spaceId=b64c4d64-f403-4087-86e0-a1e8fdb8361f&width=2000&userId=c25e5a34-6e8a-4a43-ba9f-f420d2f8d2b7&cache=v2)

我们只需要将传入的DateTime类型使用toString转化为String字符串就可以正确查询出数据

![https://cdn.nlark.com/yuque/0/2023/png/34317889/1678896210454-85719c6f-0e8d-400b-91ae-a6509207b44a.png](https://www.notion.so/image/https%3A%2F%2Fcdn.nlark.com%2Fyuque%2F0%2F2023%2Fpng%2F34317889%2F1678896210454-85719c6f-0e8d-400b-91ae-a6509207b44a.png?id=dadb0ddf-13e1-4166-80ca-fa8462a6b095&table=block&spaceId=b64c4d64-f403-4087-86e0-a1e8fdb8361f&width=2000&userId=c25e5a34-6e8a-4a43-ba9f-f420d2f8d2b7&cache=v2)

### [热部署问题][---->查看解决方案来源](https://blog.csdn.net/BBsatan/article/details/110919245)

使用JRebel查询可以便捷实现热部署，增加开发效率

### [FeignClient重复注册问题]

我们使用OpenFeign做原创调用的时候，如果我们使用@FeignClient注解标记多个类，我们需要加上ContextId属性，否则会发送重复注册

原理是Feign底层在做Bean管理的时候是使用ByName类型来注册bean，如果出现多个类使用了@FeignClient注解就会出现重复注册问题，我们需要用ContextId属性来给bean加上不同的名字

## 运维