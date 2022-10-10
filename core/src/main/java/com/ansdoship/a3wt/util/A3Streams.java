package com.ansdoship.a3wt.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.Closeable;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class A3Streams {

    private A3Streams(){}

    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    public static void transferTo(final InputStream source, final OutputStream target) throws IOException {
        checkArgNotNull(source, "source");
        checkArgNotNull(target, "target");
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int length;
        while ((length = source.read(buffer)) != -1) {
            target.write(buffer, 0, length);
        }
    }

    public static byte[] readBytesAndClose(final InputStream stream) throws IOException {
        checkArgNotNull(stream, "stream");
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int count;
            while ((count = stream.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            return output.toByteArray();
        }
        finally {
            stream.close();
        }
    }

    public static int readNBytes(final InputStream stream, final byte[] b, final int off, final int len) throws IOException {
        checkArgNotNull(stream, "stream");
        int n = 0;
        while (n < len) {
            int count = stream.read(b, off + n, len - n);
            if (count < 0)
                return count;
            n += count;
        }
        return n;
    }

    public static byte[] readNBytes(final InputStream stream, final int len) throws IOException {
        checkArgNotNull(stream, "stream");
        if (len < 0) {
            throw new IllegalArgumentException("len < 0");
        }

        List<byte[]> bufs = null;
        byte[] result = null;
        int total = 0;
        int remaining = len;
        int n;
        do {
            byte[] buf = new byte[Math.min(remaining, DEFAULT_BUFFER_SIZE)];
            int nread = 0;

            // read to EOF which may read more or less than buffer size
            while ((n = stream.read(buf, nread,
                    Math.min(buf.length - nread, remaining))) > 0) {
                nread += n;
                remaining -= n;
            }

            if (nread > 0) {
                if (MAX_BUFFER_SIZE - total < nread) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                if (nread < buf.length) {
                    buf = Arrays.copyOfRange(buf, 0, nread);
                }
                total += nread;
                if (result == null) {
                    result = buf;
                } else {
                    if (bufs == null) {
                        bufs = new ArrayList<>();
                        bufs.add(result);
                    }
                    bufs.add(buf);
                }
            }
            // if the last call to read returned -1 or the number of bytes
            // requested have been read then break
        } while (n >= 0 && remaining > 0);

        if (bufs == null) {
            if (result == null) {
                return new byte[0];
            }
            return result.length == total ?
                    result : Arrays.copyOf(result, total);
        }

        result = new byte[total];
        int offset = 0;
        remaining = total;
        for (byte[] b : bufs) {
            int count = Math.min(b.length, remaining);
            System.arraycopy(b, 0, result, offset, count);
            offset += count;
            remaining -= count;
        }

        return result;
    }

    public static void skipNBytes(final InputStream stream, long n) throws IOException {
        checkArgNotNull(stream, "stream");
        while (n > 0) {
            long ns = stream.skip(n);
            if (ns > 0 && ns <= n) {
                // adjust number to skip
                n -= ns;
            } else if (ns == 0) { // no bytes skipped
                // read one byte to check for EOS
                if (stream.read() == -1) {
                    throw new EOFException();
                }
                // one byte read so decrement number to skip
                n--;
            } else { // skipped negative or too many bytes
                throw new IOException("Unable to skip exactly");
            }
        }
    }

    public static void closeQuietly(final /*Auto*/Closeable closeable) {
        checkArgNotNull(closeable, "closeable");
        try {
            closeable.close();
        } catch (RuntimeException rethrown) {
            throw rethrown;
        } catch (Exception ignored) {
        }
    }

}
