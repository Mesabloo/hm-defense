- `Attr`: Purpose unknown
- `Code`: A unique code to reference this ressource in other data files (must **NOT** be changed)
- `Icon`: The unique identifier of a specific icon
- `Name`: A name to print somewhere (can be changed)
- `Param1`:
  - For `Coin` and `Mineral`: how much do you gain
  - For `SpAtk`: the level to apply the asset to
  - For `PtWpn`: always `0`
  - For `PtBody`: the life of the mach if it has this body
  - For `PtBtm`: ???
- `Param2`:
  - For `Coin` and `Mineral`: always `0`
  - For `SpAtk`: ???
  - For `PtWpn`: always `0`
  - For `PtBody`: ???
  - For `PtBtm`: ???
- `Param3`:
  - For `Coin` and `Mineral`: always `0`
  - For `SpAtk`: always `0`
  - For `PtWpn`: always `0`
  - For `PtBody`: ???
  - For `PtBtm`: ???
- `Type`: a string identifying the type of the item
- `strParam`:
  - For `Coin` and `Mineral`: `null`
  - For `SpAtk`: a string identifying which special attacks it is
  - For `PtWpn`, `PtBody` and `PtFoot`: the name of the file containing the asset (without the extension)

| Attr   | Code    | Icon    | Name                   | Param1 | Param2 | Param3 | Type      | strParam        |
| ------ | ------- | ------- | ---------------------- | ------ | ------ | ------ | --------- | --------------- |
| "-"    | "00001" | "32009" | "Coin 1"               |        | 0      | 0      | "Coin"    | null            |
| "-"    | "00002" | "32010" | "Coin 5"               |        | 0      | 0      | "Coin"    | null            |
| "-"    | "00003" | "32011" | "Coin 10"              |        | 0      | 0      | "Coin"    | null            |
| "-"    | "00004" | "32012" | "Coin 50"              |        | 0      | 0      | "Coin"    | null            |
| "-"    | "00005" | "32013" | "Coin 100"             |        | 0      | 0      | "Coin"    | null            |
| "-"    | "00101" | "32001" | "Mineral 1"            |        | 0      | 0      | "Mineral" | null            |
| "-"    | "00102" | "32002" | "Mineral 1"            |        | 0      | 0      | "Mineral" | null            |
| "-"    | "00103" | "32003" | "Mineral 1"            |        | 0      | 0      | "Mineral" | null            |
| "-"    | "00104" | "32004" | "Mineral 1"            |        | 0      | 0      | "Mineral" | null            |
| "-"    | "00105" | "32005" | "Mineral 1"            |        | 0      | 0      | "Mineral" | null            |
| "BSQC" | "02001" | "64001" | "Air-strike Bomb"      |        |        | 0      | "SpAtk"   | "AS_BOMB"       |
| "BSQC" | "02002" | "64002" | "Air-strike Bomb"      |        |        | 0      | "SpAtk"   | "AS_BOMB"       |
| "BSQC" | "02003" | "64003" | "Air-strike Bomb"      |        |        | 0      | "SpAtk"   | "AS_BOMB"       |
| "BSQC" | "02004" | "64004" | "Air-strike Bomb"      |        |        | 0      | "SpAtk"   | "AS_BOMB"       |
| "BSQC" | "02005" | "64005" | "Air-strike Bomb"      |        |        | 0      | "SpAtk"   | "AS_BOMB"       |
| "BSQC" | "02011" | "64011" | "Air-strike Missile"   |        |        | 0      | "SpAtk"   | "AS_MISSILE"    |
| "BSQC" | "02012" | "64012" | "Air-strike Missile"   |        |        | 0      | "SpAtk"   | "AS_MISSILE"    |
| "BSQC" | "02013" | "64013" | "Air-strike Missile"   |        |        | 0      | "SpAtk"   | "AS_MISSILE"    |
| "BSQC" | "02014" | "64014" | "Air-strike Missile"   |        |        | 0      | "SpAtk"   | "AS_MISSILE"    |
| "BSQC" | "02015" | "64015" | "Air-strike Missile"   |        |        | 0      | "SpAtk"   | "AS_MISSILE"    |
| "BSQC" | "02021" | "64021" | "Air-strike Nuclear"   |        |        | 0      | "SpAtk"   | "AS_NUCLEAR"    |
| "BSQC" | "02022" | "64022" | "Air-strike Nuclear"   |        |        | 0      | "SpAtk"   | "AS_NUCLEAR"    |
| "BSQC" | "02023" | "64023" | "Air-strike Nuclear"   |        |        | 0      | "SpAtk"   | "AS_NUCLEAR"    |
| "BSQC" | "02024" | "64024" | "Air-strike Nuclear"   |        |        | 0      | "SpAtk"   | "AS_NUCLEAR"    |
| "BSQC" | "02025" | "64025" | "Air-strike Nuclear"   |        |        | 0      | "SpAtk"   | "AS_NUCLEAR"    |
| "BSQC" | "02031" | "64031" | "Air-strike EMP Shock" |        |        | 0      | "SpAtk"   | "AS_EMP"        |
| "BSQC" | "02032" | "64032" | "Air-strike EMP Shock" |        |        | 0      | "SpAtk"   | "AS_EMP"        |
| "BSQC" | "02033" | "64033" | "Air-strike EMP Shock" |        |        | 0      | "SpAtk"   | "AS_EMP"        |
| "BSQC" | "02034" | "64034" | "Air-strike EMP Shock" |        |        | 0      | "SpAtk"   | "AS_EMP"        |
| "BSQC" | "02041" | "64041" | "Air-strike Laser"     |        |        | 0      | "SpAtk"   | "AS_LASER"      |
| "BSQC" | "02042" | "64042" | "Air-strike Laser"     |        |        | 0      | "SpAtk"   | "AS_LASER"      |
| "BSQC" | "02043" | "64043" | "Air-strike Laser"     |        |        | 0      | "SpAtk"   | "AS_LASER"      |
| "BSQC" | "02044" | "64044" | "Air-strike Laser"     |        |        | 0      | "SpAtk"   | "AS_LASER"      |
| "BSQC" | "02101" | "64101" | "Crossfire Missile"    |        |        | 0      | "SpAtk"   | "CF_MISSILE"    |
| "BSQC" | "02102" | "64102" | "Crossfire Missile"    |        |        | 0      | "SpAtk"   | "CF_MISSILE"    |
| "BSQC" | "02103" | "64103" | "Crossfire Missile"    |        |        | 0      | "SpAtk"   | "CF_MISSILE"    |
| "BSQC" | "02104" | "64104" | "Crossfire Missile"    |        |        | 0      | "SpAtk"   | "CF_MISSILE"    |
| "BSQC" | "02105" | "64105" | "Crossfire Missile"    |        |        | 0      | "SpAtk"   | "CF_MISSILE"    |
| "BSQC" | "02106" | "64106" | "Crossfire Missile"    |        |        | 0      | "SpAtk"   | "CF_MISSILE"    |
| "BSQC" | "02107" | "64107" | "Crossfire Missile"    |        |        | 0      | "SpAtk"   | "CF_MISSILE"    |
| "BSQC" | "02108" | "64108" | "Crossfire Missile"    |        |        | 0      | "SpAtk"   | "CF_MISSILE"    |
| "BSQC" | "02111" | "64111" | "Crossfire Flame"      |        |        | 0      | "SpAtk"   | "CF_FLAME"      |
| "BSQC" | "02112" | "64112" | "Crossfire Flame"      |        |        | 0      | "SpAtk"   | "CF_FLAME"      |
| "BSQC" | "02113" | "64113" | "Crossfire Flame"      |        |        | 0      | "SpAtk"   | "CF_FLAME"      |
| "BSQC" | "02114" | "64114" | "Crossfire Flame"      |        |        | 0      | "SpAtk"   | "CF_FLAME"      |
| "BSQC" | "02115" | "64115" | "Crossfire Flame"      |        |        | 0      | "SpAtk"   | "CF_FLAME"      |
| "BS"   | "10041" | "10041" | "MG-16e"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Gun_401" |
| "S"    | "10011" | "10011" | "MG-16"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Gun_101" |
| "BS"   | "10042" | "10042" | "MG-20s"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Gun_402" |
| "BS"   | "10012" | "10012" | "MG-20"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Gun_102" |
| "BS"   | "10013" | "10013" | "MG-21"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Gun_103" |
| "BS"   | "10047" | "10047" | "MG-200"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Gun_407" |
| "BS"   | "10014" | "10014" | "MG-30"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Gun_104" |
| "BS"   | "10043" | "10043" | "MG-21k"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Gun_403" |
| "BS"   | "10015" | "10015" | "MG-40"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Gun_105" |
| "BS"   | "10016" | "10016" | "MG-47"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Gun_106" |
| "BS"   | "10032" | "10032" | "MG-20k"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_HMG_01"  |
| "BS"   | "10024" | "10024" | "MG-30i"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_HMG_02"  |
| "BS"   | "10033" | "10033" | "MG-21s"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_HMG_03"  |
| "BS"   | "10044" | "10044" | "MG-30e"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_HMG_04"  |
| "BS"   | "10025" | "10025" | "MG-40e"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_HMG_05"  |
| "BS"   | "10045" | "10045" | "MG-40 mk3"            | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_HMG_06"  |
| "BS"   | "10034" | "10034" | "MG-30r"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_HMG_07"  |
| "BS"   | "10035" | "10035" | "MG-40 mk2"            | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_HMG_08"  |
| "BS"   | "10046" | "10046" | "MG-47.e"              | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_HMG_09"  |
| "BS"   | "10026" | "10026" | "MG-47.k"              | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_HMG_10"  |
| "S"    | "10111" | "10111" | "ML-1"                 | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_101" |
| "BS"   | "10118" | "10118" | "ML-E4"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_108" |
| "BS"   | "10112" | "10112" | "ML-K2"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_102" |
| "BS"   | "10115" | "10115" | "ML-603"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_105" |
| "BS"   | "10116" | "10116" | "ML-E1"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_106" |
| "BS"   | "10132" | "10132" | "ML-1 mk2"             | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_302" |
| "BS"   | "10133" | "10133" | "ML-60"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_303" |
| "BS"   | "10134" | "10134" | "ML-1 mk3"             | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_304" |
| "BS"   | "10135" | "10135" | "ML-604"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_305" |
| "BS"   | "10137" | "10137" | "ML-100"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_307" |
| "BS"   | "10126" | "10126" | "ML-T3"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_206" |
| "BS"   | "10128" | "10128" | "ML-U300"              | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_208" |
| "BS"   | "10127" | "10127" | "ML-U200"              | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_207" |
| "BS"   | "10143" | "10143" | "ML_T2"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_403" |
| "BS"   | "10140" | "10140" | "ML-200"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_310" |
| "BS"   | "10129" | "10129" | "ML-903"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_209" |
| "BS"   | "10144" | "10144" | "ML-T2 mk2"            | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_404" |
| "BS"   | "10130" | "10130" | "ML-TU"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_210" |
| "BS"   | "10131" | "10131" | "ML-TU.2"              | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_301" |
| "S"    | "10145" | "10145" | "ML-901"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Msl_405" |
| "BS"   | "10211" | "10211" | "SG-1"                 | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Shg_101" |
| "BS"   | "10215" | "10215" | "SG-7"                 | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Shg_105" |
| "BS"   | "10218" | "10218" | "SG-45 m2"             | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Shg_108" |
| "BS"   | "10212" | "10212" | "SG-1e"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Shg_102" |
| "BS"   | "10226" | "10226" | "SG-9"                 | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Shg_206" |
| "BS"   | "10221" | "10221" | "SG-60"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Shg_201" |
| "BS"   | "10228" | "10228" | "SG-X2"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Shg_208" |
| "BS"   | "10224" | "10224" | "SG-603"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Shg_204" |
| "BS"   | "10230" | "10230" | "SG-X10"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Shg_210" |
| "BS"   | "10223" | "10223" | "SG-UX"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Shg_203" |
| "BS"   | "10311" | "10311" | "LS-D"                 | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Lsr_101" |
| "BS"   | "10312" | "10312" | "LS-102"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Lsr_102" |
| "BS"   | "10313" | "10313" | "LS-103"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Lsr_103" |
| "BS"   | "10316" | "10316" | "LS-103D"              | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Lsr_106" |
| "BS"   | "10317" | "10317" | "LS-L20"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Lsr_107" |
| "BS"   | "10318" | "10318" | "LS-L21"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Lsr_108" |
| "BS"   | "10319" | "10319" | "LS-X100"              | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Lsr_109" |
| "BS"   | "10321" | "10321" | "LS-L30"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Lsr_111" |
| "BS"   | "10340" | "10340" | "LS-K102"              | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Lsr_210" |
| "BS"   | "10320" | "10320" | "LS-UL1"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Lsr_110" |
| "BS"   | "10416" | "10416" | "RG-16"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Rlg_106" |
| "BS"   | "10417" | "10417" | "RG-C1"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Rlg_107" |
| "BS"   | "10418" | "10418" | "RG-C2"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Rlg_108" |
| "BS"   | "10419" | "10419" | "RG-35"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Rlg_109" |
| "BS"   | "10420" | "10420" | "RG-DM1"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Rlg_110" |
| "BS"   | "10421" | "10421" | "RG-DM2"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Rlg_201" |
| "BS"   | "10422" | "10422" | "RG-UD"                | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Rlg_202" |
| "BS"   | "10423" | "10423" | "RG-TR500"             | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Rlg_203" |
| "BS"   | "10428" | "10428" | "RG-Ak2"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Rlg_208" |
| "BS"   | "10429" | "10429" | "RG-Ak3"               | 0      | 0      | 0      | "PtWpn"   | "P_Wpn_Rlg_209" |
| "BS"   | "11001" | "11001" | "BD-p-Dr"              |        |        | 0      | "PtBody"  | "P_Body_02"     |
| "BS"   | "11002" | "11002" | "BD-p-Pl"              |        |        | 0      | "PtBody"  | "P_Body_05"     |
| "BS"   | "11003" | "11003" | "BD-p-Jg"              |        |        | 0      | "PtBody"  | "P_Body_03"     |
| "BS"   | "11004" | "11004" | "BD-p-Mt"              |        |        | 0      | "PtBody"  | "P_Body_04"     |
| "BS"   | "11005" | "11005" | "BDP-Nasl"             |        |        | 0      | "PtBody"  | "P_Body_06"     |
| "BS"   | "11006" | "11006" | "BDP-D4"               |        |        | 0      | "PtBody"  | "P_Body_11"     |
| "BS"   | "11007" | "11007" | "BDP-D5"               |        |        | 0      | "PtBody"  | "P_Body_12"     |
| "BS"   | "11008" | "11008" | "BDP-D-Jg2"            |        |        | 0      | "PtBody"  | "P_Body_16"     |
| "BS"   | "11009" | "11009" | "BDE-F1"               |        |        | 0      | "PtBody"  | "P_Body_17"     |
| "BS"   | "11010" | "11010" | "BDE-F2"               |        |        | 0      | "PtBody"  | "P_Body_18"     |
| "BS"   | "11011" | "11001" | "BD-p-Dr"              |        |        | 0      | "PtBody"  | "PB_HMG_01"     |
| "BS"   | "11012" | "11002" | "BD-p-Pl"              |        |        | 0      | "PtBody"  | "PB_HMG_02"     |
| "BS"   | "11013" | "11003" | "BD-p-Jg"              |        |        | 0      | "PtBody"  | "PB_HMG_03"     |
| "BS"   | "11014" | "11004" | "BD-p-Mt"              |        |        | 0      | "PtBody"  | "PB_HMG_04"     |
| "BS"   | "11015" | "11005" | "BDP-Nasl"             |        |        | 0      | "PtBody"  | "PB_HMG_05"     |
| "BS"   | "11016" | "11006" | "BDP-D4"               |        |        | 0      | "PtBody"  | "PB_HMG_06"     |
| "BS"   | "11017" | "11007" | "BDP-D5"               |        |        | 0      | "PtBody"  | "PB_HMG_07"     |
| "BS"   | "11018" | "11008" | "BDP-D-Jg2"            |        |        | 0      | "PtBody"  | "PB_HMG_08"     |
| "BS"   | "11019" | "11009" | "BDE-F1"               |        |        | 0      | "PtBody"  | "PB_HMG_09"     |
| "BS"   | "11020" | "11010" | "BDE-F2"               |        |        | 0      | "PtBody"  | "PB_HMG_10"     |
| "BS"   | "11021" | "11001" | "BD-p-Dr"              |        |        | 0      | "PtBody"  | "PB_Missile_01" |
| "BS"   | "11022" | "11002" | "BD-p-Pl"              |        |        | 0      | "PtBody"  | "PB_Missile_02" |
| "BS"   | "11023" | "11003" | "BD-p-Jg"              |        |        | 0      | "PtBody"  | "PB_Missile_03" |
| "BS"   | "11024" | "11004" | "BD-p-Mt"              |        |        | 0      | "PtBody"  | "PB_Missile_04" |
| "BS"   | "11025" | "11005" | "BDP-Nasl"             |        |        | 0      | "PtBody"  | "PB_Missile_05" |
| "BS"   | "11026" | "11006" | "BDP-D4"               |        |        | 0      | "PtBody"  | "PB_Missile_06" |
| "BS"   | "11027" | "11007" | "BDP-D5"               |        |        | 0      | "PtBody"  | "PB_Missile_07" |
| "BS"   | "11028" | "11008" | "BDP-D-Jg2"            |        |        | 0      | "PtBody"  | "PB_Missile_08" |
| "BS"   | "11029" | "11009" | "BDE-F1"               |        |        | 0      | "PtBody"  | "PB_Missile_09" |
| "BS"   | "11030" | "11010" | "BDE-F2"               |        |        | 0      | "PtBody"  | "PB_Missile_10" |
| "BS"   | "11031" | "11001" | "BD-p-Dr"              |        |        | 0      | "PtBody"  | "PB_HMsl_01"    |
| "BS"   | "11032" | "11002" | "BD-p-Pl"              |        |        | 0      | "PtBody"  | "PB_HMsl_02"    |
| "BS"   | "11033" | "11003" | "BD-p-Jg"              |        |        | 0      | "PtBody"  | "PB_HMsl_03"    |
| "BS"   | "11034" | "11004" | "BD-p-Mt"              |        |        | 0      | "PtBody"  | "PB_HMsl_04"    |
| "BS"   | "11035" | "11005" | "BDP-Nasl"             |        |        | 0      | "PtBody"  | "PB_HMsl_05"    |
| "BS"   | "11036" | "11006" | "BDP-D4"               |        |        | 0      | "PtBody"  | "PB_HMsl_06"    |
| "BS"   | "11037" | "11007" | "BDP-D5"               |        |        | 0      | "PtBody"  | "PB_HMsl_07"    |
| "BS"   | "11038" | "11008" | "BDP-D-Jg2"            |        |        | 0      | "PtBody"  | "PB_HMsl_08"    |
| "BS"   | "11039" | "11009" | "BDE-F1"               |        |        | 0      | "PtBody"  | "PB_HMsl_09"    |
| "BS"   | "11040" | "11010" | "BDE-F2"               |        |        | 0      | "PtBody"  | "PB_HMsl_10"    |
| "BS"   | "11051" | "11001" | "BD-p-Dr"              |        |        | 0      | "PtBody"  | "PB_Shg_01"     |
| "BS"   | "11052" | "11002" | "BD-p-Pl"              |        |        | 0      | "PtBody"  | "PB_Shg_02"     |
| "BS"   | "11053" | "11003" | "BD-p-Jg"              |        |        | 0      | "PtBody"  | "PB_Shg_03"     |
| "BS"   | "11054" | "11004" | "BD-p-Mt"              |        |        | 0      | "PtBody"  | "PB_Shg_04"     |
| "BS"   | "11055" | "11005" | "BDP-Nasl"             |        |        | 0      | "PtBody"  | "PB_Shg_05"     |
| "BS"   | "11056" | "11006" | "BDP-D4"               |        |        | 0      | "PtBody"  | "PB_Shg_06"     |
| "BS"   | "11057" | "11007" | "BDP-D5"               |        |        | 0      | "PtBody"  | "PB_Shg_07"     |
| "BS"   | "11058" | "11008" | "BDP-D-Jg2"            |        |        | 0      | "PtBody"  | "PB_Shg_08"     |
| "BS"   | "11059" | "11009" | "BDE-F1"               |        |        | 0      | "PtBody"  | "PB_Shg_09"     |
| "BS"   | "11060" | "11010" | "BDE-F2"               |        |        | 0      | "PtBody"  | "PB_Shg_10"     |
| "BS"   | "11061" | "11001" | "BD-p-Dr"              |        |        | 0      | "PtBody"  | "PB_Plasma_01"  |
| "BS"   | "11062" | "11002" | "BD-p-Pl"              |        |        | 0      | "PtBody"  | "PB_Plasma_02"  |
| "BS"   | "11063" | "11003" | "BD-p-Jg"              |        |        | 0      | "PtBody"  | "PB_Plasma_03"  |
| "BS"   | "11064" | "11004" | "BD-p-Mt"              |        |        | 0      | "PtBody"  | "PB_Plasma_04"  |
| "BS"   | "11065" | "11005" | "BDP-Nasl"             |        |        | 0      | "PtBody"  | "PB_Plasma_05"  |
| "BS"   | "11066" | "11006" | "BDP-D4"               |        |        | 0      | "PtBody"  | "PB_Plasma_06"  |
| "BS"   | "11067" | "11007" | "BDP-D5"               |        |        | 0      | "PtBody"  | "PB_Plasma_07"  |
| "BS"   | "11068" | "11008" | "BDP-D-Jg2"            |        |        | 0      | "PtBody"  | "PB_Plasma_08"  |
| "BS"   | "11069" | "11009" | "BDE-F1"               |        |        | 0      | "PtBody"  | "PB_Plasma_09"  |
| "BS"   | "11070" | "11010" | "BDE-F2"               |        |        | 0      | "PtBody"  | "PB_Plasma_10"  |
| "BS"   | "11071" | "11001" | "BD-p-Dr"              |        |        | 0      | "PtBody"  | "PB_Ion_01"     |
| "BS"   | "11072" | "11002" | "BD-p-Pl"              |        |        | 0      | "PtBody"  | "PB_Ion_02"     |
| "BS"   | "11073" | "11003" | "BD-p-Jg"              |        |        | 0      | "PtBody"  | "PB_Ion_03"     |
| "BS"   | "11074" | "11004" | "BD-p-Mt"              |        |        | 0      | "PtBody"  | "PB_Ion_04"     |
| "BS"   | "11075" | "11005" | "BDP-Nasl"             |        |        | 0      | "PtBody"  | "PB_Ion_05"     |
| "BS"   | "11076" | "11006" | "BDP-D4"               |        |        | 0      | "PtBody"  | "PB_Ion_06"     |
| "BS"   | "11077" | "11007" | "BDP-D5"               |        |        | 0      | "PtBody"  | "PB_Ion_07"     |
| "BS"   | "11078" | "11008" | "BDP-D-Jg2"            |        |        | 0      | "PtBody"  | "PB_Ion_08"     |
| "BS"   | "11079" | "11009" | "BDE-F1"               |        |        | 0      | "PtBody"  | "PB_Ion_09"     |
| "BS"   | "11080" | "11010" | "BDE-F2"               |        |        | 0      | "PtBody"  | "PB_Ion_10"     |
| "S"    | "12001" | "12001" | "Default Foot"         |        |        |        | "PtBtm"   | "P_Foot_01"     |
| "BS"   | "12002" | "12002" | "Ft-D2"                |        |        |        | "PtBtm"   | "P_Foot_02"     |
| "BS"   | "12003" | "12003" | "Ft-D3"                |        |        |        | "PtBtm"   | "P_Foot_03"     |
| "BS"   | "12004" | "12004" | "Ft-D4"                |        |        |        | "PtBtm"   | "P_Foot_04"     |
| "BS"   | "12005" | "12005" | "Ft-D5"                |        |        |        | "PtBtm"   | "P_Foot_05"     |
| "BS"   | "12006" | "12006" | "Ft-E6"                |        |        |        | "PtBtm"   | "P_Foot_06"     |
| "BS"   | "12007" | "12007" | "Ft-E6.m2"             |        |        |        | "PtBtm"   | "P_Foot_07"     |
| "BS"   | "12008" | "12008" | "Ft-Sp1"               |        |        |        | "PtBtm"   | "P_Foot_08"     |
| "BS"   | "12009" | "12009" | "Ft-Sp2.2"             |        |        |        | "PtBtm"   | "P_Foot_09"     |
| "BS"   | "12010" | "12010" | "Ft-Sp3"               |        |        |        | "PtBtm"   | "P_Foot_10"     |
| "BS"   | "12011" | "12011" | "Ft-Sp3.5"             |        |        |        | "PtBtm"   | "P_Foot_11"     |
| "BS"   | "12012" | "12012" | "Ft-Sp3.E"             |        |        |        | "PtBtm"   | "P_Foot_12"     |
| "BS"   | "12013" | "12013" | "Ft-EM45"              |        |        |        | "PtBtm"   | "P_Foot_13"     |
| "BS"   | "12014" | "12014" | "Ft-EM47"              |        |        |        | "PtBtm"   | "P_Foot_14"     |
| "BS"   | "12015" | "12015" | "Ft-EM1000"            |        |        |        | "PtBtm"   | "P_Foot_15"     |
| "BS"   | "12016" | "12016" | "Ft-Sp700"             |        |        |        | "PtBtm"   | "P_Foot_16"     |
| "BS"   | "12017" | "12017" | "Ft-Sp720"             |        |        |        | "PtBtm"   | "P_Foot_17"     |
| "BS"   | "12018" | "12018" | "Ft-Sp800k"            |        |        |        | "PtBtm"   | "P_Foot_18"     |
| "BS"   | "12019" | "12019" | "Ft-N45"               |        |        |        | "PtBtm"   | "P_Foot_19"     |
| "BS"   | "12020" | "12020" | "Ft-N48"               |        |        |        | "PtBtm"   | "P_Foot_20"     |
| "BS"   | "12021" | "12021" | "Ft-ESM.11"            |        |        |        | "PtBtm"   | "P_Foot_21"     |
| "BS"   | "12022" | "12022" | "Ft-ESM.20"            |        |        |        | "PtBtm"   | "P_Foot_22"     |
| "BS"   | "12023" | "12023" | "Ft-ESM.X"             |        |        |        | "PtBtm"   | "P_Foot_23"     |
