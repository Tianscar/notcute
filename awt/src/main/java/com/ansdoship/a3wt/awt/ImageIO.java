/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ansdoship.a3wt.awt;

import com.twelvemonkeys.imageio.plugins.jpeg.JPEGImageWriter;

import javax.imageio.ImageReader;
import javax.imageio.ImageTranscoder;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ImageReaderWriterSpi;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.spi.ImageOutputStreamSpi;
import javax.imageio.spi.ServiceRegistry;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.net.URL;

public final class ImageIO {

    private static final IIORegistry registry = IIORegistry.getDefaultInstance();
    private static final Cache cacheInfo =  new Cache();
    
    private ImageIO() {}

    public static void scanForPlugins() {
        registry.registerApplicationClasspathSpis();
    }

    public static void setUseCache(boolean useCache) {
        cacheInfo.setUseCache(useCache);
    }

    public static boolean getUseCache() {
        return cacheInfo.getUseCache();
    }

    public static void setCacheDirectory(File cacheDirectory) {
        cacheInfo.setCacheDirectory(cacheDirectory);
    }

    public static File getCacheDirectory() {
        return cacheInfo.getCacheDirectory();
    }

    public static ImageInputStream createImageInputStream(Object input)
            throws IOException {

        if (input == null) {
            throw new IllegalArgumentException("input source cannot be NULL");
        }

        Iterator<ImageInputStreamSpi> it = registry.getServiceProviders(ImageInputStreamSpi.class, true);

        while (it.hasNext()) {
            ImageInputStreamSpi spi = it.next();
            if (spi.getInputClass().isInstance(input)) {
                return getUseCache() ?
                		spi.createInputStreamInstance(input, true, getCacheDirectory()) :
                		spi.createInputStreamInstance(input);
            }
        }
        return null;
    }

    public static ImageOutputStream createImageOutputStream(Object output)
            throws IOException {
        if (output == null) {
            throw new IllegalArgumentException("output destination cannot be NULL");
        }

        Iterator<ImageOutputStreamSpi> it = registry.getServiceProviders(ImageOutputStreamSpi.class, true);

        while (it.hasNext()) {
            ImageOutputStreamSpi spi = it.next();
            if (spi.getOutputClass().isInstance(output)) {
            	return getUseCache() ?
            			spi.createOutputStreamInstance(output, true, getCacheDirectory()) :
            		    spi.createOutputStreamInstance(output);
            }
        }
        return null;
    }

    public static String[] getReaderFormatNames() {
        List<String> FormatNames = new ArrayList<String>();

        Iterator<ImageReaderSpi> it = registry.getServiceProviders(ImageReaderSpi.class, true);
        while (it.hasNext()) {
            ImageReaderSpi spi = it.next();
            FormatNames.addAll(Arrays.asList(spi.getFormatNames()));
        }

        return (String[])FormatNames.toArray(new String[FormatNames.size()]);
    }

    public static String[] getReaderMIMETypes() {
        List<String> MIMETypes = new ArrayList<String>();

        Iterator<ImageReaderSpi> it = registry.getServiceProviders(ImageReaderSpi.class, true);
        while (it.hasNext()) {
            ImageReaderSpi spi = it.next();
            MIMETypes.addAll(Arrays.asList(spi.getMIMETypes()));
        }

        return (String[])MIMETypes.toArray(new String[MIMETypes.size()]);
    }

    public static Iterator<ImageReader> getImageReaders(Object input) {
        if (input == null) {
            throw new NullPointerException("input cannot be NULL");
        }

        Iterator<ImageReaderSpi> it = registry.getServiceProviders(ImageReaderSpi.class,
                new CanReadFilter(input), true);

        return new SpiIteratorToReadersIteratorWrapper(it);
    }

    public static Iterator<ImageReader> getImageReadersByFormatName(String formatName) {
        if (formatName == null) {
            throw new NullPointerException("format name cannot be NULL");
        }

        Iterator<ImageReaderSpi> it = registry.getServiceProviders(ImageReaderSpi.class,
                new FormatFilter(formatName), true);

        return new SpiIteratorToReadersIteratorWrapper(it);
    }

