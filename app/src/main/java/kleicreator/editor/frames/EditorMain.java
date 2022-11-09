package kleicreator.editor.frames;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import kleicreator.editor.listeners.EditorWindowListener;
import kleicreator.editor.listeners.ProjectExplorerMouseListener;
import kleicreator.editor.nodes.NodeItem;
import kleicreator.editor.nodes.NodeRecipe;
import kleicreator.editor.nodes.NodeResource;
import kleicreator.editor.state.ProjectExplorerState;
import kleicreator.editor.tabs.Tab;
import kleicreator.master.Master;
import kleicreator.modloader.Mod;
import kleicreator.modloader.resources.Resource;
import kleicreator.modloader.resources.ResourceManager;
import kleicreator.recipes.Recipe;
import kleicreator.savesystem.SaveSystem;
import kleicreator.items.Item;
import kleicreator.logging.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EditorMain {
    private final JFrame frame;
    private JTree projectExplorer;
    private JTabbedPane projectTabs;
    private JPanel editorPane;
    private JScrollPane projectExplorerScrollPane;
    private ProjectExplorerState projectExplorerState;
    public List<Tab> tabs;

    public EditorMain(JFrame frame) {
        this.frame = frame;
        tabs = new ArrayList<>();

        // Title
        final int MAX_LENGTH = 20;
        String modName = Mod.modName;
        if (modName.length() > MAX_LENGTH) {
            modName = modName.substring(0, MAX_LENGTH);
            modName += "...";
        }
        frame.setTitle(String.format("KleiCreator %s | %s", Master.version, modName));

        // Icon
        frame.setIconImage(Master.icon.getImage());

        // Preferred size
        frame.setPreferredSize(new Dimension(1024, 720));

        // Close operation
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new EditorWindowListener(frame));

        // Remove nasty border around JScrollPane
        projectExplorerScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu project = new JMenu("Project");
        JMenu help = new JMenu("Help");

        // ===== FILE =====
        JMenuItem fileNew = new JMenuItem("New");
        fileNew.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/new.png")));
        fileNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Master.CreateNewMod();
            }
        });
        JMenuItem fileOpen = new JMenuItem("Open");
        fileOpen.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/open.png")));

        fileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Master.OpenProjectFromFiles(frame);
            }
        });
        file.add(fileNew);
        file.add(fileOpen);

        // ===== PROJECT =====
        JMenuItem projectSave = new JMenuItem("Save");
        projectSave.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/save.png")));
        projectSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveSystem.Save(Mod.path);
            }
        });
        project.add(projectSave);

        // ===== HELP =====

        menuBar.add(file);
        menuBar.add(project);
        menuBar.add(help);
        frame.setJMenuBar(menuBar);

        SetupProjectExplorer();
        SetupTabs();

        frame.setContentPane(editorPane);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void SetupProjectExplorer() {
        projectExplorerState = new ProjectExplorerState();
        projectExplorer.setModel(projectExplorerState.projectExplorerModel);
        projectExplorer.addMouseListener(new ProjectExplorerMouseListener(projectExplorer, projectExplorerState, this));
        projectExplorer.setToggleClickCount(0);

        UpdateProjectExplorer();
    }

    public void UpdateProjectExplorer() {
        projectExplorerState.items.removeAllChildren();
        projectExplorerState.recipes.removeAllChildren();
        projectExplorerState.resources.removeAllChildren();

        for (Item item : Mod.items) {
            projectExplorerState.items.add(new NodeItem(item, item.itemId));
        }

        for (Recipe recipe : Mod.recipes) {
            projectExplorerState.recipes.add(new NodeRecipe(recipe, String.format("%s (%s)", recipe.result, recipe.id)));
        }

        for (Resource resource : ResourceManager.resources) {
            projectExplorerState.resources.add(new NodeResource(resource, resource));
        }

        projectExplorer.invalidate();
        frame.pack();
    }

    public void SetupTabs() {
        UpdateTabs();
    }

    public void UpdateTabs() {
        List<Tab> tabList = new ArrayList<>();
        int totalTabs = projectTabs.getTabCount();
        for (int i = 0; i < totalTabs; i++) {
            Component component;
            try {
                component = projectTabs.getComponentAt(i);
            } catch (IndexOutOfBoundsException e) {
                Logger.Error(e);
                break;
            }

            if (component instanceof Tab) {
                Tab tab = (Tab) component;
                if (!tabs.contains(tab)) {
                    projectTabs.remove(i);
                    i--;
                    continue;
                }
                tabList.add(tab);
            } else {
                projectTabs.remove(i);
                i--;
                continue;
            }
        }

        for (Tab tab : tabs) {
            if (!tabList.contains(tab)) {
                projectTabs.addTab(tab.title, tab);
                projectTabs.setTabComponentAt(projectTabs.getTabCount() - 1, tab.BuildTabComponent(this));
            }
        }

        frame.revalidate();
    }

    public void SetTabFocus(Tab tab) {
        int totalTabs = projectTabs.getTabCount();
        for (int i = 0; i < totalTabs; i++) {
            Component component = projectTabs.getComponentAt(i);

            if (component == tab) {
                projectTabs.setSelectedIndex(i);
                return;
            }
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        editorPane = new JPanel();
        editorPane.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        projectTabs = new JTabbedPane();
        projectTabs.setTabLayoutPolicy(1);
        editorPane.add(projectTabs, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 200), null, 0, false));
        projectExplorerScrollPane = new JScrollPane();
        projectExplorerScrollPane.setHorizontalScrollBarPolicy(32);
        editorPane.add(projectExplorerScrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, -1), null, null, 0, false));
        projectExplorer = new JTree();
        projectExplorer.setShowsRootHandles(false);
        projectExplorerScrollPane.setViewportView(projectExplorer);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return editorPane;
    }

}
