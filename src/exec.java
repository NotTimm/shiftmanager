// import javax.swing.*;
// import java.awt.*;
public class exec {
    public static MainApp mainApp = new MainApp();
    public static ListApp listApp = new ListApp();
    public static Login login = new Login();

    public static void main(String[] args) {
        Login myFrame = exec.login;
        myFrame.initialize();
    }
};
