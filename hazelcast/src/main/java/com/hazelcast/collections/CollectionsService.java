/*
 * Copyright (c) 2008-2012, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.collections;

import com.hazelcast.spi.*;

import java.util.Map;
import java.util.Properties;

/**
 * @ali 8/29/13
 */
public abstract class CollectionsService implements ManagedService, RemoteService {

    protected NodeEngine nodeEngine;

    protected CollectionsService(NodeEngine nodeEngine) {
        this.nodeEngine = nodeEngine;
    }

    public void init(NodeEngine nodeEngine, Properties properties) {
    }

    public void reset() {
        getContainerMap().clear();
    }

    public void shutdown() {
        reset();
    }

    public void destroyDistributedObject(Object objectId) {
        final String name = String.valueOf(objectId);
        getContainerMap().remove(name);
        nodeEngine.getEventService().deregisterAllListeners(getServiceName(), name);
    }

    protected abstract CollectionsContainer getOrCreateContainer(String name, boolean backup);
    protected abstract Map<String, ? extends CollectionsContainer> getContainerMap();
    protected abstract String getServiceName();
}
