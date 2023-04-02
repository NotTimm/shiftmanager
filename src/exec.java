import java.util.Vector;
public class exec {
    public static MainApp mainApp = new MainApp();
    public static ListApp listApp = new ListApp();
    public static Login login = new Login();
    public static String seshToke, userEmail;
    public static Vector<Vector<String>> listLocal;
    public static Shift shift;
    public static int choice;

    public static void main(String[] args) {
        Login myFrame = exec.login;
        myFrame.initialize();
    }
};
