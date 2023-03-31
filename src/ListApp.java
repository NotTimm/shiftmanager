import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
// import java.net.*;
// import java.com.xyz;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
// import java.awt.event.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class ListApp extends JFrame {
    private JList<String> itemList;
    private JScrollPane scrollPane;
    final private Font mainFont = new Font("Consolas", Font.BOLD, 13);
    private JLabel selected;
    private String listDirty;

    public void ListedApp() {
        setTitle("Shift List");
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        setSize(500,400);
        setLayout(new BorderLayout());

        try {
            URL url = new URL("http://localhost:8080/getshifts");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            String param = "token="+exec.seshToke;
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
            System.out.println(respTemp.toString());
            listDirty = respTemp.toString();
            // JSONObject temp = new JSONObject(respTemp.toString());
            // JSONArray temp1 = new JSONArray(respTemp.toString());
            // for(int i = 0; i < temp1.length(); i++)
            //     System.out.println(temp1.get(i).toString());
        } catch (Exception r) {
            r.printStackTrace();
        }
        Vector<Vector<String>> listStore = new Vector<Vector<String>>(15);
        int point = 1;
        System.out.println(listDirty.charAt(point));
        System.out.println(listDirty.charAt(point+1));
        for(int i = 0; i < 1; i++)
        {
            listStore.add(new Vector<String>());
            point+=2;
            listStore.get(i).add(listDirty.substring(point, point+10));
            point+=13;
            System.out.println(listDirty.charAt(point));
            
        }

        String[] items = {"Shift 1    Day", "Shift 1    Night",
         "Shift 2    Day", "Shift 2    Night",
          "Shift 3    Day", "Shift 3    Night",
           "Shift 4    Day", "Shift 4    Night",
            "Shift 5    Day", "Shift 5    Night",
             "Shift 6    Day", "Shift 6    Night"};
    
        JButton btnOK = new JButton("Reserve");
        btnOK.setFont(mainFont);
        JButton back = new JButton("Back");
        back.setFont(mainFont);
        JButton view = new JButton("View");
        view.setFont(mainFont);
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,3,5,5));
        buttons.setOpaque(false);
        buttons.add(btnOK);
        buttons.add(back);
        buttons.add(view);

        itemList = new JList<String>(items);
        itemList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        itemList.setFont(mainFont);
        itemList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    // List<String> selectedShift =  new List<String>(itemList.getSelectedValuesList());
                    if(itemList.getSelectedValuesList().size() > 1)
                        buttons.remove(view);
                    else
                        buttons.add(view);
                    selected.setText(itemList.getSelectedValuesList().size()*12 + " hours selected");
                }
            }
            
        });

        scrollPane = new JScrollPane(itemList);
        selected = new JLabel("Please select a shift");
        selected.setFont(mainFont);
        ImageIcon img = new ImageIcon("../com/icon.png");
        setIconImage(img.getImage());
        add(scrollPane, BorderLayout.CENTER);
        add(selected, BorderLayout.NORTH);
        add(buttons, BorderLayout.SOUTH);
        setVisible(true);
    }
    // public static void main(String[] args) {
    //     ListApp test = new ListApp();
    //     test.ListedApp();
    // }
}