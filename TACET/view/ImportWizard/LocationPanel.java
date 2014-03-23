/*
 * Copyright 2013 TecO - Karlsruhe Institute of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
