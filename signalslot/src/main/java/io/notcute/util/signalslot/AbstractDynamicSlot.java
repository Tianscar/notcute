package io.notcute.util.signalslot;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractDynamicSlot {

    protected final Object obj;
    protected final String methodName;
    protected final Class<?>[] parameterTypes;

    public AbstractDynamicSlot(final Object obj, final String methodName, final Class<?>... parameterTypes) {
        this.obj = Objects.requireNonNull(obj);
        this.methodName = Objects.requireNonNull(methodName);
        this.parameterTypes = parameterTypes;
    }

    protected Object invoke(final Object... args) throws IllegalConnectionException {
        final Object obj;
        final Class<?> clazz;
        if (this.obj instanceof Class) {
            obj = null;
            clazz = (Class<?>) this.obj;
        }
        else {
            obj = this.obj;
            clazz = obj.getClass();
        }
        final Object arg;
        final boolean cast;
        if (parameterTypes.length > 0 && parameterTypes[parameterTypes.length - 1].isArray()) {
            final Class<?> argsLastArgClass = args[args.length - 1].getClass();
            final Class<?> varargsClass = parameterTypes[parameterTypes.length - 1];
            if (argsLastArgClass == varargsClass && parameterTypes.length == args.length) {
                cast = true;
                arg = args;
            }
            else {
                try {
                    final int varargsLength = args.length - parameterTypes.length + 1;
                    final Object varargs = Array.newInstance(args[args.length - 1].getClass(), varargsLength);
                    System.arraycopy(args, args.length - varargsLength, varargs, 0, varargsLength);
                    final int preLength = args.length - varargsLength;
                    if (preLength > 0) {
                        cast = true;
                        final Object[] objects = new Object[parameterTypes.length];
                        System.arraycopy(args, 0, objects, 0, preLength);
                        objects[objects.length - 1] = varargs;
                        arg = objects;
                    }
                    else {
                        cast = false;
                        arg = varargs;
                    }
                }
                catch (final Exception e) {
                    throw new IllegalConnectionException(e);
                }
            }
        }
        else {
            cast = true;
            arg = args;
        }
        final Method method = queryMethod(clazz, methodName, parameterTypes);
        try {
            if (cast) return method.invoke(obj, (Object[]) arg);
            else return method.invoke(obj, arg);
        } catch (final IllegalAccessException e) {
            throw new IllegalConnectionException(e);
        } catch (final InvocationTargetException e) {
            throw new IllegalConnectionException(e);
        }
    }

    private static Method queryMethod(final Class<?> clazz, final String methodName, final Class<?>[] parameterTypes) {
        try {
            return clazz.getMethod(methodName, parameterTypes);
        }
        catch (final NoSuchMethodException e) {
            try {
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            }
            catch (final NoSuchMethodException ex) {
                final Class<?> superclass = clazz.getSuperclass();
                if (superclass == null) throw new IllegalConnectionException(ex);
                return queryMethod(superclass, methodName, parameterTypes);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDynamicSlot that = (AbstractDynamicSlot) o;

        if (!obj.equals(that.obj)) return false;
        if (!methodName.equals(that.methodName)) return false;
        return Arrays.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode() {
        int result = obj.hashCode();
        result = 31 * result + methodName.hashCode();
        result = 31 * result + Arrays.hashCode(parameterTypes);
        return result;
    }

}
