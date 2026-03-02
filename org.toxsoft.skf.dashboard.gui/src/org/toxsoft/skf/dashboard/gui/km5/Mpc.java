package org.toxsoft.skf.dashboard.gui.km5;

import static org.toxsoft.core.tsgui.bricks.actions.ITsStdActionDefs.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.dashboard.gui.ISkfDashboardGuiConstants.*;
import static org.toxsoft.skf.dashboard.gui.l10n.ISkfDahboardGuiSharedResources.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;

import org.toxsoft.core.tsgui.bricks.actions.*;
import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.dialogs.datarec.*;
import org.toxsoft.core.tsgui.graphics.icons.*;
import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.gui.*;
import org.toxsoft.core.tsgui.m5.gui.mpc.*;
import org.toxsoft.core.tsgui.m5.gui.mpc.impl.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.m5.model.impl.*;
import org.toxsoft.core.tsgui.panels.toolbar.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.dashboard.gui.e4.services.*;
import org.toxsoft.skf.dashboard.lib.*;

/**
 * {@link MultiPaneComponentModown} implementation for {@link SkDashboardCfgM5Model}.
 *
 * @author hazard157
 */
class Mpc
    extends MultiPaneComponentModown<ISkDashboardCfg> {

  public static final String ACTID_EDIT_DASHBOARD = "org.toxsoft.mnemos.edit_mnemo"; //$NON-NLS-1$

  public static final ITsActionDef ACDEF_EDIT_DASHBOARD = TsActionDef.ofPush1( ACTID_EDIT_DASHBOARD, //
      TSID_NAME, STR_ACT_EDIT_DASHBOARD, //
      TSID_DEFAULT_VALUE, STR_ACT_EDIT_DASHBOARD_D, //
      TSID_ICON_ID, ICONID_DASHBOARD_EDIT //
  );

  /**
   * ID of action {@link #ACDEF_ADD_COPY_DASHBOARD}.
   */

  /**
   * Action: add an dashboard created from current dashboard.
   */
  static final ITsActionDef ACDEF_ADD_COPY_DASHBOARD = TsActionDef.ofTemplate( ACDEF_ADD_COPY, //
      TSID_NAME, STR_ADD_COPY_DASHBOARD, //
      TSID_DESCRIPTION, STR_ADD_COPY_DASHBOARD_D //
  );

  public Mpc( ITsGuiContext aContext, IM5Model<ISkDashboardCfg> aModel,
      IM5ItemsProvider<ISkDashboardCfg> aItemsProvider, IM5LifecycleManager<ISkDashboardCfg> aLifecycleManager ) {
    super( aContext, aModel, aItemsProvider, aLifecycleManager );
    IMultiPaneComponentConstants.OPDEF_DBLCLICK_ACTION_ID.setValue( tsContext().params(), avStr( ACTID_EDIT ) );
  }

  @Override
  protected ITsToolbar doCreateToolbar( ITsGuiContext aContext, String aName, EIconSize aIconSize,
      IListEdit<ITsActionDef> aActs ) {
    int index = aActs.indexOf( ACDEF_ADD );
    aActs.insert( 1 + index, ACDEF_ADD_COPY_DASHBOARD );
    aActs.add( ACDEF_SEPARATOR );
    aActs.add( ACDEF_EDIT_DASHBOARD );
    return super.doCreateToolbar( aContext, aName, aIconSize, aActs );
  }

  @Override
  protected void doProcessAction( String aActionId ) {
    ISkDashboardCfg sel = selectedItem();
    switch( aActionId ) {
      case ACTID_EDIT_DASHBOARD: {
        if( sel != null ) {
          ISkDashboardEditService vss = tsContext().find( ISkDashboardEditService.class );
          if( vss != null ) {
            vss.openDashboardForEditing( sel );
          }
          else {
            // TODO invoke dialog that code is running without dashboard editing capabilities
          }
        }
        break;
      }
      case ACTID_ADD_COPY: {
        ISkDashboardCfg selected = tree().selectedItem();
        ITsDialogInfo cdi = doCreateDialogInfoToAddItem();
        IM5BunchEdit<ISkDashboardCfg> initVals = new M5BunchEdit<>( model() );
        initVals.fillFrom( selected, false );
        String itemId = initVals.getAsAv( AID_STRID ).asString();
        itemId = itemId + "_copy"; //$NON-NLS-1$
        initVals.set( AID_STRID, avStr( itemId ) );

        ISkDashboardCfg item = M5GuiUtils.askCreate( tsContext(), model(), initVals, cdi, lifecycleManager() );
        if( item != null ) {
          item.setCfgData( selected.cfgData() );
          fillViewer( item );
        }
        break;
      }

      default:
        throw new TsNotAllEnumsUsedRtException( aActionId );
    }
  }

  @Override
  protected void doUpdateActionsState( boolean aIsAlive, boolean aIsSel, ISkDashboardCfg aSel ) {
    toolbar().setActionEnabled( ACTID_ADD_COPY, aIsSel );
    toolbar().setActionEnabled( ACTID_EDIT_DASHBOARD, aIsSel );
  }

}
