# MCP Spring Boot Server

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![JDK](https://img.shields.io/badge/JDK-17-green.svg)](https://www.oracle.com/java/technologies/downloads/#java17)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Author](https://img.shields.io/badge/Author-James%20Zou-orange.svg)](mailto:18301545237@163.com)

A Spring Boot server implementation for Model Context Protocol (MCP), providing weather information services through MCP interfaces.

## Features

- Weather information service via MCP
- RESTful API endpoints
- Server-Sent Events (SSE) support
- Automatic tool registration
- Spring Boot 3.x integration

## Requirements

- JDK 17 or later
- Maven 3.6.x or later
- Spring Boot 3.2.0 or later

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/mcp-springboot-server.git
cd mcp-springboot-server
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The server will start on port 8080 by default.

## Configuration

The main configuration properties are located in `application.properties`:

```properties
server.port=8080
spring.application.name=mcp-demo
spring.ai.mcp.server.enabled=true
```

For more configuration options, please check the `application.properties` file.

## API Documentation

### Weather Service

The Weather Service provides the following MCP methods:

- `getWeather(String cityName)`: Get weather information for a specific city
- `getWeather1(String cityName)`: Alternative method to get weather information

Example usage in the test client:

```java
var transport = new HttpClientSseClientTransport("http://localhost:8080");
var client = McpClient.sync(transport).build();
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/unionhole/mcpserver/
│   │       ├── config/
│   │       ├── service/
│   │       └── McpDemoApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/unionhole/mcpserver/
            └── ClientSseTest.java
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Author

**James Zou**

- Email: [18301545237@163.com](mailto:18301545237@163.com)
- GitHub: [@jameszou](https://github.com/jameszou)

## Acknowledgments

- Spring Boot Team
- Model Context Protocol Team
- All contributors to this project

## 框架说明

### 1. Maven 依赖配置

项目使用了以下主要依赖：
```xml
<dependencies>
    <!-- Spring AI Core -->
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-core</artifactId>
        <version>${spring-ai.version}</version>
    </dependency>

    <!-- MCP Server WebMVC -->
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
        <version>${spring-ai.version}</version>
    </dependency>
    <!-- 添加 MCP Facade 生成器依赖 -->
    <dependency>
        <groupId>com.unionhole</groupId>
        <artifactId>mcp-facade-generator</artifactId>
        <version>${mcp-facade.version}</version>
    </dependency>
</dependencies>
```

### 2. MCP 配置说明

在 `application.properties` 中配置 MCP 服务器：
```properties
# MCP 服务器配置
spring.ai.mcp.server.enabled=true
spring.ai.mcp.server.resource-change-notification=true
spring.ai.mcp.server.prompt-change-notification=true
spring.ai.mcp.server.tool-change-notification=true
spring.ai.mcp.server.name=mcp-demo-service
spring.ai.mcp.server.version=1.0.0
spring.ai.mcp.server.type=SYNC
spring.ai.mcp.server.sse-message-endpoint=/mcp/messages
```

### 3. MCP Tools 配置

通过 `McpServerConfig` 类配置 MCP Tools：
```java
@Configuration
public class McpServerConfig {
    @Bean
    public ToolCallbackProvider autoRegisterTools(ApplicationContext applicationContext) {
        // 获取所有带有 @Component 注解且类名以 Facade 结尾的 bean
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(Component.class);
        List<Object> facadeBeans = new ArrayList<>();
        for (String beanName : beanNames) {
            if (beanName.endsWith("Facade")) {
                facadeBeans.add(applicationContext.getBean(beanName));
            }
        }
        return MethodToolCallbackProvider.builder()
                .toolObjects(facadeBeans.toArray())
                .build();
    }
}
```

### 4. 业务服务开发

1. 创建服务类并添加 `@MCPService` 注解：
```java
@MCPService
@Service
public class WeatherService {
    public String getWeather(String cityName) {
        // 实现业务逻辑
        return "Weather info for " + cityName;
    }
}
```

2. 使用 `@MCPMethod` 注解标记需要暴露的方法：
```java
@MCPMethod(description = "获取天气信息")
public String getWeather(String cityName) {
    // 方法实现
}
```

### 5. 客户端测试

使用提供的 `ClientSseTest` 类进行测试：
```java
public class ClientSseTest {
    public static void main(String[] args) {
        var transport = new HttpClientSseClientTransport("http://localhost:8080");
        var client = McpClient.sync(transport).build();

        try {
            client.initialize();
            client.ping();

            // 列出可用的工具
            McpSchema.ListToolsResult toolsList = client.listTools();
            System.out.println("Available Tools = " + toolsList);

            client.closeGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## 注意事项

1. 确保正确配置了 Spring AI 和 MCP 的版本
2. 所有 Facade 类都应该使用 `@Component` 注解
3. 建议使用 `@MCPMethod` 注解来提供方法的描述信息
4. 异常处理应该在服务层统一处理

## 常见问题解决

### 1. 编译时出现 IllegalArgumentException

如果在编译过程中遇到以下错误：
```
java: java.lang.IllegalArgumentException
```

**解决方案：**

在 IntelliJ IDEA 中添加 VM 选项：

1. 打开 Settings (Ctrl + Alt + S)
2. 导航到 Build, Execution, Deployment > Compiler
3. 在 "Build process VM options" 字段中添加：
   ```
   -Djps.track.ap.dependencies=false
   ```
4. 点击 Apply 和 OK
5. 重新构建项目

## 参考文档

- [Spring AI 文档](https://docs.spring.io/spring-ai/reference/index.html)
- [MCP Facade Generator](https://github.com/James-Zou/mcp-facade-generator)


---
Copyright © 2024 James Zou. All rights reserved.
