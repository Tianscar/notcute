package io.notcute.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class IOUtils {

    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    private IOUtils() {
        throw new UnsupportedOperationException();
    }

    public static void skipAllBytes(InputStream in) throws IOException {
        skipNBytes(in, Integer.MAX_VALUE);
    }

    public static void skipNBytes(InputStream in, int n) throws IOException {
        while (n > 0) {
            long ns = in.skip(n);
            if (ns > 0 && ns <= n) {
                // adjust number to skip
                n -= ns;
            }
            else if (ns == 0) { // no bytes skipped
                // read one byte to check for EOS
                if (in.read() == -1) {
                    break;
                }
                // one byte read so decrement number to skip
                n--;
            }
            else { // skipped negative or too many bytes
                throw new IOException("Unable to skip exactly");
            }
        }
    }

    public static byte[] readAllBytes(InputStream in) throws IOException {
        return readNBytes(in, Integer.MAX_VALUE);
    }

    public static byte[] readNBytes(InputStream in, int n) throws IOException {
        if (n < 0) {
            throw new IllegalArgumentException("n < 0");
        }
        Objects.requireNonNull(in);

        List<byte[]> bufs = null;
        byte[] result = null;
        int total = 0;
        int remaining = n;
        int nr;
        do {
            byte[] buf = new byte[Math.min(remaining, DEFAULT_BUFFER_SIZE)];
            int nread = 0;

            // read to EOF which may read more or less than buffer size
            while ((nr = in.read(buf, nread,
                    Math.min(buf.length - nread, remaining))) > 0) {
                nread += nr;
                remaining -= nr;
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
        } while (nr >= 0 && remaining > 0);

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

}
