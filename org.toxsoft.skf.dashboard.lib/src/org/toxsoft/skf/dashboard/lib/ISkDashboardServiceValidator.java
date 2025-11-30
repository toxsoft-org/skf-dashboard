package org.toxsoft.skf.dashboard.lib;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.utils.errors.*;

/**
 * {@link ISkDashboardService} validator.
 *
 * @author hazard157
 */
public interface ISkDashboardServiceValidator {

  /**
   * Checks if dashboardCfg can be created.
   *
   * @param aDashboardId String - the dashboard ID
   * @param aAttrs {@link IOptionSet} - attribute values
   * @return {@link ValidationResult} - the check result
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  ValidationResult canCreateDashboardCfg( String aDashboardId, IOptionSet aAttrs );

  /**
   * Checks if dashboardCfg can be edited.
   *
   * @param aDashboardId String - the dashboard ID
   * @param aAttrs {@link IOptionSet} - attribute values
   * @return {@link ValidationResult} - the check result
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  ValidationResult canEditDashboardCfg( String aDashboardId, IOptionSet aAttrs );

  /**
   * Checks if the configuration data of the existing dashboard can be changed.
   *
   * @param aDashboardId String - the dashboard ID
   * @param aData String - configuration data is the same as {@link ISkDashboardCfg#cfgData()}
   * @param aDashboardCfg {@link ISkDashboardCfg} - current dashboardCfg data
   * @return {@link ValidationResult} - the check result
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsItemNotFoundRtException no dashboard with the specified ID
   */
  ValidationResult canSetDashboardData( String aDashboardId, String aData, ISkDashboardCfg aDashboardCfg );

  /**
   * Checks if dashboard section may removed.
   *
   * @param aDashboardId String - ID of section to remove
   * @return {@link ValidationResult} - the check result
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  ValidationResult canRemoveDashboardCfg( String aDashboardId );

}
