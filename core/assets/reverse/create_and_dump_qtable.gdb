# create_and_dump_qtable fileName
define create_and_dump_qtable
  # --- Get all class information
  call (void *) objc_getClass("QTable")
  set $QTable_class = $
  call (void *) objc_getClass("NSBundle")
  set $NSBundle_class = $
  # --- Get all selectors
  call (void *) sel_registerName("alloc")
  set $QTable_alloc_sel = $
  call (void *) sel_registerName("initWithFile:")
  set $QTable_initWithFile_sel = $
  call (void *) sel_registerName("mainBundle")
  set $NSBundle_mainBundle_sel = $
  call (void *) sel_registerName("pathForResource:ofType:")
  set $NSBundle_pathForResource_sel = $
  # --- Extract table
  call (void *) objc_msgSend( \
    $QTable_class, \
    $QTable_alloc_sel \
  )
  set $table = $
  call (void *) objc_msgSend( \
    $NSBundle_class, \
    $NSBundle_mainBundle_sel \
  )
  set $bundle = $
  call (void *) objc_msgSend( \
    $bundle, \
    $NSBundle_pathForResource_sel, \
    @"$arg0", \
    @"tble" \
  )
  set $path = $
  call (void *) objc_msgSend( \
    $table, \
    $QTable_initWithFile_sel, \
    $path \
  )
  set $r0 = $
  # --- Dump table
  echo Outputting dictionary of <QTable\ 
  output/x $r0
  echo > to file $arg0.log\n
  set logging file $arg0.log
  set logging redirect on
	set logging overwrite on
  set logging on
  po *($r0 + 8)
  set logging off
	set logging overwrite off
  set logging redirect off
end
#
define extract_all_qtables
  create_and_dump_qtable BaseUpgrade
  create_and_dump_qtable Description_kr
  create_and_dump_qtable Description_us
  create_and_dump_qtable ESpawn_01
  create_and_dump_qtable ESpawn_02
  create_and_dump_qtable ESpawn_03
  create_and_dump_qtable ESpawn_04
  create_and_dump_qtable ESpawn_05
  create_and_dump_qtable ESpawn_06
  create_and_dump_qtable ESpawn_07
  create_and_dump_qtable ESpawn_08
  create_and_dump_qtable ESpawn_09
  create_and_dump_qtable ESpawn_10
  create_and_dump_qtable ESpawn_11
  create_and_dump_qtable ESpawn_12
  create_and_dump_qtable ESpawn_13
  create_and_dump_qtable ESpawn_14
  create_and_dump_qtable ESpawn_15
  create_and_dump_qtable ESpawn_16
  create_and_dump_qtable ESpawn_17
  create_and_dump_qtable ESpawn_18
  create_and_dump_qtable ESpawn_19
  create_and_dump_qtable ESpawn_20
  create_and_dump_qtable ESpawn_21
  create_and_dump_qtable ESpawn_22
  create_and_dump_qtable ESpawn_23
  create_and_dump_qtable ESpawn_24
  create_and_dump_qtable ESpawn_25
  create_and_dump_qtable ESpawn_26
  create_and_dump_qtable ESpawn_27
  create_and_dump_qtable ESpawn_28
  create_and_dump_qtable ESpawn_29
  create_and_dump_qtable ESpawn_30
  create_and_dump_qtable ESpawn_31
  create_and_dump_qtable ESpawn_32
  create_and_dump_qtable ESpawn_33
  create_and_dump_qtable ESpawn_34
  create_and_dump_qtable ESpawn_35
  create_and_dump_qtable ESpawn_36
  create_and_dump_qtable ESpawn_37
  create_and_dump_qtable ESpawn_38
  create_and_dump_qtable ESpawn_39
  create_and_dump_qtable ESpawn_40
  create_and_dump_qtable ESpawn_41
  create_and_dump_qtable ESpawn_42
  create_and_dump_qtable ESpawn_43
  create_and_dump_qtable ESpawn_44
  create_and_dump_qtable ESpawn_45
  create_and_dump_qtable ESpawn_46
  create_and_dump_qtable ESpawn_47
  create_and_dump_qtable ESpawn_48
  create_and_dump_qtable ESpawn_49
  create_and_dump_qtable ESpawn_50
  create_and_dump_qtable ESpawn_51
  create_and_dump_qtable ESpawn_52
  create_and_dump_qtable ESpawn_53
  create_and_dump_qtable ESpawn_54
  create_and_dump_qtable ESpawn_55
  create_and_dump_qtable ESpawn_56
  create_and_dump_qtable ESpawn_57
  create_and_dump_qtable ESpawn_58
  create_and_dump_qtable ESpawn_59
  create_and_dump_qtable ESpawn_60
  create_and_dump_qtable ESpawn_61
  create_and_dump_qtable ESpawn_62
  create_and_dump_qtable ESpawn_63
  create_and_dump_qtable ESpawn_64
  create_and_dump_qtable ESpawn_65
  create_and_dump_qtable ESpawn_66
  create_and_dump_qtable ESpawn_67
  create_and_dump_qtable ESpawn_68
  create_and_dump_qtable ESpawn_69
  create_and_dump_qtable ESpawn_70
  create_and_dump_qtable ESpawn_71
  create_and_dump_qtable ESpawn_72
  create_and_dump_qtable ESpawn_73
  create_and_dump_qtable ESpawn_74
  create_and_dump_qtable ESpawn_75
  create_and_dump_qtable ESpawn_76
  create_and_dump_qtable ESpawn_77
  create_and_dump_qtable ESpawn_78
  create_and_dump_qtable ESpawn_79
  create_and_dump_qtable ESpawn_80
  create_and_dump_qtable ItemInfo
  create_and_dump_qtable ItemRegen
  create_and_dump_qtable MachBuild_Name
  create_and_dump_qtable MachBuild_Parts
  create_and_dump_qtable MachList
  create_and_dump_qtable MachRegen
  create_and_dump_qtable MapList
  create_and_dump_qtable PartsList
  create_and_dump_qtable SpAttackList
  create_and_dump_qtable TileList
  create_and_dump_qtable WeaponInfo
end
