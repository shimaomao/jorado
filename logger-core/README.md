# 日志组件

- 目录
  - [基础组件使用](#基础组件使用)
  - [Dubbo组件使用](#注意事项)
  - [注意事项](#注意事项)
  - [未来规划](#未来规划)

## 基础组件使用

Gradle脚本添加如下引用
```groovy
compile('com.jorados:logger:版本号') 
```

1.快速日志提交

```java
EventClient.getDefault().submitLog(“消息体");
```

2.快速异常提交

```java         
EventClient.getDefault().submitException(“错误标题”, throwable对象);
```

3.带有附加数据的日志提交

```java         
EventClient.getDefault().createLog(“消息标题")
.addData("test", 1)
.addData("b", 2) 
.addTags("solr", "db", "api")
.submit();
```

4.带有附加数据的异常提交

```java 
EventClient.getDefault().createException(“消息标题”， throwable对象)
.addData(“a", 1)
.addData("b", 2) 
.addTags("solr", "db", "api")
.submit();
```
或者
```java
EventClient.getDefault().createLog(“消息标题")
.addData(“a", 1)
.addData("b", 2) 
.addTags("solr", "db", "api")
.setException(throwable对象)
.submit();
```

5.综合使用

```java 
EventBuilder eb = EventClient.getDefault().createEvent();
eb.addTags("标签1", "标签2", "标签3");
try {
    eb.addData("data1", "data body")
        .addData("data2", "data body")
        .addData("data2", "data body")
        .setMessage("日志标题");
} catch (Exception ex) {
    eb.setMessage("错误标题").setException(ex);
} finally {
    eb.submit();
}
```

## Dubbo组件使用

Gradle脚本添加如下引用

```groovy
compile('com.jorados:logger-dubbo:版本号') 
```

其他使用方法同上边[基础组件使用](#基础组件使用)

## 注意事项

1. > 所有submit方法（`submitLog`，`submitException`，`submit`）提交后均会返回日志引用id（`id`），用来方便使用方在后续方法中自行关联。

2. > 通常情况下如果`id`用户没有手动设定的话，框架会在所有数据收集组件组装完毕后再自动追加，也就是说用户在调用`EventClient.getDefault().createEvent()`方法后是无法取得`id`的，对于Dubbo组件提供了`DubboEventClient`，通过`DubboEventClient.getDefault().createEvent()`创建的`EventBuilder`对象会优先设定`id`。

## 未来规划

- [ ] 基于OpenTracing协议实现自定义过程方法执行时间链的收集。



