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

import com.jorado.qos.command.BaseCommand;
import com.jorado.qos.command.CommandContext;
import com.jorado.qos.command.annotation.Cmd;
import com.jorado.qos.textui.TTable;
import com.jorado.rpc.model.ApplicationModel;
import com.jorado.rpc.model.ConsumerModel;
import com.jorado.rpc.model.ProviderModel;

import java.util.Collection;

import static com.jorado.registry.support.ProviderConsumerRegTable.getConsumerAddressNum;
import static com.jorado.registry.support.ProviderConsumerRegTable.isRegistered;

@Cmd(name = "ls", summary = "ls service", example = {
        "ls"
})
public class Ls implements BaseCommand {
    @Override
    public String execute(CommandContext commandContext, String[] args) {
        StringBuilder result = new StringBuilder();
        result.append(listProvider());
        result.append(listConsumer());

        return result.toString();
    }

    public String listProvider() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("As Provider side:" + System.lineSeparator());
        Collection<ProviderModel> ProviderModelList = ApplicationModel.allProviderModels();

        TTable tTable = new TTable(new TTable.ColumnDefine[]{
                new TTable.ColumnDefine(TTable.Align.MIDDLE),
                new TTable.ColumnDefine(TTable.Align.MIDDLE)
        });

        //Header
        tTable.addRow("Provider Service Name", "PUB");

        //Content
        for (ProviderModel providerModel : ProviderModelList) {
            tTable.addRow(providerModel.getServiceName(), isRegistered(providerModel.getServiceName()) ? "Y" : "N");
        }
        stringBuilder.append(tTable.rendering());

        return stringBuilder.toString();
    }

    public String listConsumer() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("As Consumer side:" + System.lineSeparator());
        Collection<ConsumerModel> consumerModelList = ApplicationModel.allConsumerModels();

        TTable tTable = new TTable(new TTable.ColumnDefine[]{
                new TTable.ColumnDefine(TTable.Align.MIDDLE),
                new TTable.ColumnDefine(TTable.Align.MIDDLE)
        });

        //Header
        tTable.addRow("Consumer Service Name", "NUM");

        //Content
        //TODO to calculate consumerAddressNum
        for (ConsumerModel consumerModel : consumerModelList) {
            tTable.addRow(consumerModel.getServiceName(), getConsumerAddressNum(consumerModel.getServiceName()));
        }

        stringBuilder.append(tTable.rendering());

        return stringBuilder.toString();
    }
}
