/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.mongkie.visualedit.editors.util;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.beans.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import org.mongkie.visualedit.editors.EnumTagsPropertyEditor;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.explorer.propertysheet.PropertySheet;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;

/**
 * A few utility methods useful to implementors of Inplace Editors.
 *
 * @author Tim Boudreau
 */
final class PropUtils {

    /**
     * If true, hides custom editor buttons unless in editing mode. Auto popup
     * of combo boxes is suppressed in this mode
     */
    static final boolean noCustomButtons = Boolean.getBoolean("netbeans.ps.noCustomButtons"); //NOI18N
    /**
     * If true, radio button boolean editor will always be used
     */
    static boolean forceRadioButtons =
            //          !Boolean.getBoolean ("netbeans.ps.useCheckbox");
            Boolean.getBoolean("netbeans.ps.forceRadioButtons");
    /**
     * If true, caption on the checkbox boolean editor will be suppressed
     */
    static final boolean noCheckboxCaption = !Boolean.getBoolean("netbeans.ps.checkboxCaption"); //NOI18N
    /**
     * Flag which, when true, property set expansion handles will not be shown
     * when the node only has one property set. Leaving as an option since there
     * is still disagreement about the right way this should work, and I don't
     * want to repeatedly reimplement it
     */
    static final boolean hideSingleExpansion = Boolean.getBoolean("netbeans.ps.hideSingleExpansion"); //NOI18N
    static final boolean neverMargin = true;
    //    static final boolean neverMargin = Boolean.getBoolean(
    //        "netbeans.ps.neverMargin"); //NOI18N
    /**
     * If true, user will have to press enter to write the value, otherwise (the
     * default), if a cell loses focus, its value gets written.
     */
    static final boolean psCommitOnFocusLoss = !Boolean.getBoolean("netbeans.ps.NoCommitOnFocusLoss");
    /**
     * If true, the default help button inside property sheet will not be
     * present.
     */
    static final boolean psNoHelpButton = Boolean.getBoolean("netbeans.ps.noHelpButton");
    /**
     * UIManager key for alternate color for table - if present, color will
     * alternate between the standard background and this color
     */
    private static final String KEY_ALTBG = "Tree.altbackground"; //NOI18N
    /**
     * UIManager key for the background color for expandable sets. If not set,
     * the color will be derived from the default tree background color
     */
    private static final String KEY_SETBG = "PropSheet.setBackground"; //NOI18N
    /**
     * UIManager key for background color of expandable sets when selected. If
     * not set, the color will be derived from the default tree selection
     * background color
     */
    private static final String KEY_SELSETBG = "PropSheet.selectedSetBackground"; //NOI18N
    /**
     * UIManager key for foreground color of expandable sets. If not set, the
     * color will be derived from the default tree selection background color
     */
    private static final String KEY_SETFG = "PropSheet.setForeground"; //NOI18N
    /**
     * UIManager key for foreground color of expandable sets when selected. If
     * not set, the color will be derived from the default tree selection
     * background color
     */
    private static final String KEY_SELSETFG = "PropSheet.selectedSetForeground"; //NOI18N
    /**
     * UIManager key for integer icon margin, amount of space to add beside the
     * expandable set icon to make up the margin
     */
    private static final String KEY_ICONMARGIN = "netbeans.ps.iconmargin"; //NOI18N
    /**
     * UIManager key for fixed row height for rows in property sheet. If not
     * explicitly set, row height will be derived from the target font
     */
    static final String KEY_ROWHEIGHT = "netbeans.ps.rowheight"; //NOI18N

