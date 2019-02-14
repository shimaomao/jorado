/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jorado.qos.command.impl;

import com.jorado.common.URL;
import com.jorado.qos.command.CommandContext;
import com.jorado.registry.Registry;
import com.jorado.registry.support.ProviderConsumerRegTable;
import com.jorado.registry.support.ProviderInvokerWrapper;
import com.jorado.rpc.Invoker;
import com.jorado.rpc.model.ApplicationModel;
import com.jorado.rpc.model.ProviderModel;
import org.junit.jupiter.api.Test;

import static com.jorado.registry.support.ProviderConsumerRegTable.getProviderInvoker;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OnlineTest {
    @Test
    public void testExecute() throws Exception {
        ProviderModel providerModel = mock(ProviderModel.class);
        when(providerModel.getServiceName()).thenReturn("com.jorado.BarService");
        ApplicationModel.initProviderModel("com.jorado.BarService", providerModel);

        Invoker providerInvoker = mock(Invoker.class);
        URL registryUrl = mock(URL.class);
        when(registryUrl.toFullString()).thenReturn("test://localhost:8080");
        URL providerUrl = mock(URL.class);
        when(providerUrl.getServiceKey()).thenReturn("com.jorado.BarService");
        when(providerUrl.toFullString()).thenReturn("dubbo://localhost:8888/com.jorado.BarService");
        when(providerInvoker.getUrl()).thenReturn(providerUrl);
        ProviderConsumerRegTable.registerProvider(providerInvoker, registryUrl, providerUrl);

        Registry registry = mock(Registry.class);
        TestRegistryFactory.registry = registry;

        Online online = new Online();
        String output = online.execute(mock(CommandContext.class), new String[]{"com.jorado.BarService"});
        assertThat(output, equalTo("OK"));
        for (ProviderInvokerWrapper wrapper : getProviderInvoker("com.jorado.BarService")) {
            assertTrue(wrapper.isReg());
        }
    }
}
