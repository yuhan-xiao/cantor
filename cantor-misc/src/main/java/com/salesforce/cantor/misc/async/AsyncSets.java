/*
 * Copyright (c) 2019, Salesforce.com, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.cantor.misc.async;

import com.salesforce.cantor.Sets;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static com.salesforce.cantor.common.CommonPreconditions.*;
import static com.salesforce.cantor.common.SetsPreconditions.*;

public class AsyncSets extends AbstractBaseAsyncCantor implements Sets {
    private final Sets delegate;

    public AsyncSets(final Sets delegate, final ExecutorService executorService) {
        super(executorService);
        checkArgument(delegate != null, "null delegate");
        this.delegate = delegate;
    }

    @Override
    public Collection<String> namespaces() throws IOException {
        return submitCall(this.delegate::namespaces);
    }

    @Override
    public void create(final String namespace) throws IOException {
        checkCreate(namespace);
        submitCall(() -> { this.delegate.create(namespace); return null; });
    }

    @Override
    public void drop(final String namespace) throws IOException {
        checkDrop(namespace);
        submitCall(() -> { this.delegate.drop(namespace); return null; });
    }

    @Override
    public void add(final String namespace, final String set, final String entry, final long weight) throws IOException {
        checkAdd(namespace, set, entry, weight);
        submitCall(() -> { this.delegate.add(namespace, set, entry, weight); return null; });
    }

    @Override
    public void add(final String namespace, final String set, final Map<String, Long> entries) throws IOException {
        checkAdd(namespace, set, entries);
        submitCall(() -> { this.delegate.add(namespace, set, entries); return null; });
    }

    @Override
    public Collection<String> entries(final String namespace,
                                      final String set,
                                      final long min,
                                      final long max,
                                      final int start,
                                      final int count,
                                      final boolean ascending) throws IOException {
        checkEntries(namespace, set, min, max, start, count, ascending);
        return submitCall(() -> this.delegate.entries(namespace, set, min, max, start, count, ascending));
    }

    @Override
    public Map<String, Long> get(final String namespace,
                                 final String set,
                                 final long min,
                                 final long max,
                                 final int start,
                                 final int count,
                                 final boolean ascending) throws IOException {
        checkGet(namespace, set, min, max, start, count, ascending);
        return submitCall(() -> this.delegate.get(namespace, set, min, max, start, count, ascending));
    }

    @Override
    public void delete(final String namespace, final String set, final long min, final long max) throws IOException {
        checkDelete(namespace, set, min, max);
        submitCall(() -> { this.delegate.delete(namespace, set, min, max); return null; });
    }

    @Override
    public final boolean delete(final String namespace, final String set, final String entry) throws IOException {
        checkDelete(namespace, set, entry);
        return submitCall(() -> this.delegate.delete(namespace, set, entry));
    }

    @Override
    public void delete(final String namespace, final String set, final Collection<String> entries) throws IOException {
        checkDelete(namespace, set, entries);
        submitCall(() -> { this.delegate.delete(namespace, set, entries); return null; });
    }

    @Override
    public Map<String, Long> union(final String namespace,
                                   final Collection<String> sets,
                                   final long min,
                                   final long max,
                                   final int start,
                                   final int count,
                                   final boolean ascending) throws IOException {
        checkUnion(namespace, sets, min, max, start, count, ascending);
        return submitCall(() -> this.delegate.union(namespace, sets, min, max, start, count, ascending));
    }

    @Override
    public Map<String, Long> intersect(final String namespace,
                                       final Collection<String> sets,
                                       final long min,
                                       final long max,
                                       final int start,
                                       final int count,
                                       final boolean ascending) throws IOException {
        checkIntersect(namespace, sets, min, max, start, count, ascending);
        return submitCall(() -> this.delegate.intersect(namespace, sets, min, max, start, count, ascending));
    }

    @Override
    public Map<String, Long> pop(final String namespace,
                                 final String set,
                                 final long min,
                                 final long max,
                                 final int start,
                                 final int count,
                                 final boolean ascending) throws IOException {
        checkPop(namespace, set, min, max, start, count, ascending);
        return submitCall(() -> this.delegate.pop(namespace, set, min, max, start, count, ascending));
    }

    @Override
    public Collection<String> sets(final String namespace) throws IOException {
        checkSets(namespace);
        return submitCall(() -> this.delegate.sets(namespace));
    }

    @Override
    public final int size(final String namespace, final String set) throws IOException {
        checkSize(namespace, set);
        return submitCall(() -> this.delegate.size(namespace, set));
    }

    @Override
    public Long weight(final String namespace, final String set, final String entry) throws IOException {
        checkWeight(namespace, set, entry);
        return submitCall(() -> this.delegate.weight(namespace, set, entry));
    }

    @Override
    public void inc(final String namespace, final String set, final String entry, final long count) throws IOException {
        checkInc(namespace, set, entry, count);
        submitCall(() -> { this.delegate.inc(namespace, set, entry, count); return null; });
    }
}
