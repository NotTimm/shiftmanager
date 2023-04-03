import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class Shift extends JFrame {
    private JList<String> itemList;
    private JScrollPane spots;
    final private Font mainFont = new Font("Consolas", Font.BOLD, 13);
    private JLabel selected, info, added;

    public JFrame ListedApp(Vector<String> shiftDetails) {
        String res = "Reserve";
        setTitle("Shift View");
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        setSize(600,400);
        setMinimumSize(new Dimension(300, 200));
        setLayout(new BorderLayout());
        added = new JLabel();

        // String[] nurses = {"Nurse 1", "Nurse 2","Nurse 3", "Nurse 4", "Nurse 5", "<available>", "<available>", "<available>", "<available>", "<available>"};
        String[] nurses = new String[10];
        for(int i = 0; i < 10; i++)
        {
            if(shiftDetails.get(i+2).equals("*"))
            {
                nurses[i] = "<available>";
                continue;
            }
            else if(shiftDetails.get(i+2).equals(exec.userEmail))
                res = "Forfeit";
            nurses[i] = shiftDetails.get(i+2);
        }
    
        JButton btnOK = new JButton(res);
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URL url = new URL("http://localhost:8080/apply");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    String param = "email="+exec.userEmail+"&date="+exec.listLocal.get(exec.choice).get(0)+"&token="+exec.seshToke;
                    // System.out.println(exec.listLocal.get(exec.choice).get(0) + exec.userEmail);
                    OutputStream os = conn.getOutputStream();
                    os.write(param.getBytes());
                    os.flush();
                    os.close();
                    // System.out.println("yep");
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuffer respTemp = new StringBuffer();
                    while((inputLine = in.readLine()) != null) {
                        respTemp.append(inputLine);
                    }
                    // System.out.println(respTemp.toString());
                    in.close();
                    if(respTemp.toString().equals("added")) {
                        btnOK.setText("Forfeit");
                        setTitle("ADDED");
                        added.setText("ADDED");
                        for(int i = 0; i < 10; i++)
                        {
                            if(nurses[i].equals("<available>")) {
                                nurses[i] = exec.userEmail;
                                itemList.setListData(nurses);
                                break;
                            }
                        }
                    }
                    else if(respTemp.toString().equals("not added")) {
                        setTitle("NO CHANGE");
                    }
                    else {
                        btnOK.setText("Reserve");
                        setTitle("REMOVED");
                        added.setText("REMOVED");
                        for(int i = 0; i < 10; i++)
                        {
                            if(nurses[i].equals(exec.userEmail)) {
                                nurses[i] = "<available>";
                                itemList.setListData(nurses);
                                break;
                            }
                        }
                    }

                } catch (Exception r) {
                    r.printStackTrace();
                }
            }
        });
        btnOK.setFont(mainFont);
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exec.listApp = new ListApp();
                exec.listApp.ListedApp();
                // ListApp temp = exec.listApp;
                // temp.ListedApp();
                JButton button = (JButton)e.getSource();
                Window window = SwingUtilities.windowForComponent(button);
                window.setVisible(false);
            }
        });
        back.setFont(mainFont);
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2,5,5));
        buttons.setOpaque(false);
        buttons.add(btnOK);
        buttons.add(back);

        itemList = new JList<String>(nurses);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setFont(mainFont);
        itemList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String selectedValue = itemList.getSelectedValue();
                    StringSelection stringSelection = new StringSelection(selectedValue);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                    setTitle(selectedValue+" copied to clipboard");
                }
            }
        });
        itemList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                }
            }
            
        });

        spots = new JScrollPane(itemList);
        selected = new JLabel(" " + shiftDetails.get(1) + "  --  " + shiftDetails.get(0));
        info = new JLabel("Specific Start Time: 1/1/1111-1:11 pm\nBuilding #: C\nRoom Count: ~30");
        String infoStr = "Specific Start Time: 1/1/1111-1:11 pm\nBuilding #: C\nRoom Count: ~30\nDoctors Working: Dr. Marsh\nMore Info To Come";
        // String infoStr = shiftDetails.get(1) + " -- " + shiftDetails.get(0) + "\nLocation: Childrens Hospital Dallas";
        info.setText("<html>" + infoStr.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
        selected.setFont(mainFont);
        ImageIcon img = new ImageIcon("../com/icon.png");
        setIconImage(img.getImage());
        add(spots, BorderLayout.WEST);
        add(selected, BorderLayout.NORTH);
        add(info, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        add(new JLabel(new ImageIcon("../com/hospital_logo.png")),BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);
        setVisible(true);
        return(this);
    }
}