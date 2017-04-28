/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskprocess;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author tangzhichao
 */
public class SLTaskProcess extends javax.swing.JComponent {

    private SLTaskPopuPane popuPane;
    private JLabel lblWait;

    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = getX() + getWidth() - 400;
            popuPane.show(SLTaskProcess.this, 0, -popuPane.getPreferredSize().height - 5);
        }
    };

    public SLTaskProcess() {
        initComponents();
        this.popuPane = new SLTaskPopuPane();
        this.lblWait = new JLabel();
        Font font = this.lblWait.getFont();
        font = new Font(font.getName(), Font.PLAIN, font.getSize());
        this.lblWait.setFont(font);
        this.addMouseListener(mouseListener);
        this.setLayout(new BorderLayout(5, 0));
        this.add(this.lblWait, BorderLayout.EAST);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    private void changeTask() {
//        this.removeAll();
//        if (!this.tasks.isEmpty()) {
//            this.add(this.tasks.get(this.tasks.size() - 1), BorderLayout.CENTER);
//            this.add(this.lblWait, BorderLayout.EAST);
//            if (this.tasks.size() > 2) {
//                this.lblWait.setText(String.format("(还有%d个任务在运行)", this.tasks.size() - 1));
//            } else {
//                this.lblWait.setText("");
//            }
//        }
//        this.updateUI();
    }

    private class SLTaskPopuPane extends JPopupMenu {

        private JList taskList = new JList();

        public SLTaskPopuPane() {
            this.setLayout(new BorderLayout(0, 0));
            this.setPopupSize(400, 25);
            this.add(new JScrollPane(taskList));

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true) {
//                        try {
//                            TimeUnit.SECONDS.sleep(1);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(SLTaskProcess.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        
//                        
//                        Iterator<TaskItemPane> it = tasks.iterator();
//                        for (Iterator<TaskItemPane> iterator = tasks.iterator(); iterator.hasNext();) {
//                            TaskItemPane item = iterator.next();
//                            if (item.isFinished()) {
//                                item.close();
//                                continue;
//                            }
//                            item.refresh();
//                        }
//                    }
//                }
//            }).start();
        }

        private void refresh() {
        }
    }

    private class TaskItemPane extends JPanel {

        private JLabel lblTip;
        private JProgressBar pbValue;
        private JButton btnClose;

        private SLTask task;

        public TaskItemPane(SLTask task, ActionListener action) {
            this.task = task;
            this.setLayout(new BorderLayout(5, 0));
            this.lblTip = new JLabel();
            this.pbValue = new JProgressBar();
            this.btnClose = new JButton();
            this.btnClose.addActionListener(action);
            //
            this.add(this.lblTip, BorderLayout.WEST);
            this.add(this.pbValue, BorderLayout.CENTER);
            this.add(this.btnClose, BorderLayout.EAST);
            //
            this.initBtn();
            this.initProcess();
            this.initLabel();
            this.refresh();
        }

        private void initBtn() {
            Icon hoverIcon = new ImageIcon(this.getClass().getResource("close.gif"));
            Icon defaultIcon = UIManager.getLookAndFeel().getDisabledIcon(null, hoverIcon);
            this.btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.btnClose.addMouseListener(mouseListener);
            this.btnClose.setIcon(defaultIcon);
            this.btnClose.setRolloverIcon(hoverIcon);
            this.btnClose.setPressedIcon(hoverIcon);
            this.btnClose.setText("");
            this.btnClose.setContentAreaFilled(false);
            this.btnClose.setBorder(null);
        }

        private void initProcess() {
            this.pbValue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.pbValue.setPreferredSize(new Dimension(150, 25));
            this.pbValue.setStringPainted(true);
            this.pbValue.setMaximum(100);
            this.pbValue.setMinimum(0);
            this.pbValue.setValue(1);
        }

        private void initLabel() {
            Font font = this.lblTip.getFont();
            font = new Font(font.getName(), Font.PLAIN, font.getSize());
            this.lblTip.setFont(font);
            this.lblTip.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        private void setValue(int value, String tip) {
            this.pbValue.setValue(value);
            this.lblTip.setText(tip);
        }

        public void setMaxValue(int value) {
            this.pbValue.setMaximum(value);
        }

        public void setMinValue(int value) {
            this.pbValue.setMinimum(value);
        }

        private void refresh() {
            this.setValue(task.getValue(), task.getTitle());
        }

        public void cancel() {
            this.close();
        }

        private boolean isFinished() {
            return this.task.isFinish();
        }

        private void close() {
            this.firePropertyChange("close", true, false);
        }

    }

    public static interface SLTask {

        String getTitle();

        int getValue();

        boolean isFinish();
    }

    public void addTask(SLTask task) {
//        final TaskItemPane panel = new TaskItemPane(task, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JButton btn = (JButton) e.getSource();
//                Window window = SwingUtilities.getWindowAncestor(btn);
//                int res = JOptionPane.showConfirmDialog(window, "你确定要关闭此任务吗？", "提示", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
//                if (res == JOptionPane.OK_OPTION) {
//                    SLTaskProcess.this.removeAll();
//                    tasks.remove(tasks.size() - 1);
//                    changeTask();
//                }
//            }
//        });
//        this.tasks.add(new TaskItemPane(task, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        }));
//        this.popuPane.refresh();
//        this.changeTask();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
