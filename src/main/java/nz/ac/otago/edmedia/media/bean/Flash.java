package nz.ac.otago.edmedia.media.bean;

/**
 * Flash information, such as width, height.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 20/03/2008
 *         Time: 16:05:00
 */
public class Flash {

    int width;

    int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(width);
        sb.append("x");
        sb.append(height);
        sb.append("]");
        return sb.toString();
    }
}
