# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

FastAuth 是一个第三方授权登录的工具类库，基于 JustAuth 深度优化扩展。支持 40+ 主流平台（Github、Gitee、微信、支付宝、Google、Facebook 等）的 OAuth 登录，新增微信公众号、知识星球等平台支持。

**技术要求**: JDK 1.8+，需配合 HTTP 实现库（hutool-http/okhttp/httpclient 任选其一）

## 常用命令

```bash
# 编译项目
mvn clean compile

# 运行测试
mvn test

# 运行单个测试类
mvn test -Dtest=AuthRequestBuilderTest

# 运行单个测试方法
mvn test -Dtest=AuthRequestBuilderTest#testBuild

# 打包（跳过测试）
mvn clean package -DskipTests

# 安装到本地仓库
mvn clean install

# 发布快照版本
mvn clean deploy -P snapshot

# 发布正式版本
mvn clean deploy -P release
```

## 核心架构

### 包结构

```
com.yiancode.fastauth
├── request/           # OAuth请求实现（核心）
├── config/            # 配置类
├── model/             # 数据模型
├── cache/             # State缓存
├── enums/             # 枚举类
│   └── scope/         # OAuth scope 定义
├── utils/             # 工具类
├── exception/         # 异常定义
└── log/               # 日志
```

注意：项目已从 JustAuth fork 并重命名为 FastAuth，包名从 `me.zhyd.oauth` 变更为 `com.yiancode.fastauth`。

### 核心类

- **AuthDefaultRequest**: 所有 OAuth 请求的抽象基类，实现标准 OAuth2 流程
- **AuthSource**: OAuth 平台 API 地址的统一接口
- **AuthDefaultSource**: 内置平台枚举，定义各平台的 authorize/accessToken/userInfo URL
- **AuthConfig**: 客户端配置（clientId、clientSecret、redirectUri 等）
- **AuthRequestBuilder**: 快捷构建 AuthRequest 的 Builder 类

### 扩展新平台（修改源码方式）

1. 在 `AuthDefaultSource` 枚举中添加平台定义：
   - 实现 `authorize()`：授权 URL
   - 实现 `accessToken()`：获取 access token 的 URL
   - 实现 `userInfo()`：获取用户信息的 URL
   - 实现 `getTargetClass()`：返回对应的 Request 实现类
   - 可选实现 `revoke()` 和 `refresh()`（部分平台支持）
2. 在 `request/` 包下创建对应的 Request 类：
   - 继承 `AuthDefaultRequest`
   - 实现 `getAccessToken(AuthCallback)` 方法
   - 实现 `getUserInfo(AuthToken)` 方法
3. 如需自定义 scope，在 `enums/scope/` 下添加对应的 Scope 枚举

示例：微信公众号登录（`AuthWeChatMpRequest`）、知识星球登录（`AuthZsxqRequest`）

### 自定义平台（不修改源码方式）

1. 实现 `AuthSource` 接口创建自定义枚举类
2. 实现对应的 Request 类（继承 `AuthDefaultRequest`）
3. 通过 `AuthRequestBuilder.extendSource()` 注册使用：

```java
AuthRequest authRequest = AuthRequestBuilder.builder()
    .source("custom")
    .authConfig(config)
    .extendSource(new MyCustomSource())
    .build();
```

## 依赖说明

### 核心依赖

- **simple-http** (1.0.5): HTTP 工具抽象层，需配合以下任一 HTTP 实现使用：
  - hutool-http (推荐)
  - okhttp
  - httpclient
- **fastjson** (1.2.83): JSON 解析
- **lombok** (1.18.30): 代码简化工具（optional）

### 可选依赖（provided scope）

- **alipay-sdk** (4.39.165.ALL): 支付宝登录
- **jjwt** (0.12.3): Apple 登录 JWT 处理
- **bcpkix-jdk18on** (1.78): 加密算法支持

### 构建配置

项目使用 Maven profiles 管理不同发布场景：
- **snapshot**: 发布快照版本到 OSSRH
- **release**: 发布正式版本到 Maven Central（需 GPG 签名）
- **nexus**: 发布到私有 Nexus 仓库

## 测试

测试位于 `src/test/java/`，使用 JUnit 4。`AuthRequestTest.java` 被排除在测试之外（仅作 API 演示）。

## OAuth 流程概览

标准使用流程（三步）：

```java
// 1. 构建 AuthRequest
AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder()
    .clientId("your-client-id")
    .clientSecret("your-client-secret")
    .redirectUri("https://your-domain.com/callback")
    .build());

// 2. 生成授权 URL，引导用户跳转
String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());

// 3. 回调处理，获取用户信息
AuthResponse<AuthUser> response = authRequest.login(callback);
```

`AuthDefaultRequest.login()` 内部流程：检查 code → 验证 state → 获取 access_token → 获取用户信息
