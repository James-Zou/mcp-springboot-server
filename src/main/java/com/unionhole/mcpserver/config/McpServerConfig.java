/*
 * Copyright 2024 James Zou (18301545237@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.unionhole.mcpserver.config;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class McpServerConfig {
    @Bean
    public ToolCallbackProvider autoRegisterTools(ApplicationContext applicationContext) {
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
