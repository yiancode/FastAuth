# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

JustAuth 是一个第三方授权登录的工具类库，支持 Github、Gitee、微信、支付宝、Google、Facebook 等数十家第三方平台的 OAuth 登录。

## 常用命令

```bash
# 编译项目
mvn clean compile

# 运行测试
mvn test

# 运行单个测试类
mvn test -Dtest=AuthRequestBuilderTest

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
me.zhyd.oauth
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

### 核心类

- **AuthDefaultRequest**: 所有 OAuth 请求的抽象基类，实现标准 OAuth2 流程
- **AuthSource**: OAuth 平台 API 地址的统一接口
- **AuthDefaultSource**: 内置平台枚举，定义各平台的 authorize/accessToken/userInfo URL
- **AuthConfig**: 客户端配置（clientId、clientSecret、redirectUri 等）
- **AuthRequestBuilder**: 快捷构建 AuthRequest 的 Builder 类

### 扩展新平台

1. 在 `AuthDefaultSource` 枚举中添加平台定义，实现 `authorize()`、`accessToken()`、`userInfo()` 和 `getTargetClass()` 方法
2. 在 `request/` 包下创建对应的 Request 类，继承 `AuthDefaultRequest`
3. 实现 `getAccessToken(AuthCallback)` 和 `getUserInfo(AuthToken)` 方法
4. 如需自定义 scope，在 `enums/scope/` 下添加对应的 Scope 枚举

### 自定义平台（不修改源码）

实现 `AuthSource` 接口创建自定义枚举，通过 `AuthRequestBuilder.extendSource()` 注册使用。

## 依赖说明

- **simple-http**: HTTP 工具抽象层，需配合 hutool-http/httpclient/okhttp 使用
- **fastjson**: JSON 解析
- **alipay-sdk**: 支付宝登录（provided scope）
- **jjwt**: Apple 登录 JWT 处理（provided scope）

## 测试

测试位于 `src/test/java/`，使用 JUnit 4。`AuthRequestTest.java` 被排除在测试之外（仅作 API 演示）。
