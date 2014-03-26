import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class DetectiveNotes extends JDialog {
    private JComboBox<String> weapons, people, rooms;
    public DetectiveNotes(){
        weapons = createWeaponCombo();
        people = createPersonCombo();
        rooms = createRoomCombo();
        ComboListener listener = new ComboListener();
        weapons.addActionListener(listener);
        people.addActionListener(listener);
        rooms.addActionListener(listener);
        setTitle("Detective Notes");
        setSize(300, 200);
        setLayout(new GridLayout(3,2));
        JLabel peopleLabel = new JLabel("People");
        JPanel person = new JPanel();
        person.add(people);
        person.setBorder(new TitledBorder (new EtchedBorder(), "People Guess"));

        JPanel weapon = new JPanel();
        weapon.add(weapons);
        weapon.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));

        JPanel room = new JPanel();
        room.add(rooms);
        room.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));

        add(person);
        add(weapon);
        add(room);
    }
    private JComboBox<String> createWeaponCombo()
    {
        JComboBox<String> combo = new JComboBox<String>();
        combo.addItem("Aeglos");
        combo.addItem("Morgul blade");
        combo.addItem("Narsil");
        combo.addItem("Orcrist");
        combo.addItem("Warhammer");
        combo.addItem("Sting");

        return combo;
    }
    private JComboBox<String> createPersonCombo()
    {
        JComboBox<String> combo = new JComboBox<String>();
        combo.addItem("Aeglos");
        combo.addItem("Morgul blade");
        combo.addItem("Narsil");
        combo.addItem("Orcrist");
        combo.addItem("Warhammer");
        combo.addItem("Sting");
        return combo;
    }
    private JComboBox<String> createRoomCombo()
    {
        JComboBox<String> combo = new JComboBox<String>();
        combo.addItem("Aeglos");
        combo.addItem("Morgul blade");
        combo.addItem("Narsil");
        combo.addItem("Orcrist");
        combo.addItem("Warhammer");
        combo.addItem("Sting");
        return combo;
    }

    private class ComboListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == weapons)
                weapons.getSelectedItem().toString();
            else if(e.getSource() == people){
                people.getSelectedItem().toString();
            }
            else{
                rooms.getSelectedItem().toString();
            }
        }
    }


}
