import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
// import java.net.*;
// import java.com.xyz;

import java.awt.*;
// import java.awt.event.*;

public class ListApp extends JFrame {
    private JList<String> itemList;
    private JScrollPane scrollPane;
    final private Font mainFont = new Font("Consolas", Font.BOLD, 13);
    private JLabel selected;

    public void ListedApp() {
        setTitle("Shift List");
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        setSize(500,400);
        setLayout(new BorderLayout());

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