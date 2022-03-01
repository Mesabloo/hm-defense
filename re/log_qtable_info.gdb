define log_table
  echo Outputting dictionary of <QTable 0x
  output/x $r0
  echo > to file table$arg0.log\n
  #
  # setup logging to correct file
  set logging file table$arg0.log
  set logging redirect on
  set logging on
  # assuming the object is in the $r0 register
  po *($r0 + 8)
  # disable logging
  set logging off
  set logging redirect off
end