    public static Iterator<ImageReader> getImageReadersBySuffix(String fileSuffix) {
        if (fileSuffix == null) {
            throw new NullPointerException("suffix cannot be NULL");
        }
        Iterator<ImageReaderSpi> it = registry.getServiceProviders(ImageReaderSpi.class,
                new SuffixFilter(fileSuffix), true);

        return new SpiIteratorToReadersIteratorWrapper(it);
    }

    public static Iterator<ImageReader> getImageReadersByMIMEType(
                    final String MIMEType) {
        return new SpiIteratorToReadersIteratorWrapper(
                registry.getServiceProviders(ImageReaderSpi.class,
                new MIMETypeFilter(MIMEType), true));
    }

    public static String[] getWriterFormatNames() {
        ArrayList<String> FormatNames = new ArrayList<String>();

        Iterator<ImageWriterSpi> it = registry.getServiceProviders(ImageWriterSpi.class, true);
        while (it.hasNext()) {
            ImageWriterSpi spi = it.next();
            FormatNames.addAll(Arrays.asList(spi.getFormatNames()));
        }

        return (String[])FormatNames.toArray(new String[FormatNames.size()]);
    }

    public static String[] getWriterMIMETypes() {
        ArrayList<String> MIMETypes = new ArrayList<String>();

        Iterator<ImageWriterSpi> it = registry.getServiceProviders(ImageWriterSpi.class, true);
        while (it.hasNext()) {
            ImageWriterSpi spi = it.next();
            MIMETypes.addAll(Arrays.asList(spi.getMIMETypes()));
        }

        return (String[])MIMETypes.toArray(new String[MIMETypes.size()]);
    }

    public static Iterator<ImageWriter> getImageWritersByFormatName(String formatName) {
        if (formatName == null) {
            throw new NullPointerException("format name cannot be NULL");
        }

        Iterator<ImageWriterSpi> it = registry.getServiceProviders(ImageWriterSpi.class,
                new FormatFilter(formatName), true);

        return new SpiIteratorToWritersIteratorWrapper(it);
    }

    public static Iterator<ImageWriter> getImageWritersBySuffix(String fileSuffix) {
        if (fileSuffix == null) {
            throw new NullPointerException("suffix cannot be NULL");
        }
        Iterator<ImageWriterSpi> it = registry.getServiceProviders(ImageWriterSpi.class,
                new SuffixFilter(fileSuffix), true);
        return new SpiIteratorToWritersIteratorWrapper(it);
    }

    public static Iterator<ImageWriter> getImageWritersByMIMEType(
                    final String MIMEType) {
        return new SpiIteratorToWritersIteratorWrapper(
                registry.getServiceProviders(ImageWriterSpi.class,
                new MIMETypeFilter(MIMEType), true));
    }

    public static ImageWriter getImageWriter(ImageReader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null"); //$NON-NLS-1$
        }

        ImageReaderSpi readerSpi = reader.getOriginatingProvider();
        if (readerSpi.getImageWriterSpiNames() == null) {
            return null;
        }

        String writerSpiName = readerSpi.getImageWriterSpiNames()[0];

        Iterator<ImageWriterSpi> writerSpis;
        writerSpis = registry.getServiceProviders(ImageWriterSpi.class, true);

        try {
            while (writerSpis.hasNext()) {
                ImageWriterSpi writerSpi = writerSpis.next();
                if (writerSpi.getClass().getName().equals(writerSpiName)) {
                    return writerSpi.createWriterInstance();
                }
            }
        } catch (IOException e) {
            // Ignored
        }

