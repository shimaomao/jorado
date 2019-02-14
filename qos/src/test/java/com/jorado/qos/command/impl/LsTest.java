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
import com.jorado.registry.integration.RegistryDirectory;
import com.jorado.registry.support.ProviderConsumerRegTable;
import com.jorado.registry.support.ProviderInvokerWrapper;
import com.jorado.rpc.Invoker;
import com.jorado.rpc.model.ApplicationModel;
import com.jorado.rpc.model.ConsumerModel;
import com.jorado.rpc.model.ProviderModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static com.jorado.registry.support.ProviderConsumerRegTable.getProviderInvoker;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LsTest {
    @Test
    public void testExecute() throws Exception {
        ConsumerModel consumerModel = mock(ConsumerModel.class);
        when(consumerModel.getServiceName()).thenReturn("com.jorado.FooService");
        ProviderModel providerModel = mock(ProviderModel.class);
        when(providerModel.getServiceName()).thenReturn("com.jorado.BarService");
        ApplicationModel.initConsumerModel("com.jorado.FooService", consumerModel);
        ApplicationModel.initProviderModel("com.jorado.BarService", providerModel);

        Invoker providerInvoker = mock(Invoker.class);
        URL registryUrl = mock(URL.class);
        when(registryUrl.toFullString()).thenReturn("test://localhost:8080");
        URL providerUrl = mock(URL.class);
        when(providerUrl.getServiceKey()).thenReturn("com.jorado.BarService");
        when(providerUrl.toFullString()).thenReturn("dubbo://localhost:8888/com.jorado.BarService");
        when(providerInvoker.getUrl()).thenReturn(providerUrl);
        ProviderConsumerRegTable.registerProvider(providerInvoker, registryUrl, providerUrl);
        for (ProviderInvokerWrapper wrapper : getProviderInvoker("com.jorado.BarService")) {
            wrapper.setReg(true);
        }

        Invoker consumerInvoker = mock(Invoker.class);
        URL consumerUrl = mock(URL.class);
        when(consumerUrl.getServiceKey()).thenReturn("com.jorado.FooService");
        when(consumerUrl.toFullString()).thenReturn("dubbo://localhost:8888/com.jorado.FooService");
        when(consumerInvoker.getUrl()).thenReturn(consumerUrl);
        RegistryDirectory directory = mock(RegistryDirectory.class);
        Map invokers = Mockito.mock(Map.class);
        when(invokers.size()).thenReturn(100);
        when(directory.getUrlInvokerMap()).thenReturn(invokers);
        ProviderConsumerRegTable.registerConsumer(consumerInvoker, registryUrl, consumerUrl, directory);

        Ls ls = new Ls();
        String output = ls.execute(mock(CommandContext.class), null);
        assertThat(output, containsString("com.jorado.FooService|100"));
        assertThat(output, containsString("com.jorado.BarService| Y"));
    }
}
