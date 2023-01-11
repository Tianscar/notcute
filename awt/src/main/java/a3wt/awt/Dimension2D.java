package a3wt.awt;

public abstract class Dimension2D extends java.awt.geom.Dimension2D {

    public static class Float extends Dimension2D {

        public float width;
        public float height;

        public Float() {
        }

        public Float(float width, float height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public double getWidth() {
            return width;
        }

        @Override
        public double getHeight() {
            return height;
        }

        public void setSize(float width, float height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void setSize(double width, double height) {
            this.width = (float)width;
            this.height = (float)height;
        }

        @Override
        public String toString() {
            return "Dimension2D.Float["+width+", "+height+"]";
        }
    }

    public static class Double extends Dimension2D {

        public double width;
        public double height;

        public Double() {
        }

        public Double(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public double getWidth() {
            return width;
        }

        @Override
        public double getHeight() {
            return height;
        }

        @Override
        public void setSize(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public String toString() {
            return "Dimension2D.Double["+width+", "+height+"]";
        }
    }

    protected Dimension2D() {
    }

}
