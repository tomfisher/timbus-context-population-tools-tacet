package squirrel.view;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import squirrel.model.MetaClassifier;
import weka.classifiers.Classifier;

public class SortedComboBox<E> extends JComboBox<E> {

    private List<String> list;

    public SortedComboBox() {
        super();
        this.setModel(new MyComboBoxModel());
        this.setRenderer(new MyComboRenderer(this));
        list = new ArrayList<String>();
    }

    public void addClassifier(E item) {
        if (item instanceof MetaClassifier) {
            MetaClassifier c = (MetaClassifier) item;
            if (c.getType() != null) {
                String type = c.getType();
                String name = c.getName();
                if (!list.contains(type)) {
                    list.add(type);
                    addItem((E) type);
                    list.add(name);
                    addItem((E) c);
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).equals(type)) {
                            list.add(i + 1, name);
                            insertItemAt((E) c, i + 1);
                            break;
                        }
                    }
                }
//            } else {
//                String name = c.getName();
//                if (!list.contains(name)) {
//                    list.add(0, name);
//                    insertItemAt((E) c, 0);
//                }
           }
        }
    }

    class MyComboBoxModel extends DefaultComboBoxModel {

        @Override
        public void setSelectedItem(Object anObject) {

            if (anObject != null) {

                if (anObject instanceof MetaClassifier) {
                    super.setSelectedItem(anObject);
                }

            } else {
                super.setSelectedItem(anObject);

            }

        }

    }

    class MyComboRenderer extends BasicComboBoxRenderer {

        private static final long serialVersionUID = 1L;
        private JComboBox comboBox;
        final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
        private int row;

        MyComboRenderer(JComboBox fontsBox) {
            comboBox = fontsBox;
        }

        // private void manItemInCombo() {
        // if (comboBox.getItemCount() > 0) {
        // final Object comp = comboBox.getUI().getAccessibleChild(comboBox, 0);
        // if ((comp instanceof JPopupMenu)) {
        // final JList list = new JList(comboBox.getModel());
        // final JPopupMenu popup = (JPopupMenu) comp;
        // final JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
        // final JViewport viewport = scrollPane.getViewport();
        // final Rectangle rect = popup.getVisibleRect();
        // final Point pt = viewport.getViewPosition();
        // row = list.locationToIndex(pt);
        // }
        // }
        // }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            // if (list.getModel().getSize() > 0) {
            // manItemInCombo();
            // }
            // final JLabel renderer = (JLabel)
            // defaultRenderer.getListCellRendererComponent(list, value, row,
            // isSelected, cellHasFocus);
            // final Object fntObj = value;
            // final String fontFamilyName = (String) fntObj;
            if (value instanceof MetaClassifier) {
                MetaClassifier c = (MetaClassifier) value;
                    setText(" " + c.getName());
                setFont(new Font("Courier", Font.PLAIN, 12));
            } else {
                setFont(new Font("Courier", Font.BOLD, 12));
            }
            return this;
        }
    }
}
