import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
        setSize(500,350);
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
            listDirty = respTemp.toString();
        } catch (Exception r) {
            r.printStackTrace();
        }
        exec.listLocal = new Vector<Vector<String>>(15);
        int point = 1;
        for(int i = 0; i < 15; i++)
        {
            exec.listLocal.add(new Vector<String>());
            point+=2;
            System.out.println(listDirty.substring(point, point+10));
            exec.listLocal.get(i).add(listDirty.substring(point, point+10));
            point+=13;
            int shift = 0;
            while(Character.compare(listDirty.charAt(point+shift), '\"') != 0)
                shift++;
            System.out.println(listDirty.substring(point, point+shift));
            exec.listLocal.get(i).add(listDirty.substring(point, point+shift));
            for(int o = 0; o < 10; o++)
            {
                point += shift + 3;
                shift = 0;
                while(Character.compare(listDirty.charAt(point+shift), '\"') != 0 || Character.compare(listDirty.charAt(point+shift),'*') == 0)
                    shift++;
                    exec.listLocal.get(i).add(listDirty.substring(point, point+shift));
                System.out.println(listDirty.substring(point, point+shift));
            }
            point+=4;
        }

        String[] items = new String[15];
        for(int i = 0; i < 15; i++)
            items[i] = " |Date|  " + exec.listLocal.get(i).get(0) + "  |Day|  " + exec.listLocal.get(i).get(1);
    
        JButton btnOK = new JButton("Reserve");
        btnOK.setFont(mainFont);
        JButton back = new JButton("Back");
        back.setFont(mainFont);
        JButton view = new JButton("View");
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int shiftNum = itemList.getSelectedIndex();
                exec.shift = new Shift();
                exec.shift.ListedApp(exec.listLocal.get(shiftNum));
                JButton button = (JButton)e.getSource();
                Window window = SwingUtilities.windowForComponent(button);
                window.setVisible(false);
            }
        });
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
                    if(itemList.getSelectedValuesList().size() > 1)
                        buttons.remove(view);
                    else
                        buttons.add(view);
                    selected.setText(" " + itemList.getSelectedValuesList().size()*12 + " hours selected");
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
        setLocationRelativeTo(null);
        setVisible(true);
    }
}