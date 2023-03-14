package io.notcute.internal;

import io.notcute.context.Identifier;
import io.notcute.context.Initializer;
import io.notcute.context.Producer;
import io.notcute.util.I18NText;
import io.notcute.util.MIMETypes;

public class CoreInitializer extends Initializer {

    @Override
    public void initialize() {
        Producer.GLOBAL.put(new Identifier("notcute", "i18NText"), CoreInitializer::getI18NText);
        Producer.GLOBAL.put(new Identifier("notcute", "mimeTypes"), CoreInitializer::getMIMETypes);
    }

    private static volatile I18NText i18NText = null;
    public synchronized static I18NText getI18NText() {
        if (i18NText == null) i18NText = new I18NText();
        return i18NText;
    }

    private static volatile MIMETypes mimeTypes = null;
    public synchronized static MIMETypes getMIMETypes() {
        if (mimeTypes == null) {
            mimeTypes = new MIMETypes();
            mimeTypes.getExpansions().add(new BasicMIMETypesExpansion());
        }
        return mimeTypes;
    }

}
