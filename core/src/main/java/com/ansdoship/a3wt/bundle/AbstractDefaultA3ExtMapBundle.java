package com.ansdoship.a3wt.bundle;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.util.A3Arrays;
import com.ansdoship.a3wt.util.A3Charsets;
import com.ansdoship.a3wt.util.A3Maps;
import com.ansdoship.a3wt.util.A3TextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgElementsNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public abstract class AbstractDefaultA3ExtMapBundle implements A3ExtMapBundle {

    protected static final String ROOT_TAG = A3ExtMapBundle.class.getCanonicalName();
    protected static final String DELEGATE_CLASS_TAG = A3ExtMapBundle.Delegate.class.getCanonicalName();

    protected final A3BundleKit bundleKit;
    protected final Map<String, Value> map;

    protected AbstractDefaultA3ExtMapBundle(final A3BundleKit bundleKit, final Map<String, Value> map) {
        checkArgNotNull(bundleKit, "bundleKit");
        checkArgNotNull(map, "map");
        this.bundleKit = bundleKit;
        this.map = map;
    }

    protected abstract AbstractDefaultA3ExtMapBundle createExtMapBundle();

    @Override
    public boolean save(final File output, final String format) {
        checkArgNotNull(output, "output");
        checkArgNotNull(format, "format");
        try (final FileOutputStream stream = new FileOutputStream(output)) {
            return save(stream, format);
        }
        catch (final IOException e) {
            return false;
        }
    }

    protected static class XMLSaver implements Saver {

        protected final Document document;
        protected final Node node;

        public XMLSaver(final Document document, final Node node) {
            checkArgNotNull(document, "document");
            checkArgNotNull(node, "node");
            this.document = document;
            this.node = node;
        }

        private Saver put(final String key, final String value) {
            checkArgNotNull(key, "key");
            checkArgNotNull(value, "value");
            Element element = document.createElement(key);
            element.appendChild(document.createTextNode(value));
            node.appendChild(element);
            return this;
        }

        @Override
        public Saver putByte(final String key, final byte value) {
            return put(key, Byte.toString(value));
        }

        @Override
        public Saver putShort(final String key, final short value) {
            return put(key, Short.toString(value));
        }

        @Override
        public Saver putInt(final String key, final int value) {
            return put(key, Integer.toString(value));
        }

        @Override
        public Saver putLong(final String key, final long value) {
            return put(key, Long.toString(value));
        }

        @Override
        public Saver putFloat(final String key, final float value) {
            return put(key, Float.toString(value));
        }

        @Override
        public Saver putDouble(final String key, final double value) {
            return put(key, Double.toString(value));
        }

        @Override
        public Saver putBoolean(final String key, final boolean value) {
            return put(key, Boolean.toString(value));
        }

        @Override
        public Saver putChar(final String key, final char value) {
            return put(key, Character.toString(value));
        }

        @Override
        public Saver putString(final String key, final String value) {
            return put(key, value == null ? "null" : value);
        }

        @Override
        public Saver putBigInteger(final String key, final BigInteger value) {
            checkArgNotNull(value, "value");
            return put(key, value.toString());
        }

        @Override
        public Saver putBigDecimal(final String key, final BigDecimal value) {
            checkArgNotNull(value, "value");
            return put(key, value.toPlainString());
        }

        @Override
        public Saver putExtMapBundle(final String key, final A3ExtMapBundle value) {
            checkArgNotNull(key, "key");
            checkArgNotNull(value, "value");
            saveToDocument(value, document, node.appendChild(document.createElement(key)));
            return this;
        }

        @Override
        public <T extends Delegate> Saver putDelegate(String key, T value) {
            checkArgNotNull(key, "key");
            checkArgNotNull(value, "value");
            Node node = this.node.appendChild(document.createElement(key));
            Element classTag = document.createElement(DELEGATE_CLASS_TAG);
            classTag.appendChild(document.createTextNode(value.typeClass().getCanonicalName()));
            node.appendChild(classTag);
            value.save(new XMLSaver(document, node));
            return this;
        }

        @Override
        public Saver putByteArray(final String key, final byte[] value) {
            checkArgNotNull(value, "value");
            return put(key, Arrays.toString(value));
        }

        @Override
        public Saver putShortArray(final String key, final short[] value) {
            checkArgNotNull(value, "value");
            return put(key, Arrays.toString(value));
        }

        @Override
        public Saver putIntArray(final String key, final int[] value) {
            checkArgNotNull(value, "value");
            return put(key, Arrays.toString(value));
        }

        @Override
        public Saver putLongArray(final String key, final long[] value) {
            checkArgNotNull(value, "value");
            return put(key, Arrays.toString(value));
        }

        @Override
        public Saver putFloatArray(final String key, final float[] value) {
            checkArgNotNull(value, "value");
            return put(key, Arrays.toString(value));
        }

        @Override
        public Saver putDoubleArray(final String key, final double[] value) {
            checkArgNotNull(value, "value");
            return put(key, Arrays.toString(value));
        }

        @Override
        public Saver putBooleanArray(final String key, final boolean[] value) {
            checkArgNotNull(value, "value");
            return put(key, Arrays.toString(value));
        }

        @Override
        public Saver putCharArray(final String key, final char[] value) {
            checkArgNotNull(value, "value");
            return put(key, Arrays.toString(value));
        }

        @Override
        public Saver putStringArray(final String key, final String[] value) {
            checkArgElementsNotNull(value, "value");
            return put(key, Arrays.toString(value));
        }

        @Override
        public Saver putBigIntegerArray(final String key, final BigInteger[] value) {
            checkArgElementsNotNull(value, "value");
            return put(key, Arrays.toString(value));
        }

        @Override
        public Saver putBigDecimalArray(final String key, final BigDecimal[] value) {
            checkArgElementsNotNull(value, "value");
            return put(key, A3Arrays.toPlainString(value));
        }
        
    }

    protected static class XMLRestorer implements Restorer {

        protected final AbstractDefaultA3ExtMapBundle bundle;
        protected final Node node;

        public XMLRestorer(final AbstractDefaultA3ExtMapBundle bundle, final Node node) {
            checkArgNotNull(bundle, "bundle");
            checkArgNotNull(node, "node");
            this.bundle = bundle;
            this.node = node;
        }

        private Node get(final String key) {
            checkArgNotNull(key, "key");
            Node child;
            for (int i = 0; i < node.getChildNodes().getLength(); i ++) {
                child = node.getChildNodes().item(i);
                if (child.getNodeName().equals(key)) return child;
            }
            return null;
        }

        private String get(final String key, final String defValue) {
            checkArgNotNull(key, "key");
            Node child = get(key);
            if (child == null) return defValue;
            else if (child.getChildNodes().getLength() == 1 && child.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                return child.getFirstChild().getTextContent();
            }
            else return defValue;
        }

        @Override
        public byte getByte(final String key, final byte defValue) {
            final String value = get(key, null);
            return value == null ? defValue : Byte.parseByte(value);
        }

        @Override
        public short getShort(final String key, final short defValue) {
            final String value = get(key, null);
            return value == null ? defValue : Short.parseShort(value);
        }

        @Override
        public int getInt(final String key, final int defValue) {
            final String value = get(key, null);
            return value == null ? defValue : Integer.parseInt(value);
        }

        @Override
        public long getLong(final String key, final long defValue) {
            final String value = get(key, null);
            return value == null ? defValue : Long.parseLong(value);
        }

        @Override
        public float getFloat(final String key, final float defValue) {
            final String value = get(key, null);
            return value == null ? defValue : Float.parseFloat(value);
        }

        @Override
        public double getDouble(final String key, final double defValue) {
            final String value = get(key, null);
            return value == null ? defValue : Double.parseDouble(value);
        }

        @Override
        public boolean getBoolean(final String key, final boolean defValue) {
            final String value = get(key, null);
            return value == null ? defValue : Boolean.parseBoolean(value);
        }

        @Override
        public char getChar(final String key, final char defValue) {
            final String value = get(key, null);
            return value == null ? defValue : value.charAt(0);
        }

        @Override
        public String getString(final String key, final String defValue) {
            return get(key, defValue);
        }

        @Override
        public BigInteger getBigInteger(final String key, final BigInteger defValue) {
            final String value = get(key, null);
            return value == null ? defValue : new BigInteger(value);
        }

        @Override
        public BigDecimal getBigDecimal(final String key, final BigDecimal defValue) {
            final String value = get(key, null);
            return value == null ? defValue : new BigDecimal(value);
        }

        @Override
        public A3ExtMapBundle getExtMapBundle(final String key, final A3ExtMapBundle defValue) {
            Node child = get(key);
            if (child == null) return defValue;
            else {
                AbstractDefaultA3ExtMapBundle extMapBundle = bundle.createExtMapBundle();
                restoreFromDocument(extMapBundle, child);
                return extMapBundle;
            }
        }

        @Override
        public <T extends Delegate> T getDelegate(final String key, final T defValue) {
            Node child = get(key);
            Node firstElement;
            if (child == null) return defValue;
            else if ((firstElement = child.getFirstChild()) != null && firstElement.getNodeName().equals(DELEGATE_CLASS_TAG)) {
                try {
                    if (firstElement.getChildNodes().getLength() != 1 || firstElement.getFirstChild().getNodeType() != Node.TEXT_NODE) return defValue;
                    Class<?> typeClass = Class.forName(firstElement.getFirstChild().getTextContent());
                    if (Delegate.class.isAssignableFrom(typeClass)) {
                        Delegate delegate = bundle.bundleKit.createDelegate((Class<? extends Delegate>) typeClass);
                        delegate.restore(new XMLRestorer(bundle, child));
                        return (T) delegate;
                    }
                    else return defValue;
                }
                catch (final ClassNotFoundException e) {
                    return defValue;
                }
            }
            else return defValue;
        }

        @Override
        public byte[] getByteArray(final String key, final byte[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseByteArray(value);
        }

        @Override
        public short[] getShortArray(final String key, final short[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseShortArray(value);
        }

        @Override
        public int[] getIntArray(final String key, final int[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseIntArray(value);
        }

        @Override
        public long[] getLongArray(final String key, final long[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseLongArray(value);
        }

        @Override
        public float[] getFloatArray(final String key, final float[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseFloatArray(value);
        }

        @Override
        public double[] getDoubleArray(final String key, final double[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseDoubleArray(value);
        }

        @Override
        public boolean[] getBooleanArray(final String key, final boolean[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseBooleanArray(value);
        }

        @Override
        public char[] getCharArray(final String key, final char[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseCharArray(value);
        }

        @Override
        public String[] getStringArray(final String key, final String[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseStringArray(value);
        }

        @Override
        public BigInteger[] getBigIntegerArray(final String key, final BigInteger[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseBigIntegerArray(value);
        }

        @Override
        public BigDecimal[] getBigDecimalArray(final String key, final BigDecimal[] defValue) {
            final String value = get(key, null);
            return value == null ? defValue : A3TextUtils.parseBigDecimalArray(value);
        }

    }

    private static void saveToDocument(final A3ExtMapBundle bundle, final Document document, final Node node) {
        for (final String key : bundle.keySet()) {
            Value value = bundle.get(key);
            Element element = document.createElement(key);
            if (value.typeClass().isAssignableFrom(String.class)) {
                element.appendChild(document.createTextNode((String) value.getValue()));
                node.appendChild(element);
            }
            else if (Delegate.class.isAssignableFrom(value.typeClass())) {
                Delegate delegate = (Delegate) value.getValue();
                Element classTag = document.createElement(DELEGATE_CLASS_TAG);
                classTag.appendChild(document.createTextNode(delegate.typeClass().getCanonicalName()));
                element.appendChild(classTag);
                delegate.save(new XMLSaver(document, node.appendChild(element)));
            }
            else if (A3ExtMapBundle.class.isAssignableFrom(value.typeClass())) {
                saveToDocument((A3ExtMapBundle) value.getValue(), document, node.appendChild(element));
            }
        }
    }

    @Override
    public boolean save(final OutputStream output, final String format) {
        checkArgNotNull(output, "output");
        checkArgNotNull(format, "format");
        try (BufferedOutputStream stream = new BufferedOutputStream(output)) {
            switch (format.toLowerCase()) {
                case "xml":
                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                    try {
                        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                        Document document = documentBuilder.newDocument();
                        saveToDocument(this, document, document.appendChild(document.createElement(ROOT_TAG)));
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        transformer.setOutputProperty(OutputKeys.ENCODING, A3Charsets.UTF_8.name());
                        DOMSource source = new DOMSource(document);
                        StreamResult result = new StreamResult(stream);
                        transformer.transform(source, result);
                        return true;
                    }
                    catch (ParserConfigurationException e) {
                        return false;
                    }
                    catch (TransformerException e) {
                        return false;
                    }
                default:
                    return false;
            }
        }
        catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean restore(final File input) {
        checkArgNotNull(input, "input");
        try (final FileInputStream fileInputStream = new FileInputStream(input);
             final BufferedInputStream stream = new BufferedInputStream(fileInputStream)) {
            return restore(stream);
        }
        catch (final IOException e) {
            return false;
        }
    }

    private static Node getFirstElementChild(final Node node) {
        Node child;
        for (int i = 0; i < node.getChildNodes().getLength(); i ++) {
            child = node.getChildNodes().item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) return child;
        }
        return null;
    }

    private static void restoreFromDocument(final AbstractDefaultA3ExtMapBundle bundle, final Node node) {
        Node child;
        Node firstElement;
        for (int i = 0; i < node.getChildNodes().getLength(); i ++) {
            child = node.getChildNodes().item(i);
            if (!child.hasChildNodes() || child.getNodeType() != Node.ELEMENT_NODE) continue;
            firstElement = getFirstElementChild(child);
            if (child.getChildNodes().getLength() == 1 && child.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                bundle.putString(child.getNodeName(), child.getFirstChild().getTextContent());
            }
            else if (firstElement != null && firstElement.getNodeName().equals(DELEGATE_CLASS_TAG)) {
                try {
                    if (firstElement.getChildNodes().getLength() != 1 || firstElement.getFirstChild().getNodeType() != Node.TEXT_NODE) continue;
                    Class<?> typeClass = Class.forName(firstElement.getFirstChild().getTextContent());
                    if (Delegate.class.isAssignableFrom(typeClass)) {
                        Delegate delegate = bundle.bundleKit.createDelegate((Class<? extends Delegate>) typeClass);
                        XMLRestorer restorer = new XMLRestorer(bundle, child);
                        delegate.restore(restorer);
                        bundle.putDelegate(child.getNodeName(), delegate);
                    }
                }
                catch (final ClassNotFoundException ignored) {
                }
            }
            else {
                AbstractDefaultA3ExtMapBundle extMapBundle = bundle.createExtMapBundle();
                bundle.putExtMapBundle(child.getNodeName(), extMapBundle);
                restoreFromDocument(extMapBundle, child);
            }
        }
    }

    @Override
    public boolean restore(final InputStream input) {
        checkArgNotNull(input, "input");
        try (BufferedInputStream stream = new BufferedInputStream(input)) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(stream);
                restoreFromDocument(this, document.getFirstChild());
                return true;
            }
            catch (ParserConfigurationException e) {
                return false;
            }
            catch (final SAXException e) {
                return false;
            }
        }
        catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean restore(final URL input) {
        checkArgNotNull(input, "input");
        try (final InputStream stream = input.openStream()) {
            return restore(stream);
        }
        catch (final IOException e) {
            return false;
        }
    }

    @Override
    public boolean restore(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        return restore(assets.readAsset(input));
    }

    private static final String[] READER_FORMAT_NAMES = new String[] {"xml"};
    private static final String[] WRITER_FORMAT_NAMES = new String[] {"xml"};

    @Override
    public String[] getBundleReaderFormatNames() {
        return A3Arrays.copy(READER_FORMAT_NAMES);
    }

    @Override
    public String[] getBundleWriterFormatNames() {
        return A3Arrays.copy(WRITER_FORMAT_NAMES);
    }

    private A3ExtMapBundle put0(final String key, final Value value) {
        checkArgNotNull(key, "key");
        checkArgNotNull(value, "value");
        map.put(key, value);
        return this;
    }

    private A3ExtMapBundle put0(final String key, final String value) {
        return put0(key, new DefaultValue(value));
    }

    private A3ExtMapBundle put0(final String key, final A3ExtMapBundle value) {
        return put0(key, new DefaultValue(value));
    }

    private A3ExtMapBundle put0(final String key, final Delegate value) {
        return put0(key, new DefaultValue(value));
    }

    @Override
    public A3ExtMapBundle putByte(final String key, final byte value) {
        return put0(key, Byte.toString(value));
    }

    @Override
    public A3ExtMapBundle putShort(final String key, final short value) {
        return put0(key, Short.toString(value));
    }

    @Override
    public A3ExtMapBundle putInt(final String key, final int value) {
        return put0(key, Integer.toString(value));
    }

    @Override
    public A3ExtMapBundle putLong(final String key, final long value) {
        return put0(key, Long.toString(value));
    }

    @Override
    public A3ExtMapBundle putFloat(final String key, final float value) {
        return put0(key, Float.toString(value));
    }

    @Override
    public A3ExtMapBundle putDouble(final String key, final double value) {
        return put0(key, Double.toString(value));
    }

    @Override
    public A3ExtMapBundle putBoolean(final String key, final boolean value) {
        return put0(key, Boolean.toString(value));
    }

    @Override
    public A3ExtMapBundle putChar(final String key, final char value) {
        return put0(key, Character.toString(value));
    }

    @Override
    public A3ExtMapBundle putString(final String key, final String value) {
        return put0(key, value == null ? "null" : value);
    }

    @Override
    public A3ExtMapBundle putBigInteger(final String key, final BigInteger value) {
        checkArgNotNull(value, "value");
        return put0(key, value.toString());
    }

    @Override
    public A3ExtMapBundle putBigDecimal(final String key, final BigDecimal value) {
        checkArgNotNull(value, "value");
        return put0(key, value.toPlainString());
    }

    @Override
    public A3ExtMapBundle putExtMapBundle(final String key, final A3ExtMapBundle value) {
        return put0(key, value);
    }

    @Override
    public <T extends Delegate> A3ExtMapBundle putDelegate(final String key, final T value) {
        return put0(key, value);
    }

    @Override
    public A3ExtMapBundle putByteArray(final String key, final byte[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3ExtMapBundle putShortArray(final String key, final short[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3ExtMapBundle putIntArray(final String key, final int[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3ExtMapBundle putLongArray(final String key, final long[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3ExtMapBundle putFloatArray(final String key, final float[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3ExtMapBundle putDoubleArray(final String key, final double[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3ExtMapBundle putBooleanArray(final String key, final boolean[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3ExtMapBundle putCharArray(final String key, final char[] value) {
        checkArgNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3ExtMapBundle putStringArray(final String key, final String[] value) {
        checkArgElementsNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3ExtMapBundle putBigIntegerArray(final String key, final BigInteger[] value) {
        checkArgElementsNotNull(value, "value");
        return put0(key, Arrays.toString(value));
    }

    @Override
    public A3ExtMapBundle putBigDecimalArray(final String key, final BigDecimal[] value) {
        checkArgElementsNotNull(value, "value");
        return put0(key, A3Arrays.toPlainString(value));
    }

    private Value get0(final String key, final Value defValue) {
        checkArgNotNull(key, "key");
        return A3Maps.getOrDefault(map, key, defValue);
    }

    private String get0(final String key, final String defValue) {
        checkArgNotNull(key, "key");
        final Value value = get0(key, (Value) null);
        return value == null ? defValue : (String) value.getValue();
    }

    private A3ExtMapBundle get0(final String key, final A3ExtMapBundle defValue) {
        checkArgNotNull(key, "key");
        final Value value = get0(key, (Value) null);
        return value == null ? defValue : (A3ExtMapBundle) value.getValue();
    }

    private <T extends Delegate> T get0(final String key, final T defValue) {
        checkArgNotNull(key, "key");
        final Value value = get0(key, (Value) null);
        return value == null ? defValue : (T) value.getValue();
    }

    @Override
    public byte getByte(final String key, final byte defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : Byte.parseByte(value);
    }

    @Override
    public short getShort(final String key, final short defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : Short.parseShort(value);
    }

    @Override
    public int getInt(final String key, final int defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : Integer.parseInt(value);
    }

    @Override
    public long getLong(final String key, final long defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : Long.parseLong(value);
    }

    @Override
    public float getFloat(final String key, final float defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : Float.parseFloat(value);
    }

    @Override
    public double getDouble(final String key, final double defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : Double.parseDouble(value);
    }

    @Override
    public boolean getBoolean(final String key, final boolean defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : Boolean.parseBoolean(value);
    }

    @Override
    public char getChar(final String key, final char defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : value.charAt(0);
    }

    @Override
    public String getString(final String key, final String defValue) {
        return get0(key, defValue);
    }

    @Override
    public BigInteger getBigInteger(final String key, final BigInteger defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : new BigInteger(value);
    }

    @Override
    public BigDecimal getBigDecimal(final String key, final BigDecimal defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : new BigDecimal(value);
    }

    @Override
    public A3ExtMapBundle getExtMapBundle(final String key, final A3ExtMapBundle defValue) {
        return get0(key, defValue);
    }

    @Override
    public <T extends Delegate> T getDelegate(final String key, final T defValue) {
        return get0(key, defValue);
    }

    @Override
    public byte[] getByteArray(final String key, final byte[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseByteArray(value);
    }

    @Override
    public short[] getShortArray(final String key, final short[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseShortArray(value);
    }

    @Override
    public int[] getIntArray(final String key, final int[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseIntArray(value);
    }

    @Override
    public long[] getLongArray(final String key, final long[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseLongArray(value);
    }

    @Override
    public float[] getFloatArray(final String key, final float[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseFloatArray(value);
    }

    @Override
    public double[] getDoubleArray(final String key, final double[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseDoubleArray(value);
    }

    @Override
    public boolean[] getBooleanArray(final String key, final boolean[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseBooleanArray(value);
    }

    @Override
    public char[] getCharArray(final String key, final char[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseCharArray(value);
    }

    @Override
    public String[] getStringArray(final String key, final String[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseStringArray(value);
    }

    @Override
    public BigInteger[] getBigIntegerArray(final String key, final BigInteger[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseBigIntegerArray(value);
    }

    @Override
    public BigDecimal[] getBigDecimalArray(final String key, final BigDecimal[] defValue) {
        final String value = get0(key, (String) null);
        return value == null ? defValue : A3TextUtils.parseBigDecimalArray(value);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return map.containsValue(value);
    }

    @Override
    public Value get(final Object key) {
        return map.get(key);
    }

    @Override
    public Value put(final String key, final Value value) {
        return map.put(key, value);
    }

    @Override
    public Value remove(final Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(final Map<? extends String, ? extends Value> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Value> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, Value>> entrySet() {
        return map.entrySet();
    }

}
