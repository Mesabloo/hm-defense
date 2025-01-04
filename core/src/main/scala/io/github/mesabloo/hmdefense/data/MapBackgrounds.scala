package io.github.mesabloo.hmdefense.data

/** Fetches the index for the background textures that are used for some given
  * level.
  *
  * @param index
  *   The level to get the background for.
  * @return
  *   The index of the background textures.
  */
def backgroundIndex(index: Int) = index match
  case 1 | 36 | 49 | 70       => 1
  case 2 | 37 | 80            => 4
  case 3 | 25 | 61            => 24
  case 4 | 39 | 77            => 20
  case 5 | 40                 => 2
  case 6 | 41 | 76            => 8
  case 7 | 42                 => 19
  case 8                      => 10
  case 9 | 44 | 47 | 73       => 11
  case 10 | 31 | 48 | 57 | 69 => 18
  case 11 | 46 | 79           => 17
  case 12 | 24 | 50 | 62      => 16
  case 13 | 38 | 51 | 63 | 78 => 15
  case 14 | 26 | 52 | 64      => 21
  case 15 | 27 | 53 | 65      => 9
  case 16 | 28 | 54 | 66      => 12
  case 17 | 29 | 55 | 67 | 75 => 14
  case 18 | 30 | 56 | 68      => 5
  case 19 | 43                => 22
  case 20 | 32 | 58           => 7
  case 21 | 33 | 59           => 13
  case 22 | 34 | 60           => 6
  case 23 | 45                => 23
  case 35 | 74                => 25
  case 71                     => 26
  case 72                     => 27
