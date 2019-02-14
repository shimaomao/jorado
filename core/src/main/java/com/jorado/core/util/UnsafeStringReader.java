package com.jorado.core.util;

import java.io.IOException;
import java.io.Reader;


public class UnsafeStringReader extends Reader {

    private String string;

    private int position, limit, mark;

    public UnsafeStringReader(String str) {
        string = str;
        limit = str.length();
        position = mark = 0;
    }

    @Override
    public int read() throws IOException {
        ensureOpen();
        if (position >= limit) return -1;

        return string.charAt(position++);
    }

    @Override
    public int read(char[] cs, int off, int len) throws IOException {
        ensureOpen();
        if ((off < 0) || (off > cs.length) || (len < 0) || ((off + len) > cs.length) || ((off + len) < 0))
            throw new IndexOutOfBoundsException();

        if (len == 0) return 0;

        if (position >= limit) return -1;

        int n = Math.min(limit - position, len);
        string.getChars(position, position + n, cs, off);
        position += n;
        return n;
    }

    public long skip(long ns) throws IOException {
        ensureOpen();
        if (position >= limit) return 0;

        long n = Math.min(limit - position, ns);
        n = Math.max(-position, n);
        position += n;
        return n;
    }

    public boolean ready() throws IOException {
        ensureOpen();
        return true;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    public void mark(int readAheadLimit) throws IOException {
        if (readAheadLimit < 0) throw new IllegalArgumentException("Read-ahead limit < 0");

        ensureOpen();
        mark = position;
    }

    public void reset() throws IOException {
        ensureOpen();
        position = mark;
    }

    @Override
    public void close() throws IOException {
        string = null;
    }

    private void ensureOpen() throws IOException {
        if (string == null) throw new IOException("Stream closed");
    }
}