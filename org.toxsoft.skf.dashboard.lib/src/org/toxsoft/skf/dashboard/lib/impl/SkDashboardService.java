package org.toxsoft.skf.dashboard.lib.impl;

import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.core.tslib.gw.IGwHardConstants.*;
import static org.toxsoft.skf.dashboard.lib.ISkDashboardServiceHardConstants.*;
import static org.toxsoft.skf.dashboard.lib.l10n.ISkDashboardServiceSharedResources.*;
import static org.toxsoft.uskat.core.ISkHardConstants.*;

import org.toxsoft.core.tslib.av.opset.*;
import org.toxsoft.core.tslib.av.opset.impl.*;
import org.toxsoft.core.tslib.bricks.ctx.*;
import org.toxsoft.core.tslib.bricks.events.*;
import org.toxsoft.core.tslib.bricks.strid.coll.*;
import org.toxsoft.core.tslib.bricks.strid.coll.impl.*;
import org.toxsoft.core.tslib.bricks.validator.*;
import org.toxsoft.core.tslib.bricks.validator.impl.*;
import org.toxsoft.core.tslib.coll.*;
import org.toxsoft.core.tslib.coll.helpers.*;
import org.toxsoft.core.tslib.coll.primtypes.*;
import org.toxsoft.core.tslib.coll.primtypes.impl.*;
import org.toxsoft.core.tslib.gw.gwid.*;
import org.toxsoft.core.tslib.gw.skid.*;
import org.toxsoft.core.tslib.utils.errors.*;
import org.toxsoft.core.tslib.utils.logs.impl.*;
import org.toxsoft.skf.dashboard.lib.*;
import org.toxsoft.uskat.core.*;
import org.toxsoft.uskat.core.api.sysdescr.dto.*;
import org.toxsoft.uskat.core.devapi.*;
import org.toxsoft.uskat.core.impl.*;
import org.toxsoft.uskat.core.impl.dto.*;

/**
 * {@link ISkDashboardService} implementation.
 *
 * @author hazard157
 */
