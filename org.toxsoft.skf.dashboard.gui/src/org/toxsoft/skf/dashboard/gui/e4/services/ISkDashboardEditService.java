package org.toxsoft.skf.dashboard.gui.e4.services;

import org.toxsoft.skf.dashboard.gui.km5.*;
import org.toxsoft.skf.dashboard.lib.*;

/**
 * Dashboards editing service.
 * <p>
 * Service is used by {@link SkDashboardCfgM5Model} GUI panels.
 * <p>
 * Service implementation must be created and put in context by the editor.
 *
 * @author hazard157
 */
public interface ISkDashboardEditService {

  /**
   * Opens the specified mnemoscheme for editing.
   *
   * @param aDashboardCfg {@link ISkDashboardCfg} - the mnemo to edit
   */
  void openDashboardForEditing( ISkDashboardCfg aDashboardCfg );

}
