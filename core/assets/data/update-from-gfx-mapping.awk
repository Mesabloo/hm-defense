# BaseUpgrade.json

FILENAME ~ /BaseUpgrade\.json/ {
  print $0
}

# Description_us.json

FILENAME ~ /Description_us\.json/ {
  # No modification to do here
  print $0
}

# ESpawn_??.json

FILENAME ~ /ESpawn_..\.json/ {
  print $0
}

# ItemInfo.json

FILENAME ~ /ItemInfo\.json/ {
  print $0
}

# ItemRegen.json

FILENAME ~ /ItemRegen\.json/ {
  print $0
}

# MachBuild_Name.json

FILENAME ~ /MachBuild_Name\.json/ {
  print $0
}

# MachBuild_Parts.json

FILENAME ~ /MachBuild_Parts\.json/ {
  print $0
}

# MachList.json

FILENAME ~ /MachList\.json/ {
  print $0
}

# MachRegen.json

FILENAME ~ /MachRegen\.json/ {
  print $0
}

# MapList.json
FILENAME ~ /MapList\.json/ {
  $0 = gensub(/BG_([[:digit:]]+)/, "\\1", "g", $0);
  # Replace every "BG_??" with only "??"

  print $0
}

# PartsList.json

FILENAME ~ /PartsList\.json/ {
  print $0
}

# SpAttackList.json

FILENAME ~ /SpAttackList\.json/ {
  print $0
}

# TileList.json

FILENAME ~ /TileList\.json/ {
  print $0
}

# WeaponInfo.json

FILENAME ~ /WeaponInfo\.json/ {
  print $0
}
