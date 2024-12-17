public class Main {
    static String sharedString = "";

    public static void main(String[] args) throws InterruptedException {
        Thread writer1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                sharedString = "aaaaa";
            }
        });

        Thread writer2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                if(sharedString != "aaaaa" && sharedString != "bbbbb"){
                    System.out.println(sharedString);
                }

                sharedString = "bbbbb";
            }
        });

        writer1.start();
        writer2.start();

        writer1.join();
        writer2.join();

        // Przypisanie do string'a jest operacją atomową
    }
}