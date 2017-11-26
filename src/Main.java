/**
 * Created by Александр on 26.11.2017.
 */
public class Main {
    public static void main(String[] args) {
        int[] yellow = {10, 30, 50, -1};
        int[] blue = {0, 20, 40, -1};
        //int[] yellow = {1, 3, 5, 7};
        //int[] blue = {0, 2, 4, 6};
        int[] red = {-1, -1, -1, -1};
        int[] green = {-1, -1, -1, -1};
        Mensch m = new Mensch(yellow, blue, red, green);

        //Mensch.paintField(yellow, blue, red, green);
    }
}
