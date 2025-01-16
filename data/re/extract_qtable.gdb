define get_method_impl
	# returns the address of the implementation of the function whose name is given in parameter from the `QTable` class.
  call (void *) class_getMethodImplementation((void *) objc_getClass("QTable"), (void *) sel_registerName($arg0))
end
define extract_qtable_methods
	# Extract every method using the Objective-C runtime.
  get_method_impl "decodeFile"
  set $decodeFile = $
  get_method_impl "initWithFile:"
  set $initWithFile = $
  get_method_impl "loadBuffer:"
  set $loadBuffer = $
  get_method_impl "dealloc"
  set $dealloc = $
  get_method_impl "getDataFrom:Pos:To:"
  set $getDataFrom = $
  get_method_impl "getKey:"
  set $getKey = $
  get_method_impl "getString:Key:"
  set $getString = $
  get_method_impl "getInt:Key:"
  set $getInt = $
  get_method_impl "getFloat:Key:"
  set $getFloat = $
  get_method_impl "row"
  set $row = $
  get_method_impl "column"
  set $column = $
  #
  printf "Class `QTable` impl addresses:\n\
\t- [QTable $decodeFile]: 0x%x\n\
\t- [QTable $initWithFile:]: 0x%x\n\
\t- [QTable $loadBuffer:]: 0x%x\n\
\t- [QTable $dealloc]: 0x%x\n\
\t- [QTable $getDataFrom:Pos:To:]: 0x%x\n\
\t- [QTable $getKey:]: 0x%x\n\
\t- [QTable $getString:Key:]: 0x%x\n\
\t- [QTable $getInt:Key:]: 0x%x\n\
\t- [QTable $getFloat:Key:]: 0x%x\n\
\t- [QTable $row]: 0x%x\n\
\t- [QTable $column]: 0x%x\n", $decodeFile, $initWithFile, $loadBuffer, $dealloc, $getDataFrom, $getKey, $getString, $getInt, $getFloat, $row, $column
end
