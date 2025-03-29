package com.unionhole.mcpserver.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.unionhole.mcpserver.service.WeatherService;

/**
 * Auto-generated MCP Facade class for WeatherService
 *
 * @author James Zou
 * @version 1.0.0
 * @since 2025-03-29
 */
@Component
public class WeatherServiceFacade {
    @Autowired
    private WeatherService service;

    @Tool(description = "Get weather information by city name")
    public java.lang.String getWeather(java.lang.String cityName) {
        try {
            return service.getWeather(cityName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Tool(description = "getWeather1")
    public java.lang.String getWeather1(java.lang.String cityName) {
        try {
            return service.getWeather1(cityName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
