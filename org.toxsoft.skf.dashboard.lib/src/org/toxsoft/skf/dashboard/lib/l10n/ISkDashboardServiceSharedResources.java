package org.toxsoft.skf.dashboard.lib.l10n;

import org.toxsoft.skf.dashboard.lib.impl.*;

/**
 * Localizable resources.
 *
 * @author hazard157
 */
@SuppressWarnings( "javadoc" )
public interface ISkDashboardServiceSharedResources {

  /**
   * {@link ISkDashboardsServiceHardConstants}
   */
  String STR_CLB_DASHBOARD_CFG_DATA    = Messages.getString( "STR_CLB_DASHBOARD_CFG_DATA" );    //$NON-NLS-1$
  String STR_CLB_DASHBOARD_CFG_DATA_D  = Messages.getString( "STR_CLB_DASHBOARD_CFG_DATA_D" );  //$NON-NLS-1$
  String STR_ABKIND_DASHBOARD          = Messages.getString( "STR_ABKIND_DASHBOARD" );          //$NON-NLS-1$
  String STR_ABKIND_DASHBOARD_D        = Messages.getString( "STR_ABKIND_DASHBOARD_D" );        //$NON-NLS-1$
  String STR_ABILITY_DASHBOARDS_EDIT   = Messages.getString( "STR_ABILITY_DASHBOARDS_EDIT" );   //$NON-NLS-1$
  String STR_ABILITY_DASHBOARDS_EDIT_D = Messages.getString( "STR_ABILITY_DASHBOARDS_EDIT_D" ); //$NON-NLS-1$

  /**
   * {@link SkDashboardService}
   */
  String FMT_ERR_DASHBOARD_EXISTS      = Messages.getString( "FMT_ERR_DASHBOARD_EXISTS" );      //$NON-NLS-1$
  String MSG_WARN_NAME_NOT_SET         = Messages.getString( "MSG_WARN_NAME_NOT_SET" );         //$NON-NLS-1$
  String FMT_ERR_DASHBOARD_NOT_EXISTS  = Messages.getString( "FMT_ERR_DASHBOARD_NOT_EXISTS" );  //$NON-NLS-1$
  String FMT_WARN_DASHBOARD_NOT_EXISTS = Messages.getString( "FMT_WARN_DASHBOARD_NOT_EXISTS" ); //$NON-NLS-1$

  // ------------------------------------------------------------------------------------
  // USkat entities are defined only in English, l10n done via USkat localization service

  String STR_DASHBOARD_CLASS   = Messages.getString( "STR_DASHBOARDSCHEME_CLASS" );   //$NON-NLS-1$
  String STR_DASHBOARD_CLASS_D = Messages.getString( "STR_DASHBOARDSCHEME_CLASS_D" ); //$NON-NLS-1$

}
