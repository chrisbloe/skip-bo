package com.custardcoding.skipbo.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

/*
 * Copyright 2010 Jonathan Feinberg
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

/**
 * Synchronize "on an equivalence class"; i.e., if you wish to lock not a
 * specific string, but anything that equals that string, you may
 *
 * <pre>EquivalenceLock<String> equivalenceLock = new EquivalenceLock<String>();
 * equivalenceLock.lock("frank");
 * try {
 *     // whatever
 * } finally {
 *     equivalenceLock.release("frank");
 * }</pre>
 * 
 * @author Jonathan Feinberg &lt;jdf@pobox.com&gt;
 */
@Service
public class SynchronizerService {
    private final Set<Long> slots = new HashSet<>();

    public void lock(final Long ticket) throws InterruptedException {
        synchronized (slots) {
            while (slots.contains(ticket)) {
                slots.wait();
            }

            slots.add(ticket);
        }
    }

    public void release(final Long ticket) {
        synchronized (slots) {
            slots.remove(ticket);
            slots.notifyAll();
        }
    }
}
