# create_and_dump_qtable fileName
define create_and_dump_qtable
  # --- Get all class information
  call (void *) objc_getClass("NSString")
  set $NSString_class = $
  call (void *) objc_getClass("QTable")
  set $QTable_class = $
  call (void *) objc_getClass("NSBundle")
  set $NSBundle_class = $
  # --- Get all selectors
  call (void *) sel_registerName("stringWithFormat:")
  set $NSString_stringWithFormat_sel = $
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
    $NSString_class, \
    $NSString_stringWithFormat_sel, \
    @"$arg0" \
  )
  set $fileName = $
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
    $fileName, \
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
