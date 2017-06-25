/*******************************************************************************
 * Copyright 2017 Hewlett-Packard Enterprise Development Company, L.P.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.hpe.octane.ideplugins.eclipse.ui.entitylist.custom;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.hpe.octane.ideplugins.eclipse.util.resource.SWTResourceManager;

import swing2swt.layout.BorderLayout;
import swing2swt.layout.FlowLayout;

public class EntityModelRow extends Composite {

    public enum DetailsPosition {
        TOP, BOTTOM
    }

    private static StyleRange[] emptyRange;
    private StyledText lblEntityTitle;
    private StyledText lblEntitySubtitle;
    private Composite compositeTopDetails;
    private Composite compositeBottomDetails;
    private Label lblEntityIcon;
    private Label label;

    /**
     * Create the composite.
     * 
     * @param parent
     * @param style
     */
    public EntityModelRow(Composite parent, int style) {
        super(parent, style);
        setLayout(new BorderLayout(0, 0));

        lblEntityIcon = new Label(this, SWT.NONE);
        lblEntityIcon.setLayoutData(BorderLayout.WEST);

        Composite compositeTitles = new Composite(this, SWT.NONE);
        compositeTitles.setLayoutData(BorderLayout.CENTER);
        GridLayout gl_compositeTitles = new GridLayout(1, false);
        gl_compositeTitles.marginTop = 2;
        compositeTitles.setLayout(gl_compositeTitles);

        lblEntityTitle = new StyledText(compositeTitles, SWT.READ_ONLY | SWT.WRAP | SWT.SINGLE);
        lblEntityTitle.setToolTipText(
                "weqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqw");
        lblEntityTitle.setEnabled(false);
        lblEntityTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        lblEntityTitle.setText(
                "weqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqwweqeqweqw");
        lblEntityTitle.setAlwaysShowScrollBars(false);
        lblEntityTitle.setDoubleClickEnabled(false);
        lblEntityTitle.setEditable(false);

        lblEntitySubtitle = new StyledText(compositeTitles, SWT.READ_ONLY | SWT.WRAP | SWT.SINGLE);
        lblEntitySubtitle.setEnabled(false);
        lblEntitySubtitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        lblEntitySubtitle.setText("ewqeqw");
        lblEntitySubtitle.setAlwaysShowScrollBars(false);
        lblEntitySubtitle.setDoubleClickEnabled(false);
        lblEntitySubtitle.setEditable(false);

        Composite compositeDetails = new Composite(this, SWT.NONE);
        compositeDetails.setLayoutData(BorderLayout.EAST);
        GridLayout gl_compositeDetails = new GridLayout(1, false);
        gl_compositeDetails.marginTop = 5;
        gl_compositeDetails.marginHeight = 0;
        compositeDetails.setLayout(gl_compositeDetails);

        compositeTopDetails = new Composite(compositeDetails, SWT.NONE);
        compositeTopDetails.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
        compositeTopDetails.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        compositeBottomDetails = new Composite(compositeDetails, SWT.NONE);
        compositeBottomDetails.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
        compositeBottomDetails.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
        label.setLayoutData(BorderLayout.SOUTH);

    }

    public void setBackgroundColor(Color color) {
        this.setBackground(color);
    }

    public void setEntityIcon(Image entityIconImage) {
        lblEntityIcon.setImage(entityIconImage);
    }

    public void setEntityTitle(Integer id, String entityName) {

        entityName = checkEmptyValue(entityName);

        String idString;

        if (id != null) {
            idString = String.valueOf(id);
        } else {
            idString = "";
        }

        String title = idString + " " + entityName;

        StyleRange styleRange = new StyleRange();
        styleRange.start = title.indexOf(idString);
        styleRange.length = idString.length();
        styleRange.fontStyle = SWT.BOLD;

        lblEntityTitle.setText(title);
        lblEntityTitle.setToolTipText(title);
        lblEntityTitle.setStyleRanges(new StyleRange[] { styleRange });
    }

    public void setEntitySubTitle(String subtitle) {
        setEntitySubTitle(subtitle, emptyRange);
    }

    public void setEntitySubTitle(String subtitle, String defaultValue) {
        if (StringUtils.isEmpty(subtitle)) {
            setEntitySubTitle(defaultValue, emptyRange);
        } else {
            setEntitySubTitle(subtitle, emptyRange);
        }
    }

    public void setEntitySubTitle(String subtitle, StyleRange[] styleRanges) {
        subtitle = checkEmptyValue(subtitle);
        lblEntitySubtitle.setText(subtitle);
        lblEntitySubtitle.setToolTipText(subtitle);
        try {
            if (styleRanges != null) {
                lblEntitySubtitle.setStyleRanges(styleRanges);
            }
        } catch (Exception ex) {
        }
    }

    public void addDetails(String fieldName, String fieldValue, DetailsPosition position) {

        Composite parent;
        if (DetailsPosition.TOP == position) {
            parent = compositeTopDetails;
        } else {
            parent = compositeBottomDetails;
        }

        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout(3, false);
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        composite.setLayout(gridLayout);

        Label lblSeparator = new Label(composite, SWT.SEPARATOR | SWT.VERTICAL);
        GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_label.heightHint = lblSeparator.getFont().getFontData()[0].getHeight() + 2;
        gd_label.verticalIndent = 2;
        lblSeparator.setLayoutData(gd_label);

        if (StringUtils.isNotEmpty(fieldName)) {
            Label lblKey = new Label(composite, SWT.NONE);
            GridData gd_lblKey = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
            gd_lblKey.verticalIndent = 2;
            lblKey.setLayoutData(gd_lblKey);
            lblKey.setForeground(SWTResourceManager.getColor(128, 128, 128));
            lblKey.setText(fieldName + ": ");
        }

        Label lblValue = new Label(composite, SWT.NONE);
        lblValue.setForeground(SWTResourceManager.getColor(0, 0, 0));
        Font font = lblValue.getFont();
        lblValue.setFont(SWTResourceManager.getBiggerFont(font, font.getFontData()[0].getHeight() + 1));

        fieldValue = checkEmptyValue(fieldValue);
        lblValue.setText(fieldValue);

    }

    public void setLabelFontColor(Color color) {
        setLabelFontColor(this, color);
    }

    @Override
    public void setForeground(Color color) {
        setLabelFontColor(this, color);
    }

    private void setLabelFontColor(Control control, Color color) {
        if (control instanceof Composite) {
            Arrays.stream(((Composite) control).getChildren())
                    .forEach(child -> setLabelFontColor(child, color));
        }
        if (control instanceof Label) {
            Label lbl = (Label) control;
            lbl.setForeground(color);
        }
        if (control instanceof StyledText) {
            StyledText lbl = (StyledText) control;
            lbl.setForeground(color);
        }
    }

    private String checkEmptyValue(String fieldValue) {
        if (fieldValue == null || fieldValue.trim().length() == 0) {
            fieldValue = "-";
        } else {
            fieldValue = fieldValue.trim();
        }
        return fieldValue;
    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}
