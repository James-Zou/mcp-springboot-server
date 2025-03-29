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
package com.unionhole.mcpserver.service;

import com.unionhole.mcp.annotation.MCPService;
import com.unionhole.mcp.annotation.MCPMethod;
import org.springframework.stereotype.Service;

@MCPService(packageName = "com.unionhole.mcpserver.mcp")
@Service
public class WeatherService {

    /**
     * Get weather information by city name
     * @param cityName The name of the city
     * @return Weather information for the specified city
     */
    @MCPMethod(description = "Get weather information for a specific city")
    public String getWeather(String cityName) {
        // This is a demo implementation
        return "The weather in " + cityName + " is sunny with a temperature of 25°C";
    }

    @MCPMethod(description = "Get weather information for a specific city")
    public String getWeather1(String cityName) {
        // This is a demo implementation
        return "The weather in " + cityName + " is sunny with a temperature of 25°C";
    }
}
