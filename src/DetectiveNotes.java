import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class DetectiveNotes extends JDialog {
    private JComboBox<String> weapons, people, rooms;
    public DetectiveNotes(ClueGame list){
        weapons = createWeaponCombo(list.getWeapons());
        people = createPersonCombo(list.getPlayers());
        rooms = createRoomCombo();
        ComboListener listener = new ComboListener();
        weapons.addActionListener(listener);
        people.addActionListener(listener);
        rooms.addActionListener(listener);
        setTitle("Detective Notes");
        setSize(300, 200);
        setLayout(new GridLayout(3,2));
        JPanel person = new JPanel();
        person.add(people);
        person.setBorder(new TitledBorder (new EtchedBorder(), "People Guess"));

        JPanel weapon = new JPanel();
        weapon.add(weapons);
        weapon.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));

        JPanel room = new JPanel();
        room.add(rooms);
        room.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));

        JPanel arm = new JPanel();
        arm = addWeaponsToPanel(arm,list.getWeapons());
        arm.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));

        JPanel guest = new JPanel();
        guest = addPeopleToPanel(guest, list.getPlayers());
        guest.setBorder(new TitledBorder (new EtchedBorder(), "People"));

        JPanel place = new JPanel();
        place = addRoomsToPanel(place);
        place.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));

        add(guest);
        add(person);
        add(place);
        add(room);
        add(arm);
        add(weapon);
    }
    private JComboBox<String> createWeaponCombo(ArrayList<String> list)
    {
        JComboBox<String> combo = new JComboBox<String>();
        for (String s: list){
            combo.addItem(s);
        }

        return combo;
    }
    private JComboBox<String> createPersonCombo(ArrayList<Player> list)
    {
        JComboBox<String> combo = new JComboBox<String>();
        for (Player p : list){
            combo.addItem(p.getName());
        }
        return combo;
    }
    private JComboBox<String> createRoomCombo()
    {
        JComboBox<String> combo = new JComboBox<String>();
        combo.addItem("Rohan");
        combo.addItem("Dunland");
        combo.addItem("Gondor");
        combo.addItem("Mirkwood");
        combo.addItem("Ash Mountains");
        combo.addItem("The Shire");
        combo.addItem("Mordor");
        combo.addItem("Rivendell");
        combo.addItem("Rhun");
        return combo;
    }
    private JPanel addWeaponsToPanel(JPanel j,ArrayList<String> list)
    {
        for (String s: list){
            j.add(new JCheckBox(s));
        }
        return j;
    }

    private JPanel addPeopleToPanel(JPanel j, ArrayList<Player> list){
        for (Player p : list){
            j.add(new JCheckBox(p.getName()));
        }
        return j;
    }

    private JPanel addRoomsToPanel(JPanel j)
    {
        j.add(new JCheckBox("Rohan"));
        j.add(new JCheckBox("Dunland"));
        j.add(new JCheckBox("Gondor"));
        j.add(new JCheckBox("Mirkwood"));
        j.add(new JCheckBox("Ash Mountains"));
        j.add(new JCheckBox("The Shire"));
        j.add(new JCheckBox("Mordor"));
        j.add(new JCheckBox("Rivendell"));
        j.add(new JCheckBox("Rhun"));
        return j;
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
