# Replace some strings in all the JSON files
$ErrorActionPreference='silentlycontinue'


# File "BaseUpgrade.json"
$content = [System.IO.File]::ReadAllText("./BaseUpgrade.json")
<# TODO #>
[System.IO.File]::WriteAllText("./BaseUpgrade.json"i, $content)


# File "Description_us.json"
<# -- No modification to operate here #>


# File "ESpawn_??.json"
Get-ChildItem './ESpawn_*.json' | ForEach {
  $file = $_
  $content = [System.IO.File]::ReadAllText($file)
  <# TODO #>
  [System.IO.File]::WriteAllText($file, $content)
}


# File "ItemInfo.json"
$content = [System.IO.File]::ReadAllText("./ItemInfo.json")
<# TODO #>
[System.IO.File]::WriteAllText("./ItemInfo.json", $content)


# File "ItemRegen.json"
$content = [System.IO.File]::ReadAllText("./ItemRegen.json")
<# TODO #>
[System.IO.File]::WriteAllText("./ItemRegen.json", $content)


# File "MachBuild_Name.json"
$content = [System.IO.File]::ReadAllText("./MachBuild_Name.json")
<# TODO #>
[System.IO.File]::WriteAllText("./MachBuild_Name.json", $content)


# File "MachBuild_Parts.json"
$content = [System.IO.File]::ReadAllText("./MachBuild_Parts.json")
<# TODO #>
[System.IO.File]::WriteAllText("./MachBuild_Parts.json", $content)


# File "MachList.json"
$content = [System.IO.File]::ReadAllText("./MachList.json")
<# TODO #>
[System.IO.File]::WriteAllText("./MachList.json", $content)


# File "MachRegen.json"
$content = [System.IO.File]::ReadAllText("./MachRegen.json")
<# TODO #>
[System.IO.File]::WriteAllText("./MachRegen.json", $content)


# File "MapList.json"
$content = [System.IO.File]::ReadAllText("./MapList.json")
$content = $content -replace 'BG_([0-9]+)', '$1'
[System.IO.File]::WriteAllText("./MapList.json", $content)


# File "PartsList.json"
$content = [System.IO.File]::ReadAllText("./PartsList.json")
<# TODO #>
[System.IO.File]::WriteAllText("./PartsList.json", $content)


# File "SpAttackList.json"
$content = [System.IO.File]::ReadAllText("./SpAttackList.json")
<# TODO #>
[System.IO.File]::WriteAllText("./SpAttackList.json", $content)


# File "TileList.json"
$content = [System.IO.File]::ReadAllText("./TileList.json")
<# TODO #>
[System.IO.File]::WriteAllText("./TileList.json", $content)


# File "WeaponInfo.json"
$content = [System.IO.File]::ReadAllText("./WeaponInfo.json")
<# TODO #>
[System.IO.File]::WriteAllText("./WeaponInfo.json", $content)
