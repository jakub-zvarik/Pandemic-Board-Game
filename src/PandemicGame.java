import board.Board;


import java.io.FileNotFoundException;

public class PandemicGame {

    public static void main(String[] args) throws FileNotFoundException {
        Board board = new Board();
        System.out.println(board.getCITIES().get("Delhi").getAdjacentCityIndex(0).getNAME());
    }
}