    private static Preferences preferences() {
        return NbPreferences.forModule(PropUtils.class);
    }
    /**
     * Preferences key for the show description area property
     */
    private static final String PREF_KEY_SHOWDESCRIPTION = "showDescriptionArea"; //NOI18N
    /**
     * Preferences key for the storage of closed set names
     */
    private static final String PREF_KEY_CLOSEDSETNAMES = "closedSetNames"; //NOI18N
    /**
     * Preferences key for the storage of sort order
     */
    private static final String PREF_KEY_SORTORDER = "sortOrder"; //NOI18N
    /**
     * Disabled foreground color
     */
    static Color disFg = null;
    /**
     * Factor by which default font is larger/smaller than 12 point, used for
     * calculating preferred sizes and compensating for larger font size
     */
    static float fsfactor = -1f;
    /**
     * Minimum width for a property panel
     */
    static int minW = -1;
    /**
     * Minimum height for a property panel
     */
    static int minH = -1;
    /**
     * TextField foreground color for property panel
     */
    private static Color tfFg = null;
    /**
     * TextField background color for property panel
     */
    private static Color tfBg = null;
    /**
     * Flag for presence of an alternative background color (alternating "zebra"
     * style painting in property sheet)
     */
    static Boolean noAltBg = null;
    /**
     * Field to hold the width of the margin. This is used for painting, so the
     * grid is not displayed in the margin, and for figuring out if a mouse
     * event occured in the margin (toggle expanded on a single click) or not.
     */
    static int marginWidth = -1;
    /**
     * Field to hold additional space between spinner icons and set text
     */
    private static int iconMargin = -1;
    /**
     * Color for selected property set expanders - should be darker than the
     * selection color for regular properties, to differentiate and be
     * consistent with their unselected color
     */
    static Color selectedSetRendererColor = null;
    /**
     * Color for property set expanders
     */
    static Color setRendererColor = null;
    /**
     * Cached height of for the icon
     */
    static int spinnerHeight = -1;
    /**
     * UIManager or derived control color
     */
    static Color controlColor = null;
    /**
     * UIManager or derived shadow color
     */
    static Color shadowColor = null;
    /**
     * Alternative background color
     */
    static Color altBg = null;
    /**
     * Tab name for basic properties
     */
    private static String bptn = null;
    /**
     * Comparator used by property sheet
     */
    private static Comparator comp = null;
    /**
     * Left hand margin for properties in the right column of the sheet
     */
    private static int textMargin = -1;
    /**
     * Foreground color for expando sets
     */
    private static Color setForegroundColor = null;
    /**
     * Foreground color for expando sets when selected
     */
    private static Color selectedSetForegroundColor = null;
    private static final Logger LOG = Logger.getLogger(PropUtils.class.getName());
    /**
     * Painting the custom editor button is the most expensive thing we do on XP
     * and Aqua; if this flag is set, the button panel will build a bitmap and
     * blit it (this isn't appropriate for Metal, where the background color of
     * the button changes if it is selected
     */
    private static Boolean useOptimizedCustomButtonPainting = null;
    static final boolean isAqua = "Aqua".equals(UIManager.getLookAndFeel().getID()); //NOI18N
    static final boolean isGtk = "GTK".equals(UIManager.getLookAndFeel().getID()); //NOI18N
    private static Graphics scratchGraphics = null;
    //Comparators copied from original propertysheet implementation
    /**
     * Comparator which compares types
     */
    private final static Comparator<Node.Property> SORTER_TYPE = new Comparator<Node.Property>() {

        @Override
        public int compare(Node.Property l, Node.Property r) {

            Class t1 = l.getValueType();
            Class t2 = r.getValueType();
            String s1 = (t1 != null) ? t1.getName() : ""; //NOI18N
            String s2 = (t2 != null) ? t2.getName() : ""; //NOI18N

            int s = s1.compareToIgnoreCase(s2);

            if (s != 0) {
                return s;
            }

            s1 = l.getDisplayName();
            s2 = r.getDisplayName();

            return s1.compareToIgnoreCase(s2);
        }

        @Override
        public String toString() {
            return "Type comparator"; //NOI18N
        }
    };
    /**
     * Comparator which compares PropertyDetils names
     */
    private final static Comparator<Node.Property> SORTER_NAME = new Comparator<Node.Property>() {

        @Override
        public int compare(Node.Property l, Node.Property r) {
            String s1 = l.getDisplayName();
            String s2 = r.getDisplayName();

            return String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
        }

        @Override
        public String toString() {
            return "Name comparator"; //NOI18N
        }
    };
    private static java.util.List<String> missing = null;
    // #52179 don't affect just edited properties or their current
    // changes will be lost due to the firing PropertyChangeEvents to
    // theirs UI counterpart
    private static Set<Property> externallyEdited = new HashSet<Property>(3);

    /**
     * Private constructor to hide from API
     */
    private PropUtils() {
        //do nothing
    }

    /**
     * If true, ButtonPanel will build a bitmap of the custom editor button to
     * use when painting - huge amounts of painting time in XP and Aqua are used
     * scaling the L&F's background button bitmap (and the custom editor button
     * is always a fixed size), so this yields better performance when a large
     * number of custom editor buttons are displayed.
     */
    static boolean useOptimizedCustomButtonPainting() {
        if (useOptimizedCustomButtonPainting == null) {
            if ("Windows".equals(UIManager.getLookAndFeel().getID())) { //NOI18N
                useOptimizedCustomButtonPainting = Boolean.valueOf(isXPTheme());
            } else {
                useOptimizedCustomButtonPainting = Boolean.valueOf("Aqua".equals(UIManager.getLookAndFeel().getID()));
            }
        }

        return useOptimizedCustomButtonPainting.booleanValue();
    }

    static void log(Class clazz, String msg, boolean dumpstack) {
        log(clazz, msg);

        if (dumpstack) {
            dumpStack(clazz);
        }
    }

    //logging code borrowed from winsys
    static void log(Class clazz, String msg) {
        Logger.getLogger(clazz.getName()).fine(msg);
    }

    static void log(Class clazz, FocusEvent fe) {
        if (isLoggable(clazz)) {
            StringBuffer sb = new StringBuffer(30);
            focusEventToString(fe, sb);
            log(clazz, sb.toString());
        }
    }

    static boolean isLoggable(Class clazz) {
        return Logger.getLogger(clazz.getName()).isLoggable(Level.FINE);
    }

    static void logFocusOwner(Class clazz, String where) {
        if (isLoggable(clazz)) {
            StringBuffer sb = new StringBuffer(where);
            sb.append(" focus owner: "); //NOI18N

            Component owner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
            compToString(owner, sb);
        }
    }

    static void focusEventToString(FocusEvent fe, final StringBuffer sb) {
        Component target = (Component) fe.getSource();
        Component opposite = fe.getOppositeComponent();
        sb.append(" focus "); //NOI18N
        sb.append((fe.getID() == FocusEvent.FOCUS_GAINED) ? " gained by " : " lost by "); //NOI18N
        compToString(target, sb);
        sb.append((fe.getID() == FocusEvent.FOCUS_GAINED) ? " from " : " to "); //NOI18N
        compToString(opposite, sb);
        sb.append(" isTemporary: "); //NOI18N
        sb.append(fe.isTemporary());
    }

    static void compToString(Component c, final StringBuffer sb) {
        if (c == null) {
            sb.append(" null "); //NOI18N

            return;
        }

        String name = c.getName();
        Class clazz = c.getClass();
        String classname = clazz.getName();
        int i = classname.lastIndexOf('.');

        if ((i != -1) && (i != (classname.length() - 1))) {
            classname = classname.substring(i + 1);
        }

        if (name != null) {
            sb.append("\""); //NOI18N
            sb.append(name);
            sb.append("\" ("); //NOI18N
            sb.append(classname);
            sb.append(") "); //NOI18N
        } else {
            sb.append(' '); //NOI18N
            sb.append(classname);
            sb.append(' '); //NOI18N
        }

        if (!c.isVisible()) {
            sb.append(" [NOT VISIBLE] "); //NOI18N
        }

        if (!c.isDisplayable()) {
            sb.append(" [HAS NO PARENT COMPONENT] "); //NOI18N
        }
    }

