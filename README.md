[![vrml-logo](./resources/logo.png)](./README.md)

# vrml-cloudruntimes

## Vrml API

Vrml设计的核心理念同 [cloud-runtimes](https://github.com/capa-cloud/cloud-runtimes-jvm) API设计理念相同。

即定义一套框架/平台/具体实现等无关的API，面向接口进行编程，从而获得更好的拓展性和跨平台特性。

与 cloud-runtimes-api 语言无关的设计思路相比，Vrml主要为JVM编程语言设计，所以在API设计上糅合了Java的特性和Spring的使用方式。

面向API编程的理念决定了：Vrml可以在不同平台，不同框架体系中进行集成，您可以享受到`write once, run anywhere`的特性。

### Vrml With Cloud-Runtimes API

将Vrml API同Cloud-Runtimes API进行结合:

+ 由Cloud-Runtimes config实现Vrml config的能力
+ 由Cloud-Runtimes rpc实现Vrml rpc的能力
+ ...

这样，您可以在任何 Cloud-Runtimes 支持的平台上，使用Vrml的能力，例如：

+ Capa
+ Layotto
+ Dapr
+ ...

### Vrml Cloud-Runtimes Usage

Vrml Cloud-Runtimes库主要使用了Cloud-Runtimes中的Configuration API作为获取动态配置的方式。

总体而言，对于某个Vrml库需要的配置，会遵循以下方式进行获取：

1. 尝试从resources目录下获取对应的配置文件
2. 使用Cloud-Runtimes的Configuration API，从异构配置中心获取对应的配置文件

#### 开箱即用

所以，您只需要在对应的 本地resources目录/配置中心 中，按照默认的规范定义vrml库的配置文件，您就可以开箱即用的使用Vrml提供的基于动态配置的功能。

#### 自定义逻辑实现

当然，您也可以使用自定义逻辑替代默认实现，这一切都基于SpringFactory实现，所以仅需替换SpringFactory中的默认实现即可。

-------------------------------------------------------------------------------

## Modules

### 开箱即用

按照对应Vrml库的默认配置文件规范，在 本地resources目录/配置中心 中配置对应的文件即可。

---------------------------------------------------------------------

### [vrml-cloudruntimes-alert](./vrml-cloudruntimes-alert)

Alerts支持各种快捷灵活方式的告警API，避免过程式的调用告警service

### [开箱即用]基于动态配置的Alert：

根据Alert动态配置，灵活开关Alert。

#### 配置文件名称：vrml-alert-config.properties

#### 默认配置说明：

```properties
# 指示是否使用异步线程池执行Alert操作
AlertAsync=false
# 配置格式为：Alert实现类名=布尔值
# 指示对应Alert的操作是否开启
DefaultLogAlertMessage=true
DefaultCrashAlertMessage=true
```

#### 自定义说明：

```properties
# 同上...
# 新增Alert实现类
Alert实现类名称=布尔值
```

---------------------------------------------------------------------

### [vrml-cloudruntimes-api](./vrml-cloudruntimes-api)

API模块提供一个通用的基准切面，以记录接口的请求响应日志，同时支持配置化，使得更加灵活

### [开箱即用]基于动态配置的API：

根据API动态配置，灵活开关切面的日志/记录。

#### 配置文件名称：vrml-api-config.json

#### 默认配置说明：

1. logsKey规则：

    + 接口的："类名.方法名" （大小写兼容）

    + 代码：`pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName()`

```json
[
  {
    // 如上所述
    "logsKey": "default",
    // 是否记录接口请求日志
    "openRequestLog": false,
    // 是否记录接口响应日志
    "openResponseLog": false,
    // 是否记录接口异常日志
    "openErrorLog": true
  }
]
```

#### 自定义说明：

1. 自定义记录逻辑： 默认记录方式为log日志，可以通过继承+重写来自定义记录逻辑。

2. **[重要] [惰性执行]**：当接口的"类名.方法名"在配置列表中不存在时，不会执行记录操作。

在数组中添加对应的"类名.方法名"配置：

```json
[
  {
    // 类名.方法名（大小写兼容）
    "logsKey": "TestController.checkHealth",
    // 是否打印接口请求日志
    "openRequestLog": false,
    // 是否打印接口响应日志
    "openResponseLog": false,
    // 是否打印接口异常日志
    "openErrorLog": true
  }
]
```

---------------------------------------------------------------------

### [vrml-cloudruntimes-compute](./vrml-cloudruntimes-compute)

对统计触发的行为进行了封装，可以基于统计指标执行不同的Runnable操作。

### [开箱即用]基于动态配置的Compute：

根据Compute的动态配置，灵活设置compute操作的参数。

#### 配置文件名称：vrml-compute-timecounter-config.json

#### 默认配置说明：

在X秒内触发了Y次，则触发compute-right逻辑

```json
[
  {
    // compute key
    "key": "default",
    // X秒
    "expirationTime": 1,
    // Y次
    "triggerCount": 100
  }
]
```

#### 自定义说明：

1**[重要] [惰性执行]**：当key在配置列表中不存在时，执行compute计算时会报错。

```json
[
  ...
  // 新增条目
  {
    // compute key
    "key": "xxxxx",
    // X秒
    "expirationTime": 1,
    // Y次
    "triggerCount": 100
  }
]
```

---------------------------------------------------------------------

### [vrml-cloudruntimes-log](./vrml-cloudruntimes-log)

一个支持动态配置的日志记录API。

### [开箱即用]基于动态配置的Log：

根据Log的动态配置，灵活决定对应Logger的日志级别，实现更细粒度的操控。

#### 配置文件名称：vrml-log-config.json

#### 默认配置说明：

1. key规则

    + 如果使用Class生成Log对象，key为Class名称
    + 如果自行指定Log的key，则key为自行指定的String

2. 开关

    + 概念同SLF4J中的概念

3. **[重要]** 当没有指定的key时，将使用default的配置
4. **[重要]** vrml-log的配置生效早于slf4j本身的配置，vrml-log通过之后再进行slf4j的级别判断

```json
[
  {
    "key": "default",
    "trace": true,
    "debug": true,
    "info": true,
    "warn": true,
    "error": true
  }
]
```

#### 自定义说明：

1**[重要] [默认行为]**：当没有指定的key时，将使用default的配置

```json
[
  ...
  // 新增条目
  {
    "key": "xxx",
    "trace": true,
    "debug": true,
    "info": true,
    "warn": true,
    "error": true
  }
]
```

---------------------------------------------------------------------

### [vrml-cloudruntimes-metric](./vrml-cloudruntimes-metric)

用于记录应用程序埋点数据的API

### [开箱即用]基于动态配置的Metric：

根据Metrci的动态配置，灵活决定Metric辅助操作的开关，以及指标上报的方式和地址。

**[重要]** 因为Metric的上报方式和地址无法统一，所以需要继承抽象类自行实现，并注入到Spring上下文当中。

#### 配置文件名称：vrml-metric-config.json

#### 默认配置说明：

```json
{
  "topic": "test",
  // 是否开启{@code Metrics.metric}
  "metric": true,
  // 是否开启{@code Metrics.debug}
  "debug": true
}
```

#### 自定义说明：

1. 继承抽象类`AbstractMetricCloudRuntimesConfiguration`
2. 实现`doMetricException`方法，实现记录异常的方式
3. 实现`doMetricFinally`方法，实现最终上报指标的方式

---------------------------------------------------------------------

### [vrml-cloudruntimes-request](./vrml-cloudruntimes-request)

一个具有代理功能的API，用于对远程请求进行包装，从而进行日志记录/数值记录/响应检查/...

### [开箱即用]基于动态配置的Request Report：

基于Request Report的动态配置，灵活实现请求的统计和记录，优化监控统计的成本。

**[重要]** 请参考Request的使用文档，当开启`useConfig()`时，将会读取该动态配置，进行report操作。

#### 配置文件名称：vrml-request-report-config.json

#### 默认配置说明：

```json
[
  {
    // request name
    "requestReportName": "default",
    // 开关
    "openRequestReport": true,
    // 对应key在Y秒内出现X次时，触发report操作
    "reportTriggerCount": 100,
    // 对应key在Y秒内出现X次时，触发report操作
    "reportExpiredSeconds": 1000,
    // 缓存池的key容量（防止内存溢出）
    "reportPoolMaxSize": 1000,
    // 不进行记录的value
    "noRecordKeys": [
      "0"
    ]
  }
]
```

#### 自定义说明：

1**[重要] [惰性执行]**：当key在配置列表中不存在时，将不会执行request-report操作。

```json
[
  ...同上
  {
    // request name
    "requestReportName": "xxx",
    // 开关
    "openRequestReport": true,
    // 对应key在Y秒内出现X次时，触发report操作
    "reportTriggerCount": 100,
    // 对应key在Y秒内出现X次时，触发report操作
    "reportExpiredSeconds": 1000,
    // 缓存池的key容量（防止内存溢出）
    "reportPoolMaxSize": 1000,
    // 不进行记录的value
    "noRecordKeys": [
      "0"
    ]
  }
]
```

---------------------------------------------------------------------

### [vrml-cloudruntimes-switch](./vrml-cloudruntimes-switch)

对开关功能进行了封装，便捷的根据开关配置执行不同的Runnable，避免了大量if-else的开关逻辑。

### [开箱即用]基于动态配置的Switch：

根据Switch的动态配置，灵活开关对应操作。

#### 配置文件名称：vrml-switch-config.json

#### 默认配置说明：

json结构定义key即可，但最后的value必须为布尔值

```json
{
  // json结构   
}
```

#### 自定义说明：

当执行`a.b.c`这样的开关操作时，需要有以下结构：

```json
{
  "a": {
    "b": {
      "c": true
    }
  }
}
```

-------------------------------------------------------------------------------

### Maven

You can import all vrml modules:

```xml

<dependency>
    <groupId>group.rxcloud</groupId>
    <artifactId>vrml-cloudruntimes</artifactId>
    <version>1.1.4</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

Latest feature branch:

```xml

<dependency>
    <groupId>group.rxcloud</groupId>
    <artifactId>vrml-cloudruntimes</artifactId>
    <version>1.1.4</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

### Contributing

A small number of users have reported problems building vrml. Read our [contribution guide](./CONTRIBUTING.md) for
details.
