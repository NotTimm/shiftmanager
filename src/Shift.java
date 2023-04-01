import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Shift extends JFrame {
    private JList<String> itemList;
    private JScrollPane spots;
    final private Font mainFont = new Font("Consolas", Font.BOLD, 13);
    private JLabel selected, info;

    public JFrame ListedApp(Vector<String> shiftDetails) {
        setTitle("Shift View");
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        setSize(600,400);
        setMinimumSize(new Dimension(300, 200));
        setLayout(new BorderLayout());

        String[] nurses = {"Nurse 1", "Nurse 2","Nurse 3", "Nurse 4", "Nurse 5", "<available>", "<available>", "<available>", "<available>", "<available>"};

    
        JButton btnOK = new JButton("Reserve");
        btnOK.setFont(mainFont);
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListApp temp = exec.listApp;
                temp.setVisible(true);
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
        itemList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    // List<String> selectedShift =  new List<String>(itemList.getSelectedValuesList());
                    selected.setText(itemList.getSelectedValuesList().size()*12 + " hours selected");
                }
            }
            
        });

        spots = new JScrollPane(itemList);
        selected = new JLabel(" " + shiftDetails.get(1) + "  --  " + shiftDetails.get(0));
        info = new JLabel("Specific Start Time: 1/1/1111-1:11 pm\nBuilding #: C\nRoom Count: ~30");
        String infoStr = "Specific Start Time: 1/1/1111-1:11 pm\nBuilding #: C\nRoom Count: ~30\nDoctors Working: asdf\nMore Info To Come";
        // String infoStr = shiftDetails.get(1) + " -- " + shiftDetails.get(0) + "\nLocation: Childrens Hospital Dallas";
        info.setText("<html>" + infoStr.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
        selected.setFont(mainFont);
        ImageIcon img = new ImageIcon("../com/icon.png");
        setIconImage(img.getImage());
        add(spots, BorderLayout.WEST);
        add(selected, BorderLayout.NORTH);
        add(info, BorderLayout.CENTER);
        add(new JLabel(new ImageIcon("../com/hospital_logo.png")),BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);
        setVisible(true);
        return(this);
    }
}