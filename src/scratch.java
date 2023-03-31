import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class scratch extends JFrame{
    private Vector<String> selectedButtons = new Vector<>();
    public scratch() {
        // Set the title and layout
        super("Vector Frame");
        setLayout(new BorderLayout());

        // Create the panels for the vectors and buttons
        JPanel vectorPanel = new JPanel(new GridLayout(2, 7));
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create the vectors
        String[] vector1 = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        String[] vector2 = {"1", "2", "3", "4", "5", "6", "7"};

        // // Add the vectors to the vector panel
        // for (int i = 0; i < 7; i++) {
        //     vectorPanel.add(new JLabel(vector1[i], SwingConstants.CENTER));
        // }
        // for (int i = 0; i < 7; i++) {
        //     vectorPanel.add(new JLabel(vector2[i], SwingConstants.CENTER));
        // }

        for (int i = 0; i < 7; i++) {
            JToggleButton button = new JToggleButton(vector1[i]);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (button.isSelected()) {
                        selectedButtons.add(button.getText());
                    } else {
                        selectedButtons.remove(button.getText());
                    }
                }
        });
            vectorPanel.add(button);
        }
        for (int i = 0; i < 7; i++) {
            JToggleButton button = new JToggleButton(vector2[i]);
            button.addActionListener(e -> System.out.println(button.getText()));
            vectorPanel.add(button);
        }

        // Create the buttons
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");

        // Add the buttons to the button panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        // Add the panels to the frame
        add(vectorPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set the size and location of the frame
        setSize(400, 200);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        scratch frame = new scratch();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
