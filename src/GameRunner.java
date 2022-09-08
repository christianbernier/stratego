import controller.GameSyncController;
import controller.GameSyncControllerImpl;
import model.GameModel;
import model.GameModelImpl;
import view.GameTextView;
import view.GameView;

public class GameRunner {
  public static void main(String[] args) {
    char[][] test = new char[][] {
            {'T', 'X', 'T', 'J', 'A', 'C', 'F', 'E', 'F', 'T'},
            {'G', 'T', 'C', 'D', 'D', 'e', 'F', 'I', 'H', 'D'},
            {'B', 'H', 'B', 'F', 'c', ' ', 'E', 'E', 'T', 'B'},
            {' ', ' ', '-', '-', 'B', 'a', '-', '-', ' ', ' '},
            {' ', ' ', '-', '-', ' ', ' ', '-', '-', ' ', ' '},
            {'t', 'b', 't', 't', 'b', 'b', 'd', 'h', 'c', 'b'},
            {'g', 'c', 'h', 'f', 'd', 'e', 'd', 'i', 'c', 'f'},
            {'t', 'e', 'f', 'x', 'j', 't', 'e', 'a', 'g', 'd'}
    };

    GameView view = new GameTextView(System.out);
    GameModel model = new GameModelImpl(test);
    GameSyncController controller = new GameSyncControllerImpl(view, model, System.in);

    controller.run();
  }
}
