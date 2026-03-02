package org.toxsoft.skf.dashboard.gui.km5;

import static org.toxsoft.core.tsgui.m5.IM5Constants.*;
import static org.toxsoft.core.tsgui.m5.gui.mpc.IMultiPaneComponentConstants.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;

import org.toxsoft.core.tsgui.bricks.ctx.*;
import org.toxsoft.core.tsgui.m5.gui.mpc.impl.*;
import org.toxsoft.core.tsgui.m5.gui.panels.*;
import org.toxsoft.core.tsgui.m5.gui.panels.impl.*;
import org.toxsoft.core.tsgui.m5.model.*;
import org.toxsoft.core.tsgui.m5.model.impl.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.skf.dashboard.lib.*;
import org.toxsoft.uskat.core.connection.*;
import org.toxsoft.uskat.core.gui.km5.*;

/**
 * KM5-model of {@link ISkDashboardCfg}.
 *
 * @author hazard157
 */
public class SkDashboardCfgM5Model
    extends KM5ModelBasic<ISkDashboardCfg> {

  /**
   * Constructor.
   *
   * @param aConn {@link ISkConnection} - the connection
   * @throws TsNullArgumentRtException any argument = <code>null</code>
   */
  public SkDashboardCfgM5Model( ISkConnection aConn ) {
    super( ISkDashboardCfg.CLASS_ID, ISkDashboardCfg.class, aConn );
    DESCRIPTION.setFlags( M5FF_COLUMN );
    addFieldDefs( STRID, NAME, DESCRIPTION );
    setPanelCreator( new M5DefaultPanelCreator<>() {

      @Override
      protected IM5CollectionPanel<ISkDashboardCfg> doCreateCollEditPanel( ITsGuiContext aContext,
          IM5ItemsProvider<ISkDashboardCfg> aItemsProvider, IM5LifecycleManager<ISkDashboardCfg> aLifecycleManager ) {
        OPDEF_IS_ACTIONS_CRUD.setValue( aContext.params(), AV_TRUE );
        // OPDEF_IS_FILTER_PANE.setValue( aContext.params(), AV_TRUE );
        MultiPaneComponentModown<ISkDashboardCfg> mpc = new Mpc( aContext, model(), aItemsProvider, aLifecycleManager );
        return new M5CollectionPanelMpcModownWrapper<>( mpc, false );
      }
    } );
  }

  @Override
  protected IM5LifecycleManager<ISkDashboardCfg> doCreateLifecycleManager( Object aMaster ) {
    return new DashboardM5LifecycleManager( this, ISkDashboardService.class.cast( aMaster ) );
  }

}
