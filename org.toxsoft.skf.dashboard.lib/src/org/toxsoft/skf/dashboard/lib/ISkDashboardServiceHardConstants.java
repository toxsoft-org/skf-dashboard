package org.toxsoft.skf.dashboard.lib;

import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.dashboard.lib.ISkDashboardService.*;
import static org.toxsoft.skf.dashboard.lib.l10n.ISkDashboardServiceSharedResources.*;

import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.api.users.ability.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * The service constants.
 *
 * @author hazard157
 */
public interface ISkDashboardServiceHardConstants {

  // ------------------------------------------------------------------------------------
  // Sysdescr

  /**
   * Dashboard configuration class ID.
   */
  String CLSID_DASHBOARD_CFG = ISkHardConstants.SK_ID + ".DashboardCfg"; //$NON-NLS-1$

  /**
   * ID of CLOB {@link #CLBINF_DASHBOARD_CFG_DATA}.
   */
  String CLBID_DASHBOARD_CFG_DATA = "cfgData"; //$NON-NLS-1$

  /**
   * CLOB {@link ISkDashboardCfg#cfgData()}.
   */
  IDtoClobInfo CLBINF_DASHBOARD_CFG_DATA = DtoClobInfo.create2( CLBID_DASHBOARD_CFG_DATA, //
      TSID_NAME, STR_CLB_DASHBOARD_CFG_DATA, //
      TSID_DESCRIPTION, STR_CLB_DASHBOARD_CFG_DATA_D //
  );

  // ------------------------------------------------------------------------------------
  // Abilities declared by the service

  /**
   * ID of {@link #ABKIND_DASHBOARD}.
   */
  String ABKINDID_DASHBOARD = SERVICE_ID + ".abkind.dashboards"; //$NON-NLS-1$

  /**
   * ID of {@link #ABILITY_DASHBOARD_EDIT_PARAMS}.
   */
  String ABILITYID_DASHBOARD_EDIT = SERVICE_ID + ".ability.dashboards.edit"; // //$NON-NLS-1$

  /**
   * Ability kind: group of dashboard access abilities.
   */
  IDtoSkAbilityKind ABKIND_DASHBOARD = DtoSkAbilityKind.create( ABKINDID_DASHBOARD, //
      STR_ABKIND_DASHBOARD, STR_ABKIND_DASHBOARD_D );

  /**
   * Ability: edit dashboard configurations.
   */
  IDtoSkAbility ABILITY_DASHBOARD_EDIT_PARAMS = DtoSkAbility.create( ABILITYID_DASHBOARD_EDIT, ABKINDID_DASHBOARD,
      STR_ABILITY_DASHBOARDS_EDIT, STR_ABILITY_DASHBOARDS_EDIT_D );

}
