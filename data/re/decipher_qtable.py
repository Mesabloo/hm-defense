import sys

with open(sys.argv[1], 'r') as f:
  partial_key = [*map(int, filter(None, f.read().split(' ')))]

with open(sys.argv[2], 'rb') as f:
  i = 0
  f.read(6)
  while (byte := f.read(1)) and i < len(partial_key):
    print(chr(byte[0] ^ partial_key[i]), end='')
    i += 1
