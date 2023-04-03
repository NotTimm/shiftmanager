import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class Login extends JFrame {
    final private Font mainFont = new Font("Consolas", Font.BOLD, 18);
    final private Font smallFont = new Font("Consolas", Font.BOLD, 16);
    JTextField tfFullName, tfWorkID, tfPhoneNum, tfEmail, tfPassword;
    JLabel lbWelcome;
    public void initialize()
    {
        JLabel lbEmail = new JLabel(" Email:");
        lbEmail.setFont(mainFont);
        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbPassword = new JLabel(" Password:");
        lbPassword.setFont(mainFont);
        tfPassword = new JPasswordField();
        tfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6,1,5,5));
        formPanel.setOpaque(false);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbPassword);
        formPanel.add(tfPassword);

        lbWelcome = new JLabel();
        lbWelcome.setFont(mainFont);

        JButton btnOK = new JButton("Login");
        btnOK.setFont(smallFont);
        btnOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                exec.userEmail = tfEmail.getText();
                String password = tfEmail.getText();
                try {
                    URL url = new URL("http://localhost:8080/login");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    String param = "email="+exec.userEmail+"&password="+password;
                    // System.out.println(param);
                    OutputStream os = conn.getOutputStream();
                    os.write(param.getBytes());
                    os.flush();
                    os.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuffer respTemp = new StringBuffer();
                    while((inputLine = in.readLine()) != null) {
                        respTemp.append(inputLine);
                    }
                    in.close();
                    exec.seshToke = respTemp.toString();
                } catch (Exception r) {
                    r.printStackTrace();
                }
                // System.out.println(tokeTemp);
                if(exec.seshToke.equals("Invalid password!") || exec.seshToke.equals("Invalid email!"))
                {
                    lbWelcome.setText("Invalid Login");
                    lbWelcome.setVisible(true);
                } else
                {
                    exec.listApp = new ListApp();
                    exec.listApp.ListedApp();
                    JButton button = (JButton)e.getSource();
                    Window window = SwingUtilities.windowForComponent(button);
                    window.setVisible(false);
                }
                //use api to store
                // throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
            
        });

        JButton btnClear = new JButton("Cancel");
        btnClear.setFont(smallFont);
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JButton btnSignUp = new JButton("SignUp");
        btnSignUp.setFont(smallFont);
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainApp temp = exec.mainApp;
                temp.initialize();
                JButton button = (JButton)e.getSource();
                Window window = SwingUtilities.windowForComponent(button);
                window.setVisible(false);
            }
        });

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2,5,5));
        buttons.setOpaque(false);
        buttons.add(btnOK);
        buttons.add(btnSignUp);
        buttons.add(btnClear);


        JPanel mainScreen = new JPanel();
        mainScreen.setLayout(new BorderLayout());
        mainScreen.add(formPanel, BorderLayout.NORTH);
        mainScreen.add(lbWelcome, BorderLayout.CENTER);
        mainScreen.add(buttons, BorderLayout.SOUTH);

        add(mainScreen);

        setTitle("Login");
        setSize(300,269);
        ImageIcon img = new ImageIcon("../com/icon.png");
        setIconImage(img.getImage());
        setMinimumSize(new Dimension(300,100));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
