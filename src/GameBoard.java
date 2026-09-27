public class GameBoard {

    public static String[] BOARD = {    // 20x20
            "wwwwwwwwwwwwwwwwwwww",
            "w        ww        w",
            "w w  w  www w  w  ww",
            "w w  w   ww w  w  ww",
            "w  w               w",
            "w w w w w w w  w  ww",
            "w w     www w  w  ww",
            "w w     w w w  w  ww",
            "w   w w  w  w  w   w",
            "w     w  w  w  w   w",
            "w ww ww        w  ww",
            "w  w w    w    w  ww",
            "w        ww w  w  ww",
            "w         w w  w  ww",
            "w        w     w  ww",
            "w  w              ww",
            "w  w www  w w  ww ww",
            "w w      ww w     ww",
            "w   w   ww  w      w",
            "wwwwwwwwwwwwwwwwwwww"
    };

    public static boolean isWall(int x, int y) {

        // Tjekker her om koordinater er udenfor board.
        if (x < 0 || y < 0 || y >= BOARD.length || x >= BOARD[y].length()) {
            return true;
        }

        // Tjekker om koordianter er en væg.
        return BOARD[y].charAt(x) == 'w';
    }

    public static void main(String[] args) {
        System.out.println(BOARD.length);
    }
}