    public static void dumpStack(Class clazz) {
        if (Logger.getLogger(clazz.getName()).isLoggable(Level.FINE)) {
            StringWriter sw = new StringWriter();
            new Throwable().printStackTrace(new PrintWriter(sw));
            log(clazz, sw.getBuffer().toString());
        }
    }

    /**
     * Get the color for the custom editor button if specified by the theme or
     * look and feel, or null if the defaults should simply be used
     */
    static Color getButtonColor() {
        return UIManager.getColor("netbeans.ps.buttonColor"); //NOI18N
    }

    /**
     * Get the width required by the custom editor button (this varies with font
     * size).
     */
    static int getCustomButtonWidth() {
        Icon ic = getCustomButtonIcon();

        return ic.getIconWidth() + (isAqua ? 5 : 3);
    }

    /**
     * Get a scratch graphics object which can be used to calculate string
     * widths offscreen
     */
    static Graphics getScratchGraphics(Component c) {
        if (scratchGraphics == null) {
            scratchGraphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
        }

        return scratchGraphics;
    }

    /**
     * Get the color that should be used for text when an error or exception is
     * encountered in displaying a value. Either the look and feel or theme can
     * supply a color via the UIDefaults key nb.errorColor or a default
     * (currently Color.RED) will be used
     */
    static Color getErrorColor() {
        //allow theme.xml to override error color
        Color result = UIManager.getColor("nb.errorForeground"); //NOI18N

        if (result == null) {
            result = Color.RED;
        }

        return result;
    }

    /**
     * Get the foreground color for text on disabled components
     */
    static Color getDisabledForeground() {
        if (disFg == null) {
            disFg = UIManager.getColor("textInactiveText"); //NOI18N

            if (disFg == null) {
                disFg = Color.GRAY;
            }
        }

        return disFg;
    }

    /**
     * Get a factor of the difference between the default font size NetBeans
     * uses, and the actual font size which may be different if the -fontsize
     * argument was used on startup.
     */
    static float getFontSizeFactor() {
        if (fsfactor == -1) {
            Font f = UIManager.getFont("controlFont"); //NOI18N

            if (f == null) {
                JLabel jl = new JLabel();
                f = jl.getFont();
            }

            int baseSize = 12; //default font size
            fsfactor = baseSize / f.getSize();
        }

        return fsfactor;
    }

    /**
     * Minimum width for an instance of PropPanel, based on the default font
     * size
     */
    static int getMinimumPropPanelWidth() {
        if (minW == -1) {
            int base = 50;
            minW = Math.round(base * getFontSizeFactor());
        }

        return minW;
    }

    /**
     * Minimum height for an instance of PropPanel based on the default font
     * size
     */
    static int getMinimumPropPanelHeight() {
        if (minH == -1) {
            int base = 18;
            minH = Math.round(base * getFontSizeFactor());
        }

        return minH;
    }

    /**
     * Minimum size for an instance of PropPanel based on the default font size
     */
    static Dimension getMinimumPanelSize() {
        return new Dimension(getMinimumPropPanelWidth(), getMinimumPropPanelHeight());
    }

    /**
     * Try to update a property editor with a value object (presumably) provided
     * by an InplaceEditor. If exceptions are thrown, returns them for the
     * caller to do with as it wishes (for example a panel that commits multiple
     * values from several editors at once will probably want to aggregate them
     * into a single error message)
     */
    static Exception updatePropertyEditor(PropertyEditor ed, Object value) {
        //        System.err.println("UpdatePropertyEditor " + ed + " to " + value );
        Exception result = null;

        try {
            if (value instanceof String) {
                try {
                    ed.setAsText((String) value);
                } catch (IllegalArgumentException iaE) {
                    //#137706 - always treat iae from setAsText as a an invalid
                    //user input instead of broken code and display nice error message to the user
                    if (null == Exceptions.findLocalizedMessage(iaE)) {
                        Exceptions.attachLocalizedMessage(iaE, NbBundle.getMessage(PropUtils.class, "MSG_SetAsText_InvalidValue", value));
                    }
                    result = iaE;
                }
            } else {
                ed.setValue(value);
            }
        } catch (Exception e) {
            result = e;
        }

        //        System.err.println("returning " + result);
        return result;
    }

    /**
     * Fetches a localized message for an exception that may be displayed to the
     * user. If no localized message can be found in the annotations, it will
     * provide a generic one.
     *
     * @see
     * org.openide.explorer.propertysheet.PropertyDisplayer.Editable.isModifiedValueLegal
     */
    static synchronized String findLocalizedMessage(Throwable throwable, Object newValue, String title) {
        try {
            if (throwable == null) {
                //need to catch this - for mysterious reasons, calling 
                //getLocalizedMessage on a null throwable hangs/or results in an
                //endless loop - thread will stop doing anything unrecoverably
                return null;
            }

            if (throwable.getLocalizedMessage() == null ? throwable.getMessage() != null : !throwable.getLocalizedMessage().equals(throwable.getMessage())) {
                return throwable.getLocalizedMessage();
            }

            String msg = Exceptions.findLocalizedMessage(throwable);
            if (msg != null) {
                return msg;
            }

            if (throwable instanceof NumberFormatException) {
                //Handle NFE's from the core sun.beans property editors w/o raising stack traces
                return MessageFormat.format(
                        NbBundle.getMessage(PropUtils.class, "FMT_BAD_NUMBER_FORMAT"), //NOI18N
                        new Object[]{newValue});
            }
            //No localized message could be found, log the exception
            //ErrorManager.getDefault().annotate(throwable, ErrorManager.WARNING, null, null, null, null);

            //punt
            return MessageFormat.format(
                    NbBundle.getMessage(PropUtils.class, "FMT_CannotUpdateProperty"), new Object[]{newValue, title}); //NOI18N
        } catch (Exception e) {
            //We ABSOLUTELY cannot let this method throw exceptions or it will
            //quietly endlessly 
            Exceptions.printStackTrace(e);

            return null;
        }
    }

