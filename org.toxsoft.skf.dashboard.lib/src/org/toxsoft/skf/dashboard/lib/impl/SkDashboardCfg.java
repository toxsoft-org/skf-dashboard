package org.toxsoft.skf.dashboard.lib.impl;

import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.skf.dashboard.lib.*;
import org.toxsoft.uskat.core.api.objserv.*;
import org.toxsoft.uskat.core.impl.*;

/**
 * {@link ISkDashboardCfg} implementation.
 *
 * @author hazard157
 */
class SkDashboardCfg
    extends SkObject
    implements ISkDashboardCfg {

  static final ISkObjectCreator<SkDashboardCfg> CREATOR = SkDashboardCfg::new;

  SkDashboardCfg( Skid aSkid ) {
    super( aSkid );
  }

  // ------------------------------------------------------------------------------------
  // ISkDashboardCfg
  //

  @Override
  public String cfgData() {
    ISkDashboardService ms = coreApi().getService( ISkDashboardService.SERVICE_ID );
    return ms.getDashboardData( id() );
  }

  @Override
  public void setCfgData( String aCfgData ) {
    ISkDashboardService ms = coreApi().getService( ISkDashboardService.SERVICE_ID );
    ms.setDashboardData( id(), aCfgData );
  }

}
