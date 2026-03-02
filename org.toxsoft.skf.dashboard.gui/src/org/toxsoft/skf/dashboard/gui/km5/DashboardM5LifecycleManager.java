package org.toxsoft.skf.dashboard.gui.km5;

import static org.toxsoft.uskat.core.ISkHardConstants.*;

import org.toxsoft.core.tsgui.m5.*;
import org.toxsoft.core.tsgui.m5.model.impl.*;
import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.impl.*;
import org.toxsoft.core.tslib.utils.*;
import org.toxsoft.skf.dashboard.lib.*;

/**
 * LM for {@link SkDashboardCfgM5Model}.
 *
 * @author vs
 */
class DashboardM5LifecycleManager
    extends M5LifecycleManager<ISkDashboardCfg, ISkDashboardService> {

  public DashboardM5LifecycleManager( IM5Model<ISkDashboardCfg> aModel, ISkDashboardService aMaster ) {
    super( aModel, true, true, true, true, aMaster );
  }

  private static Pair<String, IOptionSet> make( IM5Bunch<ISkDashboardCfg> aValues ) {
    String dashboardId = aValues.getAsAv( AID_STRID ).asString();
    IOptionSetEdit p = new OptionSet();
    p.setValue( AID_NAME, aValues.getAsAv( AID_NAME ) );
    p.setValue( AID_DESCRIPTION, aValues.getAsAv( AID_DESCRIPTION ) );
    return new Pair<>( dashboardId, p );
  }

  // ------------------------------------------------------------------------------------
  // M5LifecycleManager
  //

  @Override
  protected ValidationResult doBeforeCreate( IM5Bunch<ISkDashboardCfg> aValues ) {
    Pair<String, IOptionSet> p = make( aValues );
    return master().svs().validator().canCreateDashboardCfg( p.left(), p.right() );
  }

  @Override
  protected ISkDashboardCfg doCreate( IM5Bunch<ISkDashboardCfg> aValues ) {
    Pair<String, IOptionSet> p = make( aValues );
    return master().createDashboard( p.left(), p.right() );
  }

  @Override
  protected ValidationResult doBeforeEdit( IM5Bunch<ISkDashboardCfg> aValues ) {
    Pair<String, IOptionSet> p = make( aValues );
    return master().svs().validator().canEditDashboardCfg( p.left(), p.right() );
  }

  @Override
  protected ISkDashboardCfg doEdit( IM5Bunch<ISkDashboardCfg> aValues ) {
    Pair<String, IOptionSet> p = make( aValues );
    return master().editDashboard( p.left(), p.right() );
  }

  @Override
  protected ValidationResult doBeforeRemove( ISkDashboardCfg aEntity ) {
    return master().svs().validator().canRemoveDashboardCfg( aEntity.strid() );
  }

  @Override
  protected void doRemove( ISkDashboardCfg aEntity ) {
    master().removeDashboard( aEntity.strid() );
  }

  @Override
  protected IList<ISkDashboardCfg> doListEntities() {
    IListEdit<ISkDashboardCfg> ll = new ElemArrayList<>();
    for( String mnid : master().listDashboardIds() ) {
      ll.add( master().getDashboard( mnid ) );
    }
    return ll;
  }

}