    /**
     * Utility method to fetch a comparator for properties based on sorting mode
     * defined in PropertySheet.
     */
    static Comparator<Property> getComparator(int sortingMode) {
        switch (sortingMode) {
            case PropertySheet.UNSORTED:
                return null;

            case PropertySheet.SORTED_BY_NAMES:
                return SORTER_NAME;

            case PropertySheet.SORTED_BY_TYPES:
                return SORTER_TYPE;

            default:
                throw new IllegalArgumentException("Unknown sorting mode: " + Integer.toString(sortingMode)); //NOI18N
        }
    }

    /**
     * Create a ComboBoxUI that does not display borders on the combo box. Since
     * the property sheet is rendered as a table, this looks better. This UI
     * also delegates closing of its popup to the property sheet, which has
     * better knowledge of when this is appropriate, in the case of focus
     * changes. Inplace editors which employ a combo box should use the UI
     * returned by this method, rather than the default for the look and feel.
     * Thus the appearance will be consistent with the rest of the property
     * sheet.
     */
    public static javax.swing.plaf.ComboBoxUI createComboUI(JComboBox box, boolean tableUI) {
        return new CleanComboUI(tableUI);
    }

    /**
     * A convenience map for missing property editor classes, so users are only
     * notified once, not every time a table cell is drawn.
     */
    private static java.util.List<String> getMissing() {
        if (missing == null) {
            missing = new ArrayList<String>();
        }

        return missing;
    }

    private static PropertyEditor ignored(PropertyEditor p) {
        if (p != null && p.getClass().getName().equals("sun.beans.editors.EnumEditor")) { // NOI18N
            return null;
        } else {
            return p;
        }
    }

    /**
     * Gets a property editor appropriate to the class. First checks {@link PropertyEditorManager}.
     * Also handles enum types, and has a fallback dummy editor.
     */
    static PropertyEditor getPropertyEditor(Class<?> c) {
        PropertyEditor result = ignored(PropertyEditorManager.findEditor(c));
        ClassLoader global = Lookup.getDefault().lookup(ClassLoader.class);
        ClassLoader now = Thread.currentThread().getContextClassLoader();
        if (result == null && global != null && now != global) {
            try {
                Thread.currentThread().setContextClassLoader(global);
                result = PropertyEditorManager.findEditor(c);
            } finally {
                Thread.currentThread().setContextClassLoader(now);
            }
        }

        if (result == null && Enum.class.isAssignableFrom(c)) {
            // XXX should this rather be done in Node.getPropertyEditor?
            result = new EnumTagsPropertyEditor(c.asSubclass(Enum.class));
        }

        if (result == null) {
            result = new NoPropertyEditorEditor();
        }

        return result;
    }

    /**
     * Get the basic color for controls in the look and feel, or a reasonable
     * default if the look and feel does not supply a value from
     * UIManager.getColor(&quot;control&quot;)
     */
    static Color getControlColor() {
        if (controlColor == null) {
            deriveColorsAndMargin();
        }

        return controlColor;
    }

    /**
     * Get the shadow color specified by the look and feel, or a reasonable
     * default if none was specified
     */
    static Color getShadowColor() {
        if (shadowColor == null) {
            deriveColorsAndMargin();
        }

        return shadowColor;
    }

    /**
     * Get the alternate background color, if any. If non-null, the table will
     * show every other row using the color specified here
     */
    static Color getAltBg() {
        if (altBg == null) {
            deriveColorsAndMargin();
        }

        return altBg;
    }

    /**
     * Determine if an alternate background color has been specified in the look
     * and feel or theme. If one is supplied, then the property sheet table will
     * not show a grid, but instead, every other line will have a different
     * background color. This method simply avoids repeated attempts to look up
     * the alternate background color if none was specified
     */
    static boolean noAltBg() {
        if (noAltBg == null) {
            noAltBg = (UIManager.getColor(KEY_ALTBG) == null) ? //NOI18N
                    Boolean.TRUE : Boolean.FALSE;
        }

        return noAltBg.booleanValue();
    }

    /**
     * Get the forground color for text editable property editors in edit mode.
     * This is required because the tree selection color is typically the same
     * color as the tree selection color. So if a string editor is given the
     * background color of a selected row in the table, it is impossible to tell
     * when some text in the field has been selected, because they will be the
     * same color.
     */
    static Color getTextFieldBackground() {
        if (tfBg == null) {
            tfBg = UIManager.getColor("TextField.background"); //NOI18N

            if (tfBg == null) {
                tfBg = UIManager.getColor("text"); //NOI18N
            }

            if (tfBg == null) {
                tfBg = Color.WHITE;
            }
        }

        return tfBg;
    }

    /**
     * Get the forground color for text editable property editors in edit mode.
     * This is required because the tree selection color is typically the same
     * color as the tree selection color.
     */
    static Color getTextFieldForeground() {
        if (tfFg == null) {
            tfFg = UIManager.getColor("TextField.foreground"); //NOI18N

            if (tfFg == null) {
                tfFg = UIManager.getColor("textText"); //NOI18N
            }

            if (tfFg == null) {
                tfFg = Color.BLACK;
            }
        }

        return tfFg;
    }

