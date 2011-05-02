package nz.ac.otago.edmedia.media.bean;

/**
 * Images object for documentation type, such as office file, openoffice file and pdf file.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 03/03/2011
 *         Time: 3:00:58 PM
 */
public class Images {
    // how many pages (slides)
    int number;

    Image image;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
