package org.toxsoft.skf.dashboard.gui;

import org.eclipse.e4.core.contexts.*;
import org.toxsoft.core.tsgui.bricks.quant.*;

/**
 * The library quant.
 *
 * @author hazard157
 */
public class QuantSkfDashboardGui
    extends AbstractQuant {

  /**
   * Constructor.
   */
  public QuantSkfDashboardGui() {
    super( QuantSkfDashboardGui.class.getSimpleName() );
  }

  // ------------------------------------------------------------------------------------
  // AbstractQuant
  //

  @Override
  protected void doInitApp( IEclipseContext aAppContext ) {
    // nop
  }

  @Override
  protected void doInitWin( IEclipseContext aWinContext ) {
    ISkfDashboardGuiConstants.init( aWinContext );
  }

}
