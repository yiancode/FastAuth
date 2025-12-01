<p align="center">
  <h1 align="center">FastAuth</h1>
  <p align="center">
    <strong>🚀 小而全而美的第三方登录开源组件，开箱即用，让登录变得 So Easy!</strong>
  </p>
</p>

<p align="center">
  <a href="https://github.com/yiancode/FastAuth/blob/master/LICENSE">
    <img src="https://img.shields.io/badge/License-MIT-blue.svg" alt="License">
  </a>
  <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
    <img src="https://img.shields.io/badge/JDK-1.8+-green.svg" alt="JDK">
  </a>
  <a href="https://github.com/yiancode/FastAuth">
    <img src="https://img.shields.io/github/stars/yiancode/FastAuth?style=social" alt="Stars">
  </a>
</p>

---

## 项目简介

**FastAuth** 是一款轻量级、开箱即用的 Java 第三方授权登录组件，基于 [JustAuth](https://github.com/justauth/JustAuth) 深度优化扩展。

只需简单几行代码，即可集成 **40+ 主流平台**的 OAuth 登录，让你专注业务开发，告别繁琐的授权对接！

> Login, so easy!

---

## 核心特性

- **轻量级** - 无侵入设计，即插即用
- **全面覆盖** - 支持 40+ 国内外主流第三方平台
- **统一 API** - 简洁一致的调用方式，学习成本极低
- **灵活扩展** - 支持自定义 OAuth 平台、State 缓存、HTTP 实现
- **持续更新** - 新增微信公众号、知识星球等平台支持

---

## 支持平台

### 国内平台

| 平台 | 平台 | 平台 | 平台 |
|:---:|:---:|:---:|:---:|
| 微信开放平台 | **微信公众号** 🆕 | QQ | 微博 |
| 支付宝 | 钉钉 | 飞书 | 企业微信 |
| Gitee | 百度 | 淘宝 | 京东 |
| 抖音 | 今日头条 | 小米 | 华为 |
| 美团 | 饿了么 | 酷家乐 | 喜马拉雅 |
| Coding | 腾讯云 | OSChina | **知识星球** 🆕 |

### 海外平台

| 平台 | 平台 | 平台 | 平台 |
|:---:|:---:|:---:|:---:|
| GitHub | Google | Facebook | Twitter |
| Microsoft | LinkedIn | Amazon | Slack |
| Line | Apple | GitLab | StackOverflow |
| Pinterest | Figma | ... | ... |

> 🆕 标识为 FastAuth 新增支持的平台

---

## 快速开始

### 1. 引入依赖

```xml
<dependency>
    <groupId>com.yiancode</groupId>
    <artifactId>fastauth</artifactId>
    <version>1.0.0</version>
</dependency>
```

同时引入任意一种 HTTP 工具依赖（如项目中已有可忽略）：

```xml
<!-- 方式一：hutool-http -->
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-http</artifactId>
    <version>5.8.25</version>
</dependency>

<!-- 方式二：okhttp -->
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.12.0</version>
</dependency>

<!-- 方式三：httpclient -->
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.14</version>
</dependency>
```

### 2. 使用示例

#### 基础用法

```java
// 1. 创建授权请求
AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder()
        .clientId("your-client-id")
        .clientSecret("your-client-secret")
        .redirectUri("https://your-domain.com/callback")
        .build());

// 2. 生成授权地址，引导用户跳转
String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());

// 3. 授权回调，获取用户信息
AuthResponse<AuthUser> response = authRequest.login(callback);
```

#### 微信公众号登录 🆕

```java
AuthRequest authRequest = new AuthWechatMpRequest(AuthConfig.builder()
        .clientId("your-appid")
        .clientSecret("your-secret")
        .redirectUri("https://your-domain.com/callback")
        .build());

String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
```

#### 知识星球登录 🆕

```java
AuthRequest authRequest = new AuthZsxqRequest(AuthConfig.builder()
        .clientId("your-client-id")
        .clientSecret("your-client-secret")
        .redirectUri("https://your-domain.com/callback")
        .build());

String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
```

#### Builder 模式（推荐）

```java
AuthRequest authRequest = AuthRequestBuilder.builder()
    .source("github")
    .authConfig(AuthConfig.builder()
        .clientId("your-client-id")
        .clientSecret("your-client-secret")
        .redirectUri("https://your-domain.com/callback")
        .build())
    .build();
```

---

## 相比 JustAuth 的增强

| 特性 | JustAuth | FastAuth |
|------|:--------:|:--------:|
| 基础第三方登录 | ✅ | ✅ |
| 微信公众号登录 | ❌ | ✅ |
| 知识星球登录 | ❌ | ✅ |
| 持续维护更新 | - | ✅ |

---

## 文档

详细使用文档请参考：[FastAuth 使用指南](docs/)

---

## 参与贡献

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支：`git checkout -b feature/your-feature`
3. 提交更改：`git commit -m 'Add some feature'`
4. 推送分支：`git push origin feature/your-feature`
5. 提交 Pull Request

---

## 开源协议

本项目基于 [MIT](LICENSE) 协议开源。

---

## 致谢

- [JustAuth](https://github.com/justauth/JustAuth) - 感谢原项目的优秀设计与实现

---

<p align="center">
  如果觉得有用，请点个 ⭐ Star 支持一下！
</p>