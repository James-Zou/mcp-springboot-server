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
package com.unionhole.mcpserver;


import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.Map;

public class ClientSseTest {
    public static void main(String[] args) {
        var transport = new HttpClientSseClientTransport("http://localhost:8080");
        var client = McpClient.sync(transport).build();

        try {
            client.initialize();
            client.ping();

            // List available tools
            McpSchema.ListToolsResult toolsList = client.listTools();
            System.out.println("Available Tools = " + toolsList);

            client.closeGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