    /**
     * Initialize the various colors we will be using.
     */
    private static void deriveColorsAndMargin() {
        controlColor = UIManager.getColor("control"); //NOI18N

        if (controlColor == null) {
            controlColor = Color.LIGHT_GRAY;
        }

        int red;
        int green;
        int blue;

        boolean windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel".equals(
                UIManager.getLookAndFeel().getClass().getName());

        boolean nimbus = "Nimbus".equals(UIManager.getLookAndFeel().getID());

        boolean gtk = "GTK".equals(UIManager.getLookAndFeel().getID());

        setRendererColor = UIManager.getColor(KEY_SETBG); //NOI18N
        selectedSetRendererColor = UIManager.getColor(KEY_SELSETBG); //NOI18N

        if (nimbus || gtk) {
            setRendererColor = UIManager.getColor("Menu.background");//NOI18N
            selectedSetRendererColor = UIManager.getColor("Tree.selectionBackground"); //NOI18N
        }

        if (setRendererColor == null) {
            if (setRendererColor == null) {
                red = adjustColorComponent(controlColor.getRed(), -25, -25);
                green = adjustColorComponent(controlColor.getGreen(), -25, -25);
                blue = adjustColorComponent(controlColor.getBlue(), -25, -25);
                setRendererColor = new Color(red, green, blue);
            }
        }

        if (selectedSetRendererColor == null) {
            Color col = windows ? UIManager.getColor("Table.selectionBackground")
                    : UIManager.getColor("activeCaptionBorder"); //NOI18N

            if (col == null) {
                col = Color.BLUE;
            }

            red = adjustColorComponent(col.getRed(), -25, -25);
            green = adjustColorComponent(col.getGreen(), -25, -25);
            blue = adjustColorComponent(col.getBlue(), -25, -25);
            selectedSetRendererColor = new Color(red, green, blue);
        }

        shadowColor = UIManager.getColor("controlShadow"); //NOI18N

        if (shadowColor == null) {
            shadowColor = Color.GRAY;
        }

        setForegroundColor = UIManager.getColor(KEY_SETFG);

        if (nimbus || gtk) {
            setForegroundColor = new Color(UIManager.getColor("Menu.foreground").getRGB()); //NOI18N
        }
        if (setForegroundColor == null) {
            setForegroundColor = UIManager.getColor("Table.foreground"); //NOI18N

            if (setForegroundColor == null) {
                setForegroundColor = UIManager.getColor("textText");

                if (setForegroundColor == null) {
                    setForegroundColor = Color.BLACK;
                }
            }
        }

        selectedSetForegroundColor = UIManager.getColor(KEY_SELSETFG);

        if (selectedSetForegroundColor == null) {
            selectedSetForegroundColor = UIManager.getColor("Table.selectionForeground"); //NOI18N

            if (selectedSetForegroundColor == null) {
                selectedSetForegroundColor = Color.WHITE;
            }
        }

        altBg = UIManager.getColor(KEY_ALTBG); //NOI18N

        if (altBg == null) {
            altBg = UIManager.getColor("Tree.background"); //NOI18N

            if (altBg == null) {
                altBg = Color.WHITE;
            }

            noAltBg = Boolean.TRUE;
        } else {
            noAltBg = Boolean.FALSE;
        }

        Icon collapsedIcon = getCollapsedIcon();

        int iconSize = 9;
        if (collapsedIcon != null) {
            iconSize = collapsedIcon.getIconWidth();
            marginWidth = Math.max(14, iconSize - 2);
        } else {
            //on the off chance a L&F doesn't provide this
            marginWidth = 13;
        }

        Integer i = (Integer) UIManager.get(KEY_ICONMARGIN); //NOI18N

