package com.hpe.octane.ideplugins.eclipse.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.hpe.adm.octane.services.filtering.Entity;
import com.hpe.octane.ideplugins.eclipse.util.EntityIconFactory;

public class TestHeaderPanel {
    protected Shell shell;
    private static final EntityIconFactory entityIconFactory = new EntityIconFactory(40, 40, 14);
    /**
     * Launch the application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
        	TestHeaderPanel window = new TestHeaderPanel();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        try {
            shell.open();
            shell.layout();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
        } finally {
            if (!shell.isDisposed()) {
                shell.dispose();
            }
        }
        display.dispose();
        System.exit(0);
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
        shell.setLayout(new FillLayout(SWT.HORIZONTAL));
        
        Composite composite = new Composite(shell, SWT.NONE);
        composite.setLayout(new org.eclipse.swt.layout.GridLayout(4, false));
        
        Label entityIcon = new Label(composite, SWT.NONE);
        entityIcon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        entityIcon.setImage(entityIconFactory.getImageIcon(Entity.DEFECT));
        
        Label lblEntityName = new Label(composite, SWT.NONE);
        lblEntityName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        lblEntityName.setText("Entity name");
        
        Label lblCurrentPhase = new Label(composite, SWT.NONE);
        lblCurrentPhase.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        lblCurrentPhase.setText("current phase");
        
        Combo comboNextPhases = new Combo(composite, SWT.NONE);
        comboNextPhases.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        
    }
}