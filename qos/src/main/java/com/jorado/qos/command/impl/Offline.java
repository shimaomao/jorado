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

import com.jorado.common.extension.ExtensionLoader;
import com.jorado.common.logger.Logger;
import com.jorado.common.logger.LoggerFactory;
import com.jorado.qos.command.BaseCommand;
import com.jorado.qos.command.CommandContext;
import com.jorado.qos.command.annotation.Cmd;
import com.jorado.registry.Registry;
import com.jorado.registry.RegistryFactory;
import com.jorado.registry.support.ProviderConsumerRegTable;
import com.jorado.registry.support.ProviderInvokerWrapper;
import com.jorado.rpc.model.ApplicationModel;
import com.jorado.rpc.model.ProviderModel;

import java.util.Collection;
import java.util.Set;

@Cmd(name = "offline", summary = "offline dubbo", example = {
        "offline dubbo",
        "offline xx.xx.xxx.service"
})
public class Offline implements BaseCommand {
    private Logger logger = LoggerFactory.getLogger(Offline.class);
    private RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();

    @Override
    public String execute(CommandContext commandContext, String[] args) {
        logger.info("receive offline command");
        String servicePattern = ".*";
        if (args != null && args.length > 0) {
            servicePattern = args[0];
        }
        boolean hasService = false;

        Collection<ProviderModel> providerModelList = ApplicationModel.allProviderModels();
        for (ProviderModel providerModel : providerModelList) {
            if (providerModel.getServiceName().matches(servicePattern)) {
                hasService = true;
                Set<ProviderInvokerWrapper> providerInvokerWrapperSet = ProviderConsumerRegTable.getProviderInvoker(providerModel.getServiceName());
                for (ProviderInvokerWrapper providerInvokerWrapper : providerInvokerWrapperSet) {
                    if (!providerInvokerWrapper.isReg()) {
                        continue;
                    }
                    Registry registry = registryFactory.getRegistry(providerInvokerWrapper.getRegistryUrl());
                    registry.unregister(providerInvokerWrapper.getProviderUrl());
                    providerInvokerWrapper.setReg(false);
                }
            }
        }

        if (hasService) {
            return "OK";
        } else {
            return "service not found";
        }
    }
}
