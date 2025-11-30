package org.toxsoft.skf.dashboard.lib;

import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.uskat.core.*;

/**
 * {@link ISkDashboardService} listener.
 *
 * @author hazard157
 */
public interface ISkDashboardServiceListener {

  /**
   * Called when any change in dashboardCfgs occurs.
   *
   * @param aCoreApi {@link ISkCoreApi} - the event source
   * @param aOp {@link ECrudOp} - the kind of change
   * @param aDashboardId String - affected dashboard ID or <code>null</code> for batch changes {@link ECrudOp#LIST}
   */
  void onDashboardCfgChanged( ISkCoreApi aCoreApi, ECrudOp aOp, String aDashboardId );

}
