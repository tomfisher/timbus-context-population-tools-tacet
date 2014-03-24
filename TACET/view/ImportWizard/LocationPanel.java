/* 
 * Copyright 2013-2014 TECO - Karlsruhe Institute of Technology.
 * 
 * This file is part of TACET.
 * 
 * TACET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TACET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TACET.  If not, see <http://www.gnu.org/licenses/>.
 */

package squirrel.view.ImportWizard;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.border.TitledBorder;

import jwf.WizardPanel;

public class LocationPanel  extends WizardPanel {

    /** A default constructor. */
    public LocationPanel() {
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("test"));
    }

    /** Called when the panel is set. */
    @Override
    public void display() {
    }

    /** Is there be a next panel?
     * @return true if there is a panel to move to next
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /** Called to validate the panel before moving to next panel.
     * @param list a List of error messages to be displayed.
     * @return true if the panel is valid,
     */
    @Override
    public boolean validateNext(List list) {
        boolean valid = true;
        return valid;
    }

    /** Get the next panel to go to. */
    @Override
    public WizardPanel next() {
        return null;
    }

    /** Can this panel finish the wizard?
     * @return true if this panel can finish the wizard.
     */
    @Override
    public boolean canFinish() {
        return false;
    }

    /** Called to validate the panel before finishing the wizard. Should
     * return false if canFinish returns false.
     * @param list a List of error messages to be displayed.
     * @return true if it is valid for this wizard to finish.
     */
    @Override
    public boolean validateFinish(List list) {
        return false;
    }

    /** Handle finishing the wizard. */
    @Override
    public void finish() {
    }


}
