import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainApp extends JFrame {
    int response;
    final private Font mainFont = new Font("Consolas", Font.BOLD, 18);
    JTextField tfFullName, tfWorkID, tfPhoneNum, tfEmail, tfPassword;
    JLabel lbWelcome;
    public void initialize()
    {
        setVisible(true);
        JLabel lbFullName = new JLabel(" Full Name:");
        lbFullName.setFont(mainFont);
        tfFullName = new JTextField();
        tfFullName.setFont(mainFont);

        JLabel lbWorkID = new JLabel(" Work ID #:");
        lbWorkID.setFont(mainFont);
        tfWorkID = new JTextField();
        tfWorkID.setFont(mainFont);

        JLabel lbPhoneNum = new JLabel(" Phone Number:");
        lbPhoneNum.setFont(mainFont);
        tfPhoneNum = new JTextField();
        tfPhoneNum.setFont(mainFont);

        JLabel lbEmail = new JLabel(" Email:");
        lbEmail.setFont(mainFont);
        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbPassword = new JLabel(" Password:");
        lbPassword.setFont(mainFont);
        tfPassword = new JTextField();
        tfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6,1,5,5));
        formPanel.setOpaque(false);
        formPanel.add(lbFullName);
        formPanel.add(tfFullName);
        formPanel.add(lbWorkID);
        formPanel.add(tfWorkID);
        formPanel.add(lbPhoneNum);
        formPanel.add(tfPhoneNum);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbPassword);
        formPanel.add(tfPassword);

        lbWelcome = new JLabel();
        lbWelcome.setFont(mainFont);

        JButton btnOK = new JButton("Submit");
        btnOK.setFont(mainFont);
        btnOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String response = "emp";
                String fullName = tfFullName.getText();
                String workID = tfWorkID.getText();
                String phoneNumber = tfPhoneNum.getText();
                String email = tfEmail.getText();
                String password = tfPassword.getText();
                System.out.println(fullName+workID+phoneNumber+email+password);
                try{
                    URL url = new URL("http://localhost:8080/newuser");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    String param = "fullName="+fullName+"&workID="+workID+"&phoneNumber="+phoneNumber+"&email="+email+"&password="+password;
                    System.out.println(param);
                    // try (OutputStream os = conn.getOutputStream()) {
                    //     byte[] go = jso.getBytes();
                    //     os.write(go, 0, go.length);
                    // }
                    OutputStream os = conn.getOutputStream();
                    os.write(param.getBytes());
                    os.flush();
                    os.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuffer respTemp = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        respTemp.append(inputLine);
                    }
                    in.close();
                    response = respTemp.toString();
                    System.out.println(response);
                } catch (Exception r) {
                    r.printStackTrace();
                }
                //use api to store
                if("Username already exists!".equals(response) || response == "emp")
                {
                    lbWelcome.setText("Email already in use");
                } else {
                    Login temp = exec.login;
                    temp.initialize();
                    JButton button = (JButton)e.getSource();
                    Window window = SwingUtilities.windowForComponent(button);
                    window.setVisible(false);
                }
                // lbWelcome.setText("Welcome " + fullName);
                // throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
            
        });

        JButton btnClear = new JButton("Back");
        btnClear.setFont(mainFont);
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login back = exec.login;
                back.initialize();
                JButton temp = (JButton)e.getSource();
                Window window = SwingUtilities.windowForComponent(temp);
                window.setVisible(false);
            }
        });

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2,5,5));
        buttons.setOpaque(false);
        buttons.add(btnOK);
        buttons.add(btnClear);


        JPanel mainScreen = new JPanel();
        mainScreen.setLayout(new BorderLayout());
        // mainScreen.setBackground((new Color(102,102,153)));
        mainScreen.add(formPanel, BorderLayout.NORTH);
        mainScreen.add(lbWelcome, BorderLayout.CENTER);
        mainScreen.add(buttons, BorderLayout.SOUTH);

        add(mainScreen);

        setTitle("SignUp");
        setSize(300,300);
        ImageIcon img = new ImageIcon("../com/icon.png");
        setIconImage(img.getImage());
        // setI
        setMinimumSize(new Dimension(300,300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // public static void main(String[] args) {
    //     MainApp myFrame = new MainApp();
    //     myFrame.initialize();
    // }
}