public class SkDashboardService
    extends AbstractSkService
    implements ISkDashboardService {

  /**
   * Service creator singleton.
   */
  public static final ISkServiceCreator<AbstractSkService> CREATOR = SkDashboardService::new;

  /**
   * {@link ISkDashboardService#svs()} implementation.
   *
   * @author hazard157
   */
  static class ValidationSupport
      extends AbstractTsValidationSupport<ISkDashboardServiceValidator>
      implements ISkDashboardServiceValidator {

    @Override
    public ISkDashboardServiceValidator validator() {
      return this;
    }

    @Override
    public ValidationResult canCreateDashboardCfg( String aDashboardId, IOptionSet aAttrs ) {
      TsNullArgumentRtException.checkNulls( aDashboardId, aAttrs );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkDashboardServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canCreateDashboardCfg( aDashboardId, aAttrs ) );
      }
      return vr;
    }

    @Override
    public ValidationResult canEditDashboardCfg( String aDashboardId, IOptionSet aAttrs ) {
      TsNullArgumentRtException.checkNulls( aDashboardId, aAttrs );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkDashboardServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canEditDashboardCfg( aDashboardId, aAttrs ) );
      }
      return vr;
    }

    @Override
    public ValidationResult canSetDashboardData( String aDashboardId, String aData, ISkDashboardCfg aDashboardCfg ) {
      TsNullArgumentRtException.checkNulls( aDashboardId, aData, aDashboardCfg );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkDashboardServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canSetDashboardData( aDashboardId, aData, aDashboardCfg ) );
      }
      return vr;
    }

    @Override
    public ValidationResult canRemoveDashboardCfg( String aDashboardId ) {
      TsNullArgumentRtException.checkNulls( aDashboardId );
      ValidationResult vr = ValidationResult.SUCCESS;
      for( ISkDashboardServiceValidator v : validatorsList() ) {
        vr = ValidationResult.firstNonOk( vr, v.canRemoveDashboardCfg( aDashboardId ) );
      }
      return vr;
    }

  }

  /**
   * {@link ISkDashboardService#eventer()} implementation.
   *
   * @author hazard157
   */
  class Eventer
      extends AbstractTsEventer<ISkDashboardServiceListener> {

    private ECrudOp op      = null;
    private String  mnemoId = null;

    @Override
    protected void doClearPendingEvents() {
      op = null;
      mnemoId = null;
    }

    @Override
    protected void doFirePendingEvents() {
      if( op != null ) {
        reallyFireConfigEvent( op, mnemoId );
      }
    }

    @Override
    protected boolean doIsPendingEvents() {
      return op != null;
    }

    private void reallyFireConfigEvent( ECrudOp aOp, String aConfigId ) {
      for( ISkDashboardServiceListener l : listeners() ) {
        try {
          l.onDashboardCfgChanged( coreApi(), aOp, aConfigId );
        }
        catch( Exception ex ) {
          LoggerUtils.errorLogger().error( ex );
        }
      }
    }

    void fireConfigChanged( ECrudOp aOp, String aConfigId ) {
      if( isFiringPaused() ) {
        if( op == null ) { // first call
          op = aOp;
          mnemoId = aConfigId;
        }
        else { // second and subsequent calls
          op = ECrudOp.LIST;
          mnemoId = null;
        }
        return;
      }
      reallyFireConfigEvent( aOp, aConfigId );
    }

  }

  private final ISkDashboardServiceValidator builtinValidator = new ISkDashboardServiceValidator() {

    @Override
    public ValidationResult canCreateDashboardCfg( String aDashboardId, IOptionSet aAttrs ) {
      // error if mnemo with same ID already exists
      SkDashboardCfg mnemo = findDashboard( aDashboardId );
      if( mnemo != null ) {
        return ValidationResult.error( FMT_ERR_DASHBOARD_EXISTS, aDashboardId );
      }
      // warn for empty/default name
      String name = aAttrs.getStr( AID_NAME, null );
      if( name == null || name.isBlank() || name.equals( DEFAULT_NAME ) ) {
        return ValidationResult.warn( MSG_WARN_NAME_NOT_SET );
      }
      return ValidationResult.SUCCESS;
    }

    @Override
    public ValidationResult canEditDashboardCfg( String aDashboardId, IOptionSet aAttrs ) {
      // error if mnemo with asked ID not exists
      SkDashboardCfg mnemo = findDashboard( aDashboardId );
      if( mnemo == null ) {
        return ValidationResult.error( FMT_ERR_DASHBOARD_NOT_EXISTS, aDashboardId );
      }
      // warn for empty/default name
      String name = aAttrs.getStr( AID_NAME, null );
      if( name == null ) {
        name = mnemo.nmName();
      }
      if( name.isBlank() || name.equals( DEFAULT_NAME ) ) {
        return ValidationResult.warn( MSG_WARN_NAME_NOT_SET );
      }
      return ValidationResult.SUCCESS;
    }

    @Override
    public ValidationResult canSetDashboardData( String aDashboardId, String aData, ISkDashboardCfg aDashboardCfg ) {
      // error if mnemo with asked ID not exists
      SkDashboardCfg mnemo = findDashboard( aDashboardId );
      if( mnemo == null ) {
        return ValidationResult.error( FMT_ERR_DASHBOARD_NOT_EXISTS, aDashboardId );
      }
      return ValidationResult.SUCCESS;
    }

    @Override
    public ValidationResult canRemoveDashboardCfg( String aDashboardId ) {
      // warn if mnemo with asked ID not exists
      SkDashboardCfg mnemo = findDashboard( aDashboardId );
      if( mnemo == null ) {
        return ValidationResult.error( FMT_WARN_DASHBOARD_NOT_EXISTS, aDashboardId );
      }
      return ValidationResult.SUCCESS;
    }

  };

  private final ValidationSupport          svs               = new ValidationSupport();
  private final Eventer                    eventer           = new Eventer();
  private final ClassClaimingCoreValidator claimingValidator = new ClassClaimingCoreValidator();

  /**
   * Constructor.
   *
   * @param aCoreApi {@link IDevCoreApi} - owner core API implementation
   */
  public SkDashboardService( IDevCoreApi aCoreApi ) {
    super( SERVICE_ID, aCoreApi );
    svs.addValidator( builtinValidator );
  }

  // ------------------------------------------------------------------------------------
  // AbstractSkService
  //

  @Override
  protected void doInit( ITsContextRo aArgs ) {
    // create class for ISkDashboardCfg
    IDtoClassInfo mnemoCinf = internalCreateDashboardCfgClassDto();
    sysdescr().defineClass( mnemoCinf );
    objServ().registerObjectCreator( ISkDashboardServiceHardConstants.CLSID_DASHBOARD_CFG, SkDashboardCfg.CREATOR );
    // claim on self classes
    sysdescr().svs().addValidator( claimingValidator );
    objServ().svs().addValidator( claimingValidator );
    linkService().svs().addValidator( claimingValidator );
    clobService().svs().addValidator( claimingValidator );
    // listen to the mnemo content changes
    clobService().eventer().addListener( this::whenClobChanged );
    // register builtin abilities
    userService().abilityManager().defineKind( ABKIND_DASHBOARD );
    userService().abilityManager().defineAbility( ABILITY_DASHBOARD_EDIT_PARAMS );
  }

  @Override
  protected void doClose() {
    // nop
  }

  @Override
  protected boolean doIsClassClaimedByService( String aClassId ) {
    return switch( aClassId ) {
      case ISkDashboardServiceHardConstants.CLSID_DASHBOARD_CFG -> true;
      default -> false;
    };
  }

  /**
   * FIXME talk to MVK<br>
   * TODO listen to the server/backend messages to generate eventer messages
   */

  // ------------------------------------------------------------------------------------
  // implementation
  //

  /**
   * Creates DTO of {@link ISkDashboardServiceHardConstants#CLSID_DASHBOARD_CFG} class.
   *
   * @return {@link IDtoClassInfo} - class info
   */
  public static IDtoClassInfo internalCreateDashboardCfgClassDto() {
    DtoClassInfo cinf = new DtoClassInfo( CLSID_DASHBOARD_CFG, GW_ROOT_CLASS_ID, OptionSetUtils.createOpSet( //
        TSID_NAME, STR_DASHBOARD_CLASS, //
        TSID_DESCRIPTION, STR_DASHBOARD_CLASS_D //
    ) );
    OPDEF_SK_IS_SOURCE_CODE_DEFINED_CLASS.setValue( cinf.params(), AV_TRUE );
    cinf.clobInfos().add( CLBINF_DASHBOARD_CFG_DATA );
    return cinf;
  }

  // FIXME with MVK -> move this methods to the base class
  private void pauseCoreValidation() {
    sysdescr().svs().pauseValidator( claimingValidator );
    objServ().svs().pauseValidator( claimingValidator );
    linkService().svs().pauseValidator( claimingValidator );
    clobService().svs().pauseValidator( claimingValidator );
  }

  private void resumeCoreValidation() {
    sysdescr().svs().resumeValidator( claimingValidator );
    objServ().svs().resumeValidator( claimingValidator );
    linkService().svs().resumeValidator( claimingValidator );
    clobService().svs().resumeValidator( claimingValidator );
  }

  private static Skid makeDashboardSkid( String aDashboardId ) {
    return new Skid( CLSID_DASHBOARD_CFG, aDashboardId );
  }

  private static Gwid makeDashboardGwid( String aDashboardId ) {
    return Gwid.createClob( CLSID_DASHBOARD_CFG, aDashboardId, CLBID_DASHBOARD_CFG_DATA );
  }

  private void whenClobChanged( @SuppressWarnings( "unused" ) ISkCoreApi aCoreApi, Gwid aClobGwid ) {
    if( aClobGwid.classId().equals( CLSID_DASHBOARD_CFG ) ) {
      eventer.fireConfigChanged( ECrudOp.EDIT, aClobGwid.strid() );
    }
  }

  // ------------------------------------------------------------------------------------
  // ISkDashboardService
  //

  @Override
  public SkDashboardCfg findDashboard( String aDashboardId ) {
    return coreApi().objService().find( makeDashboardSkid( aDashboardId ) );
  }

  @Override
  public SkDashboardCfg getDashboard( String aDashboardId ) {
    return coreApi().objService().get( makeDashboardSkid( aDashboardId ) );
  }

  @Override
  public IStringList listDashboardIds() {
    ISkidList skids = objServ().listSkids( CLSID_DASHBOARD_CFG, false );
    IStringListEdit ll = new StringArrayList( skids.size() );
    for( Skid s : skids ) {
      ll.add( s.strid() );
    }
    return ll;
  }

  @Override
  public IStridablesList<ISkDashboardCfg> listDashboardCfgs() {
    IList<ISkDashboardCfg> ll = objServ().listObjs( CLSID_DASHBOARD_CFG, true );
    return new StridablesList<>( ll );
  }

  @Override
  public ISkDashboardCfg createDashboard( String aDashboardId, IOptionSet aAttrs ) {
    TsValidationFailedRtException.checkError( svs.canCreateDashboardCfg( aDashboardId, aAttrs ) );
    DtoObject dto = new DtoObject( makeDashboardSkid( aDashboardId ), aAttrs, IStringMap.EMPTY );
    pauseCoreValidation();
    try {
      return objServ().defineObject( dto );
      // TODO generate event
    }
    finally {
      resumeCoreValidation();
    }
  }

  @Override
  public ISkDashboardCfg editDashboard( String aDashboardId, IOptionSet aAttrs ) {
    TsValidationFailedRtException.checkError( svs.canEditDashboardCfg( aDashboardId, aAttrs ) );
    DtoObject dto = DtoObject.createDtoObject( makeDashboardSkid( aDashboardId ), coreApi() );
    dto.attrs().addAll( aAttrs );
    pauseCoreValidation();
    try {
      return objServ().defineObject( dto );
      // TODO generate event
    }
    finally {
      resumeCoreValidation();
    }
  }

  @Override
  public void setDashboardData( String aDashboardId, String aData ) {
    TsNullArgumentRtException.checkNulls( aDashboardId, aData );
    SkDashboardCfg mnemo = getDashboard( aDashboardId );
    TsValidationFailedRtException.checkError( svs.canSetDashboardData( aDashboardId, aData, mnemo ) );
    pauseCoreValidation();
    try {
      clobService().writeClob( makeDashboardGwid( aDashboardId ), aData );
      // TODO generate event
    }
    finally {
      resumeCoreValidation();
    }
  }

  @Override
  public String getDashboardData( String aDashboardId ) {
    getDashboard( aDashboardId ); // checks for existence
    return clobService().readClob( makeDashboardGwid( aDashboardId ) );
  }

  @Override
  public void removeDashboard( String aDashboardId ) {
    TsValidationFailedRtException.checkError( svs.canRemoveDashboardCfg( aDashboardId ) );
    pauseCoreValidation();
    try {
      objServ().removeObject( makeDashboardSkid( aDashboardId ) );
      // TODO generate event
    }
    finally {
      resumeCoreValidation();
    }
  }

  @Override
  public ITsValidationSupport<ISkDashboardServiceValidator> svs() {
    return svs;
  }

  @Override
  public ITsEventer<ISkDashboardServiceListener> eventer() {
    return eventer;
  }

}