        if (i != null) {
            iconMargin = i.intValue();
        } else {
            if ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel".equals(
                    UIManager.getLookAndFeel().getClass().getName())) {
                iconMargin = 4;
            } else {
                iconMargin = 0;
            }
        }

        i = (Integer) UIManager.get(KEY_ROWHEIGHT); //NOI18N

        if (i != null) {
            spinnerHeight = i.intValue();
        } else {
            spinnerHeight = iconSize;
        }
    }

    /**
     * Get the icon displayed by an expanded set. Typically this is just the
     * same icon the look and feel supplies for trees
     */
    static Icon getExpandedIcon() {
        Icon expandedIcon = UIManager.getIcon(isGtk ? "Tree.gtk_expandedIcon" : "Tree.expandedIcon"); //NOI18N
        if (expandedIcon == null) {
            LOG.info("no Tree.expandedIcon found");
        }
        return expandedIcon;
    }

    /**
     * Get the icon displayed by a collapsed set. Typically this is just the
     * icon the look and feel supplies for trees
     */
    static Icon getCollapsedIcon() {
        Icon collapsedIcon = UIManager.getIcon(isGtk ? "Tree.gtk_collapsedIcon" : "Tree.collapsedIcon"); //NOI18N
        if (collapsedIcon == null) {
            LOG.info("no Tree.collapsedIcon found");
        }
        return collapsedIcon;
    }

    /**
     * Get the color for expandable sets when they are not selected
     */
    static Color getSetRendererColor() {
        if (setRendererColor == null) {
            deriveColorsAndMargin();
        }

        return setRendererColor;
    }

    /**
     * Get the background color for expandable sets when they are selected
     */
    static Color getSelectedSetRendererColor() {
        if (selectedSetRendererColor == null) {
            deriveColorsAndMargin();
        }

        return selectedSetRendererColor;
    }

    /**
     * Get the color for expandable sets when not selected
     */
    static Color getSetForegroundColor() {
        if (setForegroundColor == null) {
            deriveColorsAndMargin();
        }

        return setForegroundColor;
    }

    /**
     * Get the text color for expandable sets when they are selected
     */
    static Color getSelectedSetForegroundColor() {
        if (selectedSetForegroundColor == null) {
            deriveColorsAndMargin();
        }

        return selectedSetForegroundColor;
    }

    /**
     * Get the total width that the left side margin should have. This is
     * calculated based on the width of the expandable set icon plus some
     * spacing
     */
    static int getMarginWidth() {
        if (marginWidth == -1) {
            deriveColorsAndMargin();
        }

        return marginWidth;
    }

    /**
     * Get the height of the expansion icon
     */
    static int getSpinnerHeight() {
        if (spinnerHeight == -1) {
            deriveColorsAndMargin();
        }

        return spinnerHeight;
    }

    /**
     * Get the number of pixels that should separate the expandable set icon
     * from the left edge of the property sheet. For Metal, this can be zero;
     * for the smaller icon supplied by Windows look and feels, it should be a
     * larger number
     */
    static int getIconMargin() {
        if (iconMargin == -1) {
            deriveColorsAndMargin();
        }

        return iconMargin;
    }

    /**
     * Lazily creates the custom editor button icon
     */
    static Icon getCustomButtonIcon() {
        return new BpIcon();
    }

    /**
     * Adjust an rgb color component.
     *
     * @param base the color, an RGB value 0-255
     * @param adjBright the amount to subtract if base > 128
     * @param adjDark the amount to add if base <=128
     */
    private static int adjustColorComponent(int base, int adjBright, int adjDark) {
        if (base > 128) {
            base -= adjBright;
        } else {
            base += adjDark;
        }

        if (base < 0) {
            base = 0;
        }

        if (base > 255) {
            base = 255;
        }

        return base;
    }

    /**
     * Get the localized name for the default category of properties - in
     * English this is &quot;Properties&quot;. Used to provide the basic
     * category and tab name
     */
    static String basicPropsTabName() {
        if (bptn == null) {
            bptn = NbBundle.getMessage(PropUtils.class, "LBL_BasicTab"); //NOI18N
        }

        return bptn;
    }

    static Comparator getTabListComparator() {
        if (comp == null) {
            comp = new TabListComparator();
        }

        return comp;
    }

    static SplitPaneUI createSplitPaneUI() {
        return new CleanSplitPaneUI();
    }

    static boolean shouldShowDescription() {
        return preferences().getBoolean(PREF_KEY_SHOWDESCRIPTION, true);
    }

    static void saveShowDescription(boolean b) {
        preferences().putBoolean(PREF_KEY_SHOWDESCRIPTION, b);
    }

    static String[] getSavedClosedSetNames() {
        String s = preferences().get(PREF_KEY_CLOSEDSETNAMES, null);

        if (s != null) {
            StringTokenizer tok = new StringTokenizer(s, ","); //NOI18N
            String[] result = new String[tok.countTokens()];
            int i = 0;

            while (tok.hasMoreElements()) {
                result[i] = tok.nextToken();
                i++;
            }

            return result;
        } else {
            return new String[0];
        }
    }

    static void putSavedClosedSetNames(Set s) {
        if (s.size() > 0) {
            StringBuilder sb = new StringBuilder(s.size() * 20);
            Iterator i = s.iterator();

            while (i.hasNext()) {
                sb.append(i.next());

                if (i.hasNext()) {
                    sb.append(','); //NOI18N
                }
            }

            preferences().put(PREF_KEY_CLOSEDSETNAMES, sb.toString());
        } else {
            preferences().put(PREF_KEY_CLOSEDSETNAMES, "");
        }
    }

    static void putSortOrder(int i) {
        preferences().putInt(PREF_KEY_SORTORDER, i);
    }

    static int getSavedSortOrder() {
        return preferences().getInt(PREF_KEY_SORTORDER, PropertySheet.UNSORTED);
    }

    /**
     * Fetch the margin that should come between the edge of a property sheet
     * cell and its text. The default is 2, or it can be customized via the
     * UIManager integer key netbeans.ps.textMargin
     */
    static int getTextMargin() {
        if ("apple.laf.AquaLookAndFeel".equals(UIManager.getLookAndFeel().getClass().getName())) {
            return 0;
        }

        if (textMargin == -1) {
            Object o = UIManager.get("netbeans.ps.textMargin"); //NOI18N

            if (o instanceof Integer) {
                textMargin = ((Integer) o).intValue();
            } else {
                textMargin = 2;
            }
        }

        return textMargin;
    }

    /**
     * HTML-ize a tooltip, splitting long lines
     */
    static String createHtmlTooltip(String title, String s) {
        boolean wasHtml = false;

        if (s.matches("\\<(html|HTML)\\>.*\\<\\/(html|HTML)\\>")) { // NOI18N
            s = s.replaceAll("\\<\\/{0,1}(html|HTML)\\>", ""); // NOI18N
            wasHtml = true;
        }

        // break up massive tooltips
        String token = null;

        if (s.indexOf(" ") != -1) { //NOI18N
            token = " "; //NOI18N
        } else if (s.indexOf(",") != -1) { //NOI18N
            token = ","; //NOI18N
        } else if (s.indexOf(";") != -1) { //NOI18N
            token = ";"; //NOI18N
        } else if (s.indexOf("/") != -1) { //NOI18N
            token = "/"; //NOI18N
        } else if (s.indexOf("\\") != -1) { //NOI18N
            token = "\\"; //NOI18N
        } else {
            //give up
            return s;
        }

        StringTokenizer tk = new StringTokenizer(s, token, true);

        StringBuilder sb = new StringBuilder(s.length() + 20);
        sb.append("<html>"); //NOI18N
        sb.append("<b><u>"); //NOI18N
        sb.append(title);
        sb.append("</u></b><br>"); //NOI18N

        int charCount = 0;
        int lineCount = 0;

        while (tk.hasMoreTokens()) {
            String a = tk.nextToken();

            if (!wasHtml) {
                // HTML-ize only non-html values. HTML values should already
                // contain correct HTML strings.
                a = a.replace("&", "&amp;"); //NOI18N
                a = a.replace("<", "&lt;"); //NOI18N
                a = a.replace(">", "&gt;"); //NOI18N
            }

            charCount += a.length();
            sb.append(a);

            if (tk.hasMoreTokens()) {
                charCount++;
            }

            if (charCount > 80) {
                sb.append("<br>"); //NOI18N
                charCount = 0;
                lineCount++;

                if (lineCount > 10) {
                    //Don't let things like VCS variables create
                    //a tooltip bigger than the screen. 99% of the
                    //time this is not a problem.
                    sb.append(NbBundle.getMessage(PropUtils.class, "MSG_ELLIPSIS")); //NOI18N

                    return sb.toString();
                }
            }
        }

        sb.append("</html>"); //NOI18N

        return sb.toString();
    }

    /**
     * Returns a fixed foreground color for the custom button icon on win xp. On
     * win xp, the button background is a white bitmap, so if we use the white
     * foreground color for the cell, the ... in the icon seems to disappear
     * when a row with a custom editor button is selected.
     *
     * @see org.netbeans.core.NbTheme.installCustomDefaultsWinXP
     */
    private static Color getIconForeground() {
        return UIManager.getColor("PropSheet.customButtonForeground"); //NOI18N
    }

    public static boolean isXPTheme() {
        Boolean isXP = (Boolean) Toolkit.getDefaultToolkit().getDesktopProperty("win.xpstyle.themeActive");

        return (isXP == null) ? false : isXP.booleanValue();
    }

    /**
     * Just a helper method which delegates to shallBeRDVEnabled(Node.Property).
     */
    static boolean shallBeRDVEnabled(FeatureDescriptor fd) {
        if ((fd != null) && fd instanceof Node.Property) {
            return shallBeRDVEnabled((Node.Property) fd);
        }

        return false;
    }

    /**
     * Returns if the "Restore Default Value" action should be enabled for the
     * given
     * <code>Node.Property</code>. If the properties is not null, is instanceof
     * Node.Property, supportsDefaultValue() and isDefaultValue() returns true
     * the property shall be enabled. Otherwise not. <p> Note that due to the
     * backward compatibilty we return true also for properties which only
     * override Node.Property.supportsDefaultValue() to return true and don't
     * override Node.Property.isDefaultValue(). The isDefaultValue() return
     * false by default.<br> For more information and detailed reason why we do
     * so see <a href="http://www.netbeans.org/issues/show_bug.cgi?id=51907">
     * Issue 51907</a>.
     */
    static final boolean shallBeRDVEnabled(Node.Property property) {
        if ((property == null) || !property.supportsDefaultValue()) {
            return false;
        }

        try {
            if (property.getClass().getMethod("isDefaultValue").getDeclaringClass() == Node.Property.class) {
                // if the given property didn't override isDefaultValue method
                // but override supportsDefaultValue the RDV should be always
                // enabled
                return true;
            } else {
                return !property.isDefaultValue();
            }
        } catch (NoSuchMethodException e) {
            // cannot happen since isDefaultValue is defined in Node.Property
            assert false : "No isDefaultValue in " + property.getClass() + ": " + e;

            return true; // satisfy compiler for case when assertion is disabled
        }
    }

    static void addExternallyEdited(Property p) {
        externallyEdited.add(p);
    }

    static void removeExternallyEdited(Property p) {
        externallyEdited.remove(p);
    }

    static boolean isExternallyEdited(Property p) {
        return externallyEdited.contains(p);
    }

    /**
     * Property editor for properties which belong to more than one property,
     * but have different values.
     */
    static class DifferentValuesEditor implements PropertyEditor {

        protected PropertyEditor ed;
        private boolean notSet = true;

        public DifferentValuesEditor(PropertyEditor ed) {
            this.ed = ed;
            addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    notSet = false;
                }
            });
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            ed.addPropertyChangeListener(listener);
        }

        @Override
        public String getAsText() {
            String result;

            if (notSet) {
                result = NbBundle.getMessage(PropUtils.class, "CTL_Different_Values"); //NOI18N
            } else {
                result = ed.getAsText();
            }

            return result;
        }

        @Override
        public java.awt.Component getCustomEditor() {
            return ed.getCustomEditor();
        }

        @Override
        public String getJavaInitializationString() {
            return ed.getJavaInitializationString();
        }

        @Override
        public String[] getTags() {
            return ed.getTags();
        }

        @Override
        public Object getValue() {
            Object result;

            if (notSet) {
                result = null;
            } else {
                result = ed.getValue();
            }

            return result;
        }

        @Override
        public boolean isPaintable() {
            return notSet ? false : ed.isPaintable();
        }

        @Override
        public void paintValue(java.awt.Graphics gfx, java.awt.Rectangle box) {
            //issue 33341 - don't allow editors to paint if value not set
            if (isPaintable()) {
                ed.paintValue(gfx, box);
            }
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {
            ed.removePropertyChangeListener(listener);
        }

        @Override
        public void setAsText(String text) throws java.lang.IllegalArgumentException {
            ed.setAsText(text);
            notSet = false;
        }

        @Override
        public void setValue(Object value) {
            ed.setValue(value);
            notSet = false;
        }

        @Override
        public boolean supportsCustomEditor() {
            return ed.supportsCustomEditor();
        }
    }

    /**
     * Extended Property editor for properties which belong to more than one
     * property, but have different values.
     */
    static final class ExDifferentValuesEditor extends DifferentValuesEditor implements ExPropertyEditor {

        public ExDifferentValuesEditor(PropertyEditor ed) {
            super(ed);
        }

        @Override
        public void attachEnv(PropertyEnv env) {
            ((ExPropertyEditor) ed).attachEnv(env);
        }
    }

    /**
     * Dummy property editor for properties which have no real editor. The
     * property sheet does not handle null property editors; this editor stands
     * in, and returns &quot;No property editor&quot; from getAsText()
     */
    static final class NoPropertyEditorEditor implements PropertyEditor {

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            //no-op
        }

        @Override
        public String getAsText() {
            return NbBundle.getMessage(PropertySheet.class, "CTL_NoPropertyEditor"); //NOI18N
        }

        @Override
        public java.awt.Component getCustomEditor() {
            return null;
        }

        @Override
        public String getJavaInitializationString() {
            return "";
        }

        @Override
        public String[] getTags() {
            return null;
        }

        @Override
        public Object getValue() {
            return getAsText();
        }

        @Override
        public boolean isPaintable() {
            return false;
        }

        @Override
        public void paintValue(java.awt.Graphics gfx, java.awt.Rectangle box) {
            //no-op
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {
            //no-op
        }

        @Override
        public void setAsText(String text) throws java.lang.IllegalArgumentException {
            //no-op
        }

        @Override
        public void setValue(Object value) {
            //no-op
        }

        @Override
        public boolean supportsCustomEditor() {
            return false;
        }
    }

    /**
     * Comparator for sorting the list of tabs such that basic properties is
     * always the first tab.
     */
    private static class TabListComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;

            if (s1 == s2) {
                return 0;
            }

            String bn = basicPropsTabName();

            if (bn.equals(s1)) {
                return -1;
            }

            if (bn.equals(s2)) {
                return 1;
            }

            return s1.compareTo(s2);
        }
    }

    private static class CleanSplitPaneUI extends BasicSplitPaneUI {

        @Override
        protected void installDefaults() {
            super.installDefaults();
            divider.setBorder(new SplitBorder());
        }

        @Override
        public BasicSplitPaneDivider createDefaultDivider() {
            return new CleanSplitPaneDivider(this);
        }
    }

    private static class CleanSplitPaneDivider extends BasicSplitPaneDivider implements Accessible {

        private AccessibleContext accessibleContext;

        public CleanSplitPaneDivider(BasicSplitPaneUI ui) {
            super(ui);
        }

        @Override
        public AccessibleContext getAccessibleContext() {
            if (null == accessibleContext) {
                accessibleContext = new AccessibleAWTComponent() {

                    @Override
                    public AccessibleRole getAccessibleRole() {
                        return AccessibleRole.SPLIT_PANE;
                    }
                };

                accessibleContext.setAccessibleName("Splitter");
                accessibleContext.setAccessibleDescription("Visual divider between the properties table and the description area.");
            }
            return accessibleContext;
        }
    }

    /**
     * A split border subclass that does not draw the metal drag texture
     */
    private static class SplitBorder implements Border {

        @Override
        public Insets getBorderInsets(Component c) {
            if (UIManager.getLookAndFeel() instanceof MetalLookAndFeel) {
                return new Insets(2, 0, 1, 0);
            } else {
                return new Insets(1, 0, 1, 0);
            }
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            if (UIManager.getLookAndFeel() instanceof MetalLookAndFeel) {
                g.setColor(UIManager.getColor("controlShadow"));
                g.drawLine(x, y, x + width, y);
                g.setColor(UIManager.getColor("controlHighlight"));
                g.drawLine(x, y + 1, x + width, y + 1);
                g.drawLine(x, (y + height) - 1, x + width, (y + height) - 1);
                g.setColor(UIManager.getColor("controlShadow"));
                g.drawLine(x, (y + height) - 2, x + width, (y + height) - 2);
            } else {
                //don't do flush 3d for non-metal L&F
                g.setColor(UIManager.getColor("controlHighlight"));
                g.drawLine(x, y, x + width, y);
                g.setColor(UIManager.getColor("controlShadow"));
                g.drawLine(x, (y + height) - 1, x + width, (y + height) - 1);
            }
        }
    }

    /**
     * The ... icon for the custom editor button. Will draw slightly larger if
     * the font size is greater
     */
    static class BpIcon implements Icon {

        boolean larger;

        public BpIcon() {
            Font f = UIManager.getFont("Table.font"); //NOI18N
            larger = (f != null) ? (f.getSize() > 13) : false;
        }

        @Override
        public int getIconHeight() {
            return 12;
        }

        @Override
        public int getIconWidth() {
            return larger ? 16 : 12;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            int w = c.getWidth();
            int h = c.getHeight();
            int ybase = h - 5;
            int pos2 = (w / 2);
            int pos1 = pos2 - 4;
            int pos3 = pos2 + 4;
            g.setColor((getIconForeground() == null) ? c.getForeground() : getIconForeground());
            drawDot(g, pos1 + 1, ybase, larger);
            drawDot(g, pos2, ybase, larger);
            drawDot(g, pos3 - 1, ybase, larger);
        }

        private void drawDot(Graphics g, int x, int y, boolean larger) {
            if (!larger) {
                g.drawLine(x, y, x, y);
            } else {
                g.drawLine(x - 1, y, x + 1, y);
                g.drawLine(x, y - 1, x, y + 1);
            }
        }
    }
}
