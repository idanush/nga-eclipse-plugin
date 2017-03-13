package com.hpe.octane.ideplugins.eclipse.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class DummyCommand extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

        // create a dialog with ok and cancel buttons and a question icon
        MessageBox dialog = new MessageBox(window.getShell(), SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
        dialog.setText("My info");
        dialog.setMessage("Do you really want to do this?");

        // open dialog and await user selection
        dialog.open();

        return null;
    }
}