        return null;
    }

    public static ImageReader getImageReader(ImageWriter writer) {
        if (writer == null) {
            throw new IllegalArgumentException("Writer cannot be null"); //$NON-NLS-1$
        }
        ImageWriterSpi writerSpi = writer.getOriginatingProvider();
        if (writerSpi.getImageReaderSpiNames() == null) {
            return null;
        }

        String readerSpiName = writerSpi.getImageReaderSpiNames()[0];

        Iterator<ImageReaderSpi> readerSpis;
        readerSpis = registry.getServiceProviders(ImageReaderSpi.class, true);

        try {
            while (readerSpis.hasNext()) {
                ImageReaderSpi readerSpi = readerSpis.next();
                if (readerSpi.getClass().getName().equals(readerSpiName)) {
                    return readerSpi.createReaderInstance();
                }
            }
        } catch (IOException e) {
            // Ignored
        }

        return null;
    }

    public static Iterator<ImageWriter> getImageWriters(ImageTypeSpecifier type,
                                                        String formatName) {
        if (type == null) {
            throw new NullPointerException("type cannot be NULL");
        }

        if (formatName == null) {
            throw new NullPointerException("format name cannot be NULL");
        }

        Iterator<ImageWriterSpi> it = registry.getServiceProviders(ImageWriterSpi.class,
                new FormatAndEncodeFilter(type, formatName), true);

        return new SpiIteratorToWritersIteratorWrapper(it);
    }

    public static Iterator<ImageTranscoder> getImageTranscoders(ImageReader reader,
                                                                ImageWriter writer) {
        // Apache Harmony not implement it yet, so fallback to jdk's javax.imageio
        return javax.imageio.ImageIO.getImageTranscoders(reader, writer);
        // TODO: implement
    }

    public static BufferedImage read(File input) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("input == null!");
        }

        ImageInputStream stream = createImageInputStream(input);
        return read(stream);
    }

    public static BufferedImage read(InputStream input) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("input == null!");
        }

        ImageInputStream stream = createImageInputStream(input);
        return read(stream);
    }

    public static BufferedImage read(URL input) throws IOException {
        if (input == null) {
            throw new IllegalArgumentException("input == null!");
        }

        InputStream stream = input.openStream();
        BufferedImage res = read(stream);
        stream.close();
        
        return res;
    }

    public static BufferedImage read(ImageInputStream stream) throws IOException {
        if (stream == null) {
            throw new IllegalArgumentException("stream == null!");
        }

        Iterator<ImageReader> imageReaders = getImageReaders(stream);
        if (!imageReaders.hasNext()) {
            return null;
        }

        ImageReader reader = imageReaders.next();
        reader.setInput(stream, false, true);
        BufferedImage res = reader.read(0);
        reader.dispose();

        try {
            stream.close();
        } catch (IOException e) {
            // Stream could be already closed, proceed silently in this case
        }
        
        return res;
    }

    public static boolean write(RenderedImage im,
                                String formatName,
                                float quality,
                                ImageOutputStream output)
            throws IOException {

        if (im == null) {
            throw new IllegalArgumentException("image cannot be NULL");
        }
        if (formatName == null) {
            throw new IllegalArgumentException("format name cannot be NULL");
        }
        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }

        Iterator<ImageWriter> it = getImageWriters(ImageTypeSpecifier.createFromRenderedImage(im), formatName);
        ImageWriter writer;
        String compressionType = null;
        if (it.hasNext()) {
            writer = it.next();
            ImageWriteParam params = writer.getDefaultWriteParam();
            if (writer instanceof com.sun.imageio.plugins.bmp.BMPImageWriter) {
                compressionType = "BI_RGB";
            }
            else if (writer instanceof JPEGImageWriter || writer instanceof com.sun.imageio.plugins.jpeg.JPEGImageWriter) {
                compressionType = "JPEG";
            }
            if (params.canWriteCompressed()) {
                params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                params.setCompressionType(compressionType);
                params.setCompressionQuality(quality);
            }
            writer.setOutput(output);
            writer.write(im);
            output.flush();
            writer.dispose();
            return true;
        }
        return false;
    }

    public static boolean write(RenderedImage im,
                                String formatName,
                                float quality,
                                File output)
            throws IOException {

        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }

        if (output.exists()) {
            output.delete();
        }

        ImageOutputStream ios = createImageOutputStream(output);
        boolean rt = write(im, formatName, quality, ios);
        ios.close();
        return rt;
    }

    public static boolean write(RenderedImage im,
                                String formatName,
                                float quality,
                                OutputStream output)
            throws IOException {

        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }

        ImageOutputStream ios = createImageOutputStream(output);
        boolean rt = write(im, formatName, quality, ios);
        ios.close();
        return rt;
    }

    private static class Cache {
        private boolean useCache = false;
        private File cacheDirectory = null;
        
        public Cache() {
        }
    	
        public File getCacheDirectory() {
            return cacheDirectory;
        }
    	
        public void setCacheDirectory(File cacheDirectory) {
            if ((cacheDirectory != null) && (!cacheDirectory.isDirectory())) {
                throw new IllegalArgumentException("Not a directory!");
            }

            /*
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                String filepath;
                
                if (cacheDirectory == null) {
                    filepath = System.getProperty("java.io.tmpdir");
                } else {
                    filepath = cacheDirectory.getPath();
                }
                
                security.checkWrite(filepath);
            }
             */
            
            this.cacheDirectory = cacheDirectory;
        }
        
        public boolean getUseCache() {
            return useCache;
        }
        
        public void setUseCache(boolean useCache) {
            this.useCache = useCache;
        }
    }

    /**
     * Filter to match spi by format name
     */
    static class FormatFilter implements ServiceRegistry.Filter {
        private String name;

        public FormatFilter(String name) {
            this.name = name;
        }

        public boolean filter(Object provider) {
            ImageReaderWriterSpi spi = (ImageReaderWriterSpi) provider;
            return Arrays.asList(spi.getFormatNames()).contains(name);
        }
    }

    /**
     * Filter to match spi by format name and encoding possibility
     */
    static class FormatAndEncodeFilter extends FormatFilter {

        private ImageTypeSpecifier type;

        public FormatAndEncodeFilter(ImageTypeSpecifier type, String name) {
            super(name);
            this.type = type;
        }

        @Override
        public boolean filter(Object provider) {
            ImageWriterSpi spi = (ImageWriterSpi) provider;
            return super.filter(provider) && spi.canEncodeImage(type);
        }
    }

    /**
     * Filter to match spi by suffix
     */
    static class SuffixFilter implements ServiceRegistry.Filter {
        private String suf;

        public SuffixFilter(String suf) {
            this.suf = suf;
        }

        public boolean filter(Object provider) {
            ImageReaderWriterSpi spi = (ImageReaderWriterSpi) provider;
            return Arrays.asList(spi.getFileSuffixes()).contains(suf);
        }
    }

    /**
     * Filter to match spi by MIMEType
     */
    static class MIMETypeFilter implements ServiceRegistry.Filter {
        private final String mimeType;

        public MIMETypeFilter(final String mimeType) {
            if (mimeType == null) {
                throw new NullPointerException("MIMEType cannot be NULL");
            }
            
            this.mimeType = mimeType;
        }

        public boolean filter(final Object provider) {
            final String[] types = ((ImageReaderWriterSpi) provider).getMIMETypes();
            return (types != null) && Arrays.asList(types).contains(mimeType);
        }
    }

    /**
     * Filter to match spi by decoding possibility
     */
    static class CanReadFilter implements ServiceRegistry.Filter {
        private Object input;

        public CanReadFilter(Object input) {
            this.input = input;
        }

        public boolean filter(Object provider) {
            ImageReaderSpi spi = (ImageReaderSpi) provider;
            try {
                return spi.canDecodeInput(input);
            } catch (IOException e) {
                return false;
            }
        }
    }

    /**
     * Wraps Spi's iterator to ImageWriter iterator
     */
    static class SpiIteratorToWritersIteratorWrapper implements Iterator<ImageWriter> {

        private Iterator<ImageWriterSpi> backend;

        public SpiIteratorToWritersIteratorWrapper(Iterator<ImageWriterSpi> backend) {
            this.backend = backend;
        }

        public ImageWriter next() {
            try {
                return backend.next().createWriterInstance();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public boolean hasNext() {
            return backend.hasNext();
        }

        public void remove() {
            throw new UnsupportedOperationException("Use deregisterServiceProvider instead of Iterator.remove()");
        }
    }

    /**
     * Wraps spi's iterator to ImageReader iterator
     */
    static class SpiIteratorToReadersIteratorWrapper implements Iterator<ImageReader> {
        private Iterator<ImageReaderSpi> backend;

        public SpiIteratorToReadersIteratorWrapper(Iterator<ImageReaderSpi> backend) {
            this.backend = backend;
        }

        public ImageReader next() {
            try {
                return backend.next().createReaderInstance();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public boolean hasNext() {
            return backend.hasNext();
        }

        public void remove() {
            throw new UnsupportedOperationException("Use deregisterServiceProvider instead of Iterator.remove()");
        }
    }
}
