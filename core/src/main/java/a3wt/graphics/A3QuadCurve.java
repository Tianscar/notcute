package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;

public interface A3QuadCurve extends A3Linear<A3QuadCurve> {

    float getCtrlX();
    float getCtrlY();
    A3Point getCtrlPos();
    void getCtrlPos(final A3Point pos);

    A3QuadCurve setCtrlX(final float ctrlX);
    A3QuadCurve setCtrlY(final float ctrlY);
    A3QuadCurve setCtrlPos(final A3Point pos);

    A3QuadCurve set(final float startX, final float startY, final float ctrlX, final float ctrlY, final float endX, final float endY);
    A3QuadCurve set(final A3Point startPos, final A3Point ctrlPos, final A3Point endPos);

    String KEY_CTRL_X = "ctrlX";
    String KEY_CTRL_Y = "ctrlY";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        saver.putFloat(KEY_START_X, getStartX());
        saver.putFloat(KEY_START_Y, getStartY());
        saver.putFloat(KEY_CTRL_X, getCtrlX());
        saver.putFloat(KEY_CTRL_Y, getCtrlY());
        saver.putFloat(KEY_END_X, getEndX());
        saver.putFloat(KEY_END_Y, getEndY());
    }

    @Override
    default void restore(final A3ExtMapBundle.Restorer restorer) {
        set(restorer.getFloat(KEY_START_X, 0), restorer.getFloat(KEY_START_Y, 0),
                restorer.getFloat(KEY_CTRL_X, 0), restorer.getFloat(KEY_CTRL_Y, 0),
                restorer.getFloat(KEY_END_X, 0), restorer.getFloat(KEY_END_Y, 0));
    }
    @Override
    default Class<? extends A3ExtMapBundle.Bundleable> typeClass() {
        return A3QuadCurve.class;
    }


}
