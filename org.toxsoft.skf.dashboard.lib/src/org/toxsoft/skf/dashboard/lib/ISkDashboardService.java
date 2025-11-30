package org.toxsoft.skf.dashboard.lib;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.bricks.events.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.api.*;
import org.toxsoft.uskat.core.api.objserv.*;

/**
 * USkat service: manages dashboard configurations stored in server.
 *
 * @author hazard157
 */
public interface ISkDashboardService
    extends ISkService {

  /**
   * Service identifier.
   */
  String SERVICE_ID = ISkHardConstants.SK_SYSEXT_SERVICE_ID_PREFIX + ".Dashboards"; //$NON-NLS-1$

  // ------------------------------------------------------------------------------------
  // API
  //

  /**
   * Finds the dashboard by ID.
   *
   * @param aDashboardId String - the dashboard ID
   * @return {@link ISkDashboardCfg} - found dashboard or <code>null</code>
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  ISkDashboardCfg findDashboard( String aDashboardId );

  /**
   * Returns the dashboard by ID.
   *
   * @param aDashboardId String - the dashboard ID
   * @return {@link ISkDashboardCfg} - found dashboard
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsItemNotFoundRtException no dashboard with the specified ID
   */
  ISkDashboardCfg getDashboard( String aDashboardId );

  /**
   * Returns the IDs of all existing dashboards.
   *
   * @return {@link IStringList} - all dashboard IDs
   */
  IStringList listDashboardIds();

  /**
   * Returns all existing dashboards.
   *
   * @return {@link IStridablesList}&lt;{@link ISkDashboardCfg}&gt; - all dashboards
   */
  IStridablesList<ISkDashboardCfg> listDashboardCfgs();

  /**
   * Creates new dashboard with an empty config data.
   *
   * @param aDashboardId String - the dashboard ID
   * @param aAttrs {@link IOptionSet} - values of {@link ISkObject#attrs()} of dashboard
   * @return {@link ISkDashboardCfg} - new dashboard
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException validation failed
   */
  ISkDashboardCfg createDashboard( String aDashboardId, IOptionSet aAttrs );

  /**
   * Updates attributes of an existing dashboard while content (config data) remains intact.
   *
   * @param aDashboardId String - the dashboard ID
   * @param aAttrs {@link IOptionSet} - new values of {@link ISkObject#attrs()} of dashboard
   * @return {@link ISkDashboardCfg} - new or updated dashboard
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException validation failed
   */
  ISkDashboardCfg editDashboard( String aDashboardId, IOptionSet aAttrs );

  /**
   * Sets the configuration data of the existing dashboard.
   *
   * @param aDashboardId String - the dashboard ID
   * @param aData String - configuration data is the same as {@link ISkDashboardCfg#cfgData()}
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException validation failed
   */
  void setDashboardData( String aDashboardId, String aData );

  /**
   * Returns the dashboard configuration data.
   *
   * @param aDashboardId String - the dashboard ID
   * @return String - configuration data is the same as {@link ISkDashboardCfg#cfgData()}
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsItemNotFoundRtException no dashboard with the specified ID
   */
  String getDashboardData( String aDashboardId );

  /**
   * Removes dashboard.
   * <p>
   * Does nothing is dashboard does not exists.
   *
   * @param aDashboardId String - the dashboard ID
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   * @throws TsValidationFailedRtException validation failed
   */
  void removeDashboard( String aDashboardId );

  // ------------------------------------------------------------------------------------
  // Service support

  /**
   * Returns the service validator.
   *
   * @return {@link ITsValidationSupport}&lt;{@link ISkDashboardServiceValidator}&gt; - the service validator
   */
  ITsValidationSupport<ISkDashboardServiceValidator> svs();

  /**
   * Returns the service eventer.
   *
   * @return {@link ITsEventer}&lt;{@link ISkDashboardServiceListener}&gt; - the service eventer
   */
  ITsEventer<ISkDashboardServiceListener> eventer();

}
