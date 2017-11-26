public class Feldverarbeitung extends MiniJava {

    public static void main(String[] args) {
        int n;
        n = read("Bitte geben Sie die Größe des Arrays ein.");
        while (n < 2) {
            n = read("Bitte geben Sie eine gültige Größe ein.");
        }

        int[] array = new int[n];
        int i = 0;
        while (i < n) {
            array[i] = read("Bitte geben Sie das nächste Element ein.");
            i++;
        }

        // Teilaufgabe a
        i = 0;
        int r = 0;
        while (i < n) {
            r += -(2 * (i % 2) - 1) * array[i];
            i++;
        }
        write(r);

        // Teilaufgabe b
        int max1 = array[0];
        int max2 = array[1];
        i = 1;
        while (i < n) {
            if (array[i] > max1) {
                // Ein neues größtes Element wurde gefunden; das bisherige
                // maximum wird einen Platz nach hinten verschoben.
                max2 = max1;
                max1 = array[i];
            } else if (array[i] > max2) {
                // Ein neues zweitgrößes Element wurde gefunden; wir
                // tauschen das bisherige Element mit diesem aus.
                max2 = array[i];
            }
            i++;
        }
        write(max2);

        // Teilaufgabe c
        i = 0;
        while (i + 1 < n) {
            array[i] += array[i + 1];
            i += 2;
        }

        i = 0;
        while (i < n) {
            write(array[i]);
            i++;
        }
    }

}